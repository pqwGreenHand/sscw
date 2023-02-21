package com.zhixin.zhfz.bacs.controller.order;


import com.zhixin.zhfz.bacs.entity.*;
import com.zhixin.zhfz.bacs.form.NvrForm;
import com.zhixin.zhfz.bacs.form.OrdeAddPersonForm;
import com.zhixin.zhfz.bacs.form.OrderRequestForm;
import com.zhixin.zhfz.bacs.form.OrderRequestPersonForm;
import com.zhixin.zhfz.bacs.services.area.IAreaService;
import com.zhixin.zhfz.bacs.services.jzPerson.IJzPersonService;
import com.zhixin.zhfz.bacs.services.order.IOrderPersonService;
import com.zhixin.zhfz.bacs.services.order.IOrderRequestService;
import com.zhixin.zhfz.bacs.services.order.IOrderStatusService;
import com.zhixin.zhfz.bacs.services.person.IPersonService;
import com.zhixin.zhfz.bacs.services.serial.ISerialService;
import com.zhixin.zhfz.bacsapp.entity.InformationEntity;
import com.zhixin.zhfz.bacsapp.services.Information.IInformationService;
import com.zhixin.zhfz.common.common.HttpClientUtil;
import com.zhixin.zhfz.common.dao.common.ICommonCommonMapper;
import com.zhixin.zhfz.common.entity.*;
import com.zhixin.zhfz.common.services.notice.IMyNoticeService;
import com.zhixin.zhfz.common.services.operLog.IOperLogService;
import com.zhixin.zhfz.common.services.organiztion.IOrganizationService;
import com.zhixin.zhfz.common.services.user.IUserService;
import com.zhixin.zhfz.common.utils.ControllerTool;
import com.zhixin.zhfz.common.utils.PropertyUtil;
import com.zhixin.zhfz.common.utils.SMSSender;
import com.zhixin.zhfz.sacw.form.UserNoSearchForm;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 预约信息
 *
 * @author
 */
@Controller
@RequestMapping("/zhfz/bacs/order")
public class OrderRequestController {
    private static final Logger logger = LoggerFactory.getLogger(OrderRequestController.class);
    @Autowired
    private IOrderRequestService orderRequestService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IOperLogService operLogService;
    @Autowired
    private IPersonService personService;
    @Autowired
    private IOrderPersonService orderPersonService;
    @Autowired
    private IOrderStatusService orderStatusService;
    @Autowired
    private IOrganizationService organizationService;
    @Autowired
    private IMyNoticeService noticeService;
    @Autowired
    private ISerialService serialService;
    @Autowired
    private IAreaService areaService;
    @Autowired
    private IJzPersonService jzPersonService;
    @Autowired
    private IInformationService iInformationService;
    @Resource
    private ICommonCommonMapper commonCommonMapper;

    //系统状态
    @RequestMapping(value = "/queryXtStatus")
    @ResponseBody
    public String queryXtStatus(HttpServletRequest request) {
        try {
            String status = commonCommonMapper.queryXtStats();
            return status;
        } catch (Exception e) {
            logger.error("Error on edit orderrequest!", e);
            return "查询失败!";
        }

    }

    //进行审核
    @RequestMapping(value = "/removeOrderRequest")
    @ResponseBody
    public MessageEntity removeOrderRequest(@RequestParam("orderRequestId") Integer orderRequestId, HttpServletRequest request) {
        System.err.println("reach---" + orderRequestId);
        try {
//            人员信息暂时不删除，同一个人可能关联其他的预约
            orderRequestService.deleteById(orderRequestId);
            orderPersonService.deleteByOrderRequestId(orderRequestId);
            orderStatusService.deleteByOrderRequestId(orderRequestId);
        } catch (Exception e) {
            logger.error("Error on edit orderrequest!", e);
            return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("删除失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("消息").addContent("删除成功!");
    }

    //一键进行审核
    @RequestMapping(value = "/orderYjSh")
    @ResponseBody
    public MessageEntity orderYjSh(@RequestParam("ids") String ids, @RequestParam("shzt") Integer shzt, @RequestParam("shjg") String shjg, HttpServletRequest request) {
        try {
            logger.info(ids + "");
            String[] split = ids.split(",");
            for (int i = 0; i < split.length; i++) {
                String s = split[i];
                String[] split1 = s.split("@");
                String orderRequestId = "";
                String orderUserId = "";
                Integer orderPersonId = null;
                if (split1 != null) {
                    orderPersonId = Integer.parseInt(split1[0]);
                    orderRequestId = split1[1];
                    orderUserId = split1[2];
                    OrderPersonEntity entity = new OrderPersonEntity();
                    Map<String, Object> map = new HashMap<>();
                    map.put("orderRequestId", orderRequestId);
                    List<OrderPersonEntity> list = orderPersonService.orderPersonList(map);
                    Integer sendId = ControllerTool.getUser(request).getId();
                    String pid = ControllerTool.getSessionInfo(request).getCurrentOrg().getPid();
                    InformationEntity inform = new InformationEntity();
                    inform.setSenderId(Long.parseLong(sendId + ""));
                    inform.setReceiverId(Long.parseLong(orderUserId + ""));
                    inform.setTitle("预约审核");
                    String content = "";
                    entity.setShzt(shzt);
                    entity.setShjg(shjg);
                    entity.setId(orderPersonId);
                    orderPersonService.update(entity);
                    if (shzt == 1) {
                        content = "您的预约,嫌疑人" + list.get(0).getName() + "审核已通过，请入区。";
                    } else {
                        content = "您的预约" + list.get(0).getName() + "审核未通过，请重新预约。";
                    }
                    inform.setShzt(shzt);
                    inform.setContent(content);
                    inform.setSendTime(new Date());
                    inform.setSystemName("BA");
                    inform.setType(0);
                    inform.setIsRead(0);
                    inform.setOpPid(pid);
                    inform.setOpUserId(sendId);
                    iInformationService.insertInform(inform);
                }
            }
        } catch (Exception e) {
            logger.error("Error on edit orderrequest!", e);
            return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("审核失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("消息").addContent("审核成功!");
    }


    //进行审核
    @RequestMapping(value = "/orderSh")
    @ResponseBody
    public MessageEntity orderSh(@RequestParam("orderRequestId") Integer orderRequestId, @RequestParam("orderPersonId") Integer orderPersonId, @RequestParam("shzt") Integer shzt, @RequestParam("orderUserId") String orderUserId, @RequestParam("shjg") String shjg, HttpServletRequest request) {
        try {
            OrderPersonEntity entity = new OrderPersonEntity();
            Map<String, Object> map = new HashMap<>();
            map.put("orderRequestId", orderRequestId);
            List<OrderPersonEntity> list = orderPersonService.orderPersonList(map);
            Integer sendId = ControllerTool.getUser(request).getId();
            String pid = ControllerTool.getSessionInfo(request).getCurrentOrg().getPid();
            InformationEntity inform = new InformationEntity();
            inform.setSenderId(Long.parseLong(sendId + ""));
            inform.setReceiverId(Long.parseLong(orderUserId + ""));
            inform.setTitle("预约审核");
            String content = "";
            entity.setShzt(shzt);
            entity.setShjg(shjg);
            entity.setId(orderPersonId);
            orderPersonService.update(entity);
            if (shzt == 1) {
                content = "您的预约,嫌疑人" + list.get(0).getName() + "审核已通过，请入区。";
            } else {
                content = "您的预约" + list.get(0).getName() + "审核未通过，请重新预约。";
            }

            inform.setShzt(shzt);
            inform.setContent(content);
            inform.setSendTime(new Date());
            inform.setSystemName("BA");
            inform.setType(0);
            inform.setIsRead(0);
            inform.setOpPid(pid);
            inform.setOpUserId(sendId);
            iInformationService.insertInform(inform);
        } catch (Exception e) {
            logger.error("Error on edit orderrequest!", e);
            return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("审核失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("消息").addContent("审核成功!");
    }

    // 查询
    @RequestMapping(value = "/orderRequestlist")
    @ResponseBody
    public Map<String, Object> orderRequestlist(@RequestParam Map<String, Object> params, HttpServletRequest request)
            throws Exception {
        Map<String, Object> map = ControllerTool.mapFilter(params);
        List<OrderRequestEntity> datas = new ArrayList<OrderRequestEntity>();
        int count = 0;
        boolean flag = true;
        String personName = (String) params.get("personName");
        String userName = (String) params.get("userName");
        String orderNo = (String) params.get("orderNo");
        String areaId = (String) params.get("areaId");
        map.put("personName", personName);
        map.put("userName", userName);
        map.put("orderNo", orderNo);
        map.put("areaId", areaId);
        System.err.println("===========personName===========" + personName);
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuth(request))) {
            // 办案人员-本人
            map.put("dataAuth", " ( n.order_user_id= " + ControllerTool.getUserID(request) + " ) ");
        } else if (RoleEntity.DATA_AUTH_SELFAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 办案场所-本办案场所
            map.put("dataAuth", " n.area_id= " + ControllerTool.getCurrentAreaID(request));
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 管理员 -本办案场所及下级办案场所
            map.put("dataAuth", "  n.area_id " + sessionInfo.getCurrentAndSubAreaInStr());
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 上级办案场所及其下级办案场所
            map.put("dataAuth", "  n.area_id " + sessionInfo.getSuperAndSubAreaInStr());
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 公安领导-本部门及下级部门
            map.put("dataAuth", " ( n.op_pid like " + sessionInfo.getCurrentAndSubOrgPid() + " ) ");
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 法制人员-上级部门及下级部门
            map.put("dataAuth", " ( n.op_pid like " + sessionInfo.getSuperAndSubOrgPid() + " ) ");
        } else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuth(request))) {
            // 本部门
            map.put("dataAuth", " ( n.op_pid = " + sessionInfo.getCurrentOrgPid() + " ) ");
        } else if (RoleEntity.DATA_AUTH_FULL == (ControllerTool.getRoleDataAuth(request))) {

        } else {
            flag = false;
        }
        if (flag) {
            datas = this.orderRequestService.list(map);
            count = this.orderRequestService.count(map);
        }
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", count);
        result.put("rows", datas);
        return result;
    }

    // 添加预约信息
    @RequestMapping(value = "/addOrderRequest")
    @ResponseBody
    public MessageEntity add(@RequestParam Map<String, Object> params, HttpServletRequest request) throws Exception {

        logger.info("addOrderRequest====map===" + params.get("form"));

        JSONObject orderJson = JSONObject.fromObject(params.get("form"));
        OrderRequestForm form = (OrderRequestForm) JSONObject.toBean(orderJson, OrderRequestForm.class);

        logger.info("form====form===" + form);
        Integer value = 0;
        OrderRequestEntity entity = new OrderRequestEntity();
        entity.setId(entity.getId());
        entity.setCaseId(form.getCaseId());
        entity.setAjlx(form.getAjlx());
        entity.setAb(form.getAb());
        String orderNo = getOrderNo();
        entity.setOrderNo(orderNo);
        entity.setOrderUserId(form.getOrderUserId());
        entity.setMaleCount(form.getMaleCount());
        entity.setFemaleCount(form.getFemaleCount());
        entity.setJuvenilesCount(form.getJuvenilesCount());
        entity.setJuvenilesCountGirl(form.getJuvenilesCountGirl());
        entity.setSpecialCount(form.getSpecialCount());
        entity.setOtherCount(form.getOtherCount());
        String flog = form.getPlanTime();
        Date time = new Date(System.currentTimeMillis() + Integer.parseInt(flog) * 30 * 60 * 1000);
        entity.setPlanTime(time);
        entity.setType(form.getType());
        entity.setStatus(0);
        entity.setDescription(form.getJbaq());
        Integer id = ControllerTool.getSessionInfo(request).getUser().getId();
        entity.setNoterId(id);
        entity.setCreatedTime(new Date());
        entity.setUpdatedTime(entity.getCreatedTime());
        entity.setInterpreter(form.getInterpreter());
        // 添加预约内容
        entity.setStatus(0);
        entity.setJzCaseNumber(form.getJzCaseNumber());
        entity.setAreaId(form.getAreaId());
        try {
            //修改user表中电话字段
            if (form.getUserMobile() != null && !"".equals(form.getUserMobile().trim())) {
                UserEntity user = new UserEntity();
                user.setId(form.getOrderUserId());
                user.setMobile(form.getUserMobile());
                userService.updateMobileNo(user);
            }
            //为op_pid赋值
            SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
            if (sessionInfo.getCurrentOrgPid() != null && sessionInfo.getCurrentOrgPid() != "") {
                entity.setOpPid(sessionInfo.getCurrentOrg().getPid());
            }

            value = this.orderRequestService.insertOrderRequest(entity, request);
            logger.info("orderRequestId====" + value);

            if (params.get("persons") != null) {
                JSONObject temp = JSONObject.fromObject(params.get("persons"));
                JSONArray jsons = JSONArray.fromObject(temp.get("rows"));
                for (Object json : jsons) {
                    OrderPersonEntity ope = (OrderPersonEntity) JSONObject.toBean(JSONObject.fromObject(json), OrderPersonEntity.class);
                    // person
                    PersonEntity personEntity = new PersonEntity();
                    personEntity.setName(ope.getName());
                    personEntity.setCertificateTypeId(ope.getCertificateTypeId());
                    personEntity.setCertificateNo(ope.getCertificateNo());
                    personEntity.setSex(ope.getSex());
                    personEntity.setCountry(ope.getCountry());
                    personEntity.setNation(ope.getNation());
                    personEntity.setAreaId(form.getAreaId());
                    personEntity.setUuid(java.util.UUID.randomUUID().toString());
                    personEntity.setIsArrive(0);
                    //为op_pid赋值
                    String opPid2;
                    if (sessionInfo.getCurrentOrg().getOp_pid() != null && sessionInfo.getCurrentOrg().getOp_pid() != "") {
                        opPid2 = sessionInfo.getCurrentOrg().getOp_pid();
                    } else {
                        opPid2 = "0";
                    }
                    personEntity.setOpPid(opPid2);
                    personEntity.setOpUserId(ControllerTool.getUserID(request));
                    // orderPerson
                    OrderPersonEntity orderPersonEntity = new OrderPersonEntity();
                    orderPersonEntity.setJkb(ope.getJkb());
                    orderPersonEntity.setSfcrgfxdq(ope.getSfcrgfxdq());
                    orderPersonEntity.setJzyms(ope.getJzyms());
                    orderPersonEntity.setZjdz(ope.getZjdz());
                    orderPersonEntity.setZhdz(ope.getZhdz());
                    orderPersonEntity.setSfsjyqldaj(ope.getSfsjyqldaj());
                    orderPersonEntity.setSfsjyqldaj(ope.getSfsjyqldaj());
                    orderPersonEntity.setXyrtw(ope.getXyrtw());
                    orderPersonEntity.setZjfjrq(ope.getZjfjrq());
                    orderPersonEntity.setMgsf(ope.getMgsf());
                    orderPersonEntity.setJ3gysfyjjcgqk(ope.getJ3gysfyjjcgqk());
                    orderPersonEntity.setSfymjjcs(ope.getSfymjjcs());
                    orderPersonEntity.setIsJuvenile(ope.getIsJuvenile());
                    orderPersonEntity.setIsGravida(ope.getIsGravida());
                    orderPersonEntity.setGravidaMonth(ope.getGravidaMonth());
                    orderPersonEntity.setIsGravidaProve(ope.getIsGravidaProve());
                    orderPersonEntity.setIsDisease(ope.getIsDisease());
                    orderPersonEntity.setIsDiseaseProve(ope.getIsDiseaseProve());
                    orderPersonEntity.setPersonType(ope.getPersonType());
                    orderPersonEntity.setOther(ope.getOther());
                    orderPersonEntity.setOrderRequestId(value);
                    //通过身份证号判断本次预约中嫌疑人是否已经添加
                    Map<String, Object> mapInfo = new HashMap();
                    mapInfo.put("orderRequestId", orderPersonEntity.getOrderRequestId());
                    mapInfo.put("certificateNo", personEntity.getCertificateNo());
                    int count = orderRequestService.checkCertificateNoInOrder(mapInfo);
                    if (count == 0) {
                        //插入Person表
                        this.personService.insert(personEntity);
                        int personId = Integer.parseInt(personEntity.getId().toString());

                        if (!"".equals(ope.getAjbh()) && null != ope.getAjbh()) {
                            orderPersonEntity.setAjbh(ope.getAjbh());
                        }

                        if (!"".equals(ope.getRybh()) && null != ope.getRybh()) {
                            orderPersonEntity.setRybh(ope.getRybh());
                        }

                        if (!"".equals(ope.getAjmc()) && null != ope.getAjmc()) {
                            orderPersonEntity.setAjmc(ope.getAjmc());
                        }

                        //插入Order_person表
                        orderPersonEntity.setPersonId(personId);
                        this.orderPersonService.insertOrderPerson(orderPersonEntity);
                    }
                }
            }
            this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "预约添加日志" + entity.getId(), ControllerTool.getRoleName(request), true, OperLogEntity.SYSTEM_BACS);
            OrderStatusEntity orderStatus = new OrderStatusEntity();
            orderStatus.setStatusName("已预约");
            orderStatus.setOrderRequestId(entity.getId());
            orderStatus.setOpPid(ControllerTool.getSessionInfo(request).getCurrentOrg().getPid());
            orderStatus.setOpUserId(ControllerTool.getSessionInfo(request).getUser().getId());
            this.orderStatusService.insert(orderStatus);
            this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "添加预约状态变化日志" + orderStatus.getId(), ControllerTool.getRoleName(request), true, OperLogEntity.SYSTEM_BACS);
            String planTimeStr = "";
            Object flag = PropertyUtil.getContextProperty("send_sms");
            if (flag != null && flag.toString().equals("true")) {
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                if (entity.getPlanTime() != null) {
                    planTimeStr = df.format(entity.getPlanTime());
                }
                String smsContent = form.getOrderUserName() + "，您好！您已预约前往" + ControllerTool.getSessionInfo(request).getCurrentArea().getName() + "办案，您的预约编号是" + entity.getOrderNo() + "，请于" + planTimeStr + "前到达";
                String mobile = "";
                if (form.getUserMobile() != null && !"".equals(form.getUserMobile().trim())) {
                    mobile = form.getUserMobile();
                } else {
                    UserEntity user = userService.getUserByID(entity.getOrderUserId());
                    if (user != null) {
                        mobile = user.getMobile();
                    }
                }

                try {
                    logger.info("###发送短信：手机号码【" + mobile + "】,内容【" + smsContent + "】.");
                    SMSSender.sendSMS(new String[]{mobile}, smsContent);
                } catch (Exception ex) {
                    logger.info("发送短信异常：" + ex.getMessage());
                }
            }


            // 给预约民警添加通知
            try {

                UserEntity uo = userService.getUserByID(ControllerTool.getUserID(request));
                List<UserEntity> tzusers = userService.getUsersByOrgAndType(uo.getOrganizationId(), "预约提示");

                if (tzusers != null && tzusers.size() > 0) {
                    for (int i = 0; i < tzusers.size(); i++) {
                        /* 添加通知 */

                        InformEntity inform = new InformEntity();
                        inform.setSenderId(ControllerTool.getSessionInfo(request).getUser().getId());
                        inform.setReceiverId(tzusers.get(i).getId());
                        inform.setTitle("预约通知");
                        inform.setContent(userService.getUserByID(entity.getOrderUserId()).getRealName()
                                + "预约成功，预计到达时间" + planTimeStr);
                        inform.setType(0);
                        inform.setIsRead(0);
                        this.noticeService.insertInform(inform);

                        InformationEntity informationEntity = new InformationEntity();
                        Integer userId = ControllerTool.getSessionInfo(request).getUser().getId();
                        informationEntity.setSenderId(userId.longValue());
                        informationEntity.setReceiverId(tzusers.get(i).getId().longValue());
                        informationEntity.setTitle("预约通知");
                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        informationEntity.setContent(userService.getUserByID(entity.getOrderUserId()).getRealName() + "预约成功，预计到达时间" + df.format(entity.getPlanTime()));
                        informationEntity.setSendTime(new Date());
                        informationEntity.setIsRead(0);
                        informationEntity.setSystemName("BA");
                        informationEntity.setOpPid(ControllerTool.getSessionInfo(request).getCurrentOrg().getPid());
                        informationEntity.setOpUserId(userId);
                        this.iInformationService.insertInform(informationEntity);
                    }
                }

            } catch (Exception ex) {
                logger.info("添加异常：" + ex.getMessage());
                return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("预约通知添加失败!");
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        String returnValue = String.valueOf(value) + "@" + entity.getOrderNo();
        logger.info("returnValue====" + returnValue);
        return new MessageEntity().addCode(1).addIsError(false).addTitle(returnValue).addContent("添加成功!").addCallbackData(form.getAreaId());
    }

    // 添加预约信息
    @RequestMapping(value = "/addPerson")
    @ResponseBody
    public MessageEntity addPerson(@RequestBody OrdeAddPersonForm form, HttpServletRequest request) throws Exception {
        System.err.println(form);
        Integer value = 0;
        OrderRequestEntity entity = new OrderRequestEntity();
        entity.setId(form.getOrderRequestId());
        // 添加预约内容
        try {
            //为op_pid赋值
            SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
            if (sessionInfo.getCurrentOrgPid() != null && sessionInfo.getCurrentOrgPid() != "") {
                entity.setOpPid(sessionInfo.getCurrentOrg().getPid());
            }
            // person
            PersonEntity personEntity = new PersonEntity();
            personEntity.setName(form.getPerson_name());
            personEntity.setCertificateTypeId(form.getPerson_certificate_type());
            personEntity.setCertificateNo(form.getPerson_certificate_no());
            personEntity.setSex(form.getPerson_sex());
            personEntity.setCountry(form.getPerson_country());
            personEntity.setNation(form.getPerson_nation());

            //personEntity.setAreaId(form.getAreaId());
            personEntity.setAreaId(ControllerTool.getCurrentAreaID(request));
            personEntity.setUuid(java.util.UUID.randomUUID().toString());
            personEntity.setIsArrive(0);
            //为op_pid赋值
            String opPid2;
            if (sessionInfo.getCurrentOrg().getOp_pid() != null && sessionInfo.getCurrentOrg().getOp_pid() != "") {
                opPid2 = sessionInfo.getCurrentOrg().getOp_pid();
            } else {
                opPid2 = "0";
            }
            personEntity.setOpPid(opPid2);
            personEntity.setOpUserId(ControllerTool.getUserID(request));
            // orderPerson
            OrderPersonEntity orderPersonEntity = new OrderPersonEntity();

            orderPersonEntity.setJkb(form.getJkb());
            orderPersonEntity.setSfcrgfxdq(form.getSfcrgfxdq()+"");
            orderPersonEntity.setJzyms(form.getJzyms());
            orderPersonEntity.setIsJuvenile(form.getIsJuvenile());
            orderPersonEntity.setIsGravida(form.getIsGravida());
            orderPersonEntity.setGravidaMonth(form.getGravidaMonth());
            orderPersonEntity.setIsGravidaProve(form.getIsGravidaProve());
            orderPersonEntity.setIsDisease(form.getIsDisease());
            orderPersonEntity.setIsDiseaseProve(form.getIsDiseaseProve());
            orderPersonEntity.setPersonType(form.getPerson_personInfo());
            orderPersonEntity.setOther(form.getOther());
            orderPersonEntity.setOrderRequestId(form.getOrderRequestId());

            orderPersonEntity.setZjdz(form.getZjdz());
            orderPersonEntity.setZhdz(form.getZhdz());
            orderPersonEntity.setSfsjyqldaj(form.getSfsjyqldaj());
            orderPersonEntity.setXyrtw(form.getXyrtw());
            orderPersonEntity.setZjfjrq(form.getZjfjrq());
            orderPersonEntity.setJ3gysfyjjcgqk(form.getJ3gysfyjjcgqk()+"");
            orderPersonEntity.setMgsf(form.getMgsf());
            orderPersonEntity.setSfymjjcs(form.getSfymjjcs());

            if (!"".equals(form.getAjbh()) && null != form.getAjbh()) {
                orderPersonEntity.setAjbh(form.getAjbh());
            }

            if (!"".equals(form.getRybh()) && null != form.getRybh()) {
                orderPersonEntity.setRybh(form.getRybh());
            }

            if (!"".equals(form.getAjmc()) && null != form.getAjmc()) {
                orderPersonEntity.setAjmc(form.getAjmc());
            }

            //通过身份证号判断本次预约中嫌疑人是否已经添加
            Map<String, Object> mapInfo = new HashMap();
            mapInfo.put("orderRequestId", form.getOrderRequestId());
            mapInfo.put("certificateNo", form.getPerson_certificate_no());
            int count = orderRequestService.checkCertificateNoInOrder(mapInfo);
            if (count == 0) {
                //插入Person表
                this.personService.insert(personEntity);
                int personId = Integer.parseInt(personEntity.getId().toString());
                //插入Order_person表
                orderPersonEntity.setPersonId(personId);
                this.orderPersonService.insertOrderPerson(orderPersonEntity);
            }
            if (count > 0) {
                return new MessageEntity().addCode(1).addIsError(true).addTitle("Error").addContent("本次预约已经存在该嫌疑人").addCallbackData(orderPersonEntity.getOrderRequestId());

            }

        } catch (Exception e) {
            e.printStackTrace();
            return new MessageEntity().addCode(1).addIsError(false).addTitle("提示").addContent("添加失败!").addCallbackData("");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("提示").addContent("添加成功!").addCallbackData("");
    }


    public String getOrderNo() {
        String orderno = "YY" + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
        return orderno;
    }


    // 添加人员
    @RequestMapping(value = "/addOrderRequestPerson")
    @ResponseBody
    public MessageEntity addOrderRequestPerson(@RequestParam(value = "values", required = true) String values,
                                               HttpServletRequest request) {
        System.err.println("addOrderRequestPerson  values=" + values);
        String value = null;
        String orderRequestId = null;
        value = values;
        String[] persons = value.split("@");
        String name = persons[0];
        String certificateTypeId = persons[1];
        String certificateNo = persons[2];
        String sex = persons[3];
        String isJuvenile = persons[4];
        String isGravida = persons[5];
        String gravidaMonth = persons[6];
        String isGravidaProve = persons[7];
        String isDisease = persons[8];
        String isDiseaseProve = persons[9];
        if (persons.length >= 11) {
            orderRequestId = persons[10];
            System.err.println("------------------------------" + orderRequestId);

        }
        String personType = persons[11];
        String other = "";
        System.err.println("persons.length========================" + persons.length);
        if (persons.length >= 13) {
            other = persons[12];
        }
        String personCountry = "0";
        String personNation = "0";
        if (persons.length >= 14) {
            personCountry = persons[13];
        }
        if (persons.length >= 15) {
            personNation = persons[14];
        }
        PersonEntity entity = new PersonEntity();
        entity.setId(entity.getId());
        entity.setName(name);
        entity.setCertificateTypeId(Integer.parseInt(certificateTypeId));
        entity.setCertificateNo(certificateNo);
        entity.setSex(Integer.parseInt(sex));
        entity.setCountry(Integer.parseInt(personCountry));
        entity.setNation(Integer.parseInt(personNation));
        entity.setCreatedTime(new Date());
        entity.setUpdatedTime(entity.getCreatedTime());
        OrderRequestEntity orderRequestEntity = orderRequestService.queryStatus(Integer.parseInt(orderRequestId));
        if (orderRequestEntity == null || orderRequestEntity.getId() == null) {
            return new MessageEntity().addCode(1).addIsError(true).addTitle("Error").addContent("预约信息不存在");
        }
        entity.setAreaId(orderRequestEntity.getAreaId());
        entity.setCaseId(orderRequestEntity.getCaseId());
        entity.setUuid(java.util.UUID.randomUUID().toString());
        entity.setIsArrive(0);
        //为op_pid赋值
        String opPid;
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        if (sessionInfo.getCurrentOrg().getOp_pid() != null && sessionInfo.getCurrentOrg().getOp_pid() != "") {
            opPid = sessionInfo.getCurrentOrg().getOp_pid();
        } else {
            opPid = "0";
        }
        entity.setOpPid(opPid);
        entity.setOpUserId(ControllerTool.getUserID(request));
        OrderPersonEntity orderPersonEntity = new OrderPersonEntity();
        orderPersonEntity.setIsJuvenile(Integer.parseInt(isJuvenile));
        orderPersonEntity.setIsGravida(Integer.parseInt(isGravida));
        orderPersonEntity.setGravidaMonth(gravidaMonth);
        orderPersonEntity.setIsGravidaProve(Integer.parseInt(isGravidaProve));
        orderPersonEntity.setIsDisease(Integer.parseInt(isDisease));
        orderPersonEntity.setIsDiseaseProve(Integer.parseInt(isDiseaseProve));
        orderPersonEntity.setPersonType(personType);
        orderPersonEntity.setOther(other);
        orderPersonEntity.setOrderRequestId(Integer.parseInt(orderRequestId));
        System.err.println("addOrderRequestPerson  entity=" + entity);
        try {
            //通过身份证号判断本次预约中嫌疑人是否已经添加
            Map<String, Object> mapInfo = new HashMap();
            mapInfo.put("orderRequestId", orderPersonEntity.getOrderRequestId());
            mapInfo.put("certificateNo", entity.getCertificateNo());
            int count = orderRequestService.checkCertificateNoInOrder(mapInfo);
            logger.info("checkCertificateNoInOrder====================" + count);
            if (count > 0) {
                return new MessageEntity().addCode(1).addIsError(true).addTitle("Error").addContent("本次预约已经存在该嫌疑人").addCallbackData(orderPersonEntity.getOrderRequestId());
            }
            //插入Person表
            this.personService.insert(entity);
            int personId = Integer.parseInt(entity.getId().toString());
            System.err.println("personId  +++++++++=" + personId);
            //插入Order_person表
            orderPersonEntity.setPersonId(personId);
            this.orderPersonService.insertOrderPerson(orderPersonEntity);

            this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "预约人员添加日志" + entity.getId(), ControllerTool.getRoleName(request), true, OperLogEntity.SYSTEM_BACS);
        } catch (Exception e) {
            logger.error("Error on adding person!", e);
            this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "预约人员添加日志" + entity.getId(), ControllerTool.getRoleName(request), false, OperLogEntity.SYSTEM_BACS);
            return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("添加失败!");
        }

        return new MessageEntity().addCode(1).addIsError(false).addTitle(String.valueOf(orderPersonEntity.getOrderRequestId()))
                .addContent("添加成功!");
    }

    /**
     * 查询警号是否存在
     *
     * @param form
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/searchUser")
    @ResponseBody
    public UserEntity searchUser(@RequestBody UserNoSearchForm form) throws Exception {
        UserEntity user = userService.getUserByCertificateNo(form.getUserNo());
        if (user != null) {
            return user;
        } else {
            return null;
        }
    }

    //验证预约房间人数
    @RequestMapping(value = "/checkPersonCount")
    @ResponseBody
    public List checkPersonCount(@RequestParam Map<String, Object> map, HttpServletRequest request, @RequestParam(required = true, value = "countStr") String countStr) {
        List list = new ArrayList<>();
        String result = "";
        if (countStr != null && !"".equals(countStr)) {
            String countStrs[] = countStr.split("@");
            String maleCount = countStrs[0];
            String femaleCount = countStrs[1];
            String juvenilesCount = countStrs[2];
            String specialCount = countStrs[3];
            Integer areaId = Integer.parseInt(countStrs[4]);
            OrderRequestEntity entity = new OrderRequestEntity();
            System.err.println("办案场所id=" + areaId);
            entity.setAreaId(areaId);
            if (maleCount != null && !"".equals(maleCount)) {
                entity.setMaleCount(Integer.parseInt(maleCount));
            } else {
                entity.setMaleCount(0);
            }

            if (femaleCount != null && !"".equals(femaleCount)) {
                entity.setFemaleCount(Integer.parseInt(femaleCount));
            } else {
                entity.setFemaleCount(0);
            }

            if (juvenilesCount != null && !"".equals(juvenilesCount)) {
                entity.setJuvenilesCount(Integer.parseInt(juvenilesCount));
            } else {
                entity.setJuvenilesCount(0);
            }

            if (specialCount != null && !"".equals(specialCount)) {
                entity.setSpecialCount(Integer.parseInt(specialCount));
            } else {
                entity.setSpecialCount(0);
            }
            result = this.orderRequestService.checkOrderRequest(entity);
            System.out.println("---------result----------" + result);
            list.add(result);
        }
        return list;
    }

    // 查询person
    @RequestMapping(value = "/OrderStatusPersonlist")
    @ResponseBody
    public Map<String, Object> orderStatusPersonlist(@RequestParam Map<String, Object> params) throws Exception {
        Map<String, Object> map = ControllerTool.mapFilter(params);
        for (String key : map.keySet()) {
            logger.debug(key + ":" + map.get(key));
        }
        // map.put("usePage", true);
        if (map.get("orderRequestId") == null || map.get("orderRequestId") == "") {
            map.clear();
            map.put("orderRequestId", "-99");
        }
        List<OrderPersonEntity> datas = this.orderPersonService.orderPersonList(map);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", this.orderPersonService.orderPersonCount(map));
        result.put("rows", datas);
        System.out.print("===========OrderStatusPersonlist" + result.get("total"));
        return result;
    }

    //查询状态
    @RequestMapping(value = "/queryStatus")
    @ResponseBody
    public OrderRequestEntity queryStatus(@RequestParam Integer orderRequestId, HttpServletRequest request,
                                          HttpServletResponse response) {
        OrderRequestEntity obj = orderRequestService.queryStatus(orderRequestId);
        return obj;
    }

    @RequestMapping(value = "/queryUserNoById")
    @ResponseBody
    public UserEntity queryUserNoById(HttpServletRequest request, HttpServletResponse response, @RequestParam Long pid)
            throws Exception {

        UserEntity obj = userService.queryUserNoById(pid);
        System.out.print("++++++++++queryUserNoById" + pid);
        System.out.print("++++++++++queryUserNoById" + obj.toString());
        return obj;
    }

    /**
     * 办案场所 下拉框
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/comboArea")
    @ResponseBody
    public List<AreaEntity> comboArea(HttpServletRequest request) {
        List<AreaEntity> list = new ArrayList<AreaEntity>();
        if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuth(request))) {
            // 办案人员
            AreaEntity areaEnity = ControllerTool.getCurrentArea(request);
            list.add(areaEnity);
        } else if (RoleEntity.DATA_AUTH_SELFAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 办案场所
            AreaEntity areaEnity = ControllerTool.getCurrentArea(request);
            list.add(areaEnity);
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 公安领导与管理员
            list = ControllerTool.getSessionInfo(request).getCurrentAndSubArea();
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 上下级
            list = ControllerTool.getSessionInfo(request).getSuperAndSubArea();
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 本部门及下级部门
            list = ControllerTool.getSessionInfo(request).getCurrentAndSubArea();
        } else if (RoleEntity.DATA_AUTH_FULL == (ControllerTool.getRoleDataAuth(request))) {
            //全部
            Map<String, Object> params = new HashMap<String, Object>() {
            };
            list = areaService.listAllArea(params);
        } else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuth(request))) {
            //本部门
            list = ControllerTool.getSessionInfo(request).getCurrentAndSubArea();
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            //上级部门及下级部门
            list = ControllerTool.getSessionInfo(request).getSuperAndSubArea();
        } else {

        }
        return list;
    }

    // 修改
    @RequestMapping(value = "/edit")
    @ResponseBody
    public MessageEntity editOrderRequest(@RequestParam Map<String, Object> params, HttpServletRequest request,
                                          HttpServletResponse response) throws Exception {
        logger.info("edit====map===" + params.get("form"));
        JSONObject orderJson = JSONObject.fromObject(params.get("form"));
        OrderRequestForm form = (OrderRequestForm) JSONObject.toBean(orderJson, OrderRequestForm.class);
        logger.info("form====form===" + form);
        OrderRequestEntity entity = new OrderRequestEntity();
        entity.setId(form.getId());
        String orderNo = getOrderNo();
        entity.setOrderNo(orderNo);
        entity.setOrderUserId(form.getOrderUserId());
        entity.setMaleCount(form.getMaleCount());
        entity.setFemaleCount(form.getFemaleCount());
        entity.setJuvenilesCount(form.getJuvenilesCount());
        entity.setJuvenilesCountGirl(form.getJuvenilesCountGirl());
        entity.setSpecialCount(form.getSpecialCount());
        entity.setOtherCount(form.getOtherCount());
        entity.setType(form.getType());
        entity.setDescription(form.getJbaq());
        Integer id = ControllerTool.getSessionInfo(request).getUser().getId();
        entity.setNoterId(id);
        entity.setUpdatedTime(new Date());
        entity.setInterpreter(form.getInterpreter());
        entity.setAjlx(form.getAjlx());
        entity.setAb(form.getAb());
        // 添加预约内容
//		entity.setStatus(0);
        // 从session中取得
//		if(ControllerTool.getSessionInfo(request).getCurrentArea()!=null){
//			entity.setAreaId(ControllerTool.getSessionInfo(request).getCurrentArea().getId());
//		}
        entity.setAreaId(form.getAreaId());
        if (form.getJzCaseNumber() != null && !"".equals(form.getJzCaseNumber())) {
            entity.setJzCaseNumber(form.getJzCaseNumber());
        }
        try {
            //修改user表中电话字段
            UserEntity user = new UserEntity();
            System.err.println("-----------userid---------------" + form.getOrderUserId());
            user.setId(form.getOrderUserId());
            user.setMobile(form.getUserMobile());
            System.err.println("-----------userid---------------" + form.getUserMobile());
            System.err.println("------------user------------修改user表中电话字段-----------" + user);
            userService.updateMobileNo(user);
            //为op_pid赋值
            SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
            String opPid = "";
            if (sessionInfo.getCurrentOrgPid() != null && sessionInfo.getCurrentOrgPid() != "") {
                opPid = sessionInfo.getCurrentOrg().getOp_pid();
            } else {
                opPid = "0";
            }
            this.orderRequestService.updateOrderRequest(entity, ControllerTool.getUser(request).getId(), opPid);
            this.operLogService.insertLog(OperLogEntity.EDIT_TYPE, "预约修改日志" + entity.getId(), ControllerTool.getRoleName(request), true, OperLogEntity.SYSTEM_BACS);

        } catch (Exception e) {
            logger.error("Error on edit orderrequest!", e);
            return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("修改失败!");
        }
        int value = form.getId();
        String returnValue = String.valueOf(value) + "@" + entity.getOrderNo();
        return new MessageEntity().addCode(1).addIsError(false).addTitle(returnValue).addContent("修改成功!");
    }

    // 删除person
    @SuppressWarnings("unused")
    @RequestMapping(value = "/removeOrderRequestPerson")
    @ResponseBody
    public MessageEntity removeOrderRequestPerson(@RequestParam("orderRequestId") int orderRequestId, @RequestParam("orderPersonId") int orderPersonId, @RequestParam("personId") Long personId, HttpServletRequest request,
                                                  HttpServletResponse response) {
        Integer status = null;
        try {
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("personId", personId);
            param.put("orderRequestId", orderRequestId);
            List<SerialEntity> serialEntities = this.serialService.selectByOrderIdAndPersonId(param);
            if (serialEntities != null && serialEntities.size() > 0) {
                return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("删除失败,嫌疑人已入区");
            } else {
                param.put("id", orderPersonId);
                this.orderPersonService.deleteById(orderPersonId);
                this.personService.delete(personId);
                this.operLogService.insertLog(OperLogEntity.DELETE_TYPE, "预约人员删除日志" + param, ControllerTool.getRoleName(request), true, OperLogEntity.SYSTEM_BACS);
            }
        } catch (Exception e) {
            logger.error("Error on deleting operlog!", e);
            return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("删除失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("消息").addContent("删除成功!");
    }

    // 修改person
    @RequestMapping(value = "/editPersonRight")
    @ResponseBody
    public MessageEntity editPersonRight(@RequestBody OrdeAddPersonForm form, HttpServletRequest request,
                                         HttpServletResponse response) {
        System.err.println("-----------------" + form.toString());
        OrderPersonEntity orderPersonEntity = new OrderPersonEntity();
        if (form.getIsJuvenile() == null || "".equals(form.getIsJuvenile())) {
            orderPersonEntity.setIsJuvenile(0);
        } else {
            orderPersonEntity.setIsJuvenile(form.getIsJuvenile());
        }
        if (form.getIsGravida() == null || "".equals(form.getIsGravida())) {
            orderPersonEntity.setIsGravida(0);
        } else {
            orderPersonEntity.setIsGravida(form.getIsGravida());
        }
        if (form.getGravidaMonth() == null || "".equals(form.getGravidaMonth())) {
            orderPersonEntity.setGravidaMonth(0 + "");
        } else {
            orderPersonEntity.setGravidaMonth(form.getGravidaMonth() + "");
        }
        if (form.getIsGravidaProve() == null || "".equals(form.getIsGravidaProve())) {
            orderPersonEntity.setIsGravidaProve(0);
        } else {
            orderPersonEntity.setIsGravidaProve(form.getIsGravidaProve());
        }
        if (form.getIsDisease() == null || "".equals(form.getIsDisease())) {
            orderPersonEntity.setIsDisease(0);
        } else {
            orderPersonEntity.setIsDisease(form.getIsDisease());
        }
        if (form.getIsDiseaseProve() == null || "".equals(form.getIsDiseaseProve())) {
            orderPersonEntity.setIsDiseaseProve(0);
        } else {
            orderPersonEntity.setIsDiseaseProve(form.getIsDiseaseProve());
        }
        if (form.getJkb() == null || "".equals(form.getJkb())) {
            orderPersonEntity.setJkb(0);
        } else {
            orderPersonEntity.setJkb(form.getJkb());
        }
        if (form.getSfcrgfxdq() == null || "".equals(form.getSfcrgfxdq())) {
            orderPersonEntity.setSfcrgfxdq(0+"");
        } else {
            orderPersonEntity.setSfcrgfxdq(form.getSfcrgfxdq()+"");
        }
        if (form.getJzyms() == null || "".equals(form.getJzyms())) {
            orderPersonEntity.setJzyms(0 + "");
        } else {
            orderPersonEntity.setJzyms(form.getJzyms() + "");
        }
        orderPersonEntity.setZjdz(form.getZjdz());
        orderPersonEntity.setZhdz(form.getZhdz());
        if (form.getSfsjyqldaj() == null || "".equals(form.getSfsjyqldaj())) {
            orderPersonEntity.setSfsjyqldaj(0);
        } else {
            orderPersonEntity.setSfsjyqldaj(form.getSfsjyqldaj());
        }
        orderPersonEntity.setSfsjyqldaj(form.getSfsjyqldaj());
        orderPersonEntity.setXyrtw(form.getXyrtw());
        orderPersonEntity.setZjfjrq(form.getZjfjrq());
        orderPersonEntity.setMgsf(form.getMgsf());
        if (form.getJ3gysfyjjcgqk() == null || "".equals(form.getJ3gysfyjjcgqk())) {
            orderPersonEntity.setJ3gysfyjjcgqk(0+"");
        } else {
            orderPersonEntity.setJ3gysfyjjcgqk(form.getJ3gysfyjjcgqk()+"");
        }
        if (form.getSfymjjcs() == null || "".equals(form.getSfymjjcs())) {
            orderPersonEntity.setSfymjjcs(0);
        } else {
            orderPersonEntity.setSfymjjcs(form.getSfymjjcs());
        }

        orderPersonEntity.setPersonType(form.getPerson_personInfo());
        orderPersonEntity.setOther(form.getOther());
        orderPersonEntity.setId(form.getOrderPersonId());
        PersonEntity entity = new PersonEntity();
        entity.setName(form.getPerson_name());
        entity.setCertificateTypeId(form.getPerson_certificate_type());
        entity.setCertificateNo(form.getPerson_certificate_no());
        entity.setSex(form.getPerson_sex());
        entity.setCountry(form.getPerson_country());
        entity.setNation(form.getPerson_nation());
        entity.setAreaId(form.getAreaId());
        entity.setUuid(java.util.UUID.randomUUID().toString());
        entity.setIsArrive(0);

        entity.setId(Long.parseLong(form.getPersonId() + ""));
        entity.setUpdatedTime(new Date());
        System.out.println("entity= " + entity);
        try {
            //更新order_person表

            if (!"".equals(form.getAjbh()) && null != form.getAjbh()) {
                orderPersonEntity.setAjbh(form.getAjbh());
            }

            if (!"".equals(form.getRybh()) && null != form.getRybh()) {
                orderPersonEntity.setRybh(form.getRybh());
            }

            if (!"".equals(form.getAjmc()) && null != form.getAjmc()) {
                orderPersonEntity.setAjmc(form.getAjmc());
            }

            this.orderPersonService.update(orderPersonEntity);
            //更新person表
            this.personService.update(entity);
            this.operLogService.insertLog(OperLogEntity.EDIT_TYPE, "人员修改日志" + entity.getId(), ControllerTool.getRoleName(request), true, OperLogEntity.SYSTEM_BACS);

        } catch (Exception e) {
            logger.error("Error on edit orderrequest!", e);
            return new MessageEntity().addCode(1).addIsError(true).addTitle("Error").addContent("修改失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("提示")
                .addContent("修改成功!");
    }

    // 修改person
    @RequestMapping(value = "/editPerson")
    @ResponseBody
    public MessageEntity editPerson(@RequestBody OrderRequestPersonForm form, HttpServletRequest request,
                                    HttpServletResponse response) {
        System.err.println("-----------------" + form.toString());
        OrderPersonEntity orderPersonEntity = new OrderPersonEntity();
        if (form.getEisJuvenile() == null || "".equals(form.getEisJuvenile())) {
            orderPersonEntity.setIsJuvenile(0);
        } else {
            orderPersonEntity.setIsJuvenile(form.getEisJuvenile());
        }
        if (form.getEisGravida() == null || "".equals(form.getEisGravida())) {
            orderPersonEntity.setIsGravida(0);
        } else {
            orderPersonEntity.setIsGravida(form.getEisGravida());
        }
        if (form.getEgravidaMonth() == null || "".equals(form.getEgravidaMonth())) {
            orderPersonEntity.setGravidaMonth(0 + "");
        } else {
            orderPersonEntity.setGravidaMonth(form.getEgravidaMonth() + "");
        }
        if (form.getEisGravidaProve() == null || "".equals(form.getEisGravidaProve())) {
            orderPersonEntity.setIsGravidaProve(0);
        } else {
            orderPersonEntity.setIsGravidaProve(form.getEisGravidaProve());
        }
        if (form.getEisDisease() == null || "".equals(form.getEisDisease())) {
            orderPersonEntity.setIsDisease(0);
        } else {
            orderPersonEntity.setIsDisease(form.getEisDisease());
        }
        if (form.getEisDiseaseProve() == null || "".equals(form.getEisDiseaseProve())) {
            orderPersonEntity.setIsDiseaseProve(0);
        } else {
            orderPersonEntity.setIsDiseaseProve(form.getEisDiseaseProve());
        }
        orderPersonEntity.setPersonType(form.getPersonInfo());
        orderPersonEntity.setOther(form.getOther());
        orderPersonEntity.setId(form.getOrderPersonId());
        PersonEntity entity = new PersonEntity();
        entity.setId(Long.parseLong(form.getPersonId() + ""));
        entity.setName(form.getName());
        entity.setCertificateTypeId(form.getCertificateTypeId());
        entity.setCertificateNo(form.getCertificateNo());
        entity.setSex(form.getSex());
        entity.setCountry(form.getCountry());
        entity.setNation(form.getNation());
        entity.setUpdatedTime(new Date());
        System.out.println("entity= " + entity);
        try {
            //更新order_person表
            this.orderPersonService.update(orderPersonEntity);
            //更新person表
            this.personService.update(entity);
            this.operLogService.insertLog(OperLogEntity.EDIT_TYPE, "人员修改日志" + entity.getId(), ControllerTool.getRoleName(request), true, OperLogEntity.SYSTEM_BACS);

        } catch (Exception e) {
            logger.error("Error on edit orderrequest!", e);
            return new MessageEntity().addCode(1).addIsError(true).addTitle("Error").addContent("修改失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("提示")
                .addContent("修改成功!");
    }

    // 修改状态
    @RequestMapping(value = "/changestatus")
    @ResponseBody
    public MessageEntity changeStatusOrderRequest(@RequestBody OrderRequestForm form, HttpServletRequest request,
                                                  HttpServletResponse response) {
        OrderRequestEntity entity = new OrderRequestEntity();
        entity.setId(form.getId());
        entity.setStatus(form.getStatus());
        OrderStatusEntity orderStatus = new OrderStatusEntity();
        orderStatus.setStatusName("已取消");
        //为op_pid赋值
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        String opPid = "";
        if (sessionInfo.getCurrentOrgPid() != null && sessionInfo.getCurrentOrgPid() != "") {
            opPid = sessionInfo.getCurrentOrg().getOp_pid();
        } else {
            opPid = "0";
        }
        orderStatus.setOpPid(opPid);
        orderStatus.setOpUserId(ControllerTool.getUser(request).getId());
        orderStatus.setOrderRequestId(form.getId());
        try {
            this.orderStatusService.insert(orderStatus);
            this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "添加预约状态变化日志" + orderStatus.getId(), ControllerTool.getRoleName(request), true, OperLogEntity.SYSTEM_BACS);
            this.orderRequestService.updateOrderRequest(entity, ControllerTool.getUser(request).getId(), opPid);
            this.operLogService.insertLog(OperLogEntity.EDIT_TYPE, "预约修改日志" + entity.getId(), ControllerTool.getRoleName(request), true, OperLogEntity.SYSTEM_BACS);

        } catch (Exception e) {
            logger.error("Error on edit orderrequest!", e);
            return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("取消失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("消息").addContent("取消成功!");
    }

    //更改状态已到达
    @RequestMapping(value = "/orderReach")
    @ResponseBody
    public MessageEntity orderReach(@RequestParam Integer orderRequestId, HttpServletRequest request,
                                    HttpServletResponse response) {
        System.err.println("reach---" + orderRequestId);
        OrderRequestEntity entity = new OrderRequestEntity();
        entity.setId(orderRequestId);
        entity.setStatus(2);
        entity.setArriveTime(new Date());
        OrderStatusEntity orderStatus = new OrderStatusEntity();
        orderStatus.setStatusName("已到达");
        orderStatus.setOrderRequestId(orderRequestId);
        //为op_pid赋值
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        String opPid = "";
        if (sessionInfo.getCurrentOrgPid() != null && sessionInfo.getCurrentOrgPid() != "") {
            orderStatus.setOpPid(sessionInfo.getCurrentOrg().getOp_pid());
        } else {
            orderStatus.setOpPid("0");
        }
        opPid = orderStatus.getOpPid();
        orderStatus.setOpUserId(ControllerTool.getUser(request).getId());
        try {
            this.orderStatusService.insert(orderStatus);
            this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "添加预约状态变化日志" + orderStatus.getId(), ControllerTool.getRoleName(request), true, OperLogEntity.SYSTEM_BACS);
            this.orderRequestService.updateOrderRequest(entity, ControllerTool.getUser(request).getId(), opPid);
            this.operLogService.insertLog(OperLogEntity.EDIT_TYPE, "预约修改日志" + entity.getId(), ControllerTool.getRoleName(request), true, OperLogEntity.SYSTEM_BACS);

        } catch (Exception e) {
            logger.error("Error on edit orderrequest!", e);
            return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("到达失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("消息").addContent("到达成功!");
    }

    // 延期
    @RequestMapping(value = "/delay")
    @ResponseBody
    public MessageEntity delayOrderRequest(@RequestBody DelayForm form, HttpServletRequest request,
                                           HttpServletResponse response) throws Exception {
        OrderRequestEntity entity = new OrderRequestEntity();
        // 预约id
        entity.setId(form.getDelayId());
        int flog = form.getDelayTime();
        String planTime = form.getPlanTimeDelay();
        Date plan = new Date(Long.parseLong(planTime));
        entity.setPlanTime(new Date(plan.getTime() + flog * 30 * 60 * 1000));
        entity.setStatus(form.getDelayStatus());
        OrderStatusEntity orderStatus = new OrderStatusEntity();
        orderStatus.setStatusName("已推迟");
        //为op_pid赋值
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        String opPid = "";
        if (sessionInfo.getCurrentOrgPid() != null && sessionInfo.getCurrentOrgPid() != "") {
            opPid = sessionInfo.getCurrentOrg().getOp_pid();
        } else {
            opPid = "0";
        }
        orderStatus.setOpPid(opPid);
        orderStatus.setOpUserId(ControllerTool.getUser(request).getId());
        orderStatus.setOrderRequestId(form.getDelayId());
        try {
            this.orderStatusService.insert(orderStatus);
            this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "添加预约状态变化日志" + orderStatus.getId(), ControllerTool.getRoleName(request), true, OperLogEntity.SYSTEM_BACS);
            this.orderRequestService.updateOrderRequest(entity, ControllerTool.getUser(request).getId(), opPid);
            this.operLogService.insertLog(OperLogEntity.EDIT_TYPE, "预约修改日志" + entity.getId(), ControllerTool.getRoleName(request), true, OperLogEntity.SYSTEM_BACS);
        } catch (Exception e) {
            logger.error("Error on edit orderrequest!", e);
            return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("延期失败!");
        }
        OrderRequestEntity orderRequest = orderRequestService.queryStatus(form.getDelayId());
        if (orderRequest != null) {
            UserEntity user = userService.getUserByID(orderRequest.getOrderUserId());
            if (user != null) {
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String planTimeStr = "";
                if (entity.getPlanTime() != null) {
                    planTimeStr = df.format(entity.getPlanTime());
                }
                String smsContent = user.getRealName() + "，您好！您预约前往" + ControllerTool.getSessionInfo(request).getCurrentArea().getName() + "办案已推迟，请于" + planTimeStr + "前到达";
                String mobile = user.getMobile();
                logger.info("###发送短信：手机号码【" + mobile + "】,内容【" + smsContent + "】.");
                SMSSender.sendSMS(new String[]{mobile}, smsContent);
            }
        } else {
            logger.info("###发送短信：查询预约信息错误id【" + form.getDelayId() + "】.");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("消息").addContent("延期成功!");
    }

    // 查询警综人员信息
    @RequestMapping(value = "/listJzPerson")
    @ResponseBody
    public Map<String, Object> listJzPerson(@RequestParam Map<String, Object> params, HttpServletRequest request)
            throws Exception {
        System.out.println("+++++++++++++listJzPerson警综人员信息+++++ Start+++++++++++++++");
        Map<String, Object> map = ControllerTool.mapFilter(params);
        List<JzPersonEntity> datas = new ArrayList<JzPersonEntity>();
        int count = 0;
        datas = jzPersonService.list(map);
        count = jzPersonService.count(map);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", count);
        result.put("rows", datas);
        return result;
    }
}