package com.zhixin.zhfz.sacw.dao.outgoingwarehouse;


import com.zhixin.zhfz.sacw.entity.InputEntity;
import com.zhixin.zhfz.sacw.entity.OutgoingEntity;

import java.util.List;
import java.util.Map;

public interface OutgoingWarehouseMapper {


    public List<InputEntity> inputList(Map<String, Object> map) throws Exception;

    public int count(Map<String, Object> map) throws Exception;

    List<OutgoingEntity> listInvolved(Map<String, Object> map) throws Exception;

    List<OutgoingEntity> listInvolvedDetailed(Map<String, Object> map) throws Exception;


}
