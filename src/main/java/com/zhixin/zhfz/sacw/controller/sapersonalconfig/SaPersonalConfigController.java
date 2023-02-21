package com.zhixin.zhfz.sacw.controller.sapersonalconfig;

import com.zhixin.zhfz.common.entity.MessageEntity;
import com.zhixin.zhfz.common.entity.OperLogEntity;
import com.zhixin.zhfz.common.entity.RoleEntity;
import com.zhixin.zhfz.common.entity.SessionInfo;
import com.zhixin.zhfz.common.form.IDForm;
import com.zhixin.zhfz.common.services.operLog.IOperLogService;
import com.zhixin.zhfz.common.utils.ControllerTool;
import com.zhixin.zhfz.sacw.entity.SaPersonalConfigEntity;
import com.zhixin.zhfz.sacw.entity.SaPersonalConfigFusionEntity;
import com.zhixin.zhfz.sacw.form.SaPersonalConfigForm;
import com.zhixin.zhfz.sacw.services.sapersonalconfig.ISaPersonalConfigService;
import org.apache.commons.collections.map.HashedMap;
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
@RequestMapping("/zhfz/sacw/sapersonalconfig")
public class SaPersonalConfigController {

    private static final Logger logger = LoggerFactory.getLogger(SaPersonalConfigController.class);


    @Autowired
    private IOperLogService operLogService;
    @Autowired
    private ISaPersonalConfigService service;

    /**
     * 查所有及分页及条件查询
     *
     * @param param
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/listPersonalConfig")
    @ResponseBody
    public Map<String, Object> list(@RequestParam Map<String, Object> param, HttpServletRequest request,
                                    HttpServletResponse response) throws Exception {
        List<SaPersonalConfigEntity> list = new ArrayList<SaPersonalConfigEntity>();
        int total = 0;
        Map<String, Object> map = ControllerTool.mapFilter(param);
        boolean flag = true;
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuthForSacwConf(request))) {
            // 办案人员-本人
            map.put("dataAuth", " pc.op_user_id = " + ControllerTool.getUserID(request) );
        }else if (RoleEntity.DATA_AUTH_SELFAREA == (ControllerTool.getRoleDataAuthForSacwConf(request))) {
            // 办案场所-本办案场所
            map.put("dataAuth", " pc.warehouse_id=" + ControllerTool.getCurrentWarehouseID(request));
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuthForSacwConf(request))) {
            // 本办案场所及下级办案场所
            map.put("dataAuth", " pc.warehouse_id " + sessionInfo.getCurrentAndSubWarehouseInStr());
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEAREA == (ControllerTool.getRoleDataAuthForSacwConf(request))) {
            // 上级办案场所及其下级办案场所
            map.put("dataAuth", " pc.warehouse_id " + sessionInfo.getSuperAndSubWarehouseInStr());
        } else if (RoleEntity.DATA_AUTH_FULL == (ControllerTool.getRoleDataAuthForSacwConf(request))) {
            // 全部
            map.put("dataAuth", "");
        } else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuthForSacwConf(request))) {
            // 本部门
            map.put("dataAuth", "pc.op_pid like " + sessionInfo.getCurrentOrgPid());
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuthForSacwConf(request))) {
            // 法制人员-上级部门及其下级部门
            map.put("dataAuth", "pc.op_pid like  " + sessionInfo.getSuperAndSubOrgPid());
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuthForSacwConf(request))) {

            // 公安领导-本部门及下级部门
            map.put("dataAuth", "pc.op_pid like " + sessionInfo.getCurrentAndSubOrgPid());
        } else {
            flag = false;
            // return null
        }
        list = service.list(map);
        total = this.service.count(map);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", total);
        System.out.println("=================listPersonalConfig=========" + total);
        result.put("rows", list);

        return result;
    }

    /**
     * 插入办案场所个性化配置
     *
     * @param form
     * @throws Exception
     */
    @RequestMapping(value = "/addPersonalConfig")
    @ResponseBody
    public MessageEntity add(@RequestBody SaPersonalConfigForm form, HttpServletRequest request) {
        System.out.println("++++++++add++++++UserForm=" + form);
        SaPersonalConfigEntity entity = new SaPersonalConfigEntity();
        entity.setId(form.getId());
        entity.setType(form.getType());
        entity.setWarehouseId(form.getWarehouseId());
        entity.setDesc(form.getDesc());
        entity.setIsEnable(form.getIsEnable());
        entity.setOp_pid(ControllerTool.getSessionInfo(request).getCurrentOrg().getPid());
        entity.setOp_user_id(ControllerTool.getSessionInfo(request).getUser().getId());
        try {
            service.insert(entity);
            this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "添加办案场所个性化配置" + entity, ControllerTool.getRoleName(request), true, OperLogEntity.SYSTEM_SACW);
        } catch (Exception e) {
            logger.error("Error on adding user!", e);
            this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "添加办案场所个性化配置" + entity, ControllerTool.getRoleName(request), false, OperLogEntity.SYSTEM_SACW);
            return new MessageEntity().addCode(1).addIsError(true).addTitle("Error").addContent("添加失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("Message").addContent("添加成功!");
    }

    /**
     * 修改个性化配置信息
     *
     * @param form
     * @throws Exception
     */
    @RequestMapping(value = "/editPersonalConfig")
    @ResponseBody
    public MessageEntity edit(@RequestBody SaPersonalConfigForm form, HttpServletRequest request) throws Exception {
        logger.info("++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        SaPersonalConfigEntity entity = new SaPersonalConfigEntity();
        entity.setId(form.getId());
        entity.setType(form.getType());
        entity.setWarehouseId(form.getWarehouseId());
        entity.setDesc(form.getDesc());
        entity.setIsEnable(form.getIsEnable());
        logger.debug("$$$$$$$$$$$$edit88888888+" + form + "----------------------------------------");
        try {
            service.update(entity);
            this.operLogService.insertLog(OperLogEntity.EDIT_TYPE, "修改办案场所个性化配置" + entity, ControllerTool.getRoleName(request), true, OperLogEntity.SYSTEM_SACW);
        } catch (Exception e) {
            logger.error("Error on editing user!", e);
            this.operLogService.insertLog(OperLogEntity.EDIT_TYPE, "修改办案场所个性化配置" + entity, ControllerTool.getRoleName(request), false, OperLogEntity.SYSTEM_SACW);
            return new MessageEntity().addCode(1).addIsError(true).addTitle("Error").addContent("修改失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("Message").addContent("修改成功");
    }

    /**
     * 根据id删除办案场所个性化配置
     *
     * @param form
     * @throws Exception
     */
    @RequestMapping(value = "/removePersonalConfig")
    @ResponseBody
    public MessageEntity remove(@RequestBody IDForm form, HttpServletRequest request) {
        System.out.println("++++++++remove++++++id=  " + form.getId());
        try {
            service.delete(form.getIntID());
            this.operLogService.insertLog(OperLogEntity.DELETE_TYPE, "删除办案场所个性化配置" + form.getId(), ControllerTool.getRoleName(request), true, OperLogEntity.SYSTEM_SACW);
        } catch (Exception e) {
            logger.error("Error on deleting user!", e);
            this.operLogService.insertLog(OperLogEntity.DELETE_TYPE, "删除办案场所个性化配置" + form.getId(), ControllerTool.getRoleName(request), true, OperLogEntity.SYSTEM_SACW);
            return new MessageEntity().addCode(1).addIsError(true).addTitle("Error").addContent("删除失败");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("Message").addContent("删除成功!");
    }

    /**
     * 区域初始化
     *
     * @param form
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/initArea")
    @ResponseBody
    public MessageEntity initArea(@RequestBody SaPersonalConfigForm form, HttpServletRequest request) throws Exception {
        int warehouseId = form.getWarehouseId();
        try {
            service.initArea(warehouseId);
            this.operLogService.insertLog(OperLogEntity.EDIT_TYPE, "区域初始化,仓库id：" + warehouseId, ControllerTool.getRoleName(request), true, OperLogEntity.SYSTEM_SACW);
        } catch (Exception e) {
            this.operLogService.insertLog(OperLogEntity.EDIT_TYPE, "区域初始化,仓库id：" + warehouseId, ControllerTool.getRoleName(request), false, OperLogEntity.SYSTEM_SACW);
            return new MessageEntity().addCode(1).addIsError(true).addTitle("Error").addContent("初始化失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("Message").addContent("初始化成功!");
    }

    /**
     * @return com.zhixin.zhfz.common.entity.MessageEntity
     * @Author jzw
     * @Description
     * @Date 14:11 2019/3/6
     * @Param [areaId, type]
     **/
    @RequestMapping("/getDetailByType")
    @ResponseBody
    public MessageEntity getDetailByType(Long warehouseId, String type) {
        MessageEntity resultEntity = new MessageEntity();
        try {
            Map<String, Object> map = new HashedMap();
            map.put("type", type);
            map.put("warehouseId", warehouseId);
            List<SaPersonalConfigFusionEntity> list = service.getDetailByType(map);
            resultEntity.setError(false);
            resultEntity.addCallbackData(list);
        } catch (Exception e) {
            logger.info(" listStorageDetail：" + e.getMessage());
            resultEntity.setError(true);
            resultEntity.setContent(e.getMessage());
        }
        return resultEntity;
    }


}
