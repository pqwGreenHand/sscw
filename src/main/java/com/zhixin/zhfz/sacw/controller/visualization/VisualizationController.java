package com.zhixin.zhfz.sacw.controller.visualization;

import com.zhixin.zhfz.common.entity.MessageEntity;
import com.zhixin.zhfz.common.entity.RoleEntity;
import com.zhixin.zhfz.common.entity.SessionInfo;
import com.zhixin.zhfz.common.utils.ControllerTool;
import com.zhixin.zhfz.sacw.controller.consoledesk.ConsoleDeskController;
import com.zhixin.zhfz.sacw.entity.ConsoleEntity;
import com.zhixin.zhfz.sacw.services.visualization.IVisualizationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 涉嫌财务可视化
 * cxd
 */
@Controller
@RequestMapping("/zhfz/sacw/visualization")
public class VisualizationController {

    private static final Logger logger = LoggerFactory.getLogger(ConsoleDeskController.class);

    @Autowired
    private IVisualizationService service;

    /**
     * 查询按案件性质统计数量
     *
     * @param
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/listTotalByCaseNature")
    @ResponseBody
    public MessageEntity listTotalByCaseNature(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> map = new HashMap<>();
        List<ConsoleEntity> list = null;
        int type = Integer.parseInt(request.getParameter("type").toString());
        int warehouseId = Integer.parseInt(request.getParameter("warehouseId").toString());

        map.put("wareHouseId", warehouseId);
        map.put("type", type);
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 办案人员-本人
            map.put("dataAuth", " ( r.register_user_id=" + ControllerTool.getUserID(request)
                    + " or r.police_id1=" + ControllerTool.getUserID(request)
                    + " or i.register_user_id=" + ControllerTool.getUserID(request)
                    + " or ba.cjr=" + ControllerTool.getUserID(request)
                    + " or ba.zbmj_id=" + ControllerTool.getUserID(request)
                    + " or locate('," + ControllerTool.getUserID(request) + ",',ba.xbmj_ids)"
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 公安领导-本部门及下级部门
            map.put("dataAuth", " ( r.organization_id like " + sessionInfo.getCurrentAndSubOrgPid()
                    + " or r.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                    + " or i.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                    + " or ba.zbmj_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                    + " or ba.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 法制人员-上级部门及下级部门
            map.put("dataAuth", " ( r.organization_id like " + sessionInfo.getSuperAndSubOrgPid()
                    + " or r.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
                    + " or i.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
                    + " or ba.zbmj_pid like " + sessionInfo.getSuperAndSubOrgPid()
                    + " or ba.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 本部门
            map.put("dataAuth", " ( r.organization_id =" + sessionInfo.getCurrentOrgPid()
                    + " or r.op_pid = " + sessionInfo.getCurrentOrgPid()
                    + " or i.op_pid = " + sessionInfo.getCurrentOrgPid()
                    + " or ba.zbmj_pid = " + sessionInfo.getCurrentOrgPid()
                    + " or ba.op_pid = " + sessionInfo.getCurrentOrgPid()
                    + " ) ");
        }
//        boolean flag = true;
//        if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuthForINV(request))) {
//            // 办案人员-本人
//            map.put("dataAuth", " (i.zbmj_id=" + ControllerTool.getUserID(request)+ " ) ");
//        } else if (RoleEntity.DATA_AUTH_SELFAREA == (ControllerTool.getRoleDataAuthForINV(request))) {
//            // 本部门
//            map.put("dataAuth", " i.zbdw_id=" + ControllerTool.getCurrentOrgID(request));
//        }  else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuthForINV(request))) {
//            // 公安领导-本部门及下级部门
//            String sql = ControllerTool.queryUsersInSqlByOrganizations(ControllerTool.getSessionInfo(request).getCurrentAndSubOrg());
//            map.put("dataAuth"," (i.zbmj_id " + sql + " ) ");
//        }else {
//            flag = false;
//        }
        try {
            //if(flag){
            list = service.listTotalByCaseNature(map);
            // }
        } catch (Exception e) {
            logger.info("listTotalByCaseNature查询异常=" + e.getMessage());
            return new MessageEntity().addCode(1).addIsError(true).addTitle("Error").addContent("listTotalByCaseNature查询异常!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("Message").addContent("listTotalByCaseNature查询成功!").addCallbackData(list);
    }

    /**
     * 按办案单位统计数量
     *
     * @param
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/listTotalByOrganization")
    @ResponseBody
    public MessageEntity listTotalByOrganization(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> map = new HashMap<>();
        List<ConsoleEntity> list = null;
        int type = Integer.parseInt(request.getParameter("type").toString());
        int warehouseId = Integer.parseInt(request.getParameter("warehouseId").toString());

        map.put("wareHouseId", warehouseId);
        map.put("type", type);

//        map=getMapTime(type);

        /*if(areaId==0){
            String listAreaIds=ControllerTool.queryAreaIdByOrganizations(ControllerTool.getSessionInfo(request).getCurrentAndSubOrgs());
            map.put("listAreaIds",listAreaIds);
        }else{
            map.put("areaId",areaId);
        }*/

        boolean flag = true;
       /* if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 办案人员-本人
            map.put("dataAuth", " (r.register_user_id=" + ControllerTool.getUserID(request)+ " or r.police_id1="
                    + ControllerTool.getUserID(request) + " ) ");
        } else if (RoleEntity.DATA_AUTH_SELFAREA == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 办案场所-本办案场所
            map.put("dataAuth", " r.interrogate_area_id=" + ControllerTool.getCurrentAreaID(request));
        }  else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 公安领导-本部门及下级部门
            String sql = ControllerTool.queryUsersInSqlByOrganizations(ControllerTool.getSessionInfo(request).getCurrentAndSubOrgs());
            map.put("dataAuth"," (r.register_user_id " + sql + " or r.police_id1 " + sql+ " ) ");
        }else {
            flag = false;
        }*/

        try {
            if (flag) {
                list = service.listTotalByOrganization(map);
            }
        } catch (Exception e) {
            logger.info("listTotalByOrganization查询失败=" + e.getMessage());
            return new MessageEntity().addCode(1).addIsError(true).addTitle("Error").addContent("listTotalByOrganization查询失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("Message").addContent("listTotalByOrganization查询成功!").addCallbackData(list);
    }

    /**
     * 按出库方式统计数量
     *
     * @param
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/listTotalByOutputType")
    @ResponseBody
    public MessageEntity listTotalByOutputType(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> map = new HashMap<>();
        List<ConsoleEntity> list = null;
        int type = Integer.parseInt(request.getParameter("type").toString());
        int warehouseId = Integer.parseInt(request.getParameter("warehouseId").toString());

        map.put("wareHouseId", warehouseId);
        map.put("type", type);
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 办案人员-本人
            map.put("dataAuth", " ( r.register_user_id=" + ControllerTool.getUserID(request)
                    + " or r.police_id=" + ControllerTool.getUserID(request)
                    + " or i.register_user_id=" + ControllerTool.getUserID(request)
                    + " or ba.cjr=" + ControllerTool.getUserID(request)
                    + " or ba.zbmj_id=" + ControllerTool.getUserID(request)
                    + " or locate('," + ControllerTool.getUserID(request) + ",',ba.xbmj_ids)"
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 公安领导-本部门及下级部门
            map.put("dataAuth", " ( r.organization_id like " + sessionInfo.getCurrentAndSubOrgPid()
                    + " or r.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                    + " or i.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                    + " or ba.zbmj_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                    + " or ba.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 法制人员-上级部门及下级部门
            map.put("dataAuth", " ( r.organization_id like " + sessionInfo.getSuperAndSubOrgPid()
                    + " or r.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
                    + " or i.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
                    + " or ba.zbmj_pid like " + sessionInfo.getSuperAndSubOrgPid()
                    + " or ba.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 本部门
            map.put("dataAuth", " ( r.organization_id =" + sessionInfo.getCurrentOrgPid()
                    + " or r.op_pid = " + sessionInfo.getCurrentOrgPid()
                    + " or i.op_pid = " + sessionInfo.getCurrentOrgPid()
                    + " or ba.zbmj_pid = " + sessionInfo.getCurrentOrgPid()
                    + " or ba.op_pid = " + sessionInfo.getCurrentOrgPid()
                    + " ) ");
        }
//        map=getMapTime(type);
       /* if(areaId==0){
            String listAreaIds=ControllerTool.queryAreaIdByOrganizations(ControllerTool.getSessionInfo(request).getCurrentAndSubOrgs());
            map.put("listAreaIds",listAreaIds);
        }else{
            map.put("areaId",areaId);
        }*/
        //boolean flag = true;
       /* if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 办案人员-本人
            map.put("dataAuth", " (i.register_user_id=" + ControllerTool.getUserID(request)+ " or i.police_id="
                    + ControllerTool.getUserID(request) + " ) ");
        } else if (RoleEntity.DATA_AUTH_SELFAREA == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 办案场所-本办案场所
            map.put("dataAuth", " i.interrogate_area_id=" + ControllerTool.getCurrentAreaID(request));
        }  else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 公安领导-本部门及下级部门
            String sql = ControllerTool.queryUsersInSqlByOrganizations(ControllerTool.getSessionInfo(request).getCurrentAndSubOrgs());
            map.put("dataAuth"," (i.register_user_id " + sql + " or i.police_id " + sql+ " ) ");
        }else {
            flag = false;
        }*/

        try {
            //if(flag){
            list = service.listTotalByOutputType(map);
            //}
        } catch (Exception e) {
            logger.info("listTotalByOutputType查询异常=" + e.getMessage());
            return new MessageEntity().addCode(1).addIsError(true).addTitle("Error").addContent("listTotalByOutputType查询异常!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("Message").addContent("listTotalByOutputType查询成功!").addCallbackData(list);
    }

    /**
     * 按物品的状态统计数量
     *
     * @param
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/listTotalByInvolvedStatus")
    @ResponseBody
    public MessageEntity listTotalByInvolvedStatus(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> map = new HashMap<>();
        List<ConsoleEntity> list = null;
        int type = Integer.parseInt(request.getParameter("type").toString());
        int warehouseId = Integer.parseInt(request.getParameter("warehouseId").toString());

        map.put("wareHouseId", warehouseId);
        map.put("type", type);
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 办案人员-本人
            map.put("dataAuth", " ( i.register_user_id=" + ControllerTool.getUserID(request)
                    + " or ba.cjr=" + ControllerTool.getUserID(request)
                    + " or ba.zbmj_id=" + ControllerTool.getUserID(request)
                    + " or locate('," + ControllerTool.getUserID(request) + ",',ba.xbmj_ids)"
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 公安领导-本部门及下级部门
            map.put("dataAuth", " ( i.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                    + " or ba.zbmj_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                    + " or ba.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 法制人员-上级部门及下级部门
            map.put("dataAuth", " ( i.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
                    + " or ba.zbmj_pid like " + sessionInfo.getSuperAndSubOrgPid()
                    + " or ba.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 本部门
            map.put("dataAuth", " ( i.op_pid =" + sessionInfo.getCurrentOrgPid()
                    + " or ba.zbmj_pid = " + sessionInfo.getCurrentOrgPid()
                    + " or ba.op_pid = " + sessionInfo.getCurrentOrgPid()
                    + " ) ");
        }
//        map=getMapTime(type);
        /*if(areaId==0){
            String listAreaIds=ControllerTool.queryAreaIdByOrganizations(ControllerTool.getSessionInfo(request).getCurrentAndSubOrgs());
            map.put("listAreaIds",listAreaIds);
        }else{
            map.put("areaId",areaId);
        }*/
        //boolean flag = true;
       /* if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 办案人员-本人
            map.put("dataAuth", " (i.register_user_id=" + ControllerTool.getUserID(request)+" ) ");
        } else if (RoleEntity.DATA_AUTH_SELFAREA == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 办案场所-本办案场所
            map.put("dataAuth", " i.interrogate_area_id=" + ControllerTool.getCurrentAreaID(request));
        }  else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 公安领导-本部门及下级部门
            String sql = ControllerTool.queryUsersInSqlByOrganizations(ControllerTool.getSessionInfo(request).getCurrentAndSubOrgs());
            map.put("dataAuth"," (i.register_user_id " + sql +  " ) ");
        }else {
            flag = false;
        }*/

        try {
            //if(flag){
            list = service.listTotalByInvolvedStatus(map);
            //}
        } catch (Exception e) {
            logger.info("listTotalByInvolvedStatus查询息异常=" + e.getMessage());
            return new MessageEntity().addCode(1).addIsError(true).addTitle("Error").addContent("listTotalByInvolvedStatus查询异常!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("Message").addContent("listTotalByInvolvedStatus查询成功!").addCallbackData(list);
    }

    /**
     * 按物品种类统计数量
     *
     * @param
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/listTotalByInvolvedType")
    @ResponseBody
    public MessageEntity listTotalByInvolvedType(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> map = new HashMap<>();
        List<ConsoleEntity> list = null;
        int type = Integer.parseInt(request.getParameter("type").toString());
        int warehouseId = Integer.parseInt(request.getParameter("warehouseId").toString());

        map.put("wareHouseId", warehouseId);
        map.put("type", type);
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 办案人员-本人
            map.put("dataAuth", " ( i.register_user_id=" + ControllerTool.getUserID(request)
                    + " or ba.cjr=" + ControllerTool.getUserID(request)
                    + " or ba.zbmj_id=" + ControllerTool.getUserID(request)
                    + " or locate('," + ControllerTool.getUserID(request) + ",',ba.xbmj_ids)"
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 公安领导-本部门及下级部门
            map.put("dataAuth", " ( i.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                    + " or ba.zbmj_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                    + " or ba.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 法制人员-上级部门及下级部门
            map.put("dataAuth", " ( i.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
                    + " or ba.zbmj_pid like " + sessionInfo.getSuperAndSubOrgPid()
                    + " or ba.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 本部门
            map.put("dataAuth", " ( i.op_pid =" + sessionInfo.getCurrentOrgPid()
                    + " or ba.zbmj_pid = " + sessionInfo.getCurrentOrgPid()
                    + " or ba.op_pid = " + sessionInfo.getCurrentOrgPid()
                    + " ) ");
        }
//        map=getMapTime(type);
       /* if(areaId==0){
            String listAreaIds=ControllerTool.queryAreaIdByOrganizations(ControllerTool.getSessionInfo(request).getCurrentAndSubOrgs());
            map.put("listAreaIds",listAreaIds);
        }else{
            map.put("areaId",areaId);
        }*/
        //boolean flag = true;

       /* if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 办案人员-本人
            map.put("dataAuth", " (i.register_user_id=" + ControllerTool.getUserID(request)+ " ) ");
        } else if (RoleEntity.DATA_AUTH_SELFAREA == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 办案场所-本办案场所
            map.put("dataAuth", " i.interrogate_area_id=" + ControllerTool.getCurrentAreaID(request));
        }  else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 公安领导-本部门及下级部门
            String sql = ControllerTool.queryUsersInSqlByOrganizations(ControllerTool.getSessionInfo(request).getCurrentAndSubOrgs());
            map.put("dataAuth"," (i.register_user_id " + sql + " ) ");
        }else {
            flag = false;
        }*/

        try {
            //if(flag){
            list = service.listTotalByInvolvedType(map);
            //}
        } catch (Exception e) {
            logger.info("listTotalByInvolvedType查询息异常=" + e.getMessage());
            return new MessageEntity().addCode(1).addIsError(true).addTitle("Error").addContent("listTotalByInvolvedType查询异常!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("Message").addContent("listTotalByInvolvedType查询成功!").addCallbackData(list);
    }

    /**
     * 查看有涉案财物仓库
     *
     * @param
     * @return
     * @throws Exception
     */
    @RequestMapping("/listArea")
    @ResponseBody
    public MessageEntity listArea(HttpServletRequest request) throws Exception {
        Map<String, Object> map = new HashMap<>();
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 办案人员-本人
            map.put("dataAuth", " b.op_user_id=" + ControllerTool.getUserID(request));
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 公安领导-本部门及下级部门
            map.put("dataAuth", " (b.org_id like " + sessionInfo.getCurrentAndSubOrgPid()
                    + " or b.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                    + ")");
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 法制人员-上级部门及下级部门
            map.put("dataAuth", " (b.org_id like " + sessionInfo.getSuperAndSubOrgPid()
                    + " or b.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
                    + ")");
        } else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 本部门
            map.put("dataAuth", " (b.org_id =" + sessionInfo.getCurrentOrgPid()
                    + " or b.op_pid =" + sessionInfo.getCurrentOrgPid()
                    + ")");
        }
        List<ConsoleEntity> list = new ArrayList<>();
        try {
            list = service.listWarehouse(map);
        } catch (Exception e) {
            return new MessageEntity().addCode(1).addIsError(true).addTitle("Error").addContent("listArea异常!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("Message").addContent("listArea查询成功!").addCallbackData(list);
    }
}
