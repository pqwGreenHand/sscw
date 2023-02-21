package com.zhixin.zhfz.bacs.controller.cabinetConfig;

import com.zhixin.zhfz.bacs.entity.CabinetConfigDetailEntity;
import com.zhixin.zhfz.bacs.entity.CabinetConfigEntity;
import com.zhixin.zhfz.bacs.form.CabinetConfigForm;
import com.zhixin.zhfz.bacs.services.cabinetConfig.ICabinetConfigDetailService;
import com.zhixin.zhfz.bacs.services.cabinetConfig.ICabinetConfigService;
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
@RequestMapping("/zhfz/bacs/cabinetConfig")
public class CabinetConfigController {

	private static final Logger logger = LoggerFactory.getLogger(CabinetConfigController.class);

	@Autowired
	private ICabinetConfigService cabinetConfigService;

	@Autowired
	private IOperLogService operLogService;
	@Autowired
	private ICabinetConfigDetailService cabinetConfigDetailService;

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

		List<CabinetConfigEntity> list=new ArrayList<CabinetConfigEntity>();
		int total=0;
		SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
		if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuthForBacsConf(request))) {
			// 办案人员-本人
			map.put("dataAuth", " ( bc.op_user_id=" + ControllerTool.getUserID(request) + " or ba.op_user_id="
					+ ControllerTool.getUserID(request) + " ) ");
		} else if (RoleEntity.DATA_AUTH_SELFAREA == (ControllerTool.getRoleDataAuthForBacsConf(request))) {
			// 办案场所-本办案场所
			map.put("dataAuth", " ( ba.id=" + ControllerTool.getCurrentAreaID(request)
			+" or bc.area_id = " + ControllerTool.getCurrentAreaID(request)
			+" ) ");
		} else if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuthForBacsConf(request))) {
			// 管理员 -本办案场所及下级办案场所
			map.put("dataAuth", "( ba.id  " + sessionInfo.getCurrentAndSubAreaInStr()
			+" or bc.area_id " + sessionInfo.getCurrentAndSubAreaInStr()
			+" ) ");
		} else if (RoleEntity.DATA_AUTH_UP_CASCADEAREA == (ControllerTool.getRoleDataAuthForBacsConf(request))) {
			// 上级办案场所及其下级办案场所
			map.put("dataAuth", " ( ba.id  " + sessionInfo.getSuperAndSubAreaInStr()
			+ " or bc.area_id " + sessionInfo.getSuperAndSubAreaInStr()
			+" ) ");
		} else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuthForBacsConf(request))) {
			// 公安领导-本部门及下级部门
			map.put("dataAuth"," ( bc.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()
					+ " or ba.op_pid like " +  sessionInfo.getCurrentAndSubOrgPid()
					+ " ) ");
		} else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuthForBacsConf(request))) {
			// 法制人员-上级部门及下级部门
			map.put("dataAuth"," ( bc.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
					+ " or ba.op_pid like " +  sessionInfo.getSuperAndSubOrgPid()
					+ " ) ");
		} else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuthForBacsConf(request))) {
			// 本部门
			map.put("dataAuth"," ( bc.op_pid = " + sessionInfo.getCurrentOrgPid()
					+ " or ba.op_pid = " +  sessionInfo.getCurrentOrgPid()
					+ " ) ");
		}

		list = this.cabinetConfigService.list(map);
		total = this.cabinetConfigService.count(map);

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", total);
		result.put("rows", list);
		return result;
	}
	@RequestMapping(value = "/add")
	@ResponseBody
	public MessageEntity add(@RequestBody CabinetConfigForm form, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("++++++++add++++++OrganizationForm=" + form.toString());
		int row=form.getRowCount();//hang
		int col= form.getColumnCount();//lie
		int orderType = form.getOrderType();//0横排，1纵向排序
		int ml =form.getMlNo();//mingling
		CabinetConfigEntity entity = new CabinetConfigEntity();
		entity.setId(form.getId());
		entity.setName(form.getName());
		entity.setType(form.getType());
		entity.setIsEnable(1);
		entity.setAreaId(form.getAreaId());
		entity.setIp(form.getIp());
		entity.setPort(form.getPort());
		entity.setGroup(form.getGroup());
		entity.setOpenIp(form.getOpenIp());
		entity.setOrderType(orderType);
		entity.setRowCount(form.getRowCount());
		entity.setColumnCount(form.getColumnCount());
		entity.setOp_Pid(ControllerTool.getSessionInfo(request).getCurrentOrg().getPid());
		entity.setOp_User_Id(ControllerTool.getSessionInfo(request).getUser().getId());
		CabinetConfigDetailEntity detailEntity = new CabinetConfigDetailEntity();
		try {
		cabinetConfigService.insertCabinetConfig(entity);
		System.err.println(entity.getId());
		detailEntity.setConfigId(entity.getId());
//		横向row 列  col行
		int  count=0;
		if(form.getOrderType() ==0){
			for (int i = 0; i < row; i++) {
				for (int j = 0; j < col; j++) {
					count++;
					System.err.println("第"+(i+1)+"行"+"第"+(j+1)+"列----"+count +"--");
					detailEntity.setShowNo((count+"").length()>1?(count+""):("0"+count));
					if (ml==1) {
						detailEntity.setOpenKey((count+"").length()>1?(count+""):("0"+count));
					}else{
						//10转16进制
						detailEntity.setOpenKey(Integer.toHexString(count));
					}
					detailEntity.setRow(i+1);
					detailEntity.setColumn(j+1);
					detailEntity.setType(1);
					cabinetConfigDetailService.Insert(detailEntity);
				}
				
			}
		}else{
//			纵向row
			for (int i = 0; i < col; i++) {
				for (int j = 0; j < row; j++) {
					count++;
					System.err.println("第"+(j+1)+"行"+"第"+(i+1)+"列----"+count +"--");
					detailEntity.setShowNo((count+"").length()>1?(count+""):("0"+count));
					if (ml==1) {
						detailEntity.setOpenKey((count+"").length()>1?(count+""):("0"+count));
					}else{
						//10转16进制
						detailEntity.setOpenKey(Integer.toHexString(count));
					}
					detailEntity.setRow(j+1);
					detailEntity.setColumn(i+1);
					detailEntity.setType(1);
					cabinetConfigDetailService.Insert(detailEntity);
				}
				
			}
		}
			this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "添加柜子信息" + entity, ControllerTool.getLoginName(request), true, "办案场所");

		} catch (Exception e) {
			logger.error("Error on add 添加柜子信息!", e);
			this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "添加柜子信息" + entity, ControllerTool.getLoginName(request), false, "办案场所");
			return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("新增柜子失败!");
		}
		return new MessageEntity().addCode(1).addIsError(false).addTitle("提醒").addContent("新增柜子成功!");
	}

	@RequestMapping(value = "/edit")
	@ResponseBody
	public MessageEntity edit(@RequestBody CabinetConfigForm form, HttpServletRequest request,
			HttpServletResponse response) {
		logger.debug("++++++++edit++++++OrganizationForm=" + form);
		CabinetConfigEntity entity = new CabinetConfigEntity();
		entity.setId(form.getId());
		entity.setName(form.getName());
		entity.setType(form.getType());
		entity.setIsEnable(1);
		entity.setAreaId(form.getAreaId());
		entity.setIp(form.getIp());
		entity.setPort(form.getPort());
		entity.setGroup(form.getGroup());
		entity.setOpenIp(form.getOpenIp());
		entity.setOrderType(form.getOrderType());
		entity.setRowCount(form.getRowCount());
		entity.setColumnCount(form.getColumnCount());
		try {
			cabinetConfigService.update(entity);
			this.operLogService.insertLog(OperLogEntity.EDIT_TYPE, "修改柜子" + entity, ControllerTool.getLoginName(request), true, "办案场所");
		} catch (Exception e) {
			logger.error("Error on edit Organization!", e);
			this.operLogService.insertLog(OperLogEntity.EDIT_TYPE, "修改柜子" + entity, ControllerTool.getLoginName(request), false, "办案场所");
			return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("修改柜子失败!");
		}
		return new MessageEntity().addCode(1).addIsError(false).addTitle("提醒").addContent("修改柜子成功!");
	}

	@RequestMapping(value = "/remove")
	@ResponseBody
	public MessageEntity remove(@RequestBody IDForm form, HttpServletRequest request) {
		logger.debug("++++++++remove++++++id=" + form.getId());
		try {
			cabinetConfigService.delete(form.getIntID());
			cabinetConfigDetailService.delete(form.getIntID());
			this.operLogService.insertLog(OperLogEntity.DELETE_TYPE, "删除柜子" + form, ControllerTool.getLoginName(request), true, "办案场所");
		} catch (Exception e) {
			logger.error("Error on deleting Organization!", e);
			this.operLogService.insertLog(OperLogEntity.DELETE_TYPE, "删除柜子" + form, ControllerTool.getLoginName(request), false, "办案场所");
			return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("删除柜子失败!");
		}
		return new MessageEntity().addCode(1).addIsError(false).addTitle("提醒").addContent("删除柜子成功!");
	}
	public static void main(String[] args) {
		int  count =0;
		int col = 4;//行
		int row =6;//列
		System.err.println("---"+Integer.toHexString(11));
		//横向排序
		for (int i = 0; i < col; i++) {
			for (int j = 0; j < row; j++) {
				count++;
				if((count+"").length()>1){
					System.err.println("第"+(i+1)+"行"+"第"+(j+1)+"列----"+count +"--");
				}else{
					System.err.println("第"+(i+1)+"行"+"第"+(j+1)+"列----"+"0"+count +"--");
					
				}
			}
			
		}
//		纵向row
//		for (int i = 0; i < row; i++) {
//			for (int j = 0; j < col; j++) {
//				count++;
//				System.err.println("第"+(j+1)+"行"+"第"+(i+1)+"列----"+count +"--");
//			}
//			
//		}
}
}