package com.zhixin.zhfz.bacs.controller.quartz;

import com.zhixin.zhfz.bacs.entity.SerialEntity;
import com.zhixin.zhfz.bacs.entity.WaitingPersonCountEntity;
import com.zhixin.zhfz.bacs.services.serial.ISerialService;
import com.zhixin.zhfz.bacs.services.waitingmanage.IWaitingManageService;
import com.zhixin.zhfz.glpt.entity.AlarmEntity;
import com.zhixin.zhfz.glpt.services.areaPatrol.IAreaPatrolService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class OutPersonQuartz {

	private static final Logger logger = LoggerFactory.getLogger(OutPersonQuartz.class);

	@Autowired
	private ISerialService serialService;

	@Autowired
	private IAreaPatrolService areaPatrolService;

	public void outPersonQuartz()throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status",11);
		List<SerialEntity> list = serialService.list(map);
		for (int i = 0;i<list.size();i++){
			if(list.get(i).getStatus()==10&&list.get(i).getOutTime()!=null){
				Date d1 = list.get(i).getInTime();
				Date d2 = new Date();
				long diff = d2.getTime() - d1.getTime();
				long days = diff / (1000 * 60 * 60 * 24);
				long hours = (diff-days*(1000 * 60 * 60 * 24))/(1000* 60 * 60);
				if(hours>1){
					AlarmEntity entity = new AlarmEntity();
					entity.setSerialId((long)list.get(i).getId());
					entity.setCuffId(list.get(i).getCuffId());
					entity.setPoliceId(list.get(i).getInRegisterUserId());
					entity.setAlarmType(11);
					entity.setAlarmName("临时出区超时告警");
					entity.setStatus(0);
					entity.setAreaId(list.get(i).getAreaId().toString());
					areaPatrolService.addAlarm(entity);
				}
			}
		}
	}

}
