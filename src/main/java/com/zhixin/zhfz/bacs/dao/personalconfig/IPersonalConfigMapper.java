package com.zhixin.zhfz.bacs.dao.personalconfig;

import com.zhixin.zhfz.bacs.entity.PersonalConfigEntity;
import com.zhixin.zhfz.bacs.entity.PersonalConfigFusionEntity;

import java.util.List;
import java.util.Map;



public interface IPersonalConfigMapper {
	/**
	 * 查询所有办案场所个性化配置
	 * @param params
	 * @return
	 * @throws Exception
	 */
	List<PersonalConfigEntity> list(Map<String, Object> params)throws Exception;
	
	/**
	 * 查询特定的办案配置
	 * wangguhua
	 * @param params
	 * @return
	 * @throws Exception
	 */
	List<PersonalConfigEntity> listFn(Map<String, Object> params)throws Exception;
	/**
	 * 插入
	 * @param entity
	 * @throws Exception
	 */
	void insert(PersonalConfigEntity entity)throws Exception;
	/**
	 * 更新通用配置
	 * @param entity
	 * @throws Exception
	 */
	void update(PersonalConfigEntity entity)throws Exception;
	/**
	 * 根据id删除通用配置
	 * @param id
	 * @throws Exception
	 */
	void delete(int id)throws Exception;
	/**
	 * 分页
	 * @param params
	 * @return
	 * @throws Exception
	 */
	int count(Map<String, Object> params)throws Exception;
	
	/**
	 * 嫌犯随物箱查询
	 * @param map
	 * @return
	 */
	List<PersonalConfigEntity> listboxinfoPerson(Map<String, Object> map);
	/**
	 * 涉案物箱查询
	 * @param map
	 * @return
	 */
	List<PersonalConfigEntity> listboxinfoExhibit(Map<String, Object> map);

	/**
	 * @Author jzw
	 * @Description
	 * @Date 11:14 2019/3/6
	 * @Param [map]
	 * @return java.util.List<com.zhixin.zhfz.bacs.entity.PersonalConfigFusionEntity>
	 **/
	List<PersonalConfigFusionEntity> getDetailByType(Map<String, Object> map);
}
