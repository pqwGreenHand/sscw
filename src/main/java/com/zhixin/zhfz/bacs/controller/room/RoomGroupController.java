package com.zhixin.zhfz.bacs.controller.room;


import com.zhixin.zhfz.bacs.entity.RoomGroupEntity;
import com.zhixin.zhfz.bacs.form.RoomGroupForm;
import com.zhixin.zhfz.bacs.services.room.IRoomGroupService;
import com.zhixin.zhfz.common.entity.MessageEntity;
import com.zhixin.zhfz.common.entity.OperLogEntity;
import com.zhixin.zhfz.common.entity.RoleEntity;
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
public class RoomGroupController {

	private static final Logger logger =LoggerFactory.getLogger(RoomGroupController.class);

	@Autowired
	private IRoomGroupService roomGroupService;

	@Autowired
	private IOperLogService operLogService;

	@RequestMapping(value = "/grouplist")
	@ResponseBody
	public Map<String, Object> listAllRoomType(@RequestParam Map<String, Object> params, HttpServletRequest request)
			throws Exception {
		List<RoomGroupEntity> roomGroups = new ArrayList<RoomGroupEntity>();
		int total = 0;
		Map<String, Object> map = ControllerTool.mapFilter(params);
//		if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
//			map.put("dataAuth", ControllerTool.getAreasInSql("rg.interrogate_area_id",
//					ControllerTool.getSessionInfo(request).getCurrentAndSubArea()));
//			roomGroups = roomGroupService.list(map);
//			total = this.roomGroupService.count(map);
//		}
		if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
			// 公安领导与管理员
			params.put("dataAuth", ControllerTool.getOrgsInSql("ia.organization_id",
					ControllerTool.getSessionInfo(request).getCurrentAndSubOrg()));
			
			roomGroups = roomGroupService.list(map);
			total = this.roomGroupService.count(map);
		} 
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", total);
		result.put("rows", roomGroups);
		return result;
	}

	@RequestMapping(value = "/groupadd")
	@ResponseBody
	public MessageEntity addRoomType(@RequestBody RoomGroupForm form) throws Exception {
		System.out.println("++++++++add++++++RoomTypeForm=" + form);
		RoomGroupEntity entity = new RoomGroupEntity();
		entity.setId(form.getId());
		entity.setName(form.getName());
		entity.setInterrogateAreaId(form.getInterrogateAreaId());
		try {
			roomGroupService.insert(entity);
			//this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "添加功能室" + entity, "system", true);
		} catch (Exception e) {
			logger.error("Error on adding roomgroup!", e);
			//this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "添加功能室" + entity, "system", false);
			return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("添加失败!");
		}
		return new MessageEntity().addCode(1).addIsError(false).addTitle("提醒").addContent("添加成功!");
	}

	@RequestMapping(value = "/groupdelete")
	@ResponseBody
	public MessageEntity removeRoomType(@RequestBody RoomGroupForm form ,HttpServletRequest request) {
		System.out.println("++++++++remove++++++id=  " + form.getId());
		try {
			roomGroupService.delete(form.getId());
			this.operLogService.insertLog(OperLogEntity.DELETE_TYPE, "删除功能室" + form.getId(), ControllerTool.getUser(request).getLoginName(), true,"办案场所");
		} catch (Exception e) {
			logger.error("Error on deleting  roomgroup!", e);
			this.operLogService.insertLog(OperLogEntity.DELETE_TYPE, "删除功能室类型" + form.getId(), ControllerTool.getUser(request).getLoginName(), false,"办案场所");
			return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("删除失败!");
		}
		return new MessageEntity().addCode(1).addIsError(false).addTitle("提醒").addContent("删除成功!");
	}



	@RequestMapping(value = "/groupedit")
	@ResponseBody
	public MessageEntity editRoomType(@RequestBody RoomGroupForm form,HttpServletRequest request) throws Exception {
		logger.info("++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		RoomGroupEntity entity = new RoomGroupEntity();
		entity.setId(form.getId());
		entity.setName(form.getName());
		entity.setInterrogateAreaId(form.getInterrogateAreaId());
		try {
			roomGroupService.update(entity);
			this.operLogService.insertLog(OperLogEntity.EDIT_TYPE, "修改功能室类型" + entity, ControllerTool.getUser(request).getLoginName(), true,"办案场所");
		} catch (Exception e) {
			logger.error("Error on editing roomtype!", e);
			this.operLogService.insertLog(OperLogEntity.EDIT_TYPE, "修改功能室类型" + entity, ControllerTool.getUser(request).getLoginName(), false,"办案场所");
			return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("修改失败!");
		}
		return new MessageEntity().addCode(1).addIsError(false).addTitle("提醒").addContent("修改成功!");
	}
}
