package com.zhixin.zhfz.bacsapp.dao.order;

import com.zhixin.zhfz.bacsapp.entity.EntranceEntity;
import com.zhixin.zhfz.bacsapp.entity.OrderEntity;
import com.zhixin.zhfz.bacsapp.form.OrderForm;
import com.zhixin.zhfz.bacsapp.form.OrderPersonForm;

import java.util.List;
import java.util.Map;

/**
 * @program: zhfz
 * @description: 预约登记
 * @author: cuichengwei
 * @create: 2019-04-09
 **/

public interface IOrderMapper {

    public List<OrderEntity> orderList(Map<String, Object> map);

    public int orderCount(Map<String, Object> map);

    public OrderForm orderRequestList(Map<String,Object> map);

    public List<OrderPersonForm> orderPersonList(Map<String, Object> map);

}
