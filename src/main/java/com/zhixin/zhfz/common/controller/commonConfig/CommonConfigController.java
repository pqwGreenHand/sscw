package com.zhixin.zhfz.common.controller.commonConfig;

import com.zhixin.zhfz.common.entity.*;
import com.zhixin.zhfz.common.form.CommonConfigForm;
import com.zhixin.zhfz.common.form.IDForm;
import com.zhixin.zhfz.common.services.commonConfig.ICommonConfigService;
import com.zhixin.zhfz.common.services.operLog.IOperLogService;
import com.zhixin.zhfz.common.utils.ControllerTool;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/zhfz/common/commonConfig")
public class CommonConfigController {

	private static final Logger logger = LoggerFactory.getLogger(CommonConfigController.class);

	@Autowired
	private IOperLogService operLogService;

	@Autowired
	private ICommonConfigService commonConfigService;

	/**
	 * 查所有及分页及条件查询
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/listCommonConfig")
	@ResponseBody
	public Map<String, Object> list(@RequestParam Map<String, Object> param, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List<CommonConfigEntity> list = new ArrayList<CommonConfigEntity>();
		int total = 0;
		boolean flag = true;
		Map<String, Object> map = ControllerTool.mapFilter(param);

		/*SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);

		if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuthForXtConf(request))) {
			// 本部门及下级部门
			map.put("dataAuth","a.op_pid like " + sessionInfo.getCurrentAndSubOrgPid());
		} else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuthForXtConf(request))) {
			// 上级部门及下级部门
			map.put("dataAuth","a.op_pid like " + sessionInfo.getSuperAndSubOrgPid());
		} else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuthForXtConf(request))) {
			// 本部门
			map.put("dataAuth","a.op_pid = " + sessionInfo.getCurrentOrgPid());
		} else if(RoleEntity.DATA_AUTH_FULL == (ControllerTool.getRoleDataAuthForXtConf(request))){
			//全部
		}else if(RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuthForXtConf(request))){
			//本人
			map.put("dataAuth","a.op_user_id = " + ControllerTool.getUserID(request));
		}else{
			flag = false;
		}*/

		if (flag) {
			map.put("usePage", true);
			list = this.commonConfigService.list(map);
			total = this.commonConfigService.count(map);
		}
		Map<String, Object> result = new HashMap<String, Object>();

		result.put("total", total);
		result.put("rows", list);
		return result;
	}

	/**
	 * 新增通用配置
	 * 
	 * @param form
	 * @throws Exception
	 */
	@RequestMapping(value = "/addCommonConfig")
	@ResponseBody
	public MessageEntity add(@RequestBody CommonConfigForm form,HttpServletRequest request) {
		System.out.println("++++++++add++++++UserForm=" + form);
		CommonConfigEntity entity = new CommonConfigEntity();
		entity.setId(form.getId());
		entity.setType(form.getType());
		entity.setDesc(form.getDesc());
		entity.setIsEnable(form.getIsEnable());
		entity.setOp_Pid(ControllerTool.getSessionInfo(request).getCurrentOrg().getPid());
		entity.setOp_User_Id(ControllerTool.getSessionInfo(request).getUser().getId());
		try {
			this.commonConfigService.insert(entity);
			this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "添加通用配置" + entity, ControllerTool.getUser(request).getLoginName(), true, "系统配置");
		} catch (Exception e) {
			logger.error("Error on adding user!", e);
			this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "添加通用配置" + entity, ControllerTool.getUser(request).getLoginName(), false, "系统配置");
			return new MessageEntity().addCode(1).addIsError(true).addTitle("Error").addContent("Add failure!");
		}
		return new MessageEntity().addCode(1).addIsError(false).addTitle("Message").addContent("Add success!");
	}

	/**
	 * 根据id删除通用配置
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/removeCommonConfig")
	@ResponseBody
	public MessageEntity remove(@RequestBody IDForm form,HttpServletRequest request) {
		System.out.println("++++++++remove++++++id=  " + form.getId());
		try {
			this.commonConfigService.delete(form.getIntID());
			this.operLogService.insertLog(OperLogEntity.DELETE_TYPE, "删除通用配置" + form.getId(), ControllerTool.getUser(request).getLoginName(), true, "系统配置");
		} catch (Exception e) {
			logger.error("Error on deleting user!", e);
			this.operLogService.insertLog(OperLogEntity.DELETE_TYPE, "删除通用配置" + form.getId(), ControllerTool.getUser(request).getLoginName(), false, "系统配置");
			return new MessageEntity().addCode(1).addIsError(true).addTitle("Error").addContent("Delete failure!");
		}
		return new MessageEntity().addCode(1).addIsError(false).addTitle("Message").addContent("Delete success!");
	}

	/**
	 * 修改通用配置
	 * 
	 * @param form
	 * @throws Exception
	 */
	@RequestMapping(value = "/editCommonConfig")
	@ResponseBody
	public MessageEntity edit(@RequestBody CommonConfigForm form,HttpServletRequest request) throws Exception {
		logger.info("++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		CommonConfigEntity entity = new CommonConfigEntity();
		entity.setId(form.getId());
		entity.setType(form.getType());
		entity.setDesc(form.getDesc());
		entity.setIsEnable(form.getIsEnable());
		logger.info("$$$$$$$$$$$$edit88888888+" + form + "----------------------------------------");
		try {
			this.commonConfigService.update(entity);
			this.operLogService.insertLog(OperLogEntity.EDIT_TYPE, "修改通用配置" + entity, ControllerTool.getUser(request).getLoginName(), true, "系统配置");
		} catch (Exception e) {
			logger.error("Error on editing user!", e);
			this.operLogService.insertLog(OperLogEntity.EDIT_TYPE, "修改通用配置" + entity, ControllerTool.getUser(request).getLoginName(), false, "系统配置");
			return new MessageEntity().addCode(1).addIsError(true).addTitle("Error").addContent("Edit failure!");
		}
		return new MessageEntity().addCode(1).addIsError(false).addTitle("Message").addContent("Edit success!");
	}

}
