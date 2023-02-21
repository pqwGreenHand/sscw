package com.zhixin.zhfz.common.services.monitor;

import com.zhixin.zhfz.common.dao.monitor.IMonitorMapper;
import com.zhixin.zhfz.common.entity.MonitorEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class IMonitorServiceImpl implements IMonitorService{
	
	@Autowired
	private IMonitorMapper mapper;

	@Override
	public List<MonitorEntity> list(Map<String, Object> map){
		return mapper.list(map);
	}

	@Override
	public MonitorEntity queryById(int id) {
		return mapper.queryById(id);
	}

	@Override
	public void update(Map<String, Object> map) {
		mapper.update(map);
	}

	@Override
	public void insertMonitorEntity(MonitorEntity entity) {
		// TODO Auto-generated method stub
		mapper.insertMonitorEntity(entity);
	}

	@Override
	public void updateMonitorByID(MonitorEntity entity) {
		// TODO Auto-generated method stub
		mapper.updateMonitorByID(entity);
	}

	@Override
	public void deleteMonitorByID(Integer id) {
		// TODO Auto-generated method stub
		mapper.deleteMonitorByID(id);
	}
}
