package com.zhixin.zhfz.bacs.dao.cuff;


import com.zhixin.zhfz.bacs.entity.CuffLogEntity;

public interface ICuffLogMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CuffLogEntity record);

    int insertSelective(CuffLogEntity record);

    CuffLogEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CuffLogEntity record);

    int updateByPrimaryKey(CuffLogEntity record);

    void insertLog(CuffLogEntity record);
}