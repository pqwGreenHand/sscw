package com.zhixin.zhfz.bacs.services.alarm;

import com.zhixin.zhfz.bacs.entity.AlarmEntity;
import com.zhixin.zhfz.bacs.entity.AlarmTypeCoutEntity;

import java.util.List;
import java.util.Map;

public interface IAlarmService {

    public List<AlarmEntity> list(Map<String, Object> map);

    public int count(Map<String, Object> map);

    public List<AlarmTypeCoutEntity> listAlarmCountByType(Map<String, Object> map);
}
