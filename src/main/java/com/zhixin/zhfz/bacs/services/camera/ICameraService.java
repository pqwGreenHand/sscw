package com.zhixin.zhfz.bacs.services.camera;

import com.zhixin.zhfz.bacs.entity.CameraEntity;

import java.util.List;
import java.util.Map;



public interface ICameraService {
	/**
	 * 查询及条件查询
	 * @param map
	 * @return
	 * @throws Exception
	 */
	List<CameraEntity> list(Map<String, Object> map)throws Exception;
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
	 * 修改
	 * @param entity
	 * @throws Exception
	 */
	void update(CameraEntity entity)throws Exception;
	/**
	 * 插入方法
	 * @param entity
	 * @throws Exception
	 */
	void insert(CameraEntity entity)throws Exception;

	/**
	 * 批量插入方法
	 * @param cameraList
	 * @throws Exception
	 */
	void insertList(List<CameraEntity> cameraList)throws Exception;
	
	List<CameraEntity> getCameraByRoomID(Map<String, Object> map)throws Exception;
}
