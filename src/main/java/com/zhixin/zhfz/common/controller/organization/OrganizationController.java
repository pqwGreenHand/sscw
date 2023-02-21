package com.zhixin.zhfz.common.controller.organization;

import com.zhixin.zhfz.common.entity.*;
import com.zhixin.zhfz.common.form.IDForm;
import com.zhixin.zhfz.common.form.OrganizationForm;
import com.zhixin.zhfz.common.services.code.ICodeService;
import com.zhixin.zhfz.common.services.operLog.IOperLogService;
import com.zhixin.zhfz.common.services.organiztion.IOrganizationService;
import com.zhixin.zhfz.common.utils.ControllerTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/zhfz/common/organization")
public class OrganizationController {

	private static final Logger logger = LoggerFactory.getLogger(OrganizationController.class);
	List<Integer> rlist;
	@Autowired
	private IOrganizationService organizationService;
	@Autowired
	private IOperLogService operLogService;

	@Autowired
	private ICodeService service;

	/**
	 * 列表展示
	 *
	 * @param params
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/list")
	@ResponseBody
	public Map<String, Object> list(@RequestParam Map<String, Object> params, HttpServletRequest request) {
		Map<String, Object> map = ControllerTool.mapFilter(params);
		// map.put("usePage", true);
		List<OrganizationEntity> list = new ArrayList<OrganizationEntity>();
		int count = 0;
		boolean flag = true;

		SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);

		if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuthForXtConf(request))) {
			// 本部门及下级部门
			map.put("dataAuth","o.op_pid like " + sessionInfo.getCurrentAndSubOrgPid() + "or o.p_id like" + sessionInfo.getCurrentAndSubOrgPid());
		} else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuthForXtConf(request))) {
			// 上级部门及下级部门
			map.put("dataAuth","o.op_pid like " + sessionInfo.getSuperAndSubOrgPid());
		} else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuthForXtConf(request))) {
			// 本部门
			map.put("dataAuth","o.op_pid = " + sessionInfo.getCurrentOrgPid());
		} else if(RoleEntity.DATA_AUTH_FULL == (ControllerTool.getRoleDataAuthForXtConf(request))){
			//全部
		}else if(RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuthForXtConf(request))){
			//本人
			map.put("dataAuth","o.op_user_id = " + ControllerTool.getUserID(request));
		}else{
			flag = false;
		}

		if (flag) {
			list = this.organizationService.list(map);
			count = this.organizationService.count(map);
		}


		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", count);
		result.put("rows", list);
		return result;
	}

	/**
	 * 新增部门时上级部门展示
	 *
	 * @param params
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/upOrgCombo")
	@ResponseBody
	public List upOrgCombo(@RequestParam Map<String, Object> params, HttpServletRequest request) {

		List<OrganizationEntity> list = null;


		list = organizationService.listAllOrganization(new HashMap<String, Object>());
		return list;
	}

	@RequestMapping(value = "/add")
	@ResponseBody
	public MessageEntity add(@RequestBody OrganizationForm form, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("++++++++add++++++OrganizationForm=" + form.toString());
		OrganizationEntity entity = new OrganizationEntity();
		entity.setId(form.getId());
		entity.setName(form.getName());
		entity.setType(form.getType());
		entity.setAddress(form.getAddress());
		entity.setTelephone(form.getTelephone());
		entity.setPostcode(form.getPostcode());
		entity.setOrgCode(form.getOrgCode());
		entity.setOrgAlias(form.getOrgAlias());
		entity.setOrgRep(form.getOrgRep());
		entity.setOrgStatus(form.getOrgStatus());
		entity.setParentId(form.getParentId());
		entity.setOp_User_Id(ControllerTool.getSessionInfo(request).getUser().getId());
		entity.setOp_pid(ControllerTool.getSessionInfo(request).getCurrentOrg().getOp_pid());

		try {
			organizationService.insertOrganization(entity);
			if(form.getParentId()!=null&&form.getParentId()!=0){
				OrganizationEntity parentOrganizationEntity=organizationService.getOrganizationById(form.getParentId());
				if(parentOrganizationEntity!=null){
                    entity.setId(entity.getId());
                    entity.setPid(parentOrganizationEntity.getPid()+"_"+entity.getId());
					organizationService.updateOrganization(entity);
				}
			}

			organizationService.refreshSessionOrg(ControllerTool.getSessionInfo(request)); // 刷新缓存
			this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "添加组织机构" + entity, ControllerTool.getLoginName(request), true, "系统配置");

		} catch (Exception e) {
			logger.error("Error on add organization!", e);
			this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "添加组织机构" + entity, ControllerTool.getLoginName(request), false, "系统配置");
			return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("新增部门失败!");
		}
		return new MessageEntity().addCode(1).addIsError(false).addTitle("提醒").addContent("新增部门成功!");
	}

	@RequestMapping(value = "/edit")
	@ResponseBody
	public MessageEntity edit(@RequestBody OrganizationForm form, HttpServletRequest request,
			HttpServletResponse response) {
		logger.debug("++++++++edit++++++OrganizationForm=" + form);
		OrganizationEntity entity = new OrganizationEntity();
		entity.setId(form.getId());
		entity.setName(form.getName());
		entity.setType(form.getType());
		entity.setAddress(form.getAddress());
		entity.setTelephone(form.getTelephone());
		entity.setPostcode(form.getPostcode());
		entity.setOrgCode(form.getOrgCode());
		entity.setOrgAlias(form.getOrgAlias());
		entity.setOrgRep(form.getOrgRep());
		entity.setOrgStatus(form.getOrgStatus());
		entity.setParentId(form.getParentId());
		try {

			if(form.getParentId()!=null&&form.getParentId()!=0){
				OrganizationEntity parentOrganizationEntity=organizationService.getOrganizationById(form.getParentId());
				if(parentOrganizationEntity!=null){
					entity.setPid(parentOrganizationEntity.getPid()+"_"+entity.getId());
				}
			}

			organizationService.updateOrganization(entity);
			this.operLogService.insertLog(OperLogEntity.EDIT_TYPE, "修改组织机构" + entity, ControllerTool.getLoginName(request), true, "系统配置");
		} catch (Exception e) {
			logger.error("Error on edit Organization!", e);
			this.operLogService.insertLog(OperLogEntity.EDIT_TYPE, "修改组织机构" + entity, ControllerTool.getLoginName(request), false, "系统配置");
			return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("修改部门失败!");
		}
		return new MessageEntity().addCode(1).addIsError(false).addTitle("提醒").addContent("修改部门成功!");
	}

	@RequestMapping(value = "/remove")
	@ResponseBody
	public MessageEntity remove(@RequestBody IDForm form, HttpServletRequest request) {
		logger.debug("++++++++remove++++++id=" + form.getId());
		try {
			organizationService.deleteOrganization(form.getIntID());
			organizationService.refreshSessionOrg(ControllerTool.getSessionInfo(request)); // 刷新缓存
			this.operLogService.insertLog(OperLogEntity.DELETE_TYPE, "删除组织机构" + form, ControllerTool.getLoginName(request), true, "系统配置");
		} catch (Exception e) {
			logger.error("Error on deleting Organization!", e);
			this.operLogService.insertLog(OperLogEntity.DELETE_TYPE, "删除组织机构" + form, ControllerTool.getLoginName(request), false, "系统配置");
			return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("删除部门失败!");
		}
		return new MessageEntity().addCode(1).addIsError(false).addTitle("提醒").addContent("删除部门成功!");
	}

	@RequestMapping(value = "/listChildOrg")
	@ResponseBody
	public Map<String, Object> listChildOrg(@RequestParam Map params,HttpServletRequest request) {
		logger.info("+++++++++listChild+++++++" + params.toString());


		Map<String, Object> map = ControllerTool.mapFilter(params);
		boolean flag = true;

		SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);

		if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuthForXtConf(request))) {
			// 本部门及下级部门
			map.put("dataAuth","o.op_pid like " + sessionInfo.getCurrentAndSubOrgPid());
		} else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuthForXtConf(request))) {
			// 上级部门及下级部门
			map.put("dataAuth","o.op_pid like " + sessionInfo.getSuperAndSubOrgPid());
		} else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuthForXtConf(request))) {
			// 本部门
			map.put("dataAuth","o.op_pid = " + sessionInfo.getCurrentOrgPid());
		} else if(RoleEntity.DATA_AUTH_FULL == (ControllerTool.getRoleDataAuthForXtConf(request))){
			//全部
		}else if(RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuthForXtConf(request))){
			//本人
			map.put("dataAuth","o.op_user_id = " + ControllerTool.getUserID(request));
		}else{
			flag = false;
		}
		List<OrganizationEntity> list = organizationService.listChildOrg(map);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", this.organizationService.childCount(map));
		result.put("rows", list);
		logger.info("+++++++++listChild .size+++++++" + list.size());
		return result;
	}

	// 数据导入
	@RequestMapping(value = "/userImportByXls")
	@ResponseBody
	public MessageEntity userImportByXls(@RequestParam(value = "file", required = false) MultipartFile file,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (file == null) {
			return new MessageEntity().addCode(0).addIsError(true).addTitle("Message").addContent("上传文件为空!");
		}
		logger.info("size:" + file.getBytes().length);
		try {
			organizationService.userImportByXls(file,request);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return new MessageEntity().addCode(0).addIsError(true).addTitle("Message").addContent(e.getMessage());
		}

		return new MessageEntity().addCode(1).addIsError(false).addTitle("Message").addContent("导入民警信息成功!");
	}


}
