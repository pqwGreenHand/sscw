package com.zhixin.zhfz.bacs.services.led;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhixin.zhfz.bacs.dao.led.ILedMapper;
import com.zhixin.zhfz.bacs.entity.LedEntity;

import java.util.List;

@Service
public class LedServiceImpl implements ILedService {

	@Autowired 
	private ILedMapper ledMapper;
	
	@Override
	public LedEntity LedByRoomId(int roomId) throws Exception {
		return ledMapper.LedByRoomId(roomId);
	}


	@Override
	public void insert(LedEntity entity) throws Exception {
		ledMapper.insert(entity); 
	}

	@Override
	public List<LedEntity> LedByRoom() {
		return ledMapper.LedByRoom();
	}


	@Override
	public void update(LedEntity entity) throws Exception {
		ledMapper.update(entity);
		
	}
	
	

}
