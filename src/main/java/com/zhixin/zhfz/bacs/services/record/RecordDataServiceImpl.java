package com.zhixin.zhfz.bacs.services.record;

import com.zhixin.zhfz.bacs.dao.record.RecordDataMapper;
import com.zhixin.zhfz.bacs.entity.LedEntity;
import com.zhixin.zhfz.bacs.entity.RecordEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
@Service
public class RecordDataServiceImpl implements  RecordDataService{
    @Autowired
    private RecordDataMapper recordMapper;
    @Override
    public void updateRecordStatus(Map<String, Object> map) throws Exception {
        recordMapper.updateRecordStatus(map);
    }
    @Override
    public void updateRoom(Map<String, Object> map) throws Exception {
        recordMapper.updateRoom(map);
    }
    @Override
    public List<RecordEntity> getRecordInfo(Map<String, Object> map) throws Exception {
        return recordMapper.getRecordInfo(map);
    }
    @Override
    public List<LedEntity> getLedInfo(Map<String, Object> map) throws Exception {
        return recordMapper.getLedInfo(map);
    }
}
