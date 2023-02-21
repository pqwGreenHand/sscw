package com.zhixin.zhfz.jzgl.services.jzxx;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhixin.zhfz.jzgl.dao.jzxx.IJzxxDetailMapper;

@Service
public class JzxxDetailServiceImpl implements IJzxxDetailService {
	
	private static Logger logger = Logger.getLogger(JzxxDetailServiceImpl.class);
	
	@Autowired
	private IJzxxDetailMapper jzxxDetailMapper;
	
	//查询用户所选卷宗
	@Override
	public List<Map<String, Object>> queryJzxxDetailByYhId(Long jzxx_id,Integer jzlb) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("jzxx_id", jzxx_id);
		map.put("jzlb", jzlb);
		List<Map<String,Object>> list = jzxxDetailMapper.selectJzxxDetailByYhId(map);
		return list;
	}
	//添加用户所选卷宗
	@Override
	public void addJzxxDetailByYhId(Long jzxx_id,Long jz_id,Integer jzlb) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("jzxx_id", jzxx_id);
		map.put("jz_id", jz_id);
		map.put("jzlb", jzlb);
		jzxxDetailMapper.insertJzxxDetailByYhId(map);
	}
	//删除用户所选卷宗
	@Override
	public void removeJzxxDetailByYhId(Long jzxx_id,Integer jzlb) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("jzxx_id", jzxx_id);
		map.put("jzlb", jzlb);
		jzxxDetailMapper.deleteJzxxDetailByYhId(map);
	}
	//根据卷宗id删除用户所选卷宗
	@Override
	public void removeJzxxDetailByJzxxId(Long jzxx_id) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("jzxx_id", jzxx_id);
		jzxxDetailMapper.deleteJzxxDetailByYhId(map);
	}
}
