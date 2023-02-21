package com.zhixin.zhfz.bacs.services.recognizeRecord;

import com.zhixin.zhfz.bacs.entity.RecognizeRecordEntity;

import java.util.List;
import java.util.Map;

public interface IRecognizeRecordService {

    List<RecognizeRecordEntity> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    int getMaxId();

    void insert(RecognizeRecordEntity entity);

    void update(RecognizeRecordEntity entity);
}
