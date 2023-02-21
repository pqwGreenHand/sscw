package com.zhixin.zhfz.bacs.services.rescue;

import com.zhixin.zhfz.bacs.dao.rescue.IRescueMapper;
import com.zhixin.zhfz.bacs.entity.RescueEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @program: zhfz
 * @description: 医疗救助
 * @author: jzw
 * @create: 2019-02-26 10:51
 **/
@Service
public class RescueServiceImpl implements IRescueService {

    @Autowired
    private IRescueMapper mapper;

    @Override
    public List<RescueEntity> list(Map<String, Object> map) throws Exception {
        return mapper.list(map);
    }

    @Override
    public int count(Map<String, Object> map) throws Exception {
        return mapper.count(map);
    }

    @Override
    public void delete(Long id) throws Exception {
        mapper.delete(id);
    }

    @Override
    public void insert(RescueEntity entity) throws Exception {
        mapper.insert(entity);
    }

    @Override
    public void update(RescueEntity entity) throws Exception {
        mapper.update(entity);
    }
}
