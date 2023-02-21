package com.zhixin.zhfz.bacs.services.room;

import com.zhixin.zhfz.common.common.LedUtil;
import com.zhixin.zhfz.bacs.dao.led.ILedMapper;
import com.zhixin.zhfz.bacs.dao.room.IRoomMapper;
import com.zhixin.zhfz.bacs.entity.LEDControlEntity;
import com.zhixin.zhfz.bacs.entity.LedEntity;
import com.zhixin.zhfz.bacs.entity.RoomEntity;
import com.zhixin.zhfz.common.utils.PropertyUtil;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RoomServiceImpl implements IRoomService {

	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(RoomServiceImpl.class);

	@Autowired
	private IRoomMapper roomMapper;

	@Autowired
	private ILedMapper ledMapper;

	@Override
	public void updateRoomStatus(int roomId, int status, String roomName) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("id", roomId);
		param.put("status", status);
		//param.put("roomName", roomName);
		roomMapper.updateRoomStatus(param);
		//param.clear();
		//param.put("roomId", roomId);
		/*try {
			// 获取led启动后台web service url
			String url = PropertyUtil.getContextProperty("sms.url") + "";
			// 控制刷卡取电控制器
			// try{
			// ClientConfig clientConfig = new DefaultClientConfig();
			// // for json support
			// clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING,
			// Boolean.TRUE);
			// // for json support
			// clientConfig.getClasses().add(JacksonJsonProvider.class);
			// Client c = Client.create(clientConfig);
			//
			// WebResource r = c.resource(url);
			// // 处理string返回值
			// System.out.println(r.path("updateStatus").path(status+"").path(roomId+"").get(String.class));
			//
			// logger.info("second method power control result success");
			// }catch(Exception ex){
			// logger.info("first method power control
			// exception="+ex.getMessage());
			//
			// }

			List<LedEntity> leds = ledMapper.list(param);
			if (leds != null && leds.size() > 0) {
				LedEntity ledEntity = leds.get(0);
				String colorStr = LEDControlEntity.COLOR_RED;
				String content = "";
				if (status == 0) {
					// content = "空闲中";
					content = roomName;
					//colorStr = LEDControlEntity.COLOR_GREEN;
				} else if (status == 1) {
					content = "已预约";
					//colorStr = LEDControlEntity.COLOR_YELLOW;
				} else if (status == 2) {
					content = "使用中";
					//colorStr = LEDControlEntity.COLOR_RED;
				}
				LEDControlEntity led = new LEDControlEntity();
				led.setColor(colorStr);
				led.setContent(content);
				led.setIp(ledEntity.getIp());
				led.setPort(ledEntity.getPort());
				led.setShowType(LEDControlEntity.SHOWTYPE_STATIC);
				led.setPower(LEDControlEntity.POWER_ON);
				led.setSaveMethod(LEDControlEntity.POWER_PLAY);
				// 获取led启动后台web service url

				logger.info("===url：" + url + "===led json：" + led.toString());
				// 启动led
				
				
				LedUtil.controlLED(url, led);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}

	/*
	 * 强制结束笔录调用
	 */
	@Override
	public void stopRecordStatus(int roomId, int status, String roomName) {
		Map<String, Object> param = new HashMap<String, Object>();

		param.put("id", roomId);
		param.put("status", status);
		roomMapper.updateRoomStatus(param);
		param.clear();
		param.put("roomId", roomId);
		try {
			// 获取led启动后台web service url
			String url = PropertyUtil.getContextProperty("sms.url") + "";

			List<LedEntity> leds = ledMapper.list(param);
			if (leds != null && leds.size() > 0) {
				LedEntity ledEntity = leds.get(0);
				String colorStr = LEDControlEntity.COLOR_RED;
				String content = "";
				if (status == 0) {
					// content = "空闲中";
					content = roomName;
					//colorStr = LEDControlEntity.COLOR_GREEN;
				} else if (status == 1) {
					content = "已预约";
					//colorStr = LEDControlEntity.COLOR_YELLOW;
				} else if (status == 2) {
					content = "使用中";
					//colorStr = LEDControlEntity.COLOR_RED;
				}

				LEDControlEntity led = new LEDControlEntity();
				led.setColor(colorStr);
				led.setContent(content);
				led.setIp(ledEntity.getIp());
				led.setPort(ledEntity.getPort());
				led.setShowType(LEDControlEntity.SHOWTYPE_STATIC);
				led.setPower(LEDControlEntity.POWER_ON);
				led.setSaveMethod(LEDControlEntity.POWER_PLAY);
				// 获取led启动后台web service url
				logger.info("----url:" + url + "----------Status-" + status + "-color-" + colorStr + "---room" + content
						+ "----" + led.toString());
				// logger.info("===url："+url+"===led json："+led.toString());
				// 启动led
				LedUtil.controlLED(url, led);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 0关，1开
	// http://127.0.0.1:8081/interrogate/restful/roomPowerControlWS/updateStatus/{status}/{roomId}
	@Override
	public void updateRoomStatus(int roomId, int status) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("id", roomId);
		param.put("status", status);
		roomMapper.updateRoomStatus(param);
		param.clear();
		param.put("roomId", roomId);
		try {
			// 获取led启动后台web service url
			String url = PropertyUtil.getContextProperty("sms.url") + "";
			// 控制刷卡取电控制器
			// try{
			//
			// ClientConfig clientConfig = new DefaultClientConfig();
			// // for json support
			// clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING,
			// Boolean.TRUE);
			// // for json support
			// clientConfig.getClasses().add(JacksonJsonProvider.class);
			// Client c = Client.create(clientConfig);
			//
			// WebResource r = c.resource(url);
			// // 处理string返回值
			// System.out.println(r.path("updateStatus").path(1+"").path(roomId+"").get(String.class));
			//
			// logger.info("second method power control result success");
			// }catch(Exception ex){
			// logger.info("second method power control
			// exception="+ex.getMessage());
			// }

			List<LedEntity> leds = ledMapper.list(param);
			if (leds != null && leds.size() > 0) {
				LedEntity ledEntity = leds.get(0);
				String colorStr = LEDControlEntity.COLOR_RED;
				String content = "";
				if (status == 0) {
					content = "空闲中";
					//colorStr = LEDControlEntity.COLOR_GREEN;
				} else if (status == 1) {
					content = "已预约";
					//colorStr = LEDControlEntity.COLOR_YELLOW;
				} else if (status == 2) {
					content = "使用中";
					//colorStr = LEDControlEntity.COLOR_RED;
				}
				LEDControlEntity led = new LEDControlEntity();
				led.setColor(colorStr);
				led.setContent(content);
				led.setIp(ledEntity.getIp());
				led.setPort(ledEntity.getPort());
				led.setShowType(LEDControlEntity.SHOWTYPE_STATIC);
				led.setPower(LEDControlEntity.POWER_ON);
				led.setSaveMethod(LEDControlEntity.POWER_PLAY);

				// 启动led
				LedUtil.controlLED(url, led);
				
				
				//logger.info("===url：" + url + "===led json：" + led.toString());

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	@Override
	public RoomEntity getRoomById(Long roomId) {

		return roomMapper.getRoomById(roomId);
	}

	@Override
	public RoomEntity findRoomByIps(Map<String, Object> map) {
		return roomMapper.findRoomByIps(map);
	}

}
