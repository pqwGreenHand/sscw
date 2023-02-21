package com.zhixin.zhfz.bacs.services.jzPerson;


import com.zhixin.zhfz.bacs.entity.RycljdMysqlEntity;

import java.util.List;
import java.util.Map;

public interface IJzRyxxService {

    List<RycljdMysqlEntity> queryCljdPzsjMaxXs();

    void insertRYCLJD(RycljdMysqlEntity entity);

    List<Map<String, Object>> queryCljdMap();

    List<Map<String, Object>> queryAjztMap();

    List<Map<String, Object>> queryOrgMap();

    List<Map<String, Object>> queryUserMap();

}
