package com.zhixin.zhfz.glpt.dao.jcjPatrol;

import com.zhixin.zhfz.zhag.entity.JqxxEntity;

import java.util.List;
import java.util.Map;

public interface JcjPatrolMapper {

    List<JqxxEntity> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    List<JqxxEntity> quyJqxxListById(Map<String, Object> map);

    int count_ById(Map<String, Object> map);

}
