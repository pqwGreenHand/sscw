package com.zhixin.zhfz.bacs.controller.personalconfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhixin.zhfz.bacs.entity.PersonalConfigEntity;
import com.zhixin.zhfz.bacs.entity.PersonalConfigFusionEntity;
import com.zhixin.zhfz.bacs.form.PersonalConfigForm;
import com.zhixin.zhfz.bacs.services.personalconfig.IPersonalConfigService;
import com.zhixin.zhfz.common.common.Base64Util;
import com.zhixin.zhfz.common.entity.MessageEntity;
import com.zhixin.zhfz.common.entity.OperLogEntity;
import com.zhixin.zhfz.common.entity.RoleEntity;
import com.zhixin.zhfz.common.entity.SessionInfo;
import com.zhixin.zhfz.common.entity.UserEntity;
import com.zhixin.zhfz.common.form.IDForm;
import com.zhixin.zhfz.common.services.operLog.IOperLogService;
import com.zhixin.zhfz.common.utils.ControllerTool;

@Controller
@RequestMapping("/zhfz/bacs/personalconfig")
public class PersonalConfigController {
	
	private static final Logger logger = LoggerFactory.getLogger(PersonalConfigController.class);

	
	@Autowired
	private IOperLogService operLogService;
	@Autowired
	private IPersonalConfigService service;
	
	/**
	 * 查所有及分页及条件查询
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/listPersonalConfig")
	@ResponseBody
	public Map<String,Object> list(@RequestParam Map<String,Object> param,HttpServletRequest request,
			HttpServletResponse response)throws Exception{
		List<PersonalConfigEntity> list=new ArrayList<PersonalConfigEntity>();
		int total=0;
		Map<String, Object> map = ControllerTool.mapFilter(param);

		/*if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))){
			map.put("dataAuth", ControllerTool.getAreasInSql("pc.area_id",
					ControllerTool.getSessionInfo(request).getCurrentAndSubArea()));
			list=service.list(map);
			total=this.service.count(map);
		}*/
		SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
		if (RoleEntity.DATA_AUTH_SELFAREA == (ControllerTool.getRoleDataAuthForBacsConf(request))) {
			// 本办案场所
			map.put("dataAuth", "pc.area_id=" + ControllerTool.getCurrentAreaID(request));
		} else if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuthForBacsConf(request))) {
			// 本办案场所及下级办案场所
			map.put("dataAuth", "pc.area_id " + sessionInfo.getCurrentAndSubAreaInStr());
		} else if (RoleEntity.DATA_AUTH_UP_CASCADEAREA == (ControllerTool.getRoleDataAuthForBacsConf(request))) {
			// 上级办案场所及其下级办案场所
			map.put("dataAuth", "pc.area_id " + sessionInfo.getSuperAndSubAreaInStr());
		} else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuthForBacsConf(request))) {
			// 本部门及下级部门
			map.put("dataAuth","pc.op_pid like " + sessionInfo.getCurrentAndSubOrgPid());
		} else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuthForBacsConf(request))) {
			// 上级部门及下级部门
			map.put("dataAuth","pc.op_pid like " + sessionInfo.getSuperAndSubOrgPid());
		} else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuthForBacsConf(request))) {
			// 本部门
			map.put("dataAuth","pc.op_pid = " + sessionInfo.getCurrentOrgPid());
		} else if(RoleEntity.DATA_AUTH_FULL == (ControllerTool.getRoleDataAuthForBacsConf(request))){
			//全部
		}else if(RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuthForBacsConf(request))){
			//本人
			map.put("dataAuth","pc.op_user_id = " + ControllerTool.getUserID(request));
		}

		list = this.service.list(map);
		total = this.service.count(map);


		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", total);
		System.out.println("=================listPersonalConfig========="+total);
		result.put("rows", list);

		return result;
	}
	/**
	 * 插入办案场所个性化配置
	 * @param form
	 * @throws Exception
	 */
	@RequestMapping(value = "/addPersonalConfig")
	@ResponseBody
	public MessageEntity add(@RequestBody PersonalConfigForm form, HttpServletRequest request){
		System.out.println("++++++++add++++++UserForm=" + form);
		PersonalConfigEntity entity = new PersonalConfigEntity();
		entity.setId(form.getId());
		entity.setType(form.getType());
		entity.setAreaId(form.getAreaId());
		entity.setDesc(form.getDesc());
		entity.setIsEnable(form.getIsEnable());
		entity.setOp_Pid(ControllerTool.getSessionInfo(request).getCurrentOrg().getPid());
		entity.setOp_User_Id(ControllerTool.getSessionInfo(request).getUser().getId());
		try {
			service.insert(entity);
			this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "添加办案场所个性化配置" + entity,  ControllerTool.getRoleName(request), true,OperLogEntity.SYSTEM_BACS);
		} catch (Exception e) {
			logger.error("Error on adding user!", e);
			this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "添加办案场所个性化配置" + entity,  ControllerTool.getRoleName(request), false,OperLogEntity.SYSTEM_BACS);
			return new MessageEntity().addCode(1).addIsError(true).addTitle("Error").addContent("添加失败!");
		}
		return new MessageEntity().addCode(1).addIsError(false).addTitle("Message").addContent("添加成功!");
	}

	/**
	 * 修改个性化配置信息
	 * @param form
	 * @throws Exception
	 */
	@RequestMapping(value = "/editPersonalConfig")
	@ResponseBody
	public MessageEntity edit(@RequestBody PersonalConfigForm form, HttpServletRequest request)throws Exception{
		logger.info("++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		PersonalConfigEntity entity = new PersonalConfigEntity();
		entity.setId(form.getId());
		entity.setType(form.getType());
		entity.setAreaId(form.getAreaId());
		entity.setDesc(form.getDesc());
		entity.setIsEnable(form.getIsEnable());
		logger.debug("$$$$$$$$$$$$edit88888888+"+form+"----------------------------------------");
		try {
			service.update(entity);
			this.operLogService.insertLog(OperLogEntity.EDIT_TYPE, "修改办案场所个性化配置" + entity, ControllerTool.getRoleName(request), true,OperLogEntity.SYSTEM_BACS);
		} catch (Exception e) {
			logger.error("Error on editing user!", e);
			this.operLogService.insertLog(OperLogEntity.EDIT_TYPE, "修改办案场所个性化配置" + entity, ControllerTool.getRoleName(request), false,OperLogEntity.SYSTEM_BACS);
			return new MessageEntity().addCode(1).addIsError(true).addTitle("Error").addContent("修改失败!");
		}
		return new MessageEntity().addCode(1).addIsError(false).addTitle("Message").addContent("修改成功!");
	}

	/**
	 * 根据id删除办案场所个性化配置
	 * @param form
	 * @throws Exception
	 */
	@RequestMapping(value = "/removePersonalConfig")
	@ResponseBody
	public MessageEntity remove(@RequestBody IDForm form, HttpServletRequest request) {
		System.out.println("++++++++remove++++++id=  " + form.getId());
		try {
			service.delete(form.getIntID());
			this.operLogService.insertLog(OperLogEntity.DELETE_TYPE, "删除办案场所个性化配置" + form.getId() , ControllerTool.getRoleName(request), true,OperLogEntity.SYSTEM_BACS);
		} catch (Exception e) {
			logger.error("Error on deleting user!", e);
			this.operLogService.insertLog(OperLogEntity.DELETE_TYPE, "删除办案场所个性化配置" + form.getId() , ControllerTool.getRoleName(request), true,OperLogEntity.SYSTEM_BACS);
			return new MessageEntity().addCode(1).addIsError(true).addTitle("Error").addContent("删除失败!");
		}
		return new MessageEntity().addCode(1).addIsError(false).addTitle("Message").addContent("删除成功!");
	}

	/**
	 * 区域初始化
	 * @param form
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/initArea")
	@ResponseBody
	public MessageEntity initArea(@RequestBody PersonalConfigForm form, HttpServletRequest request)throws Exception{
		int areaId=form.getAreaId();
		try {
			service.initArea(areaId, request);
			this.operLogService.insertLog(OperLogEntity.EDIT_TYPE, "区域初始化,办案场所id：" + areaId, ControllerTool.getRoleName(request), true,OperLogEntity.SYSTEM_BACS);
		} catch (Exception e) {
			this.operLogService.insertLog(OperLogEntity.EDIT_TYPE, "区域初始化,办案场所id：" + areaId, ControllerTool.getRoleName(request), false,OperLogEntity.SYSTEM_BACS);
			return new MessageEntity().addCode(1).addIsError(true).addTitle("Error").addContent("初始化失败!");
		}
		return new MessageEntity().addCode(1).addIsError(false).addTitle("Message").addContent("初始化成功!");
	}

	/**
	 * @Author jzw
	 * @Description
	 * @Date 14:11 2019/3/6
	 * @Param [areaId, type]
	 * @return com.zhixin.zhfz.common.entity.MessageEntity
	 **/
	@RequestMapping("/getDetailByType")
	@ResponseBody
	public MessageEntity getDetailByType(Long areaId,String type){
		MessageEntity resultEntity = new MessageEntity();
		try {
			Map<String, Object> map = new HashedMap();
			map.put("type", type);
			map.put("areaId",areaId);
			List<PersonalConfigFusionEntity> list = service.getDetailByType(map);
			resultEntity.setError(false);
			resultEntity.addCallbackData(list);
		} catch (Exception e) {
			logger.info(" listStorageDetail：" + e.getMessage());
			resultEntity.setError(true);
			resultEntity.setContent(e.getMessage());
		}
		return resultEntity;
	}

	/**
	 * 安全监管
	 * @param areaId
	 * @param type
	 * @return
	 */
	@RequestMapping("/getAqjgByType")
	@ResponseBody
	public MessageEntity getAqjgByType(String type,HttpServletRequest request){
		MessageEntity resultEntity = new MessageEntity();
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("type", type); 
			map.put("areaId",ControllerTool.getCurrentAreaID(request));
			List<PersonalConfigFusionEntity> list = service.getDetailByType(map);
			PersonalConfigFusionEntity personal = list.get(0);
			UserEntity user = ControllerTool.getSessionInfo(request).getUser();
			String url = personal.getParamValue();
			url = url.replace("{userName}", user.getLoginName());
			url = url.replace("{password}", Base64Util.encode(user.getPassword()));
			url = url.replace("{userId}", user.getId().toString());
			resultEntity.setError(false);
			resultEntity.addCallbackData(url);
		} catch (Exception e) {
			logger.info(" getAqjgByType：" + e.getMessage());
			resultEntity.setError(true);
			resultEntity.setContent(e.getMessage());
		}
		return resultEntity;
	}
	
   
}
