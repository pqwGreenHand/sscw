package com.zhixin.zhfz.sacw.dao.input;

import com.zhixin.zhfz.sacw.entity.InRecordPhotoEntity;
import com.zhixin.zhfz.sacw.entity.InputEntity;

import java.util.List;
import java.util.Map;


public interface IInputMapper {

    public List<InputEntity> list(Map<String, Object> map) throws Exception;

    public int count(Map<String, Object> map) throws Exception;

    void insert(InputEntity entity) throws Exception;


}
