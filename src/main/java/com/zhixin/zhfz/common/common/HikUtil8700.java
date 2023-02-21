package com.zhixin.zhfz.common.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hikvision.cms.api.common.util.HttpClientSSLUtils;
import com.zhixin.zhfz.common.entity.HKDoorEntity;
import com.zhixin.zhfz.common.entity.HikNewEntity;
import com.zhixin.zhfz.common.entity.MessageEntity;
import com.zhixin.zhfz.common.utils.Md5Util;
import com.zhixin.zhfz.common.utils.PropertyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.*;

public class HikUtil8700 {
    private static final Logger logger = LoggerFactory.getLogger(HikUtil8700.class);

    private static String appkey;//调用方唯一标识

    private static String secret;//密钥，需要问海康现场工程师索要

    private static String opUserUuid;

    private static String httpBaseUrl;//基本的URL http://127.0.0.1:80

    static {
        httpBaseUrl = PropertyUtil.getContextProperty("hik.base.url").toString();
        appkey = PropertyUtil.getContextProperty("hik.appkey").toString();
        secret = PropertyUtil.getContextProperty("hik.secret").toString();
    }

    /**
     * HTTP方式
     * 获取默认用户UUID 测试
     *
     * @return
     * @throws Exception
     */
    public static MessageEntity getDefaultUserUUID() {
        MessageEntity exeResult = new MessageEntity();
        try {
            String mothed = "/openapi/service/base/user/getDefaultUserUuid";
            String url = httpBaseUrl + mothed;//"/openapi/service/base/user/getDefaultUserUuid";
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("appkey", appkey);//设置APPKEY
            map.put("time", System.currentTimeMillis());//设置时间参数
            String params = JSON.toJSONString(map);
            logger.info(" ====== getDefaultUserUUID 请求参数：【" + params + "】");
            String token = Md5Util.getMD5(mothed + params + secret).toUpperCase();
            String data = HttpClientSSLUtils.doPost(url + "?token=" + token, params);
            logger.info(" ====== getDefaultUserUUID 请求返回结果：【{" + data + "}】");
            if (data != null && data != "") {
                JSONObject jsonResult = JSONObject.parseObject(data);
                String uuid = jsonResult.getString("data");
                opUserUuid = uuid;
                exeResult.setContent(uuid);
                exeResult.setError(false);
            }
        } catch (Exception ex) {
            exeResult.setError(true);
            exeResult.setContent(ex.getMessage());
            logger.info("getDefaultUserUUID exception=" + ex.getMessage());
        }

        return exeResult;
    }


    /**
     * HTTP方式
     * 获取门禁的开关状态
     *
     * @return
     * @throws Exception
     */
    public static Map<String, Integer> getDoorStatusByUuids() {
        Map<String, Integer> mapResult = new HashMap<>();
        try {
            String url = httpBaseUrl + "/openapi/service/acs/status/getDoorStatusByUuids";

            String doorUuids = PropertyUtil.getContextProperty("door.status.uuid").toString();
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("appkey", appkey);//设置APPKEY
            map.put("time", System.currentTimeMillis());//设置时间参数
            map.put("opUserUuid", getDefaultUserUUID());//获得opUserUuid
            map.put("doorUuids", doorUuids);//获得doorUuids
            String params = JSON.toJSONString(map);
            logger.info(" ====== getDoorStatusByUuids 请求参数：【" + params + "】");
            String token = Md5Util.getMD5("/openapi/service/acs/status/getDoorStatusByUuids" + params + secret).toUpperCase();
            String data = HttpClientSSLUtils.doPost(url + "?token=" + token, params);
            logger.info(" ====== getDoorStatusByUuids 请求返回结果：【{" + data + "}】");
            if (data != null && data != "") {
                JSONObject jsonResult = JSONObject.parseObject(data);
                JSONArray jsonArray = jsonResult.getJSONArray("data");
                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject entity = jsonArray.getJSONObject(i);
                    if (entity.getInteger("doorStatus") == 2) {//2表示关闭状态，1表示开门状态
                        mapResult.put(entity.getString("doorUuid"), 0);
                    } else {
                        mapResult.put(entity.getString("doorUuid"), 1);
                    }

                }

            }
        } catch (Exception ex) {
            logger.info("getDoorStatusByUuids exception=" + ex.getMessage());
        }

        return mapResult;
    }


    /**
     * HTTP方式
     * 获取门禁开启超时告警
     *
     * @return
     * @throws Exception
     */
    public static List<HKDoorEntity> getDoorEventsHistory() {
        List<HKDoorEntity> hkDoorEntities = new ArrayList<>();
        try {

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date now = new Date();
            Date now_10 = new Date(now.getTime() - 10000 * 2); //十秒钟


            String url = httpBaseUrl + "/openapi/service/acs/event/getDoorEventsHistory";

            String doorUuids = PropertyUtil.getContextProperty("door.timeout.uuid").toString();
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("appkey", appkey);//设置APPKEY
            map.put("time", System.currentTimeMillis());//设置时间参数
            map.put("opUserUuid", getDefaultUserUUID());//获得opUserUuid
            map.put("pageNo", 50);
            map.put("pageSize", 50);
            map.put("eventType", "198400");//门禁开启超时告警
            map.put("startTime", sdf.format(now_10));
            map.put("endTime", sdf.format(now));
            String params = JSON.toJSONString(map);
            logger.info(" ====== getDoorEventsHistory 请求参数：【" + params + "】");
            String token = Md5Util.getMD5("/openapi/service/acs/event/getDoorEventsHistory" + params + secret).toUpperCase();
            String data = HttpClientSSLUtils.doPost(url + "?token=" + token, params);
            logger.info(" ====== getDoorEventsHistory 请求返回结果：【{" + data + "}】");

            if (data != null && data != "") {
                JSONObject jsonResult = JSONObject.parseObject(data);
                JSONObject jsonObject = jsonResult.getJSONObject("data");//获取data数组
                JSONArray jsonArray = jsonObject.getJSONArray("list");
                for (int i = 0; i < jsonArray.size(); i++) {
                    HKDoorEntity hkDoorEntity = new HKDoorEntity();
                    JSONObject entity = jsonArray.getJSONObject(i);
                    hkDoorEntity.setDoorId(entity.getString("doorUuid"));
                    hkDoorEntity.setAlarmTime(sdf.format(new Date(entity.getLong("eventTime"))));
                    hkDoorEntities.add(hkDoorEntity);
                }

            }
        } catch (Exception ex) {
            logger.info("getDoorEventsHistory exception=" + ex.getMessage());
        }

        return hkDoorEntities;
    }


    /**
     * HTTP方式
     * 通过卡片编号获取对应的人员ID
     *
     * @return
     * @throws Exception
     */
    public static String getPersonInfosByCardNos(String cardNo) throws Exception {
        String personId = "";
        try {
            String url = httpBaseUrl + "/openapi/service/base/card/getPersonInfosByCardNos";
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("appkey", appkey);//设置APPKEY
            map.put("time", System.currentTimeMillis());//设置时间参数
            map.put("opUserUuid", getDefaultUserUUID());//获得opUserUuid
            map.put("cardNos", cardNo);//获得卡片编号
            String params = JSON.toJSONString(map);
            logger.info(" ====== getPersonInfosByCardNos 请求参数：【" + params + "】");
            String token = Md5Util.getMD5("/openapi/service/base/card/getPersonInfosByCardNos" + params + secret).toUpperCase();
            String data = HttpClientSSLUtils.doPost(url + "?token=" + token, params);
            logger.info(" ====== getPersonInfosByCardNos 请求返回结果：【{" + data + "}】");
            if (data != null && data != "") {
                JSONObject jsonResult = JSONObject.parseObject(data);
                JSONArray jsonArray = jsonResult.getJSONArray("data");
                JSONObject entity = jsonArray.getJSONObject(0);//取得唯一一个人的ID
                personId = entity.getString("personId");
            }
        } catch (Exception ex) {
            logger.info("getPersonInfosByCardNos exception=" + ex.getMessage());
        }
        return personId;
    }


    /**
     * HTTP方式
     * 下发民警卡片对应的门禁权限,0-成功；1-失败；3-未处理
     *
     * @return
     * @throws Exception
     */
    public static int downloadAuthorityByPersonIds(String cardNo, boolean flag) throws Exception {
        int cardResult = 0;//0-成功；1-失败；3-未处理
        try {
            String url = httpBaseUrl + "/openapi/service/acs/auth/downloadAuthorityByPersonIds";
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("appkey", appkey);//设置APPKEY
            map.put("time", System.currentTimeMillis());//设置时间参数
            map.put("opUserUuid", getDefaultUserUUID());//获得opUserUuid
            map.put("personIds", getPersonInfosByCardNos(cardNo));//获得卡片绑定的人员ID
            map.put("doorUuids", PropertyUtil.getContextProperty("outpolice.door.uuid"));//获得哪些门禁的权限UUID
            map.put("planUuid", PropertyUtil.getContextProperty("door.plan.uuid"));//计划模版UUID
            if (flag) {
                map.put("operateType", 1);//下载门禁权限
            } else {
                map.put("operateType", 3);//删除门禁权限
            }
            map.put("includeFinger", 0);//1-需要下载；0或者其他：不需要下载
            String params = JSON.toJSONString(map);
            logger.info(" ====== getPersonInfosByCardNos 请求参数：【" + params + "】");
            String token = Md5Util.getMD5("/openapi/service/base/netZone/getNetZones" + params + secret).toUpperCase();
            String data = HttpClientSSLUtils.doPost(url + "?token=" + token, params);
            logger.info(" ====== getPersonInfosByCardNos 请求返回结果：【{" + data + "}】");

            if (data != null && data != "") {
                JSONObject jsonResult = JSONObject.parseObject(data);
                JSONObject jsonObject = jsonResult.getJSONObject("data");
                cardResult = jsonObject.getIntValue("cardResult");
            }
        } catch (Exception ex) {
            logger.info("getPersonInfosByCardNos exception=" + ex.getMessage());
        }
        return cardResult;
    }

    /**
     * HTTP方式
     * 获取所有网域
     *
     * @return
     * @throws Exception
     */
    public static MessageEntity getNetZones(String userUuid) {
        MessageEntity exeResult = new MessageEntity();
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("appkey", appkey);//设置APPKEY
            map.put("time", System.currentTimeMillis());//设置时间参数
            if (userUuid == null || userUuid.equals("") || userUuid == "") {
                exeResult = getDefaultUserUUID();
                if (exeResult.isError()) {
                    return exeResult;
                }
                map.put("opUserUuid", exeResult.getContent());

            } else {
                map.put("opUserUuid", userUuid);//获得opUserUuid
            }
            String params = JSON.toJSONString(map);
            String url = httpBaseUrl + "/openapi/service/base/netZone/getNetZones";
            logger.info(" ====== getNetZones 请求参数：【" + params + "】");
            String token = Md5Util.getMD5("/openapi/service/base/netZone/getNetZones" + params + secret).toUpperCase();
            String data = HttpClientSSLUtils.doPost(url + "?token=" + token, params);
            logger.info(" ====== getNetZones 请求返回结果：【{" + data + "}】");
            if (data != null && data != "") {
                JSONObject jsonResult = JSONObject.parseObject(data);
                JSONArray jsonArray = jsonResult.getJSONArray("data");
                JSONObject entity = jsonArray.getJSONObject(0);//取得唯一个的netZoneUuid
                exeResult.setContent(entity.getString("netZoneUuid"));
                exeResult.setError(false);
            }
        } catch (Exception ex) {
            exeResult.setError(true);
            exeResult.setContent(ex.getMessage());
            logger.info("getNetZones exception=" + ex.getMessage());
        }
        return exeResult;
    }


    /**
     * HTTP方式
     * 根据监控点UUID集和网域UUID分页获取录像计划
     *
     * @return
     * @throws Exception
     */
    public static MessageEntity getRecordPlansByCameraUuids(String cameraNo, String userUuid, String zoneUuid) {
        MessageEntity exeResult = new MessageEntity();
        exeResult.setError(true);
        try {
            String mothedString = "/openapi/service/vss/playback/getRecordPlansByCameraUuids";
            String url = httpBaseUrl + mothedString;
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("appkey", appkey);//设置APPKEY
            map.put("time", System.currentTimeMillis());//设置时间参数
            map.put("pageNo", 1);//设置分页参数
            map.put("pageSize", 100);//设置分页参数
            String uuid = userUuid;
            if (userUuid == null || userUuid.equals("") || userUuid == "") {
                exeResult = getDefaultUserUUID();
                if (exeResult.isError()) {
                    return exeResult;
                }
                uuid = exeResult.getContent();
                map.put("opUserUuid", uuid);//获得opUserUuid
            } else {
                map.put("opUserUuid", uuid);//获得opUserUuid
            }
            map.put("cameraUuids", cameraNo);//获得cameraUuid
            if (zoneUuid == null || zoneUuid.equals("") || zoneUuid == "") {
                exeResult = getNetZones(uuid);
                if (exeResult.isError()) {
                    return exeResult;
                }
                map.put("netZoneUuid", exeResult.getContent());//获得netZoneUuid
            } else {
                map.put("netZoneUuid", zoneUuid);//获得netZoneUuid
            }
            String params = JSON.toJSONString(map);
            logger.info(" ====== getRecordPlansByCameraUuids 请求参数：【" + params + "】");
            String token = Md5Util.getMD5("/openapi/service/vss/playback/getRecordPlansByCameraUuids" + params + secret).toUpperCase();
            String data = HttpClientSSLUtils.doPost(url + "?token=" + token, params);
            logger.info(" ====== getRecordPlansByCameraUuids 请求返回结果：【{" + data + "}】");
            if (data != null && data != "") {
                JSONObject jsonResult = JSONObject.parseObject(data);
                JSONObject jsonObject = jsonResult.getJSONObject("data");//获取data数组
                JSONArray jsonArray = jsonObject.getJSONArray("list");
                HikNewEntity hikNewEntity = new HikNewEntity();
                if (jsonArray.size() > 0) {
                    for (int i = 0; i < jsonArray.size(); i++) {
                        JSONObject entity = jsonArray.getJSONObject(i);
                        String enable = entity.getString("enabled");
                        if (enable != null && enable.equals("0")) {
                            hikNewEntity.setRecordPlanUuid(entity.getString("recordPlanUuid"));
                            hikNewEntity.setPlanType(entity.getIntValue("planType"));
                            hikNewEntity.setPlanName(entity.getString("planName"));
                            hikNewEntity.setCameraUuid(entity.getString("cameraUuid"));
                            exeResult.addCallbackData(hikNewEntity);
                            exeResult.setError(false);
                            return exeResult;

                        }
                    }

                }
                exeResult.setError(true);
                exeResult.setContent("没有查询到有效录像");
            }
        } catch (Exception ex) {
            exeResult.setError(true);
            exeResult.setContent(ex.getMessage());
            logger.info("getRecordPlansByCameraUuids exception=" + ex.getMessage());
        }
        return exeResult;
    }

    ///挂失
    public static MessageEntity cardLoss(String cardNo) {
        MessageEntity exeResult = new MessageEntity();
        exeResult.setError(true);
        try {
            String mothedString = "/openapi/service/base/card/reportTheLoss";
            String url = httpBaseUrl + mothedString;
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("appkey", appkey);//设置APPKEY
            map.put("time", System.currentTimeMillis());//设置时间参数

            if (opUserUuid == null || opUserUuid.equals("") || opUserUuid == "") {
                exeResult = getDefaultUserUUID();
                if (exeResult.isError()) {
                    return exeResult;
                }
                opUserUuid = exeResult.getContent();
                map.put("opUserUuid", opUserUuid);//获得opUserUuid
            } else {
                map.put("opUserUuid", opUserUuid);//获得opUserUuid
            }
            map.put("cardNo ", cardNo);//获得opUserUuid
            String params = JSON.toJSONString(map);
            logger.info(" ====== cardLoss 请求参数：【" + params + "】");
            String token = Md5Util.getMD5(mothedString + params + secret).toUpperCase();
            String data = HttpClientSSLUtils.doPost(url + "?token=" + token, params);
            logger.info(" ====== cardLoss 请求返回结果：【{" + data + "}】");
            if (data != null && data != "") {
                JSONObject jsonResult = JSONObject.parseObject(data);
                String ec = jsonResult.getString("errorCode");
                if (ec.equals("0")) {
                    exeResult.setError(false);
                    exeResult.setContent(jsonResult.getString("errorMessage"));
                } else {
                    exeResult.setError(true);
                    exeResult.setContent(jsonResult.getString("errorMessage"));
                }
            }
        } catch (Exception ex) {
            exeResult.setError(true);
            exeResult.setContent(ex.getMessage());
            logger.info("cardLoss exception=" + ex.getMessage());
        }
        return exeResult;
    }
    public static MessageEntity cardRelease(String cardNo) {
        MessageEntity exeResult = new MessageEntity();
        exeResult.setError(true);
        try {
            String mothedString = "/openapi/service/base/card/releaseTheLoss";
            String url = httpBaseUrl + mothedString;
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("appkey", appkey);//设置APPKEY
            map.put("time", System.currentTimeMillis());//设置时间参数

            if (opUserUuid == null || opUserUuid.equals("") || opUserUuid == "") {
                exeResult = getDefaultUserUUID();
                if (exeResult.isError()) {
                    return exeResult;
                }
                opUserUuid = exeResult.getContent();
                map.put("opUserUuid", opUserUuid);//获得opUserUuid
            } else {
                map.put("opUserUuid", opUserUuid);//获得opUserUuid
            }
            map.put("cardNo ", cardNo);//获得opUserUuid
            String params = JSON.toJSONString(map);
            logger.info(" ====== cardRelease 请求参数：【" + params + "】");
            String token = Md5Util.getMD5(mothedString + params + secret).toUpperCase();
            String data = HttpClientSSLUtils.doPost(url + "?token=" + token, params);
            logger.info(" ====== cardRelease 请求返回结果：【{" + data + "}】");
            if (data != null && data != "") {
                JSONObject jsonResult = JSONObject.parseObject(data);
                String ec = jsonResult.getString("errorCode");
                if (ec.equals("0")) {
                    exeResult.setError(false);
                    exeResult.setContent(jsonResult.getString("errorMessage"));
                } else {
                    exeResult.setError(true);
                    exeResult.setContent(jsonResult.getString("errorMessage"));
                }
            }
        } catch (Exception ex) {
            exeResult.setError(true);
            exeResult.setContent(ex.getMessage());
            logger.info("cardRelease exception=" + ex.getMessage());
        }
        return exeResult;
    }

    public static MessageEntity getPlayBackXml(String cameraNo) {
        MessageEntity exeResult = new MessageEntity();
        String playbackXml = "";
        try {

            Map<String, Object> map = new HashMap<String, Object>();
            exeResult = getDefaultUserUUID();
            if (exeResult.isError()) {
                return exeResult;
            }
            String userUUid = exeResult.getContent();
            if (userUUid == null || userUUid == "") {
                logger.info(" ====== getPlayBackXml 获取User uuid 为空 ");
            }
            map.put("opUserUuid", userUUid);//获得opUserUuid
            exeResult = getNetZones(userUUid);
            if (exeResult.isError()) {
                return exeResult;
            }
            String zoneUuid = exeResult.getContent();
            if (zoneUuid == null || zoneUuid == "") {
                logger.info(" ====== getPlayBackXml获取 zoneUuid  为空 ");
            } else {
                map.put("netZoneUuid", zoneUuid);//获得netZoneUuid
            }
            exeResult = getRecordPlansByCameraUuids(cameraNo, userUUid, zoneUuid);
            if (!exeResult.isError()) {
                HikNewEntity hikNewEntity = (HikNewEntity) exeResult.getCallbackData();
                if (hikNewEntity != null) {
                    map.put("appkey", appkey);//设置APPKEY
                    map.put("time", System.currentTimeMillis());//设置时间参数
                    map.put("recordPlanUuid", hikNewEntity.getRecordPlanUuid());
                    map.put("planType", hikNewEntity.getPlanType());
                    String params = JSON.toJSONString(map);
                    logger.info(" ====== getPlayBackXml 请求参数：【" + params + "】");
                    String uri = "/openapi/service/vss/playback/getPlaybackParamByPlanUuid";
                    String url = httpBaseUrl + uri;
                    String token = Md5Util.getMD5(uri + params + secret).toUpperCase();
                    String data = HttpClientSSLUtils.doPost(url + "?token=" + token, params);
                    logger.info(" ====== getPlaybackParamByPlanUuid 请求返回结果：【{" + data + "}】");
                    if (data != null && data != "") {
                        JSONObject jsonResult = JSONObject.parseObject(data);
                        playbackXml = jsonResult.getString("data");//获取data数组
                        if (playbackXml == null || playbackXml.equals("")) {
                            exeResult.setError(true);
                            exeResult.setContent("返回回放xml为空");
                        } else {
                            exeResult.setContent(playbackXml);
                        }
                    }
                } else {
                    exeResult.setError(true);
                    exeResult.setContent("返回回放xml实体为空");
                }
            }

        } catch (Exception ex) {
            exeResult.setError(true);
            exeResult.setContent("getPlayBackXml" + ex.getMessage());
            logger.info("getPlayBackXml exception=" + ex.getMessage());
        }

        return exeResult;
    }

    /**
     * HTTP方式
     * 获取预览视频的XML参数 测试
     *
     * @return
     * @throws Exception
     */
    public static MessageEntity getPlayPreviewXml(String cameraNo) {
        MessageEntity exeResult = new MessageEntity();
        String previewXml = "";
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            exeResult = getDefaultUserUUID();
            if (exeResult.isError()) {

                return exeResult;
            }
            String userUUid = exeResult.getContent();
            map.put("opUserUuid", userUUid);//获得opUserUuid
            if (userUUid == null || userUUid == "") {
                logger.info(" ====== getPlayPreviewXml 获取USER UUID 为空");
            }
            exeResult = getNetZones(userUUid);
            if (exeResult.isError()) {
                return exeResult;
            }
            map.put("netZoneUuid", exeResult.getContent());//获得netZoneUuid
            map.put("appkey", appkey);//设置APPKEY
            map.put("time", System.currentTimeMillis());//设置时间参数
            map.put("cameraUuid", cameraNo);
            String params = JSON.toJSONString(map);
            String url = httpBaseUrl + "/openapi/service/vss/preview/getPreviewParamByCameraUuid";
            logger.info(" ====== getPlayPreviewXml 请求参数：【" + params + "】");
            String token = Md5Util.getMD5("/openapi/service/vss/preview/getPreviewParamByCameraUuid" + params + secret).toUpperCase();
            String data = HttpClientSSLUtils.doPost(url + "?token=" + token, params);
            logger.info(" ====== getPlayPreviewXml 请求返回结果：【{" + data + "}】");
            if (data != null && data != "") {
                JSONObject jsonResult = JSONObject.parseObject(data);
                JSONObject jsonObject = jsonResult.getJSONObject("data");//获取data数组
                previewXml = jsonObject.getString("previewXml");
                exeResult.setError(false);
                exeResult.setContent(previewXml);
            }

        } catch (Exception ex) {
            exeResult.setError(true);
            exeResult.setContent(ex.getMessage());
            logger.info("getPlayPreviewXml exception=" + ex.getMessage());
        }

        return exeResult;
    }


    public static void main(String[] args) {
        try {
            String data = "{    \"errorCode\": 0, \n" +
                    "    \"errorMessage\": null, \n" +
                    "    \"data\": {        \"previewXml\": \"<?xml version=\\\"1.0\\\" encoding=\\\"UTF-8\\\"?><previewInfo><camera sysCode=\\\"166f9546df9643e993f9c32a5dd84c0c\\\" supportFishEye=\\\"0\\\" url=\\\"rtsp://172.10.67.220:554/pag://192.168.1.250:7302:166f9546df9643e993f9c32a5dd84c0c:0:MAIN:TCP?cnid=4&pnid=5\\\" name=\\\"10.20.139.7_Camera_1\\\" installPosition=\\\"0\\\" id=\\\"333\\\" extraSupport=\\\"0\\\" encoderUserName=\\\"DYVQXYk=\\\" encoderPwd=\\\"DYVQXYk=\\\" encoderModel=\\\"143659264\\\" decodeTag=\\\"hikvision\\\" cascade=\\\"0\\\" cameraChannelNum=\\\"1\\\"/><presetlist/><cruiselist/><server password=\\\"12345\\\" username=\\\"admin\\\" port=\\\"7302\\\" ip=\\\"172.10.67.218\\\" id=\\\"1\\\"/><right ptzcfg=\\\"1\\\" ptzcontrol=\\\"1\\\" videoParamcfg=\\\"1\\\" record=\\\"1\\\"/><user locktime=\\\"30\\\" priority=\\\"100\\\" id=\\\"4\\\"/></previewInfo>\"\n" +
                    "    }\n" +
                    "}";

            if (data != null && data != "") {
                JSONObject jsonResult = JSONObject.parseObject(data);
                JSONObject jsonObject = jsonResult.getJSONObject("data");//获取data数组
                data = jsonObject.getString("previewXml");
                System.out.println(data);
            }
        } catch (Exception ex) {
            logger.info("test exception=" + ex.getMessage());
        }

    }
}
