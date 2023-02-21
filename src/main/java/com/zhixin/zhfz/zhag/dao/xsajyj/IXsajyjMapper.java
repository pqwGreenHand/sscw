package com.zhixin.zhfz.zhag.dao.xsajyj;


import com.zhixin.zhfz.zhag.entity.GjxxEntity;

import java.util.List;
import java.util.Map;

public interface IXsajyjMapper {
    List<GjxxEntity> list(Map<String, Object> param);

    int count(Map<String, Object> map);

    List<GjxxEntity> hisListXsanyj(Map<String, Object> map);

    int hisCountXsaj(Map<String, Object> map);
}
