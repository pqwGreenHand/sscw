package com.zhixin.zhfz.sacw.dao.unowner;

import com.zhixin.zhfz.sacw.entity.UnOwnerEntity;

import java.util.List;
import java.util.Map;

public interface IUnOwnerMapper {

    List<UnOwnerEntity> list(Map<String, Object> map) throws Exception;

    int count(Map<String, Object> map) throws Exception;

    void delete(int id) throws Exception;

    void update(UnOwnerEntity entity) throws Exception;

    void change(UnOwnerEntity entity) throws Exception;

    void insert(UnOwnerEntity entity) throws Exception;

    List<UnOwnerEntity> listRecordByInvolvedId(Map<String, Object> map) throws Exception;

    int countRecord(Map<String, Object> map) throws Exception;

    List<UnOwnerEntity> listInRecordPhoto(Map<String, Object> map) throws Exception;

    List<UnOwnerEntity> listOutRecordPhoto(Map<String, Object> map) throws Exception;

}
