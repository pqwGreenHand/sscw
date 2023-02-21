package com.zhixin.zhfz.bacs.controller.checkbody;


import com.zhixin.zhfz.bacs.entity.CheckBodyEntity;
import com.zhixin.zhfz.bacs.entity.LawDocProcessEntity;
import com.zhixin.zhfz.bacs.form.CheckbodyLawDocForm;
import com.zhixin.zhfz.bacs.services.checkbody.ICheckBodyService;
import com.zhixin.zhfz.bacs.services.lawdoc.ILawDocProcessService;
import com.zhixin.zhfz.common.entity.MessageEntity;
import com.zhixin.zhfz.sacw.common.BaseConfig;
import com.zhixin.zhfz.sacw.common.FreemarkerUtil;
import com.zhixin.zhfz.sacw.common.OfficeUtil;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.Action;
import java.io.IOException;

@Controller
@RequestMapping("/zhfz/bacs/checkbody")
public class CheckBodyController {

    @Autowired
    private ICheckBodyService checkBodyService;

    @Autowired
    private ILawDocProcessService lawDocProcessService;

    @RequestMapping(value = "/selectid")
    @ResponseBody
    public CheckBodyEntity selectid(@RequestParam Long serialId, HttpServletRequest request) {
        CheckBodyEntity checkBodyEntity = new CheckBodyEntity();
        checkBodyEntity.setInterrogateSerialId(serialId);
        checkBodyEntity  = checkBodyService.getCheckBodyBySerialId(checkBodyEntity);
        return checkBodyEntity;
    }

    @RequestMapping(value = "/preview")
    @ResponseBody
    public MessageEntity officeView(@RequestBody CheckbodyLawDocForm form) throws IOException, TemplateException {
        System.out.println("===================checkbody预览==========" + form.getUserId());
        //
        String officedir = BaseConfig.getInstance().getOfficeFileDir();

        String templatePath = BaseConfig.getInstance().getTemplateDir();
        LawDocProcessEntity result = lawDocProcessService.getProcessData1(form);
        String fileName = result.getTempFileName();
        String xmlFileName = result.getXmlFileName();
        //String path = rootPath + "src/main/webapp/officefile/" + fileName;

        // 生成模板文件
        String officefile = officedir + fileName;
        FreemarkerUtil.process(templatePath, xmlFileName, officefile, result.getData());

        // 预览
        String htmlfile = officefile + ".html";
        int fileType = result.getFileType();// 1:word；2:execl
        if (fileType == 1) {
            OfficeUtil.wordTohtml(officefile, htmlfile);
        } else if (fileType == 2) {
            OfficeUtil.excelTohtml(officefile, htmlfile);
        } else {
            return new MessageEntity().addIsError(true).addContent("类型错误!");
        }
        return new MessageEntity().addIsError(false).addContent(fileName + ".html");
    }
}
