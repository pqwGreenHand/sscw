package com.zhixin.zhfz.bacs.services.room;

import com.zhixin.zhfz.bacs.dao.room.IRoomGroupMapper;
import com.zhixin.zhfz.bacs.entity.RoomGroupEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class RoomGroupServiceImpl implements IRoomGroupService{
	
	@Autowired
	private IRoomGroupMapper roomGroupMapper;

	@Override
	public List<RoomGroupEntity> list(Map<String, Object> map) throws Exception {
		return roomGroupMapper.list(map);
	}

	@Override
	public void insert(RoomGroupEntity entity) throws Exception {
		roomGroupMapper.insert(entity);		
	}

	@Override
	public void delete(int id) throws Exception {
		roomGroupMapper.delete(id);
		}

	@Override
	public void update(RoomGroupEntity entity) throws Exception {
		roomGroupMapper.update(entity);		
	}

	@Override
	public int count(Map<String, Object> map) throws Exception {
		return roomGroupMapper.count(map);
	}

	
	
}
