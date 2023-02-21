package com.zhixin.zhfz.jzgl.services.jzxx;

import java.util.List;
import java.util.Map;

import com.zhixin.zhfz.jzgl.entity.CrgLogEntity;

/**
 * ICglogService
 * 
 * @author cuichengwei
 */
public interface ICrglogService  {
	
	public CrgLogEntity getOverTimeCglogByJzxxId(Map<String, Object> map) throws Exception;
	
	public List<CrgLogEntity> getOverTimeCglog() throws Exception;
	
	public void insertCglog(CrgLogEntity cglogEntity) throws Exception;
	
	public List<CrgLogEntity> listCglog(Map<String, Object> map) throws Exception;
	
	public List<CrgLogEntity> getCglogByJzxxId(Map<String, Object> map) throws Exception;
	
	public Integer countCglog(Map<String, Object> map) throws Exception;
	
}