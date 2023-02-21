package com.zhixin.zhfz.bacs.services.alarm;

import com.zhixin.zhfz.bacs.dao.alarm.IAlarmMapper;
import com.zhixin.zhfz.bacs.entity.AlarmEntity;
import com.zhixin.zhfz.bacs.entity.AlarmTypeCoutEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AlarmServiceImpl implements IAlarmService {

    @Autowired
    private IAlarmMapper iAlarmMapper;

    @Override
    public List<AlarmEntity> list(Map<String, Object> map) {
        return iAlarmMapper.list(map);
    }

    @Override
    public int count(Map<String, Object> map) {
        return iAlarmMapper.count(map);
    }

    @Override
    public List<AlarmTypeCoutEntity> listAlarmCountByType(Map<String, Object> map) {
        return iAlarmMapper.listAlarmCountByType(map);
    }
}
