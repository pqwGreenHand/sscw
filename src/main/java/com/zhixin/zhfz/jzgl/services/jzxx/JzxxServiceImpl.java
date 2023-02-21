package com.zhixin.zhfz.jzgl.services.jzxx;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhixin.zhfz.jzgl.dao.jzxx.IJzxxMapper;
import com.zhixin.zhfz.jzgl.entity.JzxxEntity;

/**
 * 
 * @author cuichengwei
 */
@Service
public class JzxxServiceImpl implements IJzxxService {

	private static Logger logger = Logger.getLogger(JzxxServiceImpl.class);

	@Autowired
	private IJzxxMapper jzxxMapper = null;

	@Override
	public List<JzxxEntity> getJzxxByAjxxId(Map<String, Object> map) throws Exception {
		return jzxxMapper.getJzxxByAjxxId(map);
	}

	@Override
	public Integer countJzxxByAjxxId(Map<String, Object> map) throws Exception {
		return jzxxMapper.countJzxxByAjxxId(map);
	}

	@Override
	public List<JzxxEntity> getJzxxByGmxxId(Map<String, Object> map) throws Exception {
		return jzxxMapper.getJzxxByGmxxId(map);
	}

	@Override
	public Integer countJzxxByGmxxId(Map<String, Object> map) throws Exception {
		return jzxxMapper.countJzxxByGmxxId(map);
	}

	@Override
	public JzxxEntity getJzxxById(Long id) throws Exception {
		return jzxxMapper.getJzxxById(id);
	}

	@Override
	public void updateJzxx(JzxxEntity entity) throws Exception {
		jzxxMapper.updateJzxx(entity);
	}

	@Override
	public List<String> getGzbhByAjId(Long id) throws Exception {
		return jzxxMapper.getGzbhByAjId(id);
	}

	@Override
	public void insertJzxx(JzxxEntity entity) throws Exception {
		jzxxMapper.insertJzxx(entity);
	}

	@Override
	public JzxxEntity getJzxxForQR(Long id) throws Exception {
		return jzxxMapper.getJzxxForQR(id);
	}

	@Override
	public List<JzxxEntity> listJzXzCount(Map<String, Object> map) {
		return jzxxMapper.listJzXzCount(map);
	}

	@Override
	public List<JzxxEntity> queryXsxzBy30Count(Map<String, Object> map) {
		return jzxxMapper.queryXsxzBy30Count(map);
	}

	@Override
	public List<JzxxEntity> getJzxxAndAjxx(Map<String, Object> map) throws Exception {
		return jzxxMapper.getJzxxAndAjxx(map);
	}

	@Override
	public Integer countJzxxAndAjxx(Map<String, Object> map) throws Exception {
		return jzxxMapper.countJzxxAndAjxx(map);
	}

	@Override
	public List<JzxxEntity> getJzxxByuuid(Map<String, Object> map) throws Exception {
		return jzxxMapper.getJzxxByuuid(map);
	}

	@Override
	public void updateJzztByuuid(Map<String, Object> map) throws Exception {
		jzxxMapper.updateJzztByuuid(map);
	}
}