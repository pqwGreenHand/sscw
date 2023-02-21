package com.zhixin.zhfz.bacsapp.controller.security;

import com.zhixin.zhfz.bacs.entity.SecurityPhotoEntity;
import com.zhixin.zhfz.bacs.entity.SerialEntity;
import com.zhixin.zhfz.bacs.form.SecurityForm;
import com.zhixin.zhfz.bacs.services.crimedefine.ICrimeDefineService;
import com.zhixin.zhfz.bacs.services.serial.ISerialService;
import com.zhixin.zhfz.bacsapp.dao.SecurityCheck.ISecurityCheckAppMapper;
import com.zhixin.zhfz.bacsapp.entity.SecurityAppEntity;
import com.zhixin.zhfz.common.entity.*;
import com.zhixin.zhfz.common.form.FileUploadForm;
import com.zhixin.zhfz.common.services.Icase.ICaseService;
import com.zhixin.zhfz.common.services.fileServiceConfig.IFileConfigService;
import com.zhixin.zhfz.common.services.operLog.IOperLogService;
import com.zhixin.zhfz.common.services.user.IUserService;
import com.zhixin.zhfz.common.utils.ControllerTool;
import com.zhixin.zhfz.sacw.form.UserNoSearchForm;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: zhfz
 * @description: 安全检查控制中心
 * @author: xcf
 * @create: 2019-04-01 15:19
 **/
@Controller
@RequestMapping("/zhfz/bacsapp/securityCheck")
public class SecurityAppController {

    private static final Logger logger = LoggerFactory.getLogger(SecurityAppController.class);

    @Autowired
    private ISecurityCheckAppMapper securityCheckAppMapper;

    @Autowired
    private ICrimeDefineService crimeDefineService;

    @Autowired
    private ICaseService caseService;

    @Autowired
    private ISerialService interrogateSerialService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IOperLogService operLogService;

    @Autowired
    private IFileConfigService fileConfigService;
    @Autowired
    private ISerialService serialService;


    @RequestMapping(value = "/securityList")
    @ResponseBody
    public Map<String, Object> list(@RequestParam Map<String, Object> param,HttpServletRequest request) throws Exception {

        Map<String, Object> map = ControllerTool.mapFilter(param);

        Map<String, Object> map1 = getAuthMethod(request);
        List<SecurityAppEntity> securityList =null;

        int count=0;

        if ("true".equals(map1.get("flag"))){
            map.put("dataAuth",map1.get("dataAuth"));
        }

        securityList = this.securityCheckAppMapper.list(map);
        count = this.securityCheckAppMapper.count(map);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", count);
        result.put("rows", securityList);

        return result;
    }



    public  Map<String,Object> getAuthMethod(HttpServletRequest request){

        Map<String,Object> map = new HashMap<String,Object>();

        boolean flag = true;
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);

        if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuth(request))) {
            // 办案人员-本人
//            map.put("dataAuth", " locate('," + ControllerTool.getUserID(request) + ",', ic.xbmj_ids) or (ic.zbmj_id= "+
//                    ControllerTool.getUserID(request) +" or ins.in_register_user_id=" + ControllerTool.getUserID(request) +
//                    " or ins.out_register_user_id=" + ControllerTool.getUserID(request) + " ) ");
            map.put("dataAuth", " ( ins.in_register_user_id=" + ControllerTool.getUserID(request) +
                    " or ins.out_register_user_id=" + ControllerTool.getUserID(request) + " ) ");
            map.put("flag","true");
        } else if (RoleEntity.DATA_AUTH_SELFAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 办案场所-本办案场所
            map.put("dataAuth", " ins.area_id=" + ControllerTool.getCurrentAreaID(request));
            map.put("flag","true");
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 管理员 -本办案场所及下级办案场所
            map.put("dataAuth", "ins.area_id=" + sessionInfo.getCurrentAndSubAreaInStr());
            map.put("flag","true");
        }
        else if (RoleEntity.DATA_AUTH_UP_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 上级办案场所及其下级办案场所
            map.put("dataAuth", " ins.area_id=" + ControllerTool.getSessionInfo(request).getSuperAndSubAreaInStr());
            map.put("flag","true");
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 公安领导-本部门及下级部门
            map.put("dataAuth","ins.op_pid like " + ControllerTool.getSessionInfo(request).getCurrentAndSubOrgPid());
//                    +
//                    " or ic.zbmj_pid like " + ControllerTool.getSessionInfo(request).getCurrentAndSubOrgPid() +
//                    " or ic.op_pid like " + ControllerTool.getSessionInfo(request).getCurrentAndSubOrgPid());
            map.put("flag","true");
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 法制人员-上级部门及下级部门
            map.put("dataAuth","ins.op_pid like " + ControllerTool.getSessionInfo(request).getSuperAndSubOrgPid());
//            +
//                    " or ic.zbmj_pid like " + ControllerTool.getSessionInfo(request).getSuperAndSubOrgPid() +
//                    " or ic.op_pid like " + ControllerTool.getSessionInfo(request).getSuperAndSubOrgPid());
            map.put("flag","true");
        } else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuth(request))) {
            // 本部门
            map.put("dataAuth","ins.op_pid =" + sessionInfo.getCurrentOrgPid());
//                    +
//                    " or ic.zbmj_pid =" + sessionInfo.getCurrentOrgPid() +
//                    " or ic.op_pid =" + sessionInfo.getCurrentOrgPid());
            map.put("flag","true");
        } else {
            map.put("flag","false");
        }

        return map;
    }


    @RequestMapping(value = "/safeCheckStatus")
    @ResponseBody
    public MessageEntity safeCheckStatus(@RequestParam Long serialId, HttpServletRequest request) throws Exception {

//        Map<String, Object> map = ControllerTool.mapFilter(param);

        try {
            serialService.updateStatusById(serialId,1);

            SecurityAppEntity securityAppEntity = new SecurityAppEntity();

            securityAppEntity.setSerialId(serialId);

            this.securityCheckAppMapper.updateSecurityUpdateTime(securityAppEntity);

        } catch (Exception e) {
            logger.error("安检失败!", e);
            return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("安检失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("提示").addContent("安检成功!");

    }


    @RequestMapping(value = "/selectPersonList")
    @ResponseBody
    public  Map<String, Object> selectPersonList(HttpServletRequest request) throws Exception {

//        Long areaId= (Long) request.getSession().getAttribute("areaId");

        Map<String, Object> map = new HashMap<>();
        Map<String, Object> map1 = new HashMap<>();

        Map<String, Object> map2=getAuthMethod1(request);

        List<SecurityAppEntity> list = null;

        map1.put("dataAuth",map2.get("dataAuth"));

        if ("true".equals(map2.get("flag"))){


            list = this.securityCheckAppMapper.getSecurityPersonList(map1);
        }

        map.put("list",list);

        return map;

    }


    @RequestMapping(value = "/selectPersonBySerialId")
    @ResponseBody
    public  Map<String, Object> selectPersonBySerialId(@RequestParam Long serialId, HttpServletRequest request) throws Exception {


        Map<String, Object> map = new HashMap<>();
        Map<String, Object> map1 = new HashMap<>();

//        Map<String, Object> map2 = getAuthMethod1(request);

        map1.put("serialId",serialId);

//        if ("true".equals(map2.get("flag"))){
//            map1.put("dataAuth",map2.get("dataAuth"));
//        }

        SecurityAppEntity info = this.securityCheckAppMapper.selectPersonBySerialId(map1);

        map.put("info",info);

        return map;

    }


    public  Map<String,Object> getAuthMethod1(HttpServletRequest request){

        Map<String,Object> map = new HashMap<String,Object>();

        boolean flag = true;
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);

        if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuth(request))) {
            // 办案人员-本人
            map.put("dataAuth", "(s.in_register_user_id=" + ControllerTool.getUserID(request) +
                    " or s.out_register_user_id=" + ControllerTool.getUserID(request) + " ) ");
            map.put("flag","true");
        } else if (RoleEntity.DATA_AUTH_SELFAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 办案场所-本办案场所
            map.put("dataAuth", " s.area_id=" + ControllerTool.getCurrentAreaID(request));
            map.put("flag","true");
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 管理员 -本办案场所及下级办案场所
            map.put("dataAuth", "s.area_id" + sessionInfo.getCurrentAndSubAreaInStr());
            map.put("flag","true");
        }
        else if (RoleEntity.DATA_AUTH_UP_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 上级办案场所及其下级办案场所
            map.put("dataAuth", " s.area_id" + ControllerTool.getSessionInfo(request).getSuperAndSubAreaInStr());
            map.put("flag","true");
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 公安领导-本部门及下级部门
            map.put("dataAuth","s.op_pid like " + ControllerTool.getSessionInfo(request).getCurrentAndSubOrgPid());
            map.put("flag","true");
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 法制人员-上级部门及下级部门
            map.put("dataAuth","s.op_pid like " + ControllerTool.getSessionInfo(request).getSuperAndSubOrgPid());
            map.put("flag","true");
        } else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuth(request))) {
            // 本部门
            map.put("dataAuth","s.op_pid =" + sessionInfo.getCurrentOrgPid());
            map.put("flag","true");
        } else {
            map.put("flag","false");
        }

        return map;
    }



    @RequestMapping(value = "/searchUser")
    @ResponseBody
    public UserEntity searchUser(@RequestBody UserNoSearchForm form) throws Exception {
        UserEntity user = userService.getUserByCertificateNo(form.getUserNo());
        return user;
    }


    @RequestMapping(value = "/insert")
    @ResponseBody
    public MessageEntity insert(SecurityForm form, HttpServletRequest request) throws Exception {

        System.err.println("form.getInvolvedReason()====" + form.getCount());
        SecurityAppEntity entity = new SecurityAppEntity();
        // 添加案件
        CaseEntity icase = new CaseEntity();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Map<String, Object> params = new HashMap<>();
//        params.put("caseNatureId", form.getInvolvedReason());
//        System.err.println("form.getInvolvedReason()"+form.getInvolvedReason());
//        int caseType = crimeDefineService.getCrimeTypeId(params);
//        System.err.println("LawCaseId====="+form.getLawCaseId());
//        icase.setId(form.getLawCaseId());
//        icase.setAjlx(caseType);
//        icase.setAb(Integer.parseInt(form.getInvolvedReason()));
//        System.err.println("**********************************icase=" + icase);
//        caseService.updateCase(icase);
        SerialEntity serial = new SerialEntity();
        serial.setSerialNo(form.getSerialNo());
        try {
            SerialEntity interrogateSerial = interrogateSerialService.getSerialByNo(serial);
//            if(!maker.equals("fj")){
//                if (interrogateSerial.getCaseId() == null || interrogateSerial.getCaseId() <= 0) {
//                    interrogateSerialService.updateStatusById(interrogateSerial.getId(),interrogateSerial.getStatus());
//                }
//            }
            System.out.println("+++++++interrogateSerial++++++" + interrogateSerial);
            // 根据UserNo查找User
            UserEntity user = userService.getUserByCertificateNo(form.getCheckUserNo());
            System.out.println("+++++++user++++++" + user);
            // 查询该嫌疑人几次安检
            entity.setSerialId(interrogateSerial.getId());
            entity.setCheckUserId(user.getId());
            entity.setCheckedStartTime(form.getCheckedStartTime());
            entity.setCheckedEndTime(form.getCheckedEndTime());
            entity.setAreaId(ControllerTool.getSessionInfo(request).getCurrentArea().getId());
            entity.setMedicalHistory(form.getMedicalHistory());
            entity.setCheckType(0);
            entity.setPhysicalDescription(form.getPhysicalDescription());
            if (StringUtils.isNotEmpty(form.getPhysicalDescription())){
                entity.setPhysical(1);
            }else {
                entity.setPhysical(0);

            }
            entity.setInjury(form.getInjury());
            entity.setInjuryDescription(form.getInjuryDescription());
            entity.setDangerous(form.getDangerous());
            entity.setOtherDescription(form.getOtherDescription());
            entity.setCreatedTime(form.getCreatedTime());
            entity.setUpdatedTime(form.getUpdatedTime());
            entity.setHasInjury(form.getHasInjury());
            entity.setHasPhoto(form.getHasPhoto());
            entity.setHasWine(form.getHasWine());
            entity.setAgainReason(form.getAgainReason());
            serial.setId(entity.getSerialId());
            serial.setStatus(1);
            entity.setUuid(form.getUuid());
            entity.setCount(String.valueOf(1));
            entity.setOpPid(ControllerTool.getSessionInfo(request).getCurrentOrg().getPid());
            entity.setOpUserId(ControllerTool.getSessionInfo(request).getUser().getId());
            if (icase.getId() != null && icase.getId() > 0) {
                entity.setCaseId(icase.getId());
            } else {
                entity.setCaseId(interrogateSerial.getCaseId());
            }
            System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&entity=" + entity);
            securityCheckAppMapper.insert(entity);


            if(form.getFiles()!=null&&form.getFiles().length>0) {
                //循环获取file数组中得文件
                for (int i = 0; i < form.getFiles().length; i++) {

                    FileUploadForm form1 = new FileUploadForm();
                    String filename = "";
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
                    filename = "Appaj-" + dateFormat.format(new java.util.Date()) + i + ".jpg";
                    form1.setSysType("ba");
                    form1.setFileType("aj");
                    form1.setUuid(form.getUuid());
                    form1.setSysId(String.valueOf(ControllerTool.getSessionInfo(request).getCurrentArea().getId()));
                    form1.setFileName(filename);
                    form1.setFile(form.getFiles()[i]);
                    fileConfigService.upload(form1);
                }
            }


            this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "app添加安检信息" + entity, "用户", true,OperLogEntity.SYSTEM_BACS);
            System.out.println("###########################################serial=" + serial);
            interrogateSerialService.changestatus(interrogateSerial.getId(), 1);
            this.operLogService.insertLog(OperLogEntity.EDIT_TYPE, "修改状态信息" + entity, "system", true,OperLogEntity.SYSTEM_BACS);
            // 合成作战添加案件ID  此处已经去掉合成作战的代码，后期需要再加

            //更改中控门禁权限  此处已经去掉对接代码，后期需要再加
        } catch (Exception e) {
            logger.error("Error on inserting security", e);
            return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("添加失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("提醒").addContent("添加成功!").addCallbackData(entity);
    }


    @RequestMapping(value = "/AppPicture")
    @ResponseBody
    public MessageEntity inGetPicture(@RequestParam(value="file",required=false) MultipartFile file, HttpServletRequest request,String uuid,int areaId,String count) throws Exception {
        try {
            long time = System.currentTimeMillis();
            String nowTimeStamp = String.valueOf(time / 1000);
            logger.info("### ingetpicture ImageForm= " + file);
            String  fileName = "aj-photo"  + nowTimeStamp + ".jpg";
            fileConfigService.upload(FileUploadForm.of("ba", "aj", uuid, areaId, fileName, file));
            SecurityPhotoEntity securityPhotoEntity  = new SecurityPhotoEntity();
            securityPhotoEntity.setSecurityUUID(uuid);
            securityPhotoEntity.setPicName(fileName);
            securityPhotoEntity.setType(1);
            securityPhotoEntity.setCount(count);
            securityCheckAppMapper.insertSecurityMarkes(securityPhotoEntity);
        } catch (Exception e) {
            return new MessageEntity().addCode(1).addIsError(true).addTitle("提示").addContent("照片上传失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("提示").addContent("照片上传成功！");
    }





}
