package com.zhixin.zhfz.sacw.services.labellog;

import com.zhixin.zhfz.sacw.dao.labellog.ILabelLogMapper;
import com.zhixin.zhfz.sacw.entity.LabelLogEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class LabelLogServiceImpl implements ILabelLogService {

    @Autowired
    ILabelLogMapper mapper;


    @Override
    public List<LabelLogEntity> list(Map<String, Object> map) throws Exception {
        return mapper.list(map);
    }

    @Override
    public int count(Map<String, Object> map) throws Exception {
        return mapper.count(map);
    }

    @Override
    public void delete(int id) throws Exception {
        mapper.delete(id);
    }

    @Override
    public void update(LabelLogEntity entity) throws Exception {
        mapper.update(entity);
    }

    @Override
    public void insert(LabelLogEntity entity) throws Exception {
        mapper.insert(entity);
    }
}
