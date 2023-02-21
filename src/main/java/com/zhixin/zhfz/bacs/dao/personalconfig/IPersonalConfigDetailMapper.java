package com.zhixin.zhfz.bacs.dao.personalconfig;

import com.zhixin.zhfz.bacs.entity.PersonalConfigDetailEntity;

import java.util.List;
import java.util.Map;



public interface IPersonalConfigDetailMapper {
	/**
	 * 删除personConfig级联删除personalConfigDetail表
	 *
	 * @param personalConfigId
	 * @throws Exception
	 */
	void deleteByPersonalConfigId(int personalConfigId) throws Exception;

	/**
	 * 查询所有办案场所个性化配置
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	List<PersonalConfigDetailEntity> list(Map<String, Object> params) throws Exception;

	/**
	 * 插入
	 *
	 * @param entity
	 * @throws Exception
	 */
	void insert(PersonalConfigDetailEntity entity) throws Exception;

	/**
	 * 更新通用配置
	 *
	 * @param entity
	 * @throws Exception
	 */
	void update(PersonalConfigDetailEntity entity) throws Exception;

	/**
	 * 根据id删除通用配置
	 *
	 * @param id
	 * @throws Exception
	 */
	void delete(int id) throws Exception;

	/**
	 * 分页
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	int count(Map<String, Object> params) throws Exception;

	/**
	 * 分页
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	List<PersonalConfigDetailEntity> listDetailsByNameAndInterrogateAreaId(Map<String, Object> params) throws Exception;


	List<PersonalConfigDetailEntity> checkurl(String areaid);

	/**
	 * 查询个性化配置
	 * @param param
	 * @return
	 */
	List<PersonalConfigDetailEntity> listConfigDetailByAreaAndName(Map<String, Object> param);

	String queryBoxNumberById(int lockid);

	List<PersonalConfigDetailEntity> listDetailByCodeAndParam(Map<String, Object> params) throws Exception;
	
	List<PersonalConfigDetailEntity> listInParamDetailByOutParamId(Long id) throws Exception;


	
}
