package com.zhixin.zhfz.sacw.services.boundalarm;


import com.zhixin.zhfz.sacw.dao.boundalarm.IBoundAlarmMapper;
import com.zhixin.zhfz.sacw.entity.BoundAlarmEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by fym on 2017/7/10.
 */
@Service
public class BoundAlarmServiceImpl implements IBoundAlarmService {

    @Autowired
    IBoundAlarmMapper mapper;

    @Override
    public List<BoundAlarmEntity> list(Map<String, Object> map) throws Exception {
        return mapper.list(map);
    }

    @Override
    public int count(Map<String, Object> map) throws Exception {
        return mapper.count(map);
    }
}
