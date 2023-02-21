package com.zhixin.zhfz.sacw.controller.sacamera;

import com.zhixin.zhfz.common.entity.MessageEntity;
import com.zhixin.zhfz.common.entity.OperLogEntity;
import com.zhixin.zhfz.common.entity.RoleEntity;
import com.zhixin.zhfz.common.entity.SessionInfo;
import com.zhixin.zhfz.common.form.IDForm;
import com.zhixin.zhfz.common.services.operLog.IOperLogService;
import com.zhixin.zhfz.common.utils.ControllerTool;
import com.zhixin.zhfz.sacw.entity.SaCameraEntity;
import com.zhixin.zhfz.sacw.form.SaCameraForm;
import com.zhixin.zhfz.sacw.form.SaCameraListForm;
import com.zhixin.zhfz.sacw.services.sacamera.ISaCameraService;
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
@RequestMapping("/zhfz/sacw/sacamera")
public class SaCameraController {

    private static final Logger logger = LoggerFactory.getLogger(SaCameraController.class);

    @Autowired
    private IOperLogService operLogService;

    @Autowired
    private ISaCameraService service;

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
        Map<String, Object> map = ControllerTool.mapFilter(param);
        boolean flag = true;
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuthForSacwConf(request))) {
            // 办案人员-本人
            map.put("dataAuth", " c.op_user_id = " + ControllerTool.getUserID(request) );
        }else if (RoleEntity.DATA_AUTH_SELFAREA == (ControllerTool.getRoleDataAuthForSacwConf(request))) {
            // 办案场所-本办案场所
            map.put("dataAuth", " c.warehouse_id=" + ControllerTool.getCurrentWarehouseID(request));
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuthForSacwConf(request))) {
            // 本办案场所及下级办案场所
            map.put("dataAuth", " c.warehouse_id " + sessionInfo.getCurrentAndSubWarehouseInStr());
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEAREA == (ControllerTool.getRoleDataAuthForSacwConf(request))) {
            // 上级办案场所及其下级办案场所
            map.put("dataAuth", " c.warehouse_id " + sessionInfo.getSuperAndSubWarehouseInStr());
        } else if (RoleEntity.DATA_AUTH_FULL == (ControllerTool.getRoleDataAuthForSacwConf(request))) {
            // 全部
            map.put("dataAuth", "");
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuthForSacwConf(request))) {
            // 法制人员-上级部门及其下级部门
            map.put("dataAuth", "c.op_pid like  " + sessionInfo.getSuperAndSubOrgPid());
        } else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuthForSacwConf(request))) {
            // 本部门
            map.put("dataAuth", "c.op_pid like " + sessionInfo.getCurrentOrgPid());
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuthForSacwConf(request))) {

            // 公安领导-本部门及下级部门
            map.put("dataAuth", "c.op_pid like " + sessionInfo.getCurrentAndSubOrgPid());
        } else {
            flag = false;
            // return null
        }
        List<SaCameraEntity> list = service.list(map);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", this.service.count(map));
        result.put("rows", list);
        return result;
    }

    /**
     * 插入摄像头
     *
     * @param
     * @throws Exception
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public MessageEntity add(@RequestBody SaCameraForm form, HttpServletRequest request) {
        SaCameraEntity entity = new SaCameraEntity();
        entity.setName(form.getName());
        entity.setChannel(form.getChannel());
        entity.setIp(form.getIp());
        entity.setAccount(form.getAccount());
        entity.setPassword(form.getPassword());
        entity.setPort(form.getPort());

        if (form.getScreen() == null || form.getScreen().equals("0")) {
            entity.setScreen("副画面");
        } else {
            entity.setScreen("主画面");
        }
        entity.setType(form.getType());
        entity.setDownload(form.getDownload());
        entity.setDescription(form.getDescription());
        entity.setNvrId(form.getNvrId());
        entity.setVender(form.getVender());
        entity.setWarehouseId(form.getWarehouseId());
        entity.setLocationId(form.getLocationId());
        entity.setCameraNo(form.getCameraNo());
        entity.setOp_pid(ControllerTool.getSessionInfo(request).getCurrentOrg().getPid());
        entity.setOp_user_id(ControllerTool.getSessionInfo(request).getUser().getId());
        try {
            service.insert(entity);
            this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "添加摄像头信息:" + entity, ControllerTool.getUser(request).getLoginName(), true, OperLogEntity.SYSTEM_SACW);
        } catch (Exception e) {
            this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "添加摄像头信息:" + entity, ControllerTool.getUser(request).getLoginName(), false, OperLogEntity.SYSTEM_SACW);
            return new MessageEntity().addCode(1).addIsError(true).addTitle("Error").addContent("添加摄像头失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("Message").addContent("添加摄像头成功!");
    }

    /**
     * 批量插入摄像头
     *
     * @param
     * @throws Exception
     */
    @RequestMapping(value = "/addCameraList")
    @ResponseBody
    public MessageEntity addCameraList(@RequestBody SaCameraForm cameraForm, HttpServletRequest request) {
        SaCameraListForm listForm = cameraForm.getListForm();
        List<SaCameraEntity> cameraList = new ArrayList<SaCameraEntity>();
        for (int i = 0; i < cameraForm.getCameraCount(); i++) {
            SaCameraEntity entity = new SaCameraEntity();
            entity.setName(listForm.getName()[i]);
            entity.setChannel(listForm.getChannel()[i]);
            entity.setIp(listForm.getIp()[i]);
            entity.setAccount(cameraForm.getAccount());
            entity.setPassword(cameraForm.getPassword());
            entity.setOp_pid(ControllerTool.getSessionInfo(request).getCurrentOrg().getPid());
            entity.setOp_user_id(ControllerTool.getSessionInfo(request).getUser().getId());
            entity.setPort(cameraForm.getPort());
            if (listForm.getScreen()[i] == null || listForm.getScreen()[i].equals("0")) {
                entity.setScreen("副画面");
            } else {
                entity.setScreen("主画面");
            }
            entity.setType(listForm.getType()[i]);
            entity.setDownload(listForm.getDownload()[i]);
            entity.setDescription(cameraForm.getDescription());
            entity.setNvrId(cameraForm.getNvrId());
            entity.setVender(cameraForm.getVender());
            entity.setWarehouseId(cameraForm.getWarehouseId());
            entity.setLocationId(listForm.getLocation_id()[i]);
            entity.setCameraNo(listForm.getCamera_no()[i]);
            cameraList.add(entity);
        }

        try {
            service.insertList(cameraList);
            this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "添加摄像头信息:" + cameraList, ControllerTool.getUser(request).getLoginName(), true, OperLogEntity.SYSTEM_SACW);
        } catch (Exception e) {
            e.printStackTrace();
            this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "添加摄像头信息:" + cameraList, ControllerTool.getUser(request).getLoginName(), false, OperLogEntity.SYSTEM_SACW);
            return new MessageEntity().addCode(1).addIsError(true).addTitle("Error").addContent("批量添加摄像头失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("Message").addContent("批量添加摄像头成功!");
    }

    /**
     * 根据id删除
     *
     * @param form
     * @throws Exception
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public MessageEntity delete(@RequestBody IDForm form, HttpServletRequest request) {
        try {
            service.delete(form.getIntID());
            this.operLogService.insertLog(OperLogEntity.DELETE_TYPE, "删除摄像头信息:" + form.getId(), ControllerTool.getUser(request).getLoginName(), true, OperLogEntity.SYSTEM_SACW);
        } catch (Exception e) {
            this.operLogService.insertLog(OperLogEntity.DELETE_TYPE, "删除摄像头信息:" + form.getId(), ControllerTool.getUser(request).getLoginName(), false, OperLogEntity.SYSTEM_SACW);
            return new MessageEntity().addCode(1).addIsError(true).addTitle("Error").addContent("删除摄像头失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("Message").addContent("删除摄像头成功!");
    }

    /**
     * 根据id更新
     *
     * @param form
     * @throws Exception
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public MessageEntity update(@RequestBody SaCameraForm form, HttpServletRequest request) {
        SaCameraEntity entity = new SaCameraEntity();
        try {

            entity.setId(form.getId());
            entity.setName(form.getName());
            entity.setChannel(form.getChannel());
            entity.setIp(form.getIp());
            entity.setAccount(form.getAccount());
            entity.setPassword(form.getPassword());
            entity.setPort(form.getPort());
            if (form.getScreen() == null || form.getScreen().equals("0")) {
                entity.setScreen("副画面");
            } else {
                entity.setScreen("主画面");
            }
            entity.setType(form.getType());
            entity.setDownload(form.getDownload());
            entity.setDescription(form.getDescription());
            entity.setNvrId(form.getNvrId());
            entity.setVender(form.getVender());
            entity.setWarehouseId(form.getWarehouseId());
            entity.setLocationId(form.getLocationId());
            entity.setCameraNo(form.getCameraNo());
            service.update(entity);
            this.operLogService.insertLog(OperLogEntity.EDIT_TYPE, "更新摄像头信息:" + entity, ControllerTool.getUser(request).getLoginName(), true, OperLogEntity.SYSTEM_SACW);
        } catch (Exception e) {
            e.printStackTrace();
            this.operLogService.insertLog(OperLogEntity.EDIT_TYPE, "更新摄像头信息:" + entity, ControllerTool.getUser(request).getLoginName(), false, OperLogEntity.SYSTEM_SACW);
            return new MessageEntity().addCode(1).addIsError(true).addTitle("Error").addContent("更新摄像头失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("Message").addContent("更新摄像头成功!");
    }

}
