package com.zhixin.zhfz.glpt.controller.zgdb;

import com.zhixin.zhfz.common.entity.ComboboxEntity;
import com.zhixin.zhfz.common.entity.MessageEntity;
import com.zhixin.zhfz.common.entity.OperLogEntity;
import com.zhixin.zhfz.common.services.operLog.IOperLogService;
import com.zhixin.zhfz.common.utils.ControllerTool;
import com.zhixin.zhfz.glpt.entity.SupervisorAnswerEntity;
import com.zhixin.zhfz.glpt.entity.SupervisorRequestEntity;
import com.zhixin.zhfz.glpt.form.SupervisorForm;
import com.zhixin.zhfz.glpt.services.duban.ISupervisorService;
import com.zhixin.zhfz.glpt.vo.PageResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/zhfz/glpt/zgdb")
public class SupervisorController {

    private static final Logger logger = LoggerFactory.getLogger(SupervisorController.class);


    @Autowired
    private ISupervisorService supervisorService;

    @Autowired
    private IOperLogService operLogService;

    @RequestMapping("/sup/request/listRequest")
    @ResponseBody
    public PageResponse rListRequest(@RequestParam Map<String,Object> params, HttpServletRequest request){
        params = ControllerTool.mapFilter(params);
        return supervisorService.listRequest(params);
    }

    @RequestMapping("/sup/answer/listRequest")
    @ResponseBody
    public PageResponse aListRequest(@RequestParam Map<String,Object> params, HttpServletRequest request){
        params = ControllerTool.mapFilter(params);
        return supervisorService.listRequest(params);
    }

    @RequestMapping("/sup/addRequest")
    @ResponseBody
    public MessageEntity addRequest(SupervisorRequestEntity entity,HttpServletRequest request){
        logger.info("===addRequest==",entity);
        try {
            entity.setCount(0);
            entity.setAuditOrgId(entity.getReceiveOrgId());
            entity.setOpUserId(Long.valueOf(ControllerTool.getUserID(request)));
            entity.setSendUserId(entity.getOpUserId());
            entity.setSendOrgId(ControllerTool.getSessionInfo(request).getCurrentOrg().getPid());
            supervisorService.insertRequest(entity);
            this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "下发督办" + entity.toString(), ControllerTool.getUser(request).getLoginName(), true,"管理平台");
        } catch (Exception e) {
            e.printStackTrace();
            this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "下发督办" + entity.toString(), ControllerTool.getUser(request).getLoginName(), false,"管理平台");
            return MessageEntity.error("添加失败!");
        }

        return MessageEntity.success("添加成功!");
    }

    @RequestMapping("/sup/listAnswer")
    @ResponseBody
    public PageResponse listAnswer(@RequestParam Map<String,Object> params, HttpServletRequest request){
        params = ControllerTool.mapFilter(params);
        return supervisorService.listAnswer(params);
    }


    @RequestMapping("/sup/addAnswer")
    @ResponseBody
    public MessageEntity addAnswer(SupervisorAnswerEntity entity, HttpServletRequest request){
        logger.info("===addAnswer==",entity);
        try {
            entity.setOpPid(ControllerTool.getSessionInfo(request).getCurrentOrg().getPid());
            entity.setOpUserId("-"+ControllerTool.getUserID(request).toString()+"-");
            entity.setReceiveUserId(Long.valueOf(ControllerTool.getUserID(request).toString()));
            entity.setAuditOrgId(ControllerTool.getSessionInfo(request).getCurrentOrg().getPid());
            supervisorService.insertAnswer(entity);
            this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "督办反馈" + entity.toString(), ControllerTool.getUser(request).getLoginName(), true,"管理平台");
        } catch (Exception e) {
            e.printStackTrace();
            this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "督办反馈" + entity.toString(), ControllerTool.getUser(request).getLoginName(), false,"管理平台");
            return MessageEntity.error("添加失败!");
        }

        return MessageEntity.success("添加成功!");
    }


    @RequestMapping("/sup/status")
    @ResponseBody
    public MessageEntity status(SupervisorForm form,HttpServletRequest request){
        logger.info("===status==",form);
        try {
            String result = form.getStatus() == 2 ? ":驳回" : ":通过";
            form.setAuditOrgId(ControllerTool.getSessionInfo(request).getCurrentOrg().getPid());
            form.setAuditUserId(Long.valueOf(ControllerTool.getUserID(request)));
            form.setAuditContent(ControllerTool.getSessionInfo(request).getCurrentOrg().getName() + result + "\n" + form.getAuditContent());
            supervisorService.status(form);
            this.operLogService.insertLog(OperLogEntity.UPLOAD_TYPE, "督办处理" + form.toString(), ControllerTool.getUser(request).getLoginName(), true,"管理平台");
        } catch (Exception e) {
            e.printStackTrace();
            this.operLogService.insertLog(OperLogEntity.UPLOAD_TYPE, "督办处理" + form.toString(), ControllerTool.getUser(request).getLoginName(), false,"管理平台");
            return MessageEntity.error("审批失败!");
        }
        return MessageEntity.success("审批成功!");
    }

    @RequestMapping("/caseCombobox")
    @ResponseBody
    public List<ComboboxEntity> caseCombobox(Map<String,Object> params ,HttpServletRequest request){
        return supervisorService.caseCombobox(params);
    }

}
