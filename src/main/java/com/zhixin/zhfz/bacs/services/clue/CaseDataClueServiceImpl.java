package com.zhixin.zhfz.bacs.services.clue;

import com.zhixin.zhfz.bacs.dao.clue.ICaseDataClueMapper;
import com.zhixin.zhfz.bacs.entity.CaseDataClueEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CaseDataClueServiceImpl implements ICaseDataClueService {
	
	@Autowired
	ICaseDataClueMapper dao;
	@Override
	public List<CaseDataClueEntity> list(Map<String, Object> map) throws Exception {
		return dao.list(map);
	}

	@Override
	public int count(Map<String, Object> map) throws Exception {
		return dao.count(map);
	}
	@Override
	public void delete(int id) throws Exception {
		dao.delete(id);
	}
	@Override
	public void update(CaseDataClueEntity entity) throws Exception {
		dao.update(entity);
	}
	@Override
	public void insert(CaseDataClueEntity entity) throws Exception {
		dao.insert(entity);
	}
	public CaseDataClueEntity selectOneById(Map<String,Object> map)throws Exception{
		return dao.selectOneById(map);
	}
	
}
