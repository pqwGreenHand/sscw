package com.zhixin.zhfz.bacs.services.positionManage;


import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhixin.zhfz.bacs.dao.positionManage.IPositionAlarmMapper;
import com.zhixin.zhfz.bacs.dao.positionManage.IPositionEntranceMapper;
import com.zhixin.zhfz.bacs.dao.positionManage.IPositionManageMapper;
import com.zhixin.zhfz.bacs.entity.PositionAlarmEntity;
import com.zhixin.zhfz.bacs.entity.PositionDataEntity;
import com.zhixin.zhfz.bacs.entity.PositionEntranceEntity;

@Service
public class PositionEntranceServiceImpl implements IPositionEntranceService {

	private static Logger logger = LoggerFactory.getLogger(PositionEntranceServiceImpl.class);
	@Autowired
	private  IPositionEntranceMapper  positionEntranceMapper;
	@Override
	public List<PositionEntranceEntity> list(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return positionEntranceMapper.list(map);
	}
	@Override
	public int count(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return positionEntranceMapper.count(map);
	}
	
}
