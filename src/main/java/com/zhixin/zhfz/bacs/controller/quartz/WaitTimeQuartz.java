package com.zhixin.zhfz.bacs.controller.quartz;

import com.zhixin.zhfz.bacs.entity.SerialEntity;
import com.zhixin.zhfz.bacs.entity.WaitingPersonCountEntity;
import com.zhixin.zhfz.bacs.services.serial.ISerialService;
import com.zhixin.zhfz.bacs.services.waitingmanage.IWaitingManageService;
import com.zhixin.zhfz.common.entity.InformEntity;
import com.zhixin.zhfz.common.services.notice.IMyNoticeService;
import com.zhixin.zhfz.common.services.user.IUserService;
import com.zhixin.zhfz.glpt.entity.AlarmEntity;
import com.zhixin.zhfz.glpt.services.areaPatrol.IAreaPatrolService;
import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class WaitTimeQuartz {

	private static final Logger logger = LoggerFactory.getLogger(WaitTimeQuartz.class);

	@Autowired
	private IWaitingManageService waitingManageService;

	@Autowired
	private IAreaPatrolService areaPatrolService;

	public void waitTimeQuartz() {
		Map<String,Object> map = new HashMap<String,Object>();
		List<WaitingPersonCountEntity> list = waitingManageService.personRoomCountList(map);
		for (int i = 0;i<list.size();i++){
			if(list.get(i).getSum()>=list.get(i).getVolume()){
				AlarmEntity entity = new AlarmEntity();
				entity.setStatus(0);
				entity.setAlarmType(10);
				entity.setAreaId(String.valueOf(list.get(i).getAreaId()));
				entity.setAlarmName("侯问容积超出告警");
				entity.setRoomId(list.get(i).getRoomId());
				areaPatrolService.addAlarm(entity);
			}
		}
	}

}
