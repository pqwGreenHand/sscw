package com.zhixin.zhfz.bacs.controller.quartz;

import com.zhixin.zhfz.bacs.entity.LedEntity;
import com.zhixin.zhfz.bacs.entity.RecordEntity;
import com.zhixin.zhfz.bacs.services.led.ILedService;
import com.zhixin.zhfz.bacs.services.record.IRecordService;
import com.zhixin.zhfz.bacs.services.room.IRoomService;
import com.zhixin.zhfz.bacs.services.serial.ISerialService;
import com.zhixin.zhfz.common.common.HttpClientUtil;
import com.zhixin.zhfz.common.common.LedOldUtil;
import com.zhixin.zhfz.common.common.led.LedUtil;

import net.sf.json.JSONObject;
import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecordQuartz {

	private static final Logger logger = LoggerFactory.getLogger(RecordQuartz.class);

	@Autowired
	private IRecordService irecordService;

	@Autowired
	private ISerialService serialService;

	@Autowired
	private IRoomService roomService;
	
	@Autowired
	private ILedService ledService;

	public void query() {
		try {
			logger.debug("### RecordQuartz query ###" + DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
			List<RecordEntity> list = irecordService.listRecordQuartz();
			if (list != null) {
				for (int i = 0; i < list.size(); i++) {
					RecordEntity obj = list.get(i);
					int oldStatus = obj.getStatus();
					//String url = "http://14.112.65.175:7000/Easy7/rest/inquest/findById";
					String url = "http://14.27.2.251:20183/manager/record/inquest/findById";
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("inquestId", obj.getUuid());

					String para = jsonObject.toString();
					logger.debug("### url ###" + url);
					logger.debug("### param ###" + para);
					Map<String, String> param = new HashMap<>();
					param.put("param", para);
					JSONObject result = HttpClientUtil.get(url, param);
					logger.debug("### result ###" + result);

					int ret = (int) result.get("ret");
					Object content = result.get("content");
					if (ret == 0 && content != null && !"".equals(content.toString())) {
						int newStatus = Integer.parseInt(content.toString());
						if (oldStatus != newStatus) {
							logger.info("111111111111111111newStatus1111111111111==="+newStatus);
							obj.setStatus(newStatus);
							//irecordService.updateStatusQuartz(obj);//已完成
							if (newStatus==0){//已结束
								obj.setStatus(3);
								irecordService.updateStatusQuartz(obj);//已完成
								if (obj.getRoomId()!=null && obj.getRoomId()>0){
									try {
										roomService.updateRoomStatus(Integer.parseInt(obj.getRoomId()+""), 0);
									} catch (Exception e) {
										logger.error("",e);
									}
									//led
									  try {
							          	  LedEntity led = ledService.LedByRoomId(Integer.parseInt(obj.getRoomId().toString()));

										  led.setIp(led.getIp());
										  led.setPort(led.getPort());
										  led.setColor("red");
										  led.setContent(led.getRoomName());
										  if (led.getContent().indexOf("未成年讯询问") > -1) {
											  led.setFontSize(12);
											  led.setLeft(4);
											  led.setTop(6);
										  } else {
											  led.setFontSize(17);
											  led.setLeft(8);
											  led.setTop(2);
										  }
										  led.setWidth(200);
										  led.setHeight(24);
										  LedOldUtil.controlLED2010(led);
//							          	  LedUtil.ledPlay(led.getRoomName(), led.getIp(), led.getPort(), 0);
								  		 }catch (Exception e) {
								  			 logger.info("++++++++led error 设置有问题++++++");
										}
								}
								try {
									serialService.changestatus(obj.getSerialId(), 7);
								} catch (Exception e) {
									logger.error("",e);
								}
							}
						}
					}
				} // end for
			} // end if
		} catch (Exception e) {
			logger.error("", e);
		}
	}
}
