package com.zhixin.zhfz.common.controller.code;

import com.zhixin.zhfz.common.entity.*;
import com.zhixin.zhfz.common.form.CodeForm;
import com.zhixin.zhfz.common.services.code.ICodeService;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/zhfz/common/code")
public class CodeController {

	private static final Logger logger = LoggerFactory.getLogger(CodeController.class);

	
	@Autowired
	private IOperLogService operLogService;

	@Autowired
	private ICodeService service;
	/**
	 * 查所有及分页及条件查询
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	@ResponseBody
	public Map<String,Object> list(@RequestParam Map<String,Object> param, HttpServletRequest request)throws Exception{
		Map<String, Object> map = ControllerTool.mapFilter(param);
		List<CodeEntity> datas = new ArrayList<CodeEntity>();

		int total = 0;
		boolean flag = true;
		/*SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);

		if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuthForXtConf(request))) {
			// 本部门及下级部门
			map.put("dataAuth","c.op_pid like " + sessionInfo.getCurrentAndSubOrgPid());
		} else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuthForXtConf(request))) {
			// 上级部门及下级部门
			map.put("dataAuth","c.op_pid like " + sessionInfo.getSuperAndSubOrgPid());
		} else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuthForXtConf(request))) {
			// 本部门
			map.put("dataAuth","c.op_pid = " + sessionInfo.getCurrentOrgPid());
		} else if(RoleEntity.DATA_AUTH_FULL == (ControllerTool.getRoleDataAuthForXtConf(request))){
			//全部
		}else if(RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuthForXtConf(request))){
			//本人
			map.put("dataAuth","c.op_user_id = " + ControllerTool.getUserID(request));
		}else{
			flag = false;
		}*/

		if (flag) {
			datas = this.service.list(map);
			total = this.service.count(map);
		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", total);
		result.put("rows", datas);
		return result;
	}

	/**
	 * 插入证件类型
	 * @throws Exception
	 */
	@RequestMapping(value = "/addCodeEntity")
	@ResponseBody
	public MessageEntity add(@RequestBody CodeForm form, HttpServletRequest request){
		logger.info("++++++++add++++++UserForm=" + form);
		CodeEntity entity = new CodeEntity();
		entity.setCodeValue(form.getCodeValue());
		entity.setCodeKey(form.getCodeKey());
		entity.setKeyDesc(form.getKeyDesc());
		entity.setType(form.getType());
		entity.setOp_Pid(ControllerTool.getSessionInfo(request).getCurrentOrg().getPid());
		entity.setOp_User_Id(ControllerTool.getSessionInfo(request).getUser().getId());
		try {
			service.insert(entity);
			this.operLogService.insertLog(OperLogEntity.INSERT_TYPE,"添加code" + entity, "system", true,"");
		} catch (Exception e) {
			logger.error("Error on adding user!", e);
			this.operLogService.insertLog(OperLogEntity.INSERT_TYPE,"添加code" + entity, "system", false,"");
			return new MessageEntity().addCode(1).addIsError(true).addTitle("提示").addContent("添加失败!");
		}
		return new MessageEntity().addCode(1).addIsError(false).addTitle("提示").addContent("添加成功!");
	}
	/**
	 * @param id
	 * @throws Exception
	 */
	@RequestMapping(value = "/removeCodeEntity")
	@ResponseBody
	public MessageEntity remove(@RequestParam Integer id) {
		System.out.println("++++++++remove++++++id=  " + id);
		try {
			service.delete(id);
			this.operLogService.insertLog(OperLogEntity.DELETE_TYPE,"删除code" + id, "system", true,"");
		} catch (Exception e) {
			logger.error("Error on deleting user!", e);
			this.operLogService.insertLog(OperLogEntity.DELETE_TYPE,"删除code" + id, "system", false,"");
			return new MessageEntity().addCode(1).addIsError(true).addTitle("提示").addContent("删除失败!");
		}
		return new MessageEntity().addCode(1).addIsError(false).addTitle("提示").addContent("删除成功!");
	}
	
	
	/**
	 * @param form
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateCodeEntity")
	@ResponseBody
	public MessageEntity updateCodeEntity(@RequestBody CodeForm form,HttpServletRequest request) {
		try{
			CodeEntity entity=new CodeEntity();
			entity.setId(form.getId());
			entity.setKeyDesc(form.getKeyDesc());
			entity.setCodeValue(form.getCodeValue());
			entity.setCodeKey(form.getCodeKey());
			entity.setType(form.getType());
			service.update(entity);
		} catch (Exception e) {
			logger.error("Error on deleting user!", e);
			this.operLogService.insertLog(OperLogEntity.EDIT_TYPE,"修改code" , "system", false,"");
			return new MessageEntity().addCode(1).addIsError(true).addTitle("提示").addContent("修改失败!");
		}
		return new MessageEntity().addCode(1).addIsError(false).addTitle("提示").addContent("修改成功!");
	}
	/**
	 * 查所有及分页及条件查询
	 * @param type
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/listCodeByType")
	@ResponseBody
	public List<CodeEntity> listCodeByType(@RequestParam String type, HttpServletRequest reques)throws Exception{
		logger.info("listCodeByType:"+type);
		List<CodeEntity> list=service.listByType(type);
		return list;
	}


}
