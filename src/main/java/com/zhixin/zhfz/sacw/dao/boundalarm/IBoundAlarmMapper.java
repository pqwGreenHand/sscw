package com.zhixin.zhfz.sacw.dao.boundalarm;

import com.zhixin.zhfz.sacw.entity.BoundAlarmEntity;

import java.util.List;
import java.util.Map;

public interface IBoundAlarmMapper {

    List<BoundAlarmEntity> list(Map<String, Object> map) throws Exception;

    int count(Map<String, Object> map) throws Exception;

}
