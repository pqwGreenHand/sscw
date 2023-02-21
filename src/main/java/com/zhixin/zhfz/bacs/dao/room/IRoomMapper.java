package com.zhixin.zhfz.bacs.dao.room;

import com.zhixin.zhfz.bacs.entity.RoomEntity;

import java.util.List;
import java.util.Map;

public interface IRoomMapper {

	String getRecordLocationByRoomID(Long id);

	void updateRoom(RoomEntity entity);

	void deleteRoom(Long id);

	List<RoomEntity> listAllroom(Map<String, Object> map2);

	void insertRoom(RoomEntity entity);

	int count1(Map<String, Object> params);

	List<RoomEntity> listRoomByIp(Map<String, Object> param);

	List<RoomEntity> listDisRoomBySerialId(Map<String, Object> param);
	
	void updateRoomStatus(Map<String, Object> param);
	
	RoomEntity getRoomById(Long id);
	
	List<RoomEntity> countRoomByArea(Map<String, Object> map);

	RoomEntity findRoomByIps(Map<String, Object> map);
	
}