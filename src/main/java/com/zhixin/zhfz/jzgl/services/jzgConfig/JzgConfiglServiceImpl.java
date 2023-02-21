package com.zhixin.zhfz.jzgl.services.jzgConfig;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhixin.zhfz.jzgl.dao.JzgLie.IJzgLieMapper;
import com.zhixin.zhfz.jzgl.dao.jzgConfig.IJzgConfigMapper;
import com.zhixin.zhfz.jzgl.entity.JzgEntity;
import com.zhixin.zhfz.jzgl.entity.JzgGmxxEntity;
import com.zhixin.zhfz.jzgl.entity.JzgLieEntity;

@Service
public class JzgConfiglServiceImpl implements IJzgConfigService {

	Logger logger = LoggerFactory.getLogger(JzgConfiglServiceImpl.class);

	@Autowired
	private IJzgConfigMapper jzgConfigMapper;

	@Autowired
	private IJzgLieMapper jzgLieMapper;

	@Override
	public List<JzgEntity> queryAllJzgGmxx(Map<String, Object> map) {
		return jzgConfigMapper.queryAllJzgGmxx(map);
	}

	@Override
	public int countAllJzgGmxx(Map<String, Object> map) {
		return jzgConfigMapper.countAllJzgGmxx(map);
	}

	@Override
	public List<JzgLieEntity> queryAllJzgLie(Map<String, Object> map) {
		return jzgLieMapper.queryJzgLie(map);
	}

	@Override
	public void insertJzgLie(JzgLieEntity entity) {
		jzgLieMapper.insertJzgLie(entity);
	}

	@Override
	public void updateJzgLie(JzgLieEntity entity) {
		jzgLieMapper.updateJzgLie(entity);
	}

	@Override
	public void insertGmxx(JzgEntity entity) {
		jzgConfigMapper.insertGmxx(entity);
	}

	@Override
	public void updateGmxx(JzgEntity entity) {
		jzgConfigMapper.updateGmxx(entity);
	}

	@Override
	public void insertAllJzgGmxx(JzgGmxxEntity gmxxEntity) {
		jzgConfigMapper.insertAllJzgGmxx(gmxxEntity);
	}

}
