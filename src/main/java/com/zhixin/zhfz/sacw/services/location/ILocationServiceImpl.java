package com.zhixin.zhfz.sacw.services.location;

import com.zhixin.zhfz.sacw.dao.location.ILocationMapper;
import com.zhixin.zhfz.sacw.entity.LocationEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ILocationServiceImpl implements ILocationService {

    @Autowired
    private ILocationMapper locationMapper;

    @Override
    public List<LocationEntity> listLocation(Map<String, Object> map) throws Exception {

        return locationMapper.listLocation(map);
    }

    @Override
    public List<LocationEntity> listLocationByWarehouse(Map<String, Object> map) throws Exception {
        return locationMapper.listLocationByWarehouse(map);
    }

    @Override
    public void insertLocation(LocationEntity locationEntity) throws Exception {
        locationMapper.insertLocation(locationEntity);
    }

    @Override
    public void deleteLocation(int id) throws Exception {
        locationMapper.deleteLocation(id);
    }

    @Override
    public void updateLocation(LocationEntity locationEntity) throws Exception {
        locationMapper.updateLocation(locationEntity);
    }

    @Override
    public int count(Map<String, Object> map) throws Exception {

        return locationMapper.count(map);
    }

    @Override
    public List<LocationEntity> findLocationByWarehouse(Map<String, Object> map) throws Exception {
        return locationMapper.findLocationByWarehouse(map);
    }

    @Override
    public int qureyEntityByPram(LocationEntity entity){
        int is = 0;
        List<LocationEntity> entityList = locationMapper.qureyEntityByPram(entity);
        if(!entityList.isEmpty() && entityList.size() != 0){
            is = 1;
        }
        return is;
    }
}
