package com.zhixin.zhfz.bacs.controller.quartz;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zhixin.zhfz.bacs.entity.AreaEntity;
import com.zhixin.zhfz.bacs.entity.SerialEntity;
import com.zhixin.zhfz.bacs.services.area.IAreaService;
import com.zhixin.zhfz.bacs.services.serial.ISerialService;
import com.zhixin.zhfz.bacsapp.entity.InformationEntity;
import com.zhixin.zhfz.bacsapp.services.Information.IInformationService;
import com.zhixin.zhfz.common.entity.UserEntity;
import com.zhixin.zhfz.common.services.user.IUserService;

/**
 * 我的通知
 * 提醒嫌疑人XXX，入区即将超过10个小时，
 * 需要办理（提前半个小时提醒，10个小时之后在提醒半个小时，
 * 每10分钟提醒一次）
 * 
 * 执行计划按10分钟执行一次
 * @author xdp
 *
 */
@Component
public class WarnTimeQuartz {
	
	private static final Logger logger = LoggerFactory.getLogger(WarnTimeQuartz.class);
	
	@Autowired
	private ISerialService serialService;
	
	@Autowired
	private IInformationService iInformationService;
	
	@Autowired
	private IAreaService areaService;
	
	@Autowired
	private IUserService userService;
	
	public void warnTimeQuartz() {
		List<SerialEntity> serials = new ArrayList<SerialEntity>();
		Map<String,Object> params = new HashMap<String,Object>();
		try {
			serials = serialService.listWarnTime(params);
			for (int i = 0; i < serials.size(); i++) {
				SerialEntity serial = new SerialEntity();
				serial = serials.get(i);
				AreaEntity area = areaService.getAreaById(serial.getAreaId().longValue());
				List<UserEntity> users = userService.getUsersByOrgAndType(area.getOrganizationId(),"管理中队");
				 for (int j = 0; j < users.size(); j++) {
					 InformationEntity inform = new InformationEntity();
			         inform.setSenderId(1L);//系统发送
			         inform.setReceiverId(users.get(j).getId().longValue());
			         inform.setTitle("提醒通知");
			         inform.setContent("嫌疑人"+serial.getName()+"，入区即将超过10个小时，需要办理!");
			         inform.setSendTime(new Date());
			         inform.setSystemName("BA");
			         inform.setType(0);
			         inform.setIsRead(0);
			         //inform.setOpPid();
			         inform.setOpUserId(1);
			         this.iInformationService.insertInform(inform); 
				 } 
			} 
			
		}catch (Exception e) { 
			logger.info("通知提醒错误！"+e.getMessage());
		}
		
		
		
		
		
		
		
		
		
	}
	
	
	

}
