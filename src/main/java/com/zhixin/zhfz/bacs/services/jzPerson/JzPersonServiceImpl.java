package com.zhixin.zhfz.bacs.services.jzPerson;

import com.zhixin.zhfz.bacs.dao.jzAjxx.IJzAjxxMapper;
import com.zhixin.zhfz.bacs.dao.jzPerson.IJzPersonMapper;
import com.zhixin.zhfz.bacs.entity.JzPersonEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class JzPersonServiceImpl implements IJzPersonService {

    @Autowired
    private IJzPersonMapper jzPersonMapper;

    @Override
    public List<JzPersonEntity> list(Map<String, Object> map) {
        return jzPersonMapper.list(map);
    }

    @Override
    public int count(Map<String, Object> map) {
        return jzPersonMapper.count(map);
    }
}
