package com.zhixin.zhfz.common.common;

import com.zhixin.zhfz.common.utils.ControllerTool;
import com.zhixin.zhfz.common.utils.PropertyUtil;

import javax.servlet.http.HttpServletRequest;

public class StartCsHelper {

    //"zhfz://g7kUtZMzDQHscyphzeJjpccg4/CwQPPrKFE+ErgNMx/2CJJpd9xZNJ49lb+1JNL+";
    //格式 zhfz://警号/web服务头/系统类型/记录id等等 ，协议后面由aes加密
    //笔录  zhfz://policeNo#http://127.0.0.1:8080/zhfz#0#1
    //辨认 zhfz://policeNo#http://127.0.0.1:8080/zhfz#1#1
    //视频播放  zhfz://policeNo#http://127.0.0.1:8080/zhfz#2
    //刻录 zhfz://policeNo#http://127.0.0.1:8080/zhfz#3
    //虹膜 zhfz://policeNo#http://127.0.0.1:8080/zhfz#4
    //单独登录 zhfz://policeNo#http://127.0.0.1:8080/zhfz#100
    public static String GetStartCsUrl(HttpServletRequest request, int sysType, String recordId) throws Exception {
        String url = "";
        try {
            String path = request.getContextPath();
            String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
            url = ControllerTool.getUser(request).getLoginName() + "#" + basePath + "#" + sysType;
            if (recordId != null && !recordId.equals("")) {
                url += "#" + recordId;
            }
            Object propertyV = PropertyUtil.getContextProperty("webServiceAesKey");
            if (propertyV != null) {
                url = "zhfz://" + AESUtil.aesEncrypt(url, propertyV.toString());
            }
        } catch (Exception e) {

        }

        return url;
    }
}
