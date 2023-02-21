package com.zhixin.zhfz.sacw.controller.handfy;


import com.zhixin.zhfz.common.entity.MessageEntity;
import com.zhixin.zhfz.common.entity.OperLogEntity;
import com.zhixin.zhfz.common.entity.RoleEntity;
import com.zhixin.zhfz.common.entity.SessionInfo;
import com.zhixin.zhfz.common.services.operLog.IOperLogService;
import com.zhixin.zhfz.common.utils.ControllerTool;
import com.zhixin.zhfz.sacw.entity.InputRecordEntity;
import com.zhixin.zhfz.sacw.entity.InvolvedEntity;
import com.zhixin.zhfz.sacw.entity.OutputRecordEntity;
import com.zhixin.zhfz.sacw.form.OutputForm;
import com.zhixin.zhfz.sacw.services.handfy.IHandFyService;
import com.zhixin.zhfz.sacw.services.inputRecord.IInputRecordService;
import com.zhixin.zhfz.sacw.services.involved.IinvolvedService;
import com.zhixin.zhfz.sacw.services.outputRecord.IOutputRecordService;

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
@RequestMapping("/zhfz/sacw/handfy")
public class HandFyController {

    private static final Logger logger = LoggerFactory.getLogger(HandFyController.class);

    @Autowired
    private IOperLogService operLogService;

    @Autowired
    private IHandFyService service;

    @Autowired
    private IInputRecordService inputRecordService;

    @Autowired
    private IOutputRecordService outputRecordService;

    @Autowired
    private IinvolvedService iinvolvedService;

    /**
     * 查所有及分页及条件查询
     *
     * @param param
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Map<String, Object> list(@RequestParam Map<String, Object> param, HttpServletRequest request,
                                    HttpServletResponse response) throws Exception {
        List<InvolvedEntity> list = new ArrayList<>();
        Map<String, Object> result = new HashMap<String, Object>();
        int count = 0;
        boolean flag = true;
        Map<String, Object> map = ControllerTool.mapFilter(param);
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 办案人员-本人
            map.put("dataAuth", "(locate('," + ControllerTool.getUserID(request) + ",', l.xbmj_ids) or  l.zbmj_id="
                    + ControllerTool.getUserID(request) + " or o.register_user_id = " + ControllerTool.getUserID(request) +" or o.police_id = " + ControllerTool.getUserID(request) + ")");
        } else if (RoleEntity.DATA_AUTH_SELFAREA == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 办案场所-本办案场所
            map.put("dataAuth", " i.warehouse_id=" + ControllerTool.getCurrentWarehouseID(request));
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 本办案场所及下级办案场所
            map.put("dataAuth", " i.warehouse_id " + sessionInfo.getCurrentAndSubWarehouseInStr());
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEAREA == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 上级办案场所及其下级办案场所
            map.put("dataAuth", " i.warehouse_id " + sessionInfo.getSuperAndSubWarehouseInStr());
        } else if (RoleEntity.DATA_AUTH_FULL == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 全部
            map.put("dataAuth", "");
        } else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 本部门
            map.put("dataAuth", "t.p_id like " + sessionInfo.getCurrentOrgPid());
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 法制人员-上级部门及其下级部门
            map.put("dataAuth", "t.p_id like  " + sessionInfo.getSuperAndSubOrgPid());
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 公安领导-本部门及下级部门
            map.put("dataAuth", "t.p_id like " + sessionInfo.getCurrentAndSubOrgPid());
        } else {
            flag = false;
            // return null
        }


        list = service.list(map);
        if (list != null && list.size() > 0) {
            for (InvolvedEntity involvedEntity : list) {
                Map<String, Object> mapParam = new HashMap<>();
                mapParam.put("involvedId", involvedEntity.getId());
                try {
                    List<InvolvedEntity> listRecord = iinvolvedService.listLastRecord(mapParam);
                    if (listRecord != null && listRecord.size() > 0) {
                        involvedEntity.setWarehouseId(listRecord.get(0).getWarehouseId());
                        involvedEntity.setLocationId(listRecord.get(0).getLocationId());
                        involvedEntity.setShelfId(listRecord.get(0).getShelfId());
                        involvedEntity.setLocation(listRecord.get(0).getLocation());
                        involvedEntity.setRecordId(listRecord.get(0).getRecordId());
                    }
                } catch (Exception ex) {
                    logger.info("查询位置异常：" + ex.getMessage());
                }

            }
        }
        count = service.count(map);
        result.put("total", count);
        result.put("rows", list);
        return result;
    }

    /**
     * 退回公安局
     *
     * @param
     * @param request
     * @return
     */
    @RequestMapping(value = "/backJianchayuan")
    @ResponseBody
    public MessageEntity backJianchayuan(@RequestBody OutputForm form, HttpServletRequest request) {
        logger.info("-----------------HandFyController.backJianchayuan-----------------");
        OutputRecordEntity output = new OutputRecordEntity();
        try {
            //操作的入库记录
            InputRecordEntity inputRecord = inputRecordService.getInputRecordById(form.getInvolvedId());

            output.setInvolvedId(inputRecord.getInvolvedId());
            output.setRegisterUserId(ControllerTool.getUserID(request));
            output.setShelfId(inputRecord.getShelfId());
            output.setLocationId(inputRecord.getLocationId());
            output.setWarehouseId(inputRecord.getWarehouseId());
            output.setLawCaseId(inputRecord.getLawCaseId());
            output.setInvolvedTypeId(inputRecord.getInvolvedTypeId());
            output.setOutputTypeId(11);//退回检察院
            output.setInterrogateAreaId(inputRecord.getInterrogateAreaId());
            output.setOrganizationId(form.getOrganizationId());//嘉祥检察院
            output.setPoliceId(form.getPoliceId());
            output.setHandlerPerson(form.getHandlerPerson());
            output.setTelephone(form.getTelephone());
            output.setDescription(form.getDescription());
            output.setCount(form.getCount());//form.getCount()
            output.setOp_pid(ControllerTool.getSessionInfo(request).getCurrentOrg().getPid());
            output.setOp_user_id(ControllerTool.getSessionInfo(request).getUser().getId());
            outputRecordService.insertOutputRecord(output);
            Map<String, Object> map = new HashMap<>();
            map.put("status", 4);//退回检察院，状态为4
            //map.put("count",form.getCount());//数量不变更
            map.put("id", form.getInvolvedId());
            iinvolvedService.updateStatusById(map);

            this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "退回检察院:" + output, ControllerTool.getUser(request).getLoginName(), true, "涉案财物");
        } catch (Exception e) {
            return new MessageEntity().addCode(1).addIsError(true).addTitle("Error").addContent("退回失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("Message").addContent("退回成功!").addCallbackData(output.getId());
    }

}
