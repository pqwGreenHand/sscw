package com.zhixin.zhfz.bacs.controller.serial;

import com.ideal.netcare.v5.common.util.api.dls.DLSProcessApiService;
import com.zhixin.zhfz.bacs.entity.*;
import com.zhixin.zhfz.bacs.form.CuffForm;
import com.zhixin.zhfz.bacs.form.ImageForm;
import com.zhixin.zhfz.bacs.form.SerialForm;
import com.zhixin.zhfz.bacs.services.belong.IBelongService;
import com.zhixin.zhfz.bacs.services.camera.ICameraService;
import com.zhixin.zhfz.bacs.services.combine.CombineInfoService;
import com.zhixin.zhfz.bacs.services.cuff.ICuffLogService;
import com.zhixin.zhfz.bacs.services.cuff.ICuffService;
import com.zhixin.zhfz.bacs.services.door.IZKDoorService;
import com.zhixin.zhfz.bacs.services.downloadtask.IDownloadTaskService;
import com.zhixin.zhfz.bacs.services.order.IOrderPersonService;
import com.zhixin.zhfz.bacs.services.order.IOrderRequestService;
import com.zhixin.zhfz.bacs.services.order.IOrderStatusService;
import com.zhixin.zhfz.bacs.services.person.IPersonService;
import com.zhixin.zhfz.bacs.services.personalconfig.IPersonalConfigDetailService;
import com.zhixin.zhfz.bacs.services.personalconfig.IPersonalConfigService;
import com.zhixin.zhfz.bacs.services.record.IRecordService;
import com.zhixin.zhfz.bacs.services.record.RecordDataService;
import com.zhixin.zhfz.bacs.services.security.ISecurityService;
import com.zhixin.zhfz.bacs.services.serial.*;
import com.zhixin.zhfz.bacs.services.serialVideoMapping.ISerialVideoMappingService;
import com.zhixin.zhfz.bacs.services.track.ITrackService;
import com.zhixin.zhfz.bacs.services.waitingmanage.IWaitingManageService;
import com.zhixin.zhfz.bacsapp.entity.InformationEntity;
import com.zhixin.zhfz.bacsapp.services.Information.IInformationService;
import com.zhixin.zhfz.common.common.*;
import com.zhixin.zhfz.common.entity.*;
import com.zhixin.zhfz.common.form.FileUploadForm;
import com.zhixin.zhfz.common.services.Icase.ICaseService;
import com.zhixin.zhfz.common.services.fileServiceConfig.IFileConfigService;
import com.zhixin.zhfz.common.services.notice.IMyNoticeService;
import com.zhixin.zhfz.common.services.operLog.IOperLogService;
import com.zhixin.zhfz.common.services.organiztion.IOrganizationService;
import com.zhixin.zhfz.common.services.user.IUserService;
import com.zhixin.zhfz.common.utils.ControllerTool;
import com.zhixin.zhfz.common.utils.PropertyUtil;
import com.zhixin.zhfz.common.utils.RMIUtil;
import com.zhixin.zhfz.sacw.common.BaseConfig;
import com.zhixin.zhfz.sacw.common.FreemarkerUtil;
import com.zhixin.zhfz.sacw.common.Utils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.math.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.internet.MimeUtility;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/zhfz/bacs/serial")
public class SerialController {

    private static Logger logger = LoggerFactory.getLogger(SerialController.class);

    @Autowired
    private ISerialService serialService;

    @Autowired
    private IOperLogService operLogService;

    @Autowired
    private IPersonService personService;

    @Autowired
    private ISerialStatusService serialStatusService;

    @Autowired
    private ISerialVideoMappingService serialVideoMappingService;

    @Autowired
    private IFileConfigService fileConfigService;
    @Autowired
    private ICuffService cuffService;
    @Autowired
    private ICuffLogService cuffLogService;
    @Autowired
    private IInformationService iInformationService;
    @Autowired
    private IOrderRequestService orderRequestService;
    @Autowired
    private IOrderStatusService orderStatusService;
    @Autowired
    private CombineInfoService combineInfoService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IMyNoticeService noticeService;
    @Autowired
    private IOrganizationService organizationService;
    @Autowired
    private TempOutService tempOutService;
    @Autowired
    private IRecordService recordService;
    @Autowired
    private RecordDataService recordDataService;
    @Autowired
    private IDownloadTaskService taskService;
    @Autowired
    private IPersonalConfigDetailService personalConfigDetailService;
    @Autowired
    private IWaitingManageService waitingManageService;
    @Autowired
    private IPersonalConfigService personalConfigService;
    @Autowired
    private IZKDoorService izkDoorService;
    @Autowired
    private ITrackService trackService;
    @Autowired
    private ICameraService iCameraService;
    @Autowired
    private IOutReportService outReportService;
    @Autowired
    private IOrderPersonService orderPersonService;
    @Autowired
    private ICaseService caseService;

    @Autowired
    private IBelongService belongService;
    @Autowired
    private ISecurityService securityService;

    public static String tdparams = "";

    @RequestMapping(value = "/list")
    @ResponseBody
    public Map<String, Object> list(@RequestParam Map<String, Object> params, HttpServletRequest request) throws Exception {
        List<SerialEntity> interrogateSerials = new ArrayList<SerialEntity>();
        int count = 0;
        boolean flag = true;
        final HttpServletRequest request1 = request;
        SerialEntity entiy = new SerialEntity();
        entiy.setId(1L);
        final SerialEntity serialEntity = entiy;

        Map<String, Object> map = ControllerTool.mapFilter(params);
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuth(request))) {
            // 办案人员-本人
            map.put("dataAuth", " ( ints.in_register_user_id= "
                    + ControllerTool.getUserID(request)
                    + " or ints.out_register_user_id = " + ControllerTool.getUserID(request)
                    + " or ints.send_user_id = " + ControllerTool.getUserID(request)
                    + " or ints.in_register_user_id = " + ControllerTool.getUserID(request)
                    + " or ic.cjr = " + ControllerTool.getUserID(request)
                    + " or ic.zbmj_id = " + ControllerTool.getUserID(request)
                    + " or ic.xbmj_ids like %," + ControllerTool.getUserID(request) + ",%"
                    + " or o.order_user_id = " + ControllerTool.getUserID(request)
                    + " or o.noter_id = " + ControllerTool.getUserID(request)
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_SELFAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 办案场所-本办案场所
            map.put("dataAuth", "( ints.area_id=" + ControllerTool.getCurrentAreaID(request)
                    + " or o.area_id = " + ControllerTool.getCurrentAreaID(request)
                    + " or c.area_id = " + ControllerTool.getCurrentAreaID(request)
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 管理员 -本办案场所及下级办案场所
            map.put("dataAuth", "( ints.area_id " + sessionInfo.getCurrentAndSubAreaInStr()
                    + " or o.area_id " + sessionInfo.getCurrentAndSubAreaInStr()
                    + " or c.area_id " + sessionInfo.getCurrentAndSubAreaInStr()
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 上级办案场所及其下级办案场所
            map.put("dataAuth", " ( ints.area_id " + sessionInfo.getSuperAndSubAreaInStr()
                    + " or o.area_id " + sessionInfo.getSuperAndSubAreaInStr()
                    + " or c.area_id " + sessionInfo.getSuperAndSubAreaInStr()
                    + " )");
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 公安领导-本部门及下级部门
            map.put("dataAuth", " ( ints.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                    + " or o.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                    + " or c.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 法制人员-上级部门及下级部门
            map.put("dataAuth", " ( ints.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
                    + " or o.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
                    + " or c.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuth(request))) {
            // 本部门
            map.put("dataAuth", " ( ints.op_pid = " + sessionInfo.getCurrentOrgPid()
                    + " or o.op_pid = " + sessionInfo.getCurrentOrgPid()
                    + " or c.op_pid = " + sessionInfo.getCurrentOrgPid()
                    + " ) ");
        }
        if (flag) {
            interrogateSerials = this.serialService.list(map);
            count = this.serialService.count(map);
        }
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", count);
        result.put("rows", interrogateSerials);
        return result;
    }

    @RequestMapping(value = "/timeoutRecodelist")
    @ResponseBody
    public Map<String, Object> timeoutRecodelist(@RequestParam Map<String, Object> params, HttpServletRequest request) throws Exception {
        List<SerialEntity> interrogateSerials = new ArrayList<SerialEntity>();
        int count = 0;
        boolean flag = true;
        Map<String, Object> map = ControllerTool.mapFilter(params);
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuth(request))) {
            // 办案人员-本人
            map.put("dataAuth", " ( ints.in_register_user_id= "
                    + ControllerTool.getUserID(request)
                    + " or ints.out_register_user_id = " + ControllerTool.getUserID(request)
                    + " or ints.send_user_id = " + ControllerTool.getUserID(request)
                    + " or ints.in_register_user_id = " + ControllerTool.getUserID(request)
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_SELFAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 办案场所-本办案场所
            map.put("dataAuth", "( ints.area_id=" + ControllerTool.getCurrentAreaID(request)
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 管理员 -本办案场所及下级办案场所
            map.put("dataAuth", "( ints.area_id " + sessionInfo.getCurrentAndSubAreaInStr()
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 上级办案场所及其下级办案场所
            map.put("dataAuth", " ( ints.area_id " + sessionInfo.getSuperAndSubAreaInStr()
                    + " )");
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 公安领导-本部门及下级部门
            map.put("dataAuth", " ( ints.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 法制人员-上级部门及下级部门
            map.put("dataAuth", " ( ints.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuth(request))) {
            // 本部门
            map.put("dataAuth", " ( ints.op_pid = " + sessionInfo.getCurrentOrgPid()
                    + " ) ");
        }
        if (flag) {
            interrogateSerials = this.serialService.timeoutRecodelist(map);
            count = this.serialService.countTimeoutRecode(map);
        }
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", count);
        result.put("rows", interrogateSerials);
        return result;
    }

    @RequestMapping(value = "/getSerialByCuffNo")
    @ResponseBody
    public MessageEntity getSerialByCuffNo(@RequestBody CuffForm form, HttpServletRequest request) throws Exception {
        logger.debug("++++++++++++++++++++++++++getSerialByCuffNo++++++++++++++++++++++++++++++");
        SerialEntity serial = new SerialEntity();
        Map<String, Object> map = new HashMap<String, Object>();
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuth(request))) {
            // 办案人员-本人
            map.put("dataAuth", " ( c.in_register_user_id=" + ControllerTool.getUserID(request)
                    + " or c.out_register_user_id=" + ControllerTool.getUserID(request)
                    + " or c.send_user_id=" + ControllerTool.getUserID(request)
                    + " or c.op_user_id=" + ControllerTool.getUserID(request)
                    + " or c.user_id=" + ControllerTool.getUserID(request)
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_SELFAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 办案场所-本办案场所
            map.put("dataAuth", " ( c.iarea_id=" + ControllerTool.getCurrentAreaID(request)
                    + " or c.careaId = " + ControllerTool.getCurrentAreaID(request)
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 管理员 -本办案场所及下级办案场所
            map.put("dataAuth", "( c.iarea_id  " + sessionInfo.getCurrentAndSubAreaInStr()
                    + " or c.careaId " + sessionInfo.getCurrentAndSubAreaInStr()
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 上级办案场所及其下级办案场所
            map.put("dataAuth", " ( c.iarea_id  " + sessionInfo.getSuperAndSubAreaInStr()
                    + " or c.careaId " + sessionInfo.getSuperAndSubAreaInStr()
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 公安领导-本部门及下级部门
            map.put("dataAuth", " ( c.insPid like " + sessionInfo.getCurrentAndSubOrgPid()
                    + " or c.cfPid like " + sessionInfo.getCurrentAndSubOrgPid()
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 法制人员-上级部门及下级部门
            map.put("dataAuth", " ( c.insPid like " + sessionInfo.getSuperAndSubOrgPid()
                    + " or c.cfPid like " + sessionInfo.getSuperAndSubOrgPid()
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuth(request))) {
            // 本部门
            map.put("dataAuth", " ( c.insPid = " + sessionInfo.getCurrentOrgPid()
                    + " or c.cfPid = " + sessionInfo.getCurrentOrgPid()
                    + " ) ");
        }
        try {

            map.put("cuffNo", form.getCuffNo());
            map.put("type", form.getType());
            serial = this.serialService.getSerialByCuffNo(map);

            logger.debug("++++++++++++++++++++++++++++++++++++++++++++++++++serial= " + serial);
        } catch (Exception e) {
            return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("请求失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("提醒").addContent("请求成功!")
                .addCallbackData(serial);
    }


    @RequestMapping(value = "/insertRybh")
    @ResponseBody
    public MessageEntity insertRybh(@RequestBody Map<String, Object> param, HttpServletRequest request) throws Exception {
        Map<String, Object> map = ControllerTool.mapFilter(param);
        try {
            SerialEntity entity = new SerialEntity();
            entity.setId(Long.parseLong(param.get("id") + ""));
            entity.setRybh(param.get("personNo") + "");
            serialService.updateSerialById2(entity);
        } catch (Exception e) {
            return new MessageEntity().addCode(0).addIsError(true).addTitle("错误").addContent("请求失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("提醒").addContent("请求成功!");
    }


    /**
     * 其他人入区
     *
     * @param form
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/otherInsert")
    @ResponseBody
    public MessageEntity otherInsert(@RequestBody SerialForm form, HttpServletRequest request) throws Exception {
        logger.info("SerialForm=" + form);
        String uuid = java.util.UUID.randomUUID().toString();
        PersonEntity person = new PersonEntity();
        person.setCertificateTypeId(form.getCertificateTypeId());
        person.setCertificateNo(form.getCertificateNo());
        person.setName(form.getName());
        person.setSex(form.getSex());
        person.setBirth(form.getBirth());
        person.setCountry(form.getCountry());
        person.setNation(form.getNation());
        person.setCensusRegister(form.getCensusRegister());
        person.setAreaId(form.getAreaId());
        person.setCaseId(form.getCaseId());
        person.setUuid(uuid);
        person.setOpPid(ControllerTool.getSessionInfo(request).getCurrentOrg().getPid());
        person.setOpUserId(ControllerTool.getSessionInfo(request).getUser().getId());
        SerialEntity serial;
        try {
            // 确定数据库是否有这个人，不存在就添加person信息
            PersonEntity pers = personService.ishave(person);
            if (pers == null) {
                personService.insert(person);
                this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "添加其他人信息" + person,
                        ControllerTool.getSessionInfo(request).getUser().getRealName(), true, "出入区管理");
            } else {
                person.setId(pers.getId());
            }

            SerialEntity entity = new SerialEntity();
            // 根据时间生成编号
            String no = "QTR" + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
            entity.setSerialNo(no);
            entity.setUuid(uuid);
            entity.setPersonId(person.getId());
            entity.setType(form.getType());
            entity.setInRegisterUserId(ControllerTool.getSessionInfo(request).getUser().getId());
            entity.setCaseId(form.getCaseId());
            // 判断入区是否拍照
            if (form.getIsPhoto() == 1) {
                entity.setInPhotoId("rq-" + no + "-yt.jpg");
            }
            // 设置默认值
            entity.setStatus(0);
            entity.setAreaId(form.getAreaId());
            entity.setOpPid(ControllerTool.getSessionInfo(request).getCurrentOrg().getPid());
            serialService.insert(entity);

            SerialStatusEntity iss = new SerialStatusEntity();
            iss.setInterrogateSerialId(entity.getId());
            iss.setStatusName("已入区");
            serialStatusService.insert(iss);
            this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "添加其他人入区登记信息" + entity,
                    ControllerTool.getSessionInfo(request).getUser().getRealName(), true, "出入区管理");
            serial = serialService.findserialbyNo(no);
            logger.info("++++++++++++++" + serial);
        } catch (Exception e) {
            logger.error("", e);
            return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("保存失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("提示").addContent("保存成功!").addCallbackData(serial);
    }

    /**
     * 其他人入区上传照片
     *
     * @param request
     * @throws Exception
     */
    @RequestMapping(value = "/ingetpicture")
    @ResponseBody
    public MessageEntity inGetPicture(@RequestParam(value = "file", required = false) MultipartFile file, Long serialId, HttpServletRequest request) throws Exception {
        try {
            logger.info("### ingetpicture MultipartFile= " + file);
            SerialEntity serial = new SerialEntity();
            serial.setId(serialId);
            serial = serialService.getSerialById2(serial);
            fileConfigService.upload(FileUploadForm.of("ba", "RQ", serial.getUuid(), serial.getAreaId(), serial.getInPhotoId(), file));
        } catch (Exception e) {
            return new MessageEntity().addCode(1).addIsError(true).addTitle("提示").addContent("入区失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("提示").addContent("入区成功！");
    }

    /**
     * 其他人出区上传照片
     *
     * @param request
     * @throws Exception
     */
    @RequestMapping(value = "/outgetpicture")
    @ResponseBody
    public MessageEntity outGetPicture(@RequestParam(value = "file", required = false) MultipartFile file, Long serialId, String serialNo, HttpServletRequest request,
                                       HttpServletResponse response) throws Exception {
        try {
            logger.info("### outgetpicture MultipartFile= " + file);
            // 其他人出区
            SerialEntity serial = new SerialEntity();
            serial.setId(serialId);
            serial.setOutPhotoId("cq-" + serialNo + "-yt.jpg");
            serial.setStatus(11);
            serial.setOutRegisterUserId(ControllerTool.getUserID(request));
            serialService.updateSerialById(serial);

            // 新增一条SerialStatus出区记录
            SerialStatusEntity iss = new SerialStatusEntity();
            iss.setInterrogateSerialId(serialId);
            iss.setStatusName("已出区");
            serialStatusService.insert(iss);

            // 查询当前嫌疑人信息
            serial = serialService.getSerialById2(serial);
            fileConfigService.upload(FileUploadForm.of("ba", "RQ", serial.getUuid(), serial.getAreaId(), serial.getOutPhotoId(), file));
        } catch (Exception e) {
            return new MessageEntity().addCode(1).addIsError(true).addTitle("提示").addContent("出区失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("提示").addContent("出区成功！");
    }

    /**
     * 其他人出区未拍照
     *
     * @param request
     * @throws Exception
     */
    @RequestMapping(value = "/otherPersonOut")
    @ResponseBody
    public MessageEntity otherPersonOut(Long serialId, String serialNo, HttpServletRequest request,
                                        HttpServletResponse response) throws Exception {
        try {
            // 其他人出区
            SerialEntity serial = new SerialEntity();
            serial.setId(serialId);
            serial.setStatus(11);
            serial.setOutRegisterUserId(ControllerTool.getUserID(request));
            serialService.updateSerialById(serial);

            // 新增一条SerialStatus出区记录
            SerialStatusEntity iss = new SerialStatusEntity();
            iss.setInterrogateSerialId(serialId);
            iss.setStatusName("已出区");
            serialStatusService.insert(iss);
        } catch (Exception e) {
            return new MessageEntity().addCode(1).addIsError(true).addTitle("提示").addContent("出区失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("提示").addContent("出区成功！");
    }


    // 被辨认人下展示
    @RequestMapping(value = "/getIdentifyPersonList")
    @ResponseBody
    public List<SerialEntity> getIdentifyPersonList(@RequestParam String areaId, HttpServletRequest request) {
        List<SerialEntity> list = null;
        Map<String, Object> map = new HashMap<>();
        map.put("areaId", areaId);
        try {
            SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
            if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuth(request))) {
                // 办案人员-本人
                map.put("dataAuth", " ( s.in_register_user_id=" + ControllerTool.getUserID(request) + " or s.out_register_user_id="
                        + ControllerTool.getUserID(request)
                        + " or s.send_user_id = " + ControllerTool.getUserID(request)
                        + " or c.cjr = " + ControllerTool.getUserID(request)
                        + " or p.op_user_id = " + ControllerTool.getUserID(request)
                        + " ) ");
            } else if (RoleEntity.DATA_AUTH_SELFAREA == (ControllerTool.getRoleDataAuth(request))) {
                // 办案场所-本办案场所
                map.put("dataAuth", " ( s.area_id = " + ControllerTool.getCurrentAreaID(request)
                        + " or p.area_id = " + ControllerTool.getCurrentAreaID(request)
                        + " ) ");
            } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
                // 管理员 -本办案场所及下级办案场所
                map.put("dataAuth", "( s.area_id  " + sessionInfo.getCurrentAndSubAreaInStr()
                        + " or p.area_id " + sessionInfo.getCurrentAndSubAreaInStr()
                        + " ) ");
            } else if (RoleEntity.DATA_AUTH_UP_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
                // 上级办案场所及其下级办案场所
                map.put("dataAuth", " ( s.area_id  " + sessionInfo.getSuperAndSubAreaInStr()
                        + " or p.area_id " + sessionInfo.getSuperAndSubAreaInStr()
                        + " ) ");
            } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
                // 公安领导-本部门及下级部门
                map.put("dataAuth", " ( s.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                        + " or p.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                        + " or c.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                        + " ) ");
            } else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
                // 法制人员-上级部门及下级部门
                map.put("dataAuth", " ( s.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
                        + " or p.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
                        + " or c.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
                        + " ) ");
            } else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuth(request))) {
                // 本部门
                map.put("dataAuth", " ( s.op_pid = " + sessionInfo.getCurrentOrgPid()
                        + " or p.op_pid = " + sessionInfo.getCurrentOrgPid()
                        + " or c.op_pid = " + sessionInfo.getCurrentOrgPid()
                        + " ) ");
            }
            list = serialService.getIdentifyPersonList(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //System.out.println(map);
        return list;
    }


    @RequestMapping(value = "/getPersonnelList")
    @ResponseBody
    public Map<String, Object> getPersonnelList(@RequestParam Map<String, Object> param, HttpServletRequest request) throws Exception {
        Map<String, Object> map = ControllerTool.mapFilter(param);
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuth(request))) {
            // 办案人员-本人
            map.put("dataAuth", " ( s.in_register_user_id=" + ControllerTool.getUserID(request) + " or s.out_register_user_id="
                    + ControllerTool.getUserID(request)
                    + " or s.send_user_id = " + ControllerTool.getUserID(request)
                    + " or c.cjr = " + ControllerTool.getUserID(request)
                    + " or p.op_user_id = " + ControllerTool.getUserID(request)
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_SELFAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 办案场所-本办案场所
            map.put("dataAuth", " ( s.area_id = " + ControllerTool.getCurrentAreaID(request)
                    + " or p.area_id = " + ControllerTool.getCurrentAreaID(request)
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 管理员 -本办案场所及下级办案场所
            map.put("dataAuth", "( s.area_id  " + sessionInfo.getCurrentAndSubAreaInStr()
                    + " or p.area_id " + sessionInfo.getCurrentAndSubAreaInStr()
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 上级办案场所及其下级办案场所
            map.put("dataAuth", " ( s.area_id  " + sessionInfo.getSuperAndSubAreaInStr()
                    + " or p.area_id " + sessionInfo.getSuperAndSubAreaInStr()
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 公安领导-本部门及下级部门
            map.put("dataAuth", " ( s.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                    + " or p.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                    + " or c.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 法制人员-上级部门及下级部门
            map.put("dataAuth", " ( s.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
                    + " or p.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
                    + " or c.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuth(request))) {
            // 本部门
            map.put("dataAuth", " ( s.op_pid = " + sessionInfo.getCurrentOrgPid()
                    + " or p.op_pid = " + sessionInfo.getCurrentOrgPid()
                    + " or c.op_pid = " + sessionInfo.getCurrentOrgPid()
                    + " ) ");
        }
        List<SerialEntity> list = serialService.getPersonnelList(map);
        int count = serialService.getPersonnelListCount(map);
        Map<String, Object> results = new HashMap<>();
        results.put("rows", list);
        results.put("total", count);
        return results;
    }

    /**
     * 其他人入区列表
     *
     * @param params
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/otherPersonList")
    @ResponseBody
    public Map<String, Object> otherPersonList(@RequestParam Map<String, Object> params, HttpServletRequest request) throws Exception {

        Map<String, Object> map = ControllerTool.mapFilter(params);
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuth(request))) {
            // 办案人员-本人
            map.put("dataAuth", " ( ia.send_user_id=" + ControllerTool.getUserID(request)
                    + " or ia.in_register_user_id=" + ControllerTool.getUserID(request)
                    + " or ia.out_register_user_id=" + ControllerTool.getUserID(request)
                    + " or ca.zbmj_id= " + ControllerTool.getUserID(request)
                    + " or locate('," + ControllerTool.getUserID(request) + ",', ca.xbmj_ids)"
                    + " or ca.cjr=" + ControllerTool.getUserID(request)
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_SELFAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 办案场所-本办案场所
            map.put("dataAuth", " ia.area_id=" + ControllerTool.getCurrentAreaID(request));
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 管理员 -本办案场所及下级办案场所
            map.put("dataAuth", " ia.area_id " + sessionInfo.getCurrentAndSubAreaInStr());
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 上级办案场所及其下级办案场所
            map.put("dataAuth", " ia.area_id " + ControllerTool.getSessionInfo(request).getCurrentOrg().getPid());
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 公安领导-本部门及下级部门
            map.put("dataAuth", " ( ia.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                    + " or ca.zbmj_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                    + " or ca.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 法制人员-上级部门及下级部门
            map.put("dataAuth", " ( ia.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
                    + " or ca.zbmj_pid like " + sessionInfo.getSuperAndSubOrgPid()
                    + " or ca.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuth(request))) {
            // 本部门
            map.put("dataAuth", " ( ia.op_pid = " + sessionInfo.getCurrentOrgPid()
                    + " or ca.zbmj_pid = " + sessionInfo.getCurrentOrgPid()
                    + " or ca.op_pid = " + sessionInfo.getCurrentOrgPid()
                    + " ) ");
        }
        List<SerialEntity> list = new ArrayList<SerialEntity>();
        int count = 0;
        list = this.serialService.otherPersonList(map);
        count = this.serialService.otherPersonCount(map);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", count);
        result.put("rows", list);
        return result;
    }

    /**
     * 其他人入区首页只显示未出区的
     *
     * @param params
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/otherPersonList2")
    @ResponseBody
    public Map<String, Object> otherPersonList2(@RequestParam Map<String, Object> params, HttpServletRequest request) throws Exception {
        Map<String, Object> map = ControllerTool.mapFilter(params);
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuth(request))) {
            // 办案人员-本人
            map.put("dataAuth", " ( ia.send_user_id=" + ControllerTool.getUserID(request)
                    + " or ia.in_register_user_id=" + ControllerTool.getUserID(request)
                    + " or ia.out_register_user_id=" + ControllerTool.getUserID(request)
                    + " or ca.zbmj_id= " + ControllerTool.getUserID(request)
                    + " or locate('," + ControllerTool.getUserID(request) + ",', ca.xbmj_ids)"
                    + " or ca.cjr=" + ControllerTool.getUserID(request)
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_SELFAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 办案场所-本办案场所
            map.put("dataAuth", " ia.area_id=" + ControllerTool.getCurrentAreaID(request));
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 管理员 -本办案场所及下级办案场所
            map.put("dataAuth", " ia.area_id " + sessionInfo.getCurrentAndSubAreaInStr());
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 上级办案场所及其下级办案场所
            map.put("dataAuth", " ia.area_id " + sessionInfo.getSuperAndSubAreaInStr());
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 公安领导-本部门及下级部门
            map.put("dataAuth", " ( ia.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                    + " or ca.zbmj_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                    + " or ca.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 法制人员-上级部门及下级部门
            map.put("dataAuth", " ( ia.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
                    + " or ca.zbmj_pid like " + sessionInfo.getSuperAndSubOrgPid()
                    + " or ca.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuth(request))) {
            // 本部门
            map.put("dataAuth", " ( ia.op_pid = " + sessionInfo.getCurrentOrgPid()
                    + " or ca.zbmj_pid = " + sessionInfo.getCurrentOrgPid()
                    + " or ca.op_pid = " + sessionInfo.getCurrentOrgPid()
                    + " ) ");
        }
        List<SerialEntity> list = new ArrayList<SerialEntity>();
        int count = 0;
        list = this.serialService.otherPersonList2(map);
        count = this.serialService.otherPersonCount2(map);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", count);
        result.put("rows", list);
        return result;
    }

    /**
     * 嫌疑人入区
     *
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/suspectinsert")
    @ResponseBody
    public MessageEntity suspectinsert(@RequestBody SerialForm form, HttpServletRequest request,
                                       HttpServletResponse response) throws Exception {

        logger.info("InterrogateSerialForm=" + form);
        long startTime = System.currentTimeMillis();
        logger.info("嫌疑人入区===+" + (System.currentTimeMillis() - startTime));
        Boolean sfasFlag = false;
        // 新增案件start
        CaseEntity caseEntity = new CaseEntity();
        // 根据时间生成案件编号
        String ajbh = "AJ" + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
        // 根据案由id查询案由名称
        Map<String, Object> abMap = new HashMap<String, Object>();
        abMap.put("id", form.getAb());
        SerialEntity serialEntityAb = serialService.queryNatureByAb(abMap);
        // 案件类型
        String ajlxDesc = "";
        if (1 == form.getAjlx()) {
            ajlxDesc = "行政案件";
        } else if (2 == form.getAjlx()) {
            ajlxDesc = "刑事案件";
        }
        // 主办单位
        Map<String, Object> sendUserMap = new HashMap<String, Object>();
        sendUserMap.put("policeNo", form.getSendUserNO());
        SerialEntity serialEntitySendUser = serialService.queryOrgIdByPoliceNo(sendUserMap);
        if (serialEntityAb != null && serialEntitySendUser != null) {
            caseEntity.setAjmc(new SimpleDateFormat("yyyyMMdd").format(new Date()) + " " + ajlxDesc + " " + serialEntityAb.getCaseNature() + " " + serialEntitySendUser.getOrganization() + " " + serialEntitySendUser.getPoliceRealName());
        }
        // 处理案件编号
        if (!"".equals(form.getAjbh()) && form.getAjbh() != null) {
            caseEntity.setAjbh(form.getAjbh());
        } else {
            caseEntity.setAjbh(ajbh);
        }
        caseEntity.setAjlx(form.getAjlx());
        caseEntity.setAb(form.getAb());
        caseEntity.setZbmjId(form.getSendUserId().intValue());
        caseEntity.setZbdwId(serialEntitySendUser.getOrgId());
        caseEntity.setUuid(java.util.UUID.randomUUID().toString());
        caseEntity.setCreatedTime(new Date());
        caseEntity.setUpdatedTime(caseEntity.getCreatedTime());
        caseEntity.setCjr(ControllerTool.getUser(request).getId());
        caseEntity.setZbmjPid(serialEntitySendUser.getPid());
        SessionInfo sessionInfoAJ = ControllerTool.getSessionInfo(request);
        if (ControllerTool.getSessionInfo(request).getCurrentOrg().getPid() != null && ControllerTool.getSessionInfo(request).getCurrentOrg().getPid() != "") {
            caseEntity.setOpPid(sessionInfoAJ.getCurrentOrg().getOp_pid());
        }
        caseService.insertCase(caseEntity);
        if (form.getAjlx()==2) {
            sfasFlag=true;
        }else{
            if (("故意杀人,故意伤害,强奸,抢劫,走私、贩卖、运输、制造毒品,放火,爆炸,绑架").indexOf(serialEntityAb.getCaseNature())>-1) {
                //1、故意杀人2、故意伤害3、强奸4、抢劫5、走私、贩卖、运输、制造毒品罪6、放火
                //7、爆炸
                //8、投放危害物质罪
                //9、绑架
                sfasFlag=true;
            }
        }

        // 新增案件end
        logger.info("嫌疑人入区案件新增===+" + (System.currentTimeMillis() - startTime));
        final CaseEntity threadCase = caseEntity;
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                //插入即时通讯数据库数据
//                String flag = PropertyUtil.getContextProperty("insert.oraytalk.database").toString();
//                int databaseSouce = Integer.parseInt(PropertyUtil.getContextProperty("database.source.id").toString());
//                Integer addValue = Integer.parseInt(PropertyUtil.getContextProperty("base.database.id.add").toString());
//                if ("1".equals(flag)) {
//                    logger.error("insert.oraytalk.database start");
//                    int caseId = threadCase.getId();
//                    try {
//                        threadCase.setId(threadCase.getId() + addValue);
//                        threadCase.setDatabaseSource(databaseSouce);
//                        threadCase.setTableId(Long.parseLong(caseId + ""));
//                        caseOrayTalkService.insertCase(threadCase);
//                    } catch (Exception ex) {
//                        logger.error("insert.oraytalk.database=", ex);
//                    }
//                    //   threadCase.setId(caseId);
//                    logger.error("insert.oraytalk.database end");
//                }
//
//            }
//        }).start();

        logger.info("嫌疑人入区即时通讯end======" + (System.currentTimeMillis() - startTime));
        PersonEntity person = new PersonEntity();
        person.setSex(form.getSex());
        person.setCountry(form.getCountry());
        person.setNation(form.getNation());
        person.setCensusRegister(form.getCensusRegister());
        person.setOpPid(ControllerTool.getSessionInfo(request).getCurrentOrg().getPid());
        person.setOpUserId(ControllerTool.getSessionInfo(request).getUser().getId());
        // 根据时间生成编号
        final String no = "XYR" + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
        // 判断是否为无名氏
        person.setCertificateTypeId(form.getCertificateTypeId());
        if (form.getCertificateTypeId() != 7) {
            Date birth = new Date();
            person.setCertificateNo(form.getCertificateNo());
            person.setName(form.getName());
            if (null != form.getBirth() && !"".equals(form.getBirth())) {
                person.setBirth(form.getBirth());
            } else {
                if (form.getCertificateTypeId() == 111) {
                    try {
                        String icno = form.getCertificateNo();
                        String year = icno.substring(6, 10);
                        String month = icno.substring(10, 12);
                        String day = icno.substring(12, 14);
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        birth = sdf.parse(year + "-" + month + "-" + day);
                        person.setBirth(birth);
                        //判断是否未成年
                        int age = 0;
                        char[] number = icno.toCharArray();
                        boolean flag = true;
                        int years = Calendar.getInstance().get(Calendar.YEAR);
                        if (flag && icno.length() == 15) {
                            age = (years - Integer.parseInt("19" + icno.substring(6, 8)));
                        } else if (flag && icno.length() == 18) {
                            age = (years - Integer.parseInt(icno.substring(6, 10)));
                        }
                        if (age <= 18 && age != 0) {
                            sfasFlag = true;
                        }

                    } catch (Exception e) {
                    }
                }
            }
        } else {// 无名氏
            person.setCertificateNo(no);
            person.setName(form.getName());
        }
        // 生成UUID
        String uuid = java.util.UUID.randomUUID().toString();
        SerialEntity entity = new SerialEntity();

        if (null != form.getRybh() && !"".equals(form.getRybh())) {
            entity.setRybh(form.getRybh());
        }
        if (null != form.getAjbh() && !"".equals(form.getAjbh())) {
            entity.setAjbh(form.getAjbh());
        }
        if (entity.getRybh() != null && entity.getAjbh() != null) {
            // 自动关联
            entity.setSfzdgl(1);
        } else {
            // 未关联
            entity.setSfzdgl(0);
        }

        final String[] bundlingResult = {""};
        try {
            // 预约id=-1时，表示未预约
            if (form.getOrderRequestId() == null || form.getOrderRequestId() == -1) {
                person.setAreaId(form.getAreaId());
                person.setCaseId(form.getCaseId());
                //未预约嫌疑人默认中国汉族人
                person.setCountry(156);
                person.setNation(1);
                String personUuid = UUID.randomUUID().toString().replace("-", "");
                person.setIsArrive(1);
                person.setUuid(personUuid);
                //插入人员
                personService.insert(person);
                this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "添加嫌疑人信息" + person, ControllerTool.getRoleName(request), true, OperLogEntity.SYSTEM_BACS);
                entity.setPersonId(person.getId());
            } else {
                //更新人员
                person.setId(Long.valueOf(form.getPersonId()));
                person.setIsArrive(1);
                personService.update(person);
                entity.setPersonId(Long.parseLong(form.getPersonId() + ""));
            }
            if (!"".equals(form.getPersonNo()) && form.getPersonNo() != null) {
                entity.setRybh(form.getPersonNo());
            }
            entity.setSfxxcj(form.getSfxxcj());
            entity.setCaseId(caseEntity.getId());
            entity.setSendUserId(form.getSendUserId());
            entity.setSerialNo(no);
            entity.setUuid(uuid);
            entity.setType(0);// 0嫌疑人
            entity.setPersonInfo(form.getPersonInfo());
            entity.setInRegisterUserId(ControllerTool.getSessionInfo(request).getUser().getId());
            entity.setOpPid(ControllerTool.getSessionInfo(request).getCurrentOrg().getPid());
            // 预约id=-1时，表示未预约
            if (form.getOrderRequestId() != null && form.getOrderRequestId() != -1) {
                entity.setOrderId(form.getOrderRequestId());
            }
            // 设置默认值
            entity.setStatus(0);// 0已入区
            entity.setAreaId(form.getAreaId());
            int roleId = ControllerTool.getSessionInfo(request).getRole().getId();
            entity.setEntranceProcedure(form.getEntranceProcedure());
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if (form.getWrittenTime() != null && !"".equals(form.getWrittenTime())) {
                entity.setWrittenTime(df.parse(form.getWrittenTime()));
            }
           /* if (form.getCaseId() != null && form.getCaseId() > 0) {
                entity.setCaseId(form.getCaseId());
            }*/
            logger.info("----------------开始添加---" + entity.toString());
            entity.setInPhotoId("rq-" + no + "-yt.jpg");// 设置入区照片ID
            if (form.getCuffId() != null && form.getCuffId() > 0) {
                entity.setCuffId(form.getCuffId());
                // 手环是否绑定成功与否都要入区，只提示调用手环绑定失败
                /* 插入入区表和状态表 */
                /* 修改手环状态 */
                CuffEntity cuff = this.cuffService.criminalBindCuff(entity);
                entity.setCuffNo(cuff.getCuffNo());
                this.cuffLogService.insertLog(cuff.getId(), CuffLogEntity.BINGDING_TYPE, "手环绑定成功！" + cuff.getId());
            } else {
                serialService.insert(entity);
            }
            if (entity.getId() > 0) {
                ResultEntity result = createSerialVideoMappingInfo(entity);
                if (result.isStatus() == false) {
                    logger.info("生成音视频绑定数据错误-----" + result.getMessage());
                }
            }
            logger.info("嫌疑人入区添加======" + (System.currentTimeMillis() - startTime));
            logger.info("----------------结束添加---" + entity.toString());
            logger.info("interrogateSerialService.insert entity=" + entity);
            SerialStatusEntity iss = new SerialStatusEntity();
            iss.setInterrogateSerialId(entity.getId());
            iss.setStatusName("已入区");
            serialStatusService.insert(iss);
            this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "添加嫌疑人入区登记信息" + entity,
                    ControllerTool.getSessionInfo(request).getUser().getRealName(), true, OperLogEntity.SYSTEM_BACS);
            /* 如果有预约编号，修改预约状态为已到达 */
            if (form.getOrderRequestId() != null && form.getOrderRequestId().intValue() > 0) {
                //修改嫌疑人状态为已到达
                PersonEntity personEntity = new PersonEntity();
                // 向预约状态日志中插入一条已到达的数据
                OrderStatusEntity status = new OrderStatusEntity();
                //修改预约状态
                OrderRequestEntity orderRequestEntity = new OrderRequestEntity();

                personEntity.setId(Long.parseLong(form.getPersonId() + ""));
                personEntity.setIsArrive(1);

                List<OrderPersonEntity> orderPersonEntityList = orderPersonService.getNoArrivePersonByOrderId(form.getOrderRequestId());
                orderRequestEntity.setId(form.getOrderRequestId());
                if (orderPersonEntityList != null && orderPersonEntityList.size() == 1) {

                    if (Long.parseLong(orderPersonEntityList.get(0).getPersonId() + "") == personEntity.getId()) {
                        //已到达
                        orderRequestEntity.setStatus(2);
                        status.setStatusName("已到达");
                    } else {
                        //部分嫌疑人已到达
                        orderRequestEntity.setStatus(5);
                        status.setStatusName("部分已到达");
                    }
                } else {
                    //部分嫌疑人已到达
                    orderRequestEntity.setStatus(5);
                    status.setStatusName("部分已到达");
                }
                personService.update(personEntity);
                orderRequestService.changStatus(orderRequestEntity);

                status.setOrderRequestId(form.getOrderRequestId());
                //为op_pid赋值
                SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
                if (sessionInfo.getCurrentOrg().getOp_pid() != null && sessionInfo.getCurrentOrg().getOp_pid() != "") {
                    status.setOpPid(sessionInfo.getCurrentOrg().getOp_pid());
                }
                status.setOpUserId(ControllerTool.getUser(request).getId());
                orderStatusService.insert(status);
                this.operLogService.insertLog(OperLogEntity.EDIT_TYPE, "修改预约状态为已到达" + form.getOrderRequestId(),
                        ControllerTool.getSessionInfo(request).getUser().getRealName(), true, OperLogEntity.SYSTEM_BACS);
                this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "向预约状态日志中插入一条已到达的数据" + status,
                        ControllerTool.getSessionInfo(request).getUser().getRealName(), true, OperLogEntity.SYSTEM_BACS);
            }
            logger.info("嫌疑人入区修改预约信息结束======" + (System.currentTimeMillis() - startTime));
            logger.info("市局人员信息上传........");
            try {
                String sjhcurl = PropertyUtil.getContextProperty("sjhc.url") + "";
                String url = sjhcurl + "/addserial/" + uuid;
                HttpClientUtil.get(url, "");
            } catch (Exception e) {
                logger.info("ryxxInfo error " + e.getMessage());
                logger.error(e.getMessage(), e);
            }
            logger.info("市局人员信息上传结束........");
            OrganizationEntity uo = organizationService.getOrgByUserId(ControllerTool.getUserID(request)).get(0);
            /* 添加通知和待办事项 */
            final List<UserEntity> users = userService.getUsersByOrgAndType(uo.getId(), "管理中队");
            final List<UserEntity> userAs = userService.getUsersByOrgAndType(uo.getId(), "案件审核");
            logger.info("通知循环" + users);
            if (form.getAreaId() == 1) {
                // 调用天地伟业定位接口
                final Integer sendId = ControllerTool.getUser(request).getId();
                final String realName = ControllerTool.getUser(request).getRealName();
                final String clienIp = ControllerTool.getClienIp(request);
                final String pid = ControllerTool.getSessionInfo(request).getCurrentOrg().getPid();
                final PersonEntity personEntity = person;
                final SerialEntity serialEntity = entity;
                final HttpServletRequest request1 = request;
                final SerialForm serialForm = form;
                final Boolean finalSfasFlag = sfasFlag;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (users != null && users.size() > 0) {
                            logger.info("通知循环");
                            for (int i = 0; i < users.size(); i++) {
                                /* 添加待办事项 */
                                try {
                                    ScheduleEntity schedule = new ScheduleEntity();
                                    schedule.setSenderId(sendId);
                                    schedule.setReceiverId(users.get(i).getId());

                                    schedule.setTitle("入区通知");
                                    schedule.setContent("请针对嫌疑人" + personEntity.getName() + "（证件号：" + personEntity.getCertificateNo() + "）开展情报收集。专属编号：" + no);
                                    schedule.setType(1);// 1合成作战
                                    schedule.setStatus("0");//
                                    schedule.setSystemName("BA");
                                    schedule.setOpPid(pid);
                                    schedule.setOpUserId(sendId);
                                    noticeService.insertSchedule(schedule);
                                    /* 添加通知 */
                                    InformationEntity inform = new InformationEntity();
                                    inform.setSenderId(Long.parseLong(sendId + ""));
                                    inform.setReceiverId(Long.parseLong(sendId + ""));
                                    inform.setTitle("入区通知");
                                    inform.setContent(serialForm.getName() + "已入区");
                                    inform.setSendTime(new Date());
                                    inform.setSystemName("BA");
                                    inform.setType(0);
                                    inform.setIsRead(0);
                                    inform.setOpPid(pid);
                                    inform.setOpUserId(sendId);
                                    iInformationService.insertInform(inform);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        if(finalSfasFlag){
                            if (userAs != null && userAs.size() > 0) {
                                logger.info("案件审核通知循环");
                                for (int i = 0; i < userAs.size(); i++) {
                                    try {
                                        /* 添加通知 */
                                        InformationEntity inform = new InformationEntity();
                                        inform.setSenderId(Long.parseLong(sendId + ""));
                                        inform.setReceiverId(Long.parseLong(sendId + ""));
                                        inform.setTitle("入区案件审核通知");
                                        inform.setContent(serialForm.getName() + "已入区");
                                        inform.setSendTime(new Date());
                                        inform.setSystemName("BA");
                                        inform.setType(0);
                                        inform.setIsRead(0);
                                        inform.setOpPid(pid);
                                        inform.setOpUserId(sendId);
                                        iInformationService.insertInform(inform);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                        // 绑定手环，创建FTP
                        String ret = "";
                        String bundlingResult1 = "";
                        try {
                            ret = tiandyBindCuffYb(personEntity, serialEntity, realName, sendId, clienIp, serialForm.getAreaId());
                            logger.info("天地入区结果======" + ret);
                            if (!"0".equals(ret)) {
                                bundlingResult1 = "入区登记成功，但手环绑定失败！错误编码[" + ret + "]";
                                cuffLogService.insertLog(serialForm.getCuffId(), CuffLogEntity.BINGDING_TYPE, bundlingResult1);
                                //绑定失败默认没有传给天地数据
                                operLogService.insertLog("入区传天地数据错误", "天地返回数据错误：" + ret + ",入区传给天地数据失败，导致本地已入区，但天地平台没有入区的数据;姓名：" + personEntity.getName() + ",入区编号：" + serialEntity.getSerialNo() + "手环编号：" + serialForm.getCuffId(),
                                        realName, true, OperLogEntity.SYSTEM_BACS);
                            } else {
                                bundlingResult1 = "手环绑定成功！";
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            bundlingResult1 = "入区登记成功，但手环绑定失败!";
//                    //绑定失败默认没有传给天地数据
                            operLogService.insertLog("入区传天地数据错误", "本地异常：" + e.getMessage() + ",入区传给天地数据失败，导致本地已入区，但天地平台没有入区的数据;姓名：" + personEntity.getName() + ",入区编号：" + serialEntity.getSerialNo(),
                                    realName, true, OperLogEntity.SYSTEM_BACS);
//                        插入新表
                            operLogService.insertTdLog(serialEntity.getId(), "入区传天地数据错误", "本地异常：" + e.getMessage(), "");
                            logger.error("", e);
                            logger.info("天地入区结果======" + ret);
                        }
                    }
                }).start();
//                try {
//                ret = tiandyBindCuff(personEntity, serialEntity, request1, serialForm.getAreaId());
//                    logger.info("嫌疑人入区绑定手环结束======" + (System.currentTimeMillis() - startTime));
//                    if (!"0".equals(ret)) {
//                        bundlingResult = "入区登记成功，但手环绑定失败！错误编码[" + ret + "]";
//                        this.cuffLogService.insertLog(form.getCuffId(), CuffLogEntity.BINGDING_TYPE, bundlingResult);
//                        //绑定失败默认没有传给天地数据
//                        this.operLogService.insertLog("入区传天地数据错误", "天地返回数据错误：" + ret + ",入区传给天地数据失败，导致本地已入区，但天地平台没有入区的数据;姓名：" + person.getName() + ",入区编号：" + entity.getSerialNo() + "手环编号：" + form.getCuffId(),
//                                ControllerTool.getSessionInfo(request).getUser().getRealName(), true, OperLogEntity.SYSTEM_BACS);
////                        插入新表
////                        this.operLogService.insertTdLog(entity.getId(),"入区传天地数据错误","天地返回数据错误："+ret);
//                    } else {
//                        bundlingResult = "手环绑定成功！";
//                    }
//                } catch (Exception e) {
//                    bundlingResult = "入区登记成功，但手环绑定失败!";
//                    //绑定失败默认没有传给天地数据
//                    this.operLogService.insertLog("入区传天地数据错误", "本地异常：" + e.getMessage() + ",入区传给天地数据失败，导致本地已入区，但天地平台没有入区的数据;姓名：" + person.getName() + ",入区编号：" + entity.getSerialNo(),
//                            ControllerTool.getSessionInfo(request).getUser().getRealName(), true, OperLogEntity.SYSTEM_BACS);
////                        插入新表
//                    this.operLogService.insertTdLog(entity.getId(), "入区传天地数据错误", "本地异常：" + e.getMessage(), "");
//                    logger.error("", e);
//                }

            } else {
                try {
                    /* 通知DLS定位系统 */
                    logger.info("===============定位系统处理解绑数据：" + entity.getCuffNo());
                    logger.info("嫌疑人入区绑定DLS开始======" + (System.currentTimeMillis() - startTime));
                    DLSProcessApiService service = (DLSProcessApiService) RMIUtil.getInstance().lookupService("DLSProcessApiService");
                    service.isActiveTag(Long.parseLong(entity.getCuffNo()), true);
                    logger.info("嫌疑人入区绑定DLS结束======" + (System.currentTimeMillis() - startTime));
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("系统报错：" + e);

            return new MessageEntity().addCode(1).addIsError(true).addTitle("错误")
                    .addContent(bundlingResult[0] + "添加失败:" + e.getMessage());
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("提醒").addContent(bundlingResult[0] + "添加成功!")
                .addCallbackData(entity);
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

    @RequestMapping(value = "/querypersonstatus")
    @ResponseBody
    public MessageEntity queryPersonStatus(@RequestBody SerialForm form, HttpServletRequest request)
            throws Exception {
        System.err.println("form= " + form);
        int result = 0;
        try {
            SerialEntity serial = new SerialEntity();
            serial.setSerialNo(form.getSerialNo());
            serial = serialService.getSerialByNo(serial);
            result = serialStatusService.queryPersonStatus(serial.getId());
            System.err.println("result=" + result);
        } catch (Exception e) {
            logger.error("", e);
            return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("查询嫌疑人状态失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("提醒").addContent("查询嫌疑人状态成功!")
                .addCallbackData(result);
    }

    @RequestMapping("/jzFillByRybhAndAJbh")
    @ResponseBody
    public Map<String, Object> jzFillByRybhAndAJbh(@RequestBody Map<String, Object> maps) {
        Map<String, Object> map = ControllerTool.mapFilter(maps);
        Map<String, Object> result = new HashMap<>();
        List<Map<String, Object>> list = serialService.jzFillByRybhAndAJbh(map);
        if (list.size() == 1) {
            return list.get(0);
        }
        return result;
    }


    @RequestMapping(value = "/queryJzAjxxByzjhm")
    @ResponseBody
    public Map<String, Object> queryJzAjxxByzjhm(@RequestParam Map<String, Object> pageMap, HttpServletRequest request,
                                                 HttpServletResponse response, String zjhm) throws Exception {
        logger.info("开始警综信息数据查询------------------");
        Map<String, Object> map = ControllerTool.mapFilter(pageMap);
        /*String  orgCode= ControllerTool.getSessionInfo(request).getCurrentOrg().getOrgCode();
        map.put("orgCode", orgCode);*/
        System.err.println("--=====---" + map);
        logger.info("证件号码------------------" + zjhm);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        int total = 0;
        int number;
        map.put("zjhm", zjhm);
        Map<String, Object> result = new HashMap<String, Object>();
        List<Map<String, Object>> persons = this.serialService.selectJzInfoByzjhm(map);
        logger.info("根据证件号码查询案件编号------------------" + persons);
        if (persons != null && persons.size() > 0) {
            String tempAjbh = "";
            for (int i = 0; i < persons.size(); i++) {
                tempAjbh += "'" + persons.get(i).get("AJBH") + "'";
                if (i != persons.size() - 1) {
                    tempAjbh += ",";
                }
            }
            map.put("ajbh", tempAjbh);
            number = 0;
        } else {
            number = 1;
            map.put("ajbh", "'" + "测试" + "'");
        }
        list = this.serialService.queryJzDataAjxx(map);
        logger.info("警综信息数据查询:" + list);
        total = this.serialService.queryJzDataAjxxCount(map);
        result.put("total", total);
        result.put("number", number);
        result.put("rows", list);
        System.err.println(request);
        return result;
    }

    @RequestMapping(value = "/outSerialGetPicture")
    @ResponseBody
    public MessageEntity outSerialGetPicture(@RequestBody ImageForm form, HttpServletRequest request,
                                             HttpServletResponse response) throws Exception {
        try {
            logger.info("### outSerialGetPicture ImageForm= " + form.getImageData());
            SerialEntity serial = new SerialEntity();
            serial.setId(form.getSerialId());
            serial = serialService.getSerialById(serial);
            logger.info("### 出区获取serial= " + serial);
            String serialNo = serial.getSerialNo();
            String path = PropertyUtil.getContextProperty("faceImageFileSavePath").toString()
                    + Utils.getDateFromSerialNO(serialNo) + File.separator;
            Map<String, Object> map = new HashMap<>();
            map.put("serialId", form.getSerialId());
            List<SerialVideoMappingEntity> slist = serialVideoMappingService.list(map);
            if (slist.size() > 0) {
                path = slist.get(0).getWebPath() + File.separator + "faceImageUpload" + File.separator
                        + Utils.getDateFromSerialNO(serialNo) + File.separator;
            }
            AsynUtil.getInstance().createLocalDir(path);
            // 保存图片
            String image = form.getImageData();
            File file = new File(path + "cq-" + serialNo + "-yt.jpg");
            if (!file.exists()) {
                file.createNewFile();
            }
            String data = image.substring(22);
            InputStream in = null;
            OutputStream fileout = null;
            try {
                in = MimeUtility.decode(new ByteArrayInputStream(data.getBytes()), "base64");
                fileout = new FileOutputStream(file);
                byte[] buffer = new byte[4096];
                int bLen = 0;
                while ((bLen = in.read(buffer, 0, 4096)) > 0) {
                    fileout.write(buffer, 0, bLen);
                }
                fileout.write(image.getBytes());
                fileout.close();
            } catch (Exception e) {
                logger.error("", e);
                throw e;
            } finally {
                if (in != null) {
                    in.close();
                }
                if (fileout != null) {
                    fileout.close();
                }
            }
            // 图片结束
            // 1:1比对
            if (Boolean.parseBoolean(PropertyUtil.getContextProperty("is.open.face").toString())) {
                Face1v1Result face1v1Result = null;// faceImgOneToOne(serialNo,
                logger.info("### 人像1V1 face1v1Result= " + face1v1Result);
                if (face1v1Result != null) {
                    return new MessageEntity().addCode(1).addIsError(false).addTitle("提醒").addContent("人像比对成功！")
                            .addCallbackData(face1v1Result);
                } else {
                    return new MessageEntity().addCode(1).addIsError(false).addTitle("提醒")
                            .addContent("经过人像系统比对，两张照片不是同一个人！");
                }
            } else {
                logger.info("### 没有人像对比1V1  ");
                return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("人像比对成功!");
            }

        } catch (Exception e) {
            logger.error("", e);
        }
        return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("人像比对成功!");
    }

    @RequestMapping(value = "/suspectexit")
    @ResponseBody
    public MessageEntity suspectExit(@RequestBody SerialForm form, HttpServletRequest request,
                                     HttpServletResponse response) throws Exception {
        logger.error("### 出区form=" + form);
        /* Map<String, String> params = new HashMap<String, String>(); */
        String unBundlingResult = "出区失败！";
        long startTime = System.currentTimeMillis();
        logger.info("嫌疑人出区开始======" + (System.currentTimeMillis() - startTime));
        int roleId = ControllerTool.getSessionInfo(request).getRole().getId();
        try {
            SerialEntity entity = new SerialEntity();
            entity.setSerialNo(form.getSerialNo());
            entity = serialService.getSerialByNo(entity);

            if (null != form.getRybh() && !"".equals(form.getAjbh())) {
                entity.setJzrybh(form.getRybh());
            }
            if (null != form.getAjbh() && !"".equals(form.getAjbh())) {
                entity.setAjbh(form.getAjbh());
            }

            if (null != form.getYy() && !"".equals(form.getYy())) {
                entity.setUnRelationReason(form.getYy());
            }
            entity.setSfzdgl(form.getSfzdgl());
            entity.setOutType(form.getOutType());
            entity.setOutReason(form.getOutReason());
            entity.setConfirmResult(form.getConfirmResult()); // 裁决结果
            entity.setOutRegisterUserId(ControllerTool.getSessionInfo(request).getUser().getId());
            System.err.println("出区entity11= " + entity);
            System.err.println("RoleID====" + roleId);
            entity.setOutPhotoId("cq-" + form.getSerialNo() + "-yt.jpg");
            //rybh
            entity.setRybh(form.getPersonNo());
            //是否送押解队
            entity.setSfsyjd(form.getSfsyjd());
            // 手环
            CuffEntity cuff = new CuffEntity();
            System.err.println("getOutType=======" + entity.getOutType());
            if (entity.getOutType() == 1) {
                cuff.setStatus(3);// 将手环状态修改为临时出区
                entity.setStatus(10);
                System.err.println("出区entity22= " + entity);
                serialService.exit(entity);
                this.operLogService.insertLog(OperLogEntity.EDIT_TYPE, "嫌疑人临时出区" + entity,
                        ControllerTool.getSessionInfo(request).getUser().getRealName(), true, OperLogEntity.SYSTEM_BACS);
                serialService.changestatus(entity.getId(), 10);
                TempOutEntity tempOut = new TempOutEntity();
                tempOut.setSerialId(entity.getId());
                tempOut.setOutReason(entity.getOutReason());
                System.err.println("临时出区tempBack= " + tempOut);
                System.err.println("333333333333333333333");
                tempOutService.insert(tempOut);// 向临时出区表中插入记录
                System.err.println("444444444444444444444");
                unBundlingResult = "临时出区成功！";
                logger.info("嫌疑人临时出区结束======" + (System.currentTimeMillis() - startTime));
                // 临时出去调用天地伟业定位接口
                try {
                    String ret = tiandyTempOut(entity.getUuid(), request, form.getAreaId());// 临时出区
                    logger.info("临时出区 手环带出 编码  ret=" + ret); // 手环带出
                    if (!"0".equals(ret)) {
                        cuff.setStatus(2);// 将手环状态修改为损坏
                        unBundlingResult = "临时出区手环解绑失败！错误编码[" + ret + "]";
                        logger.info("手环解绑接口结果=" + unBundlingResult);
                    } else {
                        unBundlingResult = "手环解绑成功！";
                    }
                    logger.info("嫌疑人临时出区解绑结束======" + (System.currentTimeMillis() - startTime));
                } catch (Exception e) {
                    unBundlingResult = "临时出区手环解绑失败!";
                    logger.error("", e);
                    this.cuffLogService.insertLog(entity.getCuffId(), CuffLogEntity.UNBINGDING_TYPE,
                            "临时出区手环解绑失败" + entity.getCuffId());
                }

            } else {
                cuff.setStatus(0);// 将手环状态修改为空闲
                entity.setStatus(11);
                System.err.println("出区111111111111 " + entity);
                serialService.exit(entity);
                this.operLogService.insertLog(OperLogEntity.EDIT_TYPE, "嫌疑人出区" + entity.getSerialNo(),
                        ControllerTool.getSessionInfo(request).getUser().getRealName(), true, OperLogEntity.SYSTEM_BACS);
                serialService.changestatus(entity.getId(), 11);
                unBundlingResult = "正常出区成功！";
                logger.info("嫌疑人出区结束======" + (System.currentTimeMillis() - startTime));
                try {
                    String ret = tiandyFinalOut(entity.getUuid(), request, form.getAreaId());// 正常出区
                    logger.info("正常出区 手环带出 编码  ret=" + ret); // 手环带出
                    if (!"0".equals(ret)) {
                        cuff.setStatus(2);// 将手环状态修改为损坏
                        unBundlingResult = "正常出区手环解绑失败！错误编码[" + ret + "]";
                        logger.info("手环解绑接口结果=" + unBundlingResult);
                    } else {
                        unBundlingResult = "手环解绑成功！";
                    }
                    logger.info("嫌疑人出区解绑结束======" + (System.currentTimeMillis() - startTime));
                } catch (Exception e) {
                    unBundlingResult = "正常出区手环解绑失败!";
                    logger.error("", e);
                    this.cuffLogService.insertLog(entity.getCuffId(), CuffLogEntity.UNBINGDING_TYPE,
                            "正常出区手环解绑失败" + entity.getCuffId());
                }
            }
            if (entity.getCuffId() != null && entity.getCuffId() > 0) {
                cuff.setId(entity.getCuffId());
                if (entity.getOutType() == 1) {
                    this.cuffLogService.insertLog(entity.getCuffId(), CuffLogEntity.UNBINGDING_TYPE, "临时出区   手环带出");
                } else {
                    this.cuffLogService.insertLog(entity.getCuffId(), CuffLogEntity.UNBINGDING_TYPE, "正常出区 手环带出");
                }
                logger.info("手环解绑    嫌疑人        cuff=" + cuff);
                if (entity.getOutType() != 1) {
                    // 非临时出区，解绑手环
                    this.cuffService.unbindCuffById(entity.getCuffId());
                } else if (entity.getOutType() == 1) {
                    // 临时出区，设置属性
                    this.cuffService.tempOutUnbindCuffById(entity.getCuffId());
                }
                logger.info("生成轨迹任务........serialId:" + entity.getId() + ";cuffNo:" + entity.getCuffNo());
                logger.info("嫌疑人出区生产任务轨迹开始======" + (System.currentTimeMillis() - startTime));
                insertDownloadTack(entity.getId(), entity.getCuffNo());
                logger.info("嫌疑人出区生产任务轨迹结束======" + (System.currentTimeMillis() - startTime));
            }
            String uuid = entity.getUuid();

            logger.info("市局人员信息更新........+" + uuid);
            try {
                String sjhcurl = PropertyUtil.getContextProperty("sjhc.url") + "";
                String url = sjhcurl + "/updateserial/" + uuid + "&cq";
                HttpClientUtil.get(url, "");
            } catch (Exception e) {
                logger.info("ryxxInfo error " + e.getMessage());
                logger.error(e.getMessage(), e);
            }
            logger.info("市局人员信息更新结束........");
            try {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("serialId", entity.getId());
                List<RecordEntity> records = recordService.getRecordRoomInfoBySerialId(map);
                if (records != null && records.size() > 0) {
                    for (RecordEntity r : records) {
                        map.clear();
                        map.put("roomId", r.getRmId());
                        String cameraNo = recordService.getRoomCameraNo(map);
                        map.put("status", 3);
                        map.put("recordId", r.getRecordId());
                        recordDataService.updateRecordStatus(map);
                        // 更新嫌疑人状态状态
                        Map<String, Object> mapRoom = new HashMap<>();
                        mapRoom.put("recordId", r.getRecordId());
                        mapRoom.put("roomStatus", 0);
                        recordDataService.updateRoom(mapRoom);
                        logger.info("Exit updateRecordStatus：update room status,recordId=" + r.getRecordId());
                        // 提交笔录后，将传递的笔录ID和摄像头监控点编号来拆分成任务的时间段
                        Worker thread = new Worker(Long.valueOf(r.getRecordId()), cameraNo);
                        thread.start();
                        // 传递笔录ID来查看要更新的房间名和LED IP
                        try {
                            List<LedEntity> ledEntities = recordDataService.getLedInfo(mapRoom);
                            if (ledEntities != null && ledEntities.size() > 0) {
                                LEDControlEntity led = new LEDControlEntity();
                                led.setColor("red");
                                led.setContent(ledEntities.get(0).getRoomName());
                                led.setIp(ledEntities.get(0).getIp());
                                led.setPort(ledEntities.get(0).getPort());
                                led.setShowType(1);
                                led.setPower(1);
                                led.setSaveMethod(LEDControlEntity.POWER_PLAY);
                                // 获取led启动后台web service url
                                String url = PropertyUtil.getContextProperty("sms.url") + "";
                                logger.info("设置led参数：" + led.toString());
                                // 启动led
                                LedUtil.controlLED(url, led);
                            }
                        } catch (Exception e) {
                            logger.info("RecordWS updateRecordStatus led设置异常：" + e.getMessage());
                        }
                    }
                }
                logger.info("嫌疑人出区检查审讯结束======" + (System.currentTimeMillis() - startTime));
                // 检查审讯
                AreaEntity areaEntity = ControllerTool.getCurrentArea(request);
                if (areaEntity != null && areaEntity.getId() > 0) {
                    List<PersonalConfigDetailEntity> pcs = personalConfigDetailService.listDetailByCodeAndParam("Door", "", (long) areaEntity.getId());
                    if (pcs.size() > 0) {
                        String type = "";
                        String url = "";
                        for (PersonalConfigDetailEntity p : pcs) {
                            if (p.getParamKey().equals("type")) {
                                type = p.getParamValue();
                                break;
                            }
                        }
                        if (type != "") {
                            if (type.equals("hik")) {
                                if (entity.getCuffIcNo() != null && entity.getCuffIcNo() != "") {
                                    MessageEntity rs = HikUtil8700.cardLoss(entity.getCuffIcNo());
                                    if (rs.isError()) {
                                        System.err.println("rs.getContent()=======================" + rs.getContent());
                                        return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent(rs.getContent());
                                    }
                                }
                            } else if (type.equals("bio")) {
                                String token = "";
                                for (PersonalConfigDetailEntity p : pcs) {
                                    switch (p.getParamKey()) {
                                        case "url":
                                            url = p.getParamValue();
                                            break;
                                        case "token":
                                            token = p.getParamValue();
                                            break;
                                    }
                                }

                                if (!url.equals("") && !token.equals("")) {

                                    if (entity.getCuffIcNo() != null && entity.getCuffIcNo() != "") {
                                        MessageEntity rs = izkDoorService.assignCard(String.format("%s/api/card/set?access_token=%s", url, token), entity.getCuffIcNo(), "");
                                        if (rs.isError()) {
                                            System.err.println("rs.getContent()=======================" + rs.getContent());
                                            return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent(rs.getContent());
                                        }
                                    }
                                }
                            } else if (type.equals("zok")) {
                                String groupid = "0";
                                for (PersonalConfigDetailEntity p : pcs) {
                                    switch (p.getParamKey()) {
                                        case "url":
                                            url = p.getParamValue();
                                            break;
                                    }
                                    switch (p.getParamKey()) {
                                        case "c_groupid":
                                            groupid = p.getParamValue();
                                            break;
                                    }
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {
                this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "出区结束审讯 更新InqeustRecord", ControllerTool.getSessionInfo(request).getUser().getRealName(), false, OperLogEntity.SYSTEM_BACS);
                logger.error(e.getMessage(), e);
            }
            OrganizationEntity uo = organizationService.getOrgByUserId(ControllerTool.getUserID(request)).get(0);
            /* 添加通知和待办事项 */
            List<UserEntity> tzusers = userService.getUsersByOrgAndType(uo.getId(), "管理中队");
            logger.info("通知循环" + tzusers);
            if (tzusers != null && tzusers.size() > 0) {
                logger.info("通知循环");
                for (int i = 0; i < tzusers.size(); i++) {
                    /* 添加通知 */
                    InformationEntity inform = new InformationEntity();
                    inform.setSenderId(ControllerTool.getSessionInfo(request).getUser().getId().longValue());
                    inform.setReceiverId(tzusers.get(i).getId().longValue());
                    inform.setTitle("出区通知");
                    inform.setContent(entity.getName() + "已出区");
                    inform.setType(0);
                    inform.setIsRead(0);
                    inform.setSendTime(new Date());
                    inform.setSystemName("BA");
                    this.iInformationService.insertInform(inform);
                }
            }
            logger.info("嫌疑人出区发送消息结束======" + (System.currentTimeMillis() - startTime));
            /*
            final  SerialEntity finalSerial = entity;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    logger.info("回传市局数据 begin");
                    try {
        				//人员信息接口
        				String ryxxUrl = (String) PropertyUtil.getContextProperty("ryxx.url");
        				String url = ryxxUrl + finalSerial.getId() ;
        				Map<String, String> param = new HashMap<>();
        				HttpClientUtil.get(url, param);
        			} catch (Exception e) {
        				logger.info("ryxxInfo error " + e.getMessage());
        				logger.error(e.getMessage(), e);
        			}
        			
        			try {
        				//人员信轨迹接口
        				String rygjUrl = (String) PropertyUtil.getContextProperty("rygj.url");
        				String url1 = rygjUrl + finalSerial.getId() ;
        				Map<String, String> param1 = new HashMap<>();
        				HttpClientUtil.get(url1, param1);
        			} catch (Exception e) {
        				logger.info("rygjInfo error " + e.getMessage());
        				logger.error(e.getMessage(), e);
        			}
        			
        			try {
        				//随身物品接口
        				sswpInfo(finalSerial.getId());
        			} catch (Exception e) {
        				logger.info("sswpInfo error " + e.getMessage());
        				logger.error(e.getMessage(), e);
        			}
                    logger.info("回传市局数据 end");
                }
            }).start();
			*/
            logger.info("嫌疑人出区开始======" + (System.currentTimeMillis() - startTime));
            logger.info("出区通知            entity.getName()=" + entity.getName() + "已出区");
            return new MessageEntity().addCode(1).addIsError(false).addTitle("提醒").addContent(unBundlingResult);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent(unBundlingResult);
        }
    }


    @RequestMapping(value = "/serialGlUpdate")
    @ResponseBody
    public MessageEntity serialGlUpdate(@RequestBody Map<String, Object> map, HttpServletRequest request)
            throws Exception {
        SerialEntity serialEntity = new SerialEntity();
        serialEntity.setId(Long.parseLong(map.get("id").toString()));
        serialEntity.setRybh(map.get("rybh").toString());
        serialEntity.setAjbh(map.get("ajbh").toString());
        serialEntity.setHczt(Integer.parseInt(map.get("hczt").toString()));
        serialEntity.setUnRelationReason(map.get("wgl").toString());
        serialEntity.setSfzdgl(Integer.parseInt(map.get("sfzdgl").toString()));
        try {
            serialService.update(serialEntity);
            SerialEntity interrogateSerialEntitys = serialService.getSerialById(serialEntity);
            String uuid = interrogateSerialEntitys.getUuid();
            logger.info("sswplq++++++uuid+++++++++++++++++++++++++" + uuid);
            String sjhcurl = PropertyUtil.getContextProperty("sjhc.url") + "";
            String url = sjhcurl + "/updateserial/" + uuid + "&cq";
            HttpClientUtil.get(url, "");
        } catch (Exception e) {
            return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("修改失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("提醒").addContent("修改成功!");
    }

    private void insertDownloadTack(long serialId, String cuffNo) throws Exception {
        // 查询该手环的所有轨迹
        Map<String, Object> param = new HashMap<>();
        param.put("serialId", serialId);
        param.put("cuffNo", cuffNo);
        // 定位数据转为手环轨迹信息
        trackService.bathCreateTrackInfo(param);
        // 获取手环轨迹信息
        List<TrackInfoEntity> tracks = trackService.listTrack(param);
        //int minutes = Integer.parseInt(PropertyUtil.getContextProperty("record.minutes").toString());
        for (TrackInfoEntity track : tracks) {
            // 获取拆分时间段的间隔时间
            int taskNo = 1;
            DownloadTaskEntity taskEntity = new DownloadTaskEntity();
            taskEntity.setCameraNo(track.getCameraNo());
            taskEntity.setTaskNo(taskNo);
            taskEntity.setTaskType(2);// 轨迹视频
            taskEntity.setSerialId(serialId);
            taskEntity.setCuffNo(cuffNo);
            taskEntity.setStatus(0);
            taskEntity.setStartTime(track.getStartTime());
            taskEntity.setEndTime(track.getEndTime());
            taskEntity.setHashValue(RandomUtils.nextInt(10));
            param.clear();
            param.put("serialId", serialId);
            param.put("cuffNo", cuffNo);
            param.put("starttime", track.getStartTime());
            param.put("endtime", track.getEndTime());
            param.put("camerano", track.getCameraNo());
            int taskCount = taskService.queryTaskCount(param);
            if (taskCount <= 0) {
                taskService.insert(taskEntity);
            }
        }
    }

    // 生成下载任务记录
    private void insertDownloadTack(InqeustRecordEntity entity) throws Exception {
        logger.info(" 生成下载任务记录");
        int minutes = Integer.parseInt(PropertyUtil.getContextProperty("record.minutes").toString());
        logger.info(" minutes" + minutes);
        // 获取拆分时间段的间隔时间
        // 获取拆分后的时间片段，开始时间，结束时间
        Map<Date, Date> timeSpanMap = TimeSplitUtil.timeSplit(entity.getStartTime(), entity.getEndTime(), minutes);
        // 根据room_id 获取摄像头
        Map<String, Object> map = new HashMap<>();
        map.put("roomId", entity.getRecordRoomId());
        logger.info("++sss+++" + entity.getRecordRoomId());
        List<CameraEntity> list = iCameraService.getCameraByRoomID(map);
        logger.info("++cameralist+++" + list);
        for (CameraEntity camera : list) {
            int taskNo = 1;
            for (Map.Entry<Date, Date> me : timeSpanMap.entrySet()) {
                DownloadTaskEntity taskEntity = new DownloadTaskEntity();
                taskEntity.setCameraNo(camera.getCameraNo());
                taskEntity.setTaskNo(taskNo);
                taskEntity.setTaskType(3);//
                taskEntity.setSerialId(entity.getSerialId());
                taskEntity.setStatus(0);
                taskEntity.setStartTime(me.getKey());
                taskEntity.setEndTime(me.getValue());
                taskEntity.setHashValue(RandomUtils.nextInt(10));
                taskEntity.setRecordId(entity.getId());
                logger.info("++taskEntity+++" + taskEntity);
                taskService.insert(taskEntity);
                taskNo++;
            }
        }
    }

    private class Worker extends Thread {

        private long recordId;
        private String cameraNo;

        public long getRecordId() {
            return recordId;
        }

        public void setRecordId(long recordId) {
            this.recordId = recordId;
        }

        public String getCameraNo() {
            return cameraNo;
        }

        public void setCameraNo(String cameraNo) {
            this.cameraNo = cameraNo;
        }

        public Worker(long recordId, String cameraNo) {
            setRecordId(recordId);
            setCameraNo(cameraNo);
            this.setPriority(Thread.NORM_PRIORITY);
        }

        public void run() {
            try {
                Map<String, Object> map = new HashMap<>();
                map.put("id", getRecordId());// 用来查询笔录的开始结束时间
                List<RecordEntity> recordEntities = recordDataService.getRecordInfo(map);
                if (recordEntities != null && recordEntities.size() > 0) {
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date startTime = recordEntities.get(0).getStartTime();
                    Date endTime = recordEntities.get(0).getEndTime();
                    // 获取拆分时间段的间隔时间
                    int minutes = Integer.parseInt(PropertyUtil.getContextProperty("record.minutes").toString());
                    // 获取拆分后的时间片段，开始时间，结束时间
                    int taskNo = 1;
                    long serialId = recordEntities.get(0).getSerialId();
                    Random random = new Random();
                    DownloadTaskEntity taskEntity = new DownloadTaskEntity();
                    taskEntity.setCameraNo(getCameraNo());
                    taskEntity.setTaskNo(taskNo);
                    taskEntity.setTaskType(1);// 审讯视频
                    taskEntity.setRecordId(getRecordId());
                    taskEntity.setSerialId(serialId);
                    taskEntity.setStatus(0);
                    taskEntity.setStartTime(new Date(startTime.getTime() - (5 * 60000)));
                    taskEntity.setEndTime(new Date(endTime.getTime() + (5 * 60000)));
                    taskEntity.setHashValue(random.nextInt(10));
                    map.clear();
                    map.put("recordId", getRecordId());
                    map.put("starttime", taskEntity.getStartTime());
                    map.put("endtime", taskEntity.getEndTime());
                    int taskCount = taskService.queryRecordTaskCount(map);
                    if (taskCount <= 0) {
                        taskService.insert(taskEntity);
                    }
                } else {
                    logger.info("recordId=" + recordId + ",查询笔录下载信息-没有查询到笔录信息");
                }
            } catch (Exception ex) {
                logger.info("recordId=" + recordId + ",插入笔录拆分时间段异常：" + ex.getMessage());
            }

        }

    }

    @RequestMapping(value = "/confirm")
    @ResponseBody
    public MessageEntity confirm(@RequestBody SerialForm form, HttpServletRequest request) throws Exception {
        SerialEntity entity = new SerialEntity();
        entity.setId(form.getId());
        entity.setConfirmResult(form.getConfirmResult());
        entity.setOpPid(ControllerTool.getSessionInfo(request).getCurrentOrg().getPid());
        try {
            serialService.confirm(entity);
            this.operLogService.insertLog(OperLogEntity.EDIT_TYPE, "裁决" + entity,
                    ControllerTool.getSessionInfo(request).getUser().getRealName(), true, OperLogEntity.SYSTEM_BACS);

        } catch (Exception e) {
            logger.error("", e);
            return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("裁决失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("提醒").addContent("裁决成功!");
    }


    // 临时出区返回保存
    @RequestMapping(value = "/tempback")
    @ResponseBody
    public MessageEntity tempback(@RequestBody SerialForm form, HttpServletRequest request) {

        String bundlingResult = "临时出区返回失败!";
        System.err.println("临时出区返回form= " + form);
        int roleId = ControllerTool.getSessionInfo(request).getRole().getId();

        try {
            TempOutEntity entity = new TempOutEntity();
            SerialEntity serial = new SerialEntity();
            entity.setSerialId(form.getId());
            serial.setId(form.getId());
            serial = serialService.getSerialById(serial);
            // 根据serialId更新临时出区表
            tempOutService.update(entity);

            // 更新seria表的状态 临时出区已返回
            serialService.changestatus(form.getId(), 9);

            if (roleId == 21) {
                bundlingResult = "临时出区返回成功!";
            } else {
                this.cuffLogService.insertLog(serial.getCuffId(), CuffLogEntity.BINGDING_TYPE, "临时出区返回 手环带入");
                // 手环
                CuffEntity cuff = new CuffEntity();
                cuff.setId(serial.getCuffId());
                cuff.setStatus(1);
                this.cuffService.update(cuff);
                try {
                    String ret = tiandyTempIn(serial.getUuid(), request, serial.getAreaId());// 临时出区返回
                    // 手环带入
                    if (ret != null && "0".equals(ret)) {
                        bundlingResult = "临时出区返回 手环带入，手环绑定成功！";
                    } else {
                        bundlingResult = "临时出区返回 手环带入失败！错误编码[" + ret + "]";
                    }
                } catch (Exception e) {
                    bundlingResult = "临时出区返回 手环带入手环解绑失败!";
                    logger.error("", e);
                }
            }
            return new MessageEntity().addCode(1).addIsError(false).addTitle("提醒").addContent(bundlingResult);
        } catch (Exception e) {
            logger.error("", e);
            return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent(bundlingResult);
        }

    }

    //临时出区  手环带入
    private String tiandyTempIn(String uuid, HttpServletRequest request, Integer areaId) throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("standBookId", uuid);// 手环登记记录id*
        System.out.println(jsonObject.toString());
        // 发送ftp
        String uri = getTiandyUrlInfo(request, areaId);
        if (uri == null || "".equals(uri.trim())) {
            return "未正确配置手环系统的IP和端口！";
        }

        String url = uri + "/Easy7/rest/inquestStandbookUser/ringTemporaryBringIn";
        Map<String, String> params = new HashMap<String, String>();
        params.put("param", jsonObject.toString());
        String content = operResult(url, params);
        System.err.println("tiandyTempIn result content" + content);
        if (content != null && content != "") {
            return content;
        }
        return null;
    }

    private String getTiandyUrlInfo(HttpServletRequest request, Integer areaId) {
        String result = null;
        // SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        Map<String, String> map = personalConfigService
                .listConfigDetailByAreaAndName(areaId, "天地伟业审讯平台");
        if (map != null && map.size() > 0) {
            result = "http://" + map.get("ip") + ":" + map.get("port");
        }
        return result;
    }

    /**
     * 修改照片
     *
     * @param file
     * @param photoName
     * @param serialId
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/editpicture")
    @ResponseBody
    public MessageEntity editPicture(@RequestParam(value = "file", required = false) MultipartFile file, String photoName, Long serialId, HttpServletRequest request
            , HttpServletResponse response) throws Exception {
        try {
            serialService.uploadPicture(file, photoName, serialId);
            return new MessageEntity().addCode(1).addIsError(false).addTitle("提醒").addContent("照片上传成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return new MessageEntity().addCode(1).addIsError(true).addTitle("提醒").addContent("照片上传失败!").addCallbackData(e.getMessage());
        }
    }

    @RequestMapping(value = "/suspectlist")
    @ResponseBody
    public Map<String, Object> suspectlist(@RequestParam Map<String, Object> params, HttpServletRequest request)
            throws Exception {
        List<SerialEntity> interrogateSerials = new ArrayList<SerialEntity>();
        int count = 0;
        boolean flag = true;

        Map<String, Object> map = ControllerTool.mapFilter(params);
//        if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuth(request))) {
//            // 办案人员-本人
//            map.put("dataAuth", " (ints.in_user_id1=" + ControllerTool.getUserID(request) + " or ints.in_user_id2="
//                    + ControllerTool.getUserID(request) + " or ints.in_register_user_id="
//                    + ControllerTool.getUserID(request) + " or ints.out_user_id1=" + ControllerTool.getUserID(request)
//                    + " or ints.out_user_id2=" + ControllerTool.getUserID(request) + " or ints.out_register_user_id="
//                    + ControllerTool.getUserID(request) + " ) ");
//        } else if (RoleEntity.DATA_AUTH_SELFAREA == (ControllerTool.getRoleDataAuth(request))) {
//            // 办案场所-本办案场所
        map.put("dataAuth", " ints.area_id=" + ControllerTool.getCurrentAreaID(request));
//        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuth(request)) || RoleEntity.DATA_AUTH_UP_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
//            // 管理员 -本办案场所及下级办案场所
//            map.put("dataAuth", "ints.interrogate_area_id " + ControllerTool.getSessionInfo(request).getCSALLAuthorAreaIds());
//        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuth(request)) || RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuth(request)) || RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuth(request))) {
//            // 公安领导-本部门及下级部门
//
//            map.put("dataAuth",
//                    " (ints.in_user_id1 " + ControllerTool.getSessionInfo(request).getCSALLAuthorPoliceIds() + " or ints.in_user_id2 " + ControllerTool.getSessionInfo(request).getCSALLAuthorPoliceIds() + " or ints.in_register_user_id " + ControllerTool.getSessionInfo(request).getCSALLAuthorPoliceIds()
//                            + " or ints.out_user_id1 " + ControllerTool.getSessionInfo(request).getCSALLAuthorPoliceIds() + " or ints.out_user_id2 " + ControllerTool.getSessionInfo(request).getCSALLAuthorPoliceIds()
//                            + " or ints.out_register_user_id " + ControllerTool.getSessionInfo(request).getCSALLAuthorPoliceIds() + " ) ");
//        }

        interrogateSerials = this.serialService.suspectlist(map);
        count = this.serialService.suspectcount(map);


        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", count);
        result.put("rows", interrogateSerials);
        return result;
    }


    @RequestMapping(value = "/otherlist")
    @ResponseBody
    public Map<String, Object> otherlist(@RequestParam Map<String, Object> params, HttpServletRequest request)
            throws Exception {
        List<SerialEntity> interrogateSerials = new ArrayList<SerialEntity>();
        int count = 0;
        boolean flag = true;

        Map<String, Object> map = ControllerTool.mapFilter(params);
        // map.put("usePage", true);

//        if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuth(request))) {
//            // 办案人员-本人
//            map.put("dataAuth", " (ints.in_user_id1=" + ControllerTool.getUserID(request) + " or ints.in_user_id2="
//                    + ControllerTool.getUserID(request) + " or ints.in_register_user_id="
//                    + ControllerTool.getUserID(request) + " or ints.out_user_id1=" + ControllerTool.getUserID(request)
//                    + " or ints.out_user_id2=" + ControllerTool.getUserID(request) + " or ints.out_register_user_id="
//                    + ControllerTool.getUserID(request) + " ) ");
//        } else if (RoleEntity.DATA_AUTH_SELFAREA == (ControllerTool.getRoleDataAuth(request))) {
        // 办案场所-本办案场所
        map.put("dataAuth", " ints.area_id=" + ControllerTool.getCurrentAreaID(request));
//        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuth(request)) || RoleEntity.DATA_AUTH_UP_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
//            // 管理员 -本办案场所及下级办案场所
//            map.put("dataAuth", "ints.interrogate_area_id " + ControllerTool.getSessionInfo(request).getCSALLAuthorAreaIds());
//        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuth(request)) || RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuth(request)) || RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuth(request))) {
//            // 公安领导-本部门及下级部门
//            map.put("dataAuth",
//                    " (ints.in_user_id1 " + ControllerTool.getSessionInfo(request).getCSALLAuthorPoliceIds() + " or ints.in_user_id2 " + ControllerTool.getSessionInfo(request).getCSALLAuthorPoliceIds() + " or ints.in_register_user_id " + ControllerTool.getSessionInfo(request).getCSALLAuthorPoliceIds()
//                            + " or ints.out_user_id1 " + ControllerTool.getSessionInfo(request).getCSALLAuthorPoliceIds() + " or ints.out_user_id2 " + ControllerTool.getSessionInfo(request).getCSALLAuthorPoliceIds()
//                            + " or ints.out_register_user_id " + ControllerTool.getSessionInfo(request).getCSALLAuthorPoliceIds() + " ) ");
//        }

        interrogateSerials = this.serialService.otherlist(map);
        count = this.serialService.othercount(map);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", count);
        result.put("rows", interrogateSerials);
        return result;
    }

    public Face1v1Result oneToOneCompare(String renkouImage, String queryImageBase64) {

        try {
            // 然后调用人像接口进行1:1比对
//            try {
//                queryImageBase64 = ImageUtil.yituGetImageStr(path+form.getPath1());
//                dataImageBase64 = ImageUtil.yituGetImageStr(path+form.getPath2());
//                System.err.println(path+form.getPath1());
//                System.err.println(path+form.getPath2());
//            } catch (Exception e) {
//                logger.info("找不到照片");
//                throw e;
//            }
            logger.info("进入1v1比对信息------");
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("image_base64_1", "--query_base_64--");// 查询图片(JPG)的Base64编码
            jsonObject.put("image_base64_2", "--database_base_64--");// 登记照片(JPG)的Base64编码
            String json_1v1 = jsonObject.toString();
            json_1v1 = json_1v1.replaceAll("--query_base_64--", renkouImage.substring(22, renkouImage.length()));
            json_1v1 = json_1v1.replaceAll("--database_base_64--", queryImageBase64.replaceAll("\\r\\n", ""));
            logger.info("### 人像1V1 faceImgOneToOne json_input= " + json_1v1);
            String resultJson = HttpClientUtil.faceImgOneToOne(json_1v1);
            logger.info("### 人像1V1 faceImgOneToOne reponse= " + resultJson);
            JSONObject json = JSONObject.fromObject(resultJson);
            String message = json.getString("message");
            Face1v1Result face1v1Result = new Face1v1Result();
            if ("OK".equals(message)) {
                String similarity = json.getString("similarity");// 相似度
                face1v1Result.setSimilarity(similarity);
                logger.info("### 人像1V1 face1v1Result= " + face1v1Result);
            } else {
                face1v1Result.setSimilarity("0");
            }
            return face1v1Result;
        } catch (Exception e) {
            Face1v1Result face1v1Result = new Face1v1Result();
            face1v1Result.setSimilarity("0");
            logger.info("### 人像1V1 face1v1Result= " + face1v1Result);
            e.printStackTrace();
            return face1v1Result;

        }

    }

    /**
     * 嫌疑人出入区上传照片
     *
     * @param request
     * @throws Exception
     */
    @RequestMapping(value = "/personIngetpicture")
    @ResponseBody
    public MessageEntity personIngetpicture(@RequestParam(value = "file", required = false) MultipartFile file, String photoName, Long serialId, HttpServletRequest request
            , HttpServletResponse response) throws Exception {
        Map<String, Object> reaultMap = new HashMap<String, Object>();
        long startTime = System.currentTimeMillis();
        logger.info("嫌疑人出入区照片开始======" + (System.currentTimeMillis() - startTime));
        try {
            serialService.uploadPicture(file, photoName, serialId);
            SerialEntity serial = new SerialEntity();
            serial.setId(serialId);
            serial = this.serialService.getSerialById(serial);
            Map<String, Object> map = new HashMap<>();
            map.put("serialId", serialId);
            List<SerialVideoMappingEntity> slist = serialVideoMappingService.list(map);
            if (slist.size() <= 0) {
                if (serial.getId() > 0) {
                    ResultEntity result = createSerialVideoMappingInfo(serial);
                    if (result.isStatus() == false) {
                        logger.info("生成音视频绑定数据错误-----" + result.getMessage());
                    }
                }
            }
            logger.info("嫌疑人出入区照片入库结束======" + (System.currentTimeMillis() - startTime));
            // 首先查询在逃前科库，进行1:N比对,1:N结束后页面显示搜索结果
            List<Face1vNResult> templist = new ArrayList<Face1vNResult>();
            try {
                Face1vNResult renKouKuResult = new Face1vNResult();
                Face1vNResult qianKeKuResult = new Face1vNResult();
                logger.info("### 人口库与前科库比对Start= " + serial.getCertificateTypeId());
                if (serial != null && serial.getCertificateTypeId() == 111) {
                    String token = PropertyUtil.getContextProperty("population.token").toString();
                    logger.info("嫌疑人出入区照片人口库开始======" + (System.currentTimeMillis() - startTime));
                    renKouKuResult = renKouKu(serial.getCertificateNo(), token, request);
                    logger.info("嫌疑人出入区照片人口库结束======" + (System.currentTimeMillis() - startTime));
                    logger.info("### 人口库结果= " + renKouKuResult);
                    logger.info("嫌疑人出入区照片前科库开始======" + (System.currentTimeMillis() - startTime));
                    qianKeKuResult = qianKeKu(serial);
                    logger.info("### 前科库结果= " + qianKeKuResult);
                    logger.info("嫌疑人出入区照片前科库开始======" + (System.currentTimeMillis() - startTime));
                }
                try {
                    //在逃库比对
//                    templist = faceImgOneToN(serial.getSerialNo(), request,serial);
                } catch (Exception e) {
                    logger.info("-------", e);
                    //人口库
                    if (renKouKuResult != null) {
                        logger.info("进入人口库------1-----");
                        reaultMap.put("personWarehouse", renKouKuResult);
                        //获取人口库图片 和入区照片比对
                        String renkouImage = renKouKuResult.getFace_image_url();
                        logger.info("----人口照片renkouImage---" + renkouImage);
                        String queryImageBase64 = fileConfigService.getFileBase64(FileUploadForm.of("ba", FileUploadForm.FILETYPRRQ, serial.getUuid(), serial.getAreaId(), serial.getInPhotoId()));
                        logger.info("----人口照片11queryImageBase64---" + queryImageBase64.replaceAll("\r|\n", "").replaceAll("\n", "").replaceAll("\r", ""));
                        logger.info("----人口照片22queryImageBase64---" + queryImageBase64.replaceAll("\\r\\n", "").replaceAll("\\n", "").replaceAll("\\r", ""));
                        Face1v1Result oneToOneCompare = oneToOneCompare(renkouImage, queryImageBase64);
                        logger.info("人口入区对比成功--1---" + oneToOneCompare);
                        reaultMap.put("onevOneResult", oneToOneCompare);
                    }
                    //前科库
                    if (qianKeKuResult != null) {
                        reaultMap.put("oldWarehouse", qianKeKuResult);
                    }
                    return new MessageEntity().addCode(1).addIsError(true).addTitle("提醒").addContent("照片比对失败！").addCallbackData(reaultMap);
                }
                //人口库
                if (renKouKuResult != null) {
                    logger.info("进入人口库------2-----");
                    reaultMap.put("personWarehouse", renKouKuResult);
                    //获取人口库图片 和入区照片比对
                    logger.info("进入人口库------3-----");
                    String renkouImage = renKouKuResult.getFace_image_url();
                    logger.info("----人口照片1renkouImage1---" + renkouImage);
                    String queryImageBase64 = fileConfigService.getFileBase64(FileUploadForm.of("ba", FileUploadForm.FILETYPRRQ, serial.getUuid(), serial.getAreaId(), serial.getInPhotoId()));
                    logger.info("----人口照片11queryImageBase64---" + queryImageBase64.replaceAll("\r|\n", "").replaceAll("\n", "").replaceAll("\r", ""));
                    logger.info("----人口照片22queryImageBase64---" + queryImageBase64.replaceAll("\\r\\n", "").replaceAll("\\n", "").replaceAll("\\r", ""));
                    Face1v1Result oneToOneCompare = oneToOneCompare(renkouImage, queryImageBase64);
                    logger.info("人口入区对比成功--2---" + oneToOneCompare);
                    reaultMap.put("onevOneResult", oneToOneCompare);
                }
                logger.info("嫌疑人出入区照片人口库对比======" + (System.currentTimeMillis() - startTime));
                //前科库
                if (qianKeKuResult != null) {
                    reaultMap.put("oldWarehouse", qianKeKuResult);
                }
                //在逃库
                if (templist != null && templist.size() > 0) {
                    reaultMap.put("largeWarehouse", qianKeKuResult);
                }
                logger.info("嫌疑人出入区照片对比结束======" + (System.currentTimeMillis() - startTime));
                if (reaultMap.size() > 0) {// 成功返回比对结果
                    return new MessageEntity().addCode(1).addIsError(false).addTitle("提醒").addContent("照片比对成功！").addCallbackData(reaultMap);
                } else {
                    return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("照片比对失败!");
                }
            } catch (Exception e) {
                logger.info(e + "");
                return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("照片比对失败!");
            }
        } catch (Exception e) {
            return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("照片比对失败!");
        }
    }


    @RequestMapping(value = "/querystatus")
    @ResponseBody
    public Map<String, Object> querystatus(@RequestBody Map<String, Object> param, HttpServletRequest request) {

        Map<String, Object> map = ControllerTool.mapFilter(param);

        int counts = serialService.queryStatusById(map);

        int countw = waitingManageService.queryStatusBySid(map);

        Map<String, Object> result = new HashMap<>();

        result.put("sta", counts);

        result.put("wta", countw);

        return result;
    }


    /**
     * 获取word模板
     *
     * @param request
     * @throws Exception
     */
    @RequestMapping(value = "/downloadWordBase64")
    @ResponseBody
    public MessageEntity downloadWordBase64(HttpServletRequest request, HttpServletResponse response) throws Exception {

        response.reset();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/x-download");
        response.setHeader("Pragma", "public");
        response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");

        Long serialId = Long.parseLong(request.getParameter("exportSerialId"));
        LawDocProcessEntity result = outReportService.getDocData(serialId, request);

        String downFileName = result.getDownFileName();
        if (request.getHeader("User-Agent").toLowerCase().indexOf("firefox") > 0) {
            downFileName = new String(downFileName.getBytes("UTF-8"), "ISO8859-1");
        } else {
            downFileName = URLEncoder.encode(downFileName, "UTF-8");
        }
        response.addHeader("Content-Disposition", "attachment;filename=" + downFileName);
        URL url = Thread.currentThread().getContextClassLoader().getResource("");
        System.out.println("exportReport getData=" + result.getData());
        String officeDir = BaseConfig.getInstance().getOfficeFileDir();
        String officeFile = officeDir + result.getDownFileName();
        String templatePath = url.getPath() + "template";
//        PrintWriter out =  new PrintWriter(officeFile);
        String xmlFileName = result.getXmlFileName();
        FreemarkerUtil.process(templatePath, xmlFileName, officeFile, result.getData());
//        File f=new File(officeFile);
//        response.setHeader("content-length",String.valueOf(f.length()));
//        FreemarkerUtil.process(templatePath, xmlFileName, result.getData(), out);
        return new MessageEntity().addCallbackData(Base64Util.encodeBase64File(officeFile));

    }

    /**
     * 入区修改保存
     *
     * @param request
     * @throws Exception
     */
    @RequestMapping(value = "/suspectupdate")
    @ResponseBody

    public MessageEntity suspectupdate(@RequestBody SerialForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
//        this.operLogService.insertTdLog(form.getId(),"入区传天地数据错误","天地返回数据错误：");

        PersonEntity personEntity = new PersonEntity();
        personEntity.setId(Long.parseLong(form.getPersonId() + ""));
        personEntity.setName(form.getName());
        personEntity.setSex(form.getSex());
        personEntity.setCertificateTypeId(form.getCertificateTypeId());
        personEntity.setCertificateNo(form.getCertificateNo());

        if (form.getCertificateTypeId() == 111) {
            Date birth = new Date();
            if (null != form.getBirth() && !"".equals(form.getBirth())) {
                personEntity.setBirth(form.getBirth());
            } else {
                try {
                    String icno = form.getCertificateNo();
                    String year = icno.substring(6, 10);
                    String month = icno.substring(10, 12);
                    String day = icno.substring(12, 14);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    birth = sdf.parse(year + "-" + month + "-" + day);
                    personEntity.setBirth(birth);
                } catch (Exception e) {
                    logger.info("修改出入区信息，生日转换异常");
                    return new MessageEntity().addCode(1).addIsError(true).addTitle("提醒").addContent("修改出入区信息，生日转换异常!");
                }
            }
        }
        OrderPersonEntity orderPersonEntity = new OrderPersonEntity();
        orderPersonEntity.setPersonType(form.getPersonInfo());
        orderPersonEntity.setPersonId(form.getPersonId());
        orderPersonEntity.setOrderRequestId(form.getOrderRequestId());
        SerialEntity serialEntity = new SerialEntity();
        serialEntity.setId(form.getId());
        serialEntity.setPersonInfo(form.getPersonInfo());
        serialEntity.setEntranceProcedure(form.getEntranceProcedure());
        serialEntity.setSfzdgl(form.getSfzdgl());
        try {
            if (form.getWrittenTime().trim() != "") {
                logger.info(form.getWrittenTime());
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = null;
                date = formatter.parse(form.getWrittenTime());
                serialEntity.setWrittenTime(date);
            }
        } catch (Exception ex) {
            logger.info("修改出入区信息，手续开具时间转换异常");
            return new MessageEntity().addCode(1).addIsError(true).addTitle("提醒").addContent("修改出入区信息，手续开具时间转换异常!");
        }
        serialEntity.setSendUserId(form.getSendUserId());
        serialEntity.setOrderId(form.getOrderRequestId());
        serialEntity.setSfxxcj(form.getSfxxcj());
        serialEntity.setSfsyjd(form.getSfsyjd());
        serialEntity.setOutReason(form.getOutReason());
        serialEntity.setConfirmResult(form.getConfirmResult());

        try {
            personService.update(personEntity);


            if (form.getFlag().equals("1")) {
                serialEntity.setHczt(0);
            }
            serialEntity.setAjbh(form.getAjbh());
            serialEntity.setRybh(form.getRybh());

            orderPersonService.updateByOrderIdAndPersonId(orderPersonEntity);
            serialService.updateSerialById2(serialEntity);
            SerialEntity interrogateSerialEntitys = serialService.getSerialById(serialEntity);
            String uuid = interrogateSerialEntitys.getUuid();
            logger.info("sswplq++++++uuid+++++++++++++++++++++++++" + uuid);
            String sjhcurl = PropertyUtil.getContextProperty("sjhc.url") + "";
            String url = sjhcurl + "/updateserial/" + uuid + "&cq";
            HttpClientUtil.get(url, "");

            return new MessageEntity().addCode(1).addIsError(false).addTitle("提醒").addContent("修改成功!");
        } catch (Exception e) {
            logger.info("修改出入区信息");

            // completeTransactionAfterThrowing(txInfo, ex);
            return new MessageEntity().addCode(1).addIsError(true).addTitle("提醒").addContent("修改出入区信息，修改入区信息!" + e);
        }
    }

    /**
     * 下载word模板
     *
     * @param request
     * @throws Exception
     */
    @RequestMapping(value = "/downloadWord")
    @ResponseBody
    public MessageEntity downloadWord(HttpServletRequest request, HttpServletResponse response) throws Exception {

        response.reset();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/x-download");
        response.setHeader("Pragma", "public");
        response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");

        Long serialId = Long.parseLong(request.getParameter("exportSerialId"));
        LawDocProcessEntity result = outReportService.getDocData(serialId, request);

        String downFileName = result.getDownFileName();
        if (request.getHeader("User-Agent").toLowerCase().indexOf("firefox") > 0) {
            downFileName = new String(downFileName.getBytes("UTF-8"), "ISO8859-1");
        } else {
            downFileName = URLEncoder.encode(downFileName, "UTF-8");
        }
        response.addHeader("Content-Disposition", "attachment;filename=" + downFileName);
        String xmlFileName = result.getXmlFileName();
        PrintWriter out = response.getWriter();
        URL url = Thread.currentThread().getContextClassLoader().getResource("");
        String templatePath = url.getPath() + "template";
        System.out.println("exportReport getData=" + result.getData());
        FreemarkerUtil.process(templatePath, xmlFileName, result.getData(), out);
        return null;
    }

    private String tiandyBindCuffYb(PersonEntity person, SerialEntity entity, String realName, Integer userId, String pcIp, Integer areaId)
            throws Exception {
        // 创建FTP的Ring目录，名称是UUID
        // String ftpPath = "/Ring/" + Utils.getDatePath() + "/" +
        // entity.getUuid() + "/";
        // FTPUtil.createDirectory(ftpPath);

        String dirUrl = "/Ring/" + Utils.getDateFromSerialNO(entity.getSerialNo()) + "/";
        // FTPUtil.createDirectory(dirUrl);
        AsynUtil.getInstance().createFTPDirectory(dirUrl);
        dirUrl += entity.getUuid() + "/";
        // FTPUtil.createDirectory(dirUrl);
        AsynUtil.getInstance().createFTPDirectory(dirUrl);

        // 通过接口发送FTP路径以及UUID
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("standBookId", entity.getUuid());
        JSONObject personJson = new JSONObject();
        JSONObject ftpInfoJson = new JSONObject();
        personJson.put("name", person.getName());// 姓名*
        personJson.put("gender", person.getSex() == 2 ? "1" : "0");// 0为男、1为女*
        personJson.put("cardType", person.getCertificateTypeId() == 111 ? 1 : 7);// 证件类型
        // 0身份证
        personJson.put("cardNum", person.getCertificateNo());// 证件号
        personJson.put("personCode", entity.getRybh());// 人员编号
        if (person.getBirth() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            personJson.put("birthday", sdf.format(person.getBirth()));// 出生年月
        } else {
            personJson.put("birthday", "");// 出生年月
        }
        // =============================================
        jsonObject.put("person", personJson);
        // =============================================
        // 数据同步后修改
        // jsonObject.put("police1Id", "mj1");
        // jsonObject.put("police2Id", "");
        jsonObject.put("police1Id", entity.getSendUserId());
        jsonObject.put("police2Id", entity.getSendUserId());
        jsonObject.put("caseReason", entity.getSerialNo());
        CuffEntity temCuff = cuffService.getCuffById(entity.getCuffId());
        // 数据同步后修改
        jsonObject.put("ringNum", temCuff.getCuffNo());
        // jsonObject.put("ringNum", "25150926");
        // 数据同步后修改
        // jsonObject.put("userId", "admin");
        jsonObject.put("userId", userId);
        jsonObject.put("userrealname", realName);
        //电脑ip
        jsonObject.put("pcIp", pcIp);
        // =============================================
        String ftpIp = PropertyUtil.getContextProperty("ftp.ip").toString();
        String ftpUser = PropertyUtil.getContextProperty("ftp.user").toString();
        String ftpPwd = PropertyUtil.getContextProperty("ftp.pwd").toString();
        String ftpPort = PropertyUtil.getContextProperty("ftp.port").toString();
        ftpInfoJson.put("ftpIp", ftpIp);// ftp服务器ip*
        ftpInfoJson.put("ftpPort", ftpPort);// ftp服务器端口*
        ftpInfoJson.put("ftpUserName", ftpUser);// ftp服务器账号*
        ftpInfoJson.put("ftpPassword", ftpPwd);// ftp服务器密码*
        ftpInfoJson.put("ftpPathRing", dirUrl);// ftp服务器轨迹视频mp4文件上传位置*
        // =============================================
        jsonObject.put("ftpInfo", ftpInfoJson);
        // =============================================
        String uri = getTiandyUrlInfo(areaId);
        if (uri == null || "".equals(uri.trim())) {
            return "未正确配置手环系统的IP和端口！";
        }
        // 发送ftp
        String url = uri + "/Easy7/rest/inquestStandbookUser/ringRegister";
        logger.info("绑定手环接口URL=" + url);
        Map<String, String> params = new HashMap<String, String>();
        String json = jsonObject.toString();
        logger.info("绑定手环接口json=" + json);
        params.put("param", json);
        // 处理接口反馈结果，解析json
        JSONObject result = HttpClientUtil.get(url, params);
        String ret = result.get("ret").toString();
        logger.info("绑定手环接口结果=" + ret);
        if (!"0".equals(ret)) {
            //绑定失败默认没有传给天地数据
//                        插入新表
            this.operLogService.insertTdLog(entity.getId(), "入区传天地数据错误", "天地返回数据错误：" + ret, json);
        }
        return ret;
    }

    private String tiandyBindCuff(PersonEntity person, SerialEntity entity, HttpServletRequest request, Integer areaId)
            throws Exception {
        // 创建FTP的Ring目录，名称是UUID
        // String ftpPath = "/Ring/" + Utils.getDatePath() + "/" +
        // entity.getUuid() + "/";
        // FTPUtil.createDirectory(ftpPath);

        String dirUrl = "/Ring/" + Utils.getDateFromSerialNO(entity.getSerialNo()) + "/";
        // FTPUtil.createDirectory(dirUrl);
        AsynUtil.getInstance().createFTPDirectory(dirUrl);
        dirUrl += entity.getUuid() + "/";
        // FTPUtil.createDirectory(dirUrl);
        AsynUtil.getInstance().createFTPDirectory(dirUrl);

        // 通过接口发送FTP路径以及UUID
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("standBookId", entity.getUuid());
        JSONObject personJson = new JSONObject();
        JSONObject ftpInfoJson = new JSONObject();
        personJson.put("name", person.getName());// 姓名*
        personJson.put("gender", person.getSex() == 2 ? "1" : "0");// 0为男、1为女*
        personJson.put("cardType", person.getCertificateTypeId() == 111 ? 1 : 7);// 证件类型
        // 0身份证
        personJson.put("cardNum", person.getCertificateNo());// 证件号
        personJson.put("personCode", entity.getRybh());// 人员编号
        if (person.getBirth() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            personJson.put("birthday", sdf.format(person.getBirth()));// 出生年月
        } else {
            personJson.put("birthday", "");// 出生年月
        }
        // =============================================
        jsonObject.put("person", personJson);
        // =============================================
        // 数据同步后修改
        // jsonObject.put("police1Id", "mj1");
        // jsonObject.put("police2Id", "");
        jsonObject.put("police1Id", entity.getSendUserId());
        jsonObject.put("police2Id", entity.getSendUserId());
        jsonObject.put("caseReason", entity.getSerialNo());
        CuffEntity temCuff = cuffService.getCuffById(entity.getCuffId());
        // 数据同步后修改
        jsonObject.put("ringNum", temCuff.getCuffNo());
        // jsonObject.put("ringNum", "25150926");
        // 数据同步后修改
        // jsonObject.put("userId", "admin");
        jsonObject.put("userId", ControllerTool.getUser(request).getId());
        jsonObject.put("userrealname", ControllerTool.getUser(request).getRealName());
        //电脑ip
        jsonObject.put("pcIp", ControllerTool.getClienIp(request));
        // =============================================
        String ftpIp = PropertyUtil.getContextProperty("ftp.ip").toString();
        String ftpUser = PropertyUtil.getContextProperty("ftp.user").toString();
        String ftpPwd = PropertyUtil.getContextProperty("ftp.pwd").toString();
        String ftpPort = PropertyUtil.getContextProperty("ftp.port").toString();
        ftpInfoJson.put("ftpIp", ftpIp);// ftp服务器ip*
        ftpInfoJson.put("ftpPort", ftpPort);// ftp服务器端口*
        ftpInfoJson.put("ftpUserName", ftpUser);// ftp服务器账号*
        ftpInfoJson.put("ftpPassword", ftpPwd);// ftp服务器密码*
        ftpInfoJson.put("ftpPathRing", dirUrl);// ftp服务器轨迹视频mp4文件上传位置*
        // =============================================
        jsonObject.put("ftpInfo", ftpInfoJson);
        // =============================================
        String uri = getTiandyUrlInfo(areaId);
        if (uri == null || "".equals(uri.trim())) {
            return "未正确配置手环系统的IP和端口！";
        }
        // 发送ftp
        String url = uri + "/Easy7/rest/inquestStandbookUser/ringRegister";
        logger.info("绑定手环接口URL=" + url);
        Map<String, String> params = new HashMap<String, String>();
        String json = jsonObject.toString();
        logger.info("绑定手环接口json=" + json);
        params.put("param", json);
        // 处理接口反馈结果，解析json
        JSONObject result = HttpClientUtil.get(url, params);
        String ret = result.get("ret").toString();
        logger.info("绑定手环接口结果=" + ret);
        if (!"0".equals(ret)) {
            //绑定失败默认没有传给天地数据
//                        插入新表
            this.operLogService.insertTdLog(entity.getId(), "入区传天地数据错误", "天地返回数据错误：" + ret, json);
        }
        return ret;
    }

    private String getTiandyUrlInfo(Integer areaId) {
        String result = null;
        Map<String, String> map = personalConfigDetailService.listConfigDetailByAreaAndName(areaId, "天地伟业审讯平台");
        if (map != null && map.size() > 0) {
            result = "http://" + map.get("ip") + ":" + map.get("port");
        }
        return result;
    }

    // 临时出区 手环带出
    private String tiandyTempOut(String uuid, HttpServletRequest request, Integer areaId) throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("standBookId", uuid);// 手环登记记录id*
        jsonObject.put("police1Id", "mj1");// 带出民警id* 可以与带入民警不同
        jsonObject.put("reason", "带出原因1");// 带出原因*
        System.out.println(jsonObject.toString());
        // 发送ftp
        String uri = getTiandyUrlInfo(areaId);
        if (uri == null || "".equals(uri.trim())) {
            return "未正确配置手环系统的IP和端口！";
        }
        // String url =
        // "http://192.168.201.200:7000/Easy7/rest/inquestStandbookUser/ringTemporaryBringOut";
        String url = uri + "/Easy7/rest/inquestStandbookUser/ringTemporaryBringOut";
        Map<String, String> params = new HashMap<String, String>();
        params.put("param", jsonObject.toString());
        String content = operResult(url, params);
        System.err.println("tiandyTempOut result content" + content);
        if (content != null && content != "") {
            return content;
        }
        return null;
    }

    private String operResult(String url, Map<String, String> params) throws Exception {
        JSONObject result = HttpClientUtil.get(url, params);
        System.out.println("  json response -->> " + result);
        JSONObject cls_vo_result = JSONObject.fromObject(result);
        System.out.println("  cls_vo_result ret -->> " + cls_vo_result.get("ret").toString());
        System.out.println("  cls_vo_result Content -->> " + cls_vo_result.get("content").toString());
        String ret = cls_vo_result.get("ret").toString();
        if (ret != null && ret != "") {
            return ret;
        }
        return null;
    }

    // 正常出区 手环带出
    private String tiandyFinalOut(String uuid, HttpServletRequest request, Integer areaId) throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("standBookId", uuid);// 手环登记记录id*
        System.out.println(jsonObject.toString());
        // 发送ftp
        String uri = getTiandyUrlInfo(areaId);
        if (uri == null || "".equals(uri.trim())) {
            return "未正确配置手环系统的IP和端口！";
        }
        // String url =
        // "http://192.168.201.200:7000/Easy7/rest/inquestStandbookUser/ringLeave";
        String url = uri + "/Easy7/rest/inquestStandbookUser/ringLeave";
        Map<String, String> params = new HashMap<String, String>();
        params.put("param", jsonObject.toString());
        String ret = operResult(url, params);
        System.err.println("tiandyFinalOut result content" + ret);
        if (ret != null && ret != "") {
            return ret;
        }
        return null;
    }

    private Face1vNResult qianKeKu(SerialEntity serial) throws Exception {
        String url = PropertyUtil.getContextProperty("qianke.url") + "?sfzh=" + serial.getCertificateNo();
        logger.info("前科库 url= " + url);
        Face1vNResult person = new Face1vNResult();
        try {
            String resultJson = HttpClientUtil.query(url);
            logger.info("### 前科库 reponse= " + resultJson);
            if (resultJson != null && !"".equals(resultJson)) {
                person.setGender((serial.getSex() + 1) + "");
                person.setName(serial.getName());
                person.setPerson_id(serial.getCertificateNo());
                person.setFace_image_url(resultJson + "&id=0");
                person.setBorn_year(serial.getCertificateNo().substring(6, 10));
                person.setSimilarity("100");
                logger.info("前科库 person= " + person);
            } else {
                throw new Exception("前科库反馈非正确结果" + resultJson);
            }
            return person;
        } catch (Exception e) {
            logger.info("前科库反馈非正确结果" + person);
            return person;
        }
    }

    private Face1vNResult renKouKu(String certificateNo, String token, HttpServletRequest request) throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("sfzh", certificateNo);
        jsonObject.put("token", token);

        String json_1vn = jsonObject.toString();
        logger.info("### 人口库renKouKu json_input= " + json_1vn);
        String url = PropertyUtil.getContextProperty("population.url") + "?sfzh=" + certificateNo + "&token=" + token;
        // String
        // url="http://10.11.229.141:8080/wsquery-web/wsquery?sfzh="+certificateNo+"&token="+token;
        logger.info("人口库renKouKu url= " + url);
        Face1vNResult person = new Face1vNResult();
        try {
            String resultJson = HttpClientUtil.renKouKu(url, "");
            /*
             * String uri = getYituUrlInfo(request); if (uri == null ||
             * "".equals(uri)) { logger.info("未正确配置人像识别系统的IP和端口!"); throw new
             * Exception("未正确配置人像识别系统的IP和端口!"); } String resultJson =
             * HttpClientUtil.faceImgOneToN(uri +
             * ":9200/face/v1/framework/face/retrieval", json_1vn);
             */
            logger.info("### 人口库renKouKu reponse= " + resultJson);
            if (resultJson != null && !"".equals(resultJson)) {
                JSONObject json = JSONObject.fromObject(resultJson);
                String name = json.getString("name");
                String sex = json.getString("sex");
                String birthDate = json.getString("birthDate");
                String address = json.getString("address");
                String imgData = "data:image/png;base64," + json.getString("imgData");
                /*
                 * if("1".equals(sex)){ person.setGender("0"); }else{
                 * person.setGender("1"); }
                 */
                person.setGender(sex);
                person.setName(name);
                person.setPerson_id(certificateNo);
                person.setFace_image_url(imgData);
                person.setBorn_year(certificateNo.substring(6, 10));
                person.setAddress(address);

                logger.info("人口库renKouKu person= " + person);
            } else {
                throw new Exception("人口库反馈非正确结果" + resultJson);
            }
            return person;
        } catch (Exception e) {
            logger.info("人口库反馈非正确结果" + person);
            return person;
        }
    }

    private List<Face1vNResult> faceImgOneToN(String serialNo, HttpServletRequest request, SerialEntity serial) throws Exception {
        // 1:N人像比对，可以暂时不写
        // String queryImageBase64 = ImageUtil
        // .getImageStr(PropertyUtil.getContextProperty("faceImageFileSavePath")
        // + "aaaa.jpg");
//        String queryImageBase64 = ImageUtil.yituGetImageStr(PropertyUtil.getContextProperty("faceImageFileSavePath")
//                + Utils.getDateFromSerialNO(serialNo) + File.separator + "rq-" + serialNo + "-yt.jpg");
        String queryImageBase64 = fileConfigService.getFileBase64(FileUploadForm.of("ba", FileUploadForm.FILETYPRRQ, serial.getUuid(), serial.getAreaId(), serial.getInPhotoId()));

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("picture_image_content_base64", "--query_base_64--");//
        String json_1vn = jsonObject.toString();
        json_1vn = json_1vn.replaceAll("--query_base_64--", queryImageBase64);
        String resultJson = HttpClientUtil.faceImgOneToNNew(json_1vn);
        logger.info("### 人像faceImgOneToN reponse= " + resultJson);
        List<Face1vNResult> persons = new ArrayList<>();
        JSONObject json = JSONObject.fromObject(resultJson);
        String message = json.getString("message");
        if ("OK".equals(message)) {
            JSONArray ja1 = json.getJSONArray("results");
            for (int i = 0; i < ja1.size(); i++) {
                JSONObject jo2 = ja1.getJSONObject(i);
                Face1vNResult face1vNResult = new Face1vNResult();
                face1vNResult.setSimilarity(jo2.get("similarity") + "");
                face1vNResult.setBorn_year(jo2.get("born_year") + "");
                face1vNResult.setGender(jo2.get("gender") + "");
                face1vNResult.setFace_image_url(jo2.get("face_image_uri") + "");
                face1vNResult.setPerson_id(jo2.get("person_id") + "");
                face1vNResult.setName(jo2.get("name") + "");
                persons.add(face1vNResult);
            }
        } else {
            throw new Exception("人像比对反馈非正确结果" + message);
        }
        return persons;
    }

    @RequestMapping(value = "/listHistory")
    @ResponseBody
    public Map<String, Object> listHistory(@RequestParam Map<String, Object> params, HttpServletRequest request) throws Exception {
        List<SerialEntity> interrogateSerials = new ArrayList<SerialEntity>();
        int count = 0;
        boolean flag = true;

        Map<String, Object> map = ControllerTool.mapFilter(params);
        map.put("outTimeHistory", "outTimeHistory");
        interrogateSerials = this.serialService.list(map);
        count = this.serialService.count(map);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", count);
        result.put("rows", interrogateSerials);
        return result;
    }


    //随身物品
    private void sswpInfo(Long serialId) throws Exception {
        // 人员
        SerialEntity serial = new SerialEntity();
        Map<String, Object> params = new HashMap<>();
        params.put("dataAuth", "ints.id=" + serialId);
        List<SerialEntity> serialList = serialService.list(params);
        if (serialList.size() > 0) {
            serial = serialList.get(0);
        }
        // 单位
        OrganizationEntity orgEntity = organizationService
                .getOrganizationByUserId2(Integer.parseInt(serial.getSendUserId().toString()));
        if (null == orgEntity) {
            orgEntity = new OrganizationEntity();
        }

        // 随身物品
        BelongEntity mapEntity = new BelongEntity();
        mapEntity.setSerialId(serialId);
        List<BelongEntity> list = belongService.getDocInfo(mapEntity);
        for (BelongEntity entity : list) {
            httpSswpInfo(serial, entity, orgEntity);
        }

    }

    //调接口
    private void httpSswpInfo(SerialEntity serial, BelongEntity belong, OrganizationEntity orgEntity) throws Exception {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String sswpInfo = "%7B"
                + "isdel:'0'"
                + ",rybh:'" + formatStr(serial.getRybh()) + "'"
                + ",zbdw_bh:'" + formatStr(orgEntity.getOrgCode()) + "'"
                + ",zbdw_mc:'" + formatStr(orgEntity.getName()) + "'"
                + ",zbr_xm:'" + formatStr(serial.getInUserRealName1()) + "'"
                + ",lrr_sfzh:'" + formatStr(serial.getInUserNo1()) + "'"
                + ",lrsj:'" + formatter.format(formatDate(belong.getCreatedTime())) + "'"
                + ",xgsj:'" + formatter.format(formatDate(belong.getUpdatedTime())) + "'"
                + ",baqid:'475B999EABC2413B98FF19C6E1A45F0C'"
                + ",baqmc:'朝阳分局执法办案中心'"
                + ",wpbh:'" + formatStr(belong.getCabinetNo()) + "'"
                + ",lx:'随身物品'"
                + ",lb:'随身物品'"
                + ",mc:'" + formatStr(belong.getBename()) + "'"
                + ",cyr:'" + formatStr(serial.getName()) + "'"
                + ",dw:'" + formatStr(belong.getBename()) + "'"
                + ",sl:'" + formatStr(belong.getDetailCount()) + "'"
                + ",jz:'0'"
                + ",wpzt:'01'"
                + ",tz:'" + formatStr(belong.getDescription()) + "'"
                + ",cfwz:'" + formatStr(belong.getCabinetGroup()) + "'"
                + ",cllx:'1'"//0 未领取，1本人领取，2委托他人代为领取，3本人收到扣押物品清单
                + ",lqr:'" + formatStr(belong.getGetPerson()) + "'"
                + "%7D";

        String sswpUrl = (String) PropertyUtil.getContextProperty("sswp.url");
        String url = sswpUrl + sswpInfo;
        logger.info("随身物品接口sswpUrl====" + url);
        url = url.replace(" ", "%20");
        logger.info("替换url===" + url);
        Map<String, String> param = new HashMap<>();
        JSONObject template = HttpClientUtil.get(url, param);
        logger.info(template.toString());
    }


    private String formatStr(Object obj) {
        if (null == obj || "null".equals(obj) || "".equals(obj)) {
            return "9";
        } else {
            return obj.toString();
        }
    }

    private String formatSubStr(Object obj, int len) {
        if (null == obj || "null".equals(obj) || "".equals(obj)) {
            return "9";
        } else {
            if (obj.toString().length() > len) {
                return obj.toString().substring(0, len);
            }
            return obj.toString();
        }
    }

    private Date formatDate(Date date) {
        if (null == date) {
            return new Date();
        } else {
            return date;
        }

    }

    /**
     * 对比人口库修改姓名
     *
     * @param zjhm
     * @param personId
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/renKouByZjhm")
    @ResponseBody
    public MessageEntity renKouByZjhm(String zjhm, Long personId, HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            logger.info("自动对比人口库。。。。zjhm==" + zjhm + "......personId...." + personId);
            Face1vNResult renKouKuResult = new Face1vNResult();
            String token = PropertyUtil.getContextProperty("population.token").toString();
            renKouKuResult = renKouKu(zjhm, token, request);
            logger.info("人口库结果====" + renKouKuResult.toString());
            //修改姓名
            PersonEntity personEntity = new PersonEntity();
            personEntity.setName(renKouKuResult.getName());
            personEntity.setId(personId);
            personService.update(personEntity);
            return new MessageEntity().addCode(1).addIsError(false).addTitle("提示").addContent("查询成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return new MessageEntity().addCode(1).addIsError(true).addTitle("提示").addContent("查询失败!").addCallbackData(e.getMessage());
        }
    }

    /**
     * 批量修改
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     * @param(111,222,333) confirmResult(裁决结果)
     */
    @RequestMapping(value = "/batchConfirmByIds")
    @ResponseBody
    public MessageEntity batchConfirmByIds(@RequestParam Map<String, Object> params, HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            logger.info("嫌疑人批量裁决。。。。params==" + params);
            Map<String, Object> map = ControllerTool.mapFilter(params);
            serialService.batchConfirmByIds(map);
            return new MessageEntity().addCode(1).addIsError(false).addTitle("提示").addContent("批量操作成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return new MessageEntity().addCode(1).addIsError(true).addTitle("提示").addContent("批量操作失败!").addCallbackData(e.getMessage());
        }
    }

    /**
     * id> serial_id
     *
     * @param params
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/updateSftjclByid")
    @ResponseBody
    public MessageEntity updateSftjclByid(@RequestParam Map<String, Object> params, HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            logger.info("提交材料。。。。params==" + params);
            Map<String, Object> map = ControllerTool.mapFilter(params);
            serialService.updateSftjclByid(params);
            return new MessageEntity().addCode(1).addIsError(false).addTitle("提示").addContent("操作成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return new MessageEntity().addCode(1).addIsError(true).addTitle("提示").addContent("操作失败!").addCallbackData(e.getMessage());
        }
    }


    @RequestMapping(value = "/queryJzAjxx")
    @ResponseBody
    public Map<String, Object> queryJzAjxx(@RequestParam Map<String, Object> pageMap, HttpServletRequest request,
                                           HttpServletResponse response) throws Exception {
        Map<String, Object> map = ControllerTool.mapFilter(pageMap);
        // String  orgCode= ControllerTool.getSessionInfo(request).getCurrentOrg().getOrgCode();
        List<OrganizationEntity> list1 = ControllerTool.getSessionInfo(request).getSuperAndSubOrg();
        String orgCode = "";
        if (list1.size() > 0) {
            for (int i = 0; i < list1.size(); i++) {
                if (!"".equals(list1.get(i).getOrgCode()) && list1.get(i).getOrgCode() != null) {
                    orgCode += list1.get(i).getOrgCode() + ",";
                }
            }
        }
        String[] org = orgCode.split(",");
        StringBuilder orgCodeBuilder = new StringBuilder();
        for (int i = 0; i < org.length; i++) {
            orgCodeBuilder.append("'" + org[i] + "'");
            if (i != org.length - 1) {
                orgCodeBuilder.append(",");
            }
        }
        map.put("orgCode", orgCodeBuilder.toString());
        map.put("start", (Integer.parseInt(map.get("page").toString()) - 1) * Integer.parseInt(map.get("rows").toString()) + 1);
        map.put("end", Integer.parseInt(map.get("page").toString()) * Integer.parseInt(map.get("rows").toString()));
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        int total = 0;
        list = this.serialService.queryJzDataAjxx(map);
        total = this.serialService.queryJzDataAjxxCount(map);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", total);
        result.put("rows", list);
        return result;
    }


    @RequestMapping(value = "/queryJzPersonXs")
    @ResponseBody
    public Map<String, Object> queryJzPerson(@RequestParam Map<String, Object> params,
                                             HttpServletRequest request) throws Exception {
        System.err.println("-------" + params);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        list = serialService.queryJzDataPerson(params);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("rows", list);
        return result;
    }
}
