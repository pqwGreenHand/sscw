package com.zhixin.zhfz.jzgl.services.jzgConfig;

import java.util.List;
import java.util.Map;

import com.zhixin.zhfz.jzgl.entity.JzgEntity;
import com.zhixin.zhfz.jzgl.entity.JzgGmxxEntity;
import com.zhixin.zhfz.jzgl.entity.JzgLieEntity;

public interface IJzgConfigService {

	List<JzgEntity> queryAllJzgGmxx(Map<String, Object> map);

	int countAllJzgGmxx(Map<String, Object> map);

	List<JzgLieEntity> queryAllJzgLie(Map<String, Object> map);

	// 添加列信息
	void insertJzgLie(JzgLieEntity entity);

	void updateJzgLie(JzgLieEntity entity);

	void insertGmxx(JzgEntity entity);

	void updateGmxx(JzgEntity entity);

	// 一键插入柜门
	void insertAllJzgGmxx(JzgGmxxEntity gmxxEntity);

}
