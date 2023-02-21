package com.zhixin.zhfz.sacw.services.unowner;


import com.zhixin.zhfz.sacw.dao.unowner.IUnOwnerMapper;
import com.zhixin.zhfz.sacw.entity.UnOwnerEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UnOwnerServiceImpl implements IUnOwnerService {

    @Autowired
    IUnOwnerMapper mapper;

    @Override
    public List<UnOwnerEntity> list(Map<String, Object> map) throws Exception {
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
    public void update(UnOwnerEntity entity) throws Exception {
        mapper.update(entity);
    }

    @Override
    public void change(UnOwnerEntity entity) throws Exception {
        mapper.change(entity);
    }

    @Override
    public void insert(UnOwnerEntity entity) throws Exception {
        mapper.insert(entity);
    }

    @Override
    public List<UnOwnerEntity> listRecordByInvolvedId(Map<String, Object> map) throws Exception {
        return mapper.listRecordByInvolvedId(map);
    }

    @Override
    public int countRecord(Map<String, Object> map) throws Exception {
        return mapper.countRecord(map);
    }

    @Override
    public List<UnOwnerEntity> listInRecordPhoto(Map<String, Object> map) throws Exception {
        return mapper.listInRecordPhoto(map);
    }

    @Override
    public List<UnOwnerEntity> listOutRecordPhoto(Map<String, Object> map) throws Exception {
        return mapper.listOutRecordPhoto(map);
    }

}
