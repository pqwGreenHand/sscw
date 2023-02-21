package com.zhixin.zhfz.bacs.controller.crimedefine;

import com.zhixin.zhfz.bacs.entity.CrimeDefineEntity;
import com.zhixin.zhfz.bacs.form.CrimeDefineForm;
import com.zhixin.zhfz.bacs.services.crimedefine.ICrimeDefineService;
import com.zhixin.zhfz.common.entity.MessageEntity;
import com.zhixin.zhfz.common.entity.OperLogEntity;
import com.zhixin.zhfz.common.entity.RoleEntity;
import com.zhixin.zhfz.common.entity.SessionInfo;
import com.zhixin.zhfz.common.form.IDForm;
import com.zhixin.zhfz.common.services.operLog.IOperLogService;
import com.zhixin.zhfz.common.utils.ControllerTool;
import org.apache.commons.beanutils.PropertyUtils;
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
@RequestMapping("/zhfz/bacs/crimedefine")
public class CrimeDefineController {

	private static final Logger logger = LoggerFactory.getLogger(CrimeDefineController.class);

	@Autowired
	private IOperLogService operLogService;

	@Autowired
	private ICrimeDefineService service;

	/**
	 * 查所有及分页及条件查询
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	@ResponseBody
	public Map<String, Object> list(@RequestParam Map<String, Object> param, HttpServletRequest request,
                                    HttpServletResponse response) throws Exception {
		logger.info("-------------------------crimedefine/list------------------------------");
		Map<String, Object> map = ControllerTool.mapFilter(param);
		int total=0;
		List<CrimeDefineEntity> list=new ArrayList<CrimeDefineEntity>();
		list = this.service.list(map);
		total=this.service.count(map);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", total);
		result.put("rows", list);
		return result;
	}

	/**
	 * 插入犯罪类型
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/add")
	@ResponseBody
	public MessageEntity add(@RequestBody CrimeDefineForm form ,HttpServletRequest request) {
		logger.info("-------------------------crimedefine/add------------------------------");
		CrimeDefineEntity entity = new CrimeDefineEntity();
		entity.setSpellCode(form.getSpellCode());
		entity.setCodeClass(form.getCodeClass());
		entity.setCode(form.getCode());
		entity.setCodeClassDesc(form.getCodeClassDesc());
		entity.setSortNo(form.getSortNo());
		entity.setOp_Pid(ControllerTool.getSessionInfo(request).getCurrentOrg().getPid());
		entity.setOp_User_Id(ControllerTool.getSessionInfo(request).getUser().getId());
		try {
			PropertyUtils.copyProperties(entity, form);
			service.insert(entity);
			this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "添加犯罪类型:" + entity, ControllerTool.getUser(request).getLoginName(), true,"办案场所");
		} catch (Exception e) {
			this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "添加犯罪类型:" + entity, ControllerTool.getUser(request).getLoginName(), false,"办案场所");
			return new MessageEntity().addCode(1).addIsError(true).addTitle("Error").addContent("添加犯罪类型失败!");
		}
		return new MessageEntity().addCode(1).addIsError(false).addTitle("Message").addContent("添加犯罪类型成功!");
	}

	/**
	 * 根据id删除
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/remove")
	@ResponseBody
	public MessageEntity remove(@RequestBody IDForm form , HttpServletRequest request) {
		logger.info("-------------------------crimedefine/remove------------------------------");
		try {
			service.delete(form.getIntID());
			this.operLogService.insertLog(OperLogEntity.DELETE_TYPE, "删除犯罪类型:" + form.getId(), ControllerTool.getUser(request).getLoginName(), true,"办案场所");
		} catch (Exception e) {
			this.operLogService.insertLog(OperLogEntity.DELETE_TYPE, "删除犯罪类型:" + form.getId(),  ControllerTool.getUser(request).getLoginName(), false,"办案场所");
			return new MessageEntity().addCode(1).addIsError(true).addTitle("Error").addContent("删除犯罪类型失败!");
		}
		return new MessageEntity().addCode(1).addIsError(false).addTitle("Message").addContent("删除犯罪类型成功!");
	}

	/**
	 * 根据id更新
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/update")
	@ResponseBody
	public MessageEntity update(@RequestBody CrimeDefineForm form, HttpServletRequest request) {
		logger.info("-------------------------crimedefine/update------------------------------");
		CrimeDefineEntity entity = new CrimeDefineEntity();
		try {
			PropertyUtils.copyProperties(entity, form);
			service.update(entity);
			this.operLogService.insertLog(OperLogEntity.EDIT_TYPE, "修改犯罪类型:" + entity, ControllerTool.getUser(request).getLoginName(), true,"办案场所");
		} catch (Exception e) {
			logger.info("getMessage==="+e.getMessage());
			this.operLogService.insertLog(OperLogEntity.EDIT_TYPE, "修改犯罪类型:" + entity,  ControllerTool.getUser(request).getLoginName(), false,"办案场所");
			return new MessageEntity().addCode(1).addIsError(true).addTitle("Error").addContent("更新犯罪类型失败!");
		}
		return new MessageEntity().addCode(1).addIsError(false).addTitle("Message").addContent("更新犯罪类型成功!");
	}


}
