package com.zhixin.zhfz.zhag.services.yjxx;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhixin.zhfz.zhag.dao.yjxx.IYjxxMapper;
import com.zhixin.zhfz.zhag.entity.YjxxEntity;

@Service
public class YjxxServiceImpl implements IYjxxService{

	@Autowired
	private IYjxxMapper yjxxMapper;
	
	@Override
	public List<YjxxEntity> selectYjxx(Map<String, Object> map) {
		return yjxxMapper.selectYjxx(map);
	}

	@Override
	public Integer countYjxx(Map<String, Object> map) {
		return yjxxMapper.countYjxx(map);
	}

	@Override
	public List<YjxxEntity> jqYjList(Map<String, Object> map) {
		return yjxxMapper.jqYjList(map);
	}

	@Override
	public Integer countJqYj(Map<String, Object> map) {
		return yjxxMapper.countJqYj(map);
	}


}
