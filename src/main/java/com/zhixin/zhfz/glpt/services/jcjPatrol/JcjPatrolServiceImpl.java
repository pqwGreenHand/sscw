package com.zhixin.zhfz.glpt.services.jcjPatrol;

import com.zhixin.zhfz.glpt.dao.jcjPatrol.JcjPatrolMapper;
import com.zhixin.zhfz.zhag.entity.JqxxEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class JcjPatrolServiceImpl implements JcjPatrolService {

    @Autowired
    private JcjPatrolMapper jcjPatrolMapper;

    @Override
    public List<JqxxEntity> list(Map<String, Object> map) {
        return jcjPatrolMapper.list(map);
    }

    @Override
    public int count(Map<String, Object> map) {
        return jcjPatrolMapper.count(map);
    }

    @Override
    public List<JqxxEntity> quyJqxxListById(Map<String, Object> map) {
        return jcjPatrolMapper.quyJqxxListById(map);
    }

    @Override
    public int count_ById(Map<String, Object> map) {
        return jcjPatrolMapper.count_ById(map);
    }
}
