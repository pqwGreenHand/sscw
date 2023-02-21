package com.zhixin.zhfz.common.controller.role;

import com.zhixin.zhfz.common.entity.*;
import com.zhixin.zhfz.common.form.RoleForm;
import com.zhixin.zhfz.common.services.operLog.IOperLogService;
import com.zhixin.zhfz.common.services.role.IRoleService;
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
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 角色
 */
@Controller("RoleController")
@RequestMapping("/zhfz/common/role")
public class RoleController {

    private static final Logger logger = LoggerFactory.getLogger(RoleController.class);

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IOperLogService operLogService;

    /**
     * 角色管理  查询角色列表
     *
     * @param params
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getRoles")
    @ResponseBody
    public Map<String, Object> getRolesByLike(@RequestParam Map<String, Object> params, HttpServletRequest request)
            throws Exception {
        logger.info(sdf.format(new Date()) + " ----------aRoleController.getRoles-----------------");
        Map<String, Object> map = ControllerTool.mapFilter(params);
        Collection<RoleEntity> datas = new ArrayList<RoleEntity>();
        int total = 0;
        boolean flag = true;
        /*SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);

        if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuthForXtConf(request))) {
            // 本部门及下级部门
            map.put("dataAuth","xt_role.op_pid like " + sessionInfo.getCurrentAndSubOrgPid());
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuthForXtConf(request))) {
            // 上级部门及下级部门
            map.put("dataAuth","xt_role.op_pid like " + sessionInfo.getSuperAndSubOrgPid());
        } else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuthForXtConf(request))) {
            // 本部门
            map.put("dataAuth","xt_role.op_pid = " + sessionInfo.getCurrentOrgPid());
        } else if(RoleEntity.DATA_AUTH_FULL == (ControllerTool.getRoleDataAuthForXtConf(request))){
            //全部
        }else if(RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuthForXtConf(request))){
            //本人
            map.put("dataAuth","xt_role.op_user_id = " + ControllerTool.getUserID(request));
        }else{
            flag = false;
        }*/

        if (flag) {
            datas = this.roleService.getRolesByLike(map);
            total = this.roleService.count(map);
            map.put("usePage", true);
        }
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", total);
        result.put("rows", datas);
        return result;
    }

    /**
     * 修改角色数据
     *
     * @param form
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/edit")
    @ResponseBody
    public MessageEntity editUser(@RequestBody RoleForm form,HttpServletRequest request) throws Exception {
        logger.info(sdf.format(new Date()) + " ----------aRoleController.edit-----------------");
        RoleEntity entity = new RoleEntity();
        entity.setId(form.getId());
        entity.setName(form.getName());
        entity.setDescription(form.getDescription());
        entity.setBacsDataAuth(form.getBacsDataAuth());
        entity.setSacwDataAuth(form.getSacwDataAuth());
        entity.setJzglDataAuth(form.getJzglDataAuth());
        entity.setJxkpDataAuth(form.getJxkpDataAuth());
        entity.setSlaDataAuth(form.getSlaDataAuth());
        entity.setZhglDataAuth(form.getZhglDataAuth());
        entity.setBacsConfigure(form.getBacsConfigure());
        entity.setSacwConfigure(form.getSacwConfigure());
        entity.setJzglConfigure(form.getJzglConfigure());
        entity.setJxkpConfigure(form.getJxkpConfigure());
        entity.setSlaConfigure(form.getSlaConfigure());
        entity.setXtConfigure(form.getXtConfigure());
        try {
            roleService.updateRoleByID(entity);
            this.operLogService.insertLog(OperLogEntity.EDIT_TYPE, "修改角色信息" + entity, ControllerTool.getLoginName(request), true, "系统配置");
        } catch (Exception e) {
            logger.error("Error on editing role!", e);
            this.operLogService.insertLog(OperLogEntity.EDIT_TYPE, "修改角色信息" + entity, ControllerTool.getLoginName(request), false, "系统配置");
            return new MessageEntity().addCode(1).addIsError(true).addTitle("Error").addContent("修改失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("Message").addContent("修改成功!");
    }

    /**
     * 删除角色信息
     *
     * @param form
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public MessageEntity deleteUser(@RequestBody RoleForm form,HttpServletRequest request) throws Exception {
        logger.info(sdf.format(new Date()) + " ----------aRoleController.delete-----------------");
        try {
            roleService.deleteRoleByID(form.getId());
            this.operLogService.insertLog(OperLogEntity.DELETE_TYPE, "删除角色" + form.getId(), ControllerTool.getLoginName(request), true, "系统配置");
        } catch (Exception e) {
            logger.error("Error on deleting role!", e);
            this.operLogService.insertLog(OperLogEntity.DELETE_TYPE, "删除角色" + form.getId(), ControllerTool.getLoginName(request), false, "系统配置");
            return new MessageEntity().addCode(1).addIsError(true).addTitle("Error").addContent("删除失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("Message").addContent("删除成功!");
    }

    /**
     * 增加角色信息
     *
     * @param form
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public MessageEntity addRole(@RequestBody RoleForm form,HttpServletRequest request) throws Exception {
        logger.info(sdf.format(new Date()) + " ----------aRoleController.add-----------------");
        RoleEntity entity = new RoleEntity();
        entity.setId(form.getId());
        entity.setName(form.getName());
        entity.setDescription(form.getDescription());
        entity.setBacsDataAuth(form.getBacsDataAuth());
        entity.setSacwDataAuth(form.getSacwDataAuth());
        entity.setJzglDataAuth(form.getJzglDataAuth());
        entity.setJxkpDataAuth(form.getJxkpDataAuth());
        entity.setSlaDataAuth(form.getSlaDataAuth());
        entity.setZhglDataAuth(form.getZhglDataAuth());
        entity.setBacsConfigure(form.getBacsConfigure());
        entity.setSacwConfigure(form.getSacwConfigure());
        entity.setJzglConfigure(form.getJzglConfigure());
        entity.setJxkpConfigure(form.getJxkpConfigure());
        entity.setSlaConfigure(form.getSlaConfigure());
        entity.setXtConfigure(form.getXtConfigure());
        entity.setOp_Pid(ControllerTool.getSessionInfo(request).getCurrentOrg().getPid());
        entity.setOp_User_Id(ControllerTool.getSessionInfo(request).getUser().getId());
        try {
            roleService.insertRole(entity);
            this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "添加角色" + entity, ControllerTool.getLoginName(request), true, "系统配置");
        } catch (Exception e) {
            logger.error("Error on adding role !", e);
            this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "添加角色" + entity, ControllerTool.getLoginName(request), false, "系统配置");
            return new MessageEntity().addCode(1).addIsError(true).addTitle("Error").addContent("添加失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("Message").addContent("添加成功!");
    }

    /**
     * 确认权限再查询
     *
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getAllRoles")
    @ResponseBody
    public Collection<RoleEntity> getAllRoles(HttpServletRequest request) throws Exception {
        logger.info(sdf.format(new Date()) + " ----------aRoleController.getAllRoles-----------------");
        Collection<RoleEntity> datas = new ArrayList<RoleEntity>();
        if (RoleEntity.DATA_AUTH_FULL == (ControllerTool.getRoleDataAuth(request))) {
            datas = this.roleService.getAllRoles();
        }
        if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            datas = this.roleService.getAllRoles();
        }
        return datas;
    }

    /**
     * 根据用户id分页查询用户的角色
     *
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getUserRolesByUserID")
    @ResponseBody
    public Map<String, Object> getUserRolesByUserID(@RequestParam Map<String, Object> params) throws Exception {
        logger.info(sdf.format(new Date()) + " \n----------aUserController.getUserRolesByUserID-----------------\n");
        Map<String, Object> map = ControllerTool.mapFilter(params);
        Collection<RoleEntity> datas = roleService.getUserRolesByUserIDMap(map);
        map.put("id", null);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", this.roleService.count(map));
        result.put("rows", datas);
        return result;
    }

    /**
     * 查询用户下所有角色
     *
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getUserRolesByUserID2")
    @ResponseBody
    public Collection<RoleEntity> getUserRolesByUserID2(@RequestParam Integer id) throws Exception {
        logger.info(sdf.format(new Date()) + " \n----------aUserController.getUserRolesByUserID2-----------------\n");
        Collection<RoleEntity> datas = roleService.getUserRolesByUserID(id);
        return datas;
    }

    /**
     * 根据用户id查询用户角色
     *
     * @param userId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/userRoleList")
    @ResponseBody
    public Map<String, Object> getUserRoleList(@RequestParam String userId) throws Exception {
        logger.info("---------------------------getUserRoleList----------------------------");
        Map<String, Object> result = new HashMap<String, Object>();
        if (userId != null && !userId.isEmpty()) {
            List<CheckData> list = roleService.getCheckRoleByUserId(Integer.valueOf(userId));
            result.put("total", list.size());
            result.put("rows", list);
        } else {
            logger.info("userId is null");
        }
        return result;
    }

}
