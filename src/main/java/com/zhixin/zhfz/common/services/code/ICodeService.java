package com.zhixin.zhfz.common.services.code;

import com.zhixin.zhfz.common.entity.CodeEntity;

import java.util.List;
import java.util.Map;

public interface ICodeService {
	/**
	 * 查询及条件查询
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	List<CodeEntity> list(Map<String, Object> map) throws Exception;

	/**
	 * 分页
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	int count(Map<String, Object> map) throws Exception;
	
	/**
	 * 删除
	 * 
	 * @param id
	 * @throws Exception
	 */
	void delete(int id) throws Exception;

	/**
	 * 修改
	 * 
	 * @param entity
	 * @throws Exception
	 */
	void update(CodeEntity entity) throws Exception;

	/**
	 * 插入方法
	 * 
	 * @param entity
	 * @throws Exception
	 */
	void insert(CodeEntity entity) throws Exception;

	/**
	 * 根据type查询
	 * @param type
	 * @return
	 * @throws Exception
	 */
	List<CodeEntity> listByType(String type)throws Exception;


}
