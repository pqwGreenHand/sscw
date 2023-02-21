package com.zhixin.zhfz.bacs.services.room;

import com.zhixin.zhfz.bacs.entity.RoomTypeEntity;

import java.util.List;
import java.util.Map;

public interface IRoomTypeService {
	//查询所有
	List<RoomTypeEntity> list(Map<String, Object> map)throws Exception;
	//添加
	void insert(RoomTypeEntity entity)throws Exception;
	//删除
	void delete(int id)throws Exception;
	//修改
	void update(RoomTypeEntity entity)throws Exception;
	//查总数
	int count(Map<String, Object> map)throws Exception;
	//模糊查询
	List<RoomTypeEntity> listAllroomType(Map<String, Object> params);
}
