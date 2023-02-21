package com.zhixin.zhfz.jzgl.dao.jzgGmxx;

import java.util.List;
import java.util.Map;

import com.zhixin.zhfz.jzgl.entity.JzgGxEntity;

public interface IJzgGxMapper {
	
	List<JzgGxEntity> queryAllUserGmxx(Map<String, Object> map);

	int countAllUserGmxx(Map<String, Object> map);
	
	/**
	 * 柜门与用户的分配关系添加
	 * @param entity
	 */
	void insertJzgGx(JzgGxEntity entity);
	/**
	 * 柜门与用户的分配关系删除
	 * @param id
	 */
	void deletejzgGxById(Long id);
	
	/**
	 * 柜门与用户的分配关系设置
	 * @param entity
	 */
	void updateJzgGxGmxs(JzgGxEntity entity);
	
	void deleteGxByjzgGmxxId(Long jzgGmxxId);
}
