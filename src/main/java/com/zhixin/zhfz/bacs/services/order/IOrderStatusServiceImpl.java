package com.zhixin.zhfz.bacs.services.order;


import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.zhixin.zhfz.bacs.dao.order.IOrderStatusMapper;
import com.zhixin.zhfz.bacs.entity.OrderStatusEntity;
import org.springframework.stereotype.Service;

@Service
public class IOrderStatusServiceImpl implements IOrderStatusService {
	
	@Resource
	private IOrderStatusMapper orderStatusMapper;

	@Override
	public int insert(OrderStatusEntity record) {
		orderStatusMapper.deleteByOrderRequestId(record.getOrderRequestId());
		return orderStatusMapper.insert(record);
	}

	@Override
	public void deleteByOrderRequestId(Integer orderRequestId) {
		orderStatusMapper.deleteByOrderRequestId(orderRequestId);
	}
}
