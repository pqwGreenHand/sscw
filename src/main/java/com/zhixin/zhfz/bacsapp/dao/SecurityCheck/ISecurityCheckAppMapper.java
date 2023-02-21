package com.zhixin.zhfz.bacsapp.dao.SecurityCheck;

import com.zhixin.zhfz.bacs.entity.SecurityPhotoEntity;
import com.zhixin.zhfz.bacsapp.entity.SecurityAppEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: zhfz
 * @description: 安全检查dao
 * @author: xcf
 * @create: 2019-04-01 15:36
 **/

public interface ISecurityCheckAppMapper {

    List<SecurityAppEntity> list(Map<String, Object> param);

    int count(Map<String, Object> param);

    void insert(SecurityAppEntity entity);

    void insertSecurityMarkes(SecurityPhotoEntity securityPhotoEntity);

    void updateSecurityUpdateTime(SecurityAppEntity securityAppEntity);

    List<SecurityAppEntity> getSecurityPersonList(Map<String, Object> map);

    SecurityAppEntity selectPersonBySerialId(Map<String, Object> map);
}
