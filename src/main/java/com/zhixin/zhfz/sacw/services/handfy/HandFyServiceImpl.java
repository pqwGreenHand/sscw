package com.zhixin.zhfz.sacw.services.handfy;


import com.zhixin.zhfz.sacw.dao.handfy.IHandFyMapper;
import com.zhixin.zhfz.sacw.entity.InvolvedEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class HandFyServiceImpl implements IHandFyService {

    @Autowired
    IHandFyMapper mapper;

    @Override
    public List<InvolvedEntity> list(Map<String, Object> map) throws Exception {
        return mapper.list(map);
    }

    @Override
    public int count(Map<String, Object> map) throws Exception {
        return mapper.count(map);
    }


}
