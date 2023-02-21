package com.zhixin.zhfz.bacsapp.services.order;

import com.zhixin.zhfz.bacs.dao.order.IOrderPersonMapper;
import com.zhixin.zhfz.bacs.dao.order.IOrderRequestMapper;
import com.zhixin.zhfz.bacs.dao.order.IOrderStatusMapper;
import com.zhixin.zhfz.bacs.dao.person.IPersonMapper;
import com.zhixin.zhfz.bacs.entity.OrderPersonEntity;
import com.zhixin.zhfz.bacs.entity.OrderRequestEntity;
import com.zhixin.zhfz.bacs.entity.OrderStatusEntity;
import com.zhixin.zhfz.bacs.entity.PersonEntity;
import com.zhixin.zhfz.bacsapp.dao.order.IOrderMapper;
import com.zhixin.zhfz.bacsapp.entity.OrderEntity;
import com.zhixin.zhfz.bacsapp.form.OrderForm;
import com.zhixin.zhfz.bacsapp.form.OrderPersonForm;
import com.zhixin.zhfz.common.common.GetBirthAgeSexUtil;
import com.zhixin.zhfz.common.dao.operLog.IOperLogMapper;
import com.zhixin.zhfz.common.entity.MessageEntity;
import com.zhixin.zhfz.common.entity.OperLogEntity;
import com.zhixin.zhfz.common.utils.ControllerTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderServiceImpl implements IOrderService {

    private static Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private IOrderMapper orderMapper;

    @Autowired
    private IOrderRequestMapper orderRequestMapper;

    @Autowired
    private IOrderPersonMapper orderPersonMapper;

    @Autowired
    private IPersonMapper personMapper;

    @Autowired
    private IOrderStatusMapper orderStatusMapper;

    @Autowired
    private IOperLogMapper operLogMapper;


    @Override
    public List<OrderEntity> orderList(Map<String, Object> map) {
        return orderMapper.orderList(map);
    }

    @Override
    public int orderCount(Map<String, Object> map) {
        return orderMapper.orderCount(map);
    }

    @Override
    public OrderForm orderRequestList(Map<String, Object> map) {
        return orderMapper.orderRequestList(map);
    }

    @Override
    public List<OrderPersonForm> orderPersonList(Map<String, Object> map) {
        return orderMapper.orderPersonList(map);
    }

    @Override
    public MessageEntity updatePersonAndOrderPerson(OrderForm form, Integer userId, String roleName, HttpServletRequest request) throws Exception{
        try {
            // 修改预约信息
            OrderRequestEntity orderEntity = new OrderRequestEntity();
            orderEntity.setId(form.getOrderRequestId());
            orderEntity.setOrderUserId(form.getOrderUserId());
            orderEntity.setMaleCount(form.getMaleCount());
            orderEntity.setFemaleCount(form.getFemaleCount());
            orderEntity.setJuvenilesCount(form.getJuvenilesCount());
            orderEntity.setSpecialCount(form.getSpecialCount());
            orderEntity.setPlanTime(df.parse(form.getPlanTime()));
            orderEntity.setType(String.valueOf(form.getType()));
            orderEntity.setInterpreter(form.getInterpreter());
            orderEntity.setAreaId(form.getAreaId());
            orderEntity.setNoterId(userId);
            orderEntity.setCaseId(form.getCaseId());
            orderEntity.setUpdatedTime(new Date());
            this.orderRequestMapper.updateByPrimaryKeySelective(orderEntity);
            // 修改orderStatus表
            OrderStatusEntity obj2 = new OrderStatusEntity();
            obj2.setStatusTime(new Date());
            obj2.setOrderRequestId(orderEntity.getId());
            obj2.setStatusName("预约修改");
            obj2.setOpPid(ControllerTool.getSessionInfo(request).getCurrentOrg().getPid());
            obj2.setOpUserId(ControllerTool.getSessionInfo(request).getUser().getId());
            this.orderStatusMapper.insert(obj2);
            // 添加预约修改操作日志
            OperLogEntity log = new OperLogEntity();
            log.setType(OperLogEntity.EDIT_TYPE);
            log.setContent("预约修改日志" + orderEntity.getId());
            log.setUser(roleName);
            log.setIsSuccess(true);
            log.setSystemName(OperLogEntity.SYSTEM_BACS_APP);
            this.operLogMapper.insert(log);
        } catch (Exception e) {
            logger.error("预约登记信息修改失败!", e);
            return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("预约修改失败!");
        }
        List<OrderPersonForm> orderPersonList = form.getOrderPersonList();
        // 新增person和orderPerson
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
                //entity.setUuid(java.util.UUID.randomUUID().toString());
                //entity.setCreatedTime(new Date());
                //entity.setUpdatedTime(new Date());
                entity.setAreaId(form.getAreaId());
                entity.setCaseId(form.getCaseId());
                // orderPerson信息
                OrderPersonEntity orderPersonEntity = new OrderPersonEntity();
                orderPersonEntity.setIsJuvenile(orderPersonForm.getIsJuvenile());
                orderPersonEntity.setIsGravida(orderPersonForm.getIsGravida());
                orderPersonEntity.setIsGuardian(orderPersonForm.getIsGuardian());
                orderPersonEntity.setGravidaMonth(String.valueOf(orderPersonForm.getGravidaMonth()));
                orderPersonEntity.setIsDisease(orderPersonForm.getIsDisease());
                orderPersonEntity.setIsDiseaseProve(orderPersonForm.getIsDiseaseProve());
                orderPersonEntity.setPersonType(orderPersonForm.getPersonType());
                orderPersonEntity.setOrderRequestId(form.getOrderRequestId());
                try {
                    //操作person和orderPerson表
                    if(orderPersonForm.getPersonId() == null){
                        //通过身份证号判断本次预约中嫌疑人是否已经添加
                        Map<String, Object> mapInfo=new HashMap();
                        mapInfo.put("orderRequestId", orderPersonEntity.getOrderRequestId());
                        mapInfo.put("certificateNo", entity.getCertificateNo());
                        int count= orderRequestMapper.checkCertificateNoInOrder(mapInfo);
                        if(count>0) {
                            return new MessageEntity().addCode(1).addIsError(true).addTitle("Error").addContent("本次预约已经存在该嫌疑人").addCallbackData(orderPersonEntity.getOrderRequestId());
                        }
                        // 新增person
                        entity.setUuid(java.util.UUID.randomUUID().toString());
                        entity.setCreatedTime(new Date());
                        entity.setUpdatedTime(new Date());
                        entity.setOpPid(ControllerTool.getSessionInfo(request).getCurrentOrg().getPid());
                        entity.setOpUserId(ControllerTool.getSessionInfo(request).getUser().getId());
                        this.personMapper.insert(entity);
                        int personId = Integer.parseInt(entity.getId().toString());
                        // 新增orderPerson
                        orderPersonEntity.setPersonId(personId);
                        orderPersonEntity.setStatus(0);
                        orderPersonEntity.setOpPid(ControllerTool.getSessionInfo(request).getCurrentOrg().getPid());
                        orderPersonEntity.setOpUserId(ControllerTool.getSessionInfo(request).getUser().getId());
                        this.orderPersonMapper.insertSelective(orderPersonEntity);
                        // 添加人员新增操作日志
                        OperLogEntity log = new OperLogEntity();
                        log.setType(OperLogEntity.INSERT_TYPE);
                        log.setContent("人员新增日志" + entity.getId());
                        log.setUser(roleName);
                        log.setIsSuccess(true);
                        log.setSystemName(OperLogEntity.SYSTEM_BACS_APP);
                        this.operLogMapper.insert(log);
                    }else {
                        // 更新person
                        entity.setId(Long.valueOf(orderPersonForm.getPersonId()));
                        entity.setUpdatedTime(new Date());
                        this.personMapper.update(entity);
                        // 更新orderPerson
                        orderPersonEntity.setId(orderPersonForm.getOrderPersonId());
                        orderPersonEntity.setStatus(orderPersonForm.getOrderPersonStatus());
                        this.orderPersonMapper.updateByPrimaryKeySelective(orderPersonEntity);
                        // 添加人员修改操作日志
                        OperLogEntity log = new OperLogEntity();
                        log.setType(OperLogEntity.EDIT_TYPE);
                        log.setContent("人员修改日志" + entity.getId());
                        log.setUser(roleName);
                        log.setIsSuccess(true);
                        log.setSystemName(OperLogEntity.SYSTEM_BACS_APP);
                        this.operLogMapper.insert(log);
                    }
                } catch (Exception e) {
                    logger.error("Error on adding person!", e);
                    return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("预约信息修改失败!");
                }
            }
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("提示").addContent("预约信息修改成功!");
    }

}
