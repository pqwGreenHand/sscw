package com.zhixin.zhfz.bacs.dao.positionManage;

import com.zhixin.zhfz.bacs.entity.PositionAlarmEntity;
import com.zhixin.zhfz.bacs.entity.PositionDataEntity;
import com.zhixin.zhfz.bacs.entity.PositionEntranceEntity;
import com.zhixin.zhfz.bacs.entity.RoomEntity;

import java.util.List;
import java.util.Map;

public interface IPositionEntranceMapper {

	List<PositionEntranceEntity> list(Map<String, Object> map);

	int count(Map<String, Object> map);


	
}