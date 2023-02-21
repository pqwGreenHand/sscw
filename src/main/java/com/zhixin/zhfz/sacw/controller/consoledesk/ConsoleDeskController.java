package com.zhixin.zhfz.sacw.controller.consoledesk;


import com.zhixin.zhfz.common.entity.MessageEntity;
import com.zhixin.zhfz.common.entity.OrganizationEntity;
import com.zhixin.zhfz.common.entity.RoleEntity;
import com.zhixin.zhfz.common.entity.SessionInfo;
import com.zhixin.zhfz.common.services.operLog.IOperLogService;
import com.zhixin.zhfz.common.utils.ControllerTool;
import com.zhixin.zhfz.sacw.entity.ConsoleDeskEntity;
import com.zhixin.zhfz.sacw.entity.InvolvedEntity;
import com.zhixin.zhfz.sacw.entity.OutRecordPhotoEntity;
import com.zhixin.zhfz.sacw.services.consoledesk.IConsoleDeskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/zhfz/sacw/console")
public class ConsoleDeskController {

    private static final Logger logger = LoggerFactory.getLogger(ConsoleDeskController.class);

    @Autowired
    private IOperLogService operLogService;

    @Autowired
    private IConsoleDeskService service;

    /**
     * @param
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/listTotalInvolved")
    @ResponseBody
    public MessageEntity listTotalByCaseNature(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> map = new HashMap<>();
        List<ConsoleDeskEntity> list = null;
        int type = Integer.parseInt(request.getParameter("type").toString());
        int warehouseId = Integer.parseInt(request.getParameter("warehouseId").toString());

        map.put("warehouseId", warehouseId);
        map.put("type", type);

        boolean flag = true;
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 办案人员-本人
            map.put("dataAuth", "(locate('," + ControllerTool.getUserID(request) + ",', b.xbmj_ids) or  b.zbmj_id="
                    + ControllerTool.getUserID(request) + " or a.register_user_id = " + ControllerTool.getUserID(request) + ")");
        } else if (RoleEntity.DATA_AUTH_SELFAREA == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 办案场所-本办案场所
            map.put("dataAuth", " a.warehouse_id=" + ControllerTool.getCurrentWarehouseID(request));
        } else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 本部门
            map.put("dataAuth", "t.p_id like " + sessionInfo.getCurrentOrgPid());
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 本办案场所及下级办案场所
            map.put("dataAuth", " a.warehouse_id " + sessionInfo.getCurrentAndSubWarehouseInStr());
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEAREA == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 上级办案场所及其下级办案场所
            map.put("dataAuth", " a.warehouse_id " + sessionInfo.getSuperAndSubWarehouseInStr());
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

        try {
            if (flag) {
                list = service.listTotalByCaseNature(map);

            }

        } catch (Exception e) {
            logger.info("listTotalByCaseNature查询异常=" + e.getMessage());
            return new MessageEntity().addCode(1).addIsError(true).addTitle("Error").addContent("listTotalByCaseNature查询异常!");
        }

        return new MessageEntity().addCode(1).addIsError(false).addTitle("Message").addContent("listTotalByCaseNature查询成功!").addCallbackData(list);
    }

    @RequestMapping(value = "/listInvolved")
    @ResponseBody

    public Map<String, Object> listInvolvedDetail(@RequestParam Map<String, Object> param, HttpServletRequest request) throws Exception {
        List<ConsoleDeskEntity> list = new ArrayList<>();
        Map<String, Object> result = new HashMap<String, Object>();
        int count = 0;
        boolean flag = true;
        //map.put("usePage", true);
        Map<String, Object> map = ControllerTool.mapFilter(param);
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 办案人员-本人
            map.put("dataAuth", "(locate('," + ControllerTool.getUserID(request) + ",', l.xbmj_ids) or  l.zbmj_id="
                    + ControllerTool.getUserID(request) + " or a.register_user_id = " + ControllerTool.getUserID(request) + ")");
        } else if (RoleEntity.DATA_AUTH_SELFAREA == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 办案场所-本办案场所
            map.put("dataAuth", " a.warehouse_id=" + ControllerTool.getCurrentWarehouseID(request));
        } else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 本部门
            map.put("dataAuth", "cc.p_id like " + sessionInfo.getCurrentOrgPid());
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 本办案场所及下级办案场所
            map.put("dataAuth", " a.warehouse_id " + sessionInfo.getCurrentAndSubWarehouseInStr());
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEAREA == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 上级办案场所及其下级办案场所
            map.put("dataAuth", " a.warehouse_id " + sessionInfo.getSuperAndSubWarehouseInStr());
        } else if (RoleEntity.DATA_AUTH_FULL == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 全部
            map.put("dataAuth", "");
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 法制人员-上级部门及其下级部门
            map.put("dataAuth", "cc.p_id like " + sessionInfo.getSuperAndSubOrgPid());
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 公安领导-本部门及下级部门
            map.put("dataAuth", "cc.p_id like " + sessionInfo.getCurrentAndSubOrgPid());
        } else {
            flag = false;
            // return null
        }
        if (flag) {
            list = service.listInvolve(map);
            count = service.countInvolve(map);
        }
        result.put("total", count);
        result.put("rows", list);
        return result;
    }


    @RequestMapping("/listWarehouseName")
    @ResponseBody
    public MessageEntity listWarehouse(HttpServletRequest request) throws Exception {
        Map<String, Object> map = new HashMap<>();
        List<ConsoleDeskEntity> list = new ArrayList<>();
        boolean flag = true;
        try {
            SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
            if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuthForINV(request))) {
                // 办案人员-本人
                map.put("dataAuth", "  b.op_user_id = " + ControllerTool.getUserID(request) );
            } else if (RoleEntity.DATA_AUTH_SELFAREA == (ControllerTool.getRoleDataAuthForINV(request))) {
                // 办案场所-本办案场所
                map.put("dataAuth", " b.id=" + ControllerTool.getCurrentWarehouseID(request));
            } else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuthForINV(request))) {
                // 本部门
                map.put("dataAuth", "a.p_id like " + sessionInfo.getCurrentOrgPid());
            } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuthForINV(request))) {
                // 本办案场所及下级办案场所
                map.put("dataAuth", " b.id " + sessionInfo.getCurrentAndSubWarehouseInStr());
            } else if (RoleEntity.DATA_AUTH_UP_CASCADEAREA == (ControllerTool.getRoleDataAuthForINV(request))) {
                // 上级办案场所及其下级办案场所
                map.put("dataAuth", " b.id " + sessionInfo.getSuperAndSubWarehouseInStr());
            } else if (RoleEntity.DATA_AUTH_FULL == (ControllerTool.getRoleDataAuthForINV(request))) {
                // 全部
                map.put("dataAuth", "");
            } else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuthForINV(request))) {
                // 法制人员-上级部门及其下级部门
                map.put("dataAuth", "a.p_id like  " + sessionInfo.getSuperAndSubOrgPid());
            } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuthForINV(request))) {
                // 公安领导-本部门及下级部门
                map.put("dataAuth", "a.p_id like " + sessionInfo.getCurrentAndSubOrgPid());
            } else {
                flag = false;
                // return null
            }
            list = service.listWarehouse(map);
        } catch (Exception e) {

            return new MessageEntity().addCode(1).addIsError(true).addTitle("Error").addContent("list异常!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("Message").addContent("list查询成功!").addCallbackData(list);
    }

    @RequestMapping("/getImages")
    @ResponseBody
    public MessageEntity getImages(HttpServletRequest request) throws Exception {
        Map<String, Object> map = new HashMap<>();
        List<OutRecordPhotoEntity> list = new ArrayList<>();
        int invId = Integer.parseInt(request.getParameter("id").toString());
        map.put("invId", invId);
        try {
            //list = service.listArea(map);
            list = service.getImages(map);
        } catch (Exception e) {

            return new MessageEntity().addCode(1).addIsError(true).addTitle("Error").addContent("照片异常!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("Message").addContent("照片查询成功!").addCallbackData(list);
    }


}
