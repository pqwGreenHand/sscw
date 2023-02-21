package com.zhixin.zhfz.common.restful.services;

import java.util.List;
import java.util.Map;

import com.zhixin.zhfz.common.restful.entity.GoodsEntity;
import com.zhixin.zhfz.common.restful.entity.PersonInfoEntity;

public interface IRestfulService {

	/**
	 * 随身物品
	 * @param map
	 * @return
	 */
	List<GoodsEntity> getBelongingsList(Map<String, Object> map);

	/**
	 * 涉案物品
	 * @param map
	 * @return
	 */
	List<GoodsEntity> getExhibitList(Map<String, Object> map);
	
	/**
	 * 标采
	 * @param personId
	 * @return
	 */
	PersonInfoEntity getPersonInfoById(String icNo);

}
