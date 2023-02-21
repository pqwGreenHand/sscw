package com.zhixin.zhfz.jzgl.services.jdrd;

import java.util.List;
import java.util.Map;

import com.zhixin.zhfz.jzgl.entity.GjxxEntity;
import com.zhixin.zhfz.jzgl.entity.GjxxLogEntity;

/**
 * IGjxxService
 * 
 * @author cuichengwei
 */
public interface IGjxxService  {
	
    public List<GjxxEntity> listGjxx(Map<String, Object> map) throws Exception;
	
	public int countGjxx(Map<String, Object> map) throws Exception;
	
    public List<GjxxLogEntity> listGjxxLogById(Map<String, Object> map) throws Exception;
	
	public int countGjxxLog(Map<String, Object> map) throws Exception;
	
	public void insertGjxxLog(GjxxLogEntity entity) throws Exception;
	
	public void updateGjxx(GjxxEntity entity) throws Exception;
	
}