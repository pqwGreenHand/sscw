package com.zhixin.zhfz.bacs.services.person;

import com.zhixin.zhfz.bacs.entity.PersonEntity;

import java.util.List;
import java.util.Map;

public interface IPersonService {

	List<PersonEntity> list(Map<String, Object> params) throws Exception;
	
	int count(Map<String, Object> params) throws Exception;

	int insert(PersonEntity entity) throws Exception;
	
	void update(PersonEntity entity) throws Exception;

	void delete(Long id) throws Exception;
	
	PersonEntity ishave(PersonEntity entity) throws Exception;

    PersonEntity getPersonById(Long userId);
    
    /**
     * 通过案件信息查询person
     * @param params
     * @return
     */
    List<PersonEntity> personListByCase(Map<String, Object> params);


    /**
     * 给标采接口
     * @param userId
     * @return
     */
    PersonEntity getPersonInfoById(Long userId);

	int searchAreaidByCaseId(int caseId) throws Exception;

}
