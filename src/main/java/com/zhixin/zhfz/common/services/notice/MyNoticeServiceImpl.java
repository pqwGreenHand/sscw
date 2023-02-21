package com.zhixin.zhfz.common.services.notice;

import com.zhixin.zhfz.common.dao.notice.IInformMapper;
import com.zhixin.zhfz.common.dao.schedule.IScheduleMapper;
import com.zhixin.zhfz.common.entity.InformEntity;
import com.zhixin.zhfz.common.entity.ScheduleEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MyNoticeServiceImpl implements IMyNoticeService {

	@Autowired
	private IInformMapper informMapper;

	@Autowired
	private IScheduleMapper scheduleMapper;

	@Override
	public List<InformEntity> listInform(Map<String, Object> map) {
		return informMapper.list(map);
	}

	@Override
	public int count(Map<String, Object> map) {
		return informMapper.count(map);
	}

	@Override
	public List<ScheduleEntity> listSchedule(Map<String, Object> map) {
		return scheduleMapper.list(map);
	}

	@Override
	public void insertInform(InformEntity entity) throws Exception {
		if (informMapper.isInform(entity) == 0) {
			System.err.println("---------添加 Inform--------" + entity.toString());
			informMapper.insert(entity);
		} else {
			System.err.println("---------已存在 Inform--------" + entity.toString());
		}
	}

	@Override
	public List<InformEntity> listInformHistory(Map<String, Object> map) {
		return informMapper.listhistory(map);
	}

	@Override
	public int listInformHistoryCount(Map<String, Object> map) {
		return informMapper.listhistoryCount(map);
	}

	@Override
	public void updatInformById(Long id) {
		informMapper.updatInformById(id);
	}

	@Override
	public List<ScheduleEntity> listScheduleHistory(Map<String, Object> map) {
		return scheduleMapper.listhistory(map);
	}

	@Override
	public int listScheduleHistoryCount(Map<String, Object> map) {
		return scheduleMapper.listhistorycount(map);
	}

	@Override
	public void insertSchedule(ScheduleEntity entity) throws Exception {
		if (scheduleMapper.isSchedule(entity) == 0) {
			System.err.println("---------添加 Schedule--------" + entity.toString());
			scheduleMapper.insert(entity);
		} else {
			System.err.println("---------已存在 Schedule--------" + entity.toString());
		}
	}

	@Override
	public int isInform(InformEntity entity) {
		return this.informMapper.isInform(entity);
	}

	@Override
	public void changeInformOvertime() {
		this.informMapper.changeInformOvertime();
	}

	@Override
	public void changeScheduleOvertime() {
		this.scheduleMapper.changeScheduleOvertime();
	}

	@Override
	public void cleanInform() {
		this.informMapper.cleanInform();
	}

	@Override
	public void cleanSchedule() {
		this.scheduleMapper.cleanSchedule();
	}



}
