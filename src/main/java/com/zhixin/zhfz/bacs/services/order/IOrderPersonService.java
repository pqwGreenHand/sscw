package com.zhixin.zhfz.bacs.services.order;



import com.zhixin.zhfz.bacs.entity.OrderPersonEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface IOrderPersonService {
    int insertOrderPerson(OrderPersonEntity entity);

    List<OrderPersonEntity> orderPersonList(Map<String, Object> map)throws Exception;

    Object orderPersonCount(Map<String, Object> map)throws Exception;

    void deleteById(int Id)  throws Exception;

    void update(OrderPersonEntity entity)  throws  Exception;

    void updateByOrderIdAndPersonId(OrderPersonEntity entity) throws Exception;

    void deleteByOrderRequestIdList(List<Integer> orderRequestIdList);

    List<OrderPersonEntity> getNoArrivePersonByOrderId(int orderId)  throws Exception;

    void deleteByOrderRequestId(Integer orderRequestId);
}
