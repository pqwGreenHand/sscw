package com.zhixin.zhfz.jzgl.dao.jzxx;

import java.util.List;
import java.util.Map;

import com.zhixin.zhfz.jzgl.entity.JzxxEntity;

public interface IJzxxMapper {

	public List<JzxxEntity> getJzxxByAjxxId(Map<String, Object> map) throws Exception;

	public Integer countJzxxByAjxxId(Map<String, Object> map) throws Exception;

	List<JzxxEntity> getJzxxByGmxxId(Map<String, Object> map) throws Exception;

	Integer countJzxxByGmxxId(Map<String, Object> map) throws Exception;
	
	public JzxxEntity getJzxxById(Long id) throws Exception;
	
	public void updateJzxx(JzxxEntity entity) throws Exception;
	
	public List<String> getGzbhByAjId(Long id) throws Exception;
	
	public void insertJzxx(JzxxEntity entity) throws Exception;
	
	public JzxxEntity getJzxxForQR(Long id) throws Exception;
	
	public List<JzxxEntity> listJzXzCount(Map<String, Object> map);
 
	public List<JzxxEntity> queryXsxzBy30Count(Map<String, Object> map);
 
	public List<JzxxEntity> getJzxxAndAjxx(Map<String, Object> map) throws Exception;
	
	public Integer countJzxxAndAjxx(Map<String, Object> map) throws Exception;
		
	public List<JzxxEntity> getJzxxByuuid(Map<String, Object> map) throws Exception;
		
	public void updateJzztByuuid(Map<String, Object> map) throws Exception;
 
}
