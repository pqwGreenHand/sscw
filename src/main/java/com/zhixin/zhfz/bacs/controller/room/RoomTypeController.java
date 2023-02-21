package com.zhixin.zhfz.bacs.controller.room;

import com.zhixin.zhfz.bacs.entity.RoomTypeEntity;
import com.zhixin.zhfz.bacs.form.RoomTypeForm;
import com.zhixin.zhfz.bacs.services.room.IRoomTypeService;
import com.zhixin.zhfz.common.entity.MessageEntity;
import com.zhixin.zhfz.common.entity.OperLogEntity;
import com.zhixin.zhfz.common.entity.RoleEntity;
import com.zhixin.zhfz.common.entity.SessionInfo;
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
@RequestMapping("/zhfz/bacs/room")
public class RoomTypeController {
	
	private static final Logger logger =LoggerFactory.getLogger(RoomTypeController.class);
	
	@Autowired
	private IRoomTypeService roomTypeService;
	
	@Autowired
	private IOperLogService operLogService;

	@RequestMapping(value = "/typelist")
	@ResponseBody
	public Map<String, Object> listAllRoomType(@RequestParam Map<String,Object> params, HttpServletRequest request)throws Exception{
		int total=0;
		boolean flag=true;
		List<RoomTypeEntity> roomTypes =new ArrayList<RoomTypeEntity>();
		Map<String, Object> map = ControllerTool.mapFilter(params);
		logger.info("params================"+params);
		SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
		if (RoleEntity.DATA_AUTH_SELFAREA == (ControllerTool.getRoleDataAuthForBacsConf(request))) {
			// 本办案场所
			map.put("dataAuth", "ia.id=" + ControllerTool.getCurrentAreaID(request));
		} else if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuthForBacsConf(request))) {
			// 本办案场所及下级办案场所
		} else if (RoleEntity.DATA_AUTH_UP_CASCADEAREA == (ControllerTool.getRoleDataAuthForBacsConf(request))) {
			// 上级办案场所及其下级办案场所
		} else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuthForBacsConf(request))) {
			// 本部门及下级部门
		} else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuthForBacsConf(request))) {
			// 上级部门及下级部门OPPID
			map.put("dataAuth","ba_room_type.op_pid like " + sessionInfo.getSuperAndSubOrgPid());
		} else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuthForBacsConf(request))) {
			// 本部门 OPPID
			map.put("dataAuth","ba_room_type.op_pid = " + sessionInfo.getCurrentOrgPid());
		} else if(RoleEntity.DATA_AUTH_FULL == (ControllerTool.getRoleDataAuthForBacsConf(request))){
			//全部
			flag = true;
		}else if(RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuthForBacsConf(request))){
			//本人
			map.put("dataAuth","ba_room_type.op_user_id = " + ControllerTool.getUserID(request));
		}

		if (flag){
			roomTypes = roomTypeService.list(map);
			total=this.roomTypeService.count(map);
		}

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total",total );
		result.put("rows", roomTypes);
		return result;
	}
	//添加房间类型
	@RequestMapping(value = "/typeadd")
	@ResponseBody
	public MessageEntity addRoomType(@RequestBody RoomTypeForm form,HttpServletRequest request)throws Exception{
		System.out.println("++++++++add++++++RoomTypeForm=" + form);
		RoomTypeEntity entity = new RoomTypeEntity();
		entity.setId(form.getId());
		entity.setName(form.getName());
		entity.setOp_pid(ControllerTool.getSessionInfo(request).getCurrentOrg().getPid());
		entity.setOp_user_id(ControllerTool.getSessionInfo(request).getUser().getId());

		try {
			roomTypeService.insert(entity);
			this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "添加功能室类型" + entity, ControllerTool.getUser(request).getLoginName(), true,"办案场所");
		} catch (Exception e) {
			logger.error("Error on adding type!", e);
			this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "添加功能室类型" + entity, ControllerTool.getUser(request).getLoginName(), false,"办案场所");
			return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("添加失败!");
		}
		return new MessageEntity().addCode(1).addIsError(false).addTitle("提醒").addContent("添加成功!");
	}
	//删除一个房间类型
	@RequestMapping(value = "/typedelete")
	@ResponseBody
	public MessageEntity removeRoomType(@RequestBody RoomTypeForm form) {
		System.out.println("++++++++remove++++++id=  " + form.getId());
		try {
			roomTypeService.delete(form.getId());
			this.operLogService.insertLog(OperLogEntity.DELETE_TYPE, "删除功能室类型" + form.getId(), "system", true,"场所配置");
		} catch (Exception e) {
			logger.error("Error on deleting type!", e);
			this.operLogService.insertLog(OperLogEntity.DELETE_TYPE, "删除功能室类型" + form.getId(), "system", false,"场所配置");
			return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("删除失败!");
		}
		return new MessageEntity().addCode(1).addIsError(false).addTitle("提醒").addContent("删除成功!");
	}
	//修改
	@RequestMapping(value = "/typeedit")
	@ResponseBody
	public MessageEntity editRoomType(@RequestBody RoomTypeForm form)throws Exception{
		logger.info("++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		RoomTypeEntity entity = new RoomTypeEntity();
		entity.setId(form.getId());
		entity.setName(form.getName());
		try {
			roomTypeService.update(entity);
			this.operLogService.insertLog(OperLogEntity.EDIT_TYPE, "修改功能室类型" + entity, "system", true,"场所配置");
		} catch (Exception e) {
			logger.error("Error on editing type!", e);
			this.operLogService.insertLog(OperLogEntity.EDIT_TYPE, "修改功能室类型" + entity, "system", false,"场所配置");
			return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("修改失败!");
		}
		return new MessageEntity().addCode(1).addIsError(false).addTitle("提醒").addContent("修改成功!");
	}
}
