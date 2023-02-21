package com.zhixin.zhfz.bacs.dao.person;

import com.zhixin.zhfz.bacs.entity.PersonEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface IPersonMapper {
	
	List<PersonEntity> list(Map<String, Object> params) throws Exception;

	int count(Map<String, Object> params) throws Exception;

	int insert(PersonEntity entity) throws Exception;
	
	void update(PersonEntity entity) throws Exception;
	
	void delete(Long id) throws Exception;

	int countWuMs() throws Exception;
	
	List<PersonEntity> ishave(PersonEntity entity) throws Exception;

    PersonEntity queryPersonInfoById(long personId);

    PersonEntity getPersonById(Long userId);
    
    List<PersonEntity> personListByCase(Map<String, Object> params);
    
    PersonEntity getPersonInfoById(Long userId);

	int searchAreaidByCaseId(int caseId) throws Exception;


}