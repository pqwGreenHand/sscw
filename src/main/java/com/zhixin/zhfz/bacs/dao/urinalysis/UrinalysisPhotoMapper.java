package com.zhixin.zhfz.bacs.dao.urinalysis;


import com.zhixin.zhfz.bacs.entity.UrinalysisPhotoEntity;

import java.util.List;

public interface UrinalysisPhotoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UrinalysisPhotoEntity record);

    int insertSelective(UrinalysisPhotoEntity record);

    UrinalysisPhotoEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UrinalysisPhotoEntity record);

    int updateByPrimaryKey(UrinalysisPhotoEntity record);

    List<UrinalysisPhotoEntity> getPhotoByUrinalysisId(Long urinalysisId );

    void deleteByUrinalysisId(long urinalysisId) throws Exception;
}