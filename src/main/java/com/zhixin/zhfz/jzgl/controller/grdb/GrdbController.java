package com.zhixin.zhfz.jzgl.controller.grdb;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhixin.zhfz.common.entity.RoleEntity;
import com.zhixin.zhfz.common.utils.ControllerTool;
import com.zhixin.zhfz.jzgl.entity.GrdbEntity;
import com.zhixin.zhfz.jzgl.services.grdb.IGrdbService;

/**
 * 个人待办
 * 
 * @author xdp
 */
@Controller
@RequestMapping("/zhfz/jzgl/grdb")
public class GrdbController {
	private static Logger logger = Logger.getLogger(GrdbController.class);

	@Autowired
	private IGrdbService grdbService;
	
	
	@RequestMapping(value = "/listGrdbXx")
	@ResponseBody
	public Map<String, Object> listGrdbXx(@RequestParam Map<String, Object> param, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("====个人待办=====");
		Map<String, Object> mapParam = ControllerTool.mapFilter(param);
		mapParam.put("usePage", true);
		if(RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuthForJZ(request))) {
			//上级及下级部门
			String sql = "( EXISTS (SELECT 1 FROM xt_user xu WHERE xx.yh_id=xu.id AND xu.organization_id like "+ControllerTool.getSessionInfo(request).getSuperAndSubOrgPid()+")" 
					+" or    EXISTS (SELECT 1 FROM xt_user xu WHERE xx.cjyh_id=xu.id AND xu.organization_id like "+ControllerTool.getSessionInfo(request).getSuperAndSubOrgPid()+")"
					+" or xx.op_pid like "+ControllerTool.getSessionInfo(request).getSuperAndSubOrgPid()+" )";
			mapParam.put("dataAuth", sql);
		}
		
		if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuthForJZ(request))) {
			// 公安领导-本部门及下级部门
			String sql = "( EXISTS (SELECT 1 FROM xt_user xu WHERE xx.yh_id=xu.id AND xu.organization_id like "+ControllerTool.getSessionInfo(request).getCurrentAndSubOrgPid()+")" 
					+" or    EXISTS (SELECT 1 FROM xt_user xu WHERE xx.cjyh_id=xu.id AND xu.organization_id like "+ControllerTool.getSessionInfo(request).getCurrentAndSubOrgPid()+")"
					+" or xx.op_pid like "+ControllerTool.getSessionInfo(request).getCurrentAndSubOrgPid()+" )";
			mapParam.put("dataAuth", sql);
		}
		if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuthForJZ(request))) {
			// 办案人员-本人
			String sql = " (xx.yh_id="+ControllerTool.getUserID(request)
			+" or xx.cjyh_id="+ControllerTool.getUserID(request)
			+" or xx.op_user_id="+ControllerTool.getUserID(request)+")";
			mapParam.put("dataAuth", sql);
		}
		if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuthForJZ(request))) {
			// 办案人员-本部门
			String sql = "( EXISTS (SELECT 1 FROM xt_user xu WHERE xx.yh_id=xu.id AND xu.organization_id="+ControllerTool.getUser(request).getOrganizationId()+")" 
					+" or    EXISTS (SELECT 1 FROM xt_user xu WHERE xx.cjyh_id=xu.id AND xu.organization_id="+ControllerTool.getUser(request).getOrganizationId()+")"
					+" or xx.op_pid="+ControllerTool.getSessionInfo(request).getCurrentOrgPid()+" )";
			mapParam.put("dataAuth", sql);
		}
		List<GrdbEntity> datas = this.grdbService.listGrdbXx(mapParam);
		int count = this.grdbService.countListGrdbXx(mapParam);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", count);
		result.put("rows", datas);
		return result;
	}
}