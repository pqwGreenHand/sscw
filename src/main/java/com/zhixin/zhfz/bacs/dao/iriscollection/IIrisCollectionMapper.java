package com.zhixin.zhfz.bacs.dao.iriscollection;

import com.zhixin.zhfz.bacs.entity.IrisEntity;

import java.util.List;
import java.util.Map;

public interface IIrisCollectionMapper {
    
    List<IrisEntity> list(Map<String, Object> map);

    int count(Map<String, Object> map);
}
