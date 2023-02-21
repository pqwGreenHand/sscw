package com.zhixin.zhfz.sacw.controller.unowner;


import com.zhixin.zhfz.common.entity.MessageEntity;
import com.zhixin.zhfz.common.entity.OperLogEntity;
import com.zhixin.zhfz.common.entity.RoleEntity;
import com.zhixin.zhfz.common.entity.SessionInfo;
import com.zhixin.zhfz.common.form.IDForm;
import com.zhixin.zhfz.common.services.operLog.IOperLogService;
import com.zhixin.zhfz.common.utils.ControllerTool;
import com.zhixin.zhfz.sacw.common.Utils;
import com.zhixin.zhfz.sacw.entity.UnOwnerEntity;
import com.zhixin.zhfz.sacw.entity.WareHouseEntity;
import com.zhixin.zhfz.sacw.form.UnOwnerForm;
import com.zhixin.zhfz.sacw.services.unowner.IUnOwnerService;
import com.zhixin.zhfz.sacw.services.warehouse.IWareHouseService;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/zhfz/sacw/unowner")
public class UnOwnerController {

    private static final Logger logger = LoggerFactory.getLogger(UnOwnerController.class);

    @Autowired
    private IOperLogService operLogService;

    @Autowired
    private IUnOwnerService service;

    @Autowired
    private IWareHouseService wareHouseService;

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
        List<UnOwnerEntity> list = new ArrayList<>();
        Map<String, Object> result = new HashMap<String, Object>();
        int count = 0;
        boolean flag = true;
        Map<String, Object> map = ControllerTool.mapFilter(param);
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 办案人员-本人
            map.put("dataAuth", "i.register_user_id = " + ControllerTool.getUserID(request));
        } else if (RoleEntity.DATA_AUTH_SELFAREA == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 办案场所-本办案场所
            map.put("dataAuth", " i.warehouse_id=" + ControllerTool.getCurrentWarehouseID(request));
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 本办案场所及下级办案场所
            map.put("dataAuth", " i.warehouse_id " + sessionInfo.getCurrentAndSubWarehouseInStr());
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEAREA == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 上级办案场所及其下级办案场所
            map.put("dataAuth", " i.warehouse_id " + sessionInfo.getSuperAndSubWarehouseInStr());
        } else if (RoleEntity.DATA_AUTH_FULL == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 全部
            map.put("dataAuth", "");
        } else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 本部门
            map.put("dataAuth", "i.op_pid like " + sessionInfo.getCurrentOrgPid());
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 法制人员-上级部门及其下级部门
            map.put("dataAuth", "i.op_pid like  " + sessionInfo.getSuperAndSubOrgPid());
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 公安领导-本部门及下级部门
            map.put("dataAuth", "i.op_pid like " + sessionInfo.getCurrentAndSubOrgPid());
        } else {
            flag = false;
            // return null
        }

        if (flag) {
            list = service.list(map);
            count = service.count(map);
        }
        result.put("total", count);
        result.put("rows", list);
        return result;
    }

    /**
     * @param
     * @throws Exception
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public MessageEntity add(@RequestBody UnOwnerForm form, HttpServletRequest request) {
        UnOwnerEntity entity = new UnOwnerEntity();
        try {
            entity.setType(2);//无主物品类型
            entity.setStatus(1);//待入库
            entity.setName(form.getName());
            entity.setInvolvedTypeId(form.getInvolvedTypeId());
            entity.setDetailCount(form.getDetailCount());
            entity.setUnit(form.getUnit());
            entity.setInputTypeId(form.getInputTypeId());
            entity.setWorth(form.getWorth());
            entity.setUuid(Utils.getUUId());
            entity.setOp_pid(ControllerTool.getSessionInfo(request).getCurrentOrg().getPid());
            entity.setWeight(form.getWeight());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if (form.getExpiredTime() != null && form.getExpiredTime() != "") {
                entity.setExpiredTime(sdf.parse(form.getExpiredTime()));
            }
            entity.setDescription(form.getDescription());
            entity.setRegisterUserId(ControllerTool.getUserID(request));
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
            entity.setBarcode(dateFormat.format(new java.util.Date()));
            entity.setAreaId(form.getAreaId());

            service.insert(entity);

            this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "添加无主物品信息:" + entity, ControllerTool.getUser(request).getLoginName(), true, "涉案财物");
        } catch (Exception e) {
            logger.error("add 异常=" + e.getMessage());
            return new MessageEntity().addCode(1).addIsError(true).addTitle("Error").addContent("添加无主物品失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("Message").addContent("添加无主物品成功!");
    }

    /**
     * 根据id删除
     *
     * @param
     * @throws Exception
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public MessageEntity delete(@RequestBody IDForm form, HttpServletRequest request) {
        try {
            service.delete(form.getIntID());
            this.operLogService.insertLog(OperLogEntity.DELETE_TYPE, "删除无主物品信息:" + form.getId(), ControllerTool.getUser(request).getLoginName(), true, "涉案财物");
        } catch (Exception e) {
            logger.error("delete 异常=" + e.getMessage());
            return new MessageEntity().addCode(1).addIsError(true).addTitle("Error").addContent("删除无主物品失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("Message").addContent("删除无主物品成功!");
    }

    /**
     * 根据id更新
     *
     * @param
     * @throws Exception
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public MessageEntity update(@RequestBody UnOwnerForm form, HttpServletRequest request) {
        UnOwnerEntity entity = new UnOwnerEntity();
        try {
            entity.setId(form.getId());
            entity.setName(form.getName());
            entity.setInvolvedTypeId(form.getInvolvedTypeId());
            entity.setDetailCount(form.getDetailCount());
            entity.setUnit(form.getUnit());
            entity.setInputTypeId(form.getInputTypeId());
            entity.setWorth(form.getWorth());
            entity.setWeight(form.getWeight());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if (form.getExpiredTime() != null && form.getExpiredTime() != "") {
                entity.setExpiredTime(sdf.parse(form.getExpiredTime()));
            }
            entity.setDescription(form.getDescription());
            entity.setAreaId(form.getAreaId());
            service.update(entity);
            this.operLogService.insertLog(OperLogEntity.EDIT_TYPE, "更新无主物品信息:" + entity, ControllerTool.getUser(request).getLoginName(), true, "涉案财物");
        } catch (Exception e) {
            logger.error("update 异常=" + e.getMessage());
            return new MessageEntity().addCode(1).addIsError(true).addTitle("Error").addContent("更新无主物品失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("Message").addContent("更新无主物品成功!");
    }

    /**
     * 查所有及分页及条件查询
     *
     * @param param
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/listRecordByInvolvedId")
    @ResponseBody
    public Map<String, Object> listRecordByInvolvedId(@RequestParam Map<String, Object> param, HttpServletRequest request,
                                                      HttpServletResponse response) throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        Map<String, Object> map = ControllerTool.mapFilter(param);
        result.put("total", service.countRecord(map));
        result.put("rows", service.listRecordByInvolvedId(map));
        return result;
    }

    /**
     * 获取图片
     */
    @RequestMapping(value = "/getImages")
    @ResponseBody
    public List<String> getImages(@RequestBody UnOwnerForm form, HttpServletRequest request) {
        List<String> result = new ArrayList<String>();
        Map<String, Object> map = new HashMap<>();
        map.put("involvedId", form.getId());
        try {
            List<UnOwnerEntity> photos = service.listInRecordPhoto(map);
            if (photos != null && photos.size() > 0) {
                for (UnOwnerEntity photo : photos) {
                    result.add(photo.getUrl());
                }
            }
        } catch (Exception e) {
            logger.error("查看照片异常：", e);
        }
        return result;
    }

    /**
     * 根据id更新
     *
     * @param
     * @throws Exception
     */
    @RequestMapping(value = "/change")
    @ResponseBody
    public MessageEntity change(@RequestBody UnOwnerForm form, HttpServletRequest request) {
        UnOwnerEntity entity = new UnOwnerEntity();
        try {
            entity.setId(form.getId());
            entity.setLawCaseId(form.getLawCaseId());
            entity.setInvolvedOwner(form.getInvolvedOwner());
            service.change(entity);
            //先通过物品id查出物品得信息然后
            // 			this.operLogService.insertLog(OperLogEntity.EDIT_TYPE, "无主物品转换为涉案物品:" + entity, "system", true);
        } catch (Exception e) {
            logger.error("change 异常=" + e.getMessage());
            return new MessageEntity().addCode(1).addIsError(true).addTitle("Error").addContent("无主物品转换为涉案物品失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("Message").addContent("无主物品转换为涉案物品成功!");
    }

}
