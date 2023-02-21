package com.zhixin.zhfz.sacw.controller.warehouse;

import com.zhixin.zhfz.common.entity.MessageEntity;
import com.zhixin.zhfz.common.entity.OperLogEntity;
import com.zhixin.zhfz.common.entity.RoleEntity;
import com.zhixin.zhfz.common.entity.SessionInfo;
import com.zhixin.zhfz.common.form.IDForm;
import com.zhixin.zhfz.common.services.operLog.IOperLogService;
import com.zhixin.zhfz.common.utils.ControllerTool;
import com.zhixin.zhfz.sacw.entity.WareHouseEntity;
import com.zhixin.zhfz.sacw.form.WareHouseForm;
import com.zhixin.zhfz.sacw.services.rfid.IRfidServerService;
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
import java.util.*;

@Controller
@RequestMapping("/zhfz/sacw/warehouse")
public class WareHouseController {

    private static Logger logger = LoggerFactory.getLogger(WareHouseController.class);

    @Autowired
    private IWareHouseService wareHouseService;

    @Autowired
    private IOperLogService operLogService;
    
    /**
     * 查询所有涉案物品仓库
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/listallwarehouse")
    @ResponseBody
    public Map<String, Object> listAllWareHouse(@RequestParam Map<String, Object> params, HttpServletRequest request,
                                                HttpServletResponse response) throws Exception {
        Map<String, Object> map = ControllerTool.mapFilter(params);
        List<WareHouseEntity> listAllWareHouse = new ArrayList<WareHouseEntity>();
        int count = 0;
        boolean flag = true;
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuthForSacwConf(request))) {
            // 办案人员-本人
            map.put("dataAuth", " w.op_user_id = " + ControllerTool.getUserID(request) );
        }  else if (RoleEntity.DATA_AUTH_SELFAREA == (ControllerTool.getRoleDataAuthForSacwConf(request))) {
            // 办案场所-本办案场所
            map.put("dataAuth", " w.id=" + ControllerTool.getCurrentWarehouseID(request));
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuthForSacwConf(request))) {
            // 本办案场所及下级办案场所
            map.put("dataAuth", " w.id " + sessionInfo.getCurrentAndSubWarehouseInStr());
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEAREA == (ControllerTool.getRoleDataAuthForSacwConf(request))) {
            // 上级办案场所及其下级办案场所
            map.put("dataAuth", " w.id " + sessionInfo.getSuperAndSubWarehouseInStr());
        }else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuthForSacwConf(request))) {
            // 本部门
            map.put("dataAuth", "w.op_pid like " + sessionInfo.getCurrentOrgPid());
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuthForSacwConf(request))) {
            // 法制人员-上级部门及其下级部门
            map.put("dataAuth", "w.op_pid like  " + sessionInfo.getSuperAndSubOrgPid());
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuthForSacwConf(request))) {
            // 公安领导-本部门及下级部门
            map.put("dataAuth", "w.op_pid like " + sessionInfo.getCurrentAndSubOrgPid());
        } else {
            flag = false;
            // return null
        }
        listAllWareHouse = wareHouseService.listAllWareHouse(map);
        count = this.wareHouseService.count(map);
        logger.info("++++++++listAllWareHouse++++++++" + listAllWareHouse);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", count);
        result.put("rows", listAllWareHouse);
        return result;
    }

    /**
     * 查询所有涉案物品仓库
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/findAllWareHouse")
    @ResponseBody
    public List<WareHouseEntity> findAllWareHouse(@RequestParam Map<String, Object> params, HttpServletRequest request,
                                                  HttpServletResponse response) throws Exception {

        Map<String, Object> map = ControllerTool.mapFilter(params);
        List<WareHouseEntity> listAllWareHouse = new ArrayList<WareHouseEntity>();

        boolean flag = true;
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 办案人员-本人
            map.put("dataAuth", " w.op_user_id = " + ControllerTool.getUserID(request) + "");
        } else if (RoleEntity.DATA_AUTH_FULL == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 全部
            map.put("dataAuth", "");
        } else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 本部门
            map.put("dataAuth", "w.op_pid like " + sessionInfo.getCurrentOrgPid());
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 法制人员-上级部门及其下级部门
            map.put("dataAuth", "w.op_pid like  " + sessionInfo.getSuperAndSubOrgPid());
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 公安领导-本部门及下级部门
            map.put("dataAuth", "w.op_pid like " + sessionInfo.getCurrentAndSubOrgPid());
        } else {
            flag = false;
            // return null
        }


        listAllWareHouse = wareHouseService.findAllWareHouse(map);


        logger.info("++++++++listAllWareHouse++++++++" + listAllWareHouse);
        Map<String, Object> result = new HashMap<String, Object>();


        return listAllWareHouse;
    }


    /**
     * 插入涉案物品仓库
     *
     * @param form
     * @throws Exception
     */
    @RequestMapping(value = "/addwarehouse")
    @ResponseBody
    public MessageEntity addWareHouse(@RequestBody WareHouseForm form, HttpServletRequest request){
        System.out.println("++++++++addWareHouse++++++=" + form);
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("orgId",form.getOrgId());
        if(isTheOnlyCheck(form) == 1){
           return new MessageEntity().addCode(1).addIsError(true).addTitle("Error").addContent("添加涉案物品仓库已存在!");
       }else{

           String Pid = ControllerTool.getSessionInfo(request).getCurrentOrg().getPid();
           Integer op_user_id = ControllerTool.getSessionInfo(request).getUser().getId();
           WareHouseEntity entity = new WareHouseEntity();
           entity.setId(form.getId());
           entity.setOrgId(form.getOrgId());

           entity.setOp_pid(Pid);
           entity.setOp_user_id(op_user_id);
           entity.setName(form.getName());
           entity.setDescription(form.getDescription());
           entity.setAddress(form.getAddress());
           entity.setCreatedTime(new Date());
           try {
               if(  wareHouseService.findAllWareHouse(map).size()>0){
                   return new MessageEntity().addCode(1).addIsError(true).addTitle("Error").addContent("本部门已有涉案仓库!");
               }else {
            wareHouseService.insertWareHouse(entity);
               this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "添加涉案物品仓库" + entity, ControllerTool.getUser(request).getLoginName(), true, "涉案财物");
               }
           } catch (Exception e) {
               logger.error("Error on adding user!", e);
               this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "添加涉案物品仓库" + entity, ControllerTool.getUser(request).getLoginName(), false, "涉案财物");
               return new MessageEntity().addCode(1).addIsError(true).addTitle("Error").addContent("添加涉案物品仓库失败!");
           }

        }

        return new MessageEntity().addCode(1).addIsError(false).addTitle("Message").addContent("添加涉案物品仓库成功!");
    }

    /**
     * 根据id删除涉案物品仓库
     *
     * @param
     * @throws Exception
     */
    @RequestMapping(value = "/removewarehouse")
    @ResponseBody
    public MessageEntity removeInvWareHouse(@RequestBody IDForm form, HttpServletRequest request) {
        System.out.println("++++++++removeWareHouse++++++id=  " + form.getId());
        try {
            wareHouseService.deleteWareHouse(form.getIntID());

            this.operLogService.insertLog(OperLogEntity.DELETE_TYPE, "删除涉案物品仓库" + form.getId(), ControllerTool.getUser(request).getLoginName(), true, "涉案财物");
        } catch (Exception e) {
            logger.error("Error on deleting user!", e);
            this.operLogService.insertLog(OperLogEntity.DELETE_TYPE, "删除涉案物品仓库" + form.getId(), ControllerTool.getUser(request).getLoginName(), false, "涉案财物");
            return new MessageEntity().addCode(1).addIsError(true).addTitle("Error").addContent("删除涉案物品仓库失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("Message").addContent("删除涉案物品仓库成功!");
    }

    /**
     * 修改涉案物品仓库
     *
     * @param form
     * @throws Exception
     */
    @RequestMapping(value = "/editwarehouse")
    @ResponseBody
    public MessageEntity editInvWareHouse(@RequestBody WareHouseForm form) throws Exception {
        WareHouseEntity entity = new WareHouseEntity();
        try {
            entity.setId(form.getId());
            entity.setOrgId(form.getOrgId());
            entity.setName(form.getName());
            entity.setDescription(form.getDescription());
            entity.setAddress(form.getAddress());
            entity.setUpdatedTime(new Date());
            wareHouseService.updateWareHouse(entity);
            this.operLogService.insertLog(OperLogEntity.EDIT_TYPE, "修改涉案物品仓库" + entity, "system", true, "涉案财物");
        } catch (Exception e) {
            logger.error("Error on editing user!", e);
            this.operLogService.insertLog(OperLogEntity.EDIT_TYPE, "修改涉案物品仓库" + entity, "system", false, "涉案财物");
            return new MessageEntity().addCode(1).addIsError(true).addTitle("Error").addContent("修改涉案物品仓库失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("Message").addContent("修改涉案物品仓库成功!");
    }

    /**
     * 唯一校验
     * @param form
     * @return
     * @throws Exception
     */
    private int isTheOnlyCheck(WareHouseForm form){
        WareHouseEntity entity = new WareHouseEntity();
        entity.setOrgId(form.getOrgId());
        entity.setName(form.getName());
        return wareHouseService.qureyEntityByPram(entity);
    }

}
