package com.zhixin.zhfz.common.controller.commonConfig;

import com.zhixin.zhfz.common.entity.*;
import com.zhixin.zhfz.common.form.CommonConfigDetailForm;
import com.zhixin.zhfz.common.form.IDForm;
import com.zhixin.zhfz.common.services.commonConfig.ICommonConfigDetailService;
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
public class CommonConfigDetailController {

	private static Logger logger = LoggerFactory.getLogger(CommonConfigDetailController.class);

	@Autowired
	private IOperLogService operLogService;

	@Autowired
	private ICommonConfigDetailService service;

	/**
	 * 查所有及分页及条件查询
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/listCommonConfigDetail")
	@ResponseBody
	public Map<String, Object> list(@RequestParam Map<String, Object> param, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List<CommonConfigDetailEntity> list = new ArrayList<CommonConfigDetailEntity>();
		int total = 0;
		boolean flag = true;
		Map<String, Object> map = ControllerTool.mapFilter(param);

		if (flag) {
			map.put("usePage", true);
			list = service.list(map);
			total = this.service.count(map);
		}

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", total);
		result.put("rows", list);
		return result;
	}

	/**
	 * 新增通用配置详情
	 * 
	 * @param form
	 * @throws Exception
	 */
	@RequestMapping(value = "/addCommonConfigDetail")
	@ResponseBody
	public MessageEntity add(@RequestBody CommonConfigDetailForm form,HttpServletRequest request) {
		System.out.println("++++++++add++++++UserForm=" + form + "++++++++++++++++++1111111111");
		CommonConfigDetailEntity entity = new CommonConfigDetailEntity();
		entity.setCommonConfigId(form.getCommonConfigId());
		entity.setParamKey(form.getParamKey());
		entity.setParamValue(form.getParamValue());
		entity.setDesc(form.getDesc());
		entity.setOp_Pid(ControllerTool.getSessionInfo(request).getCurrentOrgPid());
		entity.setOp_User_Id(ControllerTool.getSessionInfo(request).getUser().getId());
		try {
			service.insert(entity);
			this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "添加通用配置详情" + entity, ControllerTool.getUser(request).getLoginName(), true, "系统配置");
		} catch (Exception e) {
			logger.error("Error on adding user!", e);
			this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "添加通用配置详情" + entity, ControllerTool.getUser(request).getLoginName(), false, "系统配置");
			return new MessageEntity().addCode(1).addIsError(true).addTitle("Error").addContent("Add failure!");
		}
		return new MessageEntity().addCode(1).addIsError(false).addTitle("Message").addContent("Add success!");
	}

	/**
	 * 根据id删除通用配置详情
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/removeCommonConfigDetail")
	@ResponseBody
	public MessageEntity remove(@RequestBody IDForm form,HttpServletRequest request) {
		System.out.println("++++++++remove++++++id=  " + form.getId());
		try {
			service.delete(form.getIntID());
			this.operLogService.insertLog(OperLogEntity.DELETE_TYPE, "删除通用配置详情" + form.getId(), ControllerTool.getUser(request).getLoginName(), true, "系统配置");
		} catch (Exception e) {
			logger.error("Error on deleting user!", e);
			this.operLogService.insertLog(OperLogEntity.DELETE_TYPE, "删除通用配置详情" + form.getId(), ControllerTool.getUser(request).getLoginName(), false, "系统配置");
			return new MessageEntity().addCode(1).addIsError(true).addTitle("Error").addContent("Delete failure!");
		}
		return new MessageEntity().addCode(1).addIsError(false).addTitle("Message").addContent("Delete success!");
	}

	/**
	 * 修改证件信息
	 * 
	 * @param form
	 * @throws Exception
	 */
	@RequestMapping(value = "/editCommonConfigDetail")
	@ResponseBody
	public MessageEntity edit(@RequestBody CommonConfigDetailForm form,HttpServletRequest request) throws Exception {
		logger.info("++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		CommonConfigDetailEntity entity = new CommonConfigDetailEntity();
		entity.setId(form.getId());
		entity.setCommonConfigId(form.getCommonConfigId());
		entity.setParamKey(form.getParamKey());
		entity.setParamValue(form.getParamValue());
		entity.setDesc(form.getDesc());
		logger.info("$$$$$$$$$$$$edit88888888+" + form + "----------------------------------------");
		try {
			service.update(entity);
			this.operLogService.insertLog(OperLogEntity.EDIT_TYPE, "修改通用配置详情" + entity, ControllerTool.getUser(request).getLoginName(), true, "系统配置");
		} catch (Exception e) {
			logger.error("Error on editing user!", e);
			this.operLogService.insertLog(OperLogEntity.EDIT_TYPE, "修改通用配置详情" + entity, ControllerTool.getUser(request).getLoginName(), false, "系统配置");
			return new MessageEntity().addCode(1).addIsError(true).addTitle("Error").addContent("Edit failure!");
		}
		return new MessageEntity().addCode(1).addIsError(false).addTitle("Message").addContent("Edit success!");
	}
}
