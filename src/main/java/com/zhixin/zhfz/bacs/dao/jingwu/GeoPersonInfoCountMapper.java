package com.zhixin.zhfz.bacs.dao.jingwu;

import com.zhixin.zhfz.bacs.entity.GeoPersonInfoCountEntity;

import java.util.List;

/**
 * 实体类GeoPersonInfoCountMapper 
 * 
 * @author wangguhua
 */
public interface GeoPersonInfoCountMapper {

	public List<GeoPersonInfoCountEntity> listPersonInfoCountTypeByOrg(Long orgId) throws Exception;
	
	public List<GeoPersonInfoCountEntity> listPersonInfoCountSexByOrg(Long orgId) throws Exception;
	
	public List<GeoPersonInfoCountEntity> listPersonInfoCount7DayByOrg(Long orgId) throws Exception;
}
