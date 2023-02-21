package com.zhixin.zhfz.jzgl.controller.jzg;

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

import com.zhixin.zhfz.common.entity.OrganizationEntity;
import com.zhixin.zhfz.common.entity.RoleEntity;
import com.zhixin.zhfz.common.utils.ControllerTool;
import com.zhixin.zhfz.jzgl.entity.JzgEntity;
import com.zhixin.zhfz.jzgl.entity.JzxxEntity;
import com.zhixin.zhfz.jzgl.services.jzg.IJzgService;
import com.zhixin.zhfz.jzgl.services.jzgGmxx.IJzgGmxxService;
import com.zhixin.zhfz.jzgl.services.jzxx.IJzxxService;

/**
 * 实体类JzgGmxxMysqlController table: jzg 柜子信息
 * @author xdp
 */
@Controller
@RequestMapping("/zhfz/jzgl/jzg")
public class JzgController {

	private static Logger logger = Logger.getLogger(JzgController.class);
	
	@Autowired
	private IJzgService jzgService;
	
	@Autowired
	private IJzgGmxxService jzgGmxxService;
	
	@Autowired
	private IJzxxService jzxxService;
	 
	@RequestMapping(value = "/queryAllJzgGmxx")
	@ResponseBody
	public Map<String, Object> queryAllJzgGmxx(@RequestParam Map<String, Object> param,HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> map = ControllerTool.mapFilter(param);
		map.put("usePage", true);
		map.put("jzgId", map.get("jzg_id"));
		if(RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuthForJZ(request))) {
			//上级及下级部门
			String sql = "(jzg.op_pid like "+ControllerTool.getSessionInfo(request).getSuperAndSubOrgPid()+""
		            +" or jzg.org_id "+ControllerTool.getSessionInfo(request).getSuperAndSubOrgInStr()+")";
	        map.put("dataAuth", sql);
		}
		if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuthForJZ(request))) {
			// 公安领导-本部门及下级部门
		    String sql = "(jzg.op_pid like "+ControllerTool.getSessionInfo(request).getCurrentAndSubOrgPid()+""
			            +" or jzg.org_id "+ControllerTool.getSessionInfo(request).getCurrentAndSubOrgInStr()+")";
		    map.put("dataAuth", sql);
		}
		if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuthForJZ(request))) {
			// 办案人员-本人
			map.put("dataAuth", " (jzg.op_user_id= " + ControllerTool.getUser(request).getId() + " ) ");
		}
		if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuthForJZ(request))) {
			// 办案人员-本部门
			String sql = "(jzg.org_id= " + ControllerTool.getUser(request).getOrganizationId() + " or jzg.op_pid="
					+ ControllerTool.getSessionInfo(request).getCurrentOrgPid() + ") ";
			map.put("dataAuth", sql);
		}

		List<JzgEntity> list = jzgService.queryAllJzgGmxx(map);
		int count = jzgService.countAllJzgGmxx(map);
		result.put("total", count);
		result.put("rows", list);
		return result;
	}
	
	@RequestMapping("/listJzgGmxx")
	@ResponseBody
	public List<List<Integer>> listJzgGmxx(HttpServletRequest request) {
		String jzgId = request.getParameter("jzgId");
		logger.info("-------查询卷柜ID(" + jzgId + ")--------");
		return jzgGmxxService.listJzgGmxx(jzgId);
	}
	
	@RequestMapping("/listJzgGmxxData")
	@ResponseBody
	public List<Map<String, String>> listJzgGmxxData(HttpServletRequest request) {
		String jzgId = request.getParameter("jzgId");
		logger.info("-------查询卷柜数据 卷柜ID(" + jzgId + ")--------");
		return jzgGmxxService.listJzgGmxxData(jzgId);
	}
	
	/**
	 * 根据柜门信息得到卷宗信息系
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/jzxxListByGmxxId")
	@ResponseBody
	public Map<String, Object> jzxxListByGmxxId(@RequestParam Map<String,Object> param) throws Exception{
		logger.info("==param=="+param);
		Map<String, Object> map = ControllerTool.mapFilter(param);
		map.put("usePage", true);
		List<JzxxEntity> jzxxList = this.jzxxService.getJzxxByGmxxId(map);
		int count = this.jzxxService.countJzxxByGmxxId(map);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", count);
		result.put("rows", jzxxList);
		return result;
	}
	
	/**
	 * 查询所有柜子坐标
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
		if(RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuthForJZ(request))) {
			//上级及下级部门
			 String sql = "( op_pid like "+ControllerTool.getSessionInfo(request).getSuperAndSubOrgPid()+""
			            +" or  org_id "+ControllerTool.getSessionInfo(request).getSuperAndSubOrgInStr()+")";
		    map.put("dataAuth", sql);
		}
		if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuthForJZ(request))) {
			// 公安领导-本部门及下级部门
		    String sql = "( op_pid like "+ControllerTool.getSessionInfo(request).getCurrentAndSubOrgPid()+""
			            +" or  org_id "+ControllerTool.getSessionInfo(request).getCurrentAndSubOrgInStr()+")";
		    map.put("dataAuth", sql);
		}
		if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuthForJZ(request))) {
			// 办案人员-本人
			map.put("dataAuth", " op_user_id= " + ControllerTool.getUser(request).getId());
		}
		if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuthForJZ(request))) {
			// 办案人员-本部门
			String sql = "( org_id= " + ControllerTool.getUser(request).getOrganizationId() + " or op_pid="
					+ ControllerTool.getSessionInfo(request).getCurrentOrgPid() + ") ";
			map.put("dataAuth", sql);
		}
		List<JzgEntity> list = jzgService.queryAllGm(map);
		return list;
	}

}