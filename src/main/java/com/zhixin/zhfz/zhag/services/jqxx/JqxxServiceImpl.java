package com.zhixin.zhfz.zhag.services.jqxx;

import com.zhixin.zhfz.zhag.dao.jqxx.IJqxxMapper;
import com.zhixin.zhfz.zhag.entity.JqxxEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class JqxxServiceImpl implements IJqxxService {

    @Autowired
    private IJqxxMapper jqxxMapper;

    @Override
    public List<JqxxEntity> list(Map<String, Object> map) {
        return jqxxMapper.list(map);
    }

    @Override
    public int count(Map<String, Object> map) {
        return jqxxMapper.count(map);
    }
}
