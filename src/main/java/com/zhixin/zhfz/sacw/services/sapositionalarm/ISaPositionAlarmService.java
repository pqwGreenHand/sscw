package com.zhixin.zhfz.sacw.services.sapositionalarm;

import com.zhixin.zhfz.sacw.entity.BoundAlarmEntity;
import com.zhixin.zhfz.sacw.entity.SaPositionAlarmEntity;

public interface ISaPositionAlarmService {

    void insert(BoundAlarmEntity entity) throws Exception;
}
