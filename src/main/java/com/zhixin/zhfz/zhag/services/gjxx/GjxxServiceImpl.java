package com.zhixin.zhfz.zhag.services.gjxx;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhixin.zhfz.zhag.dao.gjxx.IAgGjxxMapper;
import com.zhixin.zhfz.zhag.entity.GjxxEntity;

@Service
public class GjxxServiceImpl implements IGjxxService{

	@Autowired
	private IAgGjxxMapper gjxxMapper;
	 
	@Override
	public List<GjxxEntity> selectGjxx(Map<String, Object> map) {
		return gjxxMapper.selectGjxx(map);
	}

	@Override
	public Integer countGjxx(Map<String, Object> map) {
		return gjxxMapper.countGjxx(map);
	}

	@Override
	public List<GjxxEntity> jqGjList(Map<String, Object> map) {
		return gjxxMapper.jqGjList(map);
	}

	@Override
	public Integer countJqGj(Map<String, Object> map) {
		return gjxxMapper.countJqGj(map);
	}

}
