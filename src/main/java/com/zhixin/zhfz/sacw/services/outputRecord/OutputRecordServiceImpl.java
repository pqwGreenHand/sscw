package com.zhixin.zhfz.sacw.services.outputRecord;


import com.zhixin.zhfz.sacw.dao.outputRecord.IOutputRecordMapper;
import com.zhixin.zhfz.sacw.entity.OutputRecordEntity;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class OutputRecordServiceImpl implements IOutputRecordService {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(OutputRecordServiceImpl.class);

    @Autowired
    private IOutputRecordMapper outputRecordMapper;

    @Override
    public List<OutputRecordEntity> listOutputRecord(Map<String, Object> map) throws Exception {
        return outputRecordMapper.listOutputRecord(map);
    }

    @Override
    public Integer countOutputRecord(Map<String, Object> map) throws Exception {
        return outputRecordMapper.countOutputRecord(map);
    }

    @Override
    public void insertOutputRecord(OutputRecordEntity outputRecord) throws Exception {
        outputRecordMapper.insertOutputRecord(outputRecord);
    }

    @Override
    public void updateOutputRecordByInvolvedId(Map<String, Object> map) throws Exception {
        outputRecordMapper.updateOutputRecordByInvolvedId(map);
    }

    @Override
    public List<OutputRecordEntity> getOutputRecordByInvolvedId(Integer involvedId) throws Exception {
        return outputRecordMapper.getOutputRecordByInvolvedId(involvedId);
    }


}
