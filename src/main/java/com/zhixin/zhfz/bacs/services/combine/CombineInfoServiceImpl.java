package com.zhixin.zhfz.bacs.services.combine;

import com.zhixin.zhfz.bacs.dao.combine.CombineInfoMapper;
import com.zhixin.zhfz.bacs.entity.CombineInfoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CombineInfoServiceImpl implements  CombineInfoService{
    @Autowired
    private CombineInfoMapper combineInfoMapper;
    @Override
    public void insert(CombineInfoEntity entity) throws Exception {
        combineInfoMapper.insertSelective(entity);
    }
}
