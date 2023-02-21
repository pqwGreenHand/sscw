package com.zhixin.zhfz.sacw.services.handjcy;

import com.zhixin.zhfz.sacw.dao.handjcy.IHandJcyMapper;
import com.zhixin.zhfz.sacw.entity.InvolvedEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class HandJcyServiceImpl implements IHandJcyService {

    @Autowired
    IHandJcyMapper mapper;

    @Override
    public List<InvolvedEntity> list(Map<String, Object> map) throws Exception {
        return mapper.list(map);
    }

    @Override
    public int count(Map<String, Object> map) throws Exception {
        return mapper.count(map);
    }

    @Override
    public List<InvolvedEntity> listOutputRecordById(Map<String, Object> map) throws Exception {
        return mapper.listOutputRecordById(map);
    }

    @Override
    public int recordCount(Map<String, Object> map) throws Exception {
        return mapper.recordCount(map);
    }


}
