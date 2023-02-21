package com.zhixin.zhfz.glpt.controller.areaPatrol;

import com.zhixin.zhfz.bacs.entity.AreaEntity;
import com.zhixin.zhfz.bacs.entity.SerialEntity;
import com.zhixin.zhfz.common.entity.*;
import com.zhixin.zhfz.common.services.operLog.IOperLogService;
import com.zhixin.zhfz.common.utils.ControllerTool;
import com.zhixin.zhfz.glpt.entity.AlarmEntity;
import com.zhixin.zhfz.glpt.entity.KpEvaEntity;
import com.zhixin.zhfz.glpt.entity.KpPoliceEntity;
import com.zhixin.zhfz.glpt.services.areaPatrol.IAreaPatrolService;
import org.apache.commons.collections.map.HashedMap;
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
@RequestMapping("/zhfz/glpt/areaPatrol")
public class AreaPatrolController {

    @Autowired
    private IAreaPatrolService iAlarmService;

    private static final Logger logger = LoggerFactory.getLogger(AreaPatrolController.class);
    @Autowired
    private IOperLogService operLogService;
    /**
     * 违规报警查询
     *
     * @param pageMap
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/listByAlarm")
    @ResponseBody
    public Map<String, Object> listByAlarm(@RequestParam Map<String, Object> pageMap, HttpServletRequest request,
                                           HttpServletResponse response) throws Exception {
        Map<String, Object> map = ControllerTool.mapFilter(pageMap);
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuth(request))) {
            // 办案人员-本人
            map.put("dataAuth", " ( ba.police_id=" + ControllerTool.getUserID(request)
                    + " or ba.op_user_id=" + ControllerTool.getUserID(request)
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_SELFAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 办案场所-本办案场所
            map.put("dataAuth", " ba.area_id=" + ControllerTool.getCurrentAreaID(request));
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 管理员 -本办案场所及下级办案场所
            map.put("dataAuth", " ba.area_id  " + sessionInfo.getCurrentAndSubAreaInStr());
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 上级办案场所及其下级办案场所
            map.put("dataAuth", " ba.area_id  " + sessionInfo.getSuperAndSubAreaInStr());
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 公安领导-本部门及下级部门
            map.put("dataAuth"," ba.op_pid like " + sessionInfo.getCurrentAndSubOrgPid());
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 法制人员-上级部门及下级部门
            map.put("dataAuth"," ba.op_pid like " + sessionInfo.getSuperAndSubOrgPid());
        } else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuth(request))) {
            // 本部门
            map.put("dataAuth"," ba.op_pid = " + sessionInfo.getCurrentOrgPid());
        }
        List<AlarmEntity> list = null;
        int total = 0;
        list = iAlarmService.listByAlarm(map);
        total = iAlarmService.countByAlarm(map);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("rows", list);
        result.put("total", total);
        return result;
    }

    /**
     * 增加违规告警
     *
     * @param entity
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/addAlarm")
    @ResponseBody
    public MessageEntity addAlarm(@RequestBody AlarmEntity entity, HttpServletRequest request,
                                  HttpServletResponse response) throws Exception {
        try {
            iAlarmService.addAlarm(entity);
            this.operLogService.insertLog(OperLogEntity.EDIT_TYPE, "增加违规告警" + entity, ControllerTool.getLoginName(request), true, "管理平台");
        } catch (Exception e) {
            this.operLogService.insertLog(OperLogEntity.EDIT_TYPE, "增加违规告警" + entity, ControllerTool.getLoginName(request), false, "管理平台");
            return new MessageEntity().addCode(1).addIsError(false).addTitle("通知").addContent("添加失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("通知").addContent("添加成功!");
    }

    //获取所有办案中心
    @RequestMapping(value = "/listAllArea")
    @ResponseBody
    public Map<String, Object> listAllArea(@RequestParam Map<String, Object> pageMap, HttpServletRequest request,
                                           HttpServletResponse response) throws Exception {
        logger.debug("=======================EmployeeController.listAllArea=======================");
        Map<String, Object> map = ControllerTool.mapFilter(pageMap);
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuth(request))) {
            // 办案人员-本人
            map.put("dataAuth", " ( ia.op_user_id=" + ControllerTool.getUserID(request) + " or o.op_user_id="
                    + ControllerTool.getUserID(request) + " ) ");
        } else if (RoleEntity.DATA_AUTH_SELFAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 办案场所-本办案场所
            map.put("dataAuth", " ia.id=" + ControllerTool.getCurrentAreaID(request));
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 管理员 -本办案场所及下级办案场所
            map.put("dataAuth", " ia.id  " + sessionInfo.getCurrentAndSubAreaInStr());
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 上级办案场所及其下级办案场所
            map.put("dataAuth", " ia.id  " + sessionInfo.getSuperAndSubAreaInStr());
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 公安领导-本部门及下级部门
            map.put("dataAuth"," ( ia.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                        + " or o.p_id like " +  sessionInfo.getCurrentAndSubOrgPid()
                        + " ) ");
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 法制人员-上级部门及下级部门
            map.put("dataAuth"," ( ia.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
                    + " or o.p_id like " +  sessionInfo.getSuperAndSubOrgPid()
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuth(request))) {
            // 本部门
            map.put("dataAuth"," ( ia.op_pid = " + sessionInfo.getCurrentOrgPid()
                    + " or o.p_id = " +  sessionInfo.getCurrentOrgPid()
                    + " ) ");
        }
        List<AreaEntity> list=new ArrayList<AreaEntity>();
        int total=0;
        list = this.iAlarmService.listAllArea(map);
        total=this.iAlarmService.listAllAreaCount(map);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total",total);
        result.put("rows", list);
        return result;
    }


    /**
     * 修改处理意见
     *
     * @param entity
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/updateById")
    @ResponseBody
    public MessageEntity updateById(@RequestBody AlarmEntity entity, HttpServletRequest request,
                                    HttpServletResponse response) throws Exception {
        try {
            iAlarmService.uodateById(entity);
            this.operLogService.insertLog(OperLogEntity.EDIT_TYPE, "修改处理意见" + entity, ControllerTool.getLoginName(request), true, "管理平台");
        } catch (Exception e) {
            this.operLogService.insertLog(OperLogEntity.EDIT_TYPE, "修改处理意见" + entity, ControllerTool.getLoginName(request), false, "管理平台");
            return new MessageEntity().addCode(1).addIsError(false).addTitle("通知").addContent("添加失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("通知").addContent("添加成功!");
    }
    @RequestMapping(value = "/selectOrgEva")
    @ResponseBody
    public Map<String,Object> selectOrgEva(HttpServletRequest request,@RequestParam Map<String,Object> param) throws Exception {
        Map<String, Object> map = ControllerTool.mapFilter(param);
        Map<String,Object> result = new HashedMap();
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuthForEva(request))) {
            // 办案人员-本人
            map.put("dataAuth", " ( e.creater=" + ControllerTool.getUserID(request) + " or e.police_id="
                    + ControllerTool.getUserID(request) + " ) ");
        }else if(RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuthForEva(request))){
            //本部门（5）
            map.put("dataAuth"," ( e.oraganizationId = " + sessionInfo.getUser().getOrganizationId()+")");
        }else if(RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuthForEva(request))){
            //本部门及下级部门(6)
            map.put("dataAuth"," ( e.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                    + " or org.p_id like " +  sessionInfo.getCurrentAndSubOrgPid()
                    + " ) ");
        }
        List<KpEvaEntity> list =    iAlarmService.selectOrgEva(map);
        int count =  iAlarmService.selectOrgEvaCount(map);
        result.put("total",count);
        result.put("rows",list);
        return result;
    }
    @RequestMapping(value = "/selectPoliceInfo")
    @ResponseBody
    public Map<String,Object> selectPoliceInfo(@RequestParam Map<String,Object> param, HttpServletRequest request) throws Exception {
        Map<String, Object> map = ControllerTool.mapFilter(param);
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuthForEva(request))) {
            // 办案人员-本人
            map.put("dataAuth", " ( eva.creater=" + ControllerTool.getUserID(request)
                    + " or eva.police_id=" + ControllerTool.getUserID(request)
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuthForEva(request))) {
            // 公安领导-本部门及下级部门
            map.put("dataAuth","( eva.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                    + " or org.p_id like " +  sessionInfo.getCurrentAndSubOrgPid()
                    + " ) ");
        }else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuthForEva(request))) {
            // 本部门
            map.put("dataAuth"," ( e.oraganizationId = " + sessionInfo.getUser().getOrganizationId()+")");
        }
        Map<String,Object> result = new HashedMap();
        List<KpPoliceEntity> list =  iAlarmService.selectPoliceInfo(map);
        int count =  iAlarmService.selectPoliceInfoCount(map);
        result.put("total",count);
        result.put("rows",list);
        return result;
    }
    @RequestMapping(value = "/selectAllOrgPages")
    @ResponseBody
    public Map<String,Object> selectAllOrgPages(HttpServletRequest request,@RequestParam Map<String,Object> param) throws Exception {
        Map<String, Object> map = ControllerTool.mapFilter(param);
        Map<String,Object> result = new HashedMap();
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuthForEva(request))) {
            map.put("dataAuth"," ( org.id = " +" -1 ) ");
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuthForEva(request))) {
            // 本部门及下级部门
            map.put("dataAuth","( org.p_id like " + sessionInfo.getCurrentAndSubOrgPid()+ ")");
        }
        else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuthForEva(request))) {
            // 本部门
            map.put("dataAuth"," ( org.id = " + sessionInfo.getUser().getOrganizationId()+")");
        }
        List<OrganizationEntity> list =  iAlarmService.selectAllOrgPages(map);
        int count =  iAlarmService.selectAllOrgPagesCount(map);
        result.put("total",count);
        result.put("rows",list);
        return result;
    }
    //获取办案中心在区人员
    @RequestMapping(value = "/listPerson")
    @ResponseBody
    public Map<String, Object> listPerson(@RequestParam Map<String, Object> params, HttpServletRequest request) throws Exception {
        List<SerialEntity> interrogateSerials = new ArrayList<SerialEntity>();
        int count = 0;
        Map<String, Object> map = ControllerTool.mapFilter(params);
        String areaId = (String)params.get("areaId");
        //areaId为-1 的时候是初始化表格
        if (areaId != null && !areaId.equals("-1")) {
            interrogateSerials = this.iAlarmService.listPerson(map);
            count = this.iAlarmService.listPersonCount(map);
        }

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", count);
        result.put("rows", interrogateSerials);
        return result;
    }
}

