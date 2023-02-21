package com.zhixin.zhfz.common.services.notice;

import com.zhixin.zhfz.common.entity.InformEntity;
import com.zhixin.zhfz.common.entity.ScheduleEntity;

import java.util.List;
import java.util.Map;

public interface IMyNoticeService {

	public List<InformEntity>  listInform(Map<String, Object> map);

	int count(Map<String, Object> map);
	
	public List<ScheduleEntity> listSchedule(Map<String, Object> map);
	
	public void insertInform(InformEntity entity) throws Exception;
	
	public List<InformEntity> listInformHistory(Map<String, Object> map);
	
	public int listInformHistoryCount(Map<String, Object> map);
	
	public void updatInformById(Long id);

	public List<ScheduleEntity> listScheduleHistory(Map<String, Object> map);
	
	public int listScheduleHistoryCount(Map<String, Object> map);
	
	public void insertSchedule(ScheduleEntity entity) throws Exception;
	
	public int isInform(InformEntity entity);
	
	public void changeInformOvertime();
	
	public void changeScheduleOvertime();
	
	public void cleanInform();
	
	public void cleanSchedule();


}
