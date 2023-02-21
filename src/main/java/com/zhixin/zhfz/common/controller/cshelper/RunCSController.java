package com.zhixin.zhfz.common.controller.cshelper;

import com.zhixin.zhfz.common.common.AESUtil;
import com.zhixin.zhfz.common.entity.MessageEntity;
import com.zhixin.zhfz.common.entity.SessionInfo;
import com.zhixin.zhfz.common.utils.ControllerTool;
import com.zhixin.zhfz.common.utils.PropertyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
@RequestMapping("/zhfz/common/cshelper")
public class RunCSController {
    private static final Logger logger = LoggerFactory.getLogger(ControllerTool.class);

    /**
     * //"zhfz://g7kUtZMzDQHscyphzeJjpccg4/CwQPPrKFE+ErgNMx/2CJJpd9xZNJ49lb+1JNL+";
     * //格式 zhfz://警号/web服务头/系统类型/记录id等等 ，协议后面由aes加密
     * //笔录  zhfz://policeNo#http://127.0.0.1:8080/zhfz#0#1
     * //辨认 zhfz://policeNo#http://127.0.0.1:8080/zhfz#1#1
     * //视频播放  zhfz://policeNo#http://127.0.0.1:8080/zhfz#2
     * //刻录 zhfz://policeNo#http://127.0.0.1:8080/zhfz#3
     * //虹膜 zhfz://policeNo#http://127.0.0.1:8080/zhfz#4
     * //单独登录 zhfz://policeNo#http://127.0.0.1:8080/zhfz#100
     * //zhfz://*type*id*
     * //type 0=笔录 1=辨认 2=视频播放 3=刻录  4.远程指挥 5=同案侦查 6=远程视证 7=远程提讯 8=虹膜 100=单点登陆
     */

    @RequestMapping("/getCsUrl")
    @ResponseBody
    public MessageEntity getSessionInfo(@RequestParam Map<String, Object> params, HttpServletRequest request, HttpServletResponse response) {
        MessageEntity messageEntity = new MessageEntity();
        try {
            String url = "";
            Object id = params.get("id");//笔录id或辨认id
            Object t = params.get("type");
            if (t != null && !t.toString().equals("")) {
                String path = request.getContextPath();
                String httpHeader = request.getScheme() + "://" + "10.11.229.142" + ":" + request.getServerPort() + path;
                url = ControllerTool.getUser(request).getLoginName() + "#" + httpHeader + "#" + t.toString();
                if (id != null && !id.toString().equals("")) {

                    url += "#" + id.toString();
                }

            } else {

                messageEntity.setError(true);
                messageEntity.setContent("参数不全，无法生成url");
                return messageEntity;
            }

            url = "zhfz://" + AESUtil.aesEncrypt(url, PropertyUtil.getContextProperty("webServiceAesKey").toString()).replace("+", "%2B");
            messageEntity.setError(false);
            messageEntity.setCallbackData(url);
        } catch (Exception e) {
            messageEntity.setError(true);
            messageEntity.setContent("getCsUrl=exception:" + e.getMessage());
        }

        return messageEntity;
    }
}
