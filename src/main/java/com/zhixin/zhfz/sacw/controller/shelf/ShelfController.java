package com.zhixin.zhfz.sacw.controller.shelf;

import com.zhixin.zhfz.common.entity.MessageEntity;
import com.zhixin.zhfz.common.entity.OperLogEntity;
import com.zhixin.zhfz.common.entity.RoleEntity;
import com.zhixin.zhfz.common.entity.SessionInfo;
import com.zhixin.zhfz.common.form.IDForm;
import com.zhixin.zhfz.common.services.operLog.IOperLogService;
import com.zhixin.zhfz.common.utils.ControllerTool;
import com.zhixin.zhfz.sacw.entity.ShelfEntity;
import com.zhixin.zhfz.sacw.form.ShelfForm;
import com.zhixin.zhfz.sacw.services.shelf.IShelfService;
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
@RequestMapping("/zhfz/sacw/shelf")
public class ShelfController {

    private static Logger logger = LoggerFactory.getLogger(ShelfController.class);

    @Autowired
    private IShelfService shelfService;

    @Autowired
    private IOperLogService operLogService;

    /**
     * 查询货架信息
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/listallshelf")
    @ResponseBody
    public Map<String, Object> listAllShelf(@RequestParam Map<String, Object> params, HttpServletRequest request,
                                            HttpServletResponse response) throws Exception {
        List<ShelfEntity> listAllShelf = new ArrayList<ShelfEntity>();
        int count = 0;

        Map<String, Object> map = ControllerTool.mapFilter(params);
        boolean flag = true;
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuthForSacwConf(request))) {
            // 办案人员-本人
            map.put("dataAuth", " s.op_user_id = " + ControllerTool.getUserID(request) );
        }else if (RoleEntity.DATA_AUTH_SELFAREA == (ControllerTool.getRoleDataAuthForSacwConf(request))) {
            // 办案场所-本办案场所
            map.put("dataAuth", " s.warehouse_id=" + ControllerTool.getCurrentWarehouseID(request));
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuthForSacwConf(request))) {
            // 本办案场所及下级办案场所
            map.put("dataAuth", " s.warehouse_id " + sessionInfo.getCurrentAndSubWarehouseInStr());
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEAREA == (ControllerTool.getRoleDataAuthForSacwConf(request))) {
            // 上级办案场所及其下级办案场所
            map.put("dataAuth", " s.warehouse_id " + sessionInfo.getSuperAndSubWarehouseInStr());
        } else if (RoleEntity.DATA_AUTH_FULL == (ControllerTool.getRoleDataAuthForSacwConf(request))) {
            // 全部
            map.put("dataAuth", "");
        } else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuthForSacwConf(request))) {
            // 本部门
            map.put("dataAuth", "s.op_pid like " + sessionInfo.getCurrentOrgPid());
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuthForSacwConf(request))) {
            // 法制人员-上级部门及其下级部门
            map.put("dataAuth", "s.op_pid like  " + sessionInfo.getSuperAndSubOrgPid());
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuthForSacwConf(request))) {

            // 公安领导-本部门及下级部门
            map.put("dataAuth", "s.op_pid like " + sessionInfo.getCurrentAndSubOrgPid());
        } else {
            flag = false;
            // return null
        }
        listAllShelf = shelfService.listAllShelf(map);
        count = this.shelfService.count(map);

        logger.info("++++++++listAllShelf++++++++" + listAllShelf);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", count);
        result.put("rows", listAllShelf);
        return result;
    }


    /**
     * 批量插入货架信息
     *
     * @param form
     * @throws Exception
     */
    @RequestMapping(value = "/addshelfs")
    @ResponseBody
    public MessageEntity addShelfs(@RequestBody ShelfForm form, HttpServletRequest request) {
        logger.info("++++++++addShelfs++++++=" + form);
        if (form.getNo1() == null || form.getNo1() == "" || form.getNo2() == null || form.getNo1() == "") {
            return new MessageEntity().addCode(1).addIsError(true).addTitle("Error").addContent("请输入正确的格式!");
        }
        int no1 = Integer.parseInt(form.getNo1());
        int no2 = Integer.parseInt(form.getNo2());
        if (no1 > no2) {
            return new MessageEntity().addCode(1).addIsError(true).addTitle("Error").addContent("请检查输入");
        }
        for (int i = no1; i <= no2; i++) {
            int locationId1 = form.getLocationId1();
            ShelfEntity entity = new ShelfEntity();
            entity.setLocationId(locationId1);
            entity.setNo(String.valueOf(i));
            entity.setOp_pid(ControllerTool.getSessionInfo(request).getCurrentOrg().getPid());
            entity.setOp_user_id(ControllerTool.getSessionInfo(request).getUser().getId());
            entity.setCreatedTime(new Date());
            try {
                shelfService.insertShelf(entity);
                this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "添加货架信息" + entity, ControllerTool.getUser(request).getLoginName(), true, "涉案财物");
            } catch (Exception e) {
                logger.error("Error on adding user!", e);
                this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "添加货架信息" + entity, ControllerTool.getUser(request).getLoginName(), false, "涉案财物");
                return new MessageEntity().addCode(1).addIsError(true).addTitle("Error").addContent(i + "添加失败!");
            }
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("Message").addContent("添加成功!");
    }

    /**
     * 插入货架信息
     *
     * @param form
     * @throws Exception
     */
    @RequestMapping(value = "/addshelf")
    @ResponseBody
    public MessageEntity addShelf(@RequestBody ShelfForm form, HttpServletRequest request) {
        logger.info("++++++++addShelf++++++=" + form);
        ShelfEntity entity = new ShelfEntity();
        entity.setId(form.getId());
        entity.setLocationId(form.getLocationId());
        entity.setNo(form.getNo());
        entity.setDescription(form.getDescription());
        entity.setOp_pid(ControllerTool.getSessionInfo(request).getCurrentOrg().getPid());
        entity.setOp_user_id(ControllerTool.getSessionInfo(request).getUser().getId());
        entity.setCreatedTime(new Date());
        try {
            shelfService.insertShelf(entity);
            this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "添加货架信息" + entity, ControllerTool.getUser(request).getLoginName(), true, "涉案财物");
        } catch (Exception e) {
            logger.error("Error on adding user!", e);
            this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "添加货架信息" + entity, ControllerTool.getUser(request).getLoginName(), false, "涉案财物");
            return new MessageEntity().addCode(1).addIsError(true).addTitle("Error").addContent("添加失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("Message").addContent("添加成功!");
    }

    /**
     * 根据id删除货架信息
     *
     * @param form
     * @throws Exception
     */
    @RequestMapping(value = "/removeshelf")
    @ResponseBody
    public MessageEntity removeInvShelf(@RequestBody IDForm form, HttpServletRequest request) {
        System.out.println("++++++++removeShelf++++++id=  " + form.getId());
        try {
            shelfService.deleteShelf(form.getIntID());
            this.operLogService.insertLog(OperLogEntity.DELETE_TYPE, "删除货架信息" + form.getId(), ControllerTool.getUser(request).getLoginName(), true, "涉案财物");
        } catch (Exception e) {
            logger.error("Error on deleting user!", e);
            this.operLogService.insertLog(OperLogEntity.DELETE_TYPE, "删除货架信息" + form.getId(), ControllerTool.getUser(request).getLoginName(), false, "涉案财物");
            return new MessageEntity().addCode(1).addIsError(true).addTitle("Error").addContent("删除失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("Message").addContent("删除成功!");
    }

    /**
     * 修改货架信息
     *
     * @param form
     * @throws Exception
     */
    @RequestMapping(value = "/editshelf")
    @ResponseBody
    public MessageEntity editInvShelf(@RequestBody ShelfForm form, HttpServletRequest request) throws Exception {
        ShelfEntity entity = new ShelfEntity();
        try {
            entity.setId(form.getId());
            entity.setLocationId(form.getLocationId());
            entity.setNo(form.getNo());
            entity.setDescription(form.getDescription());
            entity.setUpdatedTime(new Date());
            shelfService.updateShelf(entity);
            this.operLogService.insertLog(OperLogEntity.EDIT_TYPE, "修改货架信息" + entity, ControllerTool.getUser(request).getLoginName(), true, "涉案财物");
        } catch (Exception e) {
            logger.error("Error on editing user!", e);
            this.operLogService.insertLog(OperLogEntity.EDIT_TYPE, "修改货架信息" + entity, ControllerTool.getUser(request).getLoginName(), false, "涉案财物");
            return new MessageEntity().addCode(1).addIsError(true).addTitle("Error").addContent("修改失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("Message").addContent("修改成功!");
    }

}
