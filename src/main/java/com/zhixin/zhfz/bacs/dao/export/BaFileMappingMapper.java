package com.zhixin.zhfz.bacs.dao.export;

import com.zhixin.zhfz.bacs.entity.BaFileMappingEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface BaFileMappingMapper {

    List<BaFileMappingEntity> queryList(Map<String,Object> queryMap);
}
