package com.zhixin.zhfz.sacw.controller.involved;


import com.zhixin.zhfz.common.entity.*;
import com.zhixin.zhfz.common.form.IDForm;
import com.zhixin.zhfz.common.services.Icase.ICaseService;
import com.zhixin.zhfz.common.services.operLog.IOperLogService;
import com.zhixin.zhfz.common.services.organiztion.IOrganizationService;
import com.zhixin.zhfz.common.utils.ControllerTool;
import com.zhixin.zhfz.sacw.common.Utils;
import com.zhixin.zhfz.sacw.entity.InputEntity;
import com.zhixin.zhfz.sacw.entity.InputRecordEntity;
import com.zhixin.zhfz.sacw.entity.InvolvedEntity;
import com.zhixin.zhfz.sacw.entity.WareHouseEntity;
import com.zhixin.zhfz.sacw.form.InputForm;
import com.zhixin.zhfz.sacw.form.InvolvedForm;
import com.zhixin.zhfz.sacw.services.input.IInputService;
import com.zhixin.zhfz.sacw.services.involved.IinvolvedService;
import com.zhixin.zhfz.sacw.services.warehouse.IWareHouseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/zhfz/sacw/involved")
public class InvolvedController {

    private static final Logger logger = LoggerFactory.getLogger(InvolvedController.class);

    @Autowired
    private IinvolvedService involvedService;


    @Autowired
    private IOperLogService operLogService;

    @Autowired
    private IWareHouseService wareHouseService;


    @Autowired
    private IInputService service;

    @Autowired
    private IOrganizationService organizationService;

	/*@Autowired
	private IJzCaseService jzCaseService;*/


    @RequestMapping("/getMaterials")
    @ResponseBody
    public Map<String, Object> getMaterials(@RequestParam Map<String, Object> param, HttpServletRequest request) throws Exception {
        List<InvolvedEntity> list = new ArrayList<>();
        Map<String, Object> result = new HashMap<String, Object>();
        int count = 0;
        boolean flag = true;
        //map.put("usePage", true);
        Map<String, Object> map = ControllerTool.mapFilter(param);
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 办案人员-本人
            map.put("dataAuth", "(locate('," + ControllerTool.getUserID(request) + ",', lawcase.xbmj_ids) or  lawcase.zbmj_id="
                    + ControllerTool.getUserID(request) + " or involved.register_user_id = " + ControllerTool.getUserID(request) + ")");
        } else if (RoleEntity.DATA_AUTH_SELFAREA == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 办案场所-本办案场所
            map.put("dataAuth", " involved.warehouse_id=" + ControllerTool.getCurrentWarehouseID(request));
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 本办案场所及下级办案场所
            map.put("dataAuth", " involved.warehouse_id " + sessionInfo.getCurrentAndSubWarehouseInStr());
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEAREA == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 上级办案场所及其下级办案场所
            map.put("dataAuth", " involved.warehouse_id " + sessionInfo.getSuperAndSubWarehouseInStr());
        } else if (RoleEntity.DATA_AUTH_FULL == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 全部
            map.put("dataAuth", "");
        } else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 本部门
            map.put("dataAuth", "cc.p_id like " + sessionInfo.getCurrentOrgPid());
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 法制人员-上级部门及其下级部门
            map.put("dataAuth", "cc.p_id like  " + sessionInfo.getSuperAndSubOrgPid());
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 公安领导-本部门及下级部门
            map.put("dataAuth", "cc.p_id like " + sessionInfo.getCurrentAndSubOrgPid());
        } else {
            flag = false;
            // return null
        }
        if (flag) {
            list = involvedService.getMaterials(map);
            if (list != null && list.size() > 0) {
                for (InvolvedEntity involvedEntity : list) {
                    Map<String, Object> mapParam = new HashMap<>();
                    mapParam.put("involvedId", involvedEntity.getId());
                    try {
                        List<InvolvedEntity> listRecord = involvedService.listLastRecord(mapParam);
                        if (listRecord != null && listRecord.size() > 0) {
                            involvedEntity.setWarehouseId(listRecord.get(0).getWarehouseId());
                            involvedEntity.setLocationId(listRecord.get(0).getLocationId());
                            involvedEntity.setShelfId(listRecord.get(0).getShelfId());
                            involvedEntity.setLocation(listRecord.get(0).getLocation());
                            involvedEntity.setRecordId(listRecord.get(0).getRecordId());
                            involvedEntity.setUuid(listRecord.get(0).getUuid());
                        }
                    } catch (Exception ex) {
                        logger.info("查询位置异常：" + ex.getMessage());
                    }

                }
            }

            count = involvedService.countMaterials(map);
        }
        result.put("total", count);
        result.put("rows", list);
        return result;
    }

    @RequestMapping("/getRecordByInvId")
    @ResponseBody
    public Map<String, Object> getRecordByInvId(@RequestParam Map<String, Object> param) throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        Map<String, Object> map = ControllerTool.mapFilter(param);
        result.put("total", involvedService.countRecordByInvId(map));
        result.put("rows", involvedService.getRecordByInvId(map));
        return result;
    }

    @RequestMapping("/getInvolvedByInvId")
    @ResponseBody
    public InvolvedEntity getInvolvedByInvId(@RequestParam Long id) throws Exception {
        InvolvedEntity involved = involvedService.getInvolvedById(id);
        return involved;
    }

    @RequestMapping("/del")
    @ResponseBody
    public MessageEntity delInvolvedById(@RequestBody IDForm form, HttpServletRequest request) {
        System.out.println("++++++++delInvolvedById++++++id=  " + form.getId());
        try {
            involvedService.deleteInvolved(form.getIntID());

            this.operLogService.insertLog(OperLogEntity.DELETE_TYPE, "删除涉案物品仓库" + form.getId(), ControllerTool.getUser(request).getLoginName(), true, "涉案财物");
        } catch (Exception e) {
            logger.error("Error on deleting user!", e);
            this.operLogService.insertLog(OperLogEntity.DELETE_TYPE, "删除涉案物品仓库" + form.getId(), ControllerTool.getUser(request).getLoginName(), false, "涉案财物");
            return new MessageEntity().addCode(1).addIsError(true).addTitle("Error").addContent("删除涉案物品仓库失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("Message").addContent("删除涉案物品仓库成功!");
    }

    @RequestMapping(value = "/addInv")
    @ResponseBody
    public MessageEntity addInv(@RequestBody InvolvedForm form, HttpServletRequest request) {
        InvolvedEntity entity = new InvolvedEntity();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if (form.getExpired_time() != null && form.getExpired_time() != "") {
                Timestamp ts = new Timestamp(System.currentTimeMillis());
                ts = Timestamp.valueOf(form.getExpired_time().toString());
                entity.setExpired_time(ts);
            } else
                entity.setExpired_time(null);
            entity.setLaw_case_id(form.getLaw_case_id());
            entity.setDetail_count(form.getDetail_count());
            entity.setInvolved_type_id(form.getInvolved_type_id());
            entity.setName(form.getName());
            entity.setUnit(form.getUnit());
            entity.setWeight(form.getWeight());
            entity.setWorth(form.getWorth());
            entity.setOp_pid(ControllerTool.getSessionInfo(request).getCurrentOrg().getPid());
            entity.setInvolved_owner(form.getInvolved_owner());
            entity.setInput_type_id(form.getInput_type_id());
            entity.setDescription(form.getDescription());
            entity.setRegister_user_id(ControllerTool.getUserID(request));
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
            entity.setBarcode(dateFormat.format(new java.util.Date()));
            entity.setNumber(form.getNumber());
            entity.setUuid(Utils.getUUId());
            entity.setWarehouseId(form.getWarehouseId());
            involvedService.addInv(entity);
            this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "添加物品信息:" + entity, "system", true, "涉案财物");
        } catch (Exception e) {
            System.out.println(e);
            this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "添加物品信息:" + entity, "system", false, "涉案财物");
            return new MessageEntity().addCode(1).addIsError(true).addTitle("Error").addContent("添加物品信息失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("Message").addContent("添加物品信息成功!");
    }

    @RequestMapping(value = "/update")
    @ResponseBody
    public MessageEntity update(@RequestBody InvolvedForm form, HttpServletRequest request) {
        InvolvedEntity entity = new InvolvedEntity();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if (form.getExpired_time() != null && form.getExpired_time() != "") {
                Timestamp ts = new Timestamp(System.currentTimeMillis());
                ts = Timestamp.valueOf(form.getExpired_time().toString());
                entity.setExpired_time(ts);
            } else
                entity.setExpired_time(null);
            entity.setId(form.getId());
            entity.setLaw_case_id(form.getLaw_case_id());
            entity.setDetail_count(form.getDetail_count());
            entity.setInvolved_type_id(form.getInvolved_type_id());
            entity.setName(form.getName());
            entity.setUnit(form.getUnit());
            entity.setWeight(form.getWeight());
            entity.setWorth(form.getWorth());
            entity.setInvolved_owner(form.getInvolved_owner());
            entity.setInput_type_id(form.getInput_type_id());
            entity.setDescription(form.getDescription());
            entity.setNumber(form.getNumber());
            entity.setWarehouseId(form.getWarehouseId());
			/*Map<String,Object> map=new HashMap<>();
			map.put("id",form.getLaw_case_id());
			List<CaseEntity> lawCaseEntities=lawCaseService.listCaseById(map);
			if(lawCaseEntities!=null&&lawCaseEntities.size()>0){
				Map<String,Object> map1=new HashMap<>();
				map1.put("orgId",lawCaseEntities.get(0).getZbdwId());
				List<WareHouseEntity> allWareHouse = wareHouseService.findAllWareHouse(map1);
				entity.setWarehouseId(allWareHouse.get(0).getId());

			}*/
            involvedService.update(entity);
            this.operLogService.insertLog(OperLogEntity.EDIT_TYPE, "修改物品信息:" + entity, "system", true, "涉案财物");
        } catch (Exception e) {
            System.out.println(e);
            this.operLogService.insertLog(OperLogEntity.EDIT_TYPE, "修改物品信息:" + entity, "system", false, "涉案财物");
            return new MessageEntity().addCode(1).addIsError(true).addTitle("Error").addContent("修改物品信息失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("Message").addContent("修改物品信息成功!");
    }

    @RequestMapping("/getRecordPhoto")
    @ResponseBody
    public List<InvolvedEntity> listInRecordPhoto(@RequestParam Map<String, Object> param) throws Exception {
        Map<String, Object> map = ControllerTool.mapFilter(param);
        List<InvolvedEntity> list = involvedService.listInRecordPhoto(map);
        return list;
    }

    @RequestMapping(value = "/changePosition")
    @ResponseBody
    public MessageEntity changePosition(@RequestBody InputForm form, HttpServletRequest request,
                                        HttpServletResponse response) throws Exception {
        logger.debug("changePosition=" + form);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("recordId", form.getRecordId());//记录的ID
            map.put("warehouseId", form.getWarehouseId());
            map.put("locationId", form.getLocationId());
            map.put("shelfId", form.getShelfId());

            involvedService.changePosition(map);
        } catch (Exception e) {
            return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("修改位置失败！");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("提醒").addContent("修改位置成功!");
    }


    /**
     * 返库
     *
     * @param
     * @throws Exception
     */

    @RequestMapping(value = "/returninput")
    @ResponseBody


    public MessageEntity returnInput(@RequestBody InputForm form, HttpServletRequest request) {
        try {
            //1.更新物品的状态
            Map<String, Object> map = new HashMap<>();
            map.put("status", 2);//返回涉案财物管理中心，状态为2
            map.put("id", form.getInvolvedId());
            involvedService.updateStatusById(map);//只更新状态

            //2.插入入库记录
            InputEntity entity = new InputEntity();
            Map<String, Object> mapInvolved = new HashMap<>();
            mapInvolved.put("id", form.getInvolvedId());
            List<InputRecordEntity> inRecordEntities = involvedService.listInRecordById(mapInvolved);
            if (inRecordEntities != null && inRecordEntities.size() > 0) {
                entity.setInvolvedId(form.getInvolvedId());
                entity.setRegisterUserId(inRecordEntities.get(0).getRegisterUserId());
                if (inRecordEntities.get(0).getShelfId() != null) {
                    entity.setShelfId(inRecordEntities.get(0).getShelfId());
                }
                entity.setWarehouseId(inRecordEntities.get(0).getWarehouseId());
                entity.setLocationId(inRecordEntities.get(0).getLocationId());
                entity.setLawCaseId(inRecordEntities.get(0).getLawCaseId());
                entity.setInvolvedTypeId(inRecordEntities.get(0).getInvolvedTypeId());
                entity.setInputTypeId(10);
                entity.setPoliceId1(form.getPoliceId());

                //通过移交民警ID找到移交民警所在的单位
                List<OrganizationEntity> userOrganizationEntities = organizationService.getOrgByUserId(form.getPoliceId());
                if (userOrganizationEntities != null && userOrganizationEntities.size() > 0) {
                    entity.setOrganizationId(userOrganizationEntities.get(0).getId());
                }
                entity.setWarehouseId(inRecordEntities.get(0).getWarehouseId());
                service.insert(entity);
            }

        } catch (Exception e) {
            this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "返库操作", "system", false, "涉案财物");
            return new MessageEntity().addCode(1).addIsError(true).addTitle("Message")
                    .addContent("返库失败：" + e.getMessage());
        }

        return new MessageEntity().addCode(1).addIsError(false).addTitle("Error")
                .addContent("返库成功");
    }

    /**
     * 查所有警综平台的涉案物品（闭环平台）
     *
     * @param param
     * @return
     * @throws Exception
     *//*

	@RequestMapping(value = "/jzBihuanInvolved")
	@ResponseBody
	public List<JZInvolvedEntity> jzBihuanInvolved(@RequestParam Map<String, Object> param, HttpServletRequest request,
                                                   HttpServletResponse response) throws Exception {
		List<JZInvolvedEntity> list =new ArrayList<>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("caseNo",request.getParameter("caseNo"));
		list = jzCaseService.listExhibitGoods(map);
		return list;
	}
*/


}
