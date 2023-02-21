package com.zhixin.zhfz.bacs.services.nvr;

import java.util.List;
import java.util.Map;

import com.zhixin.zhfz.bacs.dao.nvr.INvrMapper;
import com.zhixin.zhfz.bacs.entity.NvrEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class NvrServiceImpl implements INvrService {

	@Autowired
	INvrMapper mapper;

	@Override
	public List<NvrEntity> list(Map<String, Object> map) throws Exception {
		return mapper.list(map);
	}

	@Override
	public int count(Map<String, Object> map) throws Exception {
		return mapper.count(map);
	}

	@Override
	public void delete(int id) throws Exception {
		mapper.delete(id);
	}

	@Override
	public void update(NvrEntity entity) throws Exception {
		mapper.update(entity);
	}

	@Override
	public void insert(NvrEntity entity) throws Exception {
		mapper.insert(entity);
	}

	@Override
	public void insertList(List<NvrEntity> nvrList) throws Exception {
		mapper.insertList(nvrList);
	}

	@Override
	public void deleteCameraByNvrId(int id) throws Exception {
		mapper.deleteCameraByNvrId(id);
	}

	@Override
	public List<NvrEntity> comboNvr(Map<String, Object> map) throws Exception {
		return mapper.comboNvr(map);
	}


}
