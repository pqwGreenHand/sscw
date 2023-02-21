package com.zhixin.zhfz.sacw.services.label;

import com.zhixin.zhfz.sacw.dao.label.ILabelMapper;
import com.zhixin.zhfz.sacw.entity.LabelEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class LabelServiceImpl implements ILabelService {

    @Autowired
    ILabelMapper mapper;

    @Override
    public List<LabelEntity> list(Map<String, Object> map) throws Exception {
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
    public void update(LabelEntity entity) throws Exception {
        mapper.update(entity);
    }

    @Override
    public void insert(LabelEntity entity) throws Exception {
        mapper.insert(entity);
    }

    @Override
    public List<LabelEntity> listLabelByStatus(Map<String, Object> map) throws Exception {
        return mapper.listLabelByStatus(map);
    }

    @Override
    public List<LabelEntity> listAll() throws Exception {
        return mapper.listAll();
    }

    @Override
    public LabelEntity getLabelByNo(Map<String, Object> params) throws Exception {
        return mapper.getLabelByNo(params);
    }

    @Override
    public LabelEntity getLabelByIcNo(Map<String, Object> params) throws Exception {
        return mapper.getLabelByIcNo(params);
    }


}
