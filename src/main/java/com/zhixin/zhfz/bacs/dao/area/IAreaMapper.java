package com.zhixin.zhfz.bacs.dao.area;



import com.zhixin.zhfz.bacs.entity.AreaEntity;

import java.util.List;
import java.util.Map;


public interface IAreaMapper {

	void deleteArea(Long id);

	void deleteAreaOnly(Long id);

	int count(Map<String, Object> params);

	void insertArea(AreaEntity entity);

	void updateArea(AreaEntity entity);

	List<AreaEntity> listAllArea(Map<String, Object> map);

	List<AreaEntity> listAreaByOrgStr(Map<String, Object> map);
	
	AreaEntity getAreaById(Long id);

	AreaEntity get(Map<String, Object> map);

}