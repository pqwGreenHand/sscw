package com.zhixin.zhfz.bacs.dao.serial;

import java.util.List;

import com.zhixin.zhfz.bacs.entity.TempOutEntity;

public interface TempOutMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TempOutEntity record);

    void insertSelective(TempOutEntity record)  throws Exception;

    TempOutEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TempOutEntity record);

    int updateByPrimaryKey(TempOutEntity record);

    void update(TempOutEntity entity) throws Exception;
    
    List<TempOutEntity> queryBySerialId(Integer serialId);
}