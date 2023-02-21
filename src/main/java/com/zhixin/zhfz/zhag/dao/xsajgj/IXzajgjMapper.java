package com.zhixin.zhfz.zhag.dao.xsajgj;


import com.zhixin.zhfz.zhag.entity.GjxxEntity;

import java.util.List;
import java.util.Map;

public interface IXzajgjMapper {
    List<GjxxEntity> list(Map<String, Object> param);

    int count(Map<String, Object> map);
}
