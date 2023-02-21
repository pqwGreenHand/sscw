package com.zhixin.zhfz.sacw.services.sacamera;

import com.zhixin.zhfz.sacw.dao.sacamera.ISaCameraMapper;
import com.zhixin.zhfz.sacw.entity.SaCameraEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class SaCameraServiceImpl implements ISaCameraService {

    @Autowired
    ISaCameraMapper mapper;

    @Override
    public List<SaCameraEntity> list(Map<String, Object> map) throws Exception {
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
    public void update(SaCameraEntity entity) throws Exception {
        mapper.update(entity);
    }

    @Override
    public void insert(SaCameraEntity entity) throws Exception {
        mapper.insert(entity);
    }

    @Override
    public List<SaCameraEntity> getCameraByRoomID(Map<String, Object> map) throws Exception {
        return mapper.getCameraByRoomID(map);
    }

    @Override
    public void insertList(List<SaCameraEntity> cameraList) throws Exception {
        mapper.insertList(cameraList);
    }
}
