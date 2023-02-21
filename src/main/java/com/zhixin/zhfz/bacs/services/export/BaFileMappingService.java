package com.zhixin.zhfz.bacs.services.export;

import com.zhixin.zhfz.bacs.entity.BaFileMappingEntity;

import java.util.List;
import java.util.Map;

public interface BaFileMappingService {

    List<BaFileMappingEntity> queryList(Map<String,Object> queryMap);
}
