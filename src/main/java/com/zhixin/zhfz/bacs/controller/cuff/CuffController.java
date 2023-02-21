package com.zhixin.zhfz.bacs.controller.cuff;

import com.zhixin.zhfz.bacs.entity.CuffEntity;
import com.zhixin.zhfz.bacs.form.CuffForm;
import com.zhixin.zhfz.bacs.services.cuff.ICuffService;
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

/*
 * 手环
 * */
@Controller
@RequestMapping("/zhfz/bacs/cuff")
public class CuffController {
	private static Logger logger = LoggerFactory.getLogger(CuffController.class);
	@Autowired
	private ICuffService cuffService;
	@Autowired
	private IOperLogService operLogService;



	// 查询
	@RequestMapping(value = "/list")
	@ResponseBody
	public Map<String, Object> list(@RequestParam Map<String, Object> params, HttpServletRequest request) throws Exception {
		List<CuffEntity> datas=new ArrayList<CuffEntity>();
		int total=0;
		Map<String, Object> map = ControllerTool.mapFilter(params);
		logger.info(map.toString());
		String status=(String)map.get("status");
		System.err.println("======================"+status);
		if(status=="" ||status==null||"#".equals(status)){
			map.put("status", "");
		}
		if(status!=null && "0".equals(status) &&!"".equals(status)){
			map.put("status", "0");
		}
		
		if(status!=null && "1".equals(status) &&!"".equals(status)){
			map.put("status", "1");
		}
		if(status!=null && "2".equals(status) &&!"".equals(status)){
			map.put("status", "2");
		}

		/*if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
			map.put("dataAuth", ControllerTool.getAreasInSql("c.area_id",
					ControllerTool.getSessionInfo(request).getCurrentAndSubArea()));
			datas = this.cuffService.list(map);
			total=this.cuffService.count(map);
		}*/

		boolean flag=true;
		SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
		if (RoleEntity.DATA_AUTH_SELFAREA == (ControllerTool.getRoleDataAuthForBacsConf(request))) {
			// 本办案场所
			map.put("dataAuth", "c.area_id=" + ControllerTool.getCurrentAreaID(request));
		} else if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuthForBacsConf(request))) {
			// 本办案场所及下级办案场所
			map.put("dataAuth", "c.area_id " + sessionInfo.getCurrentAndSubAreaInStr());
		} else if (RoleEntity.DATA_AUTH_UP_CASCADEAREA == (ControllerTool.getRoleDataAuthForBacsConf(request))) {
			// 上级办案场所及其下级办案场所
			map.put("dataAuth", "c.area_id " + sessionInfo.getSuperAndSubAreaInStr());
		} else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuthForBacsConf(request))) {
			// 本部门及下级部门
			map.put("dataAuth","c.op_pid like " + sessionInfo.getCurrentAndSubOrgPid());
		} else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuthForBacsConf(request))) {
			// 上级部门及下级部门
			map.put("dataAuth","c.op_pid like " + sessionInfo.getSuperAndSubOrgPid());
		} else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuthForBacsConf(request))) {
			// 本部门
			map.put("dataAuth","c.op_pid = " + sessionInfo.getCurrentOrgPid());
		} else if(RoleEntity.DATA_AUTH_FULL == (ControllerTool.getRoleDataAuthForBacsConf(request))){
			//全部
			flag=true;
		}else if(RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuthForBacsConf(request))){
			//本人
			map.put("dataAuth","c.op_user_id = " + ControllerTool.getUserID(request));
		}
		if (flag){
			datas = this.cuffService.list(map);
			total=this.cuffService.count(map);
		}

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", total);
		result.put("rows", datas);
		return result;
	}

	// 删除
	@SuppressWarnings("unused")
	@RequestMapping(value = "/remove")
	@ResponseBody
	public MessageEntity remove(@RequestBody IDForm form, HttpServletRequest request) {
		logger.info("++++++++remove++++++id=  " + form.getId());
		int sHId = Integer.valueOf(form.getId());
		Integer status = null;
		try {
			this.cuffService.deleteCuff(form.getLongID());
			this.operLogService.insertLog(OperLogEntity.DELETE_TYPE, "手环删除日志" + form.getLongID(), ControllerTool.getUser(request).getLoginName(), true,"办案场所");
		} catch (Exception e) {
			logger.error("Error on deleting operlog!", e);
			return new MessageEntity().addCode(1).addIsError(true).addTitle("Error").addContent("删除失败!");
		}
		return new MessageEntity().addCode(1).addIsError(false).addTitle("Message").addContent("删除成功!");
	}

	// 添加
	@RequestMapping(value = "/add")
	@ResponseBody
	public MessageEntity add(@RequestBody CuffForm form, HttpServletRequest request) {
		CuffEntity entity = new CuffEntity();
		entity.setCuffNo(form.getCuffNo());
		entity.setIcNo(form.getIcNo());
		entity.setInterrogateAreaId(form.getInterrogateAreaId());
		entity.setType(form.getType());
		entity.setStatus(form.getStatus());
		entity.setHeartNo(form.getHeartNo());
		entity.setOp_Pid(ControllerTool.getSessionInfo(request).getCurrentOrg().getPid());
		entity.setOp_User_Id(ControllerTool.getSessionInfo(request).getUser().getId());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cuffNo", form.getCuffNo());
		map.put("IcfNo", form.getIcNo());
		CuffEntity	entity1 = null;
		CuffEntity	entity2 = null;
		try {
			entity1 = cuffService.getCuffByCuffNo(map);
			entity2 = cuffService.getCuffByNo(map);

		} catch (Exception e) {
			e.printStackTrace();
		}
		if(entity1!=null||entity2!=null){

			return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("数据重复!");

		}
		this.cuffService.insert(entity);
		this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "手环添加日志" + entity.getId(), ControllerTool.getUser(request).getLoginName(),true,"办案场所");
		return new MessageEntity().addCode(1).addIsError(false).addTitle("提示").addContent("添加成功!");

	}

	// 修改
	@RequestMapping(value = "/edit")
	@ResponseBody
	public MessageEntity edit(@RequestBody CuffForm form, HttpServletRequest request, HttpServletResponse response) {
		CuffEntity entity = new CuffEntity();
		entity.setId(form.getId());
		entity.setIcNo(form.getIcNo());
		entity.setCuffNo(form.getCuffNo());
		entity.setInterrogateAreaId(form.getInterrogateAreaId());
		entity.setStatus(form.getStatus());
		entity.setType(form.getType());
		entity.setHeartNo(form.getHeartNo());
		try {
			this.cuffService.update(entity);
			this.operLogService.insertLog(OperLogEntity.EDIT_TYPE, "手环修改日志" + entity.getId(), ControllerTool.getUser(request).getLoginName(), true,"办案场所");

		} catch (Exception e) {
			logger.error("Error on edit Cuff!", e);
			return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("修改失败!");
		}
		return new MessageEntity().addCode(1).addIsError(false).addTitle("提示").addContent("修改成功!");
	}
	
	@SuppressWarnings({ "rawtypes", "unused" })
	@RequestMapping(value = "/areaCombobox")
	@ResponseBody
	public List areaCombobox(){
		List<CuffEntity>list=new ArrayList<CuffEntity>();
		list=cuffService.queryArea();
		for(int i = 0 ;i<list.size(); i++){
			CuffEntity obj=list.get(i);
		}
		return list;
	}

	@RequestMapping(value = "/refrshCuff")
	@ResponseBody
	public MessageEntity refrshCuff(){
		Map<String, String> cuffMap = new HashMap<>();
		try {
			List<CuffEntity> cuffs = cuffService.listAll();
			if (cuffs!=null){
				for (CuffEntity cuff : cuffs){
					cuffMap.put(cuff.getIcNo(), cuff.getCuffNo());
				}
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("刷新手环对应关系失败!");
		}
		return new MessageEntity().addCode(1).addIsError(false).addCallbackData(cuffMap).addTitle("提示").addContent("刷新手环对应关系成功!");
	}
	
	@RequestMapping(value = "/getCuffByCuffNo")
	@ResponseBody
	public MessageEntity getCuffByCuffNo(@RequestBody CuffForm form, HttpServletRequest request) throws Exception {
		logger.info("++++++++++++++++++++++++++getCuffByCuffNo++++++++++++++++++++++++++++++form= " + form);
		CuffEntity entity = new CuffEntity();
		try {
			String cuffNo = form.getCuffNo();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("cuffNo", cuffNo);

			entity = cuffService.getCuffByCuffNo(map);

		} catch (Exception e) {
			return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("请求失败!");
		}
		return new MessageEntity().addCode(1).addIsError(false).addTitle("提醒").addContent("请求成功!")
				.addCallbackData(entity);
	}
	@RequestMapping(value = "/getCuffByNoId")
	@ResponseBody
	public MessageEntity getCuffByNoId(String cuffNo, HttpServletRequest request) throws Exception {
		logger.info("++++++++++++++++++++++++++getCuffByCuffNo++++++++++++++++++++++++++++++cuffNo= " + cuffNo);
		CuffEntity entity = new CuffEntity();
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("cuffNo", cuffNo);

			entity = cuffService.getCuffByCuffNo(map);
			System.out.println("/////////////////////////////////"+entity.getStatus());
		} catch (Exception e) {
			return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("请求失败!");
		}
		return new MessageEntity().addCode(1).addIsError(false).addTitle("提醒").addContent("请求成功!")
				.addCallbackData(entity);
	}

	@RequestMapping(value = "/getCuffNoByIcNoAndType")
	@ResponseBody
	public MessageEntity getCuffNoByNoIdAndType(@RequestBody CuffForm form, HttpServletRequest request) throws Exception {
		logger.info("++++++++++++++++++++++++++getCuffNoByIcNoAndType++++++++++++++++++++++++++++++icNo= " + form.getIcNo()+";type= "+form.getType());
		CuffEntity entity = new CuffEntity();
		String cuffTypeNmae="";
		if(form.getType() == 0){
			cuffTypeNmae = "手环";
		}else if(form.getType() == 1){
			cuffTypeNmae = "卡片";
		}
		Map<String, Object> map = new HashMap<String, Object>();
		try {

			map.put("icNo",form.getIcNo());
			map.put("type",form.getType());
			entity = cuffService.getCuffNoByIcNoAndType(map);
			if(entity == null || entity.getId() == null){
				return new MessageEntity().addCode(1).addIsError(true).addTitle("提示").addContent(cuffTypeNmae+"不存在!");
			}else if(entity.getStatus() == 2 ){
				return new MessageEntity().addCode(1).addIsError(true).addTitle("提示").addContent(cuffTypeNmae+"已损坏!");
			}else if(entity.getStatus() == 0 ){
				return new MessageEntity().addCode(1).addIsError(false).addTitle("提示").addContent(cuffTypeNmae+"未绑定!").addCallbackData(entity);
			} else if(entity.getStatus() == 1 ){
				return new MessageEntity().addCode(1).addIsError(false).addTitle("提示").addContent(cuffTypeNmae+"已绑定!").addCallbackData(entity);
			} else if(entity.getStatus() == 3 ){
				return new MessageEntity().addCode(1).addIsError(false).addTitle("提示").addContent(cuffTypeNmae+"已临时出区!").addCallbackData(entity);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("请求失败!"+e);
		}
		return new MessageEntity().addCode(1).addIsError(false).addTitle("提示").addContent("请求成功!")
				.addCallbackData(entity);
	}
}
