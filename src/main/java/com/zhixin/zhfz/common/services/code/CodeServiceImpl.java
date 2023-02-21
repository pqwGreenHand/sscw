package com.zhixin.zhfz.common.services.code;

import com.zhixin.zhfz.common.dao.code.ICodeMapper;
import com.zhixin.zhfz.common.entity.CodeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CodeServiceImpl implements ICodeService {
	
	@Autowired
	ICodeMapper dao;

	@Override
	public List<CodeEntity> list(Map<String, Object> map) throws Exception {
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
	public void update(CodeEntity entity) throws Exception {
		dao.update(entity);
	}
	@Override
	public void insert(CodeEntity entity) throws Exception {
		dao.insert(entity);
	}
	@Override
	public List<CodeEntity> listByType(String type)throws Exception{
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("type", type);
		return dao.listByType(map);
	}


}
