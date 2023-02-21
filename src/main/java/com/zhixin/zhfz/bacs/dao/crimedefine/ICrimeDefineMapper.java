package com.zhixin.zhfz.bacs.dao.crimedefine;

import com.zhixin.zhfz.bacs.entity.CrimeDefineEntity;

import java.util.List;
import java.util.Map;

public interface ICrimeDefineMapper {
	/**
	 * 查询及条件查询
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	List<CrimeDefineEntity> list(Map<String, Object> map) throws Exception;

	
	int getCrimeTypeId(Map<String, Object> map) throws Exception;
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
	void update(CrimeDefineEntity entity);

	/**
	 * 插入方法
	 * 
	 * @param entity
	 * @throws Exception
	 */
	void insert(CrimeDefineEntity entity) throws Exception;
	
	
	/**
	 * 更新热度
	 * @param entity
	 */
	void updateSortNo(CrimeDefineEntity entity);

}
