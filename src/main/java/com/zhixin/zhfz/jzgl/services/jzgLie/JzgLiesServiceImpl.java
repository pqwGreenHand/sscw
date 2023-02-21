package com.zhixin.zhfz.jzgl.services.jzgLie;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhixin.zhfz.jzgl.dao.JzgLie.IJzgLieMapper;
import com.zhixin.zhfz.jzgl.entity.JzgLieEntity;

/**
 * 实体类JzgMysqlService table: jzg
 * 
 * @author xdp
 */
 @Service
public class JzgLiesServiceImpl implements IJzgLieService {

	@Autowired
	private IJzgLieMapper jzgLieMapper = null;

	@Override
	public List<JzgLieEntity> queryAllGmLie(Long id) {
		return jzgLieMapper.queryAllGmLie(id);
	}

	@Override
	public int queryLieLx(Map<String, Object> map) {
       return jzgLieMapper.queryLieLx(map);
	}

	@Override
	public void insertAllJzgLie(JzgLieEntity jzgLieEntity) {
		jzgLieMapper.insertAllJzgLie(jzgLieEntity);
	}

	 
}