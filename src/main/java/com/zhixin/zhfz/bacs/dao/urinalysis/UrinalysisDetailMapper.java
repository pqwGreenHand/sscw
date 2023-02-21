package com.zhixin.zhfz.bacs.dao.urinalysis;


import com.zhixin.zhfz.bacs.entity.UrinalysisDetailEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UrinalysisDetailMapper {
    int deleteByPrimaryKey(Integer id) throws Exception;

    int insert(UrinalysisDetailEntity record) throws Exception;

    public void insertList(@Param("urinalysisDetailList") List<UrinalysisDetailEntity> urinalysisDetailList) throws Exception;

    int insertSelective(UrinalysisDetailEntity record) throws Exception;

    UrinalysisDetailEntity selectByPrimaryKey(Integer id) throws Exception;

    int updateByPrimaryKeySelective(UrinalysisDetailEntity record) throws Exception;

    int updateByPrimaryKey(UrinalysisDetailEntity record) throws Exception;

    void deleteByUrinalysisId(long urinalysisId) throws Exception;
}