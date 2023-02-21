package com.zhixin.zhfz.bacsapp.services.common;


import com.zhixin.zhfz.bacs.entity.RoomEntity;
import com.zhixin.zhfz.bacsapp.dao.common.ICommonMapper;
import com.zhixin.zhfz.bacsapp.entity.SerialAppEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ICommonServiceImpl implements ICommonService {

	@Autowired
	private ICommonMapper commonMapper;


	@Override

	public List<SerialAppEntity> listAllSerial(Map<String, Object> map) throws Exception {
		return commonMapper.listAllSerial(map);
	}
	@Override

	public int countAllSerial(Map<String, Object> map) throws Exception {
		return commonMapper.countAllSerial(map);
	}

	@Override

	public List<SerialAppEntity> listAllPerson(Map<String, Object> map) throws Exception {
		return commonMapper.listAllPerson(map);
	}
	@Override

	public List<RoomEntity> listAllRoom(Map<String, Object> map) throws Exception {
		return commonMapper.listAllRoom(map);
	}
}
