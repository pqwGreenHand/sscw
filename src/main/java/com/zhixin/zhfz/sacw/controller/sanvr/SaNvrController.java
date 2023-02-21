package com.zhixin.zhfz.sacw.controller.sanvr;

import com.zhixin.zhfz.common.entity.MessageEntity;
import com.zhixin.zhfz.common.entity.OperLogEntity;
import com.zhixin.zhfz.common.entity.RoleEntity;
import com.zhixin.zhfz.common.entity.SessionInfo;
import com.zhixin.zhfz.common.form.IDForm;
import com.zhixin.zhfz.common.services.operLog.IOperLogService;
import com.zhixin.zhfz.common.utils.ControllerTool;
import com.zhixin.zhfz.sacw.entity.SaNvrEntity;
import com.zhixin.zhfz.sacw.form.SaNvrForm;
import com.zhixin.zhfz.sacw.form.SaNvrListForm;
import com.zhixin.zhfz.sacw.services.nvr.ISaNvrService;
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
@RequestMapping("/zhfz/sacw/sanvr")
public class SaNvrController {

    private static final Logger logger = LoggerFactory.getLogger(SaNvrController.class);

    @Autowired
    private IOperLogService operLogService;

    @Autowired
    private ISaNvrService service;

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
        System.out.println("list:=========" + param);
        Map<String, Object> map = ControllerTool.mapFilter(param);
        boolean flag = true;
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuthForSacwConf(request))) {
            // 办案人员-本人
            map.put("dataAuth", " n.op_user_id = " + ControllerTool.getUserID(request) );
        }else if (RoleEntity.DATA_AUTH_SELFAREA == (ControllerTool.getRoleDataAuthForSacwConf(request))) {
            // 办案场所-本办案场所
            map.put("dataAuth", " n.warehouse_id=" + ControllerTool.getCurrentWarehouseID(request));
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuthForSacwConf(request))) {
            // 本办案场所及下级办案场所
            map.put("dataAuth", " n.warehouse_id " + sessionInfo.getCurrentAndSubWarehouseInStr());
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEAREA == (ControllerTool.getRoleDataAuthForSacwConf(request))) {
            // 上级办案场所及其下级办案场所
            map.put("dataAuth", " n.warehouse_id " + sessionInfo.getSuperAndSubWarehouseInStr());
        } else if (RoleEntity.DATA_AUTH_FULL == (ControllerTool.getRoleDataAuthForSacwConf(request))) {
            // 全部
            map.put("dataAuth", "");
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuthForSacwConf(request))) {
            // 法制人员-上级部门及其下级部门
            map.put("dataAuth", "n.op_pid like " + sessionInfo.getSuperAndSubOrgPid());
        } else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuthForSacwConf(request))) {
            // 本部门
            map.put("dataAuth", "n.op_pid like" + sessionInfo.getCurrentOrgPid());
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuthForSacwConf(request))) {

            // 公安领导-本部门及下级部门
            map.put("dataAuth", "n.op_pid like " + sessionInfo.getCurrentAndSubOrgPid());
        } else {
            flag = false;
            // return null
        }
        List<SaNvrEntity> list = service.list(map);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", this.service.count(map));
        result.put("rows", list);
        return result;
    }

    @RequestMapping(value = "/comboNvr")
    @ResponseBody
    public List<SaNvrEntity> comboNvr(@RequestParam Map<String, Object> param, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> map = ControllerTool.mapFilter(param);
        List<SaNvrEntity> nvrEntities = service.comboNvr(map);
        return nvrEntities;
    }

    /**
     * 单个插入
     *
     * @param
     * @throws Exception
     */
    @RequestMapping(value = "/sanvrAdd")
    @ResponseBody
    public MessageEntity add(@RequestBody SaNvrForm form, HttpServletRequest request) {
        System.out.println("++++++++add++++++SaNvrForm=" + form);
        SaNvrEntity entity = new SaNvrEntity();
        entity.setName(form.getName());
        entity.setIp(form.getIp());
        entity.setIpBack(form.getIpBack());
        entity.setAccount(form.getAccount());
        entity.setPassword(form.getPassword());
        entity.setPort(form.getPort());
        entity.setWarehouseId(form.getWarehouseId());
        entity.setVender(form.getVender());
        entity.setType(form.getType());
        entity.setOp_pid(ControllerTool.getSessionInfo(request).getCurrentOrg().getPid());
        entity.setOp_user_id(ControllerTool.getSessionInfo(request).getUser().getId());

        try {
            service.insert(entity);
            this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "添加NVR设备:" + entity, ControllerTool.getUser(request).getLoginName(), true, OperLogEntity.SYSTEM_SACW);
        } catch (Exception e) {
            this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "添加NVR设备:" + entity, ControllerTool.getUser(request).getLoginName(), false, OperLogEntity.SYSTEM_SACW);
            return new MessageEntity().addCode(1).addIsError(true).addTitle("Error").addContent("添加NVR设备失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("Message").addContent("添加NVR设备成功!");
    }

    /**
     * 批量插入
     *
     * @param
     * @throws Exception
     */
    @RequestMapping(value = "/sanvrAddList")
    @ResponseBody
    public MessageEntity addList(@RequestBody SaNvrForm form, HttpServletRequest request) {
        System.out.println("++++++++add++++++SaNvrForm=" + form);

        System.out.println("++++++++add++++++SaNvrListForm=" + form.getListForm() + "+===========count:" + form.getNvrCount());
        SaNvrListForm nvrListForm = form.getListForm();
        List<SaNvrEntity> nvrList = new ArrayList<SaNvrEntity>();
        for (int i = 0; i < form.getNvrCount(); i++) {
            SaNvrEntity entity = new SaNvrEntity();
            entity.setName(nvrListForm.getName()[i]);
            entity.setIp(nvrListForm.getIp()[i]);
            entity.setIpBack(nvrListForm.getIp_back()[i]);
            entity.setAccount(form.getAccount());
            entity.setPassword(form.getPassword());
            entity.setPort(form.getPort());
            entity.setOp_pid(ControllerTool.getSessionInfo(request).getCurrentOrg().getPid());
            entity.setOp_user_id(ControllerTool.getSessionInfo(request).getUser().getId());
            entity.setWarehouseId(form.getWarehouseId());
            entity.setVender(form.getType() + "");
            entity.setType(form.getType());
            nvrList.add(entity);
        }
        try {
            service.insertList(nvrList);
            this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "添加NVR设备:" + nvrList, ControllerTool.getUser(request).getLoginName(), true, OperLogEntity.SYSTEM_SACW);
        } catch (Exception e) {
            e.printStackTrace();
            this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "添加NVR设备:" + nvrList, ControllerTool.getUser(request).getLoginName(), false, OperLogEntity.SYSTEM_SACW);
            return new MessageEntity().addCode(1).addIsError(true).addTitle("Error").addContent("添加NVR设备失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("Message").addContent("添加NVR设备成功!");
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
            service.deleteCameraByNvrId(form.getIntID());
            this.operLogService.insertLog(OperLogEntity.DELETE_TYPE, "删除NVR设备:" + form.getId(), ControllerTool.getUser(request).getLoginName(), true, OperLogEntity.SYSTEM_SACW);
        } catch (Exception e) {
            this.operLogService.insertLog(OperLogEntity.DELETE_TYPE, "删除NVR设备:" + form.getId(), ControllerTool.getUser(request).getLoginName(), false, OperLogEntity.SYSTEM_SACW);
            return new MessageEntity().addCode(1).addIsError(true).addTitle("Error").addContent("删除NVR设备失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("Message").addContent("删除NVR设备成功!");
    }

    /**
     * 根据id更新
     *
     * @param form * @param request
     * @throws Exception
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public MessageEntity update(@RequestBody SaNvrForm form, HttpServletRequest request) {
        SaNvrEntity entity = new SaNvrEntity();
        try {
            entity.setId(form.getId());
            entity.setName(form.getName());
            entity.setIp(form.getIp());
            entity.setIpBack(form.getIpBack());
            entity.setAccount(form.getAccount());
            entity.setPassword(form.getPassword());
            entity.setPort(form.getPort());
            entity.setWarehouseId(form.getWarehouseId());
            entity.setWarehouseName(form.getWarehouseName());
            entity.setVender(form.getVender());
            entity.setType(form.getType());
            service.update(entity);
            this.operLogService.insertLog(OperLogEntity.EDIT_TYPE, "修改NVR设备:" + entity, ControllerTool.getUser(request).getLoginName(), true, OperLogEntity.SYSTEM_SACW);
        } catch (Exception e) {
            this.operLogService.insertLog(OperLogEntity.EDIT_TYPE, "修改NVR设备:" + entity, ControllerTool.getUser(request).getLoginName(), false, OperLogEntity.SYSTEM_SACW);
            return new MessageEntity().addCode(1).addIsError(true).addTitle("Error").addContent("更新NVR设备失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("Message").addContent("更新NVR设备成功!");
    }

}
