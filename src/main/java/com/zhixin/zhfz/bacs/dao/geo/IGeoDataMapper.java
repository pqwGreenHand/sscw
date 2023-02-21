/*
 * FileName: GeoDataEntity.java
 * auto create by wangguhua 2016
 * Author:   
 * Date:     2016-8-23 16:19:38
 * Description: IGeoDataMapper实体类   
 */
 
package com.zhixin.zhfz.bacs.dao.geo;


import com.zhixin.zhfz.bacs.entity.GeoDataEntity;

import java.util.List;
import java.util.Map;

/**
 * 实体类IGeoDataMapper table: geo_data
 * 
 * @author wangguhua
 */
public interface IGeoDataMapper {

	public List<GeoDataEntity> listAllGeoData() throws Exception;
	
	public Integer countAllGeoData() throws Exception;
	
	public List<GeoDataEntity> listGeoData(Map<String, Object> map) throws Exception;

	public Integer countGeoData(Map<String, Object> map) throws Exception;
	
	public GeoDataEntity getGeoDataById(Long id) throws Exception;

	public void insertGeoData(GeoDataEntity entity) throws Exception;

	public void updateGeoData(GeoDataEntity entity) throws Exception;

	public void deleteGeoDataById(Long id) throws Exception;
    
    

}