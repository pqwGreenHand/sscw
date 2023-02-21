package com.zhixin.zhfz.bacs.services.positionManage;


import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhixin.zhfz.bacs.dao.positionManage.IPositionManageMapper;
import com.zhixin.zhfz.bacs.entity.PositionDataEntity;

@Service
public class PositionManageServiceImpl implements IPositioManageService {

	private static Logger logger = LoggerFactory.getLogger(PositionManageServiceImpl.class);
	@Autowired
	private  IPositionManageMapper positionMapper;
	@Override
	public List<Map<String, Object>> selectPositionDataPersonInfo(Map<String, Object> map) {
		return positionMapper.selectPositionDataPersonInfo(map);
	}

	@Override
	public int selectPositionDataPersonInfoCount(Map<String, Object> map) {
		return positionMapper.selectPositionDataPersonInfoCount(map);
	}

	@Override
	public List<Map<String, Object>> selectPositionDataPoliceInfo(Map<String, Object> map) {
		return positionMapper.selectPositionDataPoliceInfo(map);
	}

	@Override
	public int selectPositionDataPoliceInfoCount(Map<String, Object> map) {
		return positionMapper.selectPositionDataPoliceInfoCount(map);
	}

	@Override
	public List<PositionDataEntity> list(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return positionMapper.list(map);
	}

	@Override
	public int count(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return positionMapper.count(map);
	}

	@Override
	public List<Map<String, Object>> selectPositionDataSearialInfo(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return positionMapper.selectPositionDataSearialInfo(map);
	}

	@Override
	public int selectPositionDataSearialInfoCount(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return positionMapper.selectPositionDataSearialInfoCount(map);
	}
	
}
