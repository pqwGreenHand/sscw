package com.zhixin.zhfz.sacw.dao.outputRecord;


import com.zhixin.zhfz.sacw.entity.OutputRecordEntity;

import java.util.List;
import java.util.Map;

public interface IOutputRecordMapper {

    public List<OutputRecordEntity> listOutputRecord(Map<String, Object> map) throws Exception;

    public Integer countOutputRecord(Map<String, Object> map) throws Exception;

    public void insertOutputRecord(OutputRecordEntity outputRecord) throws Exception;

    public void updateOutputRecordByInvolvedId(Map<String, Object> map) throws Exception;

    public List<OutputRecordEntity> getOutputRecordByInvolvedId(Integer involvedId) throws Exception;

}