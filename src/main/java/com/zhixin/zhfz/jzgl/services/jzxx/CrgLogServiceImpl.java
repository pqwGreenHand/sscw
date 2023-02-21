package com.zhixin.zhfz.jzgl.services.jzxx;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhixin.zhfz.jzgl.dao.jzxx.ICrgLogMapper;
import com.zhixin.zhfz.jzgl.entity.CrgLogEntity;

/**
 * 
 * @author cuichengwei
 */
@Service
public class CrgLogServiceImpl implements ICrglogService {

	@Autowired
	private ICrgLogMapper crgLogMapper = null;

	@Override
	public void insertCglog(CrgLogEntity cglogEntity) throws Exception {
		crgLogMapper.insertCglog(cglogEntity);
	}

	@Override
	public List<CrgLogEntity> listCglog(Map<String, Object> map) throws Exception {
		return crgLogMapper.listCglog(map);
	}

	@Override
	public Integer countCglog(Map<String, Object> map) throws Exception {
		return crgLogMapper.countCglog(map);
	}

	@Override
	public List<CrgLogEntity> getCglogByJzxxId(Map<String, Object> map) throws Exception {
		return crgLogMapper.getCglogByJzxxId(map);
	}

	@Override
	public List<CrgLogEntity> getOverTimeCglog() throws Exception {
		return crgLogMapper.getOverTimeCglog();
	}

	@Override
	public CrgLogEntity getOverTimeCglogByJzxxId(Map<String, Object> map) throws Exception {
		return crgLogMapper.getOverTimeCglogByJzxxId(map);
	}

}