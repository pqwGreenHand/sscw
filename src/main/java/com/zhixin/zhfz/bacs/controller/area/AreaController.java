package com.zhixin.zhfz.bacs.controller.area;


import com.zhixin.zhfz.bacs.entity.*;
import com.zhixin.zhfz.bacs.form.AreaForm;
import com.zhixin.zhfz.bacs.form.RoomForm;
import com.zhixin.zhfz.bacs.services.area.IAreaService;
import com.zhixin.zhfz.bacs.services.room.IRoomTypeService;
import com.zhixin.zhfz.bacs.services.statistics.IStatisticsService;
import com.zhixin.zhfz.common.entity.MessageEntity;
import com.zhixin.zhfz.common.entity.OperLogEntity;
import com.zhixin.zhfz.common.entity.RoleEntity;
import com.zhixin.zhfz.common.entity.SessionInfo;
import com.zhixin.zhfz.common.form.IDForm;
import com.zhixin.zhfz.common.services.operLog.IOperLogService;
import com.zhixin.zhfz.common.utils.ControllerTool;
import net.sf.json.JSONObject;
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
import java.util.*;

@Controller
@RequestMapping("/zhfz/bacs/area")
public class AreaController {

	private static Logger logger = LoggerFactory.getLogger(AreaController.class);

	@Autowired
	private IAreaService areaService;
	@Autowired
	private IOperLogService operLogService;
	@Autowired
	private IRoomTypeService roomTypeService;

	@Autowired
	private IStatisticsService statisticsService;
	
	@RequestMapping(value = "/cobolist")
	@ResponseBody
	public List<AreaEntity> list(@RequestParam(required=false) Map<String, Object> params,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		return areaService.listAllArea(params);
	}
	@RequestMapping(value = "/listAllArea")
	@ResponseBody
	public Map<String, Object> listAllArea(@RequestParam Map<String, Object> pageMap, HttpServletRequest request,
                                           HttpServletResponse response) throws Exception {
		logger.debug("=======================EmployeeController.listAllArea=======================");
		Map<String, Object> map = ControllerTool.mapFilter(pageMap);
		//map.put("usePage", true);
		List<AreaEntity> list=new ArrayList<AreaEntity>();
		int total=0;
		boolean flag=true;
		SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
		if (RoleEntity.DATA_AUTH_SELFAREA == (ControllerTool.getRoleDataAuthForBacsConf(request))) {
			// 本办案场所
			map.put("dataAuth", "ia.id=" + ControllerTool.getCurrentAreaID(request));
		} else if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuthForBacsConf(request))) {
			// 本办案场所及下级办案场所
			map.put("dataAuth", "ia.id " + sessionInfo.getCurrentAndSubAreaInStr());
		} else if (RoleEntity.DATA_AUTH_UP_CASCADEAREA == (ControllerTool.getRoleDataAuthForBacsConf(request))) {
			// 上级办案场所及其下级办案场所
			map.put("dataAuth", "ia.id " + sessionInfo.getSuperAndSubAreaInStr());
		} else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuthForBacsConf(request))) {
			// 本部门及下级部门
			map.put("dataAuth","ia.op_pid like " + sessionInfo.getCurrentAndSubOrgPid());
		} else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuthForBacsConf(request))) {
			// 上级部门及下级部门
			map.put("dataAuth","ia.op_pid like " + sessionInfo.getSuperAndSubOrgPid());
		} else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuthForBacsConf(request))) {
			// 本部门
			map.put("dataAuth","ia.op_pid = " + sessionInfo.getCurrentOrgPid());
		} else if(RoleEntity.DATA_AUTH_FULL == (ControllerTool.getRoleDataAuthForBacsConf(request))){
			//全部
			flag = true;
		}else if(RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuthForBacsConf(request))){
			//本人
			map.put("dataAuth","ia.op_user_id = " + ControllerTool.getUserID(request));
		}
		if (flag){
			list = this.areaService.listAllArea(map);
			total=this.areaService.count(map);
		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total",total);
		result.put("rows", list);
		return result;
	}

	@RequestMapping(value = "/addArea")
	@ResponseBody
	public MessageEntity addArae(@RequestBody AreaForm form, HttpServletRequest request,
								 HttpServletResponse response) {
		logger.info("++++++++add++++++AreaForm=" + form);
		AreaEntity entity = new AreaEntity();
		entity.setName(form.getName());
		entity.setType(form.getType());
		entity.setDescription(form.getDescription());
		entity.setOrganizationId(form.getOrganizationId());
		entity.setAddress(form.getAddress());
		entity.setPostcode(form.getPostcode());
		entity.setTelephone(form.getTelephone());
		entity.setCreatedTime(new Date());
		entity.setUpdatedTime(new Date());
		entity.setOp_Pid(ControllerTool.getSessionInfo(request).getCurrentOrg().getPid());
		entity.setOp_User_Id(ControllerTool.getSessionInfo(request).getUser().getId());
		try {
			if( areaService.listAreaByOrgStr(form.getOrganizationId().toString()).size()>0){
				return new MessageEntity().addCode(1).addIsError(true).addTitle("Error").addContent("本部门已有办案场所!");
			}else {
				areaService.insertArea(entity);
				this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "添加办案场所" + entity, ControllerTool.getUser(request).getLoginName(), true, "办案场所");
			}
			} catch (Exception e) {
			logger.error("Error on add insertArea!", e);
			this.operLogService.insertLog(OperLogEntity.INSERT_TYPE ,  "添加办案场所" + entity, ControllerTool.getUser(request).getLoginName(), false,"办案场所");
			return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("添加失败!");
		}
		return new MessageEntity().addCode(1).addIsError(false).addTitle("通知").addContent("添加成功!");
	}

	@RequestMapping(value = "/editArea")
	@ResponseBody
	public MessageEntity editEnterprise(@RequestBody AreaForm form, HttpServletRequest request,
                                        HttpServletResponse response) {
		logger.info("++++++++edit++++++AreaForm=" + form);
		AreaEntity entity = new AreaEntity();
		entity.setId(form.getId());
		entity.setName(form.getName());
		entity.setType(form.getType());
		entity.setDescription(form.getDescription());
		entity.setOrganizationId(form.getOrganizationId());
		entity.setAddress(form.getAddress());
		entity.setPostcode(form.getPostcode());
		entity.setCreatedTime(new Date());
		entity.setUpdatedTime(new Date());
		entity.setTelephone(form.getTelephone());
		try {
			areaService.updateArea(entity);
			this.operLogService.insertLog(OperLogEntity.EDIT_TYPE,  "修改办案场所" + entity, "system", true,"办案场所");
		} catch (Exception e) {
			logger.error("Error on edit updateArea!", e);
			this.operLogService.insertLog(OperLogEntity.EDIT_TYPE ,  "修改办案场所" + entity, "system", false,"办案场所");
			return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("修改失败!");
		}
		return new MessageEntity().addCode(1).addIsError(false).addTitle("通知").addContent("修改成功!");
	}

	@RequestMapping(value = "/removeArea")
	@ResponseBody
	public MessageEntity removeEnterprise(@RequestBody IDForm form, HttpServletRequest request) {
		logger.debug("++++++++remove++++++id=" + form.getId());
		try {
			List<RoomEntity> countRoomByArea = areaService.countRoomByArea(Integer.valueOf(form.getId()));
			if(countRoomByArea.size() == 0 ){
				areaService.deleteAreaOnly(form.getLongID());
			}else{
				areaService.deleteArea(form.getLongID());
			}

			this.operLogService.insertLog(OperLogEntity.DELETE_TYPE ,"删除办案场所" + form.getId(), ControllerTool.getUser(request).getLoginName(), true,"办案场所");
		} catch (Exception e) {
			logger.error("Error on deleting Enterprise!", e);
			this.operLogService.insertLog(OperLogEntity.DELETE_TYPE , "删除办案场所" + form.getId(), ControllerTool.getUser(request).getLoginName(), false,"办案场所");
			return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("删除失败!");
		}
		return new MessageEntity().addCode(1).addIsError(false).addTitle("通知").addContent("删除成功!");
	}

	@RequestMapping(value = "/listRoom")
	@ResponseBody
	public Map<String, Object> listRomeByEnpId(@RequestParam Map<String, Object> map,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.debug("++++++++++++++++listRomeByEnpId++++++++++++++++");
		if (map.get("enpId") == null) {
			return null;
		}
		Map<String, Object> map2 = ControllerTool.mapFilter(map);
		List<RoomEntity> list = areaService.listRomeByEnpId(map2);
//		map2.put("usePage", true);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", this.areaService.count1(map2));
		result.put("rows", list);
		return result;
	}

	@RequestMapping(value = "/addRoom")
	@ResponseBody
	public MessageEntity addOrganization(@RequestBody RoomForm form, HttpServletRequest request,
										 HttpServletResponse response) {
		logger.debug("++++++++add++++++EnterpriseForm=" + form);
		RoomEntity entity = new RoomEntity();
		entity.setId(form.getId());
		entity.setName(form.getName());
		entity.setRoomTypeId(form.getRoomTypeId());
		entity.setRoomGroupId(form.getRoomGroupId());
		entity.setDescription(form.getDescription());
		entity.setInterrogateAreaId(form.getInterrogateAreaId());
		entity.setCreatedTime(new Date());
		entity.setUpdatedTime(new Date());
		entity.setIps(form.getIps());
		entity.setIsActive(form.getIsActive());
		entity.setVolume(form.getVolume());
		entity.setLedAddress(form.getLedAddress());
		entity.setTriggerNo(form.getTriggerNo());
		entity.setAxis(form.getAxis());
		entity.setOpPid(ControllerTool.getSessionInfo(request).getCurrentOrg().getPid());
		entity.setOpUserId(ControllerTool.getSessionInfo(request).getUser().getId());
		try {
			areaService.insertRoom(entity);
			this.operLogService.insertLog(OperLogEntity.DELETE_TYPE,"添加办案场所内的功能室" + entity, ControllerTool.getUser(request).getLoginName(), true,"办案场所");
			// 新建功能室时，自动复制带入该办案场所ID
			/*ConfInstanceEntity confinst = this.stockConfigService.getLatelyConfInstance(form.getOrgenpId());
			if (confinst != null) {
				confinst.setId(null);
				confinst.setInstName(form.getOrgname() + "盘点配置");
				confinst.setOrgId(entity.getId());
				this.stockConfigService.insertConfInstance(confinst);
			}
			List<ImportDataEntity> importlist = this.importConfigService.getLatelyImportData(form.getOrgenpId());
			if (importlist != null && importlist.size() > 0) {
				for (ImportDataEntity im : importlist) {
					im.setId(null);
					im.setOrgID(entity.getId());
				}
			}*/
		} catch (Exception e) {
			logger.error("Error on add enterprise!", e);
		//	this.operLogService.insertLog( "添加办案场所内的功能室" + entity, "system", false);
			return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("添加失败!");
		}
		return new MessageEntity().addCode(1).addIsError(false).addTitle("通知").addContent("添加成功!");
	}

	@RequestMapping(value = "/editRoom")
	@ResponseBody
	public MessageEntity editRoom(@RequestBody RoomForm form, HttpServletRequest request,
                                  HttpServletResponse response) {
		logger.debug("++++++++edit++++++RoomForm=" + form);
		RoomEntity entity = new RoomEntity();
		entity.setId(form.getId());
		entity.setName(form.getName());
		entity.setRoomTypeId(form.getRoomTypeId());
		entity.setRoomGroupId(form.getRoomGroupId());
		entity.setDescription(form.getDescription());
		entity.setInterrogateAreaId(form.getInterrogateAreaId());
		entity.setCreatedTime(new Date());
		entity.setUpdatedTime(new Date());
		entity.setIsActive(form.getIsActive());
		entity.setIps(form.getIps());
		entity.setVolume(form.getVolume());
		entity.setLedAddress(form.getLedAddress());
		entity.setTriggerNo(form.getTriggerNo());
		entity.setAxis(form.getAxis());
		try {
			areaService.updateRoom(entity);
		//	this.operLogService.insertLog( "修改办案场所内的功能室" + entity, "system", true);
		} catch (Exception e) {
			logger.error("Error on edit Enterprise!", e);
		//	this.operLogService.insertLog("修改办案场所内的功能室" + entity, "system", false);
			return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("修改失败!");
		}
		return new MessageEntity().addCode(1).addIsError(false).addTitle("通知").addContent("修改成功!");
	}

	@RequestMapping(value = "/removeRoom")
	@ResponseBody
	public MessageEntity removeOrganization(@RequestBody IDForm form, HttpServletRequest request) {
		logger.debug("++++++++remove++++++id=" + form.getId());
		try {
			areaService.deleteRoom(form.getLongID());
	//		this.operLogService.insertLog( "删除办案场所内的功能室" + form.getId(), "system", true);
		} catch (Exception e) {
			logger.error("Error on deleting Organization!", e);
		//	this.operLogService.insertLog( "删除办案场所内的功能室" + form.getId(), "system", false);
			return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("删除失败!");
		}
		return new MessageEntity().addCode(1).addIsError(false).addTitle("通知").addContent("删除成功!");
	}

    //新增编辑room时选择room类型的下拉菜单
	@RequestMapping(value = "/roomtype")
	@ResponseBody
	public List<ComboboxEntity> getroomtype() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		List<RoomTypeEntity> list = this.roomTypeService.listAllroomType(params);
		logger.info("++++++++getroomtype++++++getroomtype=" + list.size());
		List<ComboboxEntity> ll = new ArrayList<ComboboxEntity>();
		for (RoomTypeEntity s : list) {
			String sid = String.valueOf(s.getId());
			ComboboxEntity b = new ComboboxEntity(sid,s.getName());
			ll.add(b);
		}
		return ll;
	}

	
	@RequestMapping(value = "/saveBatchRoom")
	@ResponseBody
	public MessageEntity saveBatchRoom(@RequestBody JSONObject json, HttpServletRequest request,
                                       HttpServletResponse response) {
		logger.info("++++++++add++++++saveBatchRoom=" + json);

		Integer areaId=json.getInt("areaId");
		
		Iterator<String> its=json.keys();
		
		Map<Integer, Integer> param=new HashMap<Integer, Integer>();
		
		while(its.hasNext()){
			String key=its.next();
			if(!"areaId".equals(key) ){

				param.put(Integer.valueOf(key), json.getInt(key));
			}
			
		}
		areaService.saveBatchRoom( areaId,param);
		

//		
		
		
		return new MessageEntity().addCode(1).addIsError(false).addTitle("通知").addContent("添加成功!");
	}



	/**
	 * 办案场所 下拉框
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/comboArea")
	@ResponseBody
	public List<AreaEntity> comboArea(HttpServletRequest request) {
		List<AreaEntity> list = new ArrayList<AreaEntity>();
		if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuth(request))) {
			// 办案人员
			AreaEntity areaEnity = ControllerTool.getCurrentArea(request);
			list.add(areaEnity);
		} else if (RoleEntity.DATA_AUTH_SELFAREA == (ControllerTool.getRoleDataAuth(request))) {
			// 办案场所
			AreaEntity areaEnity = ControllerTool.getCurrentArea(request);
			list.add(areaEnity);
		} else if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
			// 公安领导与管理员
			list = ControllerTool.getSessionInfo(request).getCurrentAndSubArea();
		} else if (RoleEntity.DATA_AUTH_UP_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
			// 法制人员
			list = ControllerTool.getSessionInfo(request).getSuperAndSubArea();
		} else if (RoleEntity.DATA_AUTH_FULL == (ControllerTool.getRoleDataAuth(request))) {
			// system
			Map<String, Object> map = new HashMap<>();
			list = this.areaService.listAllArea(map);
		} else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
			// 公安领导
			list = ControllerTool.getSessionInfo(request).getCurrentAndSubArea();

		}else{

		}
		return list;
	}
	//获取办案场所静态数据
	@RequestMapping(value = "/getAreaManageData")
	@ResponseBody
	public List<AreaManage> getAreaManageData(@RequestParam Map<String, Object> pageMap, HttpServletRequest request,
                                              HttpServletResponse response) throws Exception {
		Map<String, Object> map = ControllerTool.mapFilter(pageMap);
		//map.put("usePage", true);
		List<AreaEntity> list=new ArrayList<AreaEntity>();
		//({"a",0,0,0},{"b",0,0,0})
		List<AreaManage> finalValue = new ArrayList<AreaManage>();
		int total=0;
		boolean flag=true;
		/*if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))){
			map.put("dataAuth", ControllerTool.getOrgsInSql("ia.organization_id",
					ControllerTool.getSessionInfo(request).getCurrentAndSubOrg()));
		} else if (RoleEntity.DATA_AUTH_FULL == (ControllerTool.getRoleDataAuth(request))) {
			// 系统管理员
			flag = true;
		} else {
			flag = false;
			// return null
		}*/
		if (flag){
			list = this.areaService.listAllArea(map);
			total=this.areaService.count(map);
		}
		List<AreaManage> list1 = statisticsService.getOrderCount();
		List<AreaManage> list2 = statisticsService.getInCount();
		List<AreaManage> list3 = statisticsService.getOutCount();
		for( AreaEntity area : list){
			String areaName=area.getName();
			Integer areaid = area.getId();
			if(!areaName.contains("测试")){
				AreaManage areaManage = new AreaManage();
				areaManage.setAreaName(areaName);
				areaManage.setAreaId(areaid);
				//预约人数
				if(list1!=null && list1.size()>0){
					for(AreaManage areaManageOrder : list1){
						if(areaManageOrder.getAreaName()!=null && areaManageOrder.getAreaName()!="" && areaManageOrder.getAreaName().equals(areaManage.getAreaName())){
							if(areaManageOrder.getOrderCount()>0){
								areaManage.setOrderCount(areaManageOrder.getOrderCount());
								areaManage.setAreaId(areaManageOrder.getAreaId());
							}
						}
					}
				}
				//入区人数
				if(list2!=null && list2.size()>0){
					for(AreaManage areaManageIn : list2){
						if(areaManageIn.getAreaName()!=null && areaManageIn.getAreaName()!="" && areaManageIn.getAreaName().equals(areaManage.getAreaName())){
							if(areaManageIn.getInCount()>0){
								areaManage.setInCount(areaManageIn.getInCount());
								areaManage.setAreaId(areaManageIn.getAreaId());
							}
						}
					}
				}
				//出区人数
				if(list3!=null && list3.size()>0){
					for(AreaManage areaManageOut : list3){
						if(areaManageOut.getAreaName()!=null && areaManageOut.getAreaName()!="" && areaManageOut.getAreaName().equals(areaManage.getAreaName())){
							if(areaManageOut.getOutCount()>0){
								areaManage.setOutCount(areaManageOut.getOutCount());
								areaManage.setAreaId(areaManageOut.getAreaId());
							}
						}
					}
				}
				finalValue.add(areaManage);
			}
		}
		System.out.println("办案场所=================="+finalValue);

		return finalValue;
	}
}
