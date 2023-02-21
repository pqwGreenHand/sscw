package com.zhixin.zhfz.bacs.dao.positionManage;

import com.zhixin.zhfz.bacs.entity.PositionDataEntity;
import com.zhixin.zhfz.bacs.entity.RoomEntity;

import java.util.List;
import java.util.Map;

public interface IPositionManageMapper {

	List<Map<String, Object>> selectPositionDataPersonInfo(Map<String, Object> map);

	int selectPositionDataPersonInfoCount(Map<String, Object> map);

	List<Map<String, Object>> selectPositionDataPoliceInfo(Map<String, Object> map);

	int selectPositionDataPoliceInfoCount(Map<String, Object> map);

	List<PositionDataEntity> list(Map<String, Object> map);

	int count(Map<String, Object> map);

	List<Map<String, Object>> selectPositionDataSearialInfo(Map<String, Object> map);

	int selectPositionDataSearialInfoCount(Map<String, Object> map);

	
}