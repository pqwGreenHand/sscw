package com.zhixin.zhfz.bacs.services.rpc;

import com.zhixin.zhfz.bacs.dao.rpc.IRecognizeFlagConfigMapper;
import com.zhixin.zhfz.bacs.dao.rpc.IRecognizePersonConfigMapper;
import com.zhixin.zhfz.bacs.dao.rpc.IRecognizePhotoConfigMapper;
import com.zhixin.zhfz.bacs.entity.RecognizeFlagConfigEntity;
import com.zhixin.zhfz.bacs.entity.RecognizePersonConfigEntity;
import com.zhixin.zhfz.bacs.entity.RecognizePhotoConfigEntity;
import com.zhixin.zhfz.common.common.HttpClientUtil;
import org.apache.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @program: zhfz
 * @description: 人员图片管理
 * @author: jzw
 * @create: 2019-02-26 10:51
 **/
@Service
public class RecognizeConfigServiceImpl implements IRecognizeConfigService {

    private static final Logger logger = LoggerFactory.getLogger(RecognizeConfigServiceImpl.class);

    @Autowired
    private IRecognizePersonConfigMapper personMapper;

    @Autowired
    private IRecognizePhotoConfigMapper photoMapper;

    @Autowired
    private IRecognizeFlagConfigMapper flagMapper;

    static final Pattern pattern = Pattern
            .compile("^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$");


    @Override
    public List<RecognizePersonConfigEntity> personList(Map<String, Object> map) throws Exception {
        return personMapper.list(map);
    }

    @Override
    public int personCount(Map<String, Object> map) throws Exception {
        return personMapper.count(map);
    }

    @Override
    public void personDelete(Long id) throws Exception {
        personMapper.delete(id);
    }

    @Override
    public void personInsert(RecognizePersonConfigEntity entity) throws Exception {
        personMapper.insert(entity);
    }

    @Override
    public void insert(MultipartFile file,String url) throws Exception {
        String uuid = UUID.randomUUID().toString();
        RecognizePersonConfigEntity person = this.getPersonEntity(file.getOriginalFilename(),uuid);
        Map<String,Object> map = new HashMap<>();
        map.put("certificateNo",person.getCertificateNo());
        RecognizePersonConfigEntity entity = personMapper.get(map);
        if(entity == null){
            RecognizePhotoConfigEntity photo = this.getPhotoEntity(file.getOriginalFilename(),uuid,url);
            photoMapper.insert(photo);
            personMapper.insert(person);
            HttpResponse response = HttpClientUtil.postFile(photo.getUrl(),file);
            String result = HttpClientUtil.getContentInfoFromHTTPResponse(response);
            logger.info("result:",result);
            if(result.indexOf("false") > -1 ){
                throw new Exception();
            }
        }
    }

    @Override
    public void personUpdate(RecognizePersonConfigEntity entity) throws Exception {
        personMapper.update(entity);
    }

    @Override
    public List<RecognizeFlagConfigEntity> selectFlagAll(Map<String, Object> params) {
        return flagMapper.all(params);
    }

    private RecognizePhotoConfigEntity getPhotoEntity(String fileName,String uuid,String url) throws Exception{
        String uuidName = uuid + fileName.substring(fileName.lastIndexOf("."));
        RecognizePhotoConfigEntity entity = new RecognizePhotoConfigEntity();
        entity.setDescription(fileName);
        entity.setName(uuidName);
        entity.setUuid(uuid);
        entity.setUrl(url+ "/" + uuidName);
        return entity;
    }

    private RecognizePersonConfigEntity getPersonEntity(String fileName,String uuid) throws ParseException {
        RecognizePersonConfigEntity entity = new RecognizePersonConfigEntity();
        fileName = fileName.substring(0,fileName.lastIndexOf("."));
        String regEx="[^0-9xX]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(fileName);
        String certificateNo = m.replaceAll("").trim();
        String realName = fileName.replace(certificateNo,"");
        realName = realName == "" ? "无名氏" : realName;
        entity.setName(realName);
        entity.setPhotoUuid(uuid);
        this.extIdentityInfo(certificateNo,entity);
        return entity;
    }

    private void extIdentityInfo(String id,RecognizePersonConfigEntity entity) throws ParseException {
        String exceptionMsg = Objects.requireNonNull(id, "身份证号不能为空");
        Integer sex = 9;
        if (Objects.equals(id, "")) {
            throw new IllegalArgumentException(exceptionMsg);
        }
        Matcher matcher = pattern.matcher(id);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("身份证号码不合法");
        }
        if (Integer.parseInt(id.substring(16).substring(0, 1)) % 2 == 0) {
            sex = 2;
        } else {
            sex = 1;
        }
        entity.setSex(sex);
        String birthdayStr = id.substring(6, 14);
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        entity.setBirth(format.parse(birthdayStr));
        int now = Integer.valueOf(format.format(new Date()));
        int age = (now - Integer.valueOf(birthdayStr)) / 10000;
//        LocalDate birthday = LocalDate.from(DateTimeFormatter.ofPattern("yyyyMMdd").parse(birthdayStr));
        entity.setAge(age);
        entity.setCertificateNo(id);
    }



}
