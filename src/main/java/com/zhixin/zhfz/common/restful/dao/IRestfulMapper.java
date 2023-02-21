package com.zhixin.zhfz.common.restful.dao;

import java.util.List;
import java.util.Map;

import com.zhixin.zhfz.common.restful.entity.GoodsEntity;
import com.zhixin.zhfz.common.restful.entity.PersonInfoEntity;

public interface IRestfulMapper {
	
	List<GoodsEntity> getBelongingsList(Map<String, Object> map); 
	
	List<GoodsEntity> getExhibitList(Map<String, Object> map); 
	
	PersonInfoEntity getPersonInfoById(String icNo);
	 
}
