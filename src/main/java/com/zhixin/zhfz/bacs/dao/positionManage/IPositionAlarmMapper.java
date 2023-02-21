package com.zhixin.zhfz.bacs.dao.positionManage;


import com.zhixin.zhfz.bacs.entity.PositionAlarmEntity;

import java.util.List;
import java.util.Map;

public interface IPositionAlarmMapper {

	List<PositionAlarmEntity> list(Map<String, Object> map);

	int count(Map<String, Object> map);


	List<PositionAlarmEntity> listAll(Map<String, Object> params);

	List<PositionAlarmEntity> selectalarm(Map<String, Object> params);

	Integer selectalarmcount(Map<String, Object> params);

	List<PositionAlarmEntity> selectalarmExcel(Map<String, Object> params);

    void insert(PositionAlarmEntity entity);
}