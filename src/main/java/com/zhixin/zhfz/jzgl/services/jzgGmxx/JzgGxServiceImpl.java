package com.zhixin.zhfz.jzgl.services.jzgGmxx;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhixin.zhfz.jzgl.dao.jzgGmxx.IJzgGxMapper;
import com.zhixin.zhfz.jzgl.entity.JzgGxEntity;

 
@Service
public class JzgGxServiceImpl implements IJzgGxService {


	@Autowired
	private IJzgGxMapper jzgGxMapper = null;

	@Override
	public void insertJzgGx(JzgGxEntity entity) {
		jzgGxMapper.insertJzgGx(entity);
		
	}

	@Override
	public void deletejzgGxById(Long id) {
		jzgGxMapper.deletejzgGxById(id);
		
	}

	@Override
	public void updateJzgGxGmxs(JzgGxEntity entity) {
		jzgGxMapper.updateJzgGxGmxs(entity);
		
	}

	@Override
	public void deleteGxByjzgGmxxId(Long jzgGmxxId) {
		jzgGxMapper.deleteGxByjzgGmxxId(jzgGmxxId);		
	}

	@Override
	public List<JzgGxEntity> queryAllUserGmxx(Map<String, Object> map) {
		return jzgGxMapper.queryAllUserGmxx(map);
	}

	@Override
	public int countAllUserGmxx(Map<String, Object> map) {
		return jzgGxMapper.countAllUserGmxx(map);
	}

	 

	
}
