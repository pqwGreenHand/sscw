package com.zhixin.zhfz.sacw.controller.cwalarm;

import com.zhixin.zhfz.common.entity.MessageEntity;
import com.zhixin.zhfz.common.entity.OperLogEntity;
import com.zhixin.zhfz.common.entity.RoleEntity;
import com.zhixin.zhfz.common.entity.SessionInfo;
import com.zhixin.zhfz.common.services.operLog.IOperLogService;
import com.zhixin.zhfz.common.utils.ControllerTool;
import com.zhixin.zhfz.sacw.controller.boundalarm.BoundAlarmController;
import com.zhixin.zhfz.sacw.entity.CwAlarmEntity;
import com.zhixin.zhfz.sacw.services.cwalarm.ICwAlarmService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 涉案财物告警督查
 */
@Controller
@RequestMapping("/zhfz/sacw/cwalarm")
public class CwAlarmController {

    private static final Logger logger = LoggerFactory.getLogger(BoundAlarmController.class);

    @Autowired
    private IOperLogService operLogService;

    @Autowired
    private ICwAlarmService service;

    /**
     * 查所有及分页及条件查询
     *
     * @param param
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/alarmList")
    @ResponseBody
    public Map<String, Object> list(@RequestParam Map<String, Object> param, HttpServletRequest request,
                                    HttpServletResponse response) throws Exception {
        Map<String, Object> map = ControllerTool.mapFilter(param);
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 办案人员-本人
            map.put("dataAuth", " ( a.op_user_id=" + ControllerTool.getUserID(request)
                    + " or w.op_user_id=" + ControllerTool.getUserID(request)
                    + " or l.op_user_id=" + ControllerTool.getUserID(request)
                    + " or la.op_user_id=" + ControllerTool.getUserID(request)
                    + " or cc.cjr=" + ControllerTool.getUserID(request)
                    + " or cc.zbmj_id=" + ControllerTool.getUserID(request)
                    + " or locate('," + ControllerTool.getUserID(request) + ",',cc.xbmj_ids)"
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 公安领导-本部门及下级部门
            map.put("dataAuth", "  ( a.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                    + " or w.org_id like " + sessionInfo.getCurrentAndSubOrgPid()
                    + " or w.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                    + " or l.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                    + " or la.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                    + " or cc.zbmj_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                    + " or cc.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                    + ")");
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 法制人员-上级部门及下级部门
            map.put("dataAuth", "  ( a.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
                    + " or w.org_id like " + sessionInfo.getSuperAndSubOrgPid()
                    + " or w.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
                    + " or l.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
                    + " or la.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
                    + " or cc.zbmj_pid like " + sessionInfo.getSuperAndSubOrgPid()
                    + " or cc.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
                    + ")");
        } else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 本部门
            map.put("dataAuth", "  ( a.op_pid = " + sessionInfo.getCurrentOrgPid()
                    + " or w.org_id = " + sessionInfo.getCurrentOrgPid()
                    + " or w.op_pid = " + sessionInfo.getCurrentOrgPid()
                    + " or l.op_pid = " + sessionInfo.getCurrentOrgPid()
                    + " or la.op_pid = " + sessionInfo.getCurrentOrgPid()
                    + " or cc.zbmj_pid = " + sessionInfo.getCurrentOrgPid()
                    + " or cc.op_pid = " + sessionInfo.getCurrentOrgPid()
                    + ")");
        }
        /*boolean flag = true;

		if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuthForINV(request))) {
			// 办案人员-本人
			map.put("dataAuth", "locate('," + ControllerTool.getUserID(request) + ",', cc.xbmj_ids) or  (cc.zbmj_id="
                    + ControllerTool.getUserID(request)+"or ic.register_user_id = "+ControllerTool.getUserID(request)+")");
		}else if (RoleEntity.DATA_AUTH_SELFAREA == (ControllerTool.getRoleDataAuthForINV(request))) {
			// 办案场所-本办案场所
			map.put("dataAuth", " ic.warehouser_id=" + ControllerTool.getCurrentAreaID(request));

		}else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuthForINV(request))) {
			// 所有
            map.put("dataAuth","");
		} else {
			flag = false;
			// return null
		}*/
        // map.put("usePage", true);
        List<CwAlarmEntity> list = service.list(map);

        Map<String, Object> result = new HashMap<String, Object>();

        result.put("total", this.service.count(map));
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
    @RequestMapping(value = "/updateAlarmById")
    @ResponseBody
    public MessageEntity updateById(@RequestBody CwAlarmEntity entity, HttpServletRequest request,
                                    HttpServletResponse response) throws Exception {
        try {
            service.updateAlarmById(entity);
            this.operLogService.insertLog(OperLogEntity.EDIT_TYPE, "修改处理意见" + entity, ControllerTool.getLoginName(request), true, "管理平台");
        } catch (Exception e) {
            this.operLogService.insertLog(OperLogEntity.EDIT_TYPE, "修改处理意见" + entity, ControllerTool.getLoginName(request), false, "管理平台");
            return new MessageEntity().addCode(1).addIsError(false).addTitle("通知").addContent("添加失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("通知").addContent("添加成功!");
    }
}
