package com.zhixin.zhfz.bacs.dao.jzAjxx;

import com.zhixin.zhfz.bacs.entity.JzAjxxEntity;

import java.util.List;
import java.util.Map;

public interface IJzAjxxMapper {

    public List<JzAjxxEntity> list(Map<String, Object> map);

    public int count(Map<String, Object> map);

    List<Map<String, Object>> listRyByAjbh(Map<String, Object> map);

    int countListRyByAjbh(Map<String, Object> map);
    
    List<Map<String, Object>> queryRybhByZjhm(Map<String, Object> map);
}
