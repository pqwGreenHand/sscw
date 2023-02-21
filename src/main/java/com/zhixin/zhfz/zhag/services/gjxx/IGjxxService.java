package com.zhixin.zhfz.zhag.services.gjxx;

import java.util.List;
import java.util.Map;

import com.zhixin.zhfz.zhag.entity.GjxxEntity;

public interface IGjxxService {
	
	 
	/**
	 * 告警信息
	 * @param map
	 * @return
	 */
	List<GjxxEntity> selectGjxx(Map<String, Object> map);
	
	/**
	 * 告警信息count
	 * @param map
	 * @return
	 */
	Integer countGjxx(Map<String, Object> map);

	List<GjxxEntity> jqGjList(Map<String, Object> map);

	Integer countJqGj(Map<String, Object> map);
	
}
