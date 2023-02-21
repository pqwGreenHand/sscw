package com.zhixin.zhfz.bacs.services.positionManage;


import java.util.List;
import java.util.Map;

import com.zhixin.zhfz.bacs.entity.PositionAlarmEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhixin.zhfz.bacs.dao.positionManage.IPositionAlarmMapper;

@Service
public class PositionAlarmServiceImpl implements IPositioAlarmService {

	private static Logger logger = LoggerFactory.getLogger(PositionAlarmServiceImpl.class);
	@Autowired
	private  IPositionAlarmMapper  positionAlarmMapper;
	@Override
	public List<PositionAlarmEntity> list(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return positionAlarmMapper.list(map);
	}
	@Override
	public int count(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return positionAlarmMapper.count(map);
	}
	
}
