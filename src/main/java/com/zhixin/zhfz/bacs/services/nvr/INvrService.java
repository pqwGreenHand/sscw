package com.zhixin.zhfz.bacs.services.nvr;

import com.zhixin.zhfz.bacs.entity.NvrEntity;

import java.util.List;
import java.util.Map;

public interface INvrService {
	/**
	 * 查询及条件查询
	 * @param map
	 * @return
	 * @throws Exception
	 */
	List<NvrEntity> list(Map<String, Object> map)throws Exception;
	
	List<NvrEntity> comboNvr(Map<String, Object> map)throws Exception;
	/**
	 * 分页
	 * @param map
	 * @return
	 * @throws Exception
	 */
	int count(Map<String, Object> map)throws Exception;
	/**
	 * 删除
	 * @param id
	 * @throws Exception
	 */
	void delete(int id)throws Exception;
	
	/**
	 * 删除
	 * @param id
	 * @throws Exception
	 */
	void deleteCameraByNvrId(int id)throws Exception;
	/**
	 * 修改
	 * @param entity
	 * @throws Exception
	 */
	void update(NvrEntity entity)throws Exception;
	/**
	 * 插入方法
	 * @param entity
	 * @throws Exception
	 */
	void insert(NvrEntity entity)throws Exception;

	/**
	 * 插入方法
	 * @param nvrList
	 * @throws Exception
	 */
	void insertList(List<NvrEntity> nvrList)throws Exception;
}
