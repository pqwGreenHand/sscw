package com.zhixin.zhfz.bacsapp.services.order;

import com.zhixin.zhfz.bacsapp.entity.EntranceEntity;
import com.zhixin.zhfz.bacsapp.entity.OrderEntity;
import com.zhixin.zhfz.bacsapp.form.OrderForm;
import com.zhixin.zhfz.bacsapp.form.OrderPersonForm;
import com.zhixin.zhfz.common.entity.MessageEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface IOrderService {


    public List<OrderEntity> orderList(Map<String, Object> map);

    public int orderCount(Map<String, Object> map);

    public OrderForm orderRequestList(Map<String,Object> map);

    public List<OrderPersonForm> orderPersonList(Map<String, Object> map);

    // 更新person和orderPerson
    public MessageEntity updatePersonAndOrderPerson(OrderForm form, Integer userId, String roleName, HttpServletRequest request) throws Exception;


}
