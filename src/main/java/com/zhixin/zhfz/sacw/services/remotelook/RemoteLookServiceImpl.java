package com.zhixin.zhfz.sacw.services.remotelook;

import com.zhixin.zhfz.sacw.dao.remotelook.IRemoteLookMapper;
import com.zhixin.zhfz.sacw.entity.RemoteLookEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class RemoteLookServiceImpl implements IRemoteLookService {

    @Autowired
    IRemoteLookMapper mapper;

    @Override
    public List<RemoteLookEntity> list(Map<String, Object> map) throws Exception {
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
    public void update(RemoteLookEntity entity) throws Exception {
        mapper.update(entity);
    }

    @Override
    public void insert(RemoteLookEntity entity) throws Exception {
        mapper.insert(entity);
    }

    @Override
    public List<RemoteLookEntity> listById(Map<String, Object> map) throws Exception {
        return mapper.listById(map);
    }

    @Override
    public List<RemoteLookEntity> queryRequestInvolved(Map<String, Object> map) throws Exception {
        return mapper.queryRequestInvolved(map);
    }


}
