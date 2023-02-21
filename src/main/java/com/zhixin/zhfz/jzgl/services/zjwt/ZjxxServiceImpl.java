package com.zhixin.zhfz.jzgl.services.zjwt;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhixin.zhfz.jzgl.dao.zjwt.IZjxxMapper;
import com.zhixin.zhfz.jzgl.entity.ZjxxEntity;

/**
 * 
 * @author cuichengwei
 */
@Service
public class ZjxxServiceImpl implements IZjxxService {

	private static Logger logger = Logger.getLogger(ZjxxServiceImpl.class);

	@Autowired
	private IZjxxMapper zjxxMapper;

	@Override
	public List<ZjxxEntity> listZjxx(Map<String, Object> map) throws Exception {
		return zjxxMapper.listZjxx(map);
	}

	@Override
	public int countZjxx(Map<String, Object> map) throws Exception {
		return zjxxMapper.countZjxx(map);
	}

	@Override
	public void insertZjxx(ZjxxEntity entity) throws Exception {
		zjxxMapper.insertZjxx(entity);
	}

	@Override
	public List<Map<String, String>> queryJzxx(Map<String, Object> map) {
		return zjxxMapper.queryJzxx(map);
	}

}