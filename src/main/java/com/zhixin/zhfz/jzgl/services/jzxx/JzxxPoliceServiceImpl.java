package com.zhixin.zhfz.jzgl.services.jzxx;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhixin.zhfz.jzgl.dao.jzxx.IJzxxPoliceMapper;

/**
 * jzxx_police
 * 
 * @author xiangdeping
 */
@Service
public class JzxxPoliceServiceImpl implements IJzxxPoliceService {

	@Autowired
	private IJzxxPoliceMapper jzxxPoliceMapper;
 
	@Override
	public List<Map<String, Object>> queryJzxxPoliceByJzxxId(Long jzxxId) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("jzxxId", jzxxId);
		return jzxxPoliceMapper.queryJzxxPoliceByJzxxId(map);
	}
	@Override
	public void addJzxxDetailByJzxxId(Long jzxxId,Integer userId) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("jzxxId", jzxxId);
		map.put("userId", userId);
		jzxxPoliceMapper.addJzxxDetailByJzxxId(map);
	}
	@Override
	public void deleteJzxxPoliceByjzxxId(Long jzxxId) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("jzxxId", jzxxId);
		jzxxPoliceMapper.deleteJzxxPoliceByjzxxId(map);
	}

	 

}