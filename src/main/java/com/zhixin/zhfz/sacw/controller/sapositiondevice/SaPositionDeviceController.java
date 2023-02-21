package com.zhixin.zhfz.sacw.controller.sapositiondevice;

import com.zhixin.zhfz.common.entity.MessageEntity;
import com.zhixin.zhfz.common.entity.OperLogEntity;
import com.zhixin.zhfz.common.entity.RoleEntity;
import com.zhixin.zhfz.common.entity.SessionInfo;
import com.zhixin.zhfz.common.form.IDForm;
import com.zhixin.zhfz.common.services.operLog.IOperLogService;
import com.zhixin.zhfz.common.utils.ControllerTool;
import com.zhixin.zhfz.sacw.entity.SaPositionDeviceEntity;
import com.zhixin.zhfz.sacw.form.SaPositionDeviceForm;
import com.zhixin.zhfz.sacw.services.sapositiondevice.ISaPositionDeviceService;
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
 * 定位设备
 * */
@Controller
@RequestMapping("/zhfz/sacw/sapositiondevice")
public class SaPositionDeviceController {
    private static Logger logger = LoggerFactory.getLogger(SaPositionDeviceController.class);

    @Autowired
    private ISaPositionDeviceService positionDeviceService;

    @Autowired
    private IOperLogService operLogService;

    // 查询定位设备
    @RequestMapping(value = "/list")
    @ResponseBody
    public Map<String, Object> list(@RequestParam Map<String, Object> params, HttpServletRequest request) throws Exception {
        List<SaPositionDeviceEntity> datas = new ArrayList<SaPositionDeviceEntity>();
        int total = 0;
        Map<String, Object> map = ControllerTool.mapFilter(params);
        boolean flag = true;
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuthForSacwConf(request))) {
            // 办案人员-本人
            map.put("dataAuth", " d.op_user_id = " + ControllerTool.getUserID(request) );
        }else if (RoleEntity.DATA_AUTH_SELFAREA == (ControllerTool.getRoleDataAuthForSacwConf(request))) {
            // 办案场所-本办案场所
            map.put("dataAuth", " d.warehouse_id=" + ControllerTool.getCurrentWarehouseID(request));
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuthForSacwConf(request))) {
            // 本办案场所及下级办案场所
            map.put("dataAuth", " d.warehouse_id " + sessionInfo.getCurrentAndSubWarehouseInStr());
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEAREA == (ControllerTool.getRoleDataAuthForSacwConf(request))) {
            // 上级办案场所及其下级办案场所
            map.put("dataAuth", " d.warehouse_id " + sessionInfo.getSuperAndSubWarehouseInStr());
        } else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuthForSacwConf(request))) {
            // 本部门
            map.put("dataAuth", "d.op_pid like " + sessionInfo.getCurrentOrgPid());
        } else if (RoleEntity.DATA_AUTH_FULL == (ControllerTool.getRoleDataAuthForSacwConf(request))) {
            // 全部
            map.put("dataAuth", "");
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuthForSacwConf(request))) {
            // 法制人员-上级部门及其下级部门
            map.put("dataAuth", "d.op_pid like  " + sessionInfo.getSuperAndSubOrgPid());
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuthForSacwConf(request))) {

            // 公安领导-本部门及下级部门
            map.put("dataAuth", "d.op_pid like " + sessionInfo.getCurrentAndSubOrgPid());
        } else {
            flag = false;
            // return null
        }
        datas = this.positionDeviceService.list(map);
        total = this.positionDeviceService.count(map);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", total);
        result.put("rows", datas);
        return result;
    }

    // 添加定位设备
    @RequestMapping(value = "/add")
    @ResponseBody
    public MessageEntity add(@RequestBody SaPositionDeviceForm form, HttpServletRequest request) {
        System.err.println("++++++++++++++++++++++++++++++++++++++++++++");
        SaPositionDeviceEntity positionDevice = new SaPositionDeviceEntity();
        positionDevice.setDeviceNo(form.getDeviceNo());
        positionDevice.setDeviceType(form.getDeviceType());
        positionDevice.setMode(form.getMode());
        positionDevice.setGroupName(form.getGroupName());
        positionDevice.setGroupNo(form.getGroupNo());
        positionDevice.setIp(form.getIp());
        positionDevice.setDescription(form.getDescription());
        positionDevice.setWarehouseId(form.getWarehouseId());
        positionDevice.setLocationId(form.getLocationId());
        positionDevice.setOp_pid(ControllerTool.getSessionInfo(request).getCurrentOrg().getPid());
        positionDevice.setOp_user_id(ControllerTool.getSessionInfo(request).getUser().getId());
        try {
            this.positionDeviceService.insert(positionDevice);
            this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "定位设备添加日志" + positionDevice.getId(), ControllerTool.getUser(request).getLoginName(), true, "涉案财物");
        } catch (Exception e) {
            logger.error("定位设备添加失败!", e);
            return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("添加失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("提示").addContent("添加成功!");
    }

    // 修改
    @RequestMapping(value = "/edit")
    @ResponseBody
    public MessageEntity edit(@RequestBody SaPositionDeviceForm form, HttpServletRequest request, HttpServletResponse response) {
        SaPositionDeviceEntity positionDevice = new SaPositionDeviceEntity();
        positionDevice.setId(form.getId());
        positionDevice.setDeviceNo(form.getDeviceNo());
        positionDevice.setDeviceType(form.getDeviceType());
        positionDevice.setMode(form.getMode());
        positionDevice.setGroupName(form.getGroupName());
        positionDevice.setGroupNo(form.getGroupNo());
        positionDevice.setIp(form.getIp());
        positionDevice.setDescription(form.getDescription());
        positionDevice.setWarehouseId(form.getWarehouseId());
        positionDevice.setLocationId(form.getLocationId());
        try {
            this.positionDeviceService.update(positionDevice);
            this.operLogService.insertLog(OperLogEntity.EDIT_TYPE, "定位设备修改日志" + positionDevice.getId(), ControllerTool.getUser(request).getLoginName(), true, "涉案财物");

        } catch (Exception e) {
            logger.error("修改定位设备信息错误!", e);
            return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("修改失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("提示").addContent("修改成功!");
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
            this.positionDeviceService.delete(sHId);
            this.operLogService.insertLog(OperLogEntity.DELETE_TYPE, "定位设备删除日志" + form.getLongID(), ControllerTool.getUser(request).getLoginName(), true, "涉案财物");
        } catch (Exception e) {
            logger.error("删除定位设备失败!", e);
            return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("删除失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("提示").addContent("删除成功!");
    }

}
