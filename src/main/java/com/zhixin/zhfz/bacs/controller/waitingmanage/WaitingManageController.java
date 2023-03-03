package com.zhixin.zhfz.bacs.controller.waitingmanage;

import com.alibaba.fastjson.JSON;
import com.zhixin.zhfz.bacs.dao.waitingmanage.IWaitingManageMapper;
import com.zhixin.zhfz.bacs.entity.*;
import com.zhixin.zhfz.bacs.form.WaitingRecordForm;
import com.zhixin.zhfz.bacs.services.area.IAreaService;
import com.zhixin.zhfz.bacs.services.arraign.IArraignService;
import com.zhixin.zhfz.bacs.services.person.IPersonService;
import com.zhixin.zhfz.bacs.services.record.IRecordService;
import com.zhixin.zhfz.bacs.services.room.IRoomService;
import com.zhixin.zhfz.bacs.services.serial.ISerialService;
import com.zhixin.zhfz.bacs.services.waitingmanage.IWaitingManageService;
import com.zhixin.zhfz.common.common.AsynUtil;
import com.zhixin.zhfz.common.common.Base64Util;
import com.zhixin.zhfz.common.common.HttpClientUtil;
import com.zhixin.zhfz.common.common.IdcardUtils;
import com.zhixin.zhfz.common.entity.*;
import com.zhixin.zhfz.common.entity.CombogridEntity;
import com.zhixin.zhfz.common.services.Icase.ICaseService;
import com.zhixin.zhfz.common.services.combogrid.ICombogridService;
import com.zhixin.zhfz.common.services.operLog.IOperLogService;
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
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/zhfz/bacs/waitingmanage")
public class WaitingManageController {
    @Autowired
    private IWaitingManageService waitingManageService;
    @Autowired
    private IWaitingManageMapper waitingManageMapper;
    @Autowired
    private ICombogridService combogridService;
    @Autowired
    private IRoomService roomService;
    @Autowired
    private IOperLogService operLogService;
    @Autowired
    private IRecordService recordService;
    @Autowired
    private ISerialService serialService;
    @Autowired
    private IArraignService arraignService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IPersonService personService;
    @Autowired
    private ICaseService caseService;
    @Autowired
    private IAreaService areaService;


    private static final Logger logger = LoggerFactory.getLogger(WaitingManageController.class);

    @RequestMapping(value = "/personRoomCountList")
    @ResponseBody
    public Map<String, Object> personRoomCountList(@RequestParam Map<String, Object> map, HttpServletRequest request) {
        List<WaitingPersonCountEntity> list = null;

        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        if (RoleEntity.DATA_AUTH_SELFAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 本办案场所rm.area_id
            map.put("dataAuth", "rm.area_id = " + ControllerTool.getCurrentAreaID(request));
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 本办案场所及下级办案场所
            map.put("dataAuth", "rm.area_id " + sessionInfo.getCurrentAndSubAreaInStr());
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 上级办案场所及其下级办案场所
            map.put("dataAuth", "rm.area_id " + sessionInfo.getSuperAndSubAreaInStr());
        } /*else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 本部门及下级部门
            map.put("dataAuth","rm.op_pid like " + sessionInfo.getCurrentAndSubOrgPid());
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 上级部门及下级部门
            map.put("dataAuth","rm.op_pid like " + sessionInfo.getSuperAndSubOrgPid());
        } else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuth(request))) {
            // 本部门
            map.put("dataAuth","rm.op_pid = " + sessionInfo.getCurrentOrgPid());
        }*/ else if (RoleEntity.DATA_AUTH_FULL == (ControllerTool.getRoleDataAuth(request))) {
            //全部
        } else if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuth(request))) {
            //本人(此处设置成本办案场所rm.area_id)
            map.put("dataAuth", " rm.area_id=" + ControllerTool.getCurrentAreaID(request));
        }

        list = waitingManageService.personRoomCountList(map);
        Map<String, Object> result = new HashMap<String, Object>();
        List<List<WaitingPersonCountEntity>> countlist = null;
        if (list != null) {
            countlist = handleList(list);
        }
        result.put("list", countlist);
        return result;
    }

    /**
     * 功能房的处理，便于前台展示
     */
    private List<List<WaitingPersonCountEntity>> handleList(List<WaitingPersonCountEntity> list) {
        List<WaitingPersonCountEntity> tempList = new ArrayList<WaitingPersonCountEntity>();
        List<List<WaitingPersonCountEntity>> countlist = new ArrayList<List<WaitingPersonCountEntity>>();
        int prevareaid = -1;
        for (int i = 0; i < list.size(); i++) {
            WaitingPersonCountEntity e = list.get(i);
            if (prevareaid == e.getAreaId()) {
                tempList.add(e);
            } else {
                prevareaid = e.getAreaId();
                tempList = new ArrayList<WaitingPersonCountEntity>();
                tempList.add(e);
                countlist.add(tempList);
            }
        }
        return countlist;
    }

    //查询统计办案场所各个进度的人数
    @RequestMapping(value = "/selectPeopleCount")
    @ResponseBody
    public Map<String, Object> selectPeopleCount(@RequestParam Map<String, Object> map, HttpServletRequest request) {

        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        if (RoleEntity.DATA_AUTH_SELFAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 本办案场所rm.area_id
            map.put("dataAuth", "a.area_id = " + ControllerTool.getCurrentAreaID(request));
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 本办案场所及下级办案场所
            map.put("dataAuth", "a.area_id " + sessionInfo.getCurrentAndSubAreaInStr());
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 上级办案场所及其下级办案场所
            map.put("dataAuth", "a.area_id " + sessionInfo.getSuperAndSubAreaInStr());
        } /*else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 本部门及下级部门
            map.put("dataAuth","r.op_pid like " + sessionInfo.getCurrentAndSubOrgPid());
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 上级部门及下级部门
            map.put("dataAuth","r.op_pid like " + sessionInfo.getSuperAndSubOrgPid());
        } else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuth(request))) {
            // 本部门
            map.put("dataAuth","r.op_pid = " + sessionInfo.getCurrentOrgPid());
        }*/ else if (RoleEntity.DATA_AUTH_FULL == (ControllerTool.getRoleDataAuth(request))) {
            //全部
        } else if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuth(request))) {
            //本人(此处设置成本办案场所rm.area_id)
            map.put("dataAuth", "a.area_id = " + ControllerTool.getCurrentAreaID(request));
        }
        Map reslutMap = new HashMap();
        //看押中的人数
        //Integer countKy = waitingManageService.selectPeopleCountTypeKy(map);
        //审讯中的人数
        //Integer countSx  =waitingManageService.selectPeopleCountTypeSx(map);
        //侯问室总人数
        Integer countWaitingRoom = waitingManageService.selectCountWaitingRoom(map);
        //剩余容积
        /*if(waitingManageService.selectPeopleCountTypeRj(map)!=null){
            Integer countRj  =waitingManageService.selectPeopleCountTypeRj(map)-countKy;
            reslutMap.put("countRj",countRj);
        }else{
            reslutMap.put("countRj",0);
        }*/
        //安检/信采/存物
        //Integer countOther = waitingManageService.selectPeopleCountTypeOther(map);
        //临时出区的人数
        //Integer countLs  =waitingManageService.selectPeopleCountTypeLs(map);
        //reslutMap.put("countKy",countKy);
        //reslutMap.put("countSx",countSx);
        // 重点嫌疑人总人数
        Integer countZhongdian = waitingManageService.selectCountZhongdian(map);
        // 未成年人总人数
        Integer countIsJuvenile = waitingManageService.selectCountIsJuvenile(map);
        // 传染病总人数
        Integer countIsDisease = waitingManageService.selectCountIsDisease(map);
        // 涉毒总人数
        Integer countshedu = waitingManageService.selectCountshedu(map);
        // 孕妇总人数
        Integer countIsGravida = waitingManageService.selectCountIsGravida(map);
        reslutMap.put("countWaitingRoom", countWaitingRoom);
        reslutMap.put("countIsJuvenile", countIsJuvenile);
        reslutMap.put("countIsDisease", countIsDisease);
        reslutMap.put("countIsGravida", countIsGravida);
        reslutMap.put("countshedu", countshedu);
        reslutMap.put("countZhongdian", countZhongdian);
        //reslutMap.put("countLs",countLs);
        //reslutMap.put("countOther",countOther);
        return reslutMap;
    }

    // 获取可看押嫌疑人入区编号、姓名、身份证号码
    @RequestMapping(value = "/getAllDetainSuspectSerialNo")
    @ResponseBody
    public List<CombogridEntity> getAllDetainSuspectSerialNo(@RequestParam Map<String, Object> params,
                                                             HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<CombogridEntity> list = new ArrayList<CombogridEntity>();
        params.put("dataAuth", " ints.area_id=" + params.get("areaId")
                + " and ints.status>0 and ints.status<>6 and ints.status<11 and ints.status<>3");
        list = this.combogridService.getSuspectSerialNo(params);
        return list;
    }

    /**
     * 功能室下拉框分组展示
     */
    @RequestMapping(value = "/groupRoom")
    @ResponseBody
    public List<WaitingPersonCountEntity> grouproom(@RequestParam Map<String, Object> map) {
        List<WaitingPersonCountEntity> list = waitingManageService.personRoomCountList(map);
        return list;
    }

    /**
     * 看押，进入候问室
     */
    @RequestMapping("/insert")
    @ResponseBody
    public MessageEntity insert(@RequestBody WaitingRecordForm form, HttpServletRequest request) {
        // 判断当前看押嫌疑人最近的一条看押记录所在的房间是不是询讯问室  询问室2 讯问室3
        // 如果是询讯问室，且该询讯问室的状态为1已预约 2占用，则置空该询讯问室的状态为空闲 0
        Map<String, Object> sMap = new HashMap<String, Object>();
        sMap.put("serialId", form.getSerialId());
        WaitingRecordEntity wre = waitingManageService.queryRoomStatusBySerialId(sMap);
        if (wre != null) {

            if (wre.getRoomTypeId() != null && wre.getRoomId() != null) {
                if (wre.getRoomTypeId() == 2 || wre.getRoomTypeId() == 3) {
                    roomService.updateRoomStatus(wre.getRoomId(), 0, "");//int roomId, int status, String roomName
                }
            }
            //如果是审讯室，并且inquestId不等于null，调用天地结束办案的接口
            if (wre.getInquestId() != null) {
                String url = "http://14.112.65.176:7000/Easy7/rest/webserviceSx/endInquestKL";
                logger.info("结束办案url"+url);
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("inquestId",wre.getInquestId());
                    jsonObject.put("accessCode","beijingchaoyangduijie");
                    JSONObject result = HttpClientUtil.get(url, jsonObject);
                    logger.info("天地结束办案result"+result);
                    int ret = (int) result.get("ret");
                    if (ret == 0) {
                        logger.info("天地结束办案接口成功" + ret);
                    } else {
                        logger.info("天地结束办案接口异常" + ret);
                        this.operLogService.insertTdLog(form.getSerialId(), "结束办案接口错误", "结束办案接口错误：" + ret, "");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.info("天地结束办案接口异常" + e);
                    this.operLogService.insertTdLog(form.getSerialId(), "结束办案接口错误", "结束办案接口错误：" + e, "");

                }
            }
        }

        String roomName = "";
        WaitingRecordEntity entity = new WaitingRecordEntity();
        entity.setSerialId(form.getSerialId());
        entity.setPersonId(form.getPersonId());
        entity.setInUserId(form.getSendUserId1());
        entity.setAreaId(form.getAreaId());
        entity.setCaseId(form.getCaseId());
        entity.setRoomId(form.getRoommId());
        entity.setSendUserId1(form.getSendUserId1());
        RoomEntity room = roomService.getRoomById(new Long(form.getRoommId()));
        roomName = room.getName();
        try {
            WaitingRecordEntity updateEntity = waitingManageService.queryBySerialId(form.getSerialId().toString());
            if (updateEntity != null && (updateEntity.getOutTime() == null || ("").equals(updateEntity.getOutTime()))) {
                waitingManageService.updateWaitingRecord(updateEntity);
            }
            //更新show
            waitingManageService.updateShowBySerialId(form.getSerialId().toString());
            entity.setOpPid(ControllerTool.getSessionInfo(request).getCurrentOrg().getPid());
            entity.setOpUserId(ControllerTool.getUserID(request));
            waitingManageService.insert(entity);
            //更新下载记录已放到 waitingManageService
            this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "候问室看押" + entity, "system", true, OperLogEntity.SYSTEM_BACS);


            /* 添加通知和待办事项 */
            UserEntity uo = new UserEntity();
            List<UserEntity> users = new ArrayList<UserEntity>();

            try {
                if (ControllerTool.getCurrentAreaID(request) == 1) {
                    uo = userService.getUserByID(ControllerTool.getUserID(request));
                    users = userService.getUsersByOrgAndType(uo.getOrganizationId(), "管理中队");
                } else {
                    uo = userService.getUserByID(ControllerTool.getUserID(request));
                    users = userService.getUsersByOrgAndType(uo.getOrganizationId(), "管理中队");
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if (users != null && users.size() > 0) {
                for (int i = 0; i < users.size(); i++) {
                    /* 添加通知 */
                    PersonEntity personById = personService.getPersonById(form.getPersonId());
                }
            }
        } catch (Exception e) {
            logger.error("Error on insert WaitingRecord!", e);
            this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "候问室看押" + entity, "system", false, OperLogEntity.SYSTEM_BACS);
            return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("看押失败!");
        }

        return new MessageEntity().addCode(1).addIsError(false).addTitle("提醒").addContent("请到" + roomName);
    }

    /**
     * 查询候问室嫌疑人
     */
    @RequestMapping(value = "/listSuspect")
    @ResponseBody
    public JSON list(@RequestParam Map<String, Object> params, HttpServletRequest request) {
        List<WaitingRecordEntity> list = new ArrayList<>();
        try {
            list = waitingManageService.selectAllSuspectOfRoom(params);
        } catch (Exception e) {
            return (JSON) JSON.toJSON("error");
        }
        return (JSON) JSON.toJSON(list);
    }

    /**
     * 更改嫌疑人的侯问室
     */
    @RequestMapping(value = "/changeRoom")
    @ResponseBody
    public String changeRoom(@RequestParam Map<String, Object> params, HttpServletRequest request) {
        try {
            waitingManageService.changeRoom(params);
        } catch (Exception e) {
            return "updateError";
        }
        return "updateSuccess";
    }

    /**
     * 保存其他去向
     */
    @RequestMapping("/saveDirection")
    @ResponseBody
    public MessageEntity saveDirection(String id, String direction, String serialId1, String roomId3, String user1, String user2, HttpServletRequest request) {
        System.err.println(direction);
        try {
            Map param = new HashMap();
            param.put("id", id);
            param.put("direction", direction);
            param.put("user1", user1);
            param.put("user2", user2);
            waitingManageService.saveDirection(param);
            // 更新room状态为已预约
            ArraignEntity entity = new ArraignEntity();
            if (!"".equals(roomId3) && roomId3 != null) {
                entity.setId(Long.valueOf(roomId3));
                arraignService.updateRoom(entity);
                //判断是否去审讯室，去的话，调用天地接口，进行数据回传
                SerialEntity serialEntity = serialService.queryById(Long.valueOf(serialId1));
                UserEntity user = ControllerTool.getUser(request);
                // 设置用户名密码
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("userId", "user"+user.getId());
                jsonObject.put("username", user.getLoginName());
                jsonObject.put("password", Base64Util.encode(user.getPassword()));
                jsonObject.put("accessCode", "beijingchaoyangduijie");//接口权限验证码
                // 获取案件信息
                CaseEntity caseEntity = caseService.getCaseById(serialEntity.getCaseId());
                Map<String, Object> archiveMap = new HashMap<>();
                String caseName = caseEntity.getAjmc();
                archiveMap.put("archiveName", caseName);
                archiveMap.put("archiveNum", caseEntity.getAjbh());
                archiveMap.put("archiveDept", "dept"+user.getOrganizationId());
                archiveMap.put("archiveType", caseEntity.getAjlx() == 1 ? 3 : 1);
                archiveMap.put("archivePlace", caseEntity.getAfdd());
                archiveMap.put("archiveInstruction", caseEntity.getZyaq());
                archiveMap.put("userIdList", "");
                jsonObject.put("archive", archiveMap);
                PersonEntity personEntity = personService.getPersonById(serialEntity.getPersonId());
                Map<String, Object> suspectPersonMap = new HashMap<>();
                suspectPersonMap.put("name", personEntity.getName());// personEntity.getName()
                String sex = personEntity.getSex() == null ? "0" : personEntity.getSex().toString();
                suspectPersonMap.put("sex", sex);
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
                inquestMap.put("inquestRoomId", roomService.getRoomById(Long.valueOf(roomId3)).getTdRoomId());
                inquestMap.put("inquestType", 2);
                inquestMap.put("detectPerson1", user.getLoginName());
                inquestMap.put("detectDept1", user.getOrganizationId());
                inquestMap.put("detectPersonCode1", "");
                inquestMap.put("detectDeptCode1", "");
                inquestMap.put("detectPerson2", "");
                inquestMap.put("detectDept2", "");
                inquestMap.put("detectPersonCode2", "");
                inquestMap.put("detectDeptCode2", "");
                jsonObject.put("inquest", inquestMap);
                JSONObject ftpJsonObject = new JSONObject();
                ftpJsonObject.put("ftpIp", PropertyUtil.getContextProperty("ftp.ip"));
                ftpJsonObject.put("ftpPort", PropertyUtil.getContextProperty("ftp.port"));
                ftpJsonObject.put("ftpUserName", PropertyUtil.getContextProperty("ftp.user"));
                ftpJsonObject.put("ftpPassword", PropertyUtil.getContextProperty("ftp.pwd"));
                String serialNo = serialEntity.getSerialNo();//serialService.getSerialNoById (entity.getInterrogateSerialId ()).getSerialNo ();
                String dirUrl = "/Inquest/" + Utils.getDateFromSerialNO(serialNo) + "/";
                AsynUtil.getInstance().createFTPDirectory(dirUrl);
//                dirUrl += serialEntity.getUuid()+ "/";
                String uuid= Utils.getUUId();
                dirUrl += uuid+ "/";
                AsynUtil.getInstance().createFTPDirectory(dirUrl);
                ftpJsonObject.put("ftpPathInquest", dirUrl);
                jsonObject.put("ftpInfo", ftpJsonObject);
                logger.info("天地开始办案param"+jsonObject.toString());
                Map<String, String> params = new HashMap<String, String>();
                params.put("param", jsonObject.toString());
                String url = "http://14.112.65.176:7000/Easy7/rest/webserviceSx/startInquestKL";
                logger.info("天地开始办案url"+url);
                try {
                    JSONObject json = HttpClientUtil.get(url, params);
                    logger.info("天地开始办案result"+json);
                    int ret = (int) json.get("ret");
                    if (ret == 0) {
                        String content = json.get("content")+"";
                        logger.info("天地开始办案content"+content);
                        WaitingRecordEntity waitingRecordEntity= new WaitingRecordEntity();
                        waitingRecordEntity.setInquestId(content);
                        waitingRecordEntity.setRecordId(Integer.valueOf(id));
                        waitingRecordEntity.setRecordUuid(uuid);
                        waitingManageMapper.updateWaitingRecord(waitingRecordEntity);
                        logger.info("天地开始办案接口成功" + ret);
                    } else {
                        logger.info("天地开始办案接口异常" + ret);
                        this.operLogService.insertTdLog(Long.valueOf(serialId1), "开始办案接口接口错误", "开始办案接口错误：" + ret, jsonObject.toString());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.info("天地开始办案接口异常" + e);
                    this.operLogService.insertTdLog(Long.valueOf(serialId1), "开始办案接口接口错误", "开始办案接口错误：" + e, jsonObject.toString());

                }

            }
            //等候管理--选择去向--4结束侯问
            serialService.updateStatusById(Long.valueOf(serialId1), 4);

            SerialEntity serialEntity = new SerialEntity();
            serialEntity.setId(Long.valueOf(serialId1));
            serialEntity = serialService.getSerialById(serialEntity);
            /* 添加通知和待办事项 */
            UserEntity uo = new UserEntity();
            List<UserEntity> users = new ArrayList<UserEntity>();

            try {
                if (ControllerTool.getCurrentAreaID(request) == 1) {
                    uo = userService.getUserByID(ControllerTool.getUserID(request));
                    users = userService.getUsersByOrgAndType(uo.getOrganizationId(), "管理中队");
                } else {
                    uo = userService.getUserByID(ControllerTool.getUserID(request));
                    users = userService.getUsersByOrgAndType(uo.getOrganizationId(), "管理中队");
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if (users != null && users.size() > 0) {
                for (int i = 0; i < users.size(); i++) {
                    /* 添加通知 */
                    PersonEntity personById = personService.getPersonById(serialEntity.getPersonId());
                    System.err.println(personById);
                }
            }
        } catch (Exception e) {
            logger.error("", e);
            return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("保存失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("提醒").addContent("保存成功!");
    }

    /**
     * 返回，进入候问室
     */
    @RequestMapping("/goBack")
    @ResponseBody
    public MessageEntity goBack(@RequestParam Map<String, Object> param, HttpServletRequest request) {
        WaitingRecordEntity entity = new WaitingRecordEntity();
        try {
            WaitingRecordEntity oldEntity = waitingManageService.getWaitingRecordById(param.get("id").toString());
            //查询判断嫌疑人是否在审讯中
            Map<String, Object> map = new HashMap<>();
            map.put("serialId", oldEntity.getSerialId());
            // 判断当前看押嫌疑人最近的一条看押记录所在的房间是不是询讯问室  询问室2 讯问室3
            // 如果是询讯问室，且该询讯问室的状态为1已预约 2占用，则置空该询讯问室的状态为空闲 0
            WaitingRecordEntity wre = waitingManageService.queryRoomStatusBySerialId(map);
            if (wre != null) {
                if (wre.getRoomTypeId() != null && wre.getRoomId() != null) {
                    if (wre.getRoomTypeId() == 2 || wre.getRoomTypeId() == 3) {
                        roomService.updateRoomStatus(wre.getRoomId(), 0, "");//int roomId, int status, String roomName
                    }
                }
                //如果是审讯室，并且inquestId不等于null，调用天地结束办案的接口
                if (wre.getInquestId() != null) {
                    String url = "http://14.112.65.176:7000/Easy7/rest/webserviceSx/endInquestKL";
                    logger.info("结束办案url"+url);
                    try {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("inquestId",wre.getInquestId());
                        jsonObject.put("accessCode","beijingchaoyangduijie");
                        JSONObject result = HttpClientUtil.get(url, jsonObject);
                        logger.info("天地结束办案result"+result);
                        int ret = (int) result.get("ret");
                        if (ret == 0) {
                            logger.info("天地结束办案接口成功" + ret);
                        } else {
                            logger.info("天地结束办案接口异常" + ret);
                            this.operLogService.insertTdLog(oldEntity.getSerialId(), "结束办案接口错误", "结束办案接口错误：" + ret, jsonObject.toString());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        logger.info("天地结束办案接口异常" + e);
                        this.operLogService.insertTdLog(oldEntity.getSerialId(), "结束办案接口错误", "结束办案接口错误：" + e, "");

                    }
                }
            }
            List<RecordEntity> recordEntities = recordService.selectBySerialId(map);
            if (recordEntities != null && recordEntities.size() > 0) {
                for (int i = 0; i < recordEntities.size(); i++) {
                    RecordEntity recordEntity = recordEntities.get(i);
                    if (recordEntity.getEndTime() == null || recordEntity.getEndTime().equals("")) {
                        return new MessageEntity().addCode(1).addIsError(false).addTitle("提醒").addContent("该嫌疑人正在审讯中,无法返回侯问室!");
                    }
                }
            }
            oldEntity.setStatus(1);
            logger.info("goBack===oldEntity=====" + oldEntity.toString());
            waitingManageService.updateStatusByIdForGoBack(param.get("id").toString());

            entity.setSerialId(oldEntity.getSerialId());
            entity.setPersonId(oldEntity.getPersonId());
            entity.setInUserId(oldEntity.getInUserId());
            entity.setStatus(0);
            entity.setAreaId(oldEntity.getAreaId());
            entity.setCaseId(oldEntity.getCaseId());
            entity.setRoomId(oldEntity.getRoomId());
            entity.setSendUserId1(oldEntity.getSendUserId1());
            logger.info("goBack========" + entity.toString());
            entity.setOpPid(ControllerTool.getSessionInfo(request).getCurrentOrg().getPid());
            entity.setOpUserId(ControllerTool.getUserID(request));
            waitingManageService.insert(entity);
            this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "候问室返回" + entity, "system", true, OperLogEntity.SYSTEM_BACS);
        } catch (Exception e) {
            logger.error("Error on insert WaitingRecord!", e);
            this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "候问室返回" + entity, "system", false, OperLogEntity.SYSTEM_BACS);
            return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("返回失败!");
        }

        return new MessageEntity().addCode(1).addIsError(false).addTitle("提醒").addContent("返回成功!");
    }

    /**
     * 查询记录
     */
    @RequestMapping("/listRecord")
    @ResponseBody
    public Map<String, Object> listRecord(@RequestParam Map<String, Object> param, HttpServletRequest request) {
        Map<String, Object> map = ControllerTool.mapFilter(param);
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        if (RoleEntity.DATA_AUTH_SELFAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 本办案场所rm.area_id
            map.put("dataAuth", "record.area_id = " + ControllerTool.getCurrentAreaID(request));
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 本办案场所及下级办案场所
            map.put("dataAuth", "record.area_id " + sessionInfo.getCurrentAndSubAreaInStr());
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 上级办案场所及其下级办案场所
            map.put("dataAuth", "record.area_id " + sessionInfo.getSuperAndSubAreaInStr());
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 本部门及下级部门
            map.put("dataAuth", "record.op_pid like " + sessionInfo.getCurrentAndSubOrgPid());
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 上级部门及下级部门
            map.put("dataAuth", "record.op_pid like " + sessionInfo.getSuperAndSubOrgPid());
        } else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuth(request))) {
            // 本部门
            map.put("dataAuth", "record.op_pid = " + sessionInfo.getCurrentOrgPid());
        } else if (RoleEntity.DATA_AUTH_FULL == (ControllerTool.getRoleDataAuth(request))) {
            //全部
        } else if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuth(request))) {
            //本人(本办案场所rm.area_id)
            map.put("dataAuth", "record.area_id = " + ControllerTool.getCurrentAreaID(request));
        }
        List<WaitingRecordEntity> list = waitingManageService.listRecord(map);
        Integer count = waitingManageService.listRecordCount(map);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", count);
        result.put("rows", list);
        return result;
    }

    /**
     * 同案看押侯问室
     *
     * @param map
     * @return
     */
    @RequestMapping("/queryRoomSameCase")
    @ResponseBody
    public int queryRoomSameCase(@RequestParam Map<String, Object> map) {
        int result = -1;
        try {

            List<WaitingPersonCountEntity> sameCaseRoomList = waitingManageService.queryRoomSameCase(map);
            if (sameCaseRoomList != null && sameCaseRoomList.size() > 0) {
                result = sameCaseRoomList.size();
                AlarmEntity entity = new AlarmEntity();
                entity.setAreaId(map.get("areaId").toString());
                entity.setRoomId(Integer.parseInt(map.get("roomId").toString()));
                entity.setAlarmType(12);
                entity.setAlarmName("同案看押告警");
                entity.setAlarmType(12);
                entity.setStatus(0);
            }

        } catch (Exception e) {
            logger.error("checkSameCaseRoom", e);
        }
        return result;
    }

    /**
     * 未成年人
     *
     * @param params
     * @param request
     * @return
     */
    @RequestMapping("/listIsJuvenileAll")
    @ResponseBody
    public List<WaitingRecordEntity> listIsJuvenileAll(@RequestParam Map<String, Object> params, HttpServletRequest request) {
        Map<String, Object> map = ControllerTool.mapFilter(params);
        return waitingManageService.selectIsJuvenileAll(map);
    }

    /**
     * 传染病
     *
     * @param params
     * @param request
     * @return
     */
    @RequestMapping("/listIsDiseaseAll")
    @ResponseBody
    public List<WaitingRecordEntity> listIsDiseaseAll(@RequestParam Map<String, Object> params, HttpServletRequest request) {
        Map<String, Object> map = ControllerTool.mapFilter(params);
        return waitingManageService.selectIsDiseaseAll(map);
    }

    /**
     * 孕妇
     *
     * @param params
     * @param request
     * @return
     */
    @RequestMapping("/listIsGravidaAll")
    @ResponseBody
    public List<WaitingRecordEntity> listIsGravidaAll(@RequestParam Map<String, Object> params, HttpServletRequest request) {
        Map<String, Object> map = ControllerTool.mapFilter(params);
        return waitingManageService.selectIsGravidaAll(map);
    }

    /**
     * 侯问室
     *
     * @param params
     * @param request
     * @return
     */
    @RequestMapping("/listWaitingRoomAll")
    @ResponseBody
    public List<WaitingRecordEntity> listWaitingRoomAll(@RequestParam Map<String, Object> params, HttpServletRequest request) {
        Map<String, Object> map = ControllerTool.mapFilter(params);
        return waitingManageService.selectWaitingRoomAll(map);
    }

    /**
     * 涉毒
     *
     * @param params
     * @param request
     * @return
     */
    @RequestMapping("/listsheduAll")
    @ResponseBody
    public List<WaitingRecordEntity> listsheduAll(@RequestParam Map<String, Object> params, HttpServletRequest request) {
        Map<String, Object> map = ControllerTool.mapFilter(params);
        return waitingManageService.selectsheduAll(map);
    }

    /**
     * 重点嫌疑人
     *
     * @param params
     * @param request
     * @return
     */
    @RequestMapping("/listZhongdianAll")
    @ResponseBody
    public List<WaitingRecordEntity> listZhongdianAll(@RequestParam Map<String, Object> params, HttpServletRequest request) {
        Map<String, Object> map = ControllerTool.mapFilter(params);
        return waitingManageService.selectZhongdianAll(map);
    }

    /**
     * 推荐功能室
     *
     * @param map
     * @return
     */
    @RequestMapping("/recommendRoom")
    @ResponseBody
    public int recommendRoom(@RequestParam Map<String, Object> map) {
        int result = -1;
        try {
            result = waitingManageService.queryLastRoom(map);
//    		if(result == 0 || result == -99){//-99预约时所填
//    		   result = waitingManageService.waitingRecordRoomRecommend(map);
//    		}
        } catch (Exception e) {
            logger.error("recommendRoom", e);
        }
        return result;
    }


    @RequestMapping("/queryRecord")
    @ResponseBody
    public List<Map<String, Object>> queryRecord(@RequestParam int sId, HttpServletRequest request) {

        List<Map<String, Object>> list = waitingManageService.queryRecord(sId);

        return list;
    }

}

