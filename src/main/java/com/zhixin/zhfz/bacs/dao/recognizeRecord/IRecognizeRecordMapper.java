package com.zhixin.zhfz.bacs.dao.recognizeRecord;

import com.zhixin.zhfz.bacs.entity.RecognizeRecordEntity;

import java.util.List;
import java.util.Map;

public interface IRecognizeRecordMapper {
    
    List<RecognizeRecordEntity> list(Map<String, Object> map);

    int count(Map<String, Object> map);
    int getMaxId();

    void insert(RecognizeRecordEntity entity);

    void update(RecognizeRecordEntity entity);
}
