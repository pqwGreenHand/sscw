package com.zhixin.zhfz.common.services.commonConfig;

import com.zhixin.zhfz.common.entity.CommonConfigDetailEntity;

import java.util.List;
import java.util.Map;

public interface ICommonConfigDetailService {
	/**
	 * 查询所有通用配置
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<CommonConfigDetailEntity> list(Map<String,Object> params)throws Exception;
	/**
	 * 插入
	 * @param entity
	 * @throws Exception
	 */
	public void insert(CommonConfigDetailEntity entity)throws Exception;
	/**
	 * 更新通用配置
	 * @param entity
	 * @throws Exception
	 */
	public void update(CommonConfigDetailEntity entity)throws Exception;
	/**
	 * 根据id删除通用配置
	 * @param id
	 * @throws Exception
	 */
	public void delete(int id)throws Exception;
	/**
	 * 分页
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int count(Map<String,Object> params)throws Exception;
	/**
	 * 分页对应通用配置的配置详细信息
	 * @param parm1,parm2
	 * @return
	 * @throws Exception
	 */
	public String listDetailsByName(String parm1,String parm2)throws Exception;
	/**
	 * 更新通用配置
	 * @param entity
	 * @throws Exception
	 */
	public void updateByConfigIdAndKey(CommonConfigDetailEntity entity)throws Exception;

	public CommonConfigDetailEntity queryDetailByType(String type)throws Exception;
}
