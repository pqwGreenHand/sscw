package com.zhixin.zhfz.common.services.fileServiceConfig;

import com.zhixin.zhfz.common.common.HttpClientUtil;
import com.zhixin.zhfz.common.dao.fileServiceConfig.IFileServiceConfigMapper;
import com.zhixin.zhfz.common.entity.FileServiceConfigEntity;
import com.zhixin.zhfz.common.form.FileDownloadForm;
import com.zhixin.zhfz.common.form.FileUploadForm;
import com.zhixin.zhfz.common.utils.StaticUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @program: zhfz
 * @description: 醒酒登记
 * @author: jzw
 * @create: 2019-02-26 10:51
 **/
@Service
public class FileConfigServiceImpl implements IFileConfigService {

    private static final Logger logger = LoggerFactory.getLogger(FileConfigServiceImpl.class);

    @Autowired
    private IFileServiceConfigMapper mapper;




    @Override
    public List<FileServiceConfigEntity> list(Map<String, Object> map) throws Exception {
        return mapper.list(map);
    }

    @Override
    public int count(Map<String, Object> map) throws Exception {
        return mapper.count(map);
    }

    @Override
    public void delete(Long id) throws Exception {
        Map<String,Object> map = new HashMap<>();
        map.put("id",id);
        FileServiceConfigEntity entity = mapper.get(map);
        if(entity.getIsEnable().equals(1))
            StaticUtil.setFileServiceConfigEntity(entity,false);
        mapper.delete(id);
    }

    @Override
    public void insert(FileServiceConfigEntity entity) throws Exception {
        Map<String,Object> map = new HashMap<>();
        map.put("sysType",entity.getSysType());
        map.put("sysId",entity.getSysId());
        map.put("isEnable",1);
        map.put("valid",0);
        mapper.updateEnable(map);
        mapper.insert(entity);
    }

    @Override
    public void update(FileServiceConfigEntity entity) throws Exception {
        mapper.update(entity);
        Map<String,Object> map = new HashMap<>();
        map.put("id",entity.getId());
        entity = mapper.get(map);
        if(entity.getIsEnable().equals(1))
            StaticUtil.setFileServiceConfigEntity(entity);
    }

    @Override
    public void updateEnable(Map<String, Object> map) throws Exception {
        FileServiceConfigEntity entity = mapper.get(map);
        if(map.get("valid").equals(1)){
            StaticUtil.setFileServiceConfigEntity(entity);
            Map<String,Object> params = new HashMap<>();
            params.put("sysType",entity.getSysType());
            params.put("sysId",entity.getSysId());
            params.put("isEnable",1);
            params.put("valid",0);
            mapper.updateEnable(params);
        }else{
            StaticUtil.setFileServiceConfigEntity(entity,false);
        }
        mapper.updateEnable(map);
    }

    @Override
    public String download(FileDownloadForm form) {
        String url = getFileServiceConfigEntity(form).getServiceUrl();
        logger.info(url);
        return url;
    }

    @Override
    public void upload(FileUploadForm form) throws Exception {
        String url = getFileServiceConfigEntity(form).getServiceUrl();
        logger.info("url: " +url);
        if(form.getFileName() == null){
            form.setFileName(form.getUuid()
                    + form.getSuffix());
        }else if(form.getFileName().indexOf('.') < 0){//判断是否有后缀
            form.setFileName(form.getFileName() + form.getSuffix());
        }
        url += "/UploadFile/" + form.getUuid() + "/" + form.getFileType() + "/" + form.getFileName();
        logger.info("url: " +url);
        HttpResponse response = HttpClientUtil.postFile(url,form.getFile());
        String result = HttpClientUtil.getContentInfoFromHTTPResponse(response);
        logger.info("result:",result);
        if(result.indexOf("false") > -1){
            throw new Exception();
        }
    }

    private FileServiceConfigEntity getFileServiceConfigEntity(FileDownloadForm form){
//        FileServiceConfigEntity entity = StaticUtil.getFileServiceConfigEntity(form.getSysType(),form.getSysId());
//        if(entity == null){
//             Map<String,Object> params = new HashMap<>();
//             params.put("sysType",form.getSysType());
//             if("aj".equals(form.getSysType())){
//                 params.put("sysId",0);
//             }else{
//                 params.put("sysId",form.getSysId());
//             }
//             params.put("isEnable",1);
//             entity = mapper.get(params);
//             StaticUtil.setFileServiceConfigEntity(entity);
//        }
        Map<String,Object> params = new HashMap<>();
        params.put("sysType",form.getSysType());
        if("aj".equals(form.getSysType())){
            params.put("sysId",0);
        }else{
            params.put("sysId",form.getSysId());
        }
        params.put("isEnable",1);
        FileServiceConfigEntity entity = mapper.get(params);
        return entity;
    }

    @Override
    public String getFileBase64(FileDownloadForm form) throws Exception {
        String url = getFileServiceConfigEntity(form).getServiceUrl();
        url += "/GetFileBase64/" + form.getUuid() + "/" + form.getFileType() + "/" + form.getFileName();
        Map<String,String> head = new HashMap<String,String>();
        head.put("Content-Type","application/json;charset=UTF-8");
        head.put("Transfer-Encoding","chunke");
        HttpPost httppost = new HttpPost(url);
        httppost.addHeader("Content-Type", "application/json;charset=UTF-8");
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpResponse response = httpclient.execute(httppost);
        String result = HttpClientUtil.getContentInfoFromHTTPResponse(response);
        JSONObject jsonObject = new JSONObject(result);
        if(jsonObject.getBoolean("status")){
            logger.info("result:",result);
            return jsonObject.getString("jsonString");
        } else{
            logger.info(jsonObject.getString("message"));
            throw new Exception();
        }
    }
}
