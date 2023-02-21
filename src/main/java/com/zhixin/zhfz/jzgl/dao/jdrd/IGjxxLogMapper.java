package com.zhixin.zhfz.jzgl.dao.jdrd;

import java.util.List;
import java.util.Map;

import com.zhixin.zhfz.jzgl.entity.GjxxLogEntity;

public interface IGjxxLogMapper {
	
	public List<GjxxLogEntity> listGjxxLogById(Map<String, Object> map) throws Exception;
	
	public int countGjxxLog(Map<String, Object> map) throws Exception;
	
	public void insertGjxxLog(GjxxLogEntity entity) throws Exception;
	
}
