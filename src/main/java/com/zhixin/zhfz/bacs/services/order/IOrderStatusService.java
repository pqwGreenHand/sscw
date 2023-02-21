package com.zhixin.zhfz.bacs.services.order;

import java.util.List;
import java.util.Map;

import com.zhixin.zhfz.bacs.entity.OrderStatusEntity;

public interface IOrderStatusService {
    int insert(OrderStatusEntity record);

    void deleteByOrderRequestId(Integer orderRequestId);
}
