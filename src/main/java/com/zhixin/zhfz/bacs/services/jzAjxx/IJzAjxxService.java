package com.zhixin.zhfz.bacs.services.jzAjxx;


import com.zhixin.zhfz.bacs.entity.JzAjxxEntity;

import java.util.List;
import java.util.Map;

public interface IJzAjxxService {

    public List<JzAjxxEntity> list(Map<String, Object> map);

    public int count(Map<String, Object> map);

    List<Map<String, Object>> listRyByAjbh(Map<String, Object> map);

    int countListRyByAjbh(Map<String, Object> map);
    
    /**
     * 通过证件号码获取人员编号
     * @param map
     * @return
     */
    List<Map<String, Object>> queryRybhByZjhm(Map<String, Object> map);
}
