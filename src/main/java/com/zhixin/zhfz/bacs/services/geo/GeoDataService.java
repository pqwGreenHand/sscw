/*
 * FileName: GeoDataService.java
 * auto create by wangguhua
 * Author:   
 * Description: GeoDataService实体类   
 */
 
package com.zhixin.zhfz.bacs.services.geo;


import com.zhixin.zhfz.bacs.dao.geo.IGeoDataMapper;
import com.zhixin.zhfz.bacs.entity.GeoDataEntity;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 实体类GeoDataService table: geo_data
 * 
 * @author wangguhua
 */
 @Service
public class GeoDataService implements IGeoDataService {

	private static Logger logger = Logger.getLogger(GeoDataService.class);
	
	@Autowired
	private IGeoDataMapper geoDataMapper = null;

	@Override
	public List<GeoDataEntity> listAllGeoData() throws Exception{
		return geoDataMapper.listAllGeoData();
	}
	
	@Override
	public Integer countAllGeoData() throws Exception{
		return geoDataMapper.countAllGeoData();
	}
	
	@Override
	public List<GeoDataEntity> listGeoData(Map<String,Object> map) throws Exception{
		return geoDataMapper.listGeoData(map);
	}
	
	@Override
	public Integer countGeoData(Map<String,Object> map) throws Exception{
		return geoDataMapper.countGeoData(map);
	}
	
	@Override
	public GeoDataEntity getGeoDataById(Long id) throws Exception{
		return geoDataMapper.getGeoDataById(id);
	}

	@Override
	public void insertGeoData(GeoDataEntity entity) throws Exception{
		geoDataMapper.insertGeoData(entity);
	}

	@Override
	public void updateGeoData(GeoDataEntity entity) throws Exception{
		geoDataMapper.updateGeoData(entity);
	}

	@Override
	public void deleteGeoDataById(Long id) throws Exception{
		geoDataMapper.deleteGeoDataById(id);
	}
    
	
}