package com.zhixin.zhfz.bacs.services.jingwu;

import com.zhixin.zhfz.bacs.dao.jingwu.GeoPersonInfoCountMapper;
import com.zhixin.zhfz.bacs.entity.GeoPersonInfoCountEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class GeoPersonInfoCountServiceImpl implements IGeoPersonInfoCountService {

	@Autowired
	private GeoPersonInfoCountMapper geoPersonInfoCountMapper;
	
	@Override
	public List<GeoPersonInfoCountEntity> listPersonInfoCountTypeByOrg(Long orgId) throws Exception {
		
		
		return geoPersonInfoCountMapper.listPersonInfoCountTypeByOrg(orgId);
	}

	@Override
	public List<GeoPersonInfoCountEntity> listPersonInfoCountSexByOrg(Long orgId) throws Exception {

		return geoPersonInfoCountMapper.listPersonInfoCountSexByOrg(orgId);
	}

	@Override
	public List<GeoPersonInfoCountEntity> listPersonInfoCount7DayByOrg(Long orgId) throws Exception{
		return geoPersonInfoCountMapper.listPersonInfoCount7DayByOrg(orgId);
	}
}
