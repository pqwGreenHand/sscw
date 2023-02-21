package com.zhixin.zhfz.bacs.daojz.jzperson;

import com.zhixin.zhfz.bacs.entity.JzPersonEntity;

import java.util.List;
import java.util.Map;

public interface JzDataPersonMapper {

    List<Map<String, Object>> queryJzDataPerson(Map<String, Object> params);

    List<JzPersonEntity> selectJzInfoByzjhm(String zjhm);

    Map<String, Object> jzFillByRybhAndAJbh(Map<String, Object> map);
}
