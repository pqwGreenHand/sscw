package com.zhixin.zhfz.sacw.services.consoledesk;


import com.zhixin.zhfz.sacw.dao.consoledesk.IConsoleDeskMapper;
import com.zhixin.zhfz.sacw.entity.ConsoleDeskEntity;
import com.zhixin.zhfz.sacw.entity.OutRecordPhotoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ConsoleDeskServiceImpl implements IConsoleDeskService {

    @Autowired
    IConsoleDeskMapper mapper;


    @Override
    public List<ConsoleDeskEntity> listTotalByCaseNature(Map<String, Object> map) throws Exception {
        return mapper.listCountByCaseNature(map);
    }

    @Override
    public List<ConsoleDeskEntity> listInvolve(Map<String, Object> map) throws Exception {
        return mapper.listInvolve(map);
    }

    @Override
    public List<OutRecordPhotoEntity> getImages(Map<String, Object> map) throws Exception {
        return mapper.getImages(map);
    }

    @Override
    public int countInvolve(Map<String, Object> map) throws Exception {
        return mapper.countInvolve(map);
    }

    @Override
    public List<ConsoleDeskEntity> listWarehouse(Map<String, Object> map) throws Exception {
        return mapper.listWarehouse(map);
    }


}
