package com.zhixin.zhfz.sacw.controller.boundalarm;

import com.zhixin.zhfz.common.entity.RoleEntity;
import com.zhixin.zhfz.common.entity.SessionInfo;
import com.zhixin.zhfz.common.services.operLog.IOperLogService;
import com.zhixin.zhfz.common.utils.ControllerTool;
import com.zhixin.zhfz.sacw.entity.BoundAlarmEntity;
import com.zhixin.zhfz.sacw.services.boundalarm.IBoundAlarmService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 涉案财物告警回放
 */
@Controller
@RequestMapping("/zhfz/sacw/boundalarm")
public class BoundAlarmController {

    private static final Logger logger = LoggerFactory.getLogger(BoundAlarmController.class);

    @Autowired
    private IOperLogService operLogService;

    @Autowired
    private IBoundAlarmService service;

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
        Map<String, Object> map = ControllerTool.mapFilter(param);
        boolean flag = true;
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 办案人员-本人
            map.put("dataAuth", "(locate('," + ControllerTool.getUserID(request) + ",', l.xbmj_ids) or  l.zbmj_id="
                    + ControllerTool.getUserID(request) + " or i.register_user_id = " + ControllerTool.getUserID(request) + ")");
        } else if (RoleEntity.DATA_AUTH_SELFAREA == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 办案场所-本办案场所
            map.put("dataAuth", " i.warehouse_id=" + ControllerTool.getCurrentWarehouseID(request));
        } else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 本部门
            map.put("dataAuth", "t.p_id like " + sessionInfo.getCurrentOrgPid());
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 本办案场所及下级办案场所
            map.put("dataAuth", " i.warehouse_id " + sessionInfo.getCurrentAndSubWarehouseInStr());
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEAREA == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 上级办案场所及其下级办案场所
            map.put("dataAuth", " i.warehouse_id " + sessionInfo.getSuperAndSubWarehouseInStr());
        } else if (RoleEntity.DATA_AUTH_FULL == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 全部
            map.put("dataAuth", "");
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
        // map.put("usePage", true);
        List<BoundAlarmEntity> list = service.list(map);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", this.service.count(map));
        result.put("rows", list);
        return result;
    }

}
