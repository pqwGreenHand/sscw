package com.zhixin.zhfz.bacs.dao.cuff;


import com.zhixin.zhfz.bacs.entity.CuffEntity;

import java.util.List;
import java.util.Map;

public interface ICuffMapper {
  
	List<CuffEntity> list(Map<String, Object> params) throws Exception;
	
	int count(Map<String, Object> params) throws Exception;

	List<CuffEntity> listAll() throws Exception;
	
	void delete(Long id) throws Exception;
	
	int deleteByPrimaryKey(Integer id);

    int insert(CuffEntity record);

    int insertSelective(CuffEntity record);

    CuffEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CuffEntity record);

    int updateByPrimaryKey(CuffEntity record);

	List<CuffEntity> queryArea();
	
	CuffEntity getCuffById(Integer id) throws Exception;

	CuffEntity getCuffByNo(Map<String, Object> params) throws Exception;
	
	CuffEntity getCuffByCuffNo(Map<String, Object> params) throws Exception;
	
	/**
	 * 根据NO获取 CuffEntity (ic_no, cuff_no 任一匹配)
	 * @param params
	 * @return
	 * @throws Exception
	 */
	CuffEntity getCuffByAnyNo(Map<String, Object> params) throws Exception;
	
	
	
	
	/**
	 * 绑定手环给民警
	 * @param record
	 * @return
	 */
	int bindUserCuffById(CuffEntity record);
	
	/**
	 * 绑定手环给person
	 * @param record
	 * @return
	 */
	int bindPersonCuffById(CuffEntity record);
	
	/**
	 * 解绑手环
	 * @param id
	 * @return
	 */
	int unbindCuffById(Integer id);
	
	/**
	 * 根据uerId获取绑定的卡片
	 * @param id
	 * @return
	 */
	CuffEntity getBindCuffByUserId(Long id);
	
	/**
	 * 根据personId获取绑定的卡片
	 * @param id
	 * @return
	 */
	CuffEntity getBindCuffByPersonId(Long id);

	/**
	 * 根据标签编号和类型获取标签
	 * @param params
	 * @return
	 * @throws Exception
	 */
	CuffEntity getCuffNoByIcNoAndType(Map<String, Object> params) throws Exception;
}