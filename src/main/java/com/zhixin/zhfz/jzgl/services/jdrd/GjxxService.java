package com.zhixin.zhfz.jzgl.services.jdrd;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhixin.zhfz.jzgl.dao.jdrd.IGjxxLogMapper;
import com.zhixin.zhfz.jzgl.dao.jdrd.IGjxxMapper;
import com.zhixin.zhfz.jzgl.entity.GjxxEntity;
import com.zhixin.zhfz.jzgl.entity.GjxxLogEntity;

/**
 * 
 * @author cuichengwei
 */
@Service
public class GjxxService implements IGjxxService {

	private static Logger logger = Logger.getLogger(GjxxService.class);

	@Autowired
	private IGjxxMapper gjxxMapper;
	
	@Autowired
	private IGjxxLogMapper gjxxLogMapper; 

	@Override
	public List<GjxxEntity> listGjxx(Map<String, Object> map) throws Exception {
		return gjxxMapper.listGjxx(map);
	}

	@Override
	public int countGjxx(Map<String, Object> map) throws Exception {
		return gjxxMapper.countGjxx(map);
	}

	@Override
	public List<GjxxLogEntity> listGjxxLogById(Map<String, Object> map) throws Exception {
		return gjxxLogMapper.listGjxxLogById(map);
	}

	@Override
	public int countGjxxLog(Map<String, Object> map) throws Exception {
		return gjxxLogMapper.countGjxxLog(map);
	}

	@Override
	public void insertGjxxLog(GjxxLogEntity entity) throws Exception {
		gjxxLogMapper.insertGjxxLog(entity);
	}

	@Override
	public void updateGjxx(GjxxEntity entity) throws Exception {
		gjxxMapper.updateGjxx(entity);
	}

	public GjxxEntity queryGjxxSame(GjxxEntity gj) {
		// TODO Auto-generated method stub
		return gjxxMapper.queryGjxxSame(gj);
	}

	public void insertGjxxLXBC(GjxxEntity gj) {
		// TODO Auto-generated method stub
		gjxxMapper.insertGjxxLXBC(gj);
	}

	

}