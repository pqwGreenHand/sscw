package com.zhixin.zhfz.bacs.services.room;

import com.zhixin.zhfz.bacs.dao.room.IRoomTypeMapper;
import com.zhixin.zhfz.bacs.entity.RoomTypeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class RoomTypeServiceImpl implements IRoomTypeService{
	
	@Autowired
	private IRoomTypeMapper roomTypeMapper;
	//查询所有
	@Override
	public List<RoomTypeEntity> list(Map<String, Object> map) throws Exception {
		return roomTypeMapper.list(map);
	}
	//添加
	@Override
	public void insert(RoomTypeEntity entity) throws Exception {
		roomTypeMapper.insert(entity);
	}
	//删除
	@Override
	public void delete(int id) throws Exception {
		roomTypeMapper.delete(id);		
	}
	//修改
	@Override
	public void update(RoomTypeEntity entity) throws Exception {
		roomTypeMapper.update(entity);		
	}
	//查总数
	@Override
	public int count(Map<String, Object> map) throws Exception {
		return roomTypeMapper.count(map);
	}
	//模糊查询
	@Override
	public List<RoomTypeEntity> listAllroomType(Map<String, Object> params) {

		return roomTypeMapper.listAllroomType(params);
	}
	
	
}
