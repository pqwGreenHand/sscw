package com.zhixin.zhfz.bacs.services.positionManage;

import com.zhixin.zhfz.bacs.entity.PositionAlarmEntity;

import java.util.List;
import java.util.Map;

public interface IPositioAlarmService {

	List<PositionAlarmEntity> list(Map<String, Object> map);

	int count(Map<String, Object> map);



}
