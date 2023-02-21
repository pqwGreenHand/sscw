package com.zhixin.zhfz.jzgl.dao.jzxx;

import java.util.List;
import java.util.Map;

import com.zhixin.zhfz.jzgl.entity.CzrzEntity;

public interface ICzrzMapper {

	public void insertCzrz(CzrzEntity entity) throws Exception;
	
	List<CzrzEntity> listJzCrQcCount(Map<String, Object> map);
	
	List<CzrzEntity> listMjCount(Map<String, Object> map);
	
	List<CzrzEntity> listJzXzCount(Map<String, Object> map);

	public List<CzrzEntity> listdwJzCr(Map<String, Object> map);
	
	public List<CzrzEntity> queryCqBy30Count(Map<String, Object> map);

}