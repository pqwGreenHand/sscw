package com.zhixin.zhfz.common.dao.signfile;


import com.zhixin.zhfz.common.entity.SignFileEntity;

import java.util.List;
import java.util.Map;

public interface SignFileMapper {
    int deleteByPrimaryKey(Integer id) throws Exception;

    int insert(SignFileEntity record);

    int insertSelective(SignFileEntity record)throws Exception;

    SignFileEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SignFileEntity record);

    int updateByPrimaryKey(SignFileEntity record);

    SignFileEntity getLastFileBySerialAndType(SignFileEntity record) throws Exception;

    List<SignFileEntity> getSignatureList(Map<String,Object> param) throws Exception;
}