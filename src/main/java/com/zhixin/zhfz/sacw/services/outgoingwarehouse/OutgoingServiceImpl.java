package com.zhixin.zhfz.sacw.services.outgoingwarehouse;


import com.zhixin.zhfz.sacw.dao.outgoingwarehouse.OutgoingWarehouseMapper;
import com.zhixin.zhfz.sacw.dao.outputRecord.IOutputRecordMapper;
import com.zhixin.zhfz.sacw.entity.InputEntity;
import com.zhixin.zhfz.sacw.entity.OutgoingEntity;
import com.zhixin.zhfz.sacw.entity.OutputRecordEntity;
import com.zhixin.zhfz.sacw.services.outputRecord.IOutputRecordService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class OutgoingServiceImpl implements OutgoingService {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(OutgoingServiceImpl.class);

    @Autowired
    private OutgoingWarehouseMapper outgoingWarehouseMapper;


    @Override
    public List<OutgoingEntity> listInvolved(Map<String, Object> map) throws Exception {
        return outgoingWarehouseMapper.listInvolved(map);
    }

    @Override
    public List<OutgoingEntity> listInvolvedDetailed(Map<String, Object> map) throws Exception {
        return outgoingWarehouseMapper.listInvolvedDetailed(map);
    }

    @Override
    public List<InputEntity> list(Map<String, Object> map) throws Exception {
        return outgoingWarehouseMapper.inputList(map);
    }

    @Override
    public int count(Map<String, Object> map) throws Exception {
        return outgoingWarehouseMapper.count(map);
    }
}
