package com.zhixin.zhfz.zhag.dao.xsajyj;


import com.zhixin.zhfz.zhag.entity.GjxxEntity;

import java.util.List;
import java.util.Map;

public interface IXzajyjMapper {
    List<GjxxEntity> list(Map<String, Object> param);

    int count(Map<String, Object> map);

    List<GjxxEntity> hisList(Map<String, Object> map);

    int hisCount(Map<String, Object> map);
}
