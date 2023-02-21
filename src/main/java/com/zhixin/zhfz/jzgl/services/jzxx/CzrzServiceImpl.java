package com.zhixin.zhfz.jzgl.services.jzxx;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhixin.zhfz.jzgl.dao.jzxx.ICzrzMapper;
import com.zhixin.zhfz.jzgl.entity.CzrzEntity;

@Service
public class CzrzServiceImpl implements ICzrzService {

	private static Logger logger = Logger.getLogger(CzrzServiceImpl.class);

	@Autowired
	private ICzrzMapper czrzMapper = null;

	@Override
	public void insertCzrz(CzrzEntity entity) throws Exception {
		czrzMapper.insertCzrz(entity);
	}

	@Override
	public List<CzrzEntity> listJzCrQcCount(Map<String, Object> map) {
		return czrzMapper.listJzCrQcCount(map);
	}

	@Override
	public List<CzrzEntity> listMjCount(Map<String, Object> map) {
		return czrzMapper.listMjCount(map);
	}

	@Override
	public List<CzrzEntity> listJzXzCount(Map<String, Object> map) {
		return czrzMapper.listJzXzCount(map);
	}

	@Override
	public List<CzrzEntity> listdwJzCr(Map<String, Object> map) {
		return czrzMapper.listdwJzCr(map);
	}

	@Override
	public List<CzrzEntity> queryCqBy30Count(Map<String, Object> map) {
		return czrzMapper.queryCqBy30Count(map);
	}

}