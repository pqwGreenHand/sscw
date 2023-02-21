package com.zhixin.zhfz.common.common;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import com.zhixin.zhfz.bacs.entity.LEDControlEntity;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.MediaType;

/**
 * 调用接口
 * Created by wolf on 2016/4/21.
 */
public class LedUtil {

	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(LedUtil.class);

	private static ClientConfig createClientConfig() {
		ClientConfig clientConfig = new DefaultClientConfig();
		// for json support
		clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
		// for json support
		clientConfig.getClasses().add(JacksonJsonProvider.class);
		return clientConfig;
	}

	static class LedContrl extends Thread {

		private Object _led;
		private String _url;

		public LedContrl(Object led, String url) {
			_led = led;
			_url = url;
		}

		public void run() {
			try {
				LedUtil.sendLED(_url, _led);
				
				logger.info("已经发送LED指令===url：" + _url + "===led json：" + _led.toString());
			} catch (Exception e) {

				logger.info("发送LED数据失败:" + e.getMessage()+"===="+ _url + "=led json：" + _led.toString());
			}
		}
	}

	public static void controlLED(String url, Object entity) {
		LedContrl ledControl = new LedContrl(entity, url);
		ledControl.start();

		// Client c = Client.create(createClientConfig());//zuri hide 20170713
		// WebResource r = c.resource(url);
		// logger.info("========led set
		// result==========="+r.path("controlled").path("DHSAJ-KLJCX-DHSKJ").accept(new
		// String[] { MediaType.APPLICATION_JSON })
		// .entity(entity, MediaType.APPLICATION_JSON).post(String.class));
	}

	private static void sendLED(String url, Object entity) {

		Client c = Client.create(createClientConfig());
		WebResource r = c.resource(url);
		logger.info("========led set result==========="
				+ r.path("controlled").path("DHSAJ-KLJCX-DHSKJ").accept(new String[] { MediaType.APPLICATION_JSON })
						.entity(entity, MediaType.APPLICATION_JSON).post(String.class));
	}

	public static void main(String[] args) {
		String url = "http://172.16.5.6:8080/interrogate-hardware/restful/ws/";
		LEDControlEntity led = new LEDControlEntity();
		led.setColor(LEDControlEntity.COLOR_RED);
		led.setContent("讯问室1");
		led.setIp("172.16.3.58");
		led.setPort(6666);
		led.setShowType(LEDControlEntity.SHOWTYPE_STATIC);
		led.setPower(LEDControlEntity.POWER_ON);
		controlLED(url, led);
	}
}
