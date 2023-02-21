package com.zhixin.zhfz.bacs.controller.blwmb;

import com.zhixin.zhfz.bacs.entity.RecordTemplateEntity;
import com.zhixin.zhfz.bacs.form.RecordTemplateForm;
import com.zhixin.zhfz.bacs.services.blwmb.IRecordTemplateService;
import com.zhixin.zhfz.common.entity.MessageEntity;
import com.zhixin.zhfz.common.entity.OperLogEntity;
import com.zhixin.zhfz.common.entity.RoleEntity;
import com.zhixin.zhfz.common.entity.SessionInfo;
import com.zhixin.zhfz.common.services.operLog.IOperLogService;
import com.zhixin.zhfz.common.utils.ControllerTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: zhfz
 * @description: 笔录word模板
 * @author: jzw
 * @create: 2019-02-21 10:14
 **/
@Controller
@RequestMapping("/zhfz/bacs/blwmb")
public class RecordTemplateController {

    private static final Logger logger = LoggerFactory.getLogger(RecordTemplateController.class);


    @Autowired
    private IRecordTemplateService service;


    @Autowired
    private IOperLogService operLogService;

    /**
     * @return java.util.Map<java.lang.String       ,       java.lang.Object>
     * @Author jzw
     * @Description
     * @Date 11:45 2019/2/21
     * @Param [param]
     **/
    @RequestMapping(value = "/list")
    @ResponseBody
    public Map<String, Object> list(@RequestParam Map<String, Object> param, HttpServletRequest request) throws Exception {
        logger.info("---------------------------blwmb/list-------------------------");
        Map<String, Object> map = ControllerTool.mapFilter(param);
        List<RecordTemplateEntity> list = new ArrayList<RecordTemplateEntity>();
        int total = 0;
        list = service.list(map);
        total = this.service.count(map);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", total);
        result.put("rows", list);
        return result;
    }


    @RequestMapping("/add")
    @ResponseBody
    public MessageEntity add(RecordTemplateForm form, HttpServletRequest request) {
        logger.info("---------------------------blwmb/add-------------------------");
        if (form.getType() == 1) {
            form.setUserId(Long.valueOf(ControllerTool.getUserID(request)));
        }
        try {
            service.insert(form, request);
            this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "添加笔录word模板" + form, ControllerTool.getLoginName(request), true, "办案场所");
        } catch (Exception e) {
            logger.error("Error on adding RecordTemplate!", e);
            this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "添加笔录word模板" + form, ControllerTool.getLoginName(request), false, "办案场所");
            return new MessageEntity().addCode(1).addIsError(true).addTitle("提示").addContent("添加失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("提示").addContent("添加成功!");
    }

    @RequestMapping("/edit")
    @ResponseBody
    public MessageEntity edit(RecordTemplateForm form, HttpServletRequest request) {
        logger.info("---------------------------blwmb/edit-------------------------");
        if (form.getType() == 1) {
            form.setUserId(Long.valueOf(ControllerTool.getUserID(request)));
        }
        try {
            service.update(form);
            this.operLogService.insertLog(OperLogEntity.EDIT_TYPE, "修改笔录word模板" + form, ControllerTool.getLoginName(request), true, "办案场所");
        } catch (Exception e) {
            logger.error("Error on editing RecordTemplate!", e);
            this.operLogService.insertLog(OperLogEntity.EDIT_TYPE, "修改笔录word模板" + form, ControllerTool.getLoginName(request), false, "办案场所");
            return new MessageEntity().addCode(1).addIsError(true).addTitle("提示").addContent("更新失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("提示").addContent("更新成功!");
    }


    @RequestMapping(value = "/remove")
    @ResponseBody
    public MessageEntity remove(Long id, HttpServletRequest request) {
        logger.info("---------------------------blwmb/remove-------------------------");
        try {
            service.delete(id);
            this.operLogService.insertLog(OperLogEntity.EDIT_TYPE, "删除笔录word模板：" + id, ControllerTool.getLoginName(request), true, "办案场所");
        } catch (Exception e) {
            logger.error("Error on deleting RecordTemplate!", e);
            this.operLogService.insertLog(OperLogEntity.EDIT_TYPE, "删除笔录word模板：" + id, ControllerTool.getLoginName(request), false, "办案场所");
            return new MessageEntity().addCode(1).addIsError(true).addTitle("提示").addContent("删除失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("提示").addContent("删除成功!");
    }

    /**
     * @return void
     * @Author jzw
     * @Description word文档预览
     * @Date 19:29 2019/2/21
     * @Param [id, fileName, response]
     **/
    @RequestMapping("/load")
    public void look(Long id, String fileName, HttpServletRequest request, HttpServletResponse response) {
        logger.info("---------------------------blwmb/load-------------------------");
        try {
            RecordTemplateEntity entity = service.getWordById(id);
            if (fileName == null)
                fileName = entity.getName().split(",")[0];
            setResponseHeader(response, fileName);
            OutputStream out = response.getOutputStream();
            out.write(entity.getWord());
            this.operLogService.insertLog(OperLogEntity.DELETE_TYPE, "下载笔录word模板：" + id, ControllerTool.getLoginName(request), true, "办案场所");
        } catch (Exception e) {
            logger.error("Error on loading RecordTemplate!", e);
            this.operLogService.insertLog(OperLogEntity.DELETE_TYPE, "下载笔录word模板：" + id, ControllerTool.getLoginName(request), false, "办案场所");

        }
    }

    /**
     * 设置响应头  文件类型为zip的   可以修改对应的后缀
     */
    public static void setResponseHeader(HttpServletResponse response, String fileName) {
        logger.info("---------------------------blwmb/setResponseHeader-------------------------");
        try {
            response.reset();// 清空输出流
            response.setContentType("application/octet-stream;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename="
                    + new String(fileName.getBytes("utf-8"), "8859_1")
                    + ".doc");
            response.addHeader("Pargam", "no-cache");
            response.addHeader("Cache-Control", "no-cache");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
