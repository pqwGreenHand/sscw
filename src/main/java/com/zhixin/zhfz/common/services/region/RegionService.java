package com.zhixin.zhfz.common.services.region;

import com.zhixin.zhfz.common.entity.RegionEntity;

import java.util.List;
import java.util.Map;

public interface RegionService {
    /**
     * 1省 2城市 3区县 4街道镇;
     *
     * @param map
     */
    List<RegionEntity> list(Map<String, Object> map);

    RegionEntity selectByCode(int code) throws Exception;
}
