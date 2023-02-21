package com.zhixin.zhfz.jzgl.services.jzgGmxx;


import java.util.List;
import java.util.Map;

import com.zhixin.zhfz.jzgl.entity.JzgGxEntity;


public interface IJzgGxService {
	
	/**
     * 用户柜门分配
     * @param map
     * @return
     */
    List<JzgGxEntity> queryAllUserGmxx(Map<String, Object> map);

    /**
     * 用户柜门分配总数
     * @param map
     * @return
     */
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
	
	/**
	 * 柜门与用户的分配关系通过GmxxId删除
	 * @param jzgGmxxId
	 */
	void deleteGxByjzgGmxxId(Long jzgGmxxId);
}
