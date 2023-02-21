package com.zhixin.zhfz.bacs.services.jingwu;

import com.zhixin.zhfz.bacs.entity.GeoPersonInfoCountEntity;

import java.util.List;

public interface IGeoPersonInfoCountService {
	public List<GeoPersonInfoCountEntity> listPersonInfoCountTypeByOrg(Long orgId) throws Exception;
	
	public List<GeoPersonInfoCountEntity> listPersonInfoCountSexByOrg(Long orgId) throws Exception;
	
	public List<GeoPersonInfoCountEntity> listPersonInfoCount7DayByOrg(Long orgId) throws Exception;
	
}
