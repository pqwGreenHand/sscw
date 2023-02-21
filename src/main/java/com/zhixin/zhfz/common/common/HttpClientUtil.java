package com.zhixin.zhfz.common.common;

import com.zhixin.zhfz.common.utils.PropertyUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.*;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.params.BasicHttpParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

public class HttpClientUtil {

    private static final Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);

    public static HttpResponse head(String url, Map<String, String> headerdatas) throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpHead httphead = new HttpHead(url);
        for (String key : headerdatas.keySet()) {
            httphead.addHeader(key, headerdatas.get(key));
        }
        return httpclient.execute(httphead);
    }

    public static HttpResponse head(String url, String XAuthToken) throws Exception {
        Map<String, String> headerdatas = new HashMap<String, String>();
        headerdatas.put("X-Auth-Token", XAuthToken);
        return head(url, headerdatas);
    }

    public static HttpResponse get(String url, Map<String, String> headerdatas, Map<String, String> params)
            throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        if (params.size() > 0) {
            url += "?";
            int flag = 0;
            for (String key : params.keySet()) {
                logger.debug("params key:  " + key);
                logger.debug("params value:  " + params.get(key));
                if (flag != 0) {
                    url += "&";
                }
                String encode = URLEncoder.encode(params.get(key), "utf-8");
                url += (key + "=" + encode);
                logger.debug("params encode value:  " + encode);
                logger.debug("params decode value:  " + URLDecoder.decode(encode, "utf-8"));
                flag++;
            }
        }
        HttpGet httpget = new HttpGet(url);
        for (String key : headerdatas.keySet()) {
            httpget.addHeader(key, headerdatas.get(key));
        }
        httpget.addHeader("Content-Type", "text/html; charset=utf-8");
        logger.info("send  URI:   " + httpget.getURI());
        // logger.info("send URI: "+httpget.getURI().toURL());
        logger.info("send  URI:   " + httpget.getURI().getRawPath());
        // logger.info("send PARAM: "+httpget.getParams());
        // logger.info("send PARAM:
        // "+httpget.getParams().getParameter("param"));
        return httpclient.execute(httpget);
    }

    public static HttpResponse get(String url, String XAuthToken) throws Exception {
        Map<String, String> headerdatas = new HashMap<String, String>();
        headerdatas.put("X-Auth-Token", XAuthToken);
        return get(url, headerdatas, new HashMap<String, String>());
    }

    public static HttpResponse get(String url) throws IOException {
        HttpClient client = new DefaultHttpClient();
        HttpGet httpget = new HttpGet(url);
        return client.execute(httpget);
    }

    /**
     * 访问get路径，获取返回值
     *
     * @param url
     * @return
     * @throws Exception
     */
    public static JSONObject get(String url, Map<String, String> params) throws Exception {
        Map<String, String> headerdatas = new HashMap<String, String>();
        HttpResponse response = get(url, headerdatas, params);
        return response != null ? JSONObject.fromObject(getContentInfoFromHTTPResponse(response)) : null;
    }

    public HttpResponse delete(String url, Map<String, String> headerdatas) throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpDelete httpdelete = new HttpDelete(url);
        for (String key : headerdatas.keySet()) {
            httpdelete.addHeader(key, headerdatas.get(key));
        }
        return httpclient.execute(httpdelete);
    }

    public HttpResponse delete(String url, String XAuthToken) throws Exception {
        Map<String, String> headerdatas = new HashMap<String, String>();
        headerdatas.put("X-Auth-Token", XAuthToken);
        return this.delete(url, headerdatas);
    }

    public static HttpResponse put(String url, Map<String, String> headerdatas, String json) throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPut httpput = new HttpPut(url);
        for (String key : headerdatas.keySet()) {
            httpput.addHeader(key, headerdatas.get(key));
        }
        if (json != null) {
            HttpEntity entity = new StringEntity(json);
            httpput.setEntity(entity);
        }
        return httpclient.execute(httpput);
    }

    public static HttpResponse putFile(String url, Map<String, String> headerdatas, File file) throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPut httpput = new HttpPut(url);
        for (String key : headerdatas.keySet()) {
            httpput.addHeader(key, headerdatas.get(key));
        }
        if (file != null) {
            HttpEntity entity = new FileEntity(file, "");
            httpput.setEntity(entity);
        }
        return httpclient.execute(httpput);
    }

    public static HttpResponse putFile(String url, Map<String, String> headerdatas, MultipartFile file) throws Exception {
        return putFile(url, headerdatas, FileUtil.multipartFileToFile(file));
    }


    public static HttpResponse put(String url, String XAuthToken, String json) throws Exception {
        Map<String, String> headerdatas = new HashMap<String, String>();
        headerdatas.put("X-Auth-Token", XAuthToken);
        return put(url, headerdatas, json);
    }

    public static HttpResponse post(String url, Map<String, String> headerdatas, String json) throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost(url);
        for (String key : headerdatas.keySet()) {
            httppost.addHeader(key, headerdatas.get(key));
        }
        if (json != null) {
            StringEntity entity = new StringEntity(json, "utf-8");// 解决中文乱码问题
            entity.setContentEncoding("UTF-8");
            entity.setContentType("application/json");
            httppost.setEntity(entity);
        }
//        httppost.addHeader("Content-Type", "text/html; charset=utf-8");
        return httpclient.execute(httppost);
    }

    public static HttpClient wrapClient() {
        try {
            SSLContext ctx = SSLContext.getInstance("TLS");
            X509TrustManager tm = new X509TrustManager() {
                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                @Override
                public void checkClientTrusted(X509Certificate[] arg0,
                                               String arg1) throws CertificateException {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] arg0,
                                               String arg1) throws CertificateException {
                }
            };
            ctx.init(null, new TrustManager[] { tm }, null);
            SSLConnectionSocketFactory ssf = new SSLConnectionSocketFactory(
                    ctx, NoopHostnameVerifier.INSTANCE);
            CloseableHttpClient httpclient = HttpClients.custom()
                    .setSSLSocketFactory(ssf).build();
            return httpclient;
        } catch (Exception e) {
            return HttpClients.createDefault();
        }
    }

    public static HttpResponse postHttps(String url, Map<String, String> headerdatas, String json) throws Exception {
        CloseableHttpClient httpclient = (CloseableHttpClient) wrapClient();
        HttpPost httppost = new HttpPost(url);
        for (String key : headerdatas.keySet()) {
            httppost.addHeader(key, headerdatas.get(key));
        }
        if (json != null) {
            StringEntity entity = new StringEntity(json, "utf-8");// 解决中文乱码问题
            entity.setContentEncoding("UTF-8");
            entity.setContentType("application/json");
            httppost.setEntity(entity);
        }
//        httppost.addHeader("Content-Type", "text/html; charset=utf-8");
        return httpclient.execute(httppost);
    }


    public static HttpResponse postWithXML(String url, String xml) throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost(url);

        StringEntity entity = new StringEntity(xml, "utf-8");

        httppost.setEntity(entity);

        return httpclient.execute(httppost);
    }


    public static HttpResponse postFile(String url, File file) throws Exception {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(200000).setSocketTimeout(200000000).build();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(requestConfig);
        MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
        multipartEntityBuilder.addBinaryBody("file", file);
        multipartEntityBuilder.addTextBody("comment", "this is comment");
        HttpEntity httpEntity = multipartEntityBuilder.build();
        httpPost.setEntity(httpEntity);
        return httpClient.execute(httpPost);
    }

    public static HttpResponse postFile(String url, MultipartFile file) throws Exception {
        return postFile(url, FileUtil.multipartFileToFile(file));
    }

    public static HttpResponse post(String url, String XAuthToken, String json) throws Exception {
        Map<String, String> headerdatas = new HashMap<String, String>();
        headerdatas.put("X-Auth-Token", XAuthToken);
        return post(url, headerdatas, json);
    }

    public static HttpResponse postHttps(String url, String XAuthToken, String json) throws Exception {
        Map<String, String> headerdatas = new HashMap<String, String>();
        headerdatas.put("X-Auth-Token", XAuthToken);
        return postHttps(url, headerdatas, json);
    }

    public static Header[] getHeadersFromResponse(HttpResponse response) throws Exception {
        return response.getAllHeaders();
    }

    public static String getContentStringFromResponse(HttpResponse response) throws Exception {
        HttpEntity entity = response.getEntity();
        StringBuilder content = new StringBuilder();
        if (entity != null) {
            try {
                InputStream instream = entity.getContent();
                BufferedReader in = new BufferedReader(new InputStreamReader(instream));
                String str = null;
                while ((str = in.readLine()) != null) {
                    content.append(str);
                } // end while
                in.close();
            } catch (Exception e) {
                logger.error("", e);
            }
        }
        return content.toString();
    }

    public static String getValueFromHeadersByKey(String key, Header[] hds) {
        if (hds == null) {
            return null;
        }
        for (Header hd : hds) {
            if (key.equals(hd.getName())) {
                return hd.getValue();
            }
        }
        return null;
    }

    // ---------------------------------------for
    // test-------------------------------------

    /**
     * just for test.
     */
    public static void printHttpMethodResponse(String method, String url, String XAuthToken) throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        // HttpGet httpget = new
        // HttpGet("http://172.31.20.84:8774/v1.1/images/detail");
        // httpget.addHeader("X-Auth-Token",
        // "0077b118bd705d4828a2a49e53726708265f43b7");

        // keystone http://172.31.20.87:5000/v2.0/tenants
        // nova http://172.31.20.87:8774/v1.0/servers
        // nova http://172.31.20.87:8774/v1.1/servers
        // glance http://172.31.20.87:9292/v1/images
        // swift http://172.31.20.87:8080/v1/AUTH_1/
        HttpRequestBase mt = null;
        if ("get".equals(method)) {
            mt = new HttpGet(url);
        } else if ("post".equals(method)) {
            mt = new HttpPost(url);
        } else if ("put".equals(method)) {
            mt = new HttpPut(url);
        } else if ("delete".equals(method)) {
            mt = new HttpDelete(url);
        } else if ("head".equals(method)) {
            mt = new HttpHead(url);
        } else {
            throw new Exception("bad http method[" + method + "]!!!");
        }

        mt.addHeader("X-Auth-Token", XAuthToken);

        HttpResponse response = httpclient.execute(mt);

        System.out.println(getAllInfoFromHTTPResponse(response));
    }

    public static String getAllInfoFromHTTPResponse(HttpResponse response) throws Exception {
        Header[] headers = response.getAllHeaders();
        HttpEntity entity = response.getEntity();
        StringBuilder sb = new StringBuilder();
        sb.append("---------------header--------------\n");
        for (Header h : headers) {
            sb.append(h.getName()).append(": ").append(h.getValue()).append("\n");
        }
        sb.append("---------------content--------------\n");
        if (entity != null) {
            try {
                InputStream instream = entity.getContent();
                BufferedReader in = new BufferedReader(new InputStreamReader(instream));
                String str = null;
                while ((str = in.readLine()) != null) {
                    sb.append(str);
                } // while
                in.close();
            } catch (Exception e) {
                logger.error("", e);
            }
        }
        return sb.toString();
    }

    public static String getContentInfoFromHTTPResponse(HttpResponse response) throws Exception {
        HttpEntity entity = response.getEntity();
        StringBuilder sb = new StringBuilder();
        if (entity != null) {
            try {
                InputStream instream = entity.getContent();
                BufferedReader in = new BufferedReader(new InputStreamReader(instream, "UTF-8"));
                String str = null;
                while ((str = in.readLine()) != null) {
                    sb.append(str);
                } // while
                in.close();
            } catch (Exception e) {
                logger.error("", e);
            }
        }
        logger.info("http response:  " + sb.toString());
        return sb.toString();
    }

    // 人脸图像倒入
    public static String faceImgInput(String url, String json) {
        // String url =
        // "http://192.168.201.59:9100/face/v1/framework/face_image/repository/picture";
        String headString = "Content-type: application/json ";
        HttpResponse response = null;
        try {
            response = post(url, headString, json);
            System.out.println(json);
            return response != null ? getContentInfoFromHTTPResponse(response) : "";
        } catch (Exception e) {
            logger.error("", e);
        }
        return "";
    }

    // 检索
    public static String queryFaceByNameJson(String url, String json) {
        // String url =
        // "http://192.168.201.59:9200/face/v1/framework/face/query";
        String headString = "Content-type: application/json ";
        HttpResponse response = null;
        try {
            System.out.println(json);
            response = post(url, headString, json);
            return response != null ? getContentInfoFromHTTPResponse(response) : "";
        } catch (Exception e) {
            logger.error("", e);
        }
        return "";
    }

    // 查询Http
    public static String query(String url) {
        HttpResponse response = null;
        try {
            response = get(url, "");
            return response != null ? getContentInfoFromHTTPResponse(response) : "";
        } catch (Exception e) {
            logger.error("", e);
        }
        return "";
    }

    // 人脸图像识别1比N
    public static String faceImgOneToN(String url, String json) {
        // String url =
        // "http://192.168.201.59:9200/face/v1/framework/face/retrieval";
        String headString = "Content-type: application/json ";
        HttpResponse response = null;
        try {
            System.out.println(json);
            response = post(url, headString, json);
            return response != null ? getContentInfoFromHTTPResponse(response) : "";
        } catch (Exception e) {
            logger.error("", e);
        }
        return "";
    }

    // 人脸图像识别1比N
    public static String faceImgOneToNNew(String json) {
        String sessionId = yituLogin();
        HttpResponse response = null;
        try {
            Map<String, String> headMap = new HashMap<>();
            headMap.put("session_id", sessionId);
            //获取导图路径地址

//			String headString = "Content-type: application/json ";
//			response = post(url, headString, json.toString());
            String url  = getYiTuUrl("yitu.sync.url");
            response = post(url, headMap, json);
            response.setHeader("x-access-id", "access_id");// 请求来
            response.setHeader("x-signature", md5(json));// 客户验证签名
            String syncStr = response != null ? getContentInfoFromHTTPResponse(response) : "";
            logger.info("获取导图结果："+syncStr);
            JSONObject syncJson = JSONObject.fromObject(syncStr);
            if("OK".equals(syncJson.get("message"))){
                JSONArray results = syncJson.getJSONArray("results");
                if (results!=null && results.size()>0){
                    String faceImageId = JSONObject.fromObject(results.get(0)).get("face_image_id")+"";
                    //获取1vN路径地址
                    url = getYiTuUrl("yitu.1vN.url");
                    JSONObject paramJson = new JSONObject();
                    JSONObject retrievalJson = new JSONObject();
                    retrievalJson.put("face_image_id", faceImageId);
                    retrievalJson.put("repository_ids", PropertyUtil.getContextProperty("yitu.repository.ids").toString().split(","));
                    retrievalJson.put("threshold", 60);
                    paramJson.put("retrieval", retrievalJson.toString());
                    paramJson.put("condition", new JSONObject());
                    JSONObject orderJson = new JSONObject();
                    orderJson.put("similarity", -1);
                    paramJson.put("order", orderJson);
                    paramJson.put("start", 0);
                    paramJson.put("limit", 3);
                    response = post(url, headMap, paramJson.toString());
                    return response != null ? getContentInfoFromHTTPResponse(response) : "";
                }else{
                    logger.info("导图失败...."+results.toString());
                }
            }else{
                logger.info("导图失败....rtn["+syncJson.get("rtn")+"]，message["+syncJson.get("message"));
            }
        } catch (Exception e) {
            logger.error("", e);
        }
        return "";
    }

    // 人脸图像识别1比1
    public static String faceImgOneToOne(String url, String json) {
        // String url = "http://192.168.201.59:9200/face/v1/framework/face/1v1";
        // String url
        // ="http://www.yitu-test.com/face/v1/algorithm/recognition/face_pair_verification";
        // String url = "http://172.16.5.14:9200/face/v1/framework/face/1v1";
        String headString = "Content-type: application/json ";
        HttpResponse response = null;
        try {
            System.out.println(json);
            response = post(url, headString, json);
            response.setHeader("x-access-id", "access_id");// 请求来
            response.setHeader("x-signature", md5(json));// 客户验证签名

            return response != null ? getContentInfoFromHTTPResponse(response) : "";
        } catch (Exception e) {
            logger.error("", e);
        }
        return "";
    }

    // 人脸图像识别1比1
    public static String faceImgOneToOne(String json) {
        String sessionId = yituLogin();
        HttpResponse response = null;
        try {
            Map<String, String> headMap = new HashMap<>();
            headMap.put("session_id", sessionId);
            //获取1v1路径地址
            String url  = getYiTuUrl("yitu.1v1.url");
            response = post(url, headMap, json);
            response.setHeader("x-access-id", "access_id");// 请求来
            response.setHeader("x-signature", md5(json));// 客户验证签名

            return response != null ? getContentInfoFromHTTPResponse(response) : "";
        } catch (Exception e) {
            logger.error("", e);
        }
        return "";
    }

    public static String yituLogin(){
        String url = getYiTuUrl("yitu.login.url");
        JSONObject json = new JSONObject();
        json.put("name", PropertyUtil.getContextProperty("yitu.username"));
        json.put("password", PropertyUtil.getContextProperty("yitu.password"));
        HttpResponse response = null;
        try {
            String headString = "Content-type: application/json ";
            response = post(url, headString, json.toString());
            String result = response != null ? getContentInfoFromHTTPResponse(response) : "";
            return JSONObject.fromObject(result).get("session_id")+"";
        } catch (Exception e) {
            logger.error("", e);
        }
        return "";
    }

    private static String getYiTuUrl(String confName){
        String ip = PropertyUtil.getContextProperty("yitu.ip")+"";
        String port = PropertyUtil.getContextProperty("yitu.port")+"";
        String prefix = PropertyUtil.getContextProperty("yitu.prefix")+"";
        String url = PropertyUtil.getContextProperty(confName)+"";
        String returnUrl = "http://"+ip+":"+port+prefix+url;
        logger.info("获取到的依图路径为："+returnUrl);
        return returnUrl;
    }

    // MD5加密
    private static String md5(String plainText) {
        plainText = (plainText == null ? "" : plainText) + "456";
        String algorithm = "MD5";
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            md.update(plainText.getBytes());
            byte b[] = md.digest();

            int i;

            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            // System.out.println("result: " + buf.toString());// 32位的加密
            return buf.toString().toUpperCase();
            // System.out.println("result: " + buf.toString().substring(8,
            // 24));// 16位的加密
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "error";
        }
    }

    // 警宗平台信息
    public static String zjInfo(String url, String json) {
        // String url =
        // "http://192.168.201.59:9100/face/v1/framework/face_image/repository/picture";
        String headString = "Content-type: application/json ";
        HttpResponse response = null;
        try {
            response = post(url, headString, json, true);
            System.out.println(json);
            return response != null ? getContentInfoFromHTTPResponse(response) : "";
        } catch (Exception e) {
            logger.error("", e);
        }
        return "";
    }

    public static HttpResponse post(String url, String XAuthToken, String json, boolean headerTypeIsJson) throws Exception {
        Map<String, String> headerdatas = new HashMap<String, String>();
        headerdatas.put("X-Auth-Token", XAuthToken);
        return post(url, headerdatas, json, headerTypeIsJson);
    }

    public static HttpResponse post(String url, Map<String, String> headerdatas, String json, boolean headerTypeIsJson) throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost(url);
        for (String key : headerdatas.keySet()) {
            httppost.addHeader(key, headerdatas.get(key));
        }
        if (json != null) {
            StringEntity entity = new StringEntity(json, "utf-8");// 解决中文乱码问题
            entity.setContentEncoding("UTF-8");
            entity.setContentType("application/json");
            httppost.setEntity(entity);
        }
        httppost.addHeader("Content-Type", "application/json");

        return httpclient.execute(httppost);
    }

    public static void main(String[] args) {
        // JSONObject jsonObject = JSONObject.fromObject("{CLS_VO_Result :
        // {Ret:0,Content:[]}}");
        // System.out.println(((JSONObject)jsonObject.get("CLS_VO_Result")).get("Ret"));

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("test", "aaa");
        // String sjon = jsonObject.toString().replace("\"", "'");
        String sjon = jsonObject.toString();
        String url = "http://127.0.0.1:8082";
        //
        try {
            // HttpClientUtil.get(url);
            Map<String, String> headerdatas = new HashMap<String, String>();
            CloseableHttpClient httpclient = HttpClients.createDefault();

            HttpGet httpget = new HttpGet(url);
            for (String key : headerdatas.keySet()) {
                httpget.addHeader(key, headerdatas.get(key));
            }
            org.apache.http.params.HttpParams params = new BasicHttpParams();
            params.setParameter("param", sjon);
            httpget.setParams(params);

            System.out.println("uri:  " + httpget.getURI());

            HttpResponse response = httpclient.execute(httpget);

            System.err.println(response);
            JSONObject js = (response != null ? JSONObject.fromObject(getAllInfoFromHTTPResponse(response)) : null);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static JSONObject synchronousUser(Map<String, String> params) throws Exception {
//		http://xxx/openApi/v2/user/synchronousOrg?clientId=CLIENT_ID
        String url = "http://139.224.241.35:9001/openApi/v2/user/synchronousUser";
        JSONObject result = HttpClientUtil.get(url, params);
        logger.info("-------请求结果---" + result);
        params.clear();
        return result;
    }

    // 人口库
    public static String renKouKu(String url, String json) {
        // String url =
        // "http://10.11.229.141:8080/wsquery-web/wsquery";
        String headString = "Content-type: application/json ";
        HttpResponse response = null;
        try {
            System.out.println(json);
            response = get(url, "");
            return response != null ? getContentInfoFromHTTPResponse(response) : "";
        } catch (Exception e) {
            logger.error("", e);
        }
        return "";
    }
}
