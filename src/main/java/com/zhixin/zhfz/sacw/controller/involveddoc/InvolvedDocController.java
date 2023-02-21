package com.zhixin.zhfz.sacw.controller.involveddoc;

import com.sun.istack.logging.Logger;

import com.zhixin.zhfz.common.entity.MessageEntity;
import com.zhixin.zhfz.sacw.common.BaseConfig;
import com.zhixin.zhfz.sacw.common.FreemarkerUtil;
import com.zhixin.zhfz.sacw.common.OfficeUtil;
import com.zhixin.zhfz.sacw.common.Utils;
import com.zhixin.zhfz.sacw.entity.InvolvedDocEntity;
import com.zhixin.zhfz.sacw.form.InvolvedDocForm;
import com.zhixin.zhfz.sacw.services.involveddoc.IInvolvedDocService;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLEncoder;

@Controller
@RequestMapping("/zhfz/sacw/involveddoc")
public class InvolvedDocController {

    private static Logger logger = Logger.getLogger(InvolvedDocController.class);

    @Autowired
    private IInvolvedDocService involvedDocService;


    @RequestMapping("/preview")
    @ResponseBody
    public MessageEntity officeView(@RequestBody InvolvedDocForm form, HttpServletRequest request)
            throws IOException, TemplateException {
        logger.info("preview===================InvolvedId=" + form.getInvolvedId());
        String officeDir = BaseConfig.getInstance().getOfficeFileDir();
        String templatePath = BaseConfig.getInstance().getTemplateDir();

        InvolvedDocEntity result = involvedDocService.getProcessData(form, request);
        String fileName = result.getTempFileName();
        String xmlFileName = result.getXmlFileName();
        String officeFile = officeDir + fileName;
        // 生成文件
        FreemarkerUtil.process(templatePath, xmlFileName, officeFile, result.getData());
        // 生成预览文件
        String htmlname = Utils.getUniqueId() + ".html";
        String converPath = officeDir + htmlname;

        int fileType = result.getFileType();// 1:word；2:excel
        if (fileType == 1) {
            OfficeUtil.wordTohtml(officeFile, converPath);
        } else if (fileType == 2) {
            OfficeUtil.excelTohtml(officeFile, converPath);
        } else {
            return new MessageEntity().addIsError(true).addContent("类型错误!");
        }
        return new MessageEntity().addIsError(false).addContent(htmlname);
    }

    @RequestMapping(value = "/download")
    public void export(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        InvolvedDocForm form = new InvolvedDocForm();
        String number = request.getParameter("number");
        String name = request.getParameter("name");
        String type = request.getParameter("type");
        String involvedId = request.getParameter("involvedId");
        String recordId = request.getParameter("recordId");
        form.setNumber(Integer.parseInt(number));
        form.setInvolvedId(Long.parseLong(involvedId));
        form.setRecordId(Long.parseLong(recordId));
        logger.info("===number" + number + "===name==" + name + "===type=="
                + type + "===involvedId===" + involvedId + "===recordId===" + recordId);

        response.reset();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/x-download");
        response.setHeader("Pragma", "public");
        response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
        InvolvedDocEntity result = involvedDocService.getProcessData(form, request);
        String downFileName = result.getDownFileName();
        if (request.getHeader("User-Agent").toLowerCase().indexOf("firefox") > 0) {
            downFileName = new String(downFileName.getBytes("UTF-8"), "ISO8859-1");
        } else {
            downFileName = URLEncoder.encode(downFileName, "UTF-8");
        }
        response.addHeader("Content-Disposition", "attachment;filename=" + downFileName);
        String xmlFileName = result.getXmlFileName();
        PrintWriter out = response.getWriter();
        URL url = Thread.currentThread().getContextClassLoader().getResource("");
        String templatePath = url.getPath() + "template";
        System.out.println("getData" + result.getData());
        FreemarkerUtil.process(templatePath, xmlFileName, result.getData(), out);
    }


}
