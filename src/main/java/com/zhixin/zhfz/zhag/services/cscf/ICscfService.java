package com.zhixin.zhfz.zhag.services.cscf;

import java.util.List;
import java.util.Map;

import com.zhixin.zhfz.zhag.entity.CscfEntity;

public interface ICscfService {
	
	/**
	 * 强制措施
	 * @param map
	 * @return
	 */
	List<CscfEntity> selectCscf(Map<String, Object> map);
	/**
	 * 强制措施count
	 * @param map
	 * @return
	 */
	Integer countCscf(Map<String, Object> map);
	
	/**
	 * 新增强制措施
	 * @param entity
	 */
	void addCscf(CscfEntity entity);
	
	/**
	 * 强制措施修改
	 * @param entity
	 */
    void editCscf(CscfEntity entity);
}
