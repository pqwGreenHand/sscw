package com.zhixin.zhfz.bacs.services.urinalysis;

import com.zhixin.zhfz.bacs.entity.UrinalysisDetailEntity;

import java.util.List;

public interface UrinalysisDetailService {

    public void insert(UrinalysisDetailEntity urinalysisDetailEntity) throws Exception;

    public void insertList(List<UrinalysisDetailEntity> urinalysisDetailList) throws Exception;

    public void deleteByUrinalysisId(long urinalysisId) throws Exception;
}
