package com.zhixin.zhfz.sacw.services.cwalarm;

import com.zhixin.zhfz.sacw.entity.CwAlarmEntity;

import java.util.List;
import java.util.Map;

/**
 * 查询列表
 */
public interface ICwAlarmService {
    List<CwAlarmEntity> list(Map<String, Object> map) throws Exception;

    List<CwAlarmEntity> quyTimeOutListByParam(int day1) throws Exception;

    List<CwAlarmEntity> quyLendListByParam(int day1) throws Exception;

    void addAlarm(CwAlarmEntity caEntity) throws Exception;

    int count(Map<String, Object> map) throws Exception;

    /**
     * 修改处理内容
     *
     * @param entity
     */
    void updateAlarmById(CwAlarmEntity entity);
}
