package com.zhixin.zhfz.bacs.services.room;


import com.zhixin.zhfz.bacs.entity.RoomEntity;

import java.util.Map;

public interface IRoomService {
	void updateRoomStatus(int roomId, int status, String roomName);
	void updateRoomStatus(int roomId, int status);
	void stopRecordStatus(int roomId, int status, String roomName);

	RoomEntity getRoomById(Long roomId);

	RoomEntity findRoomByIps(Map<String, Object> map);
}
