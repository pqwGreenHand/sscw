package com.zhixin.zhfz.bacsapp.services.entrance;

import com.zhixin.zhfz.bacsapp.dao.entrance.IEntranceMapper;
import com.zhixin.zhfz.bacsapp.entity.EntranceEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class EntranceServiceImpl implements IEntranceService {

    @Autowired
    private IEntranceMapper entranceMapper;


    @Override
    public List<EntranceEntity> entranceList(Map<String, Object> map) {
        return entranceMapper.entranceList(map);
    }

    @Override
    public int count(Map<String, Object> map) {
        return entranceMapper.count(map);
    }

    @Override
    public List<EntranceEntity> orderList(Map<String, Object> map) {
        return entranceMapper.orderList(map);
    }

    @Override
    public int orderCount(Map<String, Object> map) {
        return entranceMapper.orderCount(map);
    }

    @Override
    public List<EntranceEntity> personList(Map<String, Object> map) {
        return entranceMapper.personList(map);
    }

    @Override
    public List<EntranceEntity> orderListNew(Map<String, Object> map) {
        return entranceMapper.orderListNew(map);
    }

}
