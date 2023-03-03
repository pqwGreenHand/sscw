package com.zhixin.zhfz.bacs.controller.record;

import com.zhixin.zhfz.bacs.entity.*;
import com.zhixin.zhfz.bacs.form.RecordForm;
import com.zhixin.zhfz.bacs.services.area.IAreaService;
import com.zhixin.zhfz.bacs.services.downloadtask.IDownloadTaskService;
import com.zhixin.zhfz.bacs.services.led.ILedService;
import com.zhixin.zhfz.bacs.services.person.IPersonService;
import com.zhixin.zhfz.bacs.services.personalconfig.IPersonalConfigDetailService;
import com.zhixin.zhfz.bacs.services.record.IRecordService;
import com.zhixin.zhfz.bacs.services.room.IRoomService;
import com.zhixin.zhfz.bacs.services.serial.ISerialService;
import com.zhixin.zhfz.bacs.services.waitingmanage.IWaitingManageService;
import com.zhixin.zhfz.common.common.*;
import com.zhixin.zhfz.common.common.led.LedUtil;
import com.zhixin.zhfz.common.entity.*;
import com.zhixin.zhfz.common.services.Icase.ICaseService;
import com.zhixin.zhfz.common.services.operLog.IOperLogService;
import com.zhixin.zhfz.common.services.organiztion.IOrganizationService;
import com.zhixin.zhfz.common.services.user.IUserService;
import com.zhixin.zhfz.common.utils.ControllerTool;
import com.zhixin.zhfz.common.utils.PropertyUtil;
import com.zhixin.zhfz.common.utils.Utils;
import net.sf.json.JSONObject;
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
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @program: zhfz
 * @description: 审讯
 * @author: jzw
 * @create: 2019-02-26 10:55
 **/

@Controller
@RequestMapping("/zhfz/bacs/record")
public class RecordController {

    private static final Logger logger = LoggerFactory.getLogger(RecordController.class);

    @Autowired
    private IRecordService service;

    @Autowired
    private IOperLogService operLogService;

    @Autowired
    private IAreaService areaService;

    @Autowired
    private IDownloadTaskService taskService;

    @Autowired
    private IRoomService roomService;

    @Autowired
    private IUserService userService;

    @Autowired
    private ISerialService serialService;

    @Autowired
    private IWaitingManageService waitingManageService;

    @Autowired
    private IPersonalConfigDetailService personalConfigDetailService;

    @Autowired
    private ICaseService caseService;

    @Autowired
    private IOrganizationService organizationService;

    @Autowired
    private IPersonService personService;

    @Autowired
    private ILedService ledService;

    /**
     * @return java.util.Map<java.lang.String               ,               java.lang.Object>
     * @Author jzw
     * @Description
     * @Date 11:45 2019/2/21
     * @Param [param]
     **/
    @RequestMapping(value = "/list")
    @ResponseBody
    public Map<String, Object> list(@RequestParam Map<String, Object> param, HttpServletRequest request) throws Exception {
        Map<String, Object> map = ControllerTool.mapFilter(param);
        Map<String, Object> result = new HashMap<String, Object>();
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuth(request))) {
            // 办案人员-本人
            map.put("dataAuth", " ( r.person_id=" + ControllerTool.getUserID(request) + " or r.op_user_id="
                    + ControllerTool.getUserID(request)
                    + " or r.police_ask_id = " + ControllerTool.getUserID(request)
                    + " or r.police_record_id = " + ControllerTool.getUserID(request)
                    + " or s.person_id = " + ControllerTool.getUserID(request)
                    + " or s.in_register_user_id = " + ControllerTool.getUserID(request)
                    + " or s.out_register_user_id = " + ControllerTool.getUserID(request)
                    + " or s.send_user_id = " + ControllerTool.getUserID(request)
                    + " or c.cjr = " + ControllerTool.getUserID(request)
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_SELFAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 办案场所-本办案场所
            map.put("dataAuth", " ( r.area_id=" + ControllerTool.getCurrentAreaID(request)
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 管理员 -本办案场所及下级办案场所
            map.put("dataAuth", "( r.area_id " + sessionInfo.getCurrentAndSubAreaInStr()
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 上级办案场所及其下级办案场所
            map.put("dataAuth", "(  r.area_id " + sessionInfo.getSuperAndSubAreaInStr()
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 公安领导-本部门及下级部门
            map.put("dataAuth", " ( r.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                    + " or c.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 法制人员-上级部门及下级部门
            map.put("dataAuth", " ( r.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
                    + " or c.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuth(request))) {
            // 本部门
            map.put("dataAuth", " ( r.op_pid = " + sessionInfo.getCurrentOrgPid()
                    + " or c.op_pid = " + sessionInfo.getCurrentOrgPid()
                    + " ) ");
        }
        List<RecordEntity> list = service.list(map);
        result.put("total", this.service.count(map));
        // result.put("rows", service.list(map));
        result.put("rows", list);
        return result;
    }

    /**
     * @return com.zhixin.zhfz.common.entity.MessageEntity
     * @Author jzw
     * @Description 增加
     * @Date 10:42 2019/2/23
     * @Param [form]
     **/
    @RequestMapping("/add")
    @ResponseBody
    public MessageEntity add(RecordEntity entity, HttpServletRequest request) {
        logger.info("++++++++add++++++" + entity);
        try {
            Map<String, Object> map = new HashMap<>();
            //entity.setRegisterUserId(Long.valueOf(ControllerTool.getUserID(request)));
            int count = service.getSerialRecording(entity.getSerialId());
            if (count > 0) {
                map.put("serialId", entity.getSerialId());
                String roomName = service.getRecordingRoomName(map);
                return new MessageEntity().addCode(1).addIsError(true).addTitle("错误提示")
                        .addContent(String.format("当前嫌疑人已经在(%s)审讯中，不能新增审讯!", roomName));
            }
            List<RoomEntity> rooms = areaService.listRoomByIp(ControllerTool.getClienIp(request),
                    entity.getAreaId() + "");
            // 根据ips查找room
            map.put("ip", ControllerTool.getClienIp(request));
            RoomEntity room = roomService.findRoomByIps(map);
            if (rooms != null && rooms.size() > 0) {
                entity.setRoomId(Long.valueOf(room.getId()));
                entity.setOpPid(ControllerTool.getSessionInfo(request).getCurrentOrg().getPid());
                entity.setOpUserId(ControllerTool.getSessionInfo(request).getUser().getId());
                map.clear();
                map.put("areaId", entity.getAreaId());
                map.put("ip", ControllerTool.getClienIp(request));
                count = service.getRoomRecording(map);
                if (count > 0) {
                    return new MessageEntity().addCode(1).addIsError(true).addTitle("错误提示")
                            .addContent("当前审讯室正在审讯，不能新增审讯!");
                }
            } else {
                return new MessageEntity().addCode(1).addIsError(true).addTitle("错误提示").addContent("请到询/讯问室开启笔录!");
            }
            entity = service.insert(entity);
            entity.setId(service.getMaxId());
            this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "添加审讯：" + entity, ControllerTool.getLoginName(request), true, "办案场所");
        } catch (Exception e) {
            logger.error("Error on adding Record!", e);
            this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "添加审讯：" + entity, ControllerTool.getLoginName(request), false, "办案场所");
            return new MessageEntity().addCode(1).addIsError(true).addTitle("提示").addContent("添加失败!");
        }
        return MessageEntity.success("添加成功!").addCallbackData(entity);
    }

    @RequestMapping("/addRecord")
    @ResponseBody
    public MessageEntity addRecord(@RequestBody RecordForm form, HttpServletRequest request) throws Exception {
        logger.info("===============RecordForm==============" + form);
        String resultJson = "";
        SerialEntity serialEntity = serialService.queryById(form.getSerialId());
        RecordEntity entity = new RecordEntity();
        entity.setUuid(Utils.getUUId());
        entity.setAreaId(serialEntity.getAreaId().longValue());
        entity.setCaseId(serialEntity.getCaseId().longValue());
        entity.setSerialId(form.getSerialId());
        int interrogateCount = service.queryNextCount(form.getSerialId());
        entity.setCount(interrogateCount);
        entity.setPersonId(serialEntity.getPersonId());
        entity.setPoliceAskId(userService.getUserByCertificateNo(form.getAskNo()).getId().longValue());
        entity.setPoliceRecordId(userService.getUserByCertificateNo(form.getRecodeNo()).getId().longValue());
        entity.setRecordTemplateId(Long.parseLong("11"));
        entity.setOpPid(ControllerTool.getSessionInfo(request).getCurrentOrg().getPid());
        entity.setOpUserId(ControllerTool.getSessionInfo(request).getUser().getId());
        //entity.setRecordTemplateId(form.getRecordTemp());
        List<RoomEntity> rooms = areaService.listRoomByIp(ControllerTool.getClienIp(request), serialEntity.getAreaId().toString());
        // 审讯管理页面，选择的嫌疑人必须是对应的预约房间才可以审讯
//        WaitingRecordEntity waitingRecordEntity = new WaitingRecordEntity ();
//        waitingRecordEntity.setSerialId (form.getSerialId());
//        List<WaitingRecordEntity> waitingList = waitingManageService.getWaitingRecordBySerialId (waitingRecordEntity);
//        //int count = waitingList.size ();
//        String roomName = "";//候问管理中预约的审讯室
//        int recordRoomId = 0;// 候问管理中预约的审讯室Id
//        if ((waitingList !=null && waitingList.size () > 0) && (rooms != null && rooms.size () >0)) {
//            roomName = waitingList.get (0).getGetResult ();// 预约的审讯室房间名称
//            if (waitingList.get (0).getRecordRoomId ()!= null ) {
//                recordRoomId = waitingList.get (0).getRecordRoomId ();//审讯室房间Id
//                if (   recordRoomId != rooms.get (0).getId ()) {
//                    return new MessageEntity ().addIsError (true).addTitle ("错误").addContent ("请去<" + roomName + ">开启审讯！");
//                }
//            } else {
//                return new MessageEntity ().addIsError (true).addTitle ("错误").addContent ("请先在<候问管理>中提讯该嫌疑人并选择审讯室后点击确定！！!");
//            }
//
//        } else {
//            // 没有看押就直接提讯
//            logger.info ("请检查当前审讯室=="+ roomName +"==与候问管理中提讯的房间一致");
//            return new MessageEntity ().addIsError (true).addTitle ("错误").addContent ("请先在<候问管理>中提讯该嫌疑人并选择审讯室后点击确定！！!");
//        }

        OperLogEntity operLogEntity = new OperLogEntity();
        //operLogEntity.setType ("开始审讯");
        //operLogEntity.setUser ("system");

        try {
            String ipAndPort = getTiandyUrlByClientIP(request, serialEntity.getAreaId());
            logger.info("=================ipAndPort================= " + ipAndPort);
            if (ipAndPort != null && !"".equals(ipAndPort) && rooms.size() > 0) {
                entity.setRoomId(rooms.get(0).getId().longValue());
                entity.setStatus(2);// 初始化
                long currTime = System.currentTimeMillis();


                service.insert(entity);
                logger.info("------------插入审讯记录用时--------------" + (System.currentTimeMillis() - currTime));
                this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "添加审讯信息" + entity, ControllerTool.getSessionInfo(request).getUser().getLoginName(), true, OperLogEntity.SYSTEM_BACS);

                long currTime1 = System.currentTimeMillis();
                serialService.changestatus(entity.getSerialId(), 6);
                logger.info("------------修改状态用时--------------" + (System.currentTimeMillis() - currTime1));
                logger.info("--------------获取到审讯Id：" + entity.getId());

                UserEntity user = ControllerTool.getUser(request);
                // 设置用户名密码
                JSONObject jsonObject = new JSONObject();
                // jsonObject.put("userId", "admin");
                // jsonObject.put("username", "admin");
                // jsonObject.put("password", "MTExMQ==");
                jsonObject.put("userId", user.getId());
                jsonObject.put("username", user.getLoginName());
                jsonObject.put("password", Base64Util.encode(user.getPassword()));

                operLogEntity.setType("开始审讯");
                operLogEntity.setUser(user.getLoginName());
                // 获取案件信息
                CaseEntity caseEntity = caseService.getCaseInfoById(serialEntity.getCaseId());
                Map<String, Object> archiveMap = new HashMap<>();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//				String caseName = sdf.format(caseEntity.getInvolvedDate())+" "+caseEntity.getInvolvedAddress()+" "+caseEntity.getInvolvedReason();
                // String caseName = sdf.format (caseEntity.getInvolvedDate ()) + " " + caseEntity.getInvolvedAddress () + " " + caseEntity.getCrimeType ();
                archiveMap.put("archiveName", caseEntity.getAjmc());
                archiveMap.put("archiveNum", caseEntity.getAjbh());
                // archiveMap.put("archiveDept",
                // "3329df39-b9e1-41b7-9dc2-ffcafb6189ce");//caseEntity.getInterrogateAreaId()
                archiveMap.put("archiveDept", user.getOrganizationId());
                archiveMap.put("archiveType", form.getCaseType());
                // archiveMap.put("archivePlace",
                // caseEntity.getInvolvedAddress());
                // archiveMap.put("archiveInstruction",
                // caseEntity.getInvolvedReason());
                jsonObject.put("archive", archiveMap);

                PersonEntity personEntity = personService.getPersonById(serialEntity.getPersonId());
                Map<String, Object> suspectPersonMap = new HashMap<>();
                // suspectPersonMap.put("name", "1");//personEntity.getName()
                // suspectPersonMap.put("sex", 1);
                // suspectPersonMap.put("age", 18);
                suspectPersonMap.put("name", personEntity.getName());// personEntity.getName()
                //0男1女
                String sex = "";
                if (personEntity.getSex() == null) {
                    sex = "0";
                } else {
                    sex = personEntity.getSex().toString().equals('1') ? "0" : "1";
                }
                suspectPersonMap.put("sex", sex);
                // suspectPersonMap.put("age", 18);
                int age = 18;
                try {
                    age = IdcardUtils.getAgeByIdCard(personEntity.getCertificateNo());
                } catch (Exception e) {
                    logger.error("", e);
                }
                suspectPersonMap.put("age", age);

                suspectPersonMap.put("bornDate", "");// 出生日期
                suspectPersonMap.put("nation", "");// 民族
                suspectPersonMap.put("education", "");// 文化程度
                suspectPersonMap.put("politics", "");// 政治面貌
                suspectPersonMap.put("phoneNum", "");// 电话号码
                suspectPersonMap.put("cardType", 1);// 证件类型
                suspectPersonMap.put("cardNum", personEntity.getCertificateTypeId() == 111
                        ? personEntity.getCertificateNo() : "330722197609192116");// 证件号码
                suspectPersonMap.put("nativePlace", "");// 籍贯
                suspectPersonMap.put("registeredResidence", "");// 户籍地
                suspectPersonMap.put("placeBirth", "");// 出生地
                suspectPersonMap.put("residence", "");// 现住地
                suspectPersonMap.put("post", "");// 职务
                suspectPersonMap.put("unit", "");// 单位
                suspectPersonMap.put("resume", "");// 个人简历
                suspectPersonMap.put("familyStatus", "");// 家庭情况
                suspectPersonMap.put("crimeHistory", "");// 受过何种刑事行政处罚
                suspectPersonMap.put("congressman", "");// 1为人大代表，其他为0

                jsonObject.put("suspectPerson", suspectPersonMap);

                Map<String, Object> inquestMap = new HashMap<>();
                inquestMap.put("inquestId", entity.getUuid());
                // TODO
                // inquestMap.put("inquestType", 2);
                inquestMap.put("inquestType", form.getRecordType());
                inquestMap.put("templateFlage", form.getIsFlage() == null ? "0" : "1");
                // inquestMap.put("templateType",
                // "0da6dfde-9ddd-497e-86b9-2362537e377d");
                inquestMap.put("templateType", form.getFlageType());// 笔录模板类型
                // inquestMap.put("inquestTempType",
                // "fc40e82c-6e07-48e0-9ca5-61da188115f4");
                inquestMap.put("inquestTempType", form.getRecordTemp());// 问答模板
                // inquestMap.put("burnFlag", form.getIsBurn()==null?"0":"1");
                // inquestMap.put("detectPerson1", "admin");
                inquestMap.put("detectPerson1", user.getLoginName());
                // inquestMap.put("detectDept1", form.getDetectDept());

                String location = areaService.getRecordLocationByRoomID(entity.getRoomId());
                inquestMap.put("location", location == null ? "" : location);
                jsonObject.put("inquest", inquestMap);
                JSONObject ftpJsonObject = new JSONObject();
                ftpJsonObject.put("ftpIp", PropertyUtil.getContextProperty("ftp.ip"));
                ftpJsonObject.put("ftpPort", PropertyUtil.getContextProperty("ftp.port"));
                ftpJsonObject.put("ftpUserName", PropertyUtil.getContextProperty("ftp.user"));
                ftpJsonObject.put("ftpPassword", PropertyUtil.getContextProperty("ftp.pwd"));
                //String serialNo = serialService.getSerialNoById (entity.getInterrogateSerialId ()).getSerialNo ();
                String serialNo = serialEntity.getSerialNo(); //serialService.getSerialNoById (entity.getInterrogateSerialId ()).getSerialNo ();
                String dirUrl = "/Inquest/" + Utils.getDateFromSerialNO(serialNo) + "/";
                // FTPUtil.createDirectory(dirUrl);
                AsynUtil.getInstance().createFTPDirectory(dirUrl);
                dirUrl += entity.getUuid() + "/";
                // FTPUtil.createDirectory(dirUrl);
                AsynUtil.getInstance().createFTPDirectory(dirUrl);
                ftpJsonObject.put("ftpPathInquest", dirUrl);
                jsonObject.put("ftpInfo", ftpJsonObject);
                // resultJson = new
                // String(jsonObject.toString().getBytes("UTF-8"));
                resultJson = jsonObject.toString();
                logger.info("=================resultJson1================= " + resultJson);
                // System.err.println("=================resultJson2=================
                // "+resultJson);
                // resultJson=URLEncoder.encode(resultJson, "utf-8");
                String url = ipAndPort + "/sx/templates/WfrmEexternalInquest_test.html?" + resultJson;
                logger.info("=================开始审讯 url================= " + url);
                logger.info("### 开始审讯 ### " + url);


                operLogEntity.setIsSuccess(true);
                operLogEntity.setContent("=================开始审讯 url=================" + url);
                operLogService.insertLog(OperLogEntity.INSERT_TYPE, "添加审讯信息" + entity, ControllerTool.getSessionInfo(request).getUser().getLoginName(), true, OperLogEntity.SYSTEM_BACS);
                //修改审讯室状态为占用
                roomService.updateRoomStatus(Integer.parseInt(entity.getRoomId() + ""), 2);
                return new MessageEntity().addIsError(false).addCallbackData(url);
            } else {
                entity.setStatus(2);// 初始化
                long currTime = System.currentTimeMillis();
                service.insert(entity);
                logger.info("------------插入审讯记录用时--------------" + (System.currentTimeMillis() - currTime));
                this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "添加审讯信息" + entity, "system", true, "办案场所");

                long currTime1 = System.currentTimeMillis();
                serialService.changestatus(entity.getSerialId(), 6);
                logger.info("------------修改状态用时--------------" + (System.currentTimeMillis() - currTime1));
                System.out.println("--------------获取到审讯Id：" + entity.getId());

                UserEntity user = ControllerTool.getUser(request);
                // 设置用户名密码
                JSONObject jsonObject = new JSONObject();
                // jsonObject.put("userId", "admin");
                // jsonObject.put("username", "admin");
                // jsonObject.put("password", "MTExMQ==");
                jsonObject.put("userId", user.getId());
                jsonObject.put("username", user.getLoginName());
                jsonObject.put("password", Base64Util.encode(user.getPassword()));
                // 获取案件信息
                CaseEntity caseEntity = caseService.getCaseById(serialEntity.getCaseId());
                Map<String, Object> archiveMap = new HashMap<>();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                //String caseName = sdf.format (caseEntity.getInvolvedDate ()) + " " + caseEntity.getInvolvedAddress () + " " + caseEntity.getInvolvedReason ();
                String caseName = caseEntity.getAjmc();
                archiveMap.put("archiveName", caseName);
                archiveMap.put("archiveNum", caseEntity.getAjbh());
                // archiveMap.put("archiveDept",
                // "3329df39-b9e1-41b7-9dc2-ffcafb6189ce");//caseEntity.getInterrogateAreaId()
                archiveMap.put("archiveDept", user.getOrganizationId());
                //     organizationService.getOrganizationByUserId (user.getId ()).getId ().toString ());
                archiveMap.put("archiveType", form.getCaseType());
                // archiveMap.put("archivePlace",
                // caseEntity.getInvolvedAddress());
                // archiveMap.put("archiveInstruction",
                // caseEntity.getInvolvedReason());
                jsonObject.put("archive", archiveMap);

                PersonEntity personEntity = personService.getPersonById(serialEntity.getPersonId());
                Map<String, Object> suspectPersonMap = new HashMap<>();
                // suspectPersonMap.put("name", "1");//personEntity.getName()
                // suspectPersonMap.put("sex", 1);
                // suspectPersonMap.put("age", 18);
                suspectPersonMap.put("name", personEntity.getName());// personEntity.getName()
                String sex = personEntity.getSex() == null ? "0" : personEntity.getSex().toString();
                suspectPersonMap.put("sex", sex);
                // suspectPersonMap.put("age", 18);
                int age = 18;
                try {
                    age = IdcardUtils.getAgeByIdCard(personEntity.getCertificateNo());
                } catch (Exception e) {
                    logger.error("", e);
                }
                suspectPersonMap.put("age", age);

                suspectPersonMap.put("bornDate", "");// 出生日期
                suspectPersonMap.put("nation", "");// 民族
                suspectPersonMap.put("education", "");// 文化程度
                suspectPersonMap.put("politics", "");// 政治面貌
                suspectPersonMap.put("phoneNum", "");// 电话号码
                suspectPersonMap.put("cardType", 1);
                //    personEntity.getCertificateTypeId () != 7 ? personEntity.getCertificateTypeId () : 1);// 证件类型
                suspectPersonMap.put("cardNum", personEntity.getCertificateTypeId() == 111
                        ? personEntity.getCertificateNo() : "330722197609192116");// 证件号码
                suspectPersonMap.put("nativePlace", "");// 籍贯
                suspectPersonMap.put("registeredResidence", "");// 户籍地
                suspectPersonMap.put("placeBirth", "");// 出生地
                suspectPersonMap.put("residence", "");// 现住地
                suspectPersonMap.put("post", "");// 职务
                suspectPersonMap.put("unit", "");// 单位
                suspectPersonMap.put("resume", "");// 个人简历
                suspectPersonMap.put("familyStatus", "");// 家庭情况
                suspectPersonMap.put("crimeHistory", "");// 受过何种刑事行政处罚
                suspectPersonMap.put("congressman", "");// 1为人大代表，其他为0

                jsonObject.put("suspectPerson", suspectPersonMap);

                Map<String, Object> inquestMap = new HashMap<>();
                inquestMap.put("inquestId", entity.getUuid());
                // TODO
                // inquestMap.put("inquestType", 2);
                inquestMap.put("inquestType", form.getRecordType());
                inquestMap.put("templateFlage", form.getIsFlage() == null ? "0" : "1");
                // inquestMap.put("templateType",
                // "0da6dfde-9ddd-497e-86b9-2362537e377d");
                inquestMap.put("templateType", form.getFlageType());// 笔录模板类型
                // inquestMap.put("inquestTempType",
                // "fc40e82c-6e07-48e0-9ca5-61da188115f4");
                inquestMap.put("inquestTempType", form.getRecordTemp());// 问答模板
                // inquestMap.put("burnFlag", form.getIsBurn()==null?"0":"1");
                // inquestMap.put("detectPerson1", "admin");
                inquestMap.put("detectPerson1", user.getLoginName());
                // inquestMap.put("detectDept1", form.getDetectDept());

                String location = areaService.getRecordLocationByRoomID(entity.getRoomId());
                inquestMap.put("location", location == null ? "" : location);
                jsonObject.put("inquest", inquestMap);
                JSONObject ftpJsonObject = new JSONObject();
                ftpJsonObject.put("ftpIp", PropertyUtil.getContextProperty("ftp.ip"));
                ftpJsonObject.put("ftpPort", PropertyUtil.getContextProperty("ftp.port"));
                ftpJsonObject.put("ftpUserName", PropertyUtil.getContextProperty("ftp.user"));
                ftpJsonObject.put("ftpPassword", PropertyUtil.getContextProperty("ftp.pwd"));
                String serialNo = serialEntity.getSerialNo();//serialService.getSerialNoById (entity.getInterrogateSerialId ()).getSerialNo ();
                String dirUrl = "/Inquest/" + Utils.getDateFromSerialNO(serialNo) + "/";
                // FTPUtil.createDirectory(dirUrl);
                AsynUtil.getInstance().createFTPDirectory(dirUrl);
                dirUrl += entity.getUuid() + "/";
                // FTPUtil.createDirectory(dirUrl);
                AsynUtil.getInstance().createFTPDirectory(dirUrl);
                ftpJsonObject.put("ftpPathInquest", dirUrl);
                jsonObject.put("ftpInfo", ftpJsonObject);
                // resultJson = new
                // String(jsonObject.toString().getBytes("UTF-8"));
                resultJson = jsonObject.toString();
                logger.info("=================resultJson1================= " + resultJson);
                // System.err.println("=================resultJson2=================
                // "+resultJson);
                // resultJson=URLEncoder.encode(resultJson, "utf-8");
                String url = "http://10.11.229.144:7000/sx/templates/WfrmTestInquest_test.html?" + resultJson;
                logger.info("=================开始审讯 url================= " + url);
                logger.info("### 开始审讯 ### " + url);

                operLogEntity.setIsSuccess(true);
                operLogEntity.setContent("=================开始审讯 url=================" + url);
                logger.info("------------插入审讯记录用时--------------" + (System.currentTimeMillis() - currTime));
                operLogService.insertLog(OperLogEntity.INSERT_TYPE, operLogEntity.getContent(), ControllerTool.getUser(request).getLoginName(), true, "办案场所");
                return new MessageEntity().addIsError(false).addCallbackData(url);
                //return new MessageEntity().addIsError(true).addTitle("错误信息").addContent("办案中心未配置审讯平台，或审讯室工作电脑IP配置错误！");
            }
        } catch (Exception e) {
            logger.error("Error on adding record!", e);
            logger.info(e.toString());
            //this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "添加审讯信息" + entity, "system",e false);
            operLogEntity.setIsSuccess(false);
            operLogEntity.setContent("添加审讯实体类=" + entity + ";异常信息=" + e);
            operLogService.insertLog(OperLogEntity.INSERT_TYPE, operLogEntity.getContent(), ControllerTool.getUser(request).getLoginName(), true, "办案场所");
            return new MessageEntity().addCode(1).addIsError(true).addTitle("Error").addContent("Add failure!");
        }
    }


    /**
     * 讯飞审讯
     *
     * @param form
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/addRecordXf")
    @ResponseBody
    public MessageEntity addRecordXf(@RequestBody RecordForm form, HttpServletRequest request) throws Exception {
        logger.info("===============RecordForm进入讯飞审讯成功==============" + form);
        String resultJson = "";
        SerialEntity serialEntity = serialService.queryById(form.getSerialId());
        RecordEntity entity = new RecordEntity();
        entity.setUuid(Utils.getUUId());
        entity.setAreaId(serialEntity.getAreaId().longValue());
        entity.setCaseId(serialEntity.getCaseId().longValue());
        entity.setSerialId(form.getSerialId());
        int interrogateCount = service.queryNextCount(form.getSerialId());
        entity.setCount(interrogateCount);
        entity.setPersonId(serialEntity.getPersonId());
        entity.setPoliceAskId(userService.getUserByCertificateNo(form.getAskNo()).getId().longValue());
        entity.setPoliceRecordId(userService.getUserByCertificateNo(form.getRecodeNo()).getId().longValue());
        entity.setRecordTemplateId(Long.parseLong("11"));
        entity.setOpPid(ControllerTool.getSessionInfo(request).getCurrentOrg().getPid());
        entity.setOpUserId(ControllerTool.getSessionInfo(request).getUser().getId());
        List<RoomEntity> rooms = areaService.listRoomByIp(ControllerTool.getClienIp(request), serialEntity.getAreaId().toString());

        if (rooms.size() < 1) {
            return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("无效的配置(ip and port)!");
        }

        // 审讯管理页面，选择的嫌疑人必须是对应的预约房间才可以审讯
        WaitingRecordEntity waitingRecordEntity = new WaitingRecordEntity();
        waitingRecordEntity.setSerialId(form.getSerialId());
        List<WaitingRecordEntity> waitingList = waitingManageService.getWaitingRecordBySerialId(waitingRecordEntity);
        //int count = waitingList.size ();
        String roomName = "";//候问管理中预约的审讯室
        int recordRoomId = 0;// 候问管理中预约的审讯室Id
        if ((waitingList != null && waitingList.size() > 0) && (rooms != null && rooms.size() > 0)) {
            roomName = waitingList.get(0).getGetResult();// 预约的审讯室房间名称
            if (roomName != null) {
//                recordRoomId = waitingList.get (0).getRecordRoomId ();//审讯室房间Id
                if (!roomName.equals(rooms.get(0).getName())) {
                    return new MessageEntity().addIsError(true).addTitle("错误").addContent("请去<" + roomName + ">开启审讯！");
                }
                if(waitingList.get(0).getRecordUuid()!=null){
                    logger.info("审讯设置uuid" + waitingList.get(0).getRecordUuid());
                    entity.setUuid(waitingList.get(0).getRecordUuid());
                }
            } else {
                return new MessageEntity().addIsError(true).addTitle("错误").addContent("请先在<候问管理>中提讯该嫌疑人并选择审讯室后点击确定！！!");
            }

        } else {
            // 没有看押就直接提讯
            logger.info("请检查当前审讯室==" + roomName + "==与候问管理中提讯的房间一致");
            return new MessageEntity().addIsError(true).addTitle("错误").addContent("请先在<候问管理>中提讯该嫌疑人并选择审讯室后点击确定！！!");
        }

        String noString = ControllerTool.getSessionInfo(request).getUser().getCertificateNo();
        //根据登陆警号查询讯飞的账户和密码
        Map<String, Object> param = new HashMap<>();
        param.put("account_name", noString);
        List<Map<String, Object>> list = userService.queryUserByCertNoToXF(param);

        if (list.size() < 1) {
            return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("[" + noString + "]无法跳转到智能审讯系统!");
        }

        OperLogEntity operLogEntity = new OperLogEntity();
        try {
            String ipAndPort = getTiandyUrlByClientIP(request, serialEntity.getAreaId());
            System.err.println("=================ipAndPort================= " + ipAndPort);
            if (ipAndPort != null && !"".equals(ipAndPort) && rooms.size() > 0) {
                logger.info("----------进入11--------------");

                entity.setRoomId(rooms.get(0).getId().longValue());
                entity.setStatus(1);// 审讯中
                long currTime = System.currentTimeMillis();
                service.insert(entity);

                //修改审讯室状态为占用
                roomService.updateRoomStatus(Integer.parseInt(entity.getRoomId() + ""), 2);

                try {//led审讯中
                    LedEntity led = ledService.LedByRoomId(Integer.parseInt(entity.getRoomId().toString()));
                    logger.info("++++++++进入led设置1 ++++++" + led);
                    led.setIp(led.getIp());
                    led.setPort(led.getPort());
                    led.setColor("red");
                    led.setContent("使用中");
                    led.setFontSize(17);
                    led.setLeft(8);
                    led.setTop(2);
                    led.setWidth(200);
                    led.setHeight(24);
                    LedOldUtil.controlLED2010(led);
//					  LedUtil.ledPlay("使用中", led.getIp(), led.getPort(), 0);
                } catch (Exception e) {
                    logger.info("++++++++led error 设置有问题++++++");
                }
                logger.info("------------插入审讯记录用时--------------" + (System.currentTimeMillis() - currTime));
                this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "添加审讯信息" + entity, ControllerTool.getSessionInfo(request).getUser().getLoginName(), true, OperLogEntity.SYSTEM_BACS);

                long currTime1 = System.currentTimeMillis();
                serialService.changestatus(entity.getSerialId(), 6);
                logger.info("------------修改状态用时--------------" + (System.currentTimeMillis() - currTime1));
                System.out.println("--------------获取到审讯Id：" + entity.getId());

                UserEntity user = ControllerTool.getUser(request);
                // 获取案件信息
                CaseEntity caseEntity = caseService.getCaseById(serialEntity.getCaseId());
                String caseName = caseEntity.getAjmc();
                PersonEntity personEntity = personService.getPersonById(serialEntity.getPersonId());
                String sex = personEntity.getSex() == null ? "0" : personEntity.getSex().toString();
                int age = 18;
                try {
                    age = IdcardUtils.getAgeByIdCard(personEntity.getCertificateNo());
                } catch (Exception e) {
                    logger.error("", e);
                }


                System.err.println(list);
                String dirUrl = "Inquest" + Utils.getDateFromSerialNO(serialEntity.getSerialNo());
                dirUrl += entity.getUuid();
//				String str1= "{\"route\":\"onGift\",\"time\":\"\",\"userId\":\"\",\"userName\":\"\",\"level\":\"1_2_2_2_0\",\"adminType\":0,\"tuserId\":\"\"," +
//				        "\"tuserName\":\"\",\"tlevel\":\"10_2_2_2_0\",\"roomId\":\"\",\"giftId\":\"001\",\"fcId\":\"0\",\"amount\":\"1\",\"price\":\"0\",\"bean\":\"6546316\"}";
                String str = "[";
                str += "{\"key\":\"ftpip\",\"value\":\"" + PropertyUtil.getContextProperty("ftp.ip") + "\"},";
                str += "{\"key\":\"ftpport\",\"value\":\"" + PropertyUtil.getContextProperty("ftp.port") + "\"},";
                str += "{\"key\":\"ftpusername\",\"value\":\"" + PropertyUtil.getContextProperty("ftp.user") + "\"},";
                str += "{\"key\":\"ftppassword\",\"value\":\"" + PropertyUtil.getContextProperty("ftp.pwd") + "\"},";
                str += "{\"key\":\"hearingrommip\",\"value\":\"" + rooms.get(0).getIps() + "\"},";
                str += "{\"key\":\"ftppathinquest\",\"value\":\"" + dirUrl + "\"},";
                str += "{\"key\":\"inquestid\",\"value\":\"" + rooms.get(0).getName() + "\"},";
                str += "{\"key\":\"username\",\"value\":\"" + user.getCertificateNo() + "\"},";
                str += "{\"key\":\"userId\",\"value\":\"" + "user" + user.getId() + "\"},";
                str += "{\"key\":\"archiveName\",\"value\":\"" + caseName.replaceAll(" ", "") + "\"},";
                str += "{\"key\":\"archiveNum\",\"value\":\"" + caseEntity.getAjbh() + "\"},";
                str += "{\"key\":\"archiveDept\",\"value\":\"" + "dept" + user.getOrganizationId() + "\"},";
                str += "{\"key\":\"archiveType\",\"value\":\"" + form.getCaseType() + "\"},";
                str += "{\"key\":\"id\",\"value\":\"" + form.getSerialId() + "\"},";
                str += "{\"key\":\"idNumber\",\"value\":\"" + personEntity.getCertificateNo() + "\"},";
                str += "{\"key\":\"name\",\"value\":\"" + personEntity.getName() + "\"},";
                str += "{\"key\":\"sex\",\"value\":\"" + sex + "\"},";
                str += "{\"key\":\"age\",\"value\":\"" + age + "\"},";
                str += "{\"key\":\"inquestRoomId\",\"value\":\"" + rooms.get(0).getName() + "\"},";
                str += "{\"key\":\"inquestType\",\"value\":\"\"},";
                str += "{\"key\":\"detectPerson1\",\"value\":\"" + user.getRealName() + "\"},";
                str += "{\"key\":\"detectDept1\",\"value\":\"\"},";
//				str+= "{\"key\":\"detectDept1\",\"value\":\""+user.getOrganizationName()+"\"},";
                str += "{\"key\":\"detectPersonCode1\",\"value\":\"" + user.getCertificateNo() + "\"},";
//				str+= "{\"key\":\"detectDept1\",\"value\":\"\"},";
                str += "{\"key\":\"detectPerson2\",\"value\":\"\"},";
                str += "{\"key\":\"detectDept2\",\"value\":\"\"},";
                str += "{\"key\":\"detectPersonCode2\",\"value\":\"\"}";

                str += "]";
                logger.info("----同步讯飞账号------" + list);
                System.err.println(str);
                logger.info("--------路径------------" + str);
                // 设置用户名密码
                String issso = "1";
                String uid = list.get(0).get("account_name") + "";
                String pwd = list.get(0).get("password") + "";
                String pin = "";
                String assistno = form.getRecodeNo();
                String casenumber = caseEntity.getAjbh();
                String recordtype = form.getFlageType();
                String params = "";
                params += "issso=" + issso + "&recordtype=" + recordtype + "&casenumber=" + casenumber + "&uid=" + uid + "&pwd=" + pwd + "&auxiliary=" + str;
//				String url = ipAndPort + "/sx/templates/WfrmEexternalInquest_test.html?" + params;
                String url = "sx:/" + params;
                System.err.println("=================开始审讯 url================= " + url);
                logger.info("### 开始审讯 ### " + url);

                return new MessageEntity().addIsError(false).addCallbackData(url);

            } else {
                entity.setStatus(1);// 使用中
                long currTime = System.currentTimeMillis();
                service.insert(entity);

                //修改审讯室状态为占用
                roomService.updateRoomStatus(Integer.parseInt(entity.getRoomId() + ""), 2);
                try {//led使用中
                    LedEntity led = ledService.LedByRoomId(Integer.parseInt(entity.getRoomId().toString()));
                    logger.info("++++++++进入led设置2 ++++++" + led);

                    led.setIp(led.getIp());
                    led.setPort(led.getPort());
                    led.setColor("red");
                    led.setContent("使用中");
                    led.setFontSize(17);
                    led.setLeft(8);
                    led.setTop(2);
                    led.setWidth(200);
                    led.setHeight(24);
                    LedOldUtil.controlLED2010(led);
//                    LedUtil.ledPlay("使用中", led.getIp(), led.getPort(), 0);
                } catch (Exception e) {
                    logger.info("++++++++led error 设置有问题++++++");
                }


                logger.info("------------插入审讯记录用时--------------" + (System.currentTimeMillis() - currTime));
                this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "添加审讯信息" + entity, ControllerTool.getSessionInfo(request).getUser().getLoginName(), true, OperLogEntity.SYSTEM_BACS);

                long currTime1 = System.currentTimeMillis();
                serialService.changestatus(entity.getSerialId(), 6);
                logger.info("------------修改状态用时--------------" + (System.currentTimeMillis() - currTime1));
                System.out.println("--------------获取到审讯Id：" + entity.getId());
                UserEntity user = ControllerTool.getUser(request);
                // 获取案件信息
                CaseEntity caseEntity = caseService.getCaseById(serialEntity.getCaseId());
                String caseName = caseEntity.getAjmc();
                PersonEntity personEntity = personService.getPersonById(serialEntity.getPersonId());
                String sex = personEntity.getSex() == null ? "0" : personEntity.getSex().toString();
                int age = 18;
                try {
                    age = IdcardUtils.getAgeByIdCard(personEntity.getCertificateNo());
                } catch (Exception e) {
                    logger.error("", e);
                }


                System.err.println(list);
                logger.info("----同步讯飞账号------" + list);
                String dirUrl = "Inquest" + Utils.getDateFromSerialNO(serialEntity.getSerialNo());
                dirUrl += entity.getUuid();

                String str = "[";
                str += "{\"key\":\"ftpip\",\"value\":\"" + PropertyUtil.getContextProperty("ftp.ip") + "\"},";
                str += "{\"key\":\"ftpport\",\"value\":\"" + PropertyUtil.getContextProperty("ftp.port") + "\"},";
                str += "{\"key\":\"ftpusername\",\"value\":\"" + PropertyUtil.getContextProperty("ftp.user") + "\"},";
                str += "{\"key\":\"ftppassword\",\"value\":\"" + PropertyUtil.getContextProperty("ftp.pwd") + "\"},";
                str += "{\"key\":\"hearingrommip\",\"value\":\"" + rooms.get(0).getIps() + "\"},";
                str += "{\"key\":\"ftppathinquest\",\"value\":\"" + dirUrl + "\"},";
                str += "{\"key\":\"inquestid\",\"value\":\"" + rooms.get(0).getName() + "\"},";
                str += "{\"key\":\"username\",\"value\":\"" + user.getCertificateNo() + "\"},";
                str += "{\"key\":\"userId\",\"value\":\"" + "user" + user.getId() + "\"},";
                str += "{\"key\":\"archiveName\",\"value\":\"" + caseName.replaceAll(" ", "") + "\"},";
                str += "{\"key\":\"archiveNum\",\"value\":\"" + caseEntity.getAjbh() + "\"},";
                str += "{\"key\":\"archiveDept\",\"value\":\"" + "dept" + user.getOrganizationId() + "\"},";
                str += "{\"key\":\"archiveType\",\"value\":\"" + form.getCaseType() + "\"},";
                str += "{\"key\":\"id\",\"value\":\"" + form.getSerialId() + "\"},";
                str += "{\"key\":\"idNumber\",\"value\":\"" + personEntity.getCertificateNo() + "\"},";
                str += "{\"key\":\"name\",\"value\":\"" + personEntity.getName() + "\"},";
                str += "{\"key\":\"sex\",\"value\":\"" + sex + "\"},";
                str += "{\"key\":\"age\",\"value\":\"" + age + "\"},";
                str += "{\"key\":\"inquestRoomId\",\"value\":\"" + rooms.get(0).getName() + "\"},";
                str += "{\"key\":\"inquestType\",\"value\":\"\"},";
                str += "{\"key\":\"detectPerson1\",\"value\":\"" + user.getRealName() + "\"},";
                str += "{\"key\":\"detectDept1\",\"value\":\"\"},";
//				str+= "{\"key\":\"detectDept1\",\"value\":\""+user.getOrganizationName()+"\"},";
                str += "{\"key\":\"detectPersonCode1\",\"value\":\"" + user.getCertificateNo() + "\"},";
//				str+= "{\"key\":\"detectDept1\",\"value\":\"\"},";
                str += "{\"key\":\"detectPerson2\",\"value\":\"\"},";
                str += "{\"key\":\"detectDept2\",\"value\":\"\"},";
                str += "{\"key\":\"detectPersonCode2\",\"value\":\"\"}";

                str += "]";
                logger.info("----同步讯飞账号------" + list);
                System.err.println(str);
                logger.info("--------路径------------" + str);
                // 设置用户名密码
                String issso = "1";
                String uid = list.get(0).get("account_name") + "";
                String pwd = list.get(0).get("password") + "";
                String pin = "";
                String assistno = form.getRecodeNo();
                String casenumber = caseEntity.getAjbh();
                String recordtype = form.getFlageType();
                String params = "";
                params += "issso=" + issso + "&recordtype=" + recordtype + "&casenumber=" + casenumber + "&uid=" + uid + "&pwd=" + pwd + "&auxiliary=" + str;
//				String url = ipAndPort + "/sx/templates/WfrmEexternalInquest_test.html?" + params;
                String url = "sx:/" + params;
                System.err.println("=================开始审讯 url================= " + url);
                logger.info("### 开始审讯 ### " + url);

                return new MessageEntity().addIsError(false).addCallbackData(url);
            }
        } catch (Exception e) {
            logger.error("Error on adding record!", e);
            logger.info(e.toString());
            //this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "添加审讯信息" + entity, "system",e false);
            operLogEntity.setIsSuccess(false);
            operLogEntity.setContent("添加审讯实体类=" + entity + ";异常信息=" + e);
            operLogService.insertLog(OperLogEntity.INSERT_TYPE, operLogEntity.getContent(), ControllerTool.getUser(request).getLoginName(), true, "办案场所");
            return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("跳转智能审讯系统错误,请联系管理员!");
        }
    }

    /**
     * @return com.zhixin.zhfz.common.entity.MessageEntity
     * @Author jzw
     * @Description 修改
     * @Date 10:42 2019/2/23
     * @Param [form]
     **/
    @RequestMapping("/edit")
    @ResponseBody
    public MessageEntity edit(RecordEntity entity, HttpServletRequest request) {
        logger.info("++++++++edit++++++" + entity);
        try {
            service.update(entity);
            this.operLogService.insertLog(OperLogEntity.EDIT_TYPE, "修改审讯：" + entity, ControllerTool.getLoginName(request), true, "办案场所");
        } catch (Exception e) {
            logger.error("Error on editing Record!", e);
            this.operLogService.insertLog(OperLogEntity.EDIT_TYPE, "修改审讯：" + entity, ControllerTool.getLoginName(request), false, "办案场所");
            return new MessageEntity().addCode(1).addIsError(true).addTitle("提示").addContent("更新失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("提示").addContent("更新成功!");
    }


    @RequestMapping(value = "/checkRoomBeforeStopRecord")
    @ResponseBody
    public MessageEntity checkRoomBeforeStopRecord(Long id, Long serialId, Long roomId, HttpServletRequest request) throws Exception {
        String currIp = ControllerTool.getClienIp(request);
        Map<String, String> recordRoom = service.getRecordIps(id);
        if (recordRoom != null && recordRoom.get("ips") != null) {
            if (recordRoom.get("ips").contains(currIp)) {

                return new MessageEntity().addIsError(false).addCallbackData("success");// 可以结束审讯
            } else {
                return new MessageEntity().addIsError(true).addContent("请到【" + recordRoom.get("name") + "】")
                        .addTitle("提示");
            }
        } else {
            return new MessageEntity().addIsError(true).addContent("无法查询到询/讯问记录对应的房间信息！");
        }
    }

    /**
     * @return com.zhixin.zhfz.common.entity.MessageEntity
     * @Author jzw
     * @Description 删除
     * @Date 10:42 2019/2/23
     * @Param [form]
     **/
    @RequestMapping("/stop")
    @ResponseBody
    public MessageEntity remove(Long id, Long serialId, int roomId, HttpServletRequest request) {
        logger.info("++++++++remove++++++RecordEntityId=" + id);
        String currIp = ControllerTool.getClienIp(request);
        Map<String, String> recordRoom = service.getRecordIps(id);
        if (recordRoom != null && recordRoom.get("ips") != null) {
            if (recordRoom.get("ips").contains(currIp)) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("roomId", roomId);
            } else {
                return new MessageEntity().addIsError(true).addContent("请到【" + recordRoom.get("name") + "】结束询/讯问！");
            }
        } else {
            return new MessageEntity().addIsError(true).addContent("无法查询到询/讯问记录对应的房间信息！");
        }
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("id", id);
            map.put("status", 3);
            service.updateStatus(map);

            map.put("roomId", roomId);
            String cameraNo = service.getRoomCameraNo(map);
            Worker worker = new Worker(id, cameraNo);
            worker.start();

            //this.operLogService.insertLog(OperLogEntity.DELETE_TYPE, "删除审讯：" + e , ControllerTool.getLoginName(request), true,"办案场所");
        } catch (Exception e) {
            logger.error("Error on removing Record!", e);
            //this.operLogService.insertLog(OperLogEntity.DELETE_TYPE, "删除审讯：" + map.get("id") , ControllerTool.getLoginName(request), false,"办案场所");
            return new MessageEntity().addCode(1).addIsError(true).addTitle("提示").addContent("强制结束失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("提示").addContent("强制结束成功!");
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
                RecordEntity entity = service.getRecordInfo(getRecordId());
                if (entity != null) {
                    int taskNo = 1;
                    Random random = new Random();
                    DownloadTaskEntity taskEntity = new DownloadTaskEntity();
                    taskEntity.setCameraNo(getCameraNo());
                    taskEntity.setTaskNo(taskNo);
                    taskEntity.setTaskType(1);// 审讯视频
                    taskEntity.setRecordId(getRecordId());
                    taskEntity.setSerialId(entity.getSerialId());
                    taskEntity.setStatus(0);
                    taskEntity.setStartTime(new Date(entity.getStartTime().getTime() - (5 * 60000)));
                    taskEntity.setEndTime(new Date(entity.getEndTime().getTime() + (5 * 60000)));
                    taskEntity.setHashValue(random.nextInt(10));
                    Map<String, Object> map = new HashMap<>();
                    map.put("recordId", getRecordId());
                    map.put("startTime", taskEntity.getStartTime());
                    map.put("endTime", taskEntity.getEndTime());
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


    /**
     * @return void
     * @Author jzw
     * @Description word文档预览
     * @Date 19:29 2019/2/21
     * @Param [id, fileName, response]
     **/
    @RequestMapping("/load")
    public void look(Long id, String fileName, HttpServletRequest request, HttpServletResponse response) {
        logger.info("++++++++look++++++RecordEnIdtity=  " + id);

        try {
            RecordEntity entity = service.getRecordInfo(id);
            if (fileName == null)
                fileName = entity.getUuid();
            setResponseHeader(response, fileName);
            OutputStream out = response.getOutputStream();
            out.write(entity.getWord());
            this.operLogService.insertLog(OperLogEntity.DELETE_TYPE, "下载智能审讯：" + id, ControllerTool.getLoginName(request), true, "办案场所");
        } catch (Exception e) {
            logger.error("Error on loading RecordTemplate!", e);
            this.operLogService.insertLog(OperLogEntity.DELETE_TYPE, "下载智能审讯：" + id, ControllerTool.getLoginName(request), false, "办案场所");

        }
    }

    /**
     * 设置响应头  文件类型为zip的   可以修改对应的后缀
     */
    public static void setResponseHeader(HttpServletResponse response, String fileName) {
        try {
            response.reset();// 清空输出流
            response.setContentType("application/octet-stream;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename="
                    + new String(fileName.getBytes("utf-8"), "8859_1")
                    + ".doc");
            response.addHeader("Pargam", "no-cache");
            response.addHeader("Cache-Control", "no-cache");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @RequestMapping(value = "/queryRecordData")
    @ResponseBody
    public MessageEntity queryRecordData(@RequestParam int recordType, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
//		String ipAndPort = getTiandyUrlByClientIP(request);
        String ipAndPort = "http://10.11.229.144:7000";
        Map<String, String> param = new HashMap<>();
        String url = ipAndPort + "/Easy7/rest/inquestNoteTemplate/query?id=" + recordType;
        logger.info("-------查询笔录模板------" + url);
        try {
            JSONObject template1 = HttpClientUtil.get(url, param);
            result.put("flageTypeData", template1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        url = ipAndPort + "/Easy7/rest/noteQuestionTemplate/query?inquestTypeId=" + recordType;
        logger.info("-------查询问答模板------" + url);
        try {
            JSONObject template2 = HttpClientUtil.get(url, param);
            result.put("recordTempData", template2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new MessageEntity().addIsError(false).addCallbackData(result);
    }


    private String getTiandyUrlByClientIP(HttpServletRequest request, Integer areaId) {
        String result = null;
        // 按照本地ip，获取areaId
        List<RoomEntity> rooms = areaService.listRoomByIp(ControllerTool.getClienIp(request), String.valueOf(areaId));
        if (rooms != null && rooms.size() > 0) {
            //int areaId = rooms.get (0).getInterrogateAreaId ();
            Map<String, String> map = personalConfigDetailService.listConfigDetailByAreaAndName(areaId, "天地伟业审讯平台");
            if (map != null && map.size() > 0) {
                result = "http://" + map.get("ip") + ":" + map.get("port");
            } else {
                result = "http://14.112.65.175:7000";
            }
        } else {
            result = "http://14.112.65.175:7000";
        }
        return result;
    }

    @RequestMapping(value = "/toRecordInfo")
    @ResponseBody
    public MessageEntity toRecordInfo(@RequestParam String recordId, @RequestParam int areaId,
                                      HttpServletRequest request) throws Exception {
        UserEntity user = ControllerTool.getUser(request);
        JSONObject object = new JSONObject();
        // object.put("userId", "admin");
        // object.put("userName", "admin");
        // object.put("password", "MTExMQ==");

        object.put("userId", user.getId());
        object.put("userName", user.getLoginName());
        object.put("password", Base64Util.encode(user.getPassword()));
        object.put("inquestId", recordId);

        String ipAndPort = getTiandyUrlByAreaTD(areaId, request);
        if (ipAndPort != null) {
            String url = ipAndPort + "/sx/templates/WfrmInquestManage_Test.html?" + object.toString();
            logger.info("-------查看案件信息------" + url);
            return new MessageEntity().addIsError(false).addCallbackData(url);
        } else {
            return new MessageEntity().addIsError(true).addTitle("错误信息").addContent("无效的配置(ip and port).");
        }
    }

    private String getTiandyUrlByAreaTD(int areaId, HttpServletRequest request) {
        String result = null;
        Map<String, String> map = personalConfigDetailService.listConfigDetailByAreaAndName(areaId, "天地伟业审讯平台");
        if (map != null && map.size() > 0) {
            result = "http://" + map.get("ip") + ":" + map.get("port");
        }
        return result;
    }

    @RequestMapping(value = "/toContiuneRecord")
    @ResponseBody
    public MessageEntity toContiuneRecord(@RequestParam String recordId, @RequestParam Integer areaId, HttpServletRequest request) throws Exception {
        UserEntity user = ControllerTool.getUser(request);

        JSONObject object = new JSONObject();
        // object.put("userId", "admin");
        // object.put("username", "admin");
        // object.put("password", "MTExMQ==");
        object.put("userId", user.getId());
        object.put("username", user.getLoginName());
        object.put("password", Base64Util.encode(user.getPassword()));
        object.put("inquestId", recordId);
        object.put("onInquest", true);
        String ipAndPort = getTiandyUrlByClientIP(request, areaId);
        if (ipAndPort != null) {
            String url = ipAndPort + "/sx/templates/WfrmEexternalInquest_test.html?" + object.toString();
            logger.info("-------继续审讯------" + url);
            /*OperLogEntity operLogEntity = new OperLogEntity ();
            operLogEntity.setType ("继续审讯");
            operLogEntity.setUser ("system");
            operLogEntity.setIsSuccess (true);
            operLogEntity.setContent ("=================继续审讯 url=================" + url);
            operLogService.insertTiandy (operLogEntity);*/
            this.operLogService.insertLog("天地伟业继续审讯", "=================天地伟业继续审讯 url=================" + url,
                    ControllerTool.getSessionInfo(request).getUser().getRealName(), true, OperLogEntity.SYSTEM_BACS);
            return new MessageEntity().addIsError(false).addCallbackData(url);
        } else {
            return new MessageEntity().addIsError(true).addTitle("错误信息").addContent("无效的配置(ip and port).");
        }
    }

    @RequestMapping(value = "/toStopRecord")
    @ResponseBody
    public MessageEntity toStopRecord(@RequestParam Long recordId, @RequestParam String uuid,
                                      @RequestParam Long serialId, @RequestParam int roomId, @RequestParam int areaId, HttpServletRequest request) throws Exception {

        try {
            try {//终止审讯先修改led
                LedEntity led = ledService.LedByRoomId(roomId);
                led.setIp(led.getIp());
                led.setPort(led.getPort());
                led.setColor("red");
                led.setContent(led.getRoomName());
                if (led.getContent().indexOf("未成年讯询问") > -1) {
                    led.setFontSize(12);
                    led.setLeft(4);
                    led.setTop(6);
                } else {
                    led.setFontSize(17);
                    led.setLeft(8);
                    led.setTop(2);
                }
                led.setWidth(200);
                led.setHeight(24);
                LedOldUtil.controlLED2010(led);
//                LedUtil.ledPlay(led.getRoomName(), led.getIp(), led.getPort(), 0);
            } catch (Exception e) {
                logger.info("++++++++led error 设置有问题++++++");
            }
            String ipAndPort = getTiandyUrlByClientIP(request, areaId);
            if (ipAndPort != null) {
                RecordEntity entity = new RecordEntity();
                entity.setId(recordId);
                entity.setStatus(3);
                service.updateStatusNew(entity);

                SerialEntity serialEntity = serialService.queryById(serialId);
                if (serialEntity.getStatus() < 11) {
                    serialService.changestatus(serialId, 7);
                }

                Map<String, Object> smap = new HashMap<>();
                smap.put("serialId", serialId);
                // 判断当前看押嫌疑人最近的一条看押记录所在的房间是不是询讯问室  询问室2 讯问室3
                // 如果是询讯问室，且该询讯问室的状态为1已预约 2占用，则置空该询讯问室的状态为空闲 0
                WaitingRecordEntity wre = waitingManageService.queryRoomStatusBySerialId(smap);

                if (wre != null) {
                    if (wre.getRoomTypeId() == 2 || wre.getRoomTypeId() == 3) {
                        roomService.updateRoomStatus(wre.getRoomId(), 0, "");//int roomId, int status, String roomName
                    }
                }


                JSONObject jsonObject = new JSONObject();
                jsonObject.put("inquestId", uuid);
                //String url = ipAndPort + "/Easy7/rest/inquest/endInquest";
                String url = "http://14.27.2.251:20183/manager/record/inquest/endInquest";
                logger.info("-------终止案件信息------" + url);
                Map<String, String> param = new HashMap<>();
                param.put("param", jsonObject.toString());
                HttpClientUtil.get(url, param);
                //修改审讯室状态为占用
//			if (roomId>0){
//				roomService.updateRoomStatus(roomId, 0);
//			}


                return new MessageEntity().addIsError(false).addContent("终止成功..");


            } else {
                return new MessageEntity().addIsError(true).addTitle("错误信息").addContent("无效的配置(ip and port).");
            }
        } catch (Exception e) {
            return new MessageEntity().addIsError(false).addTitle("终止失败..");
        }
    }

}
