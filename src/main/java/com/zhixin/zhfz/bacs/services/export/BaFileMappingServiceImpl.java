package com.zhixin.zhfz.bacs.services.export;

import com.zhixin.zhfz.bacs.dao.export.BaFileMappingMapper;
import com.zhixin.zhfz.bacs.dao.export.IExprotMapper;
import com.zhixin.zhfz.bacs.entity.BaFileMappingEntity;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class BaFileMappingServiceImpl implements BaFileMappingService {

    private static Logger logger = Logger.getLogger(BaFileMappingServiceImpl.class);

    @Autowired
    private BaFileMappingMapper baFileMappingMapper;

    @Override
    public List<BaFileMappingEntity> queryList(Map<String,Object> queryMap) {
        return baFileMappingMapper.queryList(queryMap);
    }
}
