package com.zhixin.zhfz.bacs.services.belongtemp;

import com.zhixin.zhfz.bacs.dao.belong.IBelongMapper;
import com.zhixin.zhfz.bacs.dao.belong.IBelongdetMapper;
import com.zhixin.zhfz.bacs.dao.belong.IOpenCabinetMapper;
import com.zhixin.zhfz.bacs.dao.belongtemp.IBelongDetailTempMapper;
import com.zhixin.zhfz.bacs.dao.serial.ISerialMapper;
import com.zhixin.zhfz.bacs.entity.*;
import com.zhixin.zhfz.bacs.services.person.IPersonLockersService;
import com.zhixin.zhfz.bacs.services.serial.ISerialService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class BelongDetailTempServiceImpl implements IBelongDetailTempService {
    @Resource
    private IBelongDetailTempMapper belongDetailTempMapper;
    @Override
    public void saveBelongTemp(BelongDetailTempEntity belongDetailTempEntity) {
        belongDetailTempMapper.saveBelongTemp(belongDetailTempEntity);
    }

    @Override
    public List<BelongDetailTempEntity> getListByTempId(Map<String, Object> map) {
        return  belongDetailTempMapper.getListByTempId(map);
    }

    @Override
    public List<BelongDetailTempEntity> getByWpUuid(Map<String, Object> map) {
        return belongDetailTempMapper.getByWpUuid(map);
    }

    @Override
    public List<BelongDetailTempEntity> getListByBelongDetail(Integer areaId, String str) {
        return null;
    }

    @Override
    public List<BelongDetailTempEntity> list(Map<String, Object> map) {
        return  belongDetailTempMapper.list(map);
    }

    @Override
    public int count(Map<String, Object> map) {
        return  belongDetailTempMapper.count(map);
    }
}
