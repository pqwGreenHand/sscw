package com.zhixin.zhfz.bacs.services.iriscollection;

import com.zhixin.zhfz.bacs.entity.IrisEntity;

import java.util.List;
import java.util.Map;

public interface IIrisCollectionService {

    List<IrisEntity> list(Map<String, Object> map);

    int count(Map<String, Object> map);
}
