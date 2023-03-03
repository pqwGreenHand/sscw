package com.zhixin.zhfz.common.utils;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import org.apache.log4j.Logger;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;

import javax.ws.rs.core.MediaType;
import java.util.ArrayList;

public class SMSSender {

	private static Logger logger = Logger.getLogger(SMSSender.class);

	private static ClientConfig createClientConfig() {
		ClientConfig clientConfig = new DefaultClientConfig();
		// for json support
		clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
		// for json support
		clientConfig.getClasses().add(JacksonJsonProvider.class);
		return clientConfig;
	}

	public static void sendSMS(String[] phones, String content) {
		try {
			logger.info("[[[[[ ### 发送短信 ### ]]]]]"+phones+"  "+content);
			if (content == null || "".equals(content)) {
				logger.info("[[[[[ ### 发送短信 ### no content to sendSMS ]]]]]");
				return;
			}
			if (phones == null || phones.length == 0) {
				logger.info("[[[[[ ### 发送短信 ### no phones to sendSMS ]]]]]");
				return;
			}
			ArrayList<String> list = new ArrayList<>();
			for (int i = 0; i < phones.length; i++) {
				if (com.zhixin.zhfz.common.utils.SMSUtil.testPhone(phones[i])) {
					list.add(phones[i]);
				}
			}
			if (list.size() == 0) {
				logger.info("[[[[[ ### 发送短信 ### no phones to sendSMS ]]]]]");
				return;
			}
			String[] ps = new String[list.size()];
			for (int i = 0; i < list.size(); i++) {
				ps[i] = list.get(i);
			}

			Client c = Client.create(createClientConfig());
			// String url =
			// "http://127.0.0.1:8081/interrogate-win-web/restful/ws/";
			// String url =
			// "http://127.0.0.1:11009/interrogate-win/restful/ws/";
			// String url =
			// "http://192.168.201.54:7070/interrogate-win/restful/ws/";
		} catch (Exception e) {
			logger.error("", e);
		}
	}

	public static void main(String[] args) throws Exception {
		sendSMS(new String[] { "18901860169" }, "短信内容123adc！。??11111");
	}

}
