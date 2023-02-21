package com.zhixin.zhfz.common.dao.commonConfig;

import com.zhixin.zhfz.common.entity.CommonConfigDetailEntity;

import java.util.List;
import java.util.Map;

public interface ICommonConfigDetailMapper {
	/**
	 * 删除commonConfig级联删除detail表
	 * @param commonConfigId
	 * @throws Exception
	 */
	void deleteByConfigId(int commonConfigId)throws Exception;	
	/**
	 * 查询对应通用配置的配置详细信息
	 * @param params
	 * @return
	 * @throws Exception
	 */
	List<CommonConfigDetailEntity> list(Map<String,Object> params)throws Exception;
	/**
	 * 插入对应通用配置的配置详细信息
	 * @param entity
	 * @throws Exception
	 */
	void insert(CommonConfigDetailEntity entity)throws Exception;
	/**
	 * 更新对应通用配置的配置详细信息
	 * @param entity
	 * @throws Exception
	 */
	void update(CommonConfigDetailEntity entity)throws Exception;
	/**
	 * 根据id删除对应通用配置的配置详细信息
	 * @param id
	 * @throws Exception
	 */
	void delete(int id)throws Exception;
	/**
	 * 分页对应通用配置的配置详细信息
	 * @param params
	 * @return
	 * @throws Exception
	 */
	int count(Map<String,Object> params)throws Exception;
	/**
	 * 分页对应通用配置的配置详细信息
	 * @param params
	 * @return
	 * @throws Exception
	 */
	String listDetailsByName(Map<String,Object> params)throws Exception;
	
	void updateByConfigIdAndKey(CommonConfigDetailEntity entity)throws Exception;
	/*
	查询配置表中的办案场所GIS管理配置
	 */
	CommonConfigDetailEntity queryDetailByType(String Type) throws Exception;
	
}
