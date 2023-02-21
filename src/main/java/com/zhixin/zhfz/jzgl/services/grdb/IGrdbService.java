package com.zhixin.zhfz.jzgl.services.grdb;

import java.util.List;
import java.util.Map;

import com.zhixin.zhfz.jzgl.entity.GrdbEntity;

public interface IGrdbService {

	/**
	 * 个人待办预警信息
	 * @param paramMap
	 * @return
	 */
	List<GrdbEntity> listGrdbXx(Map<String, Object> paramMap);
	
	int countListGrdbXx(Map<String, Object> paramMap);
}
