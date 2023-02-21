package com.zhixin.zhfz.jzgl.controller.combobox;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
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
import com.zhixin.zhfz.common.entity.OrganizationEntity;
import com.zhixin.zhfz.common.entity.RoleEntity;
import com.zhixin.zhfz.common.entity.SessionInfo;
import com.zhixin.zhfz.common.entity.UserEntity;
import com.zhixin.zhfz.common.services.combobox.IComboboxService;
import com.zhixin.zhfz.common.services.user.IUserService;
import com.zhixin.zhfz.common.utils.ControllerTool;
import com.zhixin.zhfz.jzgl.entity.JzgEntity;
import com.zhixin.zhfz.jzgl.entity.JzgGmxxEntity;
import com.zhixin.zhfz.jzgl.entity.JzgLieEntity;
import com.zhixin.zhfz.jzgl.services.jzg.IJzgService;
import com.zhixin.zhfz.jzgl.services.jzgGmxx.IJzgGmxxService;
import com.zhixin.zhfz.jzgl.services.jzgLie.IJzgLieService;


@Controller
@RequestMapping("/zhfz/jzgl/combobox")
public class JzglComboboxController extends ComboboxController{

	private static final Logger logger =  LoggerFactory.getLogger(JzglComboboxController.class);
 
	@Autowired
	private IUserService userService;
	
	@Autowired
	private IJzgGmxxService jzgGmxxService;
	
	@Autowired
	private IJzgService jzgService;
	
	@Autowired
	private IJzgLieService jzgLieService;
	
	@Autowired
    private IComboboxService comboboxService;
	
	@RequestMapping(value = "/getUsersInfo")
    @ResponseBody
    public Map<String, Object> getUsersInfo(@RequestParam Map<String, Object> params, HttpServletRequest request) throws Exception {
		logger.info("===get all users ====");
		if(RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuthForJZ(request))) {
			//上级及下级部门
			String sql= "( u.organization_id "+ControllerTool.getSessionInfo(request).getSuperAndSubOrgInStr()
					+" or u.op_pid like "+ControllerTool.getSessionInfo(request).getSuperAndSubOrgPid()+" )";
			params.put("dataAuth", sql);
		}
		if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuthForJZ(request))) {
			// 公安领导-本部门及下级部门
			String sql= "( u.organization_id "+ControllerTool.getSessionInfo(request).getCurrentAndSubOrgInStr()
					+" or u.op_pid like "+ControllerTool.getSessionInfo(request).getCurrentAndSubOrgPid()+" )";
			params.put("dataAuth", sql);
		}
		if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuthForJZ(request))) {
			// 办案人员-本人
			String sql = " ( u.id="+ControllerTool.getUserID(request)
			+" or u.op_user_id="+ControllerTool.getUserID(request)
			+" )";
			params.put("dataAuth", sql);
		}
		if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuthForJZ(request))) {
			// 本部门
			String sql = " (u.organization_id="+ControllerTool.getCurrentOrgID(request)
			+" or u.op_pid="+ControllerTool.getSessionInfo(request).getCurrentOrgPid()+" )";
			params.put("dataAuth", sql);
		}
		
		
		Collection<UserEntity> users = new ArrayList<UserEntity>();
        int count = 0;
        Map<String, Object> map = ControllerTool.mapFilter(params);
        users = this.userService.getUserInfo(map);
        count = this.userService.count(map);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", count);
        result.put("rows", users);
        return result;
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
    @RequestMapping(value = "/listAllOrganizationNameComboboxJz")
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
    
	
	@RequestMapping(value = "/getGmByUserId")
	@ResponseBody
	public List<JzgGmxxEntity> getGmByYhId(@RequestParam Map<String,Object> param) throws Exception{
		logger.info("===get jzgGmxx param==="+param);
		List<JzgGmxxEntity> list = new ArrayList<JzgGmxxEntity>();
		if(param.get("id") != null && !param.get("id").equals("") && !param.get("id").equals("null")){
			list = this.jzgGmxxService.getGmByUserId(new Long(param.get("id").toString()));
		}
		return list;
	}
	
	/**
	 * 柜子名称
	 * @param param
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryAllGmc")
	@ResponseBody
	public List<JzgEntity> listAllGmc(@RequestParam Map<String, Object> param,HttpServletRequest request,
										   HttpServletResponse response) throws Exception {
		Map<String, Object> map = ControllerTool.mapFilter(param);
		if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuthForJZ(request))) {
			// 公安领导-本部门及下级部门
			// 公安领导-本部门及下级部门
			String sql1 ="";
			List<OrganizationEntity> currentAndSubOrg=ControllerTool.getSessionInfo(request).getCurrentAndSubOrg();
			for (OrganizationEntity organizationEntity : currentAndSubOrg) {
				sql1+=Integer.toString(organizationEntity.getId())+",";
			}
			logger.info("---==----"+sql1);
			String sql= sql1.substring(0,sql1.length()-1);
			map.put("dataAuth", "org_id " + "in("+sql+")");
		}
		if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuthForJZ(request))) {
			// 办案人员-本人
			map.put("dataAuth", "org_id= " + ControllerTool.getUser(request).getOrganizationId());
		}
		if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuthForJZ(request))) {
			// 办案人员-本部门
			map.put("dataAuth", " org_id =" + ControllerTool.getUser(request).getOrganizationId() );
		}
		List<JzgEntity> list = jzgService.queryAllGm(map);
		return list;
	}
	
	/**
	 * 卷柜列信息
	 * @param param
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryAllGmLie")
	@ResponseBody
	public List<JzgLieEntity> queryAllGmLie(@RequestParam Map<String, Object> param, @RequestParam Long id)
			throws Exception {
		logger.info("---=param=----"+param);
		List<JzgLieEntity> list = jzgLieService.queryAllGmLie(id);
		return list;
	}
	  
}
