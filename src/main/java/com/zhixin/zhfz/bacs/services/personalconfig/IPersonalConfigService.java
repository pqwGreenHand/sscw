package com.zhixin.zhfz.bacs.services.personalconfig;

import com.zhixin.zhfz.bacs.entity.PersonalConfigEntity;
import com.zhixin.zhfz.bacs.entity.PersonalConfigFusionEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface IPersonalConfigService {
	/**
	 * 查询所有办案场所个性化配置
	 * 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<PersonalConfigEntity> list(Map<String, Object> params) throws Exception;


	/**
	 * 分页
	 * 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int count(Map<String, Object> params) throws Exception;

	/**
	 * 插入
	 *
	 * @param entity
	 * @throws Exception
	 */
	public void insert(PersonalConfigEntity entity) throws Exception;

	/**
	 * 更新通用配置
	 *
	 * @param entity
	 * @throws Exception
	 */
	public void update(PersonalConfigEntity entity) throws Exception;

	/**
	 * 根据id删除通用配置
	 *
	 * @param id
	 * @throws Exception
	 */
	public void delete(int id) throws Exception;

	/**
	 * 初始化区域
	 * @param areaId
	 */
	public void initArea(int areaId, HttpServletRequest request);


	List<PersonalConfigFusionEntity> getDetailByType(Map<String, Object> map);

    Map<String, String> listConfigDetailByAreaAndName(int areaId, String name);
}
