package com.zhixin.zhfz.bacs.controller.quartz;

import com.zhixin.zhfz.bacs.entity.RecordEntity;
import com.zhixin.zhfz.bacs.entity.WaitingPersonCountEntity;
import com.zhixin.zhfz.bacs.services.record.IRecordService;
import com.zhixin.zhfz.bacs.services.serial.ISerialService;
import com.zhixin.zhfz.bacs.services.waitingmanage.IWaitingManageService;
import com.zhixin.zhfz.common.entity.CombogridEntity;
import com.zhixin.zhfz.common.services.combogrid.ICombogridService;
import com.zhixin.zhfz.glpt.entity.AlarmEntity;
import com.zhixin.zhfz.glpt.services.areaPatrol.IAreaPatrolService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sun.security.x509.AttributeNameEnumeration;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class NoRecordPersonQuartz {

	private static final Logger logger = LoggerFactory.getLogger(NoRecordPersonQuartz.class);

	@Autowired
	private IRecordService recordService;
	@Autowired
	private ICombogridService combogridService;

	@Autowired
	private IAreaPatrolService areaPatrolService;

	public void noRecordPersonQuartz() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<CombogridEntity> list = combogridService.getSuspectSerialNo(map);
		Map<String, Object> record = new HashMap<String, Object>();
		for (int i = 0; i < list.size(); i++) {
			record.put("serialId", list.get(i).getId());
			record.put("personId", list.get(i).getPersonId());
			List<RecordEntity> listRecord = recordService.selectBySerialIdAndPersonId(record);
			Date d1 = list.get(i).getInTime();
			Date d2 = new Date();
			long diff = d2.getTime() - d1.getTime();
			long days = diff / (1000 * 60 * 60 * 24);
			long hours = (diff-days*(1000 * 60 * 60 * 24))/(1000* 60 * 60);
			//规定入区未审讯时间
			if(hours>1){
				if (listRecord.size() == 0) {
					AlarmEntity entity = new AlarmEntity();
					entity.setSerialId((long)list.get(i).getId());
					entity.setPoliceId(list.get(i).getSendUserId());
					entity.setAlarmType(9);
					entity.setAlarmName("入区未审讯告警");
					entity.setStatus(0);
					entity.setAreaId(list.get(i).getAreaId());
					areaPatrolService.addAlarm(entity);
				}
			}
		}
	}
}
