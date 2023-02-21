/*
 * FileName: ICzrzMysqlService.java
 * auto create by wangguhua
 * Author:   
 * Description: CzrzMysqlService实体类   
 */

package com.zhixin.zhfz.jzgl.services.jzxx;

import java.util.List;
import java.util.Map;

import com.zhixin.zhfz.jzgl.entity.CzrzEntity;

/**
 * ICzrzService table: czrz
 * 
 */
public interface ICzrzService {

	public void insertCzrz(CzrzEntity entity) throws Exception;
	
	/**
	 * 办案单位存取总次数
	 * @param map
	 * @return
	 */
	List<CzrzEntity> listJzCrQcCount(Map<String, Object> map);
	/**
	 * 民警存取总次数
	 * @param map
	 * @return
	 */
	List<CzrzEntity> listMjCount(Map<String, Object> map);
	
	/**
	 * 本周查询卷宗存取数量
	 * @param map
	 * @return
	 */
	List<CzrzEntity> listJzXzCount(Map<String, Object> map);
	
	/**
	 * 查询卷宗存取数量
	 * @param map
	 * @return
	 */
	List<CzrzEntity> listdwJzCr(Map<String, Object> map);
	
	public List<CzrzEntity> queryCqBy30Count(Map<String, Object> map);

}