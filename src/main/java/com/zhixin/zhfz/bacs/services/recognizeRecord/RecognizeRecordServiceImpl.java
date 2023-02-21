package com.zhixin.zhfz.bacs.services.recognizeRecord;

import com.zhixin.zhfz.bacs.dao.recognizeRecord.IRecognizeRecordMapper;
import com.zhixin.zhfz.bacs.entity.RecognizeRecordEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class RecognizeRecordServiceImpl implements IRecognizeRecordService {

    @Autowired
    private IRecognizeRecordMapper recognizeMapper;

    @Override
    public List<RecognizeRecordEntity> list(Map<String, Object> map) {
        return recognizeMapper.list(map);
    }

    @Override
    public int count(Map<String, Object> map) {
        return recognizeMapper.count(map);
    }

    @Override
    public int getMaxId() {
        return recognizeMapper.getMaxId();
    }

    @Override
    public void insert(RecognizeRecordEntity entity) {
        recognizeMapper.insert(entity);
    }

    @Override
    public void update(RecognizeRecordEntity entity) {
        recognizeMapper.update(entity);
    }
}
