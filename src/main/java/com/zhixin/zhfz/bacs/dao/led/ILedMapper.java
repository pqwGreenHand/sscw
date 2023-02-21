package com.zhixin.zhfz.bacs.dao.led;

import com.zhixin.zhfz.bacs.entity.LedEntity;

import java.util.List;
import java.util.Map;

public interface ILedMapper {
	/**
	 * 查询及条件查询
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	List<LedEntity> list(Map<String, Object> map) throws Exception;

	/**
	 * 分页
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	int count(Map<String, Object> map) throws Exception;

	/**
	 * 删除
	 * 
	 * @param id
	 * @throws Exception
	 */
	void delete(int id) throws Exception;

	/**
	 * 修改
	 * 
	 * @param entity
	 * @throws Exception
	 */
	void update(LedEntity entity) throws Exception;

	/**
	 * 插入方法
	 * 
	 * @param entity
	 * @throws Exception
	 */
	void insert(LedEntity entity) throws Exception;
	
	
	LedEntity LedByRoomId(int roomId)  throws Exception;

    List<LedEntity> LedByRoom();
}
