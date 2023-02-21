package com.zhixin.zhfz.bacsapp.services.belong;

import com.zhixin.zhfz.bacs.entity.*;
import com.zhixin.zhfz.bacsapp.dao.belong.IBelongAppMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class BelongAppServiceImpl implements IBelongAppService {

    private static Logger logger = LoggerFactory.getLogger(BelongAppServiceImpl.class);

    @Autowired
    private IBelongAppMapper belongMapper;

    /**
     * 查询嫌疑人存取物信息
     *
     * @param map
     * @return
     */
    @Override
    public List<BelongEntity> selectBelongByAreaId(Map<String, Object> map) {
        return belongMapper.selectBelongByAreaId(map);
    }

    /**
     * 查询嫌疑人涉案存取物信息
     *
     * @param map
     * @return
     */
    @Override
    public List<BelongEntity> selectExhibitByAreaId(Map<String, Object> map) {
        return belongMapper.selectExhibitByAreaId(map);
    }
}
