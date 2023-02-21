package com.zhixin.zhfz.bacs.services.room;
import com.zhixin.zhfz.bacs.entity.RoomGroupEntity;

import java.util.List;
import java.util.Map;

public interface IRoomGroupService {

	List<RoomGroupEntity> list(Map<String, Object> map)throws Exception;

	void insert(RoomGroupEntity entity)throws Exception;

	void delete(int id)throws Exception;

	void update(RoomGroupEntity entity)throws Exception;

	int count(Map<String, Object> map)throws Exception;


}
