package com.zhixin.zhfz.bacs.controller.quartz;

import com.zhixin.zhfz.bacs.entity.AreaEntity;
import com.zhixin.zhfz.bacs.entity.SerialEntity;
import com.zhixin.zhfz.bacs.services.area.IAreaService;
import com.zhixin.zhfz.bacs.services.serial.ISerialService;
import com.zhixin.zhfz.bacsapp.entity.InformationEntity;
import com.zhixin.zhfz.bacsapp.services.Information.IInformationService;
import com.zhixin.zhfz.common.entity.InformEntity;
import com.zhixin.zhfz.common.entity.UserEntity;
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
public class OvertimeQuartz {

	private static final Logger logger = LoggerFactory.getLogger(OvertimeQuartz.class);

	@Autowired
	private ISerialService serialService;

	@Autowired
	private IMyNoticeService noticeService;

	@Autowired
	private IUserService userService;
	@Autowired
	private IAreaPatrolService areaPatrolService;
 
	@Autowired
	private IInformationService iInformationService;
	@Autowired
	private IAreaService areaService;
	
    /**
     * 我的通知  羁押超时
     * 给xt_user 为管理中队发送
     */
	public void overtimeWakeup() {
		List<SerialEntity> serials = new ArrayList<SerialEntity>();
		try {
			Date thed = new Date();
			String crd = "currDate:" + DateFormatUtils.format(thed, "yyyy-MM-dd HH:mm:ss");
			logger.info("### 超时过羁押时间提醒开始 xt_inform ###" + crd);
			Map<String,Object> map = new HashMap<String,Object>();
			serials = serialService.listOver23Hours(map);// 先查询超出24小时的入区记录
			if (serials != null && serials.size() > 0) {
				for (int i = 0; i < serials.size(); i++) {
					SerialEntity serial = new SerialEntity();
					serial = serials.get(i); 
					AreaEntity area = areaService.getAreaById(serial.getAreaId().longValue());
					List<UserEntity> users = userService.getUsersByOrgAndType(area.getOrganizationId(),"管理中队");
					
					for(int j = 0; j < users.size(); j++) {
						 InformationEntity inform = new InformationEntity();
				         inform.setSenderId(1L);//系统发送
				         inform.setReceiverId(users.get(j).getId().longValue()); 
				         inform.setTitle("羁押超时通知");
						String suspectName = "";// 嫌疑人姓名 
						if (serial.getName() != null && serial.getName() != "") {
							suspectName = serial.getName();
						}
						String certificateTypeName = "";// 证件类型
						if (serial.getCertificateTypeName() != null && serial.getCertificateTypeName() != "") {
							certificateTypeName = serial.getCertificateTypeName();
						}
						String certificateNo = "";// 证件号码
						if (serial.getCertificateNo() != null && serial.getCertificateNo() != "") {
							certificateNo = serial.getCertificateNo();
						}
						  inform.setContent(
								"羁押嫌疑人:" + suspectName + "（" + certificateTypeName + "：" + certificateNo + "）即将超过24小时");
				         inform.setSendTime(new Date());
				         inform.setSystemName("BA");
				         inform.setType(0);
				         inform.setIsRead(0);
				         //inform.setOpPid();
				         inform.setOpUserId(1); 
				         if(this.iInformationService.isInform(inform)== 0) {
				        	 this.iInformationService.insertInform(inform); 
				         } 
					}
					 
				}

			}
			logger.info("### 超时过羁押时间提醒结束  ###");
		} catch (Exception e) {
			logger.error("", e);
		}
	}
	
	
	
	
	
	
	
	
	public void overtimeWakeupOld() {
		List<SerialEntity> serials = new ArrayList<SerialEntity>();
		try {
			Date thed = new Date();
			String crd = "currDate:" + DateFormatUtils.format(thed, "yyyy-MM-dd HH:mm:ss");
			System.err.println("### 超时过羁押时间提醒开始  ###" + crd);
			Map<String,Object> map = new HashMap<String,Object>();
			serials = serialService.listOver23Hours(map);// 先查询超出24小时的入区记录
			if (serials != null && serials.size() > 0) {
				for (int i = 0; i < serials.size(); i++) {
					SerialEntity serial = new SerialEntity();
					serial = serials.get(i);
					InformEntity inform = new InformEntity();
					inform.setSenderId(1);// 发送方为系统
					inform.setTitle("羁押超时通知");
					String suspectName = "";// 嫌疑人姓名
					AlarmEntity entity = new AlarmEntity();
					entity.setStatus(0);
					entity.setAlarmType(7);
					entity.setAreaId(serial.getAreaId().toString());
					entity.setPoliceId(serial.getInUserCertificateNo1());
					entity.setCuffId(serial.getCuffId());
					entity.setAlarmName("羁押超时告警 ");
					areaPatrolService.addAlarm(entity);
					//南沙版违规管理
					/*if (vioationStatemtService.viocountbycaseno(serial.getCaseNo())<1 ){
						Violationstatement violationstatement = new Violationstatement();
						violationstatement.setLaw_case_name(serial.getCaseName());
						violationstatement.setLaw_case_no(serial.getCaseNo());
						violationstatement.setPolice_name(serial.getInUserRealName1());
						violationstatement.setUnit_id(serial.getOrganizationid());
						violationstatement.setIllegal_project_id("1");
						violationstatement.setCreation_time(new Date(System.currentTimeMillis()));
						vioationStatemtService.InterrogateInsert(violationstatement);
					}*/
					if (serial.getName() != null && serial.getName() != "") {
						suspectName = serial.getName();
					}
					String certificateTypeName = "";// 证件类型
					if (serial.getCertificateTypeName() != null && serial.getCertificateTypeName() != "") {
						certificateTypeName = serial.getCertificateTypeName();
					}
					String certificateNo = "";// 证件号码
					if (serial.getCertificateNo() != null && serial.getCertificateNo() != "") {
						certificateNo = serial.getCertificateNo();
					}
					inform.setContent(
							"羁押嫌疑人:" + suspectName + "（" + certificateTypeName + "：" + certificateNo + "）即将超过24小时");
					inform.setType(0);
					inform.setIsRead(0);
					// 给入区民警1发送
					if (serial.getInUserCertificateNo1() != null) {
						inform.setReceiverId(serial.getInUserCertificateNo1());
						this.noticeService.insertInform(inform);
						// 发送短信
						/*if (this.noticeService.isInform(inform) == 0) {
							String smsContent = "您好，嫌疑人【" + suspectName + "】即将超过24小时羁押期限，请加快案件办理或申请延期。";
							String mobile = userService.getUserByID(serial.getInUserId1()).getMobile();
							logger.info("###发送短信：手机号码【" + mobile + "】,内容【" + smsContent + "】.");
							SMSSender.sendSMS(new String[] { mobile }, smsContent);
						}*/
					}
					// 给入区记录民警发送
					if (serial.getInRegisterUserId() != null) {
						inform.setReceiverId(serial.getInRegisterUserId());
						this.noticeService.insertInform(inform);
					}
				}

			}
			logger.info("### 超时过羁押时间提醒结束  ###");
		} catch (Exception e) {
			logger.error("", e);
		}
	}

}
