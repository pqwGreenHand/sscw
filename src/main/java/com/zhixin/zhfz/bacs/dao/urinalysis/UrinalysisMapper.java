package com.zhixin.zhfz.bacs.dao.urinalysis;

import com.zhixin.zhfz.bacs.entity.UrinalysisEntity;

import java.util.List;
import java.util.Map;

public interface UrinalysisMapper {

    public List<UrinalysisEntity> listAllUrinalysis() throws Exception;

    public Integer countAllUrinalysis() throws Exception;

    public List<UrinalysisEntity> listUrinalysis(Map<String,Object> map) throws Exception;

    public Integer countUrinalysis(Map<String,Object> map) throws Exception;

    public Integer count(Map<String,Object> map) throws Exception;

    public void insertSelective(UrinalysisEntity urinalysisEntity);

    public void updateByPrimaryKeySelective(UrinalysisEntity urinalysisEntity);

    public void deleteUrinalysisById(Long id) throws Exception;

    public UrinalysisEntity getUrinalysisById(Long id) throws Exception;


}