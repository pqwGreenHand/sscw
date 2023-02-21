package com.zhixin.zhfz.sacw.dao.cwalarm;

import com.zhixin.zhfz.sacw.entity.CwAlarmEntity;

import java.util.List;
import java.util.Map;

public interface ICwAlarmDao {
    List<CwAlarmEntity> list(Map<String, Object> map) throws Exception;

    // 查询 超期未入库
    List<CwAlarmEntity> quyTimeOutListByParam(int day1) throws Exception;

    // 查询 借出超时
    List<CwAlarmEntity> quyLendListByParam(int day2) throws Exception;

    void addAlarm(CwAlarmEntity caEntity) throws Exception;

    int count(Map<String, Object> map) throws Exception;

    /**
     * 修改处理内容
     *
     * @param entity
     */
    void updateAlarmById(CwAlarmEntity entity);
}
