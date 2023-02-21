package com.zhixin.zhfz.sacw.services.cwalarm;

import com.zhixin.zhfz.sacw.dao.cwalarm.ICwAlarmDao;
import com.zhixin.zhfz.sacw.entity.CwAlarmEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ICwAlarmServiceImpl implements ICwAlarmService {
    @Autowired
    ICwAlarmDao mapper;

    @Override
    public List<CwAlarmEntity> list(Map<String, Object> map) throws Exception {
        return mapper.list(map);
    }

    @Override
    public List<CwAlarmEntity> quyTimeOutListByParam(int day1) throws Exception {
        return mapper.quyTimeOutListByParam(day1);
    }

    @Override
    public List<CwAlarmEntity> quyLendListByParam(int day2) throws Exception {
        return mapper.quyLendListByParam(day2);
    }

    @Override
    public void addAlarm(CwAlarmEntity caEntity) throws Exception {
        // TODO Auto-generated method stub
        mapper.addAlarm(caEntity);
    }

    @Override
    public int count(Map<String, Object> map) throws Exception {
        return mapper.count(map);
    }

    /**
     * 修改处理内容
     *
     * @param entity
     */
    @Override
    public void updateAlarmById(CwAlarmEntity entity) {
        mapper.updateAlarmById(entity);
    }
}
