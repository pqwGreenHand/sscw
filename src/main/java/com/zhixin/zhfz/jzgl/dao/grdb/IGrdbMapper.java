package com.zhixin.zhfz.jzgl.dao.grdb;

import java.util.List;
import java.util.Map;

import com.zhixin.zhfz.jzgl.entity.GrdbEntity;

public interface IGrdbMapper {

	List<GrdbEntity> listGrdbXx(Map<String, Object> paramMap);
	
	int countListGrdbXx(Map<String, Object> paramMap);
}
