package com.zhixin.zhfz.bacs.services.urinalysis;

import com.zhixin.zhfz.bacs.entity.UrinalysisDocProcessEntity;
import com.zhixin.zhfz.bacs.entity.UrinalysisEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface UrinalysisService  {

    public List<UrinalysisEntity> listAllUrinalysis() throws Exception;

    public Integer countAllUrinalysis() throws Exception;

    public List<UrinalysisEntity> listUrinalysis(Map<String,Object> map) throws Exception;

    public Integer countUrinalysis(Map<String,Object> map) throws Exception;

    public Integer count(Map<String,Object> map) throws Exception;

    public void insertUrinalysis(UrinalysisEntity entity) throws Exception;

    public void updateUrinalysis(UrinalysisEntity entity) throws Exception;

    public void deleteUrinalysisById(Long id) throws Exception;

    public UrinalysisDocProcessEntity getProcessData(Long urinalysisId, HttpServletRequest request);
}
