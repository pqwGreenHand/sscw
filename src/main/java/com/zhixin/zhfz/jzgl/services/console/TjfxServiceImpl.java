package com.zhixin.zhfz.jzgl.services.console;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhixin.zhfz.jzgl.dao.console.ITjfxMapper;
import com.zhixin.zhfz.jzgl.entity.JzlxRateEntity;

@Service
public class TjfxServiceImpl implements ITjfxService {

	@Autowired
	private ITjfxMapper tjfxMapper = null;
	@Override
	public List<JzlxRateEntity> selectAllGmxx(Map<String, Object> mapParam) {
		return tjfxMapper.selectAllGmxx(mapParam);
	}

	@Override
	public List<JzlxRateEntity> selectUsedGmxx(Map<String, Object> mapParam) {
		return tjfxMapper.selectUsedGmxx(mapParam);
	}

	@Override
	public List<Map<String, String>> queryAllDwGj(Map<String, Object> mapParam) {
		return tjfxMapper.queryAllDwGj(mapParam);
	}

	@Override
	public List<Map<String, String>> queryAllMjGj(Map<String, Object> mapParam) {
		return tjfxMapper.queryAllMjGj(mapParam);
	}

}
