package com.zhixin.zhfz.zhag.controller.combobox;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhixin.zhfz.common.controller.combobox.ComboboxController;
import com.zhixin.zhfz.common.entity.ComboboxEntity;
import com.zhixin.zhfz.common.entity.RoleEntity;
import com.zhixin.zhfz.common.entity.SessionInfo;
import com.zhixin.zhfz.common.services.combobox.IComboboxService;
import com.zhixin.zhfz.common.utils.ControllerTool;


@Controller
@RequestMapping("/zhfz/zhag/combobox")
public class zhagComboboxController extends ComboboxController{

	private static final Logger logger =  LoggerFactory.getLogger(zhagComboboxController.class);
 
	@Autowired
    private IComboboxService comboboxService;
     
	
	/**
     * 查询所有部门信息
     *
     * @param params
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/listAllOrganizationNameComboboxAg")
    @ResponseBody
    public List<ComboboxEntity> listAllOrganizationNameCombobox(@RequestParam Map<String, Object> params,
                                                                HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.info("-----------------listAllOrganizationNameCombobox-----------------");
        List<ComboboxEntity> list = new ArrayList<ComboboxEntity>();
        boolean flag = true;
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        if(RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuthForJZ(request))) {
			//上级及下级部门
        	 params.put("dataAuth", " ( o.id " + sessionInfo.getSuperAndSubOrgInStr()
             + " or o.p_id like " + sessionInfo.getSuperAndSubOrgPid()
             + " or u.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
             + " ) ");
		}
        if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuthForJZ(request))) {
            // 办案人员-本人
            params.put("dataAuth", " ( u.id=" + ControllerTool.getUserID(request)
                    + " or u.op_user_id=" + ControllerTool.getUserID(request)
                    + " or o.op_user_id=" + ControllerTool.getUserID(request)
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuthForJZ(request))) {
            // 公安领导-本部门及下级部门
            params.put("dataAuth", " ( o.id " + sessionInfo.getCurrentAndSubOrgInStr()
                    + " or o.p_id like " + sessionInfo.getCurrentAndSubOrgPid()
                    + " or u.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuthForJZ(request))) {
            // 本部门
            params.put("dataAuth", " ( o.id = " + sessionInfo.getCurrentOrg().getId()
                    + " or o.p_id = " + sessionInfo.getCurrentOrgPid()
                    + " or u.op_pid = " + sessionInfo.getCurrentOrgPid()
                    + " ) ");
        }
        try {
            if (flag) {
                list = comboboxService.listAllOrganizationName(params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
