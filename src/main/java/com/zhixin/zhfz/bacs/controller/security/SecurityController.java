package com.zhixin.zhfz.bacs.controller.security;

import com.alibaba.fastjson.JSON;
import com.zhixin.zhfz.bacs.entity.*;
import com.zhixin.zhfz.bacs.form.SecurityForm;
import com.zhixin.zhfz.bacs.services.crimedefine.ICrimeDefineService;
import com.zhixin.zhfz.bacs.services.cuff.ICuffService;
import com.zhixin.zhfz.bacs.services.personalconfig.IPersonalConfigDetailService;
import com.zhixin.zhfz.bacs.services.security.ISecurityService;
import com.zhixin.zhfz.bacs.services.serial.ISerialService;
import com.zhixin.zhfz.bacs.services.serialVideoMapping.ISerialVideoMappingService;
import com.zhixin.zhfz.common.common.HttpClientUtil;
import com.zhixin.zhfz.common.entity.*;
import com.zhixin.zhfz.common.form.FileUploadForm;
import com.zhixin.zhfz.common.form.IDForm;
import com.zhixin.zhfz.common.services.Icase.ICaseService;
import com.zhixin.zhfz.common.services.authority.IAuthorityService;
import com.zhixin.zhfz.common.services.fileServiceConfig.IFileConfigService;
import com.zhixin.zhfz.common.services.operLog.IOperLogService;
import com.zhixin.zhfz.common.services.user.IUserService;
import com.zhixin.zhfz.common.utils.ControllerTool;
import com.zhixin.zhfz.common.utils.PropertyUtil;
import com.zhixin.zhfz.common.utils.WaterMarkUtil;
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
import java.awt.*;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/zhfz/bacs/security")
public class SecurityController {

    private static final Logger logger = LoggerFactory.getLogger(SecurityController.class);

    @Autowired
    private ISecurityService securityService;

    @Autowired
    private IOperLogService operLogService;

    @Autowired
    private ISerialService interrogateSerialService;

    @Autowired
    private IUserService userService;

    @Autowired
    private ICaseService caseService;


    @Autowired
    private ICrimeDefineService crimeDefineService;

    @Autowired
    private IFileConfigService fileConfigService;

    @Autowired
    private IAuthorityService authorityService;

    @Autowired
    private ICuffService cuffService;

    @Autowired
    private IPersonalConfigDetailService personalConfigService;
    @Autowired
    private ISerialVideoMappingService serialVideoMappingService;
    @RequestMapping(value = "/list")
    @ResponseBody
    public Map<String, Object> list(@RequestParam Map<String, Object> params, HttpServletRequest request)
            throws Exception {
        List<SecurityEntity> securitys = new ArrayList<SecurityEntity>();
        int count = 0;
        boolean flag = true;
        Map<String, Object> map = ControllerTool.mapFilter(params);
        // map.put("usePage", true);
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        if (RoleEntity.DATA_AUTH_SELFAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 本办案场所
            map.put("dataAuth", "ia.id=" + ControllerTool.getCurrentAreaID(request));
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 本办案场所及下级办案场所
            map.put("dataAuth", "ia.id " + sessionInfo.getCurrentAndSubAreaInStr());
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 上级办案场所及其下级办案场所
            map.put("dataAuth", "ia.id " + sessionInfo.getSuperAndSubAreaInStr());
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 本部门及下级部门
            map.put("dataAuth","( s.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                + " or ic.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                + " or ins.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()
            + " ) ");
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 上级部门及下级部门
            map.put("dataAuth"," ( s.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
                    + " or ic.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
                    + " or ins.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
            +" ) ");
        } else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuth(request))) {
            // 本部门
            map.put("dataAuth","( s.op_pid = " + sessionInfo.getCurrentOrgPid()
                    + " or ic.op_pid = " + sessionInfo.getCurrentOrgPid()
                    + " or ins.op_pid = " + sessionInfo.getCurrentOrgPid()
            +" ) ");
        } else if(RoleEntity.DATA_AUTH_FULL == (ControllerTool.getRoleDataAuth(request))){
            //全部
        }else if(RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuth(request))){
            //本人
            map.put("dataAuth","( s.op_user_id = " + ControllerTool.getUserID(request)
                +" or s.check_user_id = " + ControllerTool.getUserID(request)
                +" or ic.cjr = " + ControllerTool.getUserID(request)
                +" or ic.zbmj_id = " + ControllerTool.getUserID(request)
                + " or locate('," + ControllerTool.getUserID(request) + ",', ic.xbmj_ids)"
                +" or ins.in_register_user_id = " + ControllerTool.getUserID(request)
                +" or ins.out_register_user_id = " + ControllerTool.getUserID(request)
                +" or ins.send_user_id = " + ControllerTool.getUserID(request)
            + " ) ");
        }else{
            flag = false;
        }
        if (flag) {
            securitys = this.securityService.list(map);
            count = this.securityService.count(map);
        }
        System.err.println("securitys"+securitys);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", count);
        result.put("rows", securitys);
        return result;
    }

    @RequestMapping(value = "/insert")
    @ResponseBody
    public MessageEntity insert(@RequestBody SecurityForm form, HttpServletRequest request,String maker) throws Exception {
        System.err.println("form.getInvolvedReason()====" + form.getCount());
        SecurityEntity entity = new SecurityEntity();

        SerialEntity serial = new SerialEntity();
        serial.setSerialNo(form.getSerialNo());
        try {
            SerialEntity interrogateSerial = interrogateSerialService.getSerialByNo(serial);
            if(!maker.equals("fj")){
                if (interrogateSerial.getCaseId() == null || interrogateSerial.getCaseId() <= 0) {
                    interrogateSerialService.updateStatusById(interrogateSerial.getId(),interrogateSerial.getStatus());
                }
            }
            System.out.println("+++++++interrogateSerial++++++" + interrogateSerial);
            // 根据UserNo查找User
            /*UserEntity user = userService.getUserByCertificateNo(form.getCheckUserNo());
            System.out.println("+++++++user++++++" + user);*/
            // 查询该嫌疑人几次安检
            entity.setSerialId(interrogateSerial.getId());
            entity.setCheckUserId(form.getCheckUserId());
            entity.setCheckedStartTime(form.getCheckedStartTime());
            entity.setCheckedEndTime(form.getCheckedEndTime());
            entity.setAreaId(Integer.parseInt(form.getAreaId()));
            entity.setMedicalHistory(form.getMedicalHistory());
            entity.setCheckType(form.getType());
            entity.setPhysicalDescription(form.getPhysicalDescription());
            entity.setPhysical(form.getPhysical());
            entity.setInjury(form.getInjury());
            entity.setInjuryDescription(form.getInjuryDescription());
            entity.setDangerous(form.getDangerous());
            entity.setOtherDescription(form.getOtherDescription());
            entity.setDangerous(form.getDangerous());
            entity.setCreatedTime(form.getCreatedTime());
            entity.setUpdatedTime(form.getUpdatedTime());
            entity.setHasInjury(form.getInjury());
            entity.setHasPhoto(form.getHasPhoto());
            entity.setHasWine(form.getHasWine());
            entity.setAgainReason(form.getAgainReason());
            serial.setId(entity.getSerialId());
            serial.setStatus(1);
            entity.setUuid(form.getUuid());
            entity.setCount(form.getCount());
            entity.setOpPid(ControllerTool.getSessionInfo(request).getCurrentOrg().getPid());
            entity.setOpUserId(ControllerTool.getSessionInfo(request).getUser().getId().toString());
            entity.setCaseId(form.getLawCaseId());
            System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&entity=" + entity);
            securityService.insert(entity);
            this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "添加安检信息" + entity, "system", true,OperLogEntity.SYSTEM_BACS);
            System.out.println("###########################################serial=" + serial);
            interrogateSerialService.changestatus(interrogateSerial.getId(), 1);
            this.operLogService.insertLog(OperLogEntity.EDIT_TYPE, "修改状态信息" + entity, "system", true,OperLogEntity.SYSTEM_BACS);
            // 合成作战添加案件ID  此处已经去掉合成作战的代码，后期需要再加
            String uuid= interrogateSerial.getUuid();
            logger.info("uuid+++++++++++++++++++++"+uuid);
            logger.info("市局人员信息更新........");
            try {
                String sjhcurl = PropertyUtil.getContextProperty("sjhc.url") + "";
                String url=sjhcurl+"/updateserial/"+uuid+"&aqjc";
                HttpClientUtil.get(url,"");
            } catch (Exception e) {
                logger.info("ryxxInfo error " + e.getMessage());
                logger.error(e.getMessage(), e);
            }
            logger.info("市局人员信息更新结束........");

            //更改中控门禁权限  此处已经去掉对接代码，后期需要再加
        } catch (Exception e) {
            logger.error("Error on inserting security", e);
            return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("添加失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("提醒").addContent("添加成功!").addCallbackData(entity);
    }
    @RequestMapping(value = "/listHistory")
    @ResponseBody
    public Map<String, Object> listHistory(@RequestParam Map<String, Object> params, HttpServletRequest request) throws Exception {
        System.err.println("+++++++listHistory++++++IDForm=" + params);
        List<SecurityEntity> securityEntities = new ArrayList<SecurityEntity>();
        securityEntities = securityService.listHistory(params);
        Map<String, Object> map = new HashMap<>();
        map.put("rows",securityEntities != null?securityEntities:new ArrayList<SecurityEntity>() );
        map.put("total", securityEntities != null?securityEntities.size():0);
        return map;
    }
    @RequestMapping(value = "/getcaseidbyorderid")
    @ResponseBody
    public MessageEntity getCaseIdByOrderId(@RequestBody IDForm form) throws Exception {
        System.err.println("+++++++getCaseIdByOrderId++++++IDForm=" + form.getIntID());
        int caseId = 0;
        SerialEntity serial = new SerialEntity();
        try {
            serial = interrogateSerialService.getCaseIdByOrderId(form.getIntID());
            if (serial != null) {
                System.err.println("+++++++getCaseIdByOrderId++++++serial=" + serial);
                caseId = serial.getCaseId();
            }
        } catch (Exception e) {
            return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("getCaseIdByOrderId失败!")
                    .addCallbackData(caseId);
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("提醒").addContent("getCaseIdByOrderId成功!")
                .addCallbackData(caseId);
    }
    private ResultEntity createSerialVideoMappingInfo(SerialEntity entity) throws Exception {
        ResultEntity result = new ResultEntity();
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("serialId", entity.getId());
            List<SerialVideoMappingEntity> list = serialVideoMappingService.list(map);
            if (list.size() <= 0) {
                map.put("areaId", entity.getAreaId());
                map.put("type", "storage");
                list = serialVideoMappingService.getServerConfig(map);
                if (list.size() > 0) {
                    SerialVideoMappingEntity sentity = list.get(0);
                    sentity.setSerialId(entity.getId());
                    sentity.setConfigId(list.get(0).getId());
                    serialVideoMappingService.insert(sentity);
                }
            }
            result.setStatus(true);
        } catch (Exception e) {
            result.setStatus(false);
            result.setMessage(e.getMessage());
        }
        return result;
    }
    @RequestMapping(value = "/getImages")
    @ResponseBody
    public List<SecurityPhotoEntity> getImages(@RequestParam Map<String, Object> map)
            throws Exception {
        for(String s:map.keySet()){
            System.err.println(s+"======"+map.get(s));
        }
        List<SecurityPhotoEntity> list =  securityService.getImages(map);
        System.err.println(list);
        return list;
    }
    @RequestMapping(value = "/insertPersonData")
    @ResponseBody
    public MessageEntity insertPersonData(@RequestBody Map<String, Object> map, HttpServletRequest request)
            throws Exception {
       /* for(String s : map.keySet()){
            System.err.println(s+"=============="+map.get(s));
        }*/
        String outImgPath = "";
        String waterMarkContent = "";
        StringBuilder sb = new StringBuilder();
        try {
            int ids = Integer.parseInt(request.getParameter("ids"));
            int starts = Integer.parseInt(request.getParameter("starts"));
            String securityUUID = map.get("uuid").toString();
            String areaId = map.get("areaId").toString();
            String count = map.get("count").toString();
            System.err.println(securityUUID);
            System.err.println(areaId);
            System.err.println(count);
            String textTop = "";
            String textLeft = "";
            int textsTop = 0;
            int textsLeft = 0;
            if (starts != 1) {
                waterMarkContent = (String) map.get("content");
                sb.append(waterMarkContent).append(",");
                textTop = (String) map.get("textTop");
                textLeft = (String) map.get("textLeft");
                textsTop = (int) Math.floor(Double.valueOf(textTop));
                textsLeft = (int) Math.floor(Double.valueOf(textLeft));
            }
            String logoboxsWidth = (String) map.get("logoboxWidth");
            String logoboxsHeight = (String) map.get("logoboxHeight");
            int backWidth = (int) Math.floor(Double.valueOf(logoboxsWidth));
            int backHeight = (int) Math.floor(Double.valueOf(logoboxsHeight));
            String realPath = request.getSession().getServletContext().getRealPath("/");
            logger.info("================the web app real path is : {}", realPath);
            String srcImgPath = realPath + "\\zhfz\\bacs\\security\\image\\demo4.jpg";
           /* System.err.println("srcImgPath"+srcImgPath);
            String dirPath = PropertyUtil.getContextProperty("checkbodyFileSavePath") + Utils.getDatePath()
                    + File.separator;
            FileUtil.createDir(dirPath);
            outImgPath = dirPath + "person.png";*/
            long time = System.currentTimeMillis();
            String nowTimeStamp = String.valueOf(time / 1000);
            String  fileName = "aj-markes"  + nowTimeStamp + ".jpg";
            if (starts != 1) {
                File file = WaterMarkUtil.mark(textsTop, textsLeft, backWidth, backHeight, srcImgPath,Color.red,
                        waterMarkContent);
                 fileConfigService.upload(FileUploadForm.of("ba", "aj", securityUUID, Integer.parseInt(areaId), fileName, file));
                outImgPath = file.getAbsolutePath();
            }
            for (int i = 1; i < ids + 1; i++) {
                if (starts != 1) {
                    if (i <= ids) {
                        srcImgPath = outImgPath;
                        waterMarkContent = (String) map.get("content" + i);
                        textTop = (String) map.get("textTop" + i);
                        textsTop = (int) Math.floor(Double.valueOf(textTop));
                        textLeft = (String) map.get("textLeft" + i);
                        textsLeft = (int) Math.floor(Double.valueOf(textLeft));
                        sb.append(waterMarkContent).append(",");
                        File file = WaterMarkUtil.mark(textsTop, textsLeft, backWidth, backHeight, srcImgPath,
                                Color.red, waterMarkContent);
                        fileConfigService.upload(FileUploadForm.of("ba", "aj", securityUUID, Integer.parseInt(areaId), fileName, file));
                    }
                } else {
                    if (i < ids + 1) {
                        waterMarkContent = (String) map.get("content" + i);
                        textTop = (String) map.get("textTop" + i);
                        textsTop = (int) Math.floor(Double.valueOf(textTop));
                        textLeft = (String) map.get("textLeft" + i);
                        textsLeft = (int) Math.floor(Double.valueOf(textLeft));
                        File file = WaterMarkUtil.mark(textsTop, textsLeft, backWidth, backHeight, srcImgPath,
                                Color.red, waterMarkContent);
                        fileConfigService.upload(FileUploadForm.of("ba", "aj", securityUUID, Integer.parseInt(areaId), fileName, file));
                        srcImgPath = outImgPath;
                    }
                }
            }
			/*
			 * }else{ return new
			 * MessageEntity().addCode(1).addIsError(false).addTitle("提醒").
			 * addContent("保存图片路径不存在!"); }
			 */
            sb.append(",").append("###").append(outImgPath);
            Map mapParam = new HashMap();
            mapParam.put("securityUUID",securityUUID);
            mapParam.put("type",0);
            SecurityPhotoEntity securityPhotoEntity  = new SecurityPhotoEntity();
            securityPhotoEntity.setSecurityUUID(securityUUID);
            securityPhotoEntity.setPicName(fileName);
            securityPhotoEntity.setType(0);
            securityPhotoEntity.setCount(count);
            securityPhotoEntity.setOpPid(ControllerTool.getSessionInfo(request).getCurrentOrg().getPid());
            securityPhotoEntity.setOpUserId(ControllerTool.getSessionInfo(request).getUser().getId().toString());
            securityService.insertSecurityMarkes(securityPhotoEntity);
        } catch (Exception e) {
            logger.error("Error on add enterprise!", e);
            return new MessageEntity().addCode(1).addIsError(true).addTitle("提醒").addContent("添加失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("提醒").addContent("添加成功!")
                .addCallbackData(sb.toString().replace(",,", ""));
    }

    @RequestMapping(value = "/ingetpicture")
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
            //新添加字段
            securityPhotoEntity.setOpPid(ControllerTool.getSessionInfo(request).getCurrentOrg().getPid());
            securityPhotoEntity.setOpUserId(ControllerTool.getSessionInfo(request).getUser().getId().toString());
            securityService.insertSecurityMarkes(securityPhotoEntity);
        } catch (Exception e) {
            return new MessageEntity().addCode(1).addIsError(true).addTitle("提示").addContent("照片上传失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("提示").addContent("照片上传成功！");
    }


    /**
     *
     * 修改人身检查数据
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public MessageEntity update(@RequestBody Map<String,Object> param,HttpServletRequest request){

        Map<String, Object> map = ControllerTool.mapFilter(param);

        SecurityEntity entity = new SecurityEntity();

        entity.setId(Integer.parseInt(map.get("securityId")+""));

        //根据警号查询id
        UserEntity userEntity = userService.queryUserByCertNo(map.get("checkUserNo")+"");

        entity.setCheckUserId(userEntity.getId());

        if(map.get("medicalHistory") != "" && map.get("medicalHistory") != null){
            entity.setMedicalHistory( map.get("medicalHistory").toString());
        }else{

        }

        if(map.get("physicalDescription") != "" && map.get("physicalDescription") != null){
            entity.setPhysicalDescription( map.get("physicalDescription").toString());
        }

        if(map.get("dangerous") != "" && map.get("dangerous") != null){
            entity.setDangerous(map.get("dangerous").toString());
        }

        if(map.get("injuryDescription") != "" && map.get("injuryDescription") != null){
            entity.setInjuryDescription( map.get("injuryDescription").toString());
        }

        if(map.get("otherDescription") != "" && map.get("otherDescription") != null){
            entity.setOtherDescription( map.get("otherDescription").toString());
        }

        if (map.get("hasInjury") != "" && map.get("hasInjury") != null){
            entity.setHasInjury(Integer.parseInt(map.get("hasInjury")+""));
        }

        if (map.get("hasWine") != "" && map.get("hasWine") != null){
            entity.setHasWine(Integer.parseInt(map.get("hasWine")+""));
        }

        if (map.get("hasPhoto") != "" && map.get("hasPhoto") != null){
            entity.setHasPhoto(Integer.parseInt(map.get("hasPhoto")+""));
        }

        try{
            securityService.update(entity);
        }catch (Exception e){
            return new MessageEntity().addCode(0).addIsError(false).addTitle("提示").addContent("修改数据失败！");
        }

        return new MessageEntity().addCode(1).addIsError(false).addTitle("提示").addContent("修改数据成功！");
    }


    @RequestMapping(value = "/selectRepetCount")
    @ResponseBody
    public int selectRepetCount(String serialId) throws Exception {
        int count = securityService.selectSecurityCount(serialId);
        return count+1;
    }

    @RequestMapping(value = "/selectRepeatPerson")
    @ResponseBody
    public JSON selectRepeatPerson(String serialNo) throws Exception {
        securityService.selectRepeatPerson(serialNo);
        SecurityEntity entity = securityService.selectRepeatPerson(serialNo);
        return (JSON) JSON.toJSON(entity);
    }
}

