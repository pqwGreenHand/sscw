package com.zhixin.zhfz.bacs.services.led;

import com.zhixin.zhfz.bacs.entity.LedEntity;

import java.util.List;

public interface ILedService {
	
	LedEntity LedByRoomId(int roomId)  throws Exception;
	
	void update(LedEntity entity)  throws Exception;
	
	void insert(LedEntity entity)  throws Exception;

    List<LedEntity> LedByRoom();
}
