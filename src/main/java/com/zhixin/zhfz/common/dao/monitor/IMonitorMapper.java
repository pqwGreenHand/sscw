package com.zhixin.zhfz.common.dao.monitor;

import com.zhixin.zhfz.common.entity.MonitorEntity;

import java.util.List;
import java.util.Map;

public interface IMonitorMapper {

	public List<MonitorEntity> list(Map<String, Object> map); 
	
	public MonitorEntity queryById(int id);
	
	public void update(Map<String, Object> map);

	public void insertMonitorEntity(MonitorEntity entity);

	public void updateMonitorByID(MonitorEntity entity);

	void deleteMonitorByID(Integer id);
}
