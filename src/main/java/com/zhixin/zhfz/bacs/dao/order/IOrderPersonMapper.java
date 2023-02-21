package com.zhixin.zhfz.bacs.dao.order;

import com.zhixin.zhfz.bacs.entity.OrderPersonEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface IOrderPersonMapper {
    int insertSelective(OrderPersonEntity record);

    List<OrderPersonEntity> orderPersonList(Map<String,Object> param) throws  Exception;

    int orderPersonCount(Map<String,Object> param) throws  Exception;

    void deleteById(int id) throws Exception;
    //根据ID更新
    void updateByPrimaryKeySelective(OrderPersonEntity entity) throws Exception;
    //根据requestID批量删除预约相关数据（order_person、person、order_status）
    void deleteByOrderRequestIdList(@Param("orderRequestIdList") List<Integer> orderRequestIdList);
    //根据预约编号获取未入区嫌疑人
    List<OrderPersonEntity> getNoArrivePersonByOrderId(int orderId) throws Exception;
    //根据预约id和嫌疑人id更新
    void updateByOrderIdAndPersonId(OrderPersonEntity entity) throws Exception;

    void deleteByOrderRequestId(Integer orderRequestId);
}
