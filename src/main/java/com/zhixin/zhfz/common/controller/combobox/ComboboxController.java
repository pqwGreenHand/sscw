package com.zhixin.zhfz.common.controller.combobox;

import com.zhixin.zhfz.bacs.services.order.IOrderRequestService;
import com.zhixin.zhfz.bacs.services.person.IPersonService;
import com.zhixin.zhfz.common.entity.ComboboxEntity;
import com.zhixin.zhfz.common.entity.RoleEntity;
import com.zhixin.zhfz.common.entity.SessionInfo;
import com.zhixin.zhfz.common.services.combobox.IComboboxService;
import com.zhixin.zhfz.common.utils.ControllerTool;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 公共下拉框
 */
@Controller
@RequestMapping("/zhfz/common/combobox")
public class ComboboxController {

    private static final Logger logger = LoggerFactory.getLogger(ComboboxController.class);

    @Autowired
    private IComboboxService comboboxService;
    @Autowired
    private IOrderRequestService personService;

    /**
     * 查询所有的无名氏
     */
    @RequestMapping(value = "/listWMSCount")
    @ResponseBody
    public int listWMSCount(@RequestParam Map<String, Object> map, HttpServletRequest request) throws Exception {
        int  countwms =	personService.countWuMs();
        return countwms;
    }

    /**
     * 字典下拉列表
     *
     * @param params
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/listCode")
    @ResponseBody
    public List<ComboboxEntity> getCodeCombobox(@RequestParam Map<String, Object> params,
                                                HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.info("-----------------listCode-----------------");
        return this.comboboxService.listCode(params);
    }

    /**
     * 查询证件类型
     *
     * @param params
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/certificateTypes")
    @ResponseBody
    public List<ComboboxEntity> getCertificateTypeCombobox(@RequestParam Map<String, Object> params,
                                                           HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.debug("-----------------certificateTypes-----------------");
        return this.comboboxService.listCertificateType(params);
    }

    /**
     * 查询所有部门信息
     *
     * @param params
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/listAllOrganizationNameCombobox")
    @ResponseBody
    public List<ComboboxEntity> listAllOrganizationNameCombobox(@RequestParam Map<String, Object> params,
                                                                HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.info("-----------------listAllOrganizationNameCombobox-----------------");
        List<ComboboxEntity> list = new ArrayList<ComboboxEntity>();
        boolean flag = true;
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuthForXtConf(request))) {
            // 办案人员-本人
            params.put("dataAuth", " ( u.id=" + ControllerTool.getUserID(request)
                    + " or u.op_user_id=" + ControllerTool.getUserID(request)
                    + " or o.op_user_id=" + ControllerTool.getUserID(request)
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuthForXtConf(request))) {
            // 公安领导-本部门及下级部门
            params.put("dataAuth", " ( o.p_id like " + sessionInfo.getCurrentAndSubOrgPid()
                    + " or o.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                    + " or u.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuthForXtConf(request))) {
            // 法制人员-上级部门及下级部门
            params.put("dataAuth", " ( o.p_id like " + sessionInfo.getSuperAndSubOrgPid()
                    + " or o.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
                    + " or u.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuthForXtConf(request))) {
            // 本部门
            params.put("dataAuth", " ( o.p_id = " + sessionInfo.getCurrentOrgPid()
                    + " or o.op_pid = " + sessionInfo.getCurrentOrgPid()
                    + " or u.op_pid = " + sessionInfo.getCurrentOrgPid()
                    + " ) ");
        }
        try {
            if (flag) {
                list = this.comboboxService.listAllOrganizationName(params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 查询外单位部门信息
     *
     * @param params
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/listOutOrganizationNameCombobox")
    @ResponseBody
    public List<ComboboxEntity> listOutOrganizationNameCombobox(@RequestParam Map<String, Object> params,
                                                                HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.info("-----------------listOutOrganizationNameCombobox-----------------");
        List<ComboboxEntity> list = new ArrayList<ComboboxEntity>();
        boolean flag = true;
        try {
            if (flag) {
                list = this.comboboxService.listOutOrganizationName(params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 查询所有角色
     *
     * @param params
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getRoles")
    @ResponseBody
    public List<ComboboxEntity> getRoles(@RequestParam Map<String, Object> params,
                                         HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.debug("-----------------getCertificateTypeCombobox-----------------");
        return this.comboboxService.getRoles(params);
    }

    /**
     * 查询所有办案中心
     * @return
     */
    @RequestMapping("/listArea")
    @ResponseBody
    public List<ComboboxEntity> listArea(HttpServletRequest request) {
        Map<String,Object> params = new HashMap<String,Object>();
        return comboboxService.listArea(params);
    }

    /**
     * 查询所有涉案中心
     * @return
     */
    @RequestMapping("/listWare")
    @ResponseBody
    public List<ComboboxEntity> listWare(HttpServletRequest request) {
        Map<String,Object> params = new HashMap<String,Object>();
        return comboboxService.listWare(params);
    }
}
