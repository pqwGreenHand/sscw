package com.zhixin.zhfz.zhag.services.xsajyj;


import com.zhixin.zhfz.zhag.entity.GjxxEntity;

import java.util.List;
import java.util.Map;

/**
 * @prgram: zhfz
 * @Description: 刑事案件告警实现服务类
 * @Auther: xcf
 * @Date: 2019-05-22 09:34
 */
public interface IXsajyjService {

    List<GjxxEntity> listXsanyj(Map<String, Object> param);

    int count(Map<String, Object> map);

    List<GjxxEntity> listXzanyj(Map<String, Object> map);

    int xzanCount(Map<String, Object> map);

    List<GjxxEntity> hisList(Map<String, Object> map);

    int hisCount(Map<String, Object> map);

    List<GjxxEntity> hisListXsanyj(Map<String, Object> map);

    int hisCountXsaj(Map<String, Object> map);
}
