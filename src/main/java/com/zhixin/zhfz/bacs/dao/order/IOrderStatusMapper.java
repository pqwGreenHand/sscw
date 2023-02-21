package com.zhixin.zhfz.bacs.dao.order;

import com.zhixin.zhfz.bacs.entity.OrderStatusEntity;

/**
 * 预约状态
 * @author
 *
 */
public interface IOrderStatusMapper {

    void updateByPrimaryKeySelective(OrderStatusEntity record);
    void deleteByOrderRequestId(Integer orderRequestId);
    int insert(OrderStatusEntity record);
}