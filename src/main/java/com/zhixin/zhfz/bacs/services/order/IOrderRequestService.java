package com.zhixin.zhfz.bacs.services.order;
import com.zhixin.zhfz.bacs.entity.OrderPersonEntity;
import com.zhixin.zhfz.bacs.entity.OrderRequestEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface IOrderRequestService {
	
	List<OrderRequestEntity> list(Map<String, Object> params) throws Exception;
	int count(Map<String, Object> params) throws Exception;

	int insertOrderRequest(OrderRequestEntity entity, HttpServletRequest request);

	int checkCertificateNoInOrder(Map<String, Object> map);

	String checkOrderRequest (OrderRequestEntity entity);

	OrderRequestEntity queryStatus(Integer orderRequestId);

	void updateOrderRequest(OrderRequestEntity entity,int oUserId,String opPid);

	int countWuMs() throws Exception;

	List<OrderRequestEntity> listByCaseId(int caseId) throws Exception;

	void deleteOrderRequestByCaseId(int caseId)  throws Exception;

	void changStatus(OrderRequestEntity entity);

    void deleteById(Integer orderRequestId);
}
