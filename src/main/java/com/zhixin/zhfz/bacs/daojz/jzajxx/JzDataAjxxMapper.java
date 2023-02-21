package com.zhixin.zhfz.bacs.daojz.jzajxx;

import java.util.List;
import java.util.Map;

public interface JzDataAjxxMapper {
    
    List<Map<String, Object>> queryJzDataAjxx(Map<String, Object> map);

    int queryJzDataAjxxCount(Map<String, Object> map);
}
