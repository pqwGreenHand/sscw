package com.zhixin.zhfz.common.controller.user;

import com.zhixin.zhfz.common.common.HttpClientUtil;
import com.zhixin.zhfz.common.entity.*;
import com.zhixin.zhfz.common.form.UserForm;
import com.zhixin.zhfz.common.form.UserRoleForm;
import com.zhixin.zhfz.common.services.operLog.IOperLogService;
import com.zhixin.zhfz.common.services.organiztion.IOrganizationService;
import com.zhixin.zhfz.common.services.role.IRoleService;
import com.zhixin.zhfz.common.services.user.IUserService;
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

@Controller("aUserController")
@RequestMapping("/zhfz/common/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Autowired
    private IUserService userService;

    @Autowired
    private IOperLogService operLogService;


    @Autowired
    private IOrganizationService iOrganizationService;

    @Autowired
    private IRoleService roleService;


    @RequestMapping(value = "/getUsersInfo")
    @ResponseBody
    public Map<String, Object> getUsersInfo(@RequestParam Map<String, Object> params, HttpServletRequest request) throws Exception {
        Collection<UserEntity> users = new ArrayList<UserEntity>();
        int count = 0;
        boolean flag = true;

        Map<String, Object> map = ControllerTool.mapFilter(params);
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);

        if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuthForXtConf(request))) {
            // 本部门及下级部门
            map.put("dataAuth","u.op_pid like " + sessionInfo.getCurrentAndSubOrgPid());
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuthForXtConf(request))) {
            // 上级部门及下级部门
            map.put("dataAuth","u.op_pid like " + sessionInfo.getSuperAndSubOrgPid());
        } else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuthForXtConf(request))) {
            // 本部门
            map.put("dataAuth","u.op_pid = " + sessionInfo.getCurrentOrgPid());
        } else if(RoleEntity.DATA_AUTH_FULL == (ControllerTool.getRoleDataAuthForXtConf(request))){
            //全部
        }else if(RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuthForXtConf(request))){
            //本人
        	String sql = " (u.op_user_id = " + ControllerTool.getUserID(request)
        	+" or u.id="+ControllerTool.getUserID(request)+")";
            map.put("dataAuth",sql);
        }else{
            flag = false;
        }

        if (flag) {
            users = this.userService.getUserInfo(map);
            count = this.userService.count(map);
        }

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", count);
        result.put("rows", users);
        return result;
    }
    
    @RequestMapping(value = "/add")
    @ResponseBody
    public MessageEntity addUser(@RequestBody UserForm form, HttpServletRequest request) throws Exception {
        UserEntity entity = new UserEntity();
	        entity.setId(form.getId());
	        entity.setLoginName(form.getLoginName());
	        entity.setRealName(form.getRealName());
	        entity.setJobTitle(form.getJobTitle());
	        entity.setOrganizationId(form.getOrganizationId());
	        entity.setPassword(form.getPassword());
	        entity.setMobile(form.getMobile());
	        entity.setEmail(form.getEmail());
	        entity.setCertificateTypeId(form.getCertificateTypeId());
	        entity.setCertificateNo(form.getCertificateNo());
	        entity.setSex(form.getSex());
	        entity.setIsActive(form.getIsActive());
	        entity.setDescription(form.getDescription());
	        entity.setType(form.getType());
	        entity.setIdentity(form.getIdentity());
	        entity.setRoleId(form.getRoleId());
            entity.setOp_Pid(ControllerTool.getSessionInfo(request).getCurrentOrg().getPid());
            entity.setOp_User_Id(ControllerTool.getSessionInfo(request).getUser().getId());
        try {
            userService.insertUser(entity);
            this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "添加用户" + entity, ControllerTool.getLoginName(request), true,"系统配置");
        } catch (Exception e) {
            logger.error("Error on adding user!", e);
            this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "添加用户" + entity, ControllerTool.getLoginName(request), false,"系统配置");
            return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("添加用户失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("消息").addContent("添加用户成功!");
    }
    
    @RequestMapping(value = "/edit")
    @ResponseBody
    public MessageEntity editUser(@RequestBody UserForm form, HttpServletRequest request) throws Exception {
        UserEntity entity = new UserEntity();
        entity.setId(form.getId());
        entity.setLoginName(form.getLoginName());
        entity.setRealName(form.getRealName());
        entity.setJobTitle(form.getJobTitle());
        entity.setOrganizationId(form.getOrganizationId());
        entity.setPassword(form.getPassword());
        entity.setMobile(form.getMobile());
        entity.setEmail(form.getEmail());
        entity.setCertificateTypeId(form.getCertificateTypeId());
        entity.setCertificateNo(form.getCertificateNo());
        entity.setSex(form.getSex());
        entity.setIsActive(form.getIsActive());
        entity.setDescription(form.getDescription());
        entity.setType(form.getType());
        entity.setIdentity(form.getIdentity());
        entity.setRoleId(form.getRoleId());
        try {
            userService.updateUserByID(entity);
            this.operLogService.insertLog(OperLogEntity.EDIT_TYPE, "修改用户信息" + entity, ControllerTool.getLoginName(request), true,"系统配置");
        } catch (Exception e) {
            logger.error("Error on editing user!", e);
            this.operLogService.insertLog(OperLogEntity.EDIT_TYPE, "修改用户信息" + entity, ControllerTool.getLoginName(request), false,"系统配置");
            return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("修改失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("消息").addContent("修改成功!");
    }
    @RequestMapping(value = "/delete")
    @ResponseBody
    public MessageEntity deleteUser(@RequestBody UserForm form, HttpServletRequest request) throws Exception {
        logger.info(sdf.format(new Date()) + " ----------aUserController.delete-----------------");
        try {
            //删除 user
            userService.deleteUserByID(form.getId());
            this.operLogService.insertLog(OperLogEntity.DELETE_TYPE, "删除用户" + form.getId(),  ControllerTool.getLoginName(request), true,"系统配置");
        } catch (Exception e) {
            logger.error("Error on deleting user!", e);
            this.operLogService.insertLog(OperLogEntity.DELETE_TYPE, "删除用户" + form.getId(),  ControllerTool.getLoginName(request), false,"系统配置");
            return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("删除用户失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("消息").addContent("删除用户成功!");
    }
    @RequestMapping(value = "/getRoles")
	@ResponseBody
	public Collection<RoleEntity> getRolesByLike(@RequestParam Map<String, Object> params, HttpServletRequest request)
			throws Exception {
		Collection<RoleEntity> datas=new ArrayList<RoleEntity>();
		
		/*if (RoleEntity.DATA_AUTH_FULL == (ControllerTool.getRoleDataAuth(request))) {
			datas= this.roleService.getAllRoles();
		}
		if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
			datas= this.roleService.getAllRoles();
		}*/
        datas= this.roleService.getAllRoles();
		return datas;
	}
    @RequestMapping(value = "updateUserRoles")
	@ResponseBody
	public MessageEntity updateUserRoles(@RequestBody UserRoleForm form, HttpServletRequest request) throws Exception {
		try {
			userService.updateUserRoles(form.getUserId(), form.getRoleId());
			this.operLogService.insertLog(OperLogEntity.EDIT_TYPE, "修改用户角色信息" + form,  ControllerTool.getLoginName(request), true,"系统配置");
		} catch (Exception e) {
			logger.error("Error on editing role!", e);
			this.operLogService.insertLog(OperLogEntity.EDIT_TYPE, "修改用户角色信息" + form,  ControllerTool.getLoginName(request), false,"系统配置");
			return new MessageEntity().addCode(1).addIsError(true).addTitle("Error").addContent("修改失败!");
		}
		return new MessageEntity().addCode(1).addIsError(false).addTitle("Message").addContent("修改成功!");
	}
    

	@RequestMapping(value = "/getUsers")
	@ResponseBody
	public Map<String, Object> getUsersByLike(@RequestParam Map<String, Object> params ,HttpServletRequest request) throws Exception{
        Map<String, Object> map = ControllerTool.mapFilter(params);
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuth(request))) {
            // 办案人员-本人
            map.put("dataAuth", " ( u.op_user_id=" + ControllerTool.getUserID(request)
                    + " or u.id=" + ControllerTool.getUserID(request)
                    + " or ct.op_user_id=" + ControllerTool.getUserID(request)
                    + " or org.op_user_id=" + ControllerTool.getUserID(request)
                    + " or ia.op_user_id=" + ControllerTool.getUserID(request)
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_SELFAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 办案场所-本办案场所
            map.put("dataAuth", " ia.id=" + ControllerTool.getCurrentAreaID(request));
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 管理员 -本办案场所及下级办案场所
            map.put("dataAuth", " ia.id  " + sessionInfo.getCurrentAndSubAreaInStr());
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 上级办案场所及其下级办案场所
            map.put("dataAuth", " ia.id  " + sessionInfo.getSuperAndSubAreaInStr());
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 公安领导-本部门及下级部门
            map.put("dataAuth"," ( u.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                    + " or ct.op_pid like " +  sessionInfo.getCurrentAndSubOrgPid()
                    + " or org.p_id like " +  sessionInfo.getCurrentAndSubOrgPid()
                    + " or org.op_pid like " +  sessionInfo.getCurrentAndSubOrgPid()
                    + " or ia.op_pid like " +  sessionInfo.getCurrentAndSubOrgPid()
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 法制人员-上级部门及下级部门
            map.put("dataAuth"," ( u.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
                    + " or ct.op_pid like " +  sessionInfo.getSuperAndSubOrgPid()
                    + " or org.p_id like " +  sessionInfo.getSuperAndSubOrgPid()
                    + " or org.op_pid like " +  sessionInfo.getSuperAndSubOrgPid()
                    + " or ia.op_pid like " +  sessionInfo.getSuperAndSubOrgPid()
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuth(request))) {
            // 本部门
            map.put("dataAuth"," ( u.op_pid = " + sessionInfo.getCurrentOrgPid()
                    + " or ct.op_pid like " +  sessionInfo.getCurrentOrgPid()
                    + " or org.p_id like " +  sessionInfo.getCurrentOrgPid()
                    + " or org.op_pid like " +  sessionInfo.getCurrentOrgPid()
                    + " or ia.op_pid like " +  sessionInfo.getCurrentOrgPid()
                    + " ) ");
        } else if(RoleEntity.DATA_AUTH_FULL == (ControllerTool.getRoleDataAuth(request))){
            //全部
        }
        Collection<UserEntity> datas = this.userService.getUsersByLike(map);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", this.userService.getUsersByLikeCount(map));
        result.put("rows", datas);
        return result;
    }

    @RequestMapping(value = "/changePsw")
    @ResponseBody
    public Map<String, String> changePsw(@RequestBody UserForm form,HttpServletRequest request){
        String msg = "";
        UserEntity entity = new UserEntity();
        entity.setId(ControllerTool.getUserID(request));
        entity.setNewPwd(form.getNewPwd());
        entity.setOldPwd(form.getOldPwd());
        try {
            if(form.getOldPwd().equals(ControllerTool.getUser(request).getPassword())){
                userService.changePswByID(entity);
                this.operLogService.insertLog(OperLogEntity.EDIT_TYPE, "修改用户密码" + entity, "system", true, "系统");
                msg = "密码修改成功！";
            }else{
                this.operLogService.insertLog(OperLogEntity.EDIT_TYPE, "修改用户密码" + entity, "system", false, "系统");
                msg = "旧密码不正确，请重新输入！";
            }

        } catch (Exception e) {
            logger.error("修改密码错误", e);
            this.operLogService.insertLog(OperLogEntity.EDIT_TYPE, "修改用户密码" + entity, "system", false, "系统");
            msg = "密码修改失败！";
        }
        Map<String, String> result = new HashMap<>();
        result.put("msg", msg);
        return result;
    }

}
