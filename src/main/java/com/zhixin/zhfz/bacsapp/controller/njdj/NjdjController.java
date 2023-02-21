package com.zhixin.zhfz.bacsapp.controller.njdj;

import com.zhixin.zhfz.bacs.controller.serial.SerialController;
import com.zhixin.zhfz.bacs.entity.SerialEntity;
import com.zhixin.zhfz.bacs.entity.SerialStatusEntity;
import com.zhixin.zhfz.bacs.entity.UrinalysisDetailEntity;
import com.zhixin.zhfz.bacs.entity.UrinalysisEntity;
import com.zhixin.zhfz.bacs.form.UrinalysisForm;
import com.zhixin.zhfz.bacs.services.serial.ISerialService;
import com.zhixin.zhfz.bacs.services.serial.ISerialStatusService;
import com.zhixin.zhfz.bacs.services.urinalysis.UrinalysisDetailService;
import com.zhixin.zhfz.bacs.services.urinalysis.UrinalysisPhotoService;
import com.zhixin.zhfz.bacs.services.urinalysis.UrinalysisService;
import com.zhixin.zhfz.bacsapp.entity.NjdjAppEntity;
import com.zhixin.zhfz.bacsapp.services.njdj.INjdjService;
import com.zhixin.zhfz.common.entity.MessageEntity;
import com.zhixin.zhfz.common.entity.RoleEntity;
import com.zhixin.zhfz.common.entity.SessionInfo;
import com.zhixin.zhfz.common.entity.UserEntity;
import com.zhixin.zhfz.common.form.FileUploadForm;
import com.zhixin.zhfz.common.services.fileServiceConfig.IFileConfigService;
import com.zhixin.zhfz.common.services.user.IUserService;
import com.zhixin.zhfz.common.utils.ControllerTool;
import com.zhixin.zhfz.sacw.form.UserNoSearchForm;
import org.apache.commons.beanutils.PropertyUtils;
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
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/zhfz/bacsapp/njdj")
public class NjdjController {

    private static Logger logger = LoggerFactory.getLogger(SerialController.class);

    @Autowired
    private INjdjService njdjService;

    @Autowired
    private IUserService userService;

    @Autowired
    private UrinalysisService urinalysisService;

    @Autowired
    private UrinalysisPhotoService urinalysisPhotoService;

    @Autowired
    private UrinalysisDetailService urinalysisDetailService;

    @Autowired
    private ISerialService serialService;
    @Autowired
    private IFileConfigService fileConfigService;

    @Autowired
    private ISerialStatusService serialStatusService;

    @RequestMapping(value = "/list")
    @ResponseBody
    public Map<String, Object> list(@RequestParam Map<String, Object> pageMap, HttpServletRequest request,
                                          HttpServletResponse response) throws Exception {
        Map<String, Object> map = ControllerTool.mapFilter(pageMap);
        List<NjdjAppEntity> list = null;
        int total = 0;

        Map<String, Object> map1 = getAuthMethod(request);

        if ("true".equals(map1.get("flag"))){

            map.put("dataAuth",map1.get("dataAuth"));

        }
        list = njdjService.list(map);
        total = njdjService.count(map);

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("rows", list);
        result.put("total", total);
        return result;
    }



    public  Map<String,Object> getAuthMethod(HttpServletRequest request){

        Map<String,Object> map = new HashMap<String,Object>();

        boolean flag = true;
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);

        if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuth(request))) {
            // 办案人员-本人
            map.put("dataAuth", " (a.in_register_user_id=" + ControllerTool.getUserID(request) +
                    " or a.out_register_user_id=" + ControllerTool.getUserID(request) +
                    " or u.main_user_id=" + ControllerTool.getUserID(request) +
                    " or u.check_user_id=" + ControllerTool.getUserID(request) +
                    " ) ");
            map.put("flag","true");
        } else if (RoleEntity.DATA_AUTH_SELFAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 办案场所-本办案场所
            map.put("dataAuth", " ( a.area_id=" + ControllerTool.getCurrentAreaID(request) + ")");
            map.put("flag","true");
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 管理员 -本办案场所及下级办案场所
            map.put("dataAuth", " ( a.area_id" + sessionInfo.getCurrentAndSubAreaInStr() + ")");
            map.put("flag","true");
        }
        else if (RoleEntity.DATA_AUTH_UP_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 上级办案场所及其下级办案场所
            map.put("dataAuth", " ( a.area_id" + ControllerTool.getSessionInfo(request).getSuperAndSubAreaInStr() + ")");
            map.put("flag","true");
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 公安领导-本部门及下级部门
            map.put("dataAuth"," ( a.op_pid like " + ControllerTool.getSessionInfo(request).getCurrentAndSubOrgPid()
                    +" or u.op_pid like " + ControllerTool.getSessionInfo(request).getCurrentAndSubOrgPid() + ")"
                   );
            map.put("flag","true");
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 法制人员-上级部门及下级部门
            map.put("dataAuth"," ( a.op_pid like " + ControllerTool.getSessionInfo(request).getSuperAndSubOrgPid()+
                    " or u.op_pid like " + ControllerTool.getSessionInfo(request).getSuperAndSubOrgPid() + ")");
            map.put("flag","true");
        } else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuth(request))) {
            // 本部门
            map.put("dataAuth"," ( a.op_pid = " + sessionInfo.getCurrentOrgPid() +
                    "or u.op_pid = " + sessionInfo.getCurrentOrgPid() + ")");
            map.put("flag","true");
        } else {
            map.put("flag","false");
        }

        return map;
    }


    @RequestMapping(value = "/personList")
    @ResponseBody
    public  HashMap<String, Object> personList(HttpServletRequest request) throws Exception {
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        HashMap<String, Object> map = new HashMap<>();
//        map.put("areaId",sessionInfo.getCurrentArea().getId());

        Map<String, Object> map1 = getAuthMethod(request);

        List<NjdjAppEntity> list =null;

        map.put("dataAuth",map1.get("dataAuth"));

        if ("true".equals(map1.get("flag"))){

            list = njdjService.personList(map);
        }

        map.put("list",list);
        return map;

    }

    @RequestMapping(value = "/personListUpdate")
    @ResponseBody
    public  HashMap<String, Object> personListUpdate(HttpServletRequest request) throws Exception {
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        HashMap<String, Object> map = new HashMap<>();
//        map.put("areaId",sessionInfo.getCurrentArea().getId());

        Map<String, Object> map1 = getAuthMethod(request);

        List<NjdjAppEntity> list =null;

        map.put("dataAuth",map1.get("dataAuth"));

        if ("true".equals(map1.get("flag"))){

            list = njdjService.personListUpdate(map);
        }

        map.put("list",list);
        return map;

    }

    @RequestMapping(value = "/searchUser")
    @ResponseBody
    public UserEntity searchUser(@RequestBody UserNoSearchForm form) throws Exception {
        UserEntity user = userService.getUserByCertificateNo(form.getUserNo());
        return user;
    }

    /**
     * 新增尿检信息
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/addNjdj")
    @ResponseBody
    public MessageEntity addNjdj(UrinalysisForm form, HttpServletRequest request,
                                       HttpServletResponse response) throws Exception {
        System.err.print(form);
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);

        SerialEntity serialEntity = null;
        if (form.getSerialId() != null) {
            serialEntity = serialService.queryById(form.getSerialId());
        } else {
            return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("查无此人!");
        }
        UserEntity user = userService.getUserByCertificateNo(form.getCheckUserNo());
        if (user == null) {
            return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("民警编号错误!");
        }
        UrinalysisEntity entity = new UrinalysisEntity();
        try {
            PropertyUtils.copyProperties(entity, form);
            entity.setAreaId(sessionInfo.getCurrentArea().getId());
            if (user != null) {
                entity.setCheckUserId(user.getId());
            }
            if (serialEntity != null) {
                entity.setMainUserId(serialEntity.getInUserId1());
                entity.setCaseId(serialEntity.getCaseId());
            }
            Map<String, Object> map = new HashMap<>();
            map.put("serialId", entity.getSerialId());
            entity.setCount(urinalysisService.count(map));
            entity.setUrinalysisTime(new Date());
            entity.setCreatedTime(new Date());
            entity.setOpPid(sessionInfo.getCurrentOrg().getPid());
            entity.setOpUserId(sessionInfo.getUser().getId());
            logger.info(entity.toString());
            //插入尿检表
            this.urinalysisService.insertUrinalysis(entity);

            if(form.getFiles()!=null&&form.getFiles().length>0) {
                //循环获取file数组中得文件
                for (int i = 0; i < form.getFiles().length; i++) {

                    FileUploadForm form1 = new FileUploadForm();
                    String filename = "";
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
                    filename = "Appnj-" + dateFormat.format(new java.util.Date()) + i + ".jpg";
                    form1.setSysType("ba");
                    form1.setFileType("nj");
                    form1.setUuid(form.getUuid());
                    form1.setSysId(String.valueOf(sessionInfo.getCurrentArea().getId()));
                    form1.setFileName(filename);
                    form1.setFile(form.getFiles()[i]);
                    fileConfigService.upload(form1);
                }
            }
            //插入尿检详情表
            if(form.getResult() != null){
                String [] results = form.getResult().split(";");
                List<UrinalysisDetailEntity> urinalysisDetailEntityList = new ArrayList<UrinalysisDetailEntity>();
                if(results != null && results.length >0){
                    for (String reString:results) {
                        UrinalysisDetailEntity urinalysisDetail = new UrinalysisDetailEntity();
                        urinalysisDetail.setUrinalysisId(entity.getId());
                        urinalysisDetail.setCheckUserId(entity.getCheckUserId());
                        urinalysisDetail.setCheckItem( reString.split("呈")[0]);
                        urinalysisDetail.setItemResult( reString.split("呈")[1]);
                        urinalysisDetail.setOpPid(sessionInfo.getCurrentOrg().getPid());
                        urinalysisDetail.setOpUserId(sessionInfo.getUser().getId());
                        urinalysisDetail.setCreatedTime(new Date());
                        urinalysisDetailEntityList.add(urinalysisDetail);
                    }
                    if(urinalysisDetailEntityList != null && urinalysisDetailEntityList.size() >0){
                        urinalysisDetailService.insertList(urinalysisDetailEntityList);
                    }
                }
            }
            SerialStatusEntity iss = new SerialStatusEntity();
            iss.setInterrogateSerialId(entity.getSerialId());
            iss.setStatusName("已尿检-"+entity.getResult());
            iss.setOpPid(sessionInfo.getCurrentOrg().getPid());
            iss.setOpUserId(sessionInfo.getUser().getId());
            serialStatusService.insert(iss);
        } catch (Exception e) {
            logger.error("Error on addNjdj!", e);
            return new MessageEntity().addCode(1).addIsError(true).addTitle("提示").addContent("保存失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("提示").addContent("保存成功!").addCallbackData(entity.getId());
    }


    /**
     * 修改尿检信息
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/updateNjdj")
    @ResponseBody
    public MessageEntity updateNjdj(UrinalysisForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {
        System.err.print(form);
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);

        SerialEntity serialEntity = null;
        if (form.getSerialId() != null) {
            serialEntity = serialService.queryById(form.getSerialId());
        } else {
            return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("查无此人!");
        }
        UserEntity user = userService.getUserByCertificateNo(form.getCheckUserNo());
        if (user == null) {
            return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("民警编号错误!");
        }
        UrinalysisEntity entity = new UrinalysisEntity();
        try {
            PropertyUtils.copyProperties(entity, form);
            entity.setAreaId(sessionInfo.getCurrentArea().getId());
            if (user != null) {
                entity.setCheckUserId(user.getId());
            }
            if (serialEntity != null) {
                entity.setMainUserId(serialEntity.getInUserId1());
                entity.setCaseId(serialEntity.getCaseId());
            }
            Map<String, Object> map = new HashMap<>();
            map.put("serialId", entity.getSerialId());
            entity.setCount(urinalysisService.count(map));
            entity.setUrinalysisTime(new Date());
            entity.setCreatedTime(new Date());
            entity.setOpPid(sessionInfo.getCurrentOrg().getPid());
            entity.setOpUserId(sessionInfo.getUser().getId());
            logger.info(entity.toString());
            //更新尿检表
            this.urinalysisService.updateUrinalysis(entity);

            if(form.getFiles()!=null&&form.getFiles().length>0) {
                //循环获取file数组中得文件
                for (int i = 0; i < form.getFiles().length; i++) {

                    FileUploadForm form1 = new FileUploadForm();
                    String filename = "";
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
                    filename = "Appnj-" + dateFormat.format(new java.util.Date()) + i + ".jpg";
                    form1.setSysType("ba");
                    form1.setFileType("nj");
                    form1.setUuid(form.getUuid());
                    form1.setSysId(String.valueOf(sessionInfo.getCurrentArea().getId()));
                    form1.setFileName(filename);
                    form1.setFile(form.getFiles()[i]);
                    fileConfigService.upload(form1);
                }
            }
            //插入尿检详情表
            if(form.getResult() != null){
                String [] results = form.getResult().split(";");
                List<UrinalysisDetailEntity> urinalysisDetailEntityList = new ArrayList<UrinalysisDetailEntity>();
                if(results != null && results.length >0){
                    for (String reString:results) {
                        UrinalysisDetailEntity urinalysisDetail = new UrinalysisDetailEntity();
                        urinalysisDetail.setUrinalysisId(entity.getId());
                        urinalysisDetail.setCheckUserId(entity.getCheckUserId());
                        urinalysisDetail.setCheckItem( reString.split("呈")[0]);
                        urinalysisDetail.setItemResult( reString.split("呈")[1]);
                        urinalysisDetail.setOpPid(sessionInfo.getCurrentOrg().getPid());
                        urinalysisDetail.setOpUserId(sessionInfo.getUser().getId());
                        urinalysisDetail.setCreatedTime(new Date());
                        urinalysisDetailEntityList.add(urinalysisDetail);
                    }
                    if(urinalysisDetailEntityList != null && urinalysisDetailEntityList.size() >0){
                        urinalysisDetailService.insertList(urinalysisDetailEntityList);
                    }
                }
            }
            SerialStatusEntity iss = new SerialStatusEntity();
            iss.setInterrogateSerialId(entity.getSerialId());
            iss.setStatusName("已尿检-"+entity.getResult());
            iss.setOpPid(sessionInfo.getCurrentOrg().getPid());
            iss.setOpUserId(sessionInfo.getUser().getId());
            serialStatusService.insert(iss);
        } catch (Exception e) {
            logger.error("Error on addNjdj!", e);
            return new MessageEntity().addCode(1).addIsError(true).addTitle("提示").addContent("保存失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("提示").addContent("保存成功!").addCallbackData(entity.getId());
    }


}
