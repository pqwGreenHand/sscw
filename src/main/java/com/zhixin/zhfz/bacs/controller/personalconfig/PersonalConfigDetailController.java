package com.zhixin.zhfz.bacs.controller.personalconfig;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zhixin.zhfz.bacs.entity.PersonalConfigDetailEntity;
import com.zhixin.zhfz.bacs.form.PersonalConfigDetailForm;
import com.zhixin.zhfz.bacs.services.personalconfig.IPersonalConfigDetailService;
import com.zhixin.zhfz.bacs.services.personalconfig.IPersonalConfigService;
import com.zhixin.zhfz.common.entity.MessageEntity;
import com.zhixin.zhfz.common.entity.OperLogEntity;
import com.zhixin.zhfz.common.form.IDForm;
import com.zhixin.zhfz.common.services.operLog.IOperLogService;
import com.zhixin.zhfz.common.utils.ControllerTool;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import net.sf.json.JSONObject;

@Controller
@RequestMapping("/zhfz/bacs/personalconfig")
public class PersonalConfigDetailController {
	
	private static Logger logger = Logger.getLogger(PersonalConfigDetailController.class);

	
	@Autowired
	private IOperLogService operLogService;
	@Autowired
	private IPersonalConfigDetailService service;
	@Autowired
	private IPersonalConfigService personalConfigService;
	
	/**
	 * 查所有及分页及条件查询
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/listPersonalConfigDetail")
	@ResponseBody
	public Map<String,Object> list(@RequestParam Map<String,Object> param,HttpServletRequest request,
			HttpServletResponse response)throws Exception{
		Map<String, Object> map = ControllerTool.mapFilter(param);
		map.put("usePage", true);
		List<PersonalConfigDetailEntity> list=service.list(map);
		Map<String, Object> result = new HashMap<String, Object>();
		logger.info("++++++++++++++listAllPersonalConfig-----------"+list+"--------");
		result.put("total", this.service.count(map));
		result.put("rows", list);
		return result;
	}

	/**
	 * 插入办案场所个性化配置详细信息
	 * @param form
	 * @throws Exception
	 */
	@RequestMapping(value = "/addPersonalConfigDetail")
	@ResponseBody
	public MessageEntity add(@RequestBody PersonalConfigDetailForm form, HttpServletRequest request){
		System.out.println("++++++++add++++++UserForm=" + form);
		PersonalConfigDetailEntity entity = new PersonalConfigDetailEntity();
		entity.setId(form.getId());
		entity.setParamKey(form.getParamKey());
		entity.setParamValue(form.getParamValue());
		entity.setPersonalConfigId(form.getPersonalConfigId());
		entity.setDesc(form.getDesc());

		try {
			service.insert(entity);
			this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "添加办案场所个性化配置详细信息" + entity, ControllerTool.getRoleName(request), true,OperLogEntity.SYSTEM_BACS);
		} catch (Exception e) {
			logger.fatal("Error on adding user!", e);
			this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "添加办案场所个性化配置详细信息" + entity,  ControllerTool.getRoleName(request), true,OperLogEntity.SYSTEM_BACS);
			return new MessageEntity().addCode(1).addIsError(true).addTitle("Error").addContent("添加失败!");
		}
		return new MessageEntity().addCode(1).addIsError(false).addTitle("Message").addContent("添加成功!");
	}

	/**
	 * 根据id删除办案场所个性化配置详细信息
	 * @throws Exception
	 */
	@RequestMapping(value = "/removePersonalConfigDetail")
	@ResponseBody
	public MessageEntity remove(@RequestBody IDForm form, HttpServletRequest request) {
		System.out.println("++++++++remove++++++id=  " + form.getId());
		try {
			service.delete(form.getIntID());
			this.operLogService.insertLog(OperLogEntity.DELETE_TYPE, "删除办案场所个性化配置详细信息" + form.getId(), ControllerTool.getRoleName(request), true,OperLogEntity.SYSTEM_BACS);
		} catch (Exception e) {
			logger.fatal("Error on deleting user!", e);
			this.operLogService.insertLog(OperLogEntity.DELETE_TYPE, "删除办案场所个性化配置详细信息" + form.getId(), ControllerTool.getRoleName(request), false,OperLogEntity.SYSTEM_BACS);
			return new MessageEntity().addCode(1).addIsError(true).addTitle("Error").addContent("删除失败!");
		}
		return new MessageEntity().addCode(1).addIsError(false).addTitle("Message").addContent("删除成功!");
	}
	/**
	 * 修改证件信息
	 * @throws Exception
	 */
	@RequestMapping(value = "/editPersonalConfigDetail")
	@ResponseBody
	public MessageEntity edit(@RequestBody PersonalConfigDetailForm form, HttpServletRequest request)throws Exception{
		//System.out.println("++++++++edit++++++UserForm=" + form);
		PersonalConfigDetailEntity entity = new PersonalConfigDetailEntity();
		entity.setId(form.getId());
		entity.setParamKey(form.getParamKey());
		entity.setParamValue(form.getParamValue());
		entity.setPersonalConfigId(form.getPersonalConfigId());
		entity.setDesc(form.getDesc());
		try {
			service.update(entity);
			this.operLogService.insertLog(OperLogEntity.EDIT_TYPE, "修改办案场所个性化配置详细信息" + entity,  ControllerTool.getRoleName(request), false,OperLogEntity.SYSTEM_BACS);
		} catch (Exception e) {
			logger.fatal("Error on editing user!", e);
			this.operLogService.insertLog(OperLogEntity.EDIT_TYPE, "修改办案场所个性化配置详细信息" + entity,  ControllerTool.getRoleName(request), false,OperLogEntity.SYSTEM_BACS);
			return new MessageEntity().addCode(1).addIsError(true).addTitle("Error").addContent("修改失败!");
		}
		return new MessageEntity().addCode(1).addIsError(false).addTitle("Message").addContent("修改成功!");
	}

	/**
	 * 查询个性化配置
	 * @param name
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/listConfigDetailByAreaAndName")
	@ResponseBody
	public MessageEntity listConfigDetailByAreaAndName(@RequestParam String name,  HttpServletRequest request){
		try{
			Integer areaId=ControllerTool.getCurrentAreaID(request);
			if(areaId>0){
				Map<String, String> map = service.listConfigDetailByAreaAndName(areaId, java.net.URLDecoder.decode(name, "utf-8"));
				return new MessageEntity().addCallbackData(map).addIsError(false);
			}
		} catch (Exception e) {
			return new MessageEntity().addCode(1).addIsError(true).addContent("查询"+name+"失败，错误代码："+e);
		}
		return new MessageEntity().addIsError(false).addContent("当前办案区不明确，请重新确认并操作");
	}
}
