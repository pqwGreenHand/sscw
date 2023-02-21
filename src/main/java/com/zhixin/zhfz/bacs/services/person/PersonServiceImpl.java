package com.zhixin.zhfz.bacs.services.person;

import java.util.List;
import java.util.Map;

import com.zhixin.zhfz.bacs.dao.order.IOrderPersonMapper;
import com.zhixin.zhfz.bacs.dao.order.IOrderRequestMapper;
import com.zhixin.zhfz.bacs.entity.OrderPersonEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhixin.zhfz.bacs.dao.person.IPersonMapper;
import com.zhixin.zhfz.bacs.entity.PersonEntity;


@Service("personService")
public class PersonServiceImpl implements IPersonService{
	private static final Logger logger = LoggerFactory.getLogger(PersonServiceImpl.class);

	@Autowired
	private IPersonMapper personMapper;
	@Autowired
	private IOrderRequestMapper orderRequestMapper;
	@Autowired
	private IOrderPersonMapper orderPersonMapper;

	@Override
	public List<PersonEntity> list(Map<String, Object> params) throws Exception {
		return personMapper.list(params);
	}

	@Override
	public int count(Map<String, Object> params) throws Exception {
		return personMapper.count(params);
	}

	@Override
	public int insert(PersonEntity entity) throws Exception {
		return personMapper.insert(entity);
	}

	@Override
	public void update(PersonEntity entity) throws Exception {
		personMapper.update(entity);		
	}

	@Override
	public void delete(Long id) throws Exception {
		personMapper.delete(id);		
	}

	@Override
	public PersonEntity ishave(PersonEntity entity) throws Exception {
		List<PersonEntity> list=personMapper.ishave(entity);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}

	@Override
	public PersonEntity getPersonById(Long userId) {
		return personMapper.getPersonById(userId);
	}

	@Override
	public List<PersonEntity> personListByCase(Map<String, Object> params) {
		return personMapper.personListByCase(params);
	}

	@Override
	public PersonEntity getPersonInfoById(Long userId) {
		return personMapper.getPersonInfoById(userId);
	}

	@Override
	public int searchAreaidByCaseId(int caseId) throws Exception {
		return personMapper.searchAreaidByCaseId(caseId);
	}

}
