package com.zhixin.zhfz.glpt.controller.zgdb;

import com.zhixin.zhfz.common.entity.ComboboxEntity;
import com.zhixin.zhfz.common.entity.MessageEntity;
import com.zhixin.zhfz.common.entity.OperLogEntity;
import com.zhixin.zhfz.common.services.operLog.IOperLogService;
import com.zhixin.zhfz.common.utils.ControllerTool;
import com.zhixin.zhfz.glpt.entity.RectAnswerEntity;
import com.zhixin.zhfz.glpt.entity.RectRequestEntity;
import com.zhixin.zhfz.glpt.form.RectForm;
import com.zhixin.zhfz.glpt.services.zg.IRectService;
import com.zhixin.zhfz.glpt.vo.PageResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/zhfz/glpt/zgdb")
public class RectController {

    private static final Logger logger = LoggerFactory.getLogger(RectController.class);


    @Autowired
    private IRectService RectService;

    @Autowired
    private IOperLogService operLogService;

    @RequestMapping("/rect/request/listRequest")
    @ResponseBody
    public PageResponse rListRequest(@RequestParam Map<String,Object> params, HttpServletRequest request){
        params = ControllerTool.mapFilter(params);
        return RectService.listRequest(params);
    }

    @RequestMapping("/rect/answer/listRequest")
    @ResponseBody
    public PageResponse aListRequest(@RequestParam Map<String,Object> params, HttpServletRequest request){
        params = ControllerTool.mapFilter(params);
        return RectService.listRequest(params);
    }

    @RequestMapping("/rect/addRequest")
    @ResponseBody
    public MessageEntity addRequest(RectRequestEntity entity,HttpServletRequest request){
        logger.info("===addRequest==",entity);
        try {
            entity.setCount(0);
            entity.setAuditOrgId(entity.getReceiveOrgId());
            entity.setOpUserId(Long.valueOf(ControllerTool.getUserID(request)));
            entity.setSendUserId(entity.getOpUserId());
            entity.setSendOrgId(ControllerTool.getSessionInfo(request).getCurrentOrg().getPid());
            RectService.insertRequest(entity);
            this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "下发整改" + entity.toString(), ControllerTool.getUser(request).getLoginName(), true,"管理平台");
        } catch (Exception e) {
            e.printStackTrace();
            this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "下发整改" + entity.toString(), ControllerTool.getUser(request).getLoginName(), false,"管理平台");
            return MessageEntity.error("添加失败!");
        }

        return MessageEntity.success("添加成功!");
    }

    @RequestMapping("/rect/listAnswer")
    @ResponseBody
    public PageResponse listAnswer(@RequestParam Map<String,Object> params, HttpServletRequest request){
        params = ControllerTool.mapFilter(params);
        return RectService.listAnswer(params);
    }


    @RequestMapping("/rect/addAnswer")
    @ResponseBody
    public MessageEntity addAnswer(RectAnswerEntity entity, HttpServletRequest request){
        logger.info("===addAnswer==",entity);
        try {
            entity.setOpOrgId(ControllerTool.getSessionInfo(request).getCurrentOrg().getPid());
            entity.setOpUserId("-"+ControllerTool.getUserID(request).toString()+"-");
            entity.setReceiveUserId(Long.valueOf(ControllerTool.getUserID(request).toString()));
            entity.setAuditOrgId(ControllerTool.getSessionInfo(request).getCurrentOrg().getPid());
            RectService.insertAnswer(entity);
            this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "整改反馈" + entity.toString(), ControllerTool.getUser(request).getLoginName(), true,"管理平台");
        } catch (Exception e) {
            e.printStackTrace();
            this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "整改反馈" + entity.toString(), ControllerTool.getUser(request).getLoginName(), false,"管理平台");
            return MessageEntity.error("添加失败!");
        }

        return MessageEntity.success("添加成功!");
    }


    @RequestMapping("/rect/status")
    @ResponseBody
    public MessageEntity status(RectForm form,HttpServletRequest request){
        logger.info("===status==",form);
        try {
            String result = form.getStatus() == 2 ? ":驳回" : ":通过";
            form.setAuditOrgId(ControllerTool.getSessionInfo(request).getCurrentOrg().getPid());
            form.setAuditUserId(Long.valueOf(ControllerTool.getUserID(request)));
            form.setAuditContent(ControllerTool.getSessionInfo(request).getCurrentOrg().getName() + result + "\n" + form.getAuditContent());
            RectService.status(form);
            this.operLogService.insertLog(OperLogEntity.UPLOAD_TYPE, "整改处理" + form.toString(), ControllerTool.getUser(request).getLoginName(), true,"管理平台");
        } catch (Exception e) {
            e.printStackTrace();
            this.operLogService.insertLog(OperLogEntity.UPLOAD_TYPE, "整改处理" + form.toString(), ControllerTool.getUser(request).getLoginName(), false,"管理平台");
            return MessageEntity.error("审批失败!");
        }
        return MessageEntity.success("审批成功!");
    }

}
