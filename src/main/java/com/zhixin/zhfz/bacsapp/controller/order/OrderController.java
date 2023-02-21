package com.zhixin.zhfz.bacsapp.controller.order;

import com.zhixin.zhfz.bacs.entity.*;
import com.zhixin.zhfz.bacs.services.order.IOrderPersonService;
import com.zhixin.zhfz.bacs.services.order.IOrderRequestService;
import com.zhixin.zhfz.bacs.services.order.IOrderStatusService;
import com.zhixin.zhfz.bacs.services.person.IPersonService;
import com.zhixin.zhfz.bacsapp.entity.InformationEntity;
import com.zhixin.zhfz.bacsapp.entity.OrderEntity;
import com.zhixin.zhfz.bacsapp.form.OrderForm;
import com.zhixin.zhfz.bacsapp.form.OrderPersonForm;
import com.zhixin.zhfz.bacsapp.services.Information.IInformationService;
import com.zhixin.zhfz.bacsapp.services.order.IOrderService;
import com.zhixin.zhfz.common.common.GetBirthAgeSexUtil;
import com.zhixin.zhfz.common.entity.*;
import com.zhixin.zhfz.common.services.notice.IMyNoticeService;
import com.zhixin.zhfz.common.services.operLog.IOperLogService;
import com.zhixin.zhfz.common.services.user.IUserService;
import com.zhixin.zhfz.common.utils.ControllerTool;
import com.zhixin.zhfz.common.utils.PropertyUtil;
import com.zhixin.zhfz.common.utils.SMSSender;
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

/**
 * @program: zhfz
 * @description: 预约登记
 * @author: cuicchengwei
 * @create: 2019-04-09
 **/
@Controller
@RequestMapping("/zhfz/bacsapp/order")
public class OrderController {

    private static Logger logger = LoggerFactory.getLogger(OrderController.class);

    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private IOrderService orderService;

    @Autowired
    private IOrderRequestService orderRequestService;

    @Autowired
    private IOrderStatusService orderStatusService;

    @Autowired
    private IPersonService personService;

    @Autowired
    private IOrderPersonService orderPersonService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IInformationService iInformationService;

    @Autowired
    private IOperLogService operLogService;

    @RequestMapping(value = "/orderList")
    @ResponseBody
    public Map<String, Object> entranceList(@RequestParam Map<String, Object> pageMap, HttpServletRequest request,
                                            HttpServletResponse response) throws Exception {
        Map<String, Object> map = ControllerTool.mapFilter(pageMap);
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuth(request))) {
            // 办案人员-本人
            map.put("dataAuth", " ( b.zbmj_id= " + ControllerTool.getUserID(request)
                    + " or b.cjr="+ ControllerTool.getUserID(request)
                    + " or locate('," + ControllerTool.getUserID(request) + ",', b.xbmj_ids) "
                    + " or a.order_user_id=" + ControllerTool.getUserID(request)
                    + " or a.noter_id=" + ControllerTool.getUserID(request)
                    +  " ) ");
        } else if (RoleEntity.DATA_AUTH_SELFAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 办案场所-本办案场所
            map.put("dataAuth", " a.area_id=" + ControllerTool.getCurrentAreaID(request));
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 管理员 -本办案场所及下级办案场所
            map.put("dataAuth", " a.area_id" + sessionInfo.getCurrentAndSubAreaInStr());
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 上级办案场所及其下级办案场所
            map.put("dataAuth", " a.area_id" + sessionInfo.getSuperAndSubAreaInStr());
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 公安领导-本部门及下级部门
            map.put("dataAuth"," ( a.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                    + " or b.zbmj_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                    + " or b.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 法制人员-上级部门及下级部门
            map.put("dataAuth"," ( a.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
                    + " or b.zbmj_pid like " + sessionInfo.getSuperAndSubOrgPid()
                    + " or b.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuth(request))) {
            // 本部门
            map.put("dataAuth"," ( a.op_pid = " + sessionInfo.getCurrentOrgPid()
                    + " or b.zbmj_pid = " + sessionInfo.getCurrentOrgPid()
                    + " or b.op_pid = " + sessionInfo.getCurrentOrgPid()
                    + " ) ");
        }
        List<OrderEntity> list = new ArrayList<OrderEntity>();
        int total = 0;
        list = orderService.orderList(map);
        total = orderService.orderCount(map);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("list", list);
        result.put("total", total);
        return result;
    }

    @RequestMapping(value = "/orderPersonList")
    @ResponseBody
    public Map<String, Object> orderPersonList(Integer orderRequestId,HttpServletRequest request) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuth(request))) {
            // 办案人员-本人
            map.put("dataAuth", " ( b.zbmj_id= " + ControllerTool.getUserID(request)
                    + " or b.cjr="+ ControllerTool.getUserID(request)
                    + " or locate('," + ControllerTool.getUserID(request) + ",', b.xbmj_ids) "
                    + " or a.order_user_id=" + ControllerTool.getUserID(request)
                    + " or a.noter_id=" + ControllerTool.getUserID(request)
                    +  " ) ");
        } else if (RoleEntity.DATA_AUTH_SELFAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 办案场所-本办案场所
            map.put("dataAuth", " a.area_id=" + ControllerTool.getCurrentAreaID(request));
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 管理员 -本办案场所及下级办案场所
            map.put("dataAuth", " a.area_id " + sessionInfo.getCurrentAndSubAreaInStr());
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 上级办案场所及其下级办案场所
            map.put("dataAuth", " a.area_id " + sessionInfo.getSuperAndSubAreaInStr());
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 公安领导-本部门及下级部门
            map.put("dataAuth"," ( a.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                    + " or b.zbmj_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                    + " or b.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 法制人员-上级部门及下级部门
            map.put("dataAuth"," ( a.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
                    + " or b.zbmj_pid like " + sessionInfo.getSuperAndSubOrgPid()
                    + " or b.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuth(request))) {
            // 本部门
            map.put("dataAuth"," ( a.op_pid = " + sessionInfo.getCurrentOrgPid()
                    + " or b.zbmj_pid = " + sessionInfo.getCurrentOrgPid()
                    + " or b.op_pid = " + sessionInfo.getCurrentOrgPid()
                    + " ) ");
        }
        map.put("orderRequestId",orderRequestId);
        OrderForm order = new OrderForm();
        List<OrderPersonForm> orderPersonList = new ArrayList<>();
        order = this.orderService.orderRequestList(map);
        orderPersonList = this.orderService.orderPersonList(map);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("order", order);
        result.put("personList", orderPersonList);
        return result;
    }

    // 添加预约登记
    @RequestMapping(value = "/addOrder")
    @ResponseBody
    public MessageEntity addOrder(@RequestBody OrderForm form, HttpServletRequest request) throws Exception {
        logger.info("form=" + form);
        // 验证警号是否存在
        UserEntity userp = userService.getUserByCertificateNo(form.getPoliceNo());
        if (userp == null) {
            return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("民警警号错误!");
        }
        form.setOrderUserId(userp.getId());
        // 预约编号
        String orderNo = "YY" + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
        // 当前登录用户id
        Integer userId = ControllerTool.getSessionInfo(request).getUser().getId();
        try {
            // 添加预约信息
            OrderRequestEntity orderEntity = new OrderRequestEntity();
            orderEntity.setOrderNo(orderNo);
            orderEntity.setOrderUserId(form.getOrderUserId());
            orderEntity.setMaleCount(form.getMaleCount());
            orderEntity.setFemaleCount(form.getFemaleCount());
            orderEntity.setJuvenilesCount(form.getJuvenilesCount());
            orderEntity.setSpecialCount(form.getSpecialCount());
            orderEntity.setPlanTime(df.parse(form.getPlanTime()));
            orderEntity.setType(String.valueOf(form.getType()));
            orderEntity.setInterpreter(form.getInterpreter());
            orderEntity.setStatus(0); // 已预约
            orderEntity.setAreaId(form.getAreaId());
            orderEntity.setNoterId(userId);
            orderEntity.setOpPid(ControllerTool.getSessionInfo(request).getCurrentOrg().getPid());
            orderEntity.setCaseId(form.getCaseId());
            orderEntity.setCreatedTime(new Date());
            orderEntity.setUpdatedTime(new Date());
            int orderRequestId = orderRequestService.insertOrderRequest(orderEntity,request);
            // 添加预约状态信息
            OrderStatusEntity orderStatus = new OrderStatusEntity();
            orderStatus.setStatusName("已预约");
            orderStatus.setOrderRequestId(orderRequestId);
            orderStatus.setOpUserId(ControllerTool.getSessionInfo(request).getUser().getId());
            orderStatus.setOpPid(ControllerTool.getSessionInfo(request).getCurrentOrg().getPid());
            this.orderStatusService.insert(orderStatus);
            // 发送短信
            String	planTimeStr ="";
            Object flag=	PropertyUtil.getContextProperty("send_sms");
            if(flag!=null&&flag.toString().equals("true")) {
                UserEntity user = new UserEntity();
                if (orderEntity.getPlanTime() != null) {
                    planTimeStr = df.format(orderEntity.getPlanTime());
                }
                if(orderEntity.getOrderUserId() != null){
                    user = userService.getUserByID(orderEntity.getOrderUserId());
                }
                String smsContent = user.getRealName() + "，您好！您已预约前往" + ControllerTool.getSessionInfo(request).getCurrentArea().getName() + "办案，您的预约编号是" + orderEntity.getOrderNo() + "，请于" + planTimeStr + "前到达";
                String mobile = "";
                try {
                    if (user != null) {
                        mobile = user.getMobile();
                    }
                    logger.info("###发送短信：手机号码【" + mobile + "】,内容【" + smsContent + "】.");
                    SMSSender.sendSMS(new String[]{mobile}, smsContent);
                } catch (Exception ex) {
                    logger.info("发送短信异常：" + ex.getMessage());
                }
            }
            // 添加person和orderPserson
            List<OrderPersonForm> orderPersonList = form.getOrderPersonList();
            if(orderPersonList != null && orderPersonList.size() > 0){
                for(OrderPersonForm orderPersonForm : orderPersonList){
                    // person信息
                    PersonEntity entity = new PersonEntity();
                    entity.setName(orderPersonForm.getPersonName());
                    Map<String, String> birAgeSex = GetBirthAgeSexUtil.getBirAgeSex(orderPersonForm.getCertificateNo());
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    entity.setBirth(sdf.parse(birAgeSex.get("birthday")));
                    entity.setCertificateTypeId(orderPersonForm.getCertificateTypeId());
                    entity.setCertificateNo(orderPersonForm.getCertificateNo());
                    entity.setSex(orderPersonForm.getSex());
                    entity.setOfficer(orderPersonForm.getOfficer());
                    entity.setCountry(orderPersonForm.getCountry());
                    entity.setNation(orderPersonForm.getNation());
                    entity.setCensusRegister(orderPersonForm.getCensusRegister());
                    entity.setAddress(orderPersonForm.getAddress());
                    entity.setPersonNo(form.getPoliceNo());
                    entity.setUuid(java.util.UUID.randomUUID().toString());
                    entity.setCreatedTime(new Date());
                    entity.setUpdatedTime(new Date());
                    entity.setAreaId(form.getAreaId());
                    entity.setCaseId(form.getCaseId());
                    entity.setOpPid(ControllerTool.getSessionInfo(request).getCurrentOrgPid());
                    entity.setOpUserId(ControllerTool.getSessionInfo(request).getUser().getId());

                    // orderPerson信息
                    OrderPersonEntity orderPersonEntity = new OrderPersonEntity();
                    orderPersonEntity.setIsJuvenile(orderPersonForm.getIsJuvenile());
                    orderPersonEntity.setIsGravida(orderPersonForm.getIsGravida());
                    orderPersonEntity.setIsGuardian(orderPersonForm.getIsGuardian());
                    orderPersonEntity.setGravidaMonth(String.valueOf(orderPersonForm.getGravidaMonth()));
                    orderPersonEntity.setIsDisease(orderPersonForm.getIsDisease());
                    orderPersonEntity.setIsDiseaseProve(orderPersonForm.getIsDiseaseProve());
                    orderPersonEntity.setIsGravidaProve(orderPersonForm.getIsGravidaProve());
                    orderPersonEntity.setPersonType(orderPersonForm.getPersonType());
                    orderPersonEntity.setStatus(0);
                    orderPersonEntity.setOrderRequestId(orderRequestId);
                    try {
                        //通过身份证号判断本次预约中嫌疑人是否已经添加
                        Map<String, Object> mapInfo=new HashMap();
                        mapInfo.put("orderRequestId", orderPersonEntity.getOrderRequestId());
                        mapInfo.put("certificateNo", entity.getCertificateNo());
                        int count= orderRequestService.checkCertificateNoInOrder(mapInfo);
                        if(count>0) {
                            return new MessageEntity().addCode(1).addIsError(true).addTitle("Error").addContent("本次预约已经存在该嫌疑人").addCallbackData(orderPersonEntity.getOrderRequestId());
                        }
                        //插入Person表
                        this.personService.insert(entity);
                        int personId = Integer.parseInt(entity.getId().toString());
                        //插入Order_person表
                        orderPersonEntity.setPersonId(personId);
                        orderPersonEntity.setOpPid(ControllerTool.getSessionInfo(request).getCurrentOrg().getPid());
                        orderPersonEntity.setOpUserId(ControllerTool.getSessionInfo(request).getUser().getId());
                        this.orderPersonService.insertOrderPerson(orderPersonEntity);
                    } catch (Exception e) {
                        logger.error("Error on adding person!", e);
                        return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("嫌疑人添加失败!");
                    }
                }
            }
            // 给预约民警添加通知
            try {
                UserEntity userInform = userService.getUserByID(orderEntity.getOrderUserId());
                InformationEntity informationEntity = new InformationEntity();
                informationEntity.setSenderId(userId.longValue());
                informationEntity.setReceiverId(orderEntity.getOrderUserId().longValue());
                informationEntity.setTitle("预约通知");
                informationEntity.setContent(userInform.getRealName() + "预约成功，预计到达时间" + df.format(orderEntity.getPlanTime()));
                informationEntity.setSendTime(new Date());
                informationEntity.setIsRead(0);
                informationEntity.setSystemName("BaAPP");
                informationEntity.setAjmc(form.getAjmc());
                informationEntity.setAjbh(form.getAjbh());
                informationEntity.setOpPid(ControllerTool.getSessionInfo(request).getCurrentOrg().getPid());
                informationEntity.setOpUserId(userId);
                this.iInformationService.insertInform(informationEntity);
                // 给主办民警添加通知
                UserEntity userInform2 = userService.getUserByID(form.getZbmjId());
                InformationEntity informationEntity2 = new InformationEntity();
                informationEntity2.setSenderId(userId.longValue());
                informationEntity2.setReceiverId(form.getZbmjId().longValue());
                informationEntity2.setTitle("预约通知");
                informationEntity2.setContent(userInform2.getRealName() + "预约成功，预计到达时间" + df.format(orderEntity.getPlanTime()));
                informationEntity2.setSendTime(new Date());
                informationEntity2.setIsRead(0);
                informationEntity2.setSystemName("BaAPP");
                informationEntity2.setAjmc(form.getAjmc());
                informationEntity2.setAjbh(form.getAjbh());
                informationEntity2.setOpPid(ControllerTool.getSessionInfo(request).getCurrentOrg().getPid());
                informationEntity2.setOpUserId(userId);
                this.iInformationService.insertInform(informationEntity2);
            }catch (Exception ex){
            	logger.info("添加异常：" + ex.getMessage());
                    return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("预约通知添加失败!");
                }
        } catch (Exception e) {
            logger.error("预约登记信息添加失败!", e);
            return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("预约失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("提示").addContent("预约成功!");
    }

    // 修改预约登记
    @RequestMapping(value = "/editOrder")
    @ResponseBody
    public MessageEntity editOrder(@RequestBody OrderForm form, HttpServletRequest request) throws Exception {
        logger.info("form=" + form);
        MessageEntity messageEntity = new MessageEntity();
        // 当前登录用户id
        Integer userId = ControllerTool.getSessionInfo(request).getUser().getId();
        // 当前登录用户角色名
        String roleName = ControllerTool.getRoleName(request);
        if(form != null) {
            // 验证警号是否存在
            UserEntity userp = userService.getUserByCertificateNo(form.getPoliceNo());
            if (userp == null) {
                return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("民警警号错误!");
            }
            form.setOrderUserId(userp.getId());

            // 修改orderRequest,person和orderPserson
            messageEntity = this.orderService.updatePersonAndOrderPerson(form,userId,roleName,request);

            // 发送短信
            String planTimeStr = "";
            Object flag = PropertyUtil.getContextProperty("send_sms");
            if (flag != null && flag.toString().equals("true")) {
                UserEntity user = new UserEntity();
                if (form.getPlanTime() != null) {
                    planTimeStr = form.getPlanTime();
                }
                if (form.getOrderUserId() != null) {
                    user = userService.getUserByID(form.getOrderUserId());
                }
                String smsContent = user.getRealName() + "，您好！您已预约前往" + ControllerTool.getSessionInfo(request).getCurrentArea().getName() + "办案，您的预约编号是" + form.getOrderNo() + "，请于" + planTimeStr + "前到达";
                String mobile = "";
                try {
                    if (user != null) {
                        mobile = user.getMobile();
                    }
                    logger.info("###发送短信：手机号码【" + mobile + "】,内容【" + smsContent + "】.");
                    SMSSender.sendSMS(new String[]{mobile}, smsContent);
                } catch (Exception ex) {
                    logger.info("发送短信异常：" + ex.getMessage());
                    return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("发送短信失败!");
                }
            }

            // 添加通知
            try {
                // 给预约民警添加通知
                UserEntity userInform = userService.getUserByID(form.getOrderUserId());
                InformationEntity informationEntity = new InformationEntity();
                informationEntity.setSenderId(userId.longValue());
                informationEntity.setReceiverId(form.getOrderUserId().longValue());
                informationEntity.setTitle("预约修改通知");
                informationEntity.setContent(userInform.getRealName()+"预约修改成功，预计到达时间"+form.getPlanTime());
                informationEntity.setSendTime(new Date());
                informationEntity.setIsRead(0);
                informationEntity.setSystemName("BaAPP");
                informationEntity.setAjmc(form.getAjmc());
                informationEntity.setAjbh(form.getAjbh());
                informationEntity.setOpPid(ControllerTool.getSessionInfo(request).getCurrentOrg().getPid());
                informationEntity.setOpUserId(userId);
                this.iInformationService.insertInform(informationEntity);
                // 给主办民警添加通知
                UserEntity userInform2 = userService.getUserByID(form.getZbmjId());
                InformationEntity informationEntity2 = new InformationEntity();
                informationEntity2.setSenderId(userId.longValue());
                informationEntity2.setReceiverId(form.getZbmjId().longValue());
                informationEntity2.setTitle("预约修改通知");
                informationEntity2.setContent(userInform2.getRealName()+"预约修改成功，预计到达时间"+form.getPlanTime());
                informationEntity2.setSendTime(new Date());
                informationEntity2.setIsRead(0);
                informationEntity2.setSystemName("BaAPP");
                informationEntity2.setAjmc(form.getAjmc());
                informationEntity2.setAjbh(form.getAjbh());
                informationEntity2.setOpPid(ControllerTool.getSessionInfo(request).getCurrentOrg().getPid());
                informationEntity2.setOpUserId(userId);
                this.iInformationService.insertInform(informationEntity2);
            }catch (Exception ex){
                logger.info("修改异常：" + ex.getMessage());
                return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("预约通知添加失败!");
            }
        }
        return messageEntity;
    }

}
