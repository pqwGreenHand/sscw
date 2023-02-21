package com.zhixin.zhfz.bacs.dao.room;



import com.zhixin.zhfz.bacs.entity.RoomTypeEntity;

import java.util.List;
import java.util.Map;

public interface IRoomTypeMapper {
	//返回所有RoomTypeEntiy的集合
	List<RoomTypeEntity> list(Map<String, Object> map)throws Exception;
	//新增一个RoomTypeEntiy对象
	void insert(RoomTypeEntity entity)throws Exception;
	//删除一个RoomTypeEntiy对象
	void delete(int id)throws Exception;
	//修改RoomTypeEntiy对象
	void update(RoomTypeEntity entity)throws Exception;
	//查总数
	int count(Map<String, Object> map)throws Exception;
	//根据name字段的模糊查询
	List<RoomTypeEntity> listAllroomType(Map<String, Object> params);
}
