package com.zhixin.zhfz.bacs.services.wakeup;

import com.zhixin.zhfz.bacs.dao.wakeup.IWakeUpMapper;
import com.zhixin.zhfz.bacs.entity.WakeUpEntity;
import com.zhixin.zhfz.bacs.services.serial.ISerialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @program: zhfz
 * @description: 醒酒登记
 * @author: jzw
 * @create: 2019-02-26 10:51
 **/
@Service
public class WakeUpServiceImpl implements IWakeUpService {

    @Autowired
    private IWakeUpMapper mapper;

//    @Autowired
//    private ISerialService serialService;

    @Override
    public List<WakeUpEntity> list(Map<String, Object> map) throws Exception {
        return mapper.list(map);
    }

    @Override
    public int count(Map<String, Object> map) throws Exception {
        return mapper.count(map);
    }

    @Override
    public void delete(Long id) throws Exception {
        mapper.delete(id);
    }

    @Override
    public void insert(WakeUpEntity entity) throws Exception {
        mapper.insert(entity);
        //serialService.updateStatusById(entity.getSerialId(),12);
    }

    @Override
    public void update(WakeUpEntity entity) throws Exception {
        mapper.update(entity);
//        if(entity.getOutUserId() != null){
//            serialService.updateStatusById(entity.getSerialId(),13);
//        }
    }
}
