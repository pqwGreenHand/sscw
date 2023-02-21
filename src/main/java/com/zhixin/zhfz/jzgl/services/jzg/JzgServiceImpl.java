package com.zhixin.zhfz.jzgl.services.jzg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhixin.zhfz.jzgl.dao.jzg.IJzgMapper;
import com.zhixin.zhfz.jzgl.entity.JzgEntity;

import java.util.List;
import java.util.Map;

/**
 * 实体类JzgMysqlService table: jzg
 * 
 * @author xdp
 */
 @Service
public class JzgServiceImpl implements IJzgService {

	@Autowired
	private IJzgMapper jzgMapper = null;

	@Override
	public List<JzgEntity> queryAllJzgGmxx(Map<String, Object> map) {
		return jzgMapper.queryAllJzgGmxx(map);
	}

	@Override
	public int countAllJzgGmxx(Map<String, Object> map) {
		return jzgMapper.countAllJzgGmxx(map);
	}

	@Override
	public List<JzgEntity> queryAllGm(Map<String, Object> map) {
		return jzgMapper.queryAllGm(map);
	}
}