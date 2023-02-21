package com.zhixin.zhfz.bacs.services.iriscollection;

import com.zhixin.zhfz.bacs.dao.iriscollection.IIrisCollectionMapper;
import com.zhixin.zhfz.bacs.entity.IrisEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class IrisCollectionServiceImpl  implements IIrisCollectionService{

    @Autowired
    private IIrisCollectionMapper irisCollectionMapper;


    @Override
    public List<IrisEntity> list(Map<String, Object> map) {
        return irisCollectionMapper.list(map);
    }

    @Override
    public int count(Map<String, Object> map) {
        return irisCollectionMapper.count(map);
    }
}
