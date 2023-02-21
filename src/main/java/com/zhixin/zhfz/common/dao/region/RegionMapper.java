package com.zhixin.zhfz.common.dao.region;

import com.zhixin.zhfz.common.entity.RegionEntity;

import java.util.List;
import java.util.Map;

public interface RegionMapper {
    List<RegionEntity> list(Map<String, Object> map);

    RegionEntity selectByCode(int code) throws Exception;
}
