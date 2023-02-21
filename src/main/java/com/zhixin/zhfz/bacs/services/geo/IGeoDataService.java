/*
 * FileName: IGeoDataService.java
 * auto create by wangguhua
 * Author:   
 * Description: GeoDataService实体类   
 */
 
package com.zhixin.zhfz.bacs.services.geo;



import com.zhixin.zhfz.bacs.entity.GeoDataEntity;

import java.util.List;
import java.util.Map;

/**
 * IGeoDataService table: geo_data
 * 
 * @author wangguhua
 */
public interface IGeoDataService  {

	public List<GeoDataEntity> listAllGeoData() throws Exception;
	
	public Integer countAllGeoData() throws Exception;
	
	public List<GeoDataEntity> listGeoData(Map<String, Object> map) throws Exception;

	public Integer countGeoData(Map<String, Object> map) throws Exception;
	
	public GeoDataEntity getGeoDataById(Long id) throws Exception;

	public void insertGeoData(GeoDataEntity entity) throws Exception;

	public void updateGeoData(GeoDataEntity entity) throws Exception;

	public void deleteGeoDataById(Long id) throws Exception;
    
    

}