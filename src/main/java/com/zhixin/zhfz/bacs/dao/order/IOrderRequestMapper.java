package com.zhixin.zhfz.bacs.dao.order;

import com.zhixin.zhfz.bacs.entity.OrderRequestCheckEntity;
import com.zhixin.zhfz.bacs.entity.OrderRequestEntity;

import java.util.List;
import java.util.Map;

/*
 * 预约
 */
public interface IOrderRequestMapper {

	List<OrderRequestEntity> list(Map<String, Object> params);

	int count(Map<String, Object> params);

	int insertSelective(OrderRequestEntity record);

	int checkCertificateNoInOrder(Map<String, Object> map);

	List<OrderRequestCheckEntity> getOccupyRoomAmount(Map<String, Object> params);

	List<OrderRequestEntity> getOrderRequestlistByAreaId(Map<String, Object> params);

	OrderRequestEntity queryStatus(Integer orderRequestId);

	int updateByPrimaryKeySelective(OrderRequestEntity record);

	//根据案件Id获取预约信息
	List<OrderRequestEntity> getOrderRequestByCaseId(int caseId) throws Exception;
	//根据案件Id删除预约信息
	void deleteOrderRequestByCaseId(int caseId) throws Exception;


    void deleteById(Integer orderRequestId);
}