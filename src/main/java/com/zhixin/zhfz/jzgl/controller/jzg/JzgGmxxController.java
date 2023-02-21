package com.zhixin.zhfz.jzgl.controller.jzg;


import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhixin.zhfz.common.entity.MessageEntity;
import com.zhixin.zhfz.common.entity.OperLogEntity;
import com.zhixin.zhfz.common.entity.OrganizationEntity;
import com.zhixin.zhfz.common.entity.RoleEntity;
import com.zhixin.zhfz.common.entity.UserEntity;
import com.zhixin.zhfz.common.services.operLog.IOperLogService;
import com.zhixin.zhfz.common.services.user.IUserService;
import com.zhixin.zhfz.common.utils.ControllerTool;
import com.zhixin.zhfz.jzgl.common.PowerCacheUtilForJZ;
import com.zhixin.zhfz.jzgl.entity.JzgGmxxEntity;
import com.zhixin.zhfz.jzgl.services.jzgGmxx.IJzgGmxxService;
import com.zhixin.zhfz.jzgl.services.jzgLie.IJzgLieService;

/**
 * 实体类 table: jz_jzg_gmxx 卷宗柜柜门信息
 */
@Controller
@RequestMapping("/zhfz/jzgl/jzgGmxx")
public class JzgGmxxController {

	private static Logger logger = Logger.getLogger(JzgGmxxController.class);
	@Autowired
	private IJzgGmxxService jzgGmxxService;
	
	@Autowired
	private IJzgLieService jzgLieService;
	
	@Autowired
	private IUserService userService;
    
	@Autowired
	private IOperLogService operLogService;
	 
	@RequestMapping("/listJzgGmxx")
	@ResponseBody
	public List<List<Integer>> listJzgGmxx(HttpServletRequest request) {
		String jzgId = request.getParameter("jzgId");
		logger.info("-------查询卷柜ID(" + jzgId + ")--------");
		return jzgGmxxService.listJzgGmxx(jzgId);
	}

	/**
	 * 柜门信息
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryAllJzgGmxx")
	@ResponseBody
	public Map<String, Object> listAllJzgGmxx(@RequestParam Map<String, Object> param,HttpServletRequest request,
											  HttpServletResponse response) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> map = ControllerTool.mapFilter(param);
		map.put("usePage", true);
		map.put("orgId", map.get("bmId"));
		map.put("mc", map.get("mc"));
		logger.info("=====queryAllJzgGmxx===param==="+param);
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
			map.put("dataAuth", " org_id= " + ControllerTool.getUser(request).getOrganizationId() );
		}
		if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuthForJZ(request))) {
			// 本部门
			map.put("dataAuth", " org_id= " + ControllerTool.getUser(request).getOrganizationId());
		}

		List<JzgGmxxEntity> list = jzgGmxxService.queryAllJzgGmxxAll(map);
		int count = jzgGmxxService.countAllJzgGmxx(map);
		result.put("total", count);
		result.put("rows", list);
		return result;
	}
	
	
	/**
	 * 柜门添加
	 * 
	 * @param entity
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "/addJzgGmxx")
	@ResponseBody
	public MessageEntity addJzgGmxx(@RequestBody JzgGmxxEntity entity, @RequestParam Map<String, Object> param,
			HttpServletRequest request, HttpServletResponse response) throws ParseException {
		Map<String, Object> map = new HashMap<String, Object>();
		String mc = entity.getMc();
		String wz = entity.getWz();
		Integer pz = entity.getMllx();
		String mlbh = entity.getMlbh();
		map.put("mc", mc);
		map.put("wz", wz);
		int lx = 0;
		try {
			lx = jzgLieService.queryLieLx(map);
			for (int i = 0; i < lx; i++) {
				// //先转integer，再转string
				int bh = Integer.parseInt(mlbh) + i;
				entity.setXsbh(bh+"");
				if ((bh + "").length() == 1) {
					entity.setXsbh("0" + bh);
				} 
				entity.setMlbh(PowerCacheUtilForJZ.class.newInstance()
						.getPz(bh + "_" + pz));
				if( (bh + "").length() == 1) {
					entity.setMlbh(PowerCacheUtilForJZ.class.newInstance()
							.getPz("0" + bh + "_" + pz));
				} 
				jzgGmxxService.insertGmxxAll(entity);
				 this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "柜门分配添加" + entity, ControllerTool.getLoginName(request), true,OperLogEntity.SYSTEM_JZGL);
			}
		} catch (Exception e) {
			logger.fatal("Error on adding AjxxMysqlEntity!", e);
			return new MessageEntity().addCode(1).addIsError(true).addTitle("警告").addContent("添加失败!");
		}
		return new MessageEntity().addCode(1).addIsError(false).addTitle("提示").addContent("添加成功!");
	}
	
	@RequestMapping(value = "/searchUserByGmxxId")
	@ResponseBody
	public List<UserEntity> searchUserByGmxxId(@RequestParam Map<String, Object> params) throws Exception {
		Map<String, Object> map = ControllerTool.mapFilter(params);
		logger.info("-------------" + params + "==========");
		List<UserEntity> list =	userService.searchAllUserByGmxxId(map);
		return list;
	}

	@RequestMapping(value = "/searchUser")
	@ResponseBody
	public List<UserEntity> searchUser(@RequestParam Map<String, Object> params,HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> map = ControllerTool.mapFilter(params);
		map.put("usePage", true);
		logger.info("-------------" + params + "==========");
		/*if(RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuthForJZ(request))) {
			//上级及下级部门
			 String sql= "o.p_id like "+ControllerTool.getSessionInfo(request).getSuperAndSubOrgPid();
				map.put("dataAuth", sql);
		}
		if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuthForJZ(request))) {
			// 公安领导-本部门及下级部门
			 String sql= "o.p_id like "+ControllerTool.getSessionInfo(request).getCurrentOrgPid();
			map.put("dataAuth", sql);
		}
		if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuthForJZ(request))) {
			// 办案人员-本人
			map.put("dataAuth", " u.id= " + ControllerTool.getUserID(request));
		}
		if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuthForJZ(request))) {
			// 办案人员-本部门
			map.put("dataAuth", " o.id =" + ControllerTool.getUser(request).getOrganizationId() );
		}*/
		List<UserEntity> list =	userService.searchAllUser(map);
		return list;
	}
	
	/**
	 * 卷宗柜柜门信息修改
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/editGmxx")
	@ResponseBody
	public MessageEntity editGmxx(@RequestBody JzgGmxxEntity entity, HttpServletRequest request,
			HttpServletResponse response) {
		String xsbh = entity.getXsbh();
		String mlbh = entity.getMlbh();
		Integer pz = entity.getMllx();
		logger.info("xsbh====="+xsbh+"===mlbh"+mlbh);
		System.err.println("=====" + xsbh);
		System.err.println("=====" + mlbh);
		
		try {
			if (((Integer.parseInt(xsbh)) + "").length() == 1) {
				entity.setXsbh("0" + (Integer.parseInt(xsbh)) + "");
			} else {
				entity.setXsbh((Integer.parseInt(xsbh)) + "");
			}
			if (((Integer.parseInt(xsbh)) + "").length() == 1) {
				String mlbh1 = PowerCacheUtilForJZ.class.newInstance().getPz("0" + (Integer.parseInt(xsbh)) + "_" + pz);
				entity.setMlbh(mlbh1);
			} else {
				String mlbh1 = PowerCacheUtilForJZ.class.newInstance().getPz((Integer.parseInt(xsbh)) + "_" + pz);
				entity.setMlbh(mlbh1);
			}
			jzgGmxxService.updateGmxx(entity);
            this.operLogService.insertLog(OperLogEntity.EDIT_TYPE, "柜门分配修改" + entity, ControllerTool.getLoginName(request), true,OperLogEntity.SYSTEM_JZGL);
		} catch (Exception e) {
			logger.fatal("Error on edit GmxxMysqlEntity!", e);
			return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("编辑失败!");
		}
		return new MessageEntity().addCode(1).addIsError(false).addTitle("提示").addContent("编辑成功!");
	}
}