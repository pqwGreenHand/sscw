package com.zhixin.zhfz.common.restful.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhixin.zhfz.common.restful.dao.IRestfulMapper;
import com.zhixin.zhfz.common.restful.entity.GoodsEntity;
import com.zhixin.zhfz.common.restful.entity.PersonInfoEntity;

 

@Service
public class RestfulServiceImpl implements IRestfulService {

	@Autowired
	private IRestfulMapper mapper;
	
	
	@Override
	public List<GoodsEntity> getBelongingsList(Map<String, Object> map) {
		return mapper.getBelongingsList(map);
	}
	@Override
	public List<GoodsEntity> getExhibitList(Map<String, Object> map) {
		return mapper.getExhibitList(map);
	}
	@Override
	public PersonInfoEntity getPersonInfoById(String icNo) {
		return mapper.getPersonInfoById(icNo);
	}

	 
}
