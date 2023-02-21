package com.zhixin.zhfz.jzgl.services.grdb;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhixin.zhfz.jzgl.dao.grdb.IGrdbMapper;
import com.zhixin.zhfz.jzgl.entity.GrdbEntity;

/**
 * 个人待办
 * @author xdp
 *
 */
@Service
public class GrdbServiceImpl implements IGrdbService {

	@Autowired
	private IGrdbMapper grdbMapper = null;
	
	@Override
	public List<GrdbEntity> listGrdbXx(Map<String, Object> paramMap) {
		return grdbMapper.listGrdbXx(paramMap);
	}

	@Override
	public int countListGrdbXx(Map<String, Object> paramMap) {
		return grdbMapper.countListGrdbXx(paramMap);
	}
	

}
