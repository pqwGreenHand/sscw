package com.zhixin.zhfz.zhag.dao.jqxx;

import com.zhixin.zhfz.zhag.entity.JqxxEntity;

import java.util.List;
import java.util.Map;

public interface IJqxxMapper {

    public List<JqxxEntity> list(Map<String, Object> map);

    public int count(Map<String, Object> map);

}
