package com.zhixin.zhfz.bacs.services.serial;

import java.util.List;

import com.zhixin.zhfz.bacs.entity.TempOutEntity;

public interface TempOutService {
    void insert(TempOutEntity entity) throws Exception;
    void update(TempOutEntity entity) throws Exception;
    List<TempOutEntity> queryBySerialId(Integer serialId);
}
