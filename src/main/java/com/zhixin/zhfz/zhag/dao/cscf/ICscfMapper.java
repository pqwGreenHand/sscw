package com.zhixin.zhfz.zhag.dao.cscf;

import java.util.List;
import java.util.Map;

import com.zhixin.zhfz.zhag.entity.CscfEntity;

public interface ICscfMapper {
	 
	List<CscfEntity> selectCscf(Map<String, Object> map);
	 
	Integer countCscf(Map<String, Object> map);
	
	void addCscf(CscfEntity entity);
	
	void editCscf(CscfEntity entity);
}
