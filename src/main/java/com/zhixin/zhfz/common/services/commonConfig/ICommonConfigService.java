package com.zhixin.zhfz.common.services.commonConfig;

import java.util.List;
import java.util.Map;

import com.zhixin.zhfz.common.entity.CommonConfigEntity;

public interface ICommonConfigService {

	/**
	 * 查询所有通用配置
	 * 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	List<CommonConfigEntity> list(Map<String, Object> params) throws Exception;

	/**
	 * 插入
	 * 
	 * @param entity
	 * @throws Exception
	 */
	void insert(CommonConfigEntity entity) throws Exception;

	/**
	 * 更新通用配置
	 * 
	 * @param entity
	 * @throws Exception
	 */
	void update(CommonConfigEntity entity) throws Exception;

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
}
