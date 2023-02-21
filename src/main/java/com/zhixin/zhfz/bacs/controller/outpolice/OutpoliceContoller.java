package com.zhixin.zhfz.bacs.controller.outpolice;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhixin.zhfz.bacs.services.outpolice.OutPoliceService;
import com.zhixin.zhfz.bacs.services.personalconfig.IPersonalConfigDetailService;
import com.zhixin.zhfz.common.entity.MessageEntity;
import com.zhixin.zhfz.common.entity.OperLogEntity;
import com.zhixin.zhfz.common.entity.OrganizationEntity;
import com.zhixin.zhfz.common.entity.RoleEntity;
import com.zhixin.zhfz.common.entity.SessionInfo;
import com.zhixin.zhfz.common.entity.UserEntity;
import com.zhixin.zhfz.common.form.OrganizationForm;
import com.zhixin.zhfz.common.form.UserForm;
import com.zhixin.zhfz.common.services.commonConfig.ICommonConfigDetailService;
import com.zhixin.zhfz.common.services.operLog.IOperLogService;
import com.zhixin.zhfz.common.services.organiztion.IOrganizationService;
import com.zhixin.zhfz.common.utils.ControllerTool;

@Controller
@RequestMapping("/zhfz/bacs/outpolice")
public class OutpoliceContoller {
    private static final Logger logger = LoggerFactory.getLogger(OutpoliceContoller.class);
    @Autowired
    private OutPoliceService outPoliceService;

    @Autowired
    private IOperLogService operLogService;

    @Autowired
    private IOrganizationService organizationService;

    @Autowired
    private IPersonalConfigDetailService service;

    @Autowired
    private ICommonConfigDetailService ommonConfigDetailSservice;


    @RequestMapping(value = "/getOutpoliceInfo")
    @ResponseBody
    public Map<String, Object> getOutpoliceInfo(@RequestParam Map<String, Object> params, HttpServletRequest request) throws Exception {
        Map<String, Object> map = ControllerTool.mapFilter(params);
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuth(request))) {
            // 办案人员-本人
            map.put("dataAuth", "  u.op_user_id=" + ControllerTool.getUserID(request));
        }else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 公安领导-本部门及下级部门
            map.put("dataAuth"," ( u.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                    + " or u.organization_id like " + sessionInfo.getCurrentAndSubOrgPid()
           +")" );
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 法制人员-上级部门及下级部门
            map.put("dataAuth"," ( u.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
                    +" or u.organization_id like " + sessionInfo.getSuperAndSubOrgPid()
            +")");
        } else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuth(request))) {
            // 本部门
            map.put("dataAuth"," ( u.op_pid = " + sessionInfo.getCurrentOrgPid()
                    + " or u.organization_id = " + sessionInfo.getCurrentOrgPid()
            +")");
        }

        List<UserEntity> users = new ArrayList<UserEntity>();
        int count = 0;
        String parm1 = "outOrg";
        String parm2 = "org.outpolice";
        String value = ommonConfigDetailSservice.listDetailsByName( parm1,parm2);
        map.put("parentId",value);
        users = this.outPoliceService.getOutpoliceInfo(map);
        count = this.outPoliceService.count(map);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", count);
        result.put("rows", users);
        return result;
    }

    @RequestMapping(value = "/add")
    @ResponseBody
    public MessageEntity addUser(@RequestBody UserForm form, HttpServletRequest request) throws Exception {
        UserEntity entity = new UserEntity();
        String value =null;

            String parm1 = "outOrg";
            String parm2 = "role.outpolice";
            value = ommonConfigDetailSservice.listDetailsByName( parm1,parm2);
            //value = map1.get("role.outpolice");


        entity.setId(form.getId());
        entity.setLoginName(form.getCertificateNo().toString());
        entity.setRealName(form.getRealName());
        entity.setJobTitle("民警");
        entity.setOrganizationId(form.getOrganizationId());
        entity.setPassword("123456");
        entity.setMobile(form.getMobile());
        entity.setEmail(form.getEmail());
        entity.setCertificateTypeId(191);
        entity.setCertificateNo(form.getCertificateNo());
        entity.setSex(form.getSex());
        entity.setIsActive(1);
        entity.setDescription(form.getDescription());
        entity.setType(2);
        entity.setIdentity(form.getIdentity());
        entity.setRoleId(Integer.parseInt(value));
        entity.setOp_Pid(ControllerTool.getSessionInfo(request).getCurrentOrg().getPid());
        entity.setOp_User_Id(ControllerTool.getSessionInfo(request).getUser().getId());
        try {
            outPoliceService.insertOutpolice(entity);
            this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "添加用户" + entity, ControllerTool.getLoginName(request), true,"办案场所");
        } catch (Exception e) {
            logger.error("Error on adding user!", e);
            this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "添加用户" + entity, ControllerTool.getLoginName(request), false,"办案场所");
            return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("添加用户失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("消息").addContent("添加用户成功!");
    }

    @RequestMapping(value = "/edit")
    @ResponseBody
    public MessageEntity editUser(@RequestBody UserForm form, HttpServletRequest request) throws Exception {
        UserEntity entity = new UserEntity();
        String value =null;
        String parm1 = "outOrg";
        String parm2 = "role.outpolice";
        value = ommonConfigDetailSservice.listDetailsByName( parm1,parm2);
        entity.setId(form.getId());
        entity.setLoginName(form.getCertificateNo().toString());
        entity.setRealName(form.getRealName());
        entity.setJobTitle("民警");
        entity.setOrganizationId(form.getOrganizationId());
        entity.setPassword("123456");
        entity.setMobile(form.getMobile());
        entity.setEmail(form.getEmail());
        entity.setCertificateTypeId(191);
        entity.setCertificateNo(form.getCertificateNo());
        entity.setSex(form.getSex());
        entity.setIsActive(1);
        entity.setDescription(form.getDescription());
        entity.setType(2);
        entity.setIdentity(form.getIdentity());
        entity.setRoleId(Integer.parseInt(value));
        entity.setOp_Pid(ControllerTool.getSessionInfo(request).getCurrentOrg().getPid());
        entity.setOp_User_Id(ControllerTool.getSessionInfo(request).getUser().getId());
        try {
            outPoliceService.updateOutpoliceByID(entity);
            this.operLogService.insertLog(OperLogEntity.EDIT_TYPE, "修改用户信息" + entity, ControllerTool.getLoginName(request), true,"办案场所");
        } catch (Exception e) {
            logger.error("Error on editing user!", e);
            this.operLogService.insertLog(OperLogEntity.EDIT_TYPE, "修改用户信息" + entity, ControllerTool.getLoginName(request), false,"办案场所");
            return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("修改失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("消息").addContent("修改成功!");
    }


    @RequestMapping(value = "/addOrg")
    @ResponseBody
    public MessageEntity add(@RequestBody OrganizationForm form, HttpServletRequest request,
                             HttpServletResponse response) throws Exception{
        logger.info("++++++++add++++++OrganizationForm=" + form.toString());
        String value =null;
        String parm1 = "outOrg";
        String parm2 = "org.outpolice";
        value = ommonConfigDetailSservice.listDetailsByName( parm1,parm2);
        OrganizationEntity entity = new OrganizationEntity();

        entity.setId(form.getId());
        entity.setName(form.getName());
        entity.setType("3");
        entity.setAddress(form.getAddress());
        entity.setTelephone(form.getTelephone());
        entity.setPostcode(form.getPostcode());
        entity.setOrgCode(form.getOrgCode());
        entity.setOrgAlias(form.getOrgAlias());
        entity.setOrgRep(form.getOrgRep());
        entity.setOrgStatus("1");
        entity.setParentId(Integer.parseInt(value));
        entity.setPid(ControllerTool.getSessionInfo(request).getCurrentOrg().getPid());
        entity.setOp_pid(ControllerTool.getSessionInfo(request).getCurrentOrg().getPid());
        entity.setOp_User_Id(ControllerTool.getSessionInfo(request).getUser().getId());
        try {
            organizationService.insertOrganization(entity);
            organizationService.refreshSessionOrg(ControllerTool.getSessionInfo(request)); // 刷新缓存
            this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "添加组织机构" + entity, ControllerTool.getLoginName(request), true, "办案场所");

        } catch (Exception e) {
            logger.error("Error on add organization!", e);
            this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "添加组织机构" + entity, ControllerTool.getLoginName(request), false, "办案场所");
            return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("新增部门失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("提醒").addContent("新增部门成功!");
    }



}
