package com.zhixin.zhfz.zhag.services.yjxx;

import java.util.List;
import java.util.Map;

import com.zhixin.zhfz.zhag.entity.YjxxEntity;

public interface IYjxxService {
	
	/**
	 * 预警信息
	 * @param map
	 * @return
	 */
	List<YjxxEntity> selectYjxx(Map<String, Object> map);
	
	/**
	 * 预警信息count
	 * @param map
	 * @return
	 */
	Integer countYjxx(Map<String, Object> map);


	List<YjxxEntity> jqYjList(Map<String, Object> map);

	Integer countJqYj(Map<String, Object> map);
	
	
}
