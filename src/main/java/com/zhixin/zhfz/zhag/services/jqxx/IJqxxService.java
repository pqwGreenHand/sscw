package com.zhixin.zhfz.zhag.services.jqxx;

import com.zhixin.zhfz.zhag.entity.JqxxEntity;

import java.util.List;
import java.util.Map;

public interface IJqxxService {

    public List<JqxxEntity> list(Map<String, Object> map);

    public int count(Map<String, Object> map);
}
