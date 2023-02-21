package com.zhixin.zhfz.sacw.controller.combobox;


import com.zhixin.zhfz.bacs.entity.ComboboxEntity;
import com.zhixin.zhfz.common.entity.OrganizationEntity;
import com.zhixin.zhfz.common.entity.RoleEntity;
import com.zhixin.zhfz.common.entity.SessionInfo;
import com.zhixin.zhfz.common.entity.UserEntity;
import com.zhixin.zhfz.common.services.organiztion.IOrganizationService;
import com.zhixin.zhfz.common.services.user.IUserService;
import com.zhixin.zhfz.common.utils.ControllerTool;
import com.zhixin.zhfz.sacw.entity.WareHouseEntity;
import com.zhixin.zhfz.sacw.form.UserNoSearchForm;
import com.zhixin.zhfz.sacw.services.combobox.IInvComboboxService;
import com.zhixin.zhfz.sacw.services.warehouse.IWareHouseService;
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
import java.util.*;

@Controller
@RequestMapping("/zhfz/sacw/combobox")
public class InvComboboxController {

    private static final Logger logger = LoggerFactory.getLogger(InvComboboxController.class);

    @Autowired
    private IInvComboboxService comboboxService;


    @Autowired
    private IUserService userService;

    @Autowired
    private IWareHouseService wareHouseService;


    /**
     * 查询字典类型
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/listCodeCombobox")
    @ResponseBody
    public List<ComboboxEntity> listCodeCombobox(HttpServletRequest request,
                                                 HttpServletResponse response) throws Exception {

        logger.info("-----------------listCodeCombobox-----------------");
        String type = request.getParameter("type").toString();//字典类型
        Map<String, Object> map = new HashMap<>();
        map.put("type", type);
        List<ComboboxEntity> list = new ArrayList<ComboboxEntity>();
        boolean flag = true;

        list = this.comboboxService.listCodeCombobox(map);

        return list;
    }

    @RequestMapping(value = "/getWarehousId")
    @ResponseBody
    public List<ComboboxEntity> getWareHouse(HttpServletRequest request, HttpServletResponse response) throws Exception {

        Map<String, Object> map = new HashMap<>();
        String name = request.getParameter("name").toString();//仓库名字
        map.put("name", name);
        List<ComboboxEntity> list = new ArrayList<ComboboxEntity>();

        list = this.comboboxService.getWareHouse(map);

        if (list != null && list.size() > 0) {
            return list;
        } else {
            return null;
        }

    }

    @RequestMapping(value = "/getUsersInfo")
    @ResponseBody
    public UserEntity getUsersInfo(@RequestBody UserNoSearchForm form) throws Exception {
        Collection<UserEntity> users = new ArrayList<UserEntity>();
        Map<String, Object> map = new HashMap<>();
        map.put("certificate_no", form.getUserNo());
        map.put("pageStart", 0);
        map.put("rows", 10);

        users = this.userService.getUserInfo(map);

        if (users != null && users.size() > 0) {
            return users.iterator().next();
        } else {
            return null;
        }

    }

    /**
     * 通过仓库查询入库记录
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/listinrecordbywareHouseid")
    @ResponseBody
    public List<WareHouseEntity> listInRecordByWareHouseId(@RequestParam Map<String, Object> params, HttpServletRequest request,
                                                           HttpServletResponse response) throws Exception {

        logger.info("-----------------listinrecordbywareHouseid-----------------");
        List<WareHouseEntity> list = new ArrayList<WareHouseEntity>();
        boolean flag = true;
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuthForSacwConf(request))) {
            // 办案人员-本人
            params.put("dataAuth", " w.op_user_id = " + ControllerTool.getUserID(request));
        }  else if (RoleEntity.DATA_AUTH_SELFAREA == (ControllerTool.getRoleDataAuthForSacwConf(request))) {
            // 办案场所-本办案场所
            params.put("dataAuth", " w.id=" + ControllerTool.getCurrentWarehouseID(request));
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuthForSacwConf(request))) {
            // 本办案场所及下级办案场所
            params.put("dataAuth", " w.id " + sessionInfo.getCurrentAndSubWarehouseInStr());
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEAREA == (ControllerTool.getRoleDataAuthForSacwConf(request))) {
            // 上级办案场所及其下级办案场所
            params.put("dataAuth", " w.id " + sessionInfo.getSuperAndSubWarehouseInStr());
        }else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuthForSacwConf(request))) {
            // 本部门
            params.put("dataAuth", "w.op_pid like " + sessionInfo.getCurrentOrgPid());
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuthForSacwConf(request))) {
            // 法制人员-上级部门及其下级部门
            params.put("dataAuth", "w.op_pid like  " + sessionInfo.getSuperAndSubOrgPid());
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuthForSacwConf(request))) {
            // 公安领导-本部门及下级部门
            params.put("dataAuth", "w.op_pid like " + sessionInfo.getCurrentAndSubOrgPid());
        } else {
            flag = false;
            // return null
        }


        list = this.wareHouseService.findAllWareHouse(params);
        return list;
    }


    //查询货架
    @RequestMapping(value = "/invShelfCombobox")
    @ResponseBody
    public List<ComboboxEntity> getAllInvShelfCombobox(@RequestParam Map<String, Object> params, HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.info("-----------------invShelfCombobox-----------------");

        return this.comboboxService.getAllShelf(params);
    }

    /**
     * 查询保管部门
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/listorgbytype")
    @ResponseBody
    public List<ComboboxEntity> listOrgByType(@RequestParam Map<String, Object> params, HttpServletRequest request,
                                              HttpServletResponse response) throws Exception {

        String type = request.getParameter("type");
        logger.info("-----------------listOrgByType-----------------");
        boolean flag = true;
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuthForSacwConf(request))) {
            // 办案人员-本人
            params.put("dataAuth", " a.op_user_id = " + ControllerTool.getUserID(request) );
        }  else if (RoleEntity.DATA_AUTH_SELFAREA == (ControllerTool.getRoleDataAuthForSacwConf(request))) {
            // 办案场所-本办案场所
            params.put("dataAuth", " b.id=" + ControllerTool.getCurrentWarehouseID(request));
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuthForSacwConf(request))) {
            // 本办案场所及下级办案场所
            params.put("dataAuth", " b.id " + sessionInfo.getCurrentAndSubWarehouseInStr());
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEAREA == (ControllerTool.getRoleDataAuthForSacwConf(request))) {
            // 上级办案场所及其下级办案场所
            params.put("dataAuth", " b.id " + sessionInfo.getSuperAndSubWarehouseInStr());
        }else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuthForSacwConf(request))) {
            // 本部门
            params.put("dataAuth", "a.op_pid = " + sessionInfo.getCurrentOrgPid()+" or a.p_id ="+sessionInfo.getCurrentOrgPid());
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuthForSacwConf(request))) {
            // 法制人员-上级部门及其下级部门
            params.put("dataAuth", "a.op_pid like  " + sessionInfo.getSuperAndSubOrgPid()+" or a.p_id like"+sessionInfo.getSuperAndSubOrgPid());
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuthForSacwConf(request))) {
            // 公安领导-本部门及下级部门
            params.put("dataAuth", "a.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()+" or a.p_id like"+sessionInfo.getCurrentAndSubOrgPid());
        } else {
            flag = false;
            // return null
        }
        List<ComboboxEntity> list = new ArrayList<ComboboxEntity>();
        list = this.comboboxService.listOrgByType(params);

        return list;
    }

    /**
     * 查询仓库的区域
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/listlocation")
    @ResponseBody
    public List<ComboboxEntity> listLocation(@RequestParam Map<String, Object> params, HttpServletRequest request,
                                             HttpServletResponse response) throws Exception {

        logger.info("-----------------listLocation-----------------");
        List<ComboboxEntity> list = new ArrayList<ComboboxEntity>();

        list = this.comboboxService.listLocation(params);

        System.out.println(list);
        return list;
    }

    /**
     * 查询用户名
     *
     * @param form
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/searchUser")
    @ResponseBody
    public UserEntity searchUser(@RequestBody UserNoSearchForm form) throws Exception {
        UserEntity user = userService.getUserByCertificateNo(form.getUserNo());
        if (user != null) {
            return user;
        } else {

            return null;
        }
    }

    /**
     * 根据案件查询物品
     *
     * @param
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getInvolvedByCase")
    @ResponseBody
    public List<ComboboxEntity> getInvolvedByCase(Long caseId, HttpServletResponse response) throws Exception {
        logger.info("-----------------getInvolvedByCase-----------------");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("caseId", caseId);
        return this.comboboxService.getInvolvedByCase(params);
    }

    // 查询所有部门信息---w.xb
    @RequestMapping(value = "/listAllOrganizationNameCombobox")
    @ResponseBody
    public List<ComboboxEntity> listAllOrganizationNameCombobox(@RequestParam Map<String, Object> params,
                                                                HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.info("-----------------listAllOrganizationNameCombobox-----------------");
        List<ComboboxEntity> list = new ArrayList<ComboboxEntity>();

        boolean flag = true;
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 办案人员-本人
            params.put("dataAuth", "(locate('," + ControllerTool.getUserID(request) + ",', b.xbmj_ids) or  b.zbmj_id="
                    + ControllerTool.getUserID(request) + " or b.cjr = " + ControllerTool.getUserID(request) + ")");
        } else if (RoleEntity.DATA_AUTH_SELFAREA == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 办案场所-本办案场所
            params.put("dataAuth", " w.id=" + ControllerTool.getCurrentWarehouseID(request));
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 本办案场所及下级办案场所
            params.put("dataAuth", " w.id " + sessionInfo.getCurrentAndSubWarehouseInStr());
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEAREA == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 上级办案场所及其下级办案场所
            params.put("dataAuth", " w.id " + sessionInfo.getSuperAndSubWarehouseInStr());
        } else if (RoleEntity.DATA_AUTH_FULL == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 全部
            params.put("dataAuth", "");
        } else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 本部门
            params.put("dataAuth", "t.p_id like " + sessionInfo.getSuperAndSubOrgPid());
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 法制人员-上级部门及其下级部门
            params.put("dataAuth", "t.p_id like  " + sessionInfo.getCurrentAndSubOrgPid());
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 公安领导-本部门及下级部门
            params.put("dataAuth", "t.p_id like " + sessionInfo.getCurrentAndSubOrgPid());
        } else {
            flag = false;
            // return null
        }


        list = this.comboboxService.listAllOrganizationName(params);

        return list;
    }

    @RequestMapping(value = "/listInputType")
    @ResponseBody
    public List<ComboboxEntity> listInputType(@RequestParam Map<String, Object> params) throws Exception {
        logger.info("-----------------listInputType-----------------");
        List<ComboboxEntity> listInputType = this.comboboxService.listInputType();
        return listInputType;
    }

    /**
     * @param caseId
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getInvolvedByCaseId")
    @ResponseBody
    public List<ComboboxEntity> getInvolvedByCaseId(Long caseId, HttpServletResponse response) throws Exception {
        logger.info("-----------------getInvolvedByCaseId-----------------");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("caseId", caseId);
        return this.comboboxService.getInvolvedByCaseId(params);
    }


}
