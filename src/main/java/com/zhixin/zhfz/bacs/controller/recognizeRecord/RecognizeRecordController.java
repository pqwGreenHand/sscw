package com.zhixin.zhfz.bacs.controller.recognizeRecord;


import com.zhixin.zhfz.bacs.entity.RecognizeRecordEntity;
import com.zhixin.zhfz.bacs.services.recognizeRecord.IRecognizeRecordService;
import com.zhixin.zhfz.common.entity.MessageEntity;
import com.zhixin.zhfz.common.entity.OperLogEntity;
import com.zhixin.zhfz.common.entity.RoleEntity;
import com.zhixin.zhfz.common.entity.SessionInfo;
import com.zhixin.zhfz.common.services.operLog.IOperLogService;
import com.zhixin.zhfz.common.utils.ControllerTool;
import com.zhixin.zhfz.common.utils.PropertyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/zhfz/bacs/recognizeRecord")
public class RecognizeRecordController {

     @Autowired
     private IRecognizeRecordService recognizeService;

     @Autowired
     private IOperLogService operLogService;

    private static final Logger logger = LoggerFactory.getLogger(RecognizeRecordController.class);
    /**
     * 查所有及分页及条件查询
     *
     * @param param
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Map<String,Object> list(@RequestParam Map<String,Object> param, HttpServletRequest request){
        Map<String, Object> map = ControllerTool.mapFilter(param);
        List<RecognizeRecordEntity> list = new ArrayList<>();
        int total = 0;
        boolean flag = true;

        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuth(request))) {
            // 办案人员-本人
            String policeName = ControllerTool.getSessionInfo(request).getUser().getRealName();
            //map.put("policeName1", policeName);
            map.put("dataAuth", " ( r.op_user_id=" + ControllerTool.getUserID(request)
                    + " or r.policeman= " + ControllerTool.getUserID(request)
                    + " or i.op_user_id= " + ControllerTool.getUserID(request)
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_SELFAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 办案场所-本办案场所
           // map.put("areaId", "r.area_id=" + ControllerTool.getCurrentAreaID(request));
            map.put("dataAuth", "( i.id=" + ControllerTool.getCurrentAreaID(request)
                +" or  r.area_id = " + ControllerTool.getCurrentAreaID(request)
            +" ) ");
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 管理员 -本办案场所及下级办案场所
           // map.put("areaId", ControllerTool.getAreasInSql("r.area_id",  ControllerTool.getSessionInfo(request).getCurrentAndSubArea()));
            map.put("dataAuth", "( i.id  " + sessionInfo.getCurrentAndSubAreaInStr()
                +" or r.area_id  " + sessionInfo.getCurrentAndSubAreaInStr()
            +" ) ");
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 上级办案场所及其下级办案场所
            map.put("dataAuth", " (i.id  " + sessionInfo.getSuperAndSubAreaInStr()
                +" or r.area_id " + sessionInfo.getSuperAndSubAreaInStr()
            +" ) ");
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 公安领导-本部门及下级部门
            map.put("dataAuth"," ( r.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                    + " or i.op_pid like " +  sessionInfo.getCurrentAndSubOrgPid()
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 法制人员-上级部门及下级部门
            map.put("dataAuth"," ( r.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
                    + " or i.op_pid like " +  sessionInfo.getSuperAndSubOrgPid()
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuth(request))) {
            // 本部门
            map.put("dataAuth"," ( r.op_pid = " + sessionInfo.getCurrentOrgPid()
                    + " or i.op_pid = " +  sessionInfo.getCurrentOrgPid()
                    + " ) ");
        }
        if (flag) {
            list = this.recognizeService.list(map);
            total = this.recognizeService.count(map);
        }
        /*list = recognizeService.list(map);
        int count = recognizeService.count(map);*/

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", total);
        result.put("rows", list);
        return result;
    }


    // 新增辨认笔录信息
    @RequestMapping(value = "/add")
    @ResponseBody
    public MessageEntity insert(@RequestBody RecognizeRecordEntity form,HttpServletRequest request){
         RecognizeRecordEntity entity = new RecognizeRecordEntity();
         entity.setAreaId(form.getAreaId());
         entity.setAddress(form.getAddress());
         entity.setCaseId(form.getCaseId());
         entity.setPersonId(form.getPersonId());
         entity.setPoliceman(form.getPoliceman());
         entity.setRecognize(form.getRecognize());
         entity.setVictim(form.getVictim());
         entity.setWitniss(form.getWitniss());
         entity.setTarget(form.getTarget());
         entity.setResult(form.getResult());
         entity.setRecognizeType(form.getRecognizeType());
         entity.setUserId(ControllerTool.getUserID(request));
        entity.setOpPid(ControllerTool.getSessionInfo(request).getCurrentOrg().getPid());
        entity.setOpUserId(ControllerTool.getSessionInfo(request).getUser().getId());
        if ((form.getSerialId()+"" == null) || (form.getSerialId()+"" == "")) {
            entity.setSerialId(-99);
        } else {
            entity.setSerialId(form.getSerialId());
        }

        try {
            recognizeService.insert(entity);
            entity.setId(recognizeService.getMaxId());
            this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "添加辨认记录信息:" + entity, ControllerTool.getRoleName(request), true,"办案场所");
        } catch (Exception e) {
            e.printStackTrace();
            this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "添加辨认记录信息:" + entity, ControllerTool.getRoleName(request), false,"办案场所");
            return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("添加辨认记录失败!");
        }
        return MessageEntity.success("添加辨认记录成功").addCallbackData(entity);
    }


    // 修改
    @RequestMapping(value = "/update")
    @ResponseBody
    public MessageEntity update(@RequestBody RecognizeRecordEntity form,HttpServletRequest request){
        RecognizeRecordEntity entity = new RecognizeRecordEntity();
        entity.setId(form.getId());
        entity.setAreaId(form.getAreaId());
        entity.setAddress(form.getAddress());
        entity.setCaseId(form.getCaseId());
        entity.setPersonId(form.getPersonId());
        entity.setPoliceman(form.getPoliceman());
        entity.setRecognize(form.getRecognize());
        entity.setVictim(form.getVictim());
        entity.setWitniss(form.getWitniss());
        entity.setTarget(form.getTarget());
        entity.setResult(form.getResult());
        entity.setRecognizeType(form.getRecognizeType());
        entity.setUserId(ControllerTool.getUserID(request));

        if ((form.getSerialId()+"" == null) || (form.getSerialId()+"" == "")) {
            entity.setSerialId(-99);
        } else {
            entity.setSerialId(form.getSerialId());
        }

        try {
            recognizeService.update(entity);
            this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "修改辨认记录信息:" + entity, ControllerTool.getRoleName(request), true,"办案场所");
        } catch (Exception e) {
            e.printStackTrace();
            this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "修改辨认记录信息:" + entity, ControllerTool.getRoleName(request), false,"办案场所");
            return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("修改辨认记录失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("提示").addContent("修改辨认记录成功!");
    }


    @RequestMapping(value = "/queryConfig")
    @ResponseBody
    public MessageEntity queryConfig(HttpServletRequest request, HttpServletResponse response){
         String id = request.getParameter("id");
         String param = "";
         try {
             //  1(id) 4(action_code 4代表是辨认) http://127.0.0.1:8081 127.0.0.1;21;interrogate;ns204$$" 样例
            String returnUrl = getCSUrl(Long.parseLong(id), 4);
            logger.info("参数==========================" + returnUrl);
            return new MessageEntity().addIsError(false).addCallbackData(returnUrl);
         } catch (NumberFormatException e) {
             e.printStackTrace();
             logger.error("发起查询辨认笔录启动参数：", e.getMessage());
             return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("开启辨认笔录失败!");
         }
    }

    private String getCSUrl(long recordId, int actionNum) {
        Object recordUrl = PropertyUtil.getContextProperty("record.address");
        Object webserviceIp = PropertyUtil.getContextProperty("webservice.ip");
        Object webservicePort = PropertyUtil.getContextProperty("webservice.port");

        Object ftpIp = PropertyUtil.getContextProperty("record.ftp.ip");
        Object ftpPort = PropertyUtil.getContextProperty("record.ftp.port");
        Object ftpUserName = PropertyUtil.getContextProperty("record.ftp.username");
        Object ftpPassword = PropertyUtil.getContextProperty("record.ftp.password");
        Object tokenO = PropertyUtil.getContextProperty("webservice.token");
        String token = "";
        if (tokenO != null && tokenO != "") {
            token = tokenO.toString();
        }
        String webserviceUrl = "http://" + webserviceIp + ":" + webservicePort;

        String ftpUrl = ftpIp + ";" + ftpPort + ";" + ftpUserName + ";" + ftpPassword;
        String param = recordId + " " + actionNum + " " + webserviceUrl + " " + ftpUrl;
        return recordUrl + " " + param;
    }
}
