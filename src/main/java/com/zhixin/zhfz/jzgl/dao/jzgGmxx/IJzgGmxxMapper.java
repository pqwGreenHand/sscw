package com.zhixin.zhfz.jzgl.dao.jzgGmxx;

import java.util.List;
import java.util.Map;

import com.zhixin.zhfz.jzgl.entity.JzgGmxxEntity;

public interface IJzgGmxxMapper {
	
	List<JzgGmxxEntity> getGmByUserId(Long id);

	List<Map<String, String>> listJzgGmxxData(String jzgId);
	
	List<Map<String, Object>> listJzgGmxx(String jzgId);
	
	List<JzgGmxxEntity> queryAllJzgGmxx(Map<String, Object> map);

	int countAllJzgGmxx(Map<String, Object> map);
	
	void insertGmxx(JzgGmxxEntity entity);
	
	void updateGmxx(JzgGmxxEntity entity);
}
