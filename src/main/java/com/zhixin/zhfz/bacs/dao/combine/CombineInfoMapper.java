package com.zhixin.zhfz.bacs.dao.combine;


import com.zhixin.zhfz.bacs.entity.CombineInfoEntity;

public interface CombineInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CombineInfoEntity record);

    int insertSelective(CombineInfoEntity record);

    CombineInfoEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CombineInfoEntity record);

    int updateByPrimaryKey(CombineInfoEntity record);
}