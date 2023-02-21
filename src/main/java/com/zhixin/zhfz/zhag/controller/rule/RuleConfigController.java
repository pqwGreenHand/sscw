package com.zhixin.zhfz.zhag.controller.rule;

import com.zhixin.zhfz.common.entity.MessageEntity;
import com.zhixin.zhfz.common.services.role.IRoleService;
import com.zhixin.zhfz.common.utils.ControllerTool;
import com.zhixin.zhfz.zhag.entity.RuleConfigEntity;
import com.zhixin.zhfz.zhag.form.RuleConfigForm;
import com.zhixin.zhfz.zhag.services.rule.IAgRuleConfigService;
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
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Controller
@RequestMapping("/zhfz/zhag/rule")
public class RuleConfigController {

	private static final Logger logger = LoggerFactory.getLogger(RuleConfigController.class);
	@Autowired
	private IAgRuleConfigService ruleConfigService;
	@Autowired
	private IRoleService roleService;
	/**
	 * 列表展示
	 * @param params
	 * @param request
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value = "/listInterfaceLog")
	@ResponseBody
	public Map<String, Object> list(@RequestParam Map<String, Object> params,HttpServletResponse response,HttpServletRequest request) {
		Map<String, Object> map = ControllerTool.mapFilter(params);
		System.err.println(map.get("ruleId"));
		map.put("usePage", true);
		System.err.println(map);
		List<RuleConfigEntity> list = ruleConfigService.listAllRuleConfig(map);
		System.err.println(list);
		int total = ruleConfigService.countConfig(map);
		System.err.println(total);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total",total);
		result.put("rows", list);
		return result;
	}
	/**
	 * 列表展示
	 * @param params
	 * @param request
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value = "/listInterfaceLogGj")
	@ResponseBody
	public Map<String, Object> listGj(@RequestParam Map<String, Object> params,HttpServletResponse response,HttpServletRequest request) {
		logger.info("-----------------OrganizationController.list-----------------");
		Map<String, Object> map = ControllerTool.mapFilter(params);
		System.err.println(map.get("ruleId"));
		map.put("usePage", true);
		List<RuleConfigEntity> list = ruleConfigService.listAllRuleConfigGj(map);
		int total = ruleConfigService.countConfigGj(map);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total",total);
		result.put("rows", list);
		return result;
	}
	
	@RequestMapping(value = "/addRule")
	@ResponseBody
	public MessageEntity add(@RequestBody RuleConfigForm form, HttpServletRequest request,
							 HttpServletResponse response) throws Exception {
		logger.info("++++++++add++++++OrganizationForm=" + form.toString());
		System.err.println(form.toString());
		System.err.println("---=---"+form.getSendRoleids());
		RuleConfigEntity entity = new RuleConfigEntity();
		entity.setId(form.getId());
		entity.setRuleConfigType(1);
		entity.setSendDay(form.getSendDay());
		entity.setRuleId(form.getRuleId());
		entity.setSendContentTemplate(form.getSendContentTemplate());
		entity.setSendRoleids(form.getSendRoleids());
		entity.setSendRoleNames(form.getSendRoleNames().substring(1, form.getSendRoleNames().length()));
		entity.setSendMode(form.getSendMode());
		try {
			ruleConfigService.insertRuleConfig(entity);
		} catch (Exception e) {
			logger.error("Error on add ruleConfig!", e);
			return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("新增失败!");
		}
		return new MessageEntity().addCode(1).addIsError(false).addTitle("提醒").addContent("新增成功!");
	}
	@RequestMapping(value = "/removeYj")
	@ResponseBody
	public MessageEntity remove(@RequestBody RuleConfigForm form, HttpServletRequest request) {
		logger.debug("++++++++remove++++++id=" + form.getId());
		try {
			ruleConfigService.deleteRuleConfig(form.getId());
		} catch (Exception e) {
			logger.error("Error on deleting deleteRuleConfig!", e);
			return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("删除失败!");
		}
		return new MessageEntity().addCode(1).addIsError(false).addTitle("提醒").addContent("删除成功!");
	}	
	@RequestMapping(value = "/editRule")
	@ResponseBody
	public MessageEntity editRule(@RequestBody RuleConfigForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("++++++++add++++++OrganizationForm=" + form.toString());
		System.err.println(form.toString());
		RuleConfigEntity entity = new RuleConfigEntity();
		entity.setId(form.getId());
		entity.setRuleConfigType(1);
		entity.setSendDay(form.getSendDay());
		entity.setRuleId(form.getRuleId());
		entity.setSendContentTemplate(form.getSendContentTemplate());
		entity.setSendRoleids(form.getSendRoleids());
		entity.setSendRoleNames(form.getSendRoleNames().substring(1, form.getSendRoleNames().length()));
		entity.setSendMode(form.getSendMode());
		try {
			ruleConfigService.updateRuleConfig(entity);
		} catch (Exception e) {
			logger.error("Error on add ruleConfig!", e);
			return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("修改失败!");
		}
		return new MessageEntity().addCode(1).addIsError(false).addTitle("提醒").addContent("修改成功!");
	}
	
	@RequestMapping(value = "/addRuleCq")
	@ResponseBody
	public MessageEntity addRuleCq(@RequestBody RuleConfigForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("++++++++add++++++OrganizationForm=" + form.toString());
		System.err.println(form.toString());
		RuleConfigEntity entity = new RuleConfigEntity();
		entity.setId(form.getId());
		entity.setRuleConfigType(2);
		entity.setSendDay(form.getSendDay());
		entity.setRuleId(form.getRuleId());
		entity.setSendContentTemplate(form.getSendContentTemplate());
		entity.setSendRoleids(form.getSendRoleids());
		entity.setSendRoleNames(form.getSendRoleNames().substring(1, form.getSendRoleNames().length()));
		entity.setSendMode(form.getSendMode());
		try {
			ruleConfigService.insertRuleConfig(entity);
		} catch (Exception e) {
			logger.error("Error on add ruleConfig!", e);
			return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("新增失败!");
		}
		return new MessageEntity().addCode(1).addIsError(false).addTitle("提醒").addContent("新增成功!");
	}
	
	@RequestMapping(value = "/removeCq")
	@ResponseBody
	public MessageEntity removeCq(@RequestBody RuleConfigForm form, HttpServletRequest request) {
		logger.debug("++++++++remove++++++id=" + form.getId());
		try {
			ruleConfigService.deleteRuleConfig(form.getId());
		} catch (Exception e) {
			logger.error("Error on deleting deleteRuleConfig!", e);
			return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("删除失败!");
		}
		return new MessageEntity().addCode(1).addIsError(false).addTitle("提醒").addContent("删除成功!");
	}
	
	@RequestMapping(value = "/editRuleCq")
	@ResponseBody
	public MessageEntity editRuleCq(@RequestBody RuleConfigForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("++++++++add++++++OrganizationForm=" + form.toString());
		System.err.println(form.toString());
		RuleConfigEntity entity = new RuleConfigEntity();
		entity.setId(form.getId());
		entity.setRuleConfigType(2);
		entity.setSendDay(form.getSendDay());
		entity.setRuleId(form.getRuleId());
		entity.setSendContentTemplate(form.getSendContentTemplate());
		entity.setSendRoleids(form.getSendRoleids());
		entity.setSendRoleNames(form.getSendRoleNames().substring(1, form.getSendRoleNames().length()));
		entity.setSendMode(form.getSendMode());
		try {
			ruleConfigService.updateRuleConfig(entity);
		} catch (Exception e) {
			logger.error("Error on add ruleConfig!", e);
			return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("修改失败!");
		}
		return new MessageEntity().addCode(1).addIsError(false).addTitle("提醒").addContent("修改成功!");
	}
	
}
