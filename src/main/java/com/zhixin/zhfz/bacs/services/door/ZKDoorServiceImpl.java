package com.zhixin.zhfz.bacs.services.door;

import com.zhixin.zhfz.common.entity.BioResultEntity;
import com.zhixin.zhfz.common.entity.MessageEntity;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ZKDoorServiceImpl implements IZKDoorService {
    private static Logger logger = LoggerFactory.getLogger(ZKDoorServiceImpl.class);

    public static String sendHttpPost(String httpUrl, String obj) {
        System.out.println(httpUrl + " " + obj);
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        HttpEntity entity = null;
        String responseContent = null;
        try {
            HttpPost httpPost = new HttpPost(httpUrl);// 创建httpPost
            httpPost.addHeader("Content-type", "application/json; charset=utf-8");
            httpPost.setHeader("Accept", "application/json");
            StringEntity requestEntity = new StringEntity(obj, "utf-8");
            requestEntity.setContentType("application/json; charset=utf-8");
            requestEntity.setContentEncoding("UTF-8");
            httpPost.setEntity(requestEntity);
            // 创建默认的httpClient实例.
            httpClient = HttpClients.createDefault();
            // 执行请求
            response = httpClient.execute(httpPost);
            entity = response.getEntity();
            responseContent = EntityUtils.toString(entity, "UTF-8");
            logger.info("Executing request " + httpPost.getRequestLine());
        } catch (Exception e) {
            logger.error("==error==", e);
            e.printStackTrace();
            responseContent = null;
            return null;
        } finally {
            try {
                // 关闭连接,释放资源
                if (response != null) {
                    response.close();
                }
                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (IOException e) {
                logger.error("==error2==", e);
                e.printStackTrace();
                return null;
            }
        }
        return responseContent;
    }
    //发卡换卡
    @Override
    public MessageEntity assignCard(String url, String pin, String cardNo) {
        MessageEntity result = new MessageEntity();
        try {
            JSONObject json = new JSONObject();
            json.put("pin", pin);
            json.put("cardNo", cardNo);
            json.put("cardType", "0");
            String msg = sendHttpPost(url, json.toString());
            BioResultEntity rs = (BioResultEntity) JSONObject.toBean(JSONObject.fromObject(msg), BioResultEntity.class);
            if (rs.getCode() >= 0) {
                result.setError(false);
                result.setContent(cardNo.equals("") ? "中控还成功" : "中控发卡成功---Pin:" + pin);
            } else {
                result.setError(true);
                result.setContent(cardNo.equals("") ? "中控还卡失败---pin:" : "中控发卡失败---pin:" + pin + "----" + rs.getMessage());
            }
        } catch (Exception e) {
            logger.error("发卡失败：" + e.getMessage());
            e.printStackTrace();
            result.setError(true);
            result.setContent(e.getMessage());
        }
        logger.info(result.getContent());
        return result;
    }
}