package com.zhixin.zhfz.bacs.controller.rescue;

import com.zhixin.zhfz.bacs.entity.RescueEntity;
import com.zhixin.zhfz.bacs.services.rescue.IRescueService;
import com.zhixin.zhfz.common.entity.MessageEntity;
import com.zhixin.zhfz.common.entity.OperLogEntity;
import com.zhixin.zhfz.common.entity.RoleEntity;
import com.zhixin.zhfz.common.entity.SessionInfo;
import com.zhixin.zhfz.common.services.operLog.IOperLogService;
import com.zhixin.zhfz.common.utils.ControllerTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: zhfz
 * @description: 医疗救助
 * @author: jzw
 * @create: 2019-02-26 10:55
 **/

@Controller
@RequestMapping("/zhfz/bacs/rescue")
public class RescueController {

    private static final Logger logger = LoggerFactory.getLogger(RescueController.class);

    @Autowired
    private IRescueService service;

    @Autowired
    private IOperLogService operLogService;



    /**
     * @Author jzw
     * @Description
     * @Date 11:45 2019/2/21
     * @Param [param]
     * @return java.util.Map<java.lang.String , java.lang.Object>
     **/
    @RequestMapping(value = "/list")
    @ResponseBody
    public Map<String,Object> list(@RequestParam Map<String,Object> param,HttpServletRequest request)throws Exception{
        Map<String, Object> map = ControllerTool.mapFilter(param);
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuth(request))) {
            // 办案人员-本人
            map.put("dataAuth", " ( r.op_user_id=" + ControllerTool.getUserID(request)
                    + " or r.register_user_id = " + ControllerTool.getUserID(request)
                    + " or r.police_id = " + ControllerTool.getUserID(request)
                    + " or c.cjr = " + ControllerTool.getUserID(request)
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_SELFAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 办案场所-本办案场所
            map.put("dataAuth", "  r.area_id =" + ControllerTool.getCurrentAreaID(request));
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 管理员 -本办案场所及下级办案场所
            map.put("dataAuth", " r.area_id  " + sessionInfo.getCurrentAndSubAreaInStr());
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 上级办案场所及其下级办案场所
            map.put("dataAuth", "  r.area_id   " + sessionInfo.getSuperAndSubAreaInStr());
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 公安领导-本部门及下级部门
            map.put("dataAuth"," ( r.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                    + " or c.op_pid like " +  sessionInfo.getCurrentAndSubOrgPid()
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 法制人员-上级部门及下级部门
            map.put("dataAuth"," ( r.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
                    + " or c.op_pid like " +  sessionInfo.getSuperAndSubOrgPid()
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuth(request))) {
            // 本部门
            map.put("dataAuth"," ( r.op_pid = " + sessionInfo.getCurrentOrgPid()
                    + " or c.op_pid = " +  sessionInfo.getCurrentOrgPid()
                    + " ) ");
        }
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", this.service.count(map));
        result.put("rows", service.list(map));
        return result;
    }

    /**
     * @Author jzw
     * @Description 增加
     * @Date 10:42 2019/2/23
     * @Param [form]
     * @return com.zhixin.zhfz.common.entity.MessageEntity
     **/
    @RequestMapping("/add")
    @ResponseBody
    public MessageEntity add( RescueEntity entity, HttpServletRequest request){
        logger.info("++++++++add++++++" + entity);
        try {
            entity.setRegisterUserId(Long.valueOf(ControllerTool.getUserID(request)));
            //新添加的字段 add by lizh
            entity.setOpPid(ControllerTool.getSessionInfo(request).getCurrentOrg().getPid());
            entity.setOpUserId(ControllerTool.getSessionInfo(request).getUser().getId());
            service.insert(entity);
            this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "添加医疗救助：" + entity , ControllerTool.getLoginName(request), true,"办案场所");
        } catch (Exception e) {
            logger.error("Error on adding Rescue!", e);
            this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "添加医疗救助：" + entity , ControllerTool.getLoginName(request), false,"办案场所");
            return new MessageEntity().addCode(1).addIsError(true).addTitle("提示").addContent("添加失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("提示").addContent("添加成功!");
    }

    /**
     * @Author jzw
     * @Description 修改
     * @Date 10:42 2019/2/23
     * @Param [form]
     * @return com.zhixin.zhfz.common.entity.MessageEntity
     **/
    @RequestMapping("/edit")
    @ResponseBody
    public MessageEntity edit( RescueEntity entity, HttpServletRequest request){
        logger.info("++++++++edit++++++" + entity);
        try {
            service.update(entity);
            this.operLogService.insertLog(OperLogEntity.EDIT_TYPE, "修改医疗救助：" + entity , ControllerTool.getLoginName(request), true,"办案场所");
        } catch (Exception e) {
            logger.error("Error on editing Rescue!", e);
            this.operLogService.insertLog(OperLogEntity.EDIT_TYPE, "修改医疗救助：" + entity , ControllerTool.getLoginName(request), false,"办案场所");
            return new MessageEntity().addCode(1).addIsError(true).addTitle("提示").addContent("更新失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("提示").addContent("更新成功!");
    }

    /**
     * @Author jzw
     * @Description 删除
     * @Date 10:42 2019/2/23
     * @Param [form]
     * @return com.zhixin.zhfz.common.entity.MessageEntity
     **/
    @RequestMapping("/remove")
    @ResponseBody
    public MessageEntity remove(Long id,HttpServletRequest request){
        logger.info("++++++++remove++++++RescueEntityId=" + id);
        try {
            service.delete(id);
            this.operLogService.insertLog(OperLogEntity.DELETE_TYPE, "删除医疗救助：" + id , ControllerTool.getLoginName(request), true,"办案场所");
        } catch (Exception e) {
            logger.error("Error on removing Rescue!", e);
            this.operLogService.insertLog(OperLogEntity.DELETE_TYPE, "删除医疗救助：" + id , ControllerTool.getLoginName(request), false,"办案场所");
            return new MessageEntity().addCode(1).addIsError(true).addTitle("提示").addContent("删除失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("提示").addContent("删除成功!");
    }



}
