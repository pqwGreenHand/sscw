package com.zhixin.zhfz.bacs.controller.positiondevice;

import com.zhixin.zhfz.bacs.entity.PositionDeviceEntity;
import com.zhixin.zhfz.bacs.form.PositionDeviceForm;
import com.zhixin.zhfz.bacs.services.positiondevice.IPositionDeviceService;
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
 * 定位设备
 * */
@Controller
@RequestMapping("/zhfz/bacs/positiondevice")
public class PositionDeviceController {
    private static Logger logger = LoggerFactory.getLogger(PositionDeviceController.class);

    @Autowired
    private IPositionDeviceService positionDeviceService;

    @Autowired
    private IOperLogService operLogService;

    // 查询定位设备
    @RequestMapping(value = "/list")
    @ResponseBody
    public Map<String, Object> list(@RequestParam Map<String, Object> params, HttpServletRequest request) throws Exception {
        List<PositionDeviceEntity> datas = new ArrayList<PositionDeviceEntity>();
        int total = 0;
        Map<String, Object> map = ControllerTool.mapFilter(params);
        boolean flag=true;
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        if (RoleEntity.DATA_AUTH_SELFAREA == (ControllerTool.getRoleDataAuthForBacsConf(request))) {
            // 本办案场所
            map.put("dataAuth", "d.area_id=" + ControllerTool.getCurrentAreaID(request));
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuthForBacsConf(request))) {
            // 本办案场所及下级办案场所
            map.put("dataAuth", "d.area_id " + sessionInfo.getCurrentAndSubAreaInStr());
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEAREA == (ControllerTool.getRoleDataAuthForBacsConf(request))) {
            // 上级办案场所及其下级办案场所
            map.put("dataAuth", "d.area_id " + sessionInfo.getSuperAndSubAreaInStr());
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuthForBacsConf(request))) {
            // 本部门及下级部门
            map.put("dataAuth","d.op_pid like " + sessionInfo.getCurrentAndSubOrgPid());
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuthForBacsConf(request))) {
            // 上级部门及下级部门
            map.put("dataAuth","d.op_pid like " + sessionInfo.getSuperAndSubOrgPid());
        } else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuthForBacsConf(request))) {
            // 本部门
            map.put("dataAuth","d.op_pid = " + sessionInfo.getCurrentOrgPid());
        } else if(RoleEntity.DATA_AUTH_FULL == (ControllerTool.getRoleDataAuthForBacsConf(request))){
            //全部
            flag = true;
        }else if(RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuthForBacsConf(request))){
            //本人
            map.put("dataAuth","d.op_user_id = " + ControllerTool.getUserID(request));
        }
        if (flag){
            datas = this.positionDeviceService.list(map);
            total=this.positionDeviceService.count(map);
        }
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", total);
        result.put("rows", datas);
        return result;
    }

    // 添加定位设备
    @RequestMapping(value = "/add")
    @ResponseBody
    public MessageEntity add(@RequestBody PositionDeviceForm form, HttpServletRequest request) {
        System.err.println("++++++++++++++++++++++++++++++++++++++++++++");
        PositionDeviceEntity positionDevice = new PositionDeviceEntity();
        positionDevice.setDeviceNo(form.getDeviceNo());
        positionDevice.setDeviceType(form.getDeviceType());
        positionDevice.setMode(form.getMode());
        positionDevice.setGroupName(form.getGroupName());
        positionDevice.setGroupNo(form.getGroupNo());
        positionDevice.setIp(form.getIp());
        positionDevice.setDescription(form.getDescription());
        positionDevice.setAreaId(form.getAreaId());
        positionDevice.setRoomId(form.getRoomId());
        positionDevice.setOp_Pid(ControllerTool.getSessionInfo(request).getCurrentOrg().getPid());
        positionDevice.setOp_User_Id(ControllerTool.getSessionInfo(request).getUser().getId());
        try {
            this.positionDeviceService.insert(positionDevice);
            this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "定位设备添加日志" + positionDevice.getId(), ControllerTool.getUser(request).getLoginName(), true, "办案场所");
        } catch (Exception e) {
            logger.error("定位设备添加失败!", e);
            return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("添加失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("提示").addContent("添加成功!");
    }

    // 修改
    @RequestMapping(value = "/edit")
    @ResponseBody
    public MessageEntity edit(@RequestBody PositionDeviceForm form, HttpServletRequest request, HttpServletResponse response) {
        PositionDeviceEntity positionDevice = new PositionDeviceEntity();
        positionDevice.setId(form.getId());
        positionDevice.setDeviceNo(form.getDeviceNo());
        positionDevice.setDeviceType(form.getDeviceType());
        positionDevice.setMode(form.getMode());
        positionDevice.setGroupName(form.getGroupName());
        positionDevice.setGroupNo(form.getGroupNo());
        positionDevice.setIp(form.getIp());
        positionDevice.setDescription(form.getDescription());
        positionDevice.setAreaId(form.getAreaId());
        positionDevice.setRoomId(form.getRoomId());
        try {
            this.positionDeviceService.update(positionDevice);
            this.operLogService.insertLog(OperLogEntity.EDIT_TYPE, "定位设备修改日志" + positionDevice.getId(), ControllerTool.getUser(request).getLoginName(), true, "办案场所");

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
            this.operLogService.insertLog(OperLogEntity.DELETE_TYPE, "定位设备删除日志" + form.getLongID(), ControllerTool.getUser(request).getLoginName(), true, "办案场所");
        } catch (Exception e) {
            logger.error("删除定位设备失败!", e);
            return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("删除失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("提示").addContent("删除成功!");
    }
}
