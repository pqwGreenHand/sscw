package com.zhixin.zhfz.bacs.services.clue;

import com.zhixin.zhfz.bacs.entity.CaseDataClueEntity;

import java.util.List;
import java.util.Map;

public interface ICaseDataClueService {
	/**
	 * 查询及条件查询
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	List<CaseDataClueEntity> list(Map<String, Object> map) throws Exception;

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
	void update(CaseDataClueEntity entity) throws Exception;

	/**
	 * 插入方法
	 * 
	 * @param entity
	 * @throws Exception
	 */
	void insert(CaseDataClueEntity entity) throws Exception;
	/**
	 * 根据id查询方法
	 * @param entity
	 * @throws Exception
	 */
	CaseDataClueEntity selectOneById(Map<String, Object> map)throws Exception;
	
}
