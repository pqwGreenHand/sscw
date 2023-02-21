package com.zhixin.zhfz.sacw.services.boundalarm;

import com.zhixin.zhfz.sacw.entity.BoundAlarmEntity;

import java.util.List;
import java.util.Map;

/**
 * Created by fym on 2017/7/10.
 */
public interface IBoundAlarmService {
    List<BoundAlarmEntity> list(Map<String, Object> map) throws Exception;

    int count(Map<String, Object> map) throws Exception;
}
