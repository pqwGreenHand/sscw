package com.zhixin.zhfz.bacs.services.order;

import com.zhixin.zhfz.bacs.dao.order.IOrderPersonMapper;
import com.zhixin.zhfz.bacs.entity.OrderPersonEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class IOrderPersonServiceImpl implements  IOrderPersonService{
    @Resource
    private IOrderPersonMapper orderPersonMapper;
    @Override
    public int insertOrderPerson(OrderPersonEntity entity){
        return this.orderPersonMapper.insertSelective(entity);
    }

    @Override
    public List<OrderPersonEntity> orderPersonList(Map<String, Object> map)  throws Exception{

        return this.orderPersonMapper.orderPersonList(map);
    }

    @Override
    public Object orderPersonCount(Map<String, Object> map) throws Exception{

        return this.orderPersonMapper.orderPersonCount(map);
    }
    @Override
    public void deleteById(int id) throws Exception {
        this.orderPersonMapper.deleteById(id);
    }
    @Override
    public void update(OrderPersonEntity entity) throws Exception{
        this.orderPersonMapper.updateByPrimaryKeySelective(entity);
    }

    @Override
    public void updateByOrderIdAndPersonId(OrderPersonEntity entity) throws Exception{
        this.orderPersonMapper.updateByOrderIdAndPersonId(entity);
    }

    //根据requestID批量删除预约相关数据（order_person、person、order_status）
    @Override
    public void deleteByOrderRequestIdList(List<Integer> orderRequestIdList) {
        this.orderPersonMapper.deleteByOrderRequestIdList(orderRequestIdList);
    }

    @Override
    public List<OrderPersonEntity> getNoArrivePersonByOrderId(int orderId) throws Exception{
        return this.orderPersonMapper.getNoArrivePersonByOrderId(orderId);
    }

    @Override
    public void deleteByOrderRequestId(Integer orderRequestId) {
        this.orderPersonMapper.deleteByOrderRequestId(orderRequestId);
    }


}
