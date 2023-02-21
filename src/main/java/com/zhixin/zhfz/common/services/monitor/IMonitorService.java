package com.zhixin.zhfz.common.services.monitor;

import com.zhixin.zhfz.common.entity.MonitorEntity;

import java.util.List;
import java.util.Map;

public interface IMonitorService {

	public List<MonitorEntity> list(Map<String, Object> map); 
	
	public MonitorEntity queryById(int id);
	
	public void update(Map<String, Object> map);

	void insertMonitorEntity(MonitorEntity entity);

	void updateMonitorByID(MonitorEntity entity);

	void deleteMonitorByID(Integer id);
}
