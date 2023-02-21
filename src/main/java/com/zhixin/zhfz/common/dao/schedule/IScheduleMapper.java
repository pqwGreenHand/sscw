package com.zhixin.zhfz.common.dao.schedule;

import com.zhixin.zhfz.common.entity.ScheduleEntity;
import com.zhixin.zhfz.common.form.ScheduleForm;

import java.util.List;
import java.util.Map;

public interface IScheduleMapper {

	public List<ScheduleEntity> list(Map<String, Object> map);

	int count(Map<String, Object> map);

	public List<ScheduleEntity> listhistory(Map<String, Object> map);

	public int listhistorycount(Map<String, Object> map);

	public void insert(ScheduleEntity entity) throws Exception;

	public int isSchedule(ScheduleEntity entity);

	public void changeScheduleOvertime();

	public void cleanSchedule();


	int updateScheduleById(ScheduleForm scheduleForm);
}