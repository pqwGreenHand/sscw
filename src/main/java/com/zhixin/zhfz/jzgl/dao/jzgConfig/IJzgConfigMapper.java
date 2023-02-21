package com.zhixin.zhfz.jzgl.dao.jzgConfig;

import java.util.List;
import java.util.Map;

import com.zhixin.zhfz.jzgl.entity.JzgEntity;
import com.zhixin.zhfz.jzgl.entity.JzgGmxxEntity;

public interface IJzgConfigMapper {

	// 查询 柜门信息
	public List<JzgEntity> queryAllJzgGmxx(Map<String, Object> map);

	// 查询 柜门数量
	public int countAllJzgGmxx(Map<String, Object> map);
	
	void insertGmxx(JzgEntity entity);

	void updateGmxx(JzgEntity entity);
	
	// 一键插入柜门
	void insertAllJzgGmxx(JzgGmxxEntity gmxxEntity);
}
