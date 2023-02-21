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
import com.zhixin.zhfz.common.entity.OrganizationEntity;
import com.zhixin.zhfz.common.entity.RoleEntity;
import com.zhixin.zhfz.common.utils.ControllerTool;
import com.zhixin.zhfz.jzgl.common.PowerCacheUtilForJZ;
import com.zhixin.zhfz.jzgl.entity.JzgEntity;
import com.zhixin.zhfz.jzgl.entity.JzgGmxxEntity;
import com.zhixin.zhfz.jzgl.entity.JzgLieEntity;
import com.zhixin.zhfz.jzgl.services.jzgConfig.IJzgConfigService;
import com.zhixin.zhfz.jzgl.services.jzgLie.IJzgLieService;

@Controller
@RequestMapping("/zhfz/jzgl/jzgConfig")
public class JzgConfigController {

	private static Logger logger = Logger.getLogger(JzgConfigController.class);
	
	@Autowired
	private IJzgConfigService jzgConfigService;
	
	@Autowired
	private IJzgLieService JzgLieService;
	
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
		if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuthForJzglConf(request))) {
			// 公安领导-本部门及下级部门
		    String sql = "(jzg.op_pid like "+ControllerTool.getSessionInfo(request).getCurrentAndSubOrgPid()+""
			            +" or jzg.org_id "+ControllerTool.getSessionInfo(request).getCurrentAndSubOrgInStr()+")";
		    map.put("dataAuth", sql);
		}
		if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuthForJzglConf(request))) {
			// 办案人员-本人
			map.put("dataAuth", " (jzg.op_user_id= " + ControllerTool.getUser(request).getId() + " ) ");
		}
		if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuthForJzglConf(request))) {
			// 办案人员-本部门
			String sql = "(jzg.org_id= " + ControllerTool.getUser(request).getOrganizationId() + " or jzg.op_pid="
					+ ControllerTool.getSessionInfo(request).getCurrentOrgPid() + ") ";
			map.put("dataAuth", sql);
		}

		List<JzgEntity> list = jzgConfigService.queryAllJzgGmxx(map);
		int count = jzgConfigService.countAllJzgGmxx(map);
		result.put("total", count);
		result.put("rows", list);
		return result;
	}
	
	@RequestMapping(value = "/queryJzgLie")
	@ResponseBody
	public Map<String, Object> listAllJzgLie(@RequestParam Map<String,Object> param) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> map = ControllerTool.mapFilter(param);
		map.put("usePage", true);
		map.put("id", map.get("id"));
		List<JzgLieEntity> list = jzgConfigService.queryAllJzgLie(map);
		System.err.println(list+"===============");
		result.put("total", list.size());
		result.put("rows", list);
		return result;
	}
	
	/**
	 * 添加
	 * 
	 * @param entity
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "/addJzgLie")
	@ResponseBody
	public MessageEntity addJzgGmxx(@RequestBody JzgLieEntity entity, HttpServletRequest request,
			HttpServletResponse response) throws ParseException {
		System.out.println("------------"+entity + "==============");
		try {
			jzgConfigService.insertJzgLie(entity);
		} catch (Exception e) {
			logger.fatal("Error on adding JzgLieEntity!", e);
			return new MessageEntity().addCode(1).addIsError(true).addTitle("警告").addContent("添加失败!");
		}
		return new MessageEntity().addCode(1).addIsError(false).addTitle("提示").addContent("添加成功!");
	}
	/**
	 * 修改
	 * 
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/editJzgLie")
	@ResponseBody
	public MessageEntity editGmxx(@RequestBody JzgLieEntity entity, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			jzgConfigService.updateJzgLie(entity);
		} catch (Exception e) {
			logger.fatal("Error on edit JzgLieEntity!", e);
			return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("修改失败!");
		}
		return new MessageEntity().addCode(1).addIsError(false).addTitle("提示").addContent("修改成功!");
	}
	
	/**
	 * 添加
	 * 
	 * @param entity
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "/addJzgGmxx")
	@ResponseBody
	public MessageEntity addJzgGmxx(@RequestBody JzgEntity entity, HttpServletRequest request,
			HttpServletResponse response) throws ParseException {
		System.err.println("----------" + entity + "==============");
		try {
			entity.setOpPid(ControllerTool.getSessionInfo(request).getCurrentOrg().getPid());
			entity.setOpUserId(ControllerTool.getSessionInfo(request).getUser().getId());
			jzgConfigService.insertGmxx(entity);
		} catch (Exception e) {
			logger.fatal("Error on adding AjxxMysqlEntity!", e);
			return new MessageEntity().addCode(1).addIsError(true).addTitle("警告").addContent("添加失败!");
		}
		return new MessageEntity().addCode(1).addIsError(false).addTitle("提示").addContent("添加成功!");
	}

	/**
	 * 修改
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/editGmxx")
	@ResponseBody
	public MessageEntity editGmxx(@RequestBody JzgEntity entity, HttpServletRequest request,
			HttpServletResponse response) {
		System.err.println(entity + "----------============");
		try {
			jzgConfigService.updateGmxx(entity);
		} catch (Exception e) {
			logger.fatal("Error on edit GmxxMysqlEntity!", e);
			return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("修改失败!");
		}
		return new MessageEntity().addCode(1).addIsError(false).addTitle("提示").addContent("修改成功!");
	}
	
	/**
	 * 一键建柜
	 * 
	 * @param entity
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "/addAllJzgGmxx")
	@ResponseBody
	public MessageEntity addAllJzgGmxx(@RequestBody JzgEntity entity, HttpServletRequest request,
			HttpServletResponse response) throws ParseException {
		// [id=null, mc=1, ms=2, ip=3, dk=4, lx=null, zglx=7, mllx=0, sl=3,
		// wz=2, bmId=1]
		System.err.println("----1123------" + entity + "==============");
		JzgLieEntity jzgLieEntity = new JzgLieEntity();
	
		JzgGmxxEntity gmxxEntity = new  JzgGmxxEntity();
		try {
			// 添加柜子信息
			entity.setOpPid(ControllerTool.getSessionInfo(request).getCurrentOrg().getPid());
			entity.setOpUserId(ControllerTool.getUserID(request));
			jzgConfigService.insertGmxx(entity);
			// 添加柜子列信息
			Integer mllx = entity.getMllx();
			Integer sl = entity.getSl();
			Integer zglx = entity.getZglx();
			Integer wz = entity.getWz();
			int  xsbh = 0;
			for (int i = 1; i <= sl; i++) {
				jzgLieEntity.setOrgId(entity.getOrgId());
				jzgLieEntity.setJzgId(entity.getId());
				jzgLieEntity.setWz(i);
				if (wz== i) {
					jzgLieEntity.setLx(zglx);
				}else{
					jzgLieEntity.setLx(5);
				}
				JzgLieService.insertAllJzgLie(jzgLieEntity);
				// 添加柜门信息
				//插入的总数
				Integer lx =  jzgLieEntity.getLx();
				gmxxEntity.setJzgId(entity.getId());
				gmxxEntity.setJzgLieId(jzgLieEntity.getId());
				gmxxEntity.setMllx(mllx);
				for (int j = 1; j <= lx; j++) {
					xsbh++;
					gmxxEntity.setXsbh(xsbh<10?"0"+xsbh:xsbh+"");
					gmxxEntity.setMlbh(PowerCacheUtilForJZ.class.newInstance().getPz(gmxxEntity.getXsbh()+"_"+gmxxEntity.getMllx()));
					jzgConfigService.insertAllJzgGmxx(gmxxEntity);
				}
				
			}						
		} catch (Exception e) {
			logger.fatal("Error on adding AjxxMysqlEntity!", e);
			return new MessageEntity().addCode(1).addIsError(true).addTitle("警告").addContent("添加失败!");
		}
		return new MessageEntity().addCode(1).addIsError(false).addTitle("提示").addContent("添加成功!");
	}

	
}