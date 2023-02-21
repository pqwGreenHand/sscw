package com.zhixin.zhfz.zhag.services.cscf;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhixin.zhfz.zhag.dao.cscf.ICscfMapper;
import com.zhixin.zhfz.zhag.entity.CscfEntity;

@Service
public class CscfServiceImpl implements ICscfService{

	@Autowired
	private ICscfMapper qzcsMapper;
	@Override
	public List<CscfEntity> selectCscf(Map<String, Object> map) {
		return qzcsMapper.selectCscf(map);
	}

	@Override
	public Integer countCscf(Map<String, Object> map) {
		return qzcsMapper.countCscf(map);
	}

	@Override
	public void addCscf(CscfEntity entity) {
		qzcsMapper.addCscf(entity); 
	}

	@Override
	public void editCscf(CscfEntity entity) {
		qzcsMapper.editCscf(entity);
	}

}
