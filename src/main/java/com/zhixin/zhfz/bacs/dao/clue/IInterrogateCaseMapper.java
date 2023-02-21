package com.zhixin.zhfz.bacs.dao.clue;

import com.zhixin.zhfz.bacs.entity.ArchivesTreeEntity;
import com.zhixin.zhfz.bacs.entity.InterrogateCaseEntity;

import java.util.List;
import java.util.Map;

public interface IInterrogateCaseMapper {
	/**
	 * 查询及条件查询
	 * @param map
	 * @return
	 * @throws Exception
	 */
	List<InterrogateCaseEntity> list(Map<String, Object> map)throws Exception;
	/**
	 * 分页
	 * @param map
	 * @return
	 * @throws Exception
	 */
	int count(Map<String, Object> map)throws Exception;
	/**
	 * 删除
	 * @param id
	 * @throws Exception
	 */
	void delete(int id)throws Exception;
	/**
	 * 修改
	 * @param entity
	 * @throws Exception
	 */
	void update(InterrogateCaseEntity entity)throws Exception;
	/**
	 * 插入方法
	 * @param entity
	 * @throws Exception
	 */
	void insert(InterrogateCaseEntity entity)throws Exception;

	int getId(InterrogateCaseEntity entity) throws Exception;

	InterrogateCaseEntity getCaseById(Integer id) throws Exception;

	//案件树形展示
	public List<ArchivesTreeEntity> listCaseTreelimit(Map<String, Object> map);

	public List<ArchivesTreeEntity> listCaseTree(Map<String, Object> map);

	public int listCaseTreeCount(Map<String, Object> map);

	public List findFilePath(Map<String, Object> map);

	public List getPersionInfo(Map<String, Object> map);

	public List<Map<String, Object>> getRecordPersionCombobox(Map<String, Object> map);
	
	//修改涉案人员
	void updateInvolvedPeople(InterrogateCaseEntity entity)throws Exception;
	
}