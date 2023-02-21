package com.zhixin.zhfz.sacw.services.sapositiondata;

import com.zhixin.zhfz.sacw.dao.sapositiondata.ISaPositionDataMapper;
import com.zhixin.zhfz.sacw.entity.SaPositionDataEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SaPositionDataServiceImpl implements ISaPositionDataService {

    @Resource
    private ISaPositionDataMapper mapper;

    @Override
    public void insert(SaPositionDataEntity entity) throws Exception {
        mapper.insert(entity);
    }
}
