package com.zhixin.zhfz.bacs.controller.nvr;

import com.zhixin.zhfz.bacs.entity.NvrEntity;
import com.zhixin.zhfz.bacs.form.NvrForm;
import com.zhixin.zhfz.bacs.form.NvrListForm;
import com.zhixin.zhfz.bacs.services.nvr.INvrService;
import com.zhixin.zhfz.common.entity.MessageEntity;
import com.zhixin.zhfz.common.entity.OperLogEntity;
import com.zhixin.zhfz.common.entity.RoleEntity;
import com.zhixin.zhfz.common.entity.SessionInfo;
import com.zhixin.zhfz.common.form.IDForm;
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
@RequestMapping("/zhfz/bacs/nvr")
public class NvrController {

	private static final Logger logger = LoggerFactory.getLogger(NvrController.class);

	@Autowired
	private IOperLogService operLogService;

	@Autowired
	private INvrService service;

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
		System.out.println("list:========="+param);
		Map<String, Object> map = ControllerTool.mapFilter(param);
		List<NvrEntity> list=new ArrayList<NvrEntity>();
		int total=0;
		boolean flag=true;
		SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
		if (RoleEntity.DATA_AUTH_SELFAREA == (ControllerTool.getRoleDataAuthForBacsConf(request))) {
			// 本办案场所
			map.put("dataAuth", "n.area_id=" + ControllerTool.getCurrentAreaID(request));
		} else if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuthForBacsConf(request))) {
			// 本办案场所及下级办案场所
			map.put("dataAuth", "n.area_id " + sessionInfo.getCurrentAndSubAreaInStr());
		} else if (RoleEntity.DATA_AUTH_UP_CASCADEAREA == (ControllerTool.getRoleDataAuthForBacsConf(request))) {
			// 上级办案场所及其下级办案场所
			map.put("dataAuth", "n.area_id " + sessionInfo.getSuperAndSubAreaInStr());
		} else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuthForBacsConf(request))) {
			// 本部门及下级部门
			map.put("dataAuth","n.op_pid like " + sessionInfo.getCurrentAndSubOrgPid());
		} else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuthForBacsConf(request))) {
			// 上级部门及下级部门
			map.put("dataAuth","n.op_pid like " + sessionInfo.getSuperAndSubOrgPid());
		} else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuthForBacsConf(request))) {
			// 本部门
			map.put("dataAuth","n.op_pid = " + sessionInfo.getCurrentOrgPid());
		} else if(RoleEntity.DATA_AUTH_FULL == (ControllerTool.getRoleDataAuthForBacsConf(request))){
			//全部
			flag = true;
		}else if(RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuthForBacsConf(request))){
			//本人
			map.put("dataAuth","n.op_user_id = " + ControllerTool.getUserID(request));
		}

		if (flag){
			list = this.service.list(map);
			total = this.service.count(map);
		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", total);
		result.put("rows", list);
		return result;
	}

	@RequestMapping(value = "/comboNvr")
	@ResponseBody
	public List<NvrEntity> comboNvr(@RequestParam Map<String, Object> param,HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> map = ControllerTool.mapFilter(param);
		List<NvrEntity> nvrEntities = service.comboNvr(map);
		return nvrEntities;
	}
    /**
     * 单个插入
     *
     * @param
     * @throws Exception
     */
    @RequestMapping(value = "/nvrAdd")
    @ResponseBody
    public MessageEntity add(@RequestBody NvrForm form,HttpServletRequest request) {
        System.out.println("++++++++add++++++NvrForm=" + form);
        NvrEntity entity = new NvrEntity();
        entity.setName(form.getName());
        entity.setIp(form.getIp());
        entity.setIpBack(form.getIpBack());
        entity.setAccount(form.getAccount());
        entity.setPassword(form.getPassword());
        entity.setPort(form.getPort());
        entity.setAreaId(form.getAreaId());
        entity.setVender(form.getVender());
        entity.setType(form.getType());
		entity.setOp_Pid(ControllerTool.getSessionInfo(request).getCurrentOrg().getPid());
		entity.setOp_User_Id(ControllerTool.getSessionInfo(request).getUser().getId());
        try {
            service.insert(entity);
            this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "添加NVR设备:" + entity, ControllerTool.getRoleName(request), true,OperLogEntity.SYSTEM_BACS);
        } catch (Exception e) {
            this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "添加NVR设备:" + entity, ControllerTool.getRoleName(request), false,OperLogEntity.SYSTEM_BACS);
            return new MessageEntity().addCode(1).addIsError(true).addTitle("Error").addContent("添加NVR设备失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("Message").addContent("添加NVR设备成功!");
    }
	/**
	 * 批量插入
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/nvrAddList")
	@ResponseBody
	public MessageEntity addList(@RequestBody NvrForm form,HttpServletRequest request) {
		System.out.println("++++++++add++++++NvrListForm=" + form.getListForm()+"+===========count:"+form.getNvrCount());
		String op_pid = ControllerTool.getSessionInfo(request).getCurrentOrg().getPid();
		Integer user_pid = ControllerTool.getSessionInfo(request).getUser().getId();
		NvrListForm nvrListForm = form.getListForm();
		List<NvrEntity> nvrList = new ArrayList<NvrEntity>();
		for(int i = 0;i<form.getNvrCount();i++){
			NvrEntity entity = new NvrEntity();
			entity.setName(nvrListForm.getName()[i]);
			entity.setIp(nvrListForm.getIp()[i]);
			entity.setIpBack(nvrListForm.getIp_back()[i]);
			entity.setAccount(form.getAccount());
			entity.setPassword(form.getPassword());
			entity.setPort(form.getPort());
			entity.setAreaId(form.getAreaId());
			entity.setVender(form.getType()+"");
			entity.setType(form.getType());
			entity.setOp_Pid(op_pid);
			entity.setOp_User_Id(user_pid);
			nvrList.add(entity);
		}
		try {
			service.insertList(nvrList);
			this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "添加NVR设备:" + form.getNvrCount(), ControllerTool.getRoleName(request), true,OperLogEntity.SYSTEM_BACS);
		} catch (Exception e) {
			e.printStackTrace();
			this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "添加NVR设备:" + form.getNvrCount(), ControllerTool.getRoleName(request), false,OperLogEntity.SYSTEM_BACS);
			return new MessageEntity().addCode(1).addIsError(true).addTitle("Error").addContent("添加NVR设备失败!");
		}
		return new MessageEntity().addCode(1).addIsError(false).addTitle("Message").addContent("添加NVR设备成功!");
	}

	/**
	 * 根据id删除
	 * 
	 * @param form
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	@ResponseBody
	public MessageEntity delete(@RequestBody IDForm form,HttpServletRequest request) {
		try {
			service.delete(form.getIntID());
			service.deleteCameraByNvrId(form.getIntID());
			this.operLogService.insertLog(OperLogEntity.DELETE_TYPE, "删除NVR设备:" + form.getId(), ControllerTool.getRoleName(request), true,OperLogEntity.SYSTEM_BACS);
		} catch (Exception e) {
			this.operLogService.insertLog(OperLogEntity.DELETE_TYPE, "删除NVR设备:" + form.getId(), ControllerTool.getRoleName(request), false,OperLogEntity.SYSTEM_BACS);
			return new MessageEntity().addCode(1).addIsError(true).addTitle("Error").addContent("删除NVR设备失败!");
		}
		return new MessageEntity().addCode(1).addIsError(false).addTitle("Message").addContent("删除NVR设备成功!");
	}

	/**
	 * 根据id更新
	 * 
	 * @param form
	 ** @param request
	 * @throws Exception
	 */
	@RequestMapping(value = "/update")
	@ResponseBody
	public MessageEntity update(@RequestBody NvrForm form, HttpServletRequest request) {
		NvrEntity entity = new NvrEntity();
		try {
			entity.setId(form.getId());
			entity.setName(form.getName());
			entity.setIp(form.getIp());
			entity.setIpBack(form.getIpBack());
			entity.setAccount(form.getAccount());
			entity.setPassword(form.getPassword());
			entity.setPort(form.getPort());
			entity.setAreaId(form.getAreaId());
			entity.setVender(form.getVender());
			entity.setType(form.getType());
			service.update(entity);
			this.operLogService.insertLog(OperLogEntity.EDIT_TYPE, "修改NVR设备:" + entity, ControllerTool.getRoleName(request), true,OperLogEntity.SYSTEM_BACS);
		} catch (Exception e) {
			this.operLogService.insertLog(OperLogEntity.EDIT_TYPE, "修改NVR设备:" + entity, ControllerTool.getRoleName(request), false,OperLogEntity.SYSTEM_BACS);
			return new MessageEntity().addCode(1).addIsError(true).addTitle("Error").addContent("更新NVR设备失败!");
		}
		return new MessageEntity().addCode(1).addIsError(false).addTitle("Message").addContent("更新NVR设备成功!");
	}

}
