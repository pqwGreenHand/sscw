package com.zhixin.zhfz.jzgl.services.jzxx;

import java.util.List;
import java.util.Map;

import com.zhixin.zhfz.jzgl.entity.JzxxEntity;

/**
 * IJzxxService table: jzxx
 * 
 * @author cuichengwei
 */
public interface IJzxxService {

	public List<JzxxEntity> getJzxxByAjxxId(Map<String, Object> map) throws Exception;

	public Integer countJzxxByAjxxId(Map<String, Object> map) throws Exception;

	/**
	 * 根据柜门id查询卷管信息
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	List<JzxxEntity> getJzxxByGmxxId(Map<String, Object> map) throws Exception;

	Integer countJzxxByGmxxId(Map<String, Object> map) throws Exception;

	public JzxxEntity getJzxxById(Long id) throws Exception;

	public void updateJzxx(JzxxEntity entity) throws Exception;
	
	public List<String> getGzbhByAjId(Long id) throws Exception;
	
	public void insertJzxx(JzxxEntity entity) throws Exception;
	
	public JzxxEntity getJzxxForQR(Long id) throws Exception;
	
	/**
	 * 柜门使用率
	 * @param map
	 * @return
	 */
	public List<JzxxEntity> listJzXzCount(Map<String, Object> map);
	
	/**
	 * 查询卷宗存取数量
	 * @param map
	 * @return
	 */
	public List<JzxxEntity> queryXsxzBy30Count(Map<String, Object> map);
	
	public List<JzxxEntity> getJzxxAndAjxx(Map<String, Object> map) throws Exception;
	
	public Integer countJzxxAndAjxx(Map<String, Object> map) throws Exception;
	
	public List<JzxxEntity> getJzxxByuuid(Map<String, Object> map) throws Exception;
	
	public void updateJzztByuuid(Map<String, Object> map) throws Exception;

}