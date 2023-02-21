package com.zhixin.zhfz.bacs.controller.person;


import com.zhixin.zhfz.bacs.entity.PersonEntity;
import com.zhixin.zhfz.bacs.form.PersonAddSscwForm;
import com.zhixin.zhfz.bacs.form.PersonForm;
import com.zhixin.zhfz.bacs.services.person.IPersonService;
import com.zhixin.zhfz.common.common.IdCardUtil;
import com.zhixin.zhfz.common.entity.MessageEntity;
import com.zhixin.zhfz.common.entity.OperLogEntity;
import com.zhixin.zhfz.common.entity.RoleEntity;
import com.zhixin.zhfz.common.entity.SessionInfo;
import com.zhixin.zhfz.common.services.Icase.ICaseService;
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
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/zhfz/bacs/person")
public class PersonController {

    private static final Logger logger = LoggerFactory.getLogger(PersonController.class);

    @Autowired
    private IPersonService personService;

    @Autowired
    private IOperLogService operLogService;


    @Autowired
    private ICaseService caseService;

    @RequestMapping(value = "/list")
    @ResponseBody
    public Map<String, Object> list(@RequestParam Map<String, Object> params, HttpServletRequest request)
            throws Exception {
        List<PersonEntity> persons = new ArrayList<PersonEntity>();
        int total = 0;
        Map<String, Object> map = ControllerTool.mapFilter(params);
        /*if (RoleEntity.DATA_AUTH_SELFAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 办案场所-本办案场所
            map.put("dataAuth", " p.area_id=" + ControllerTool.getCurrentArea(request).getId());
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuth(request)) || RoleEntity.DATA_AUTH_UP_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 管理员 -本办案场所及下级办案场所
            map.put("dataAuth", "p.area_id " + ControllerTool.getSessionInfo(request).getCurrentAndSubArea());
        }*/
        boolean flag=true;
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        if (RoleEntity.DATA_AUTH_SELFAREA == (ControllerTool.getRoleDataAuthForBacsConf(request))) {
            // 本办案场所
            map.put("dataAuth", "p.area_id=" + ControllerTool.getCurrentAreaID(request));
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuthForBacsConf(request))) {
            // 本办案场所及下级办案场所
            map.put("dataAuth", "p.area_id " + sessionInfo.getCurrentAndSubAreaInStr());
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEAREA == (ControllerTool.getRoleDataAuthForBacsConf(request))) {
            // 上级办案场所及其下级办案场所
            map.put("dataAuth", "p.area_id " + sessionInfo.getSuperAndSubAreaInStr());
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuthForBacsConf(request))) {
            // 本部门及下级部门
            map.put("dataAuth","p.op_pid like " + sessionInfo.getCurrentAndSubOrgPid());
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuthForBacsConf(request))) {
            // 上级部门及下级部门
            map.put("dataAuth","p.op_pid like " + sessionInfo.getSuperAndSubOrgPid());
        } else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuthForBacsConf(request))) {
            // 本部门
            map.put("dataAuth","p.op_pid = " + sessionInfo.getCurrentOrgPid());
        } else if(RoleEntity.DATA_AUTH_FULL == (ControllerTool.getRoleDataAuthForBacsConf(request))){
            //全部
        }else if(RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuthForBacsConf(request))){
            //本人
            map.put("dataAuth", " ( p.op_user_id=" + ControllerTool.getUserID(request) + " or p.id=" + ControllerTool.getUserID(request) + " ) ");
        }else{
            flag = false;
        }
        if (flag){
            persons = this.personService.list(map);
            total = this.personService.count(map);
        }

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", total);
        result.put("rows", persons);
        return result;
    }

    @RequestMapping(value = "/insert")
    @ResponseBody
    public MessageEntity insert(@RequestBody PersonForm form,HttpServletRequest request) throws Exception {

        try {
            System.out.println("+++++++add++++++PersonForm=" + form);
            PersonEntity entity = new PersonEntity();
            entity.setCertificateTypeId(form.getCertificateTypeId());
            entity.setCertificateNo(form.getCertificateNo());
            entity.setName(form.getName());
            entity.setOldName(form.getOldName());
            entity.setNickname(form.getNickname());
            entity.setSex(form.getSex());
            SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
            if(form.getBirth()!=null&&form.getBirth()!=""){
                entity.setBirth(dateFormat.parse(form.getBirth()));
            }else{
                if(form.getCertificateTypeId()==111){
                    Map<String,String> map=IdCardUtil.getBirAgeSex(form.getCertificateNo());
                    entity.setBirth(dateFormat.parse(map.get("birthday").toString()));
                }
            }
            entity.setEducation(form.getEducation());
            entity.setPolitic(form.getPolitic());
            entity.setOfficer(form.getOfficer());
            entity.setCountry(form.getCountry());
            entity.setNation(form.getNation());
            entity.setCensusRegister(form.getCensusRegister());
            entity.setAddress(form.getAddress());
            entity.setJob(form.getJob());
            entity.setJobTitle(form.getJobTitle());
            entity.setMobile(form.getMobile());
            entity.setTelephone(form.getTelephone());
            entity.setEmail(form.getEmail());
            entity.setQq(form.getQq());
            entity.setWeixin(form.getWeixin());
            entity.setWeibo(form.getWeibo());
            entity.setInternetInfo(form.getInternetInfo());
            entity.setPersonNo(form.getPersonNo());
            entity.setAreaId(form.getAreaId());
            String uuid = UUID.randomUUID().toString().replace("-", "");
            entity.setUuid(uuid);
            entity.setOpPid(ControllerTool.getSessionInfo(request).getCurrentOrg().getPid());
            entity.setOpUserId(ControllerTool.getSessionInfo(request).getUser().getId());

            personService.insert(entity);
            this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "添加人员信息" + entity,ControllerTool.getUser(request).getLoginName() , true,"办案场所");
        } catch (Exception e) {
            logger.error("Error on add enterprise!", e);
            return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("添加成功!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("提醒").addContent("添加失败!");
    }

    @RequestMapping(value = "/update")
    @ResponseBody
    public MessageEntity update(@RequestBody PersonForm form,HttpServletRequest request) throws Exception {
        try {
            System.out.println("+++++++update++++++PersonForm=" + form);
            PersonEntity entity = new PersonEntity();
            entity.setId(form.getId());
            entity.setCertificateTypeId(form.getCertificateTypeId());
            entity.setCertificateNo(form.getCertificateNo());
            entity.setName(form.getName());
            entity.setOldName(form.getOldName());
            entity.setNickname(form.getNickname());
            entity.setSex(form.getSex());
            SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
            if(form.getBirth()!=null&&form.getBirth()!=""){
                entity.setBirth(dateFormat.parse(form.getBirth()));
            }else{
                if(form.getCertificateTypeId()==111){
                    Map<String,String> map=IdCardUtil.getBirAgeSex(form.getCertificateNo());
                    entity.setBirth(dateFormat.parse(map.get("birthday").toString()));
                }
            }

            entity.setEducation(form.getEducation());
            entity.setPolitic(form.getPolitic());
            entity.setOfficer(form.getOfficer());
            entity.setCountry(form.getCountry());
            entity.setNation(form.getNation());
            entity.setCensusRegister(form.getCensusRegister());
            entity.setAddress(form.getAddress());
            entity.setJob(form.getJob());
            entity.setJobTitle(form.getJobTitle());
            entity.setMobile(form.getMobile());
            entity.setTelephone(form.getTelephone());
            entity.setEmail(form.getEmail());
            entity.setQq(form.getQq());
            entity.setWeixin(form.getWeixin());
            entity.setWeibo(form.getWeibo());
            entity.setInternetInfo(form.getInternetInfo());
            entity.setPersonNo(form.getPersonNo());
            entity.setAreaId(form.getAreaId());

            personService.update(entity);
            this.operLogService.insertLog(OperLogEntity.EDIT_TYPE, "修改人员信息" + entity, ControllerTool.getUser(request).getLoginName() , true,"办案场所");
        } catch (Exception e) {
            logger.error("Error on add enterprise!", e);
            return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("修改失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("提醒").addContent("修改成功!");
    }

    @RequestMapping(value = "/delete")
    @ResponseBody
    public MessageEntity delete(@RequestBody PersonForm form,HttpServletRequest request) throws Exception {
        System.out.println("+++++++delete++++++PersonForm=" + form.getId());
        PersonEntity entity = new PersonEntity();
        entity.setId(form.getId());
        try {
            personService.delete(entity.getId());
            this.operLogService.insertLog(OperLogEntity.DELETE_TYPE, "删除人员信息" + entity, ControllerTool.getUser(request).getLoginName() , true,"办案场所");
        } catch (Exception e) {
            logger.error("Error on add enterprise!", e);
            return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("删除失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("提醒").addContent("删除成功!");
    }

    @RequestMapping(value = "/insertPerson")
    @ResponseBody
    public MessageEntity insertPerson(@RequestBody PersonAddSscwForm form, HttpServletRequest request) throws Exception {
        try {
            System.out.println("+++++++add++++++PersonForm=" + form);
            PersonEntity entity = new PersonEntity();
            entity.setCertificateTypeId(form.getCertificateTypeId());
            entity.setCaseId(form.getCaseId());
            entity.setCertificateNo(form.getCertificateNo());
            entity.setName(form.getName());
            entity.setSex(form.getSex());
            SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
            entity.setCountry(form.getCountry());
            entity.setNation(form.getNation());
            entity.setAreaId(ControllerTool.getCurrentArea(request).getId());
            String uuid = UUID.randomUUID().toString().replace("-", "");
            entity.setUuid(uuid);
            entity.setOpPid(ControllerTool.getSessionInfo(request).getCurrentOrg().getPid());
            entity.setOpUserId(ControllerTool.getSessionInfo(request).getUser().getId());

            personService.insert(entity);
            this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "添加人员信息" + entity,ControllerTool.getUser(request).getLoginName() , true,"办案场所");
        } catch (Exception e) {
            logger.error("Error on add enterprise!", e);
            return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("添加失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("提醒").addContent("添加成功!");
    }
    @RequestMapping(value = "/updatePerson")
    @ResponseBody
    public MessageEntity updatePerson(@RequestBody PersonAddSscwForm form,HttpServletRequest request) throws Exception {
        try {
            System.out.println("+++++++update++++++PersonForm=" + form);
            PersonEntity entity = new PersonEntity();
            entity.setId(form.getPersonId());
            entity.setCertificateTypeId(form.getCertificateTypeId());
            entity.setCertificateNo(form.getCertificateNo());
            entity.setName(form.getName());
            entity.setSex(form.getSex());
            SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
            entity.setCountry(form.getCountry());
            entity.setNation(form.getNation());
            //entity.setAreaId(form.getAreaId());
            personService.update(entity);
            this.operLogService.insertLog(OperLogEntity.EDIT_TYPE, "修改人员信息" + entity, ControllerTool.getUser(request).getLoginName() , true,"办案场所");
        } catch (Exception e) {
            logger.error("Error on add enterprise!", e);
            return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("修改失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("提醒").addContent("修改成功!");
    }

    @RequestMapping(value = "/deletePerson")
    @ResponseBody
    public MessageEntity deletePerson(@RequestBody PersonAddSscwForm form,HttpServletRequest request) throws Exception {
        System.out.println("+++++++delete++++++PersonForm=" + form.getId());
        PersonEntity entity = new PersonEntity();
        entity.setId(form.getId());
        try {
            personService.delete(entity.getId());
            this.operLogService.insertLog(OperLogEntity.DELETE_TYPE, "删除人员信息" + entity, ControllerTool.getUser(request).getLoginName() , true,"办案场所");
        } catch (Exception e) {
            logger.error("Error on add enterprise!", e);
            return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("删除失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("提醒").addContent("删除成功!");
    }


}
