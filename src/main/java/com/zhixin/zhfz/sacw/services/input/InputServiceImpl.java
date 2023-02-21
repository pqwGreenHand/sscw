package com.zhixin.zhfz.sacw.services.input;


import com.zhixin.zhfz.sacw.dao.input.IInputMapper;
import com.zhixin.zhfz.sacw.entity.InputEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class InputServiceImpl implements IInputService {

    @Autowired
    IInputMapper mapper;

    @Override
    public List<InputEntity> list(Map<String, Object> map) throws Exception {
        return mapper.list(map);
    }

    @Override
    public int count(Map<String, Object> map) throws Exception {
        return mapper.count(map);
    }

    @Override
    public void insert(InputEntity entity) throws Exception {
        mapper.insert(entity);
    }

}
