package com.zhixin.zhfz.common.services.region;

import com.zhixin.zhfz.common.dao.region.RegionMapper;
import com.zhixin.zhfz.common.entity.RegionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class RegionServiceImpl implements RegionService {

    @Autowired
    private RegionMapper mapper;


    /**
     * level 1省 2城市 3区县 4街道镇;
     *
     * @param map
     */
    @Override
    public List<RegionEntity> list(Map<String, Object> map) {
        return mapper.list(map);
    }

    @Override
    public RegionEntity selectByCode(int code) throws Exception {
        return mapper.selectByCode(code);
    }
}