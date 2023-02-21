package com.zhixin.zhfz.jzgl.services.jzgGmxx;

import java.util.List;
import java.util.Map;

import com.zhixin.zhfz.jzgl.entity.JzgGmxxEntity;


public interface IJzgGmxxService {
	
	/**
	 * 根据用户查询柜子信息
	 * @param id
	 * @return
	 */
	List<JzgGmxxEntity> getGmByUserId(Long id);
	/**
	 * 根据用户查询柜子信息
	 * @param jzgId
	 * @return
	 */
	List<Map<String, String>> listJzgGmxxData(String jzgId);
	/**
	 * 查询卷柜
	 * @param jzgId
	 * @return
	 */
	List<List<Integer>> listJzgGmxx(String jzgId);
	
	 /**
	  * 柜门分配查询
	  * @param map
	  * @return
	  */
    List<JzgGmxxEntity> queryAllJzgGmxxAll(Map<String, Object> map);

    /**
     * 柜门分配查询总数
     * @param map
     * @return
     */
    int countAllJzgGmxx(Map<String, Object> map);
    
	
	/**
	 * 添加卷宗柜柜门信息
	 * @param entity
	 */
	void insertGmxxAll(JzgGmxxEntity entity);
	
	/**
	 * 卷宗柜柜门信息删除
	 * @param entity
	 */
	void updateGmxx(JzgGmxxEntity entity);
}
