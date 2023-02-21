package com.zhixin.zhfz.sacw.services.outgoingwarehouse;


import com.zhixin.zhfz.sacw.entity.InputEntity;
import com.zhixin.zhfz.sacw.entity.OutgoingEntity;


import java.util.List;
import java.util.Map;

public interface OutgoingService {

    List<OutgoingEntity> listInvolved(Map<String, Object> map) throws Exception;

    List<OutgoingEntity> listInvolvedDetailed(Map<String, Object> map) throws Exception;

    List<InputEntity> list(Map<String, Object> map) throws Exception;

    int count(Map<String, Object> map) throws Exception;

}
