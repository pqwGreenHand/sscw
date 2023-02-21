package com.zhixin.zhfz.common.common;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.MediaType;

/**
 * Created by wolf on 2016/4/21.
 */
public class LedOldUtil {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(LedOldUtil.class);
    private final static  String url ="http://127.0.0.1:8084/interrogate-hardware/restful/ws/";

    private static ClientConfig createClientConfig() {
        ClientConfig clientConfig = new DefaultClientConfig();
        // for json support
        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
        // for json support
        clientConfig.getClasses().add(JacksonJsonProvider.class);
        return clientConfig;
    }

    public static void controlLED(String url, Object entity) {
        Client c = Client.create(createClientConfig());
        WebResource r = c.resource(url);
        logger.info("========led set result==========="+r.path("controlled").path("DHSAJ-KLJCX-DHSKJ").accept(new String[] { MediaType.APPLICATION_JSON })
                .entity(entity, MediaType.APPLICATION_JSON).post(String.class));
    }
    
    
    public static void controlLED2010(Object entity) {
        Client c = Client.create(createClientConfig());
        logger.info("调用ledurl"+url);
        WebResource r = c.resource(url);
        logger.info("========led set result==========="+r.path("controlled2010").path("DHSAJ-KLJCX-DHSKJ").accept(new String[] { MediaType.APPLICATION_JSON })
                .entity(entity, MediaType.APPLICATION_JSON).post(String.class));
        logger.info("调用led成功"+url);
    }

    /**
     * 获取字符串的长度，如果有中文，则每个中文字符计为2位
     * @param value 指定的字符串
     * @return 字符串的长度
     */
    public static int stringLength(String value) {
        int valueLength = 0;
        String chinese = "[\u0391-\uFFE5]";
        /* 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1 */
        for (int i = 0; i < value.length(); i++) {
            /* 获取一个字符 */
            String temp = value.substring(i, i + 1);
            /* 判断是否为中文字符 */
            if (temp.matches(chinese)) {
                /* 中文字符长度为2 */
                valueLength += 2;
            } else {
                /* 其他字符长度为1 */
                valueLength += 1;
            }
        }
        return valueLength;
    }
}
