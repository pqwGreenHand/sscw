package com.zhixin.zhfz.sacw.services.sapositionalarm;

import com.zhixin.zhfz.sacw.dao.sapositionalarm.ISaPositionAlarmMapper;
import com.zhixin.zhfz.sacw.entity.BoundAlarmEntity;
import com.zhixin.zhfz.sacw.entity.SaPositionAlarmEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SaPositionAlarmServiceImpl implements ISaPositionAlarmService {

    @Resource
    private ISaPositionAlarmMapper mapper;


    @Override
    public void insert(BoundAlarmEntity entity) throws Exception {
        mapper.insert(entity);
    }
}
