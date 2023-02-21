package com.zhixin.zhfz.sacw.controller.combogrid;


import com.zhixin.zhfz.bacs.entity.CombogridEntity;
import com.zhixin.zhfz.common.entity.RoleEntity;
import com.zhixin.zhfz.common.entity.SessionInfo;
import com.zhixin.zhfz.common.utils.ControllerTool;
import com.zhixin.zhfz.sacw.entity.InvolvedEntity;
import com.zhixin.zhfz.sacw.entity.LocationEntity;
import com.zhixin.zhfz.sacw.entity.ShelfEntity;
import com.zhixin.zhfz.sacw.entity.WareHouseEntity;
import com.zhixin.zhfz.sacw.services.combogrid.IInvCombogridService;

import com.zhixin.zhfz.sacw.services.involved.IinvolvedService;
import com.zhixin.zhfz.sacw.services.location.ILocationService;
import com.zhixin.zhfz.sacw.services.shelf.IShelfService;
import com.zhixin.zhfz.sacw.services.warehouse.IWareHouseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/zhfz/sacw/combogrid")
public class InvCombogridController {

    private static final Logger logger = LoggerFactory.getLogger(InvCombogridController.class);

    @Autowired
    private IInvCombogridService combogridService;

    @Autowired
    private IinvolvedService involvedService;

    @Autowired
    private IWareHouseService wareHouseService;

    @Autowired
    private ILocationService locationService;

    @Autowired
    private IShelfService shelfService;


    // 获取案件信息
    @RequestMapping(value = "/getLawCase")
    @ResponseBody
    public List<CombogridEntity> getLawCase(@RequestParam Map<String, Object> params, HttpServletRequest request,
                                            HttpServletResponse response) throws Exception {

        Map<String, Object> map = ControllerTool.mapFilter(params);
        logger.info("-----------------getLawCase-----------------");
        List<CombogridEntity> list = new ArrayList<CombogridEntity>();
        boolean flag = true;
       /* SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 办案人员-本人
            map.put("dataAuth", "locate('," + ControllerTool.getUserID(request) + ",', l.xbmj_ids) or  (l.zbmj_id="
                    + ControllerTool.getUserID(request) + " or i.register_user_id = " + ControllerTool.getUserID(request) + ")");
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
            map.put("dataAuth", "t.p_id like " + sessionInfo.getSuperAndSubOrgPid());
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 法制人员-上级部门及其下级部门
            map.put("dataAuth", "t.p_id like  " + sessionInfo.getCurrentAndSubOrgPid());
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 公安领导-本部门及下级部门
            map.put("dataAuth", "t.p_id like " + sessionInfo.getCurrentAndSubOrgPid());
        } else {
            flag = false;
            // return null
        }*/
        list = combogridService.getLawCase(map);

        return list;
    }

    // 获取有保管物品的案件信息
    @RequestMapping(value = "/getLawCaseForInv")
    @ResponseBody
    public List<CombogridEntity> getLawCaseForInv(@RequestParam Map<String, Object> params, HttpServletRequest request,
                                                  HttpServletResponse response) throws Exception {
        logger.info("-----------------getLawCaseForInv-----------------");
        List<CombogridEntity> list = new ArrayList<CombogridEntity>();
        Map<String, Object> map = ControllerTool.mapFilter(params);
        boolean flag = true;
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 办案人员-本人
            map.put("dataAuth", "(locate('," + ControllerTool.getUserID(request) + ",', b.xbmj_ids) or  b.zbmj_id="
                    + ControllerTool.getUserID(request) + " or s.register_user_id = " + ControllerTool.getUserID(request) + ")");
        } else if (RoleEntity.DATA_AUTH_SELFAREA == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 办案场所-本办案场所
            map.put("dataAuth", " s.warehouse_id=" + ControllerTool.getCurrentWarehouseID(request));
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 本办案场所及下级办案场所
            map.put("dataAuth", " s.warehouse_id " + sessionInfo.getCurrentAndSubWarehouseInStr());
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEAREA == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 上级办案场所及其下级办案场所
            map.put("dataAuth", " s.warehouse_id " + sessionInfo.getSuperAndSubWarehouseInStr());
        } else if (RoleEntity.DATA_AUTH_FULL == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 全部
            map.put("dataAuth", "");
        } else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 本部门
            map.put("dataAuth", " ( t.p_id like " + sessionInfo.getSuperAndSubOrgPid()
                    + " or s.op_pid like " + sessionInfo.getSuperAndSubOrgPid()  +
                    " or b.op_pid like " + sessionInfo.getSuperAndSubOrgPid() +")"
            );
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 法制人员-上级部门及其下级部门
            map.put("dataAuth", " (t.p_id like  " + sessionInfo.getCurrentAndSubOrgPid()
                    + " or s.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()  +
                    " or b.op_pid like " + sessionInfo.getCurrentAndSubOrgPid() + ")"
            );
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 公安领导-本部门及下级部门
            map.put("dataAuth", " (t.p_id like " + sessionInfo.getCurrentAndSubOrgPid()

                    + " or s.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()  +
                    " or b.op_pid like " + sessionInfo.getCurrentAndSubOrgPid() + ")"
            );
        } else {
            flag = false;
            // return null
        }
        list = combogridService.getLawCaseForInv(map);
        return list;
    }

    // 获取物品信息
    @RequestMapping(value = "/searchWoods")
    @ResponseBody
    public List<InvolvedEntity> searchWoods(@RequestParam Map<String, Object> params, HttpServletRequest request,
                                            HttpServletResponse response) throws Exception {
        logger.info("-----------------searchWoods-----------------");
        String id = params.get("id").toString();
        List<InvolvedEntity> list = new ArrayList<InvolvedEntity>();
        if (id != null && !"".equals(id)) {
            int caseId = Integer.parseInt(id);
            if (caseId != -1 && caseId != 0) {
                list = involvedService.getUnStockedRecordByCaseId(caseId);
            }
        }
        return list;
    }


    // 获取仓库信息
    @RequestMapping(value = "/listAllWareHouse")
    @ResponseBody
    public List<WareHouseEntity> getCangku(@RequestParam Map<String, Object> params, HttpServletRequest request,
                                           HttpServletResponse response) throws Exception {
        logger.info("-----------------listAllWareHouse-----------------");
        List<WareHouseEntity> list = new ArrayList<WareHouseEntity>();
        boolean flag = true;
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 办案人员-本人
            params.put("dataAuth", " w.op_user_id = " + ControllerTool.getUserID(request) + "");
        } else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 本部门
            params.put("dataAuth", "o.p_id like " + sessionInfo.getCurrentOrgPid());
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 法制人员-上级部门及其下级部门
            params.put("dataAuth", "o.p_id like  " + sessionInfo.getSuperAndSubOrgPid());
        } else if (RoleEntity.DATA_AUTH_FULL == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 全部
            params.put("dataAuth", "" );
        }else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 公安领导-本部门及下级部门
            params.put("dataAuth", "o.p_id like " + sessionInfo.getCurrentAndSubOrgPid());
        } else {
            flag = false;
            // return null
        }

        if (flag) {

            list = wareHouseService.findAllWareHouse(params);

        }
        return list;
    }


    // 获取区域信息
    @RequestMapping(value = "/searchLocation")
    @ResponseBody
    public List<LocationEntity> searchLocation(@RequestParam Map<String, Object> params, HttpServletRequest request,
                                               HttpServletResponse response) throws Exception {
        logger.info("-----------------searchWoods-----------------");
        String wareHouseId = params.get("id").toString();
        List<LocationEntity> list = new ArrayList<LocationEntity>();
        boolean flag = true;


        if (flag) {
            if (wareHouseId != null && !"".equals(wareHouseId)) {
                int wareHouseIdInt = Integer.parseInt(wareHouseId);
                if (wareHouseIdInt != -1 && wareHouseIdInt != 0) {
                    params.put("wareHouseId", wareHouseIdInt);
                    list = locationService.listLocationByWarehouse(params);
                }
            }
        }
        return list;
    }


    // 获取区域信息
    @RequestMapping(value = "/findLocation")
    @ResponseBody
    public List<LocationEntity> findLocation(@RequestParam Map<String, Object> params, HttpServletRequest request,
                                             HttpServletResponse response) throws Exception {
        logger.info("-----------------findLocation-----------------");
        List<LocationEntity> list = new ArrayList<LocationEntity>();
        list = locationService.findLocationByWarehouse(params);
        return list;
    }


    // 获取货架信息
    @RequestMapping(value = "/searchShelf")
    @ResponseBody
    public List<ShelfEntity> searchShelf(@RequestParam Map<String, Object> params, HttpServletRequest request,
                                         HttpServletResponse response) throws Exception {
        logger.info("-----------------searchShelf-----------------");
        String locationId = params.get("id").toString();
        List<ShelfEntity> list = new ArrayList<ShelfEntity>();
        boolean flag = true;


        if (flag) {
            if (locationId != null && !"".equals(locationId)) {
                int locationIdInt = Integer.parseInt(locationId);
                if (locationIdInt != -1 && locationIdInt != 0) {
                    params.put("locationId", locationIdInt);
                    list = shelfService.listAllShelfByLocationId(params);
                }
            }
        }
        return list;
    }

    // 获取案件信息
    @RequestMapping(value = "/getLawCaseId")
    @ResponseBody
    public List<CombogridEntity> getLawCaseId(@RequestParam Map<String, Object> params, HttpServletRequest request,
                                              HttpServletResponse response) throws Exception {

        Map<String, Object> map = ControllerTool.mapFilter(params);
        logger.info("-----------------getLawCaseId-----------------");
        List<CombogridEntity> list = new ArrayList<CombogridEntity>();
        boolean flag = true;


       /* if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 办案人员-本人
            params.put("dataAuth", "locate('," + ControllerTool.getUserID(request) + ",', l.xbmj_ids) or  (l.zbmj_id="
                    + ControllerTool.getUserID(request)+"or s.register_user_id = "+ControllerTool.getUserID(request)+")");
        } else if (RoleEntity.DATA_AUTH_SELFAREA == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 办案场所-本办案场所
            map.put("dataAuth", "  s.warehouser_id =" + ControllerTool.getCurrentWarehouseID(request));
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuthForINV(request)) || RoleEntity.DATA_AUTH_UP_CASCADEAREA == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 涉案管理员
            map.put("dataAuth","b.op_pid like " + ControllerTool.getSessionInfo(request).getCurrentAndSubOrgPid());
        } else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuthForINV(request)) || RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuthForINV(request)) || RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 公安领导-本部门及下级部门
            map.put("dataAuth","b.op_pid like " + ControllerTool.getSessionInfo(request).getCurrentOrg().getPid());
        }*/

        list = combogridService.getLawCaseId(map);

        return list;
    }


}
