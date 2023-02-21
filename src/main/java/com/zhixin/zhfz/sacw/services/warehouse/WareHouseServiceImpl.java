package com.zhixin.zhfz.sacw.services.warehouse;


import com.zhixin.zhfz.sacw.dao.warehouse.IWareHouseMapper;
import com.zhixin.zhfz.sacw.entity.WareHouseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class WareHouseServiceImpl implements IWareHouseService {

    @Autowired
    private IWareHouseMapper wareHouseMapper;

    @Override
    public List<WareHouseEntity> listAllWareHouse(Map<String, Object> map) throws Exception {
        return wareHouseMapper.listAllWareHouse(map);
    }

    @Override
    public List<WareHouseEntity> AllWareHouse(Map<String, Object> map) throws Exception {
        return wareHouseMapper.AllWareHouse(map);
    }

    @Override
    public void insertWareHouse(WareHouseEntity wareHouseEntity) throws Exception {
        wareHouseMapper.insertWareHouse(wareHouseEntity);
    }

    @Override
    public void deleteWareHouse(int id) throws Exception {
        wareHouseMapper.deleteWareHouse(id);
        wareHouseMapper.deleteWareHouse1(id);
    }

    @Override
    public void updateWareHouse(WareHouseEntity wareHouseEntity) throws Exception {
        wareHouseMapper.updateWareHouse(wareHouseEntity);
    }

    @Override
    public int count(Map<String, Object> map) throws Exception {
        return wareHouseMapper.count(map);
    }

    @Override
    public List<WareHouseEntity> findAllWareHouse(Map<String, Object> map) throws Exception {
        return wareHouseMapper.findAllWareHouse(map);
    }

    @Override
    public int qureyEntityByPram(WareHouseEntity entity){
        int is = 0;
        List<WareHouseEntity> entityList = wareHouseMapper.qureyEntityByPram(entity);
        if(!entityList.isEmpty() && entityList.size() != 0){
            is = 1;
        }
        return is;
    }

}
