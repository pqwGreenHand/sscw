package com.zhixin.zhfz.bacs.services.receptionentrance;

import com.zhixin.zhfz.bacs.dao.receptionentrance.IReceptionEntranceMapper;
import com.zhixin.zhfz.bacs.entity.ReceptionEntranceEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ReceptionEntranceServiceImpl implements IReceptionEntranceService {

    @Autowired
    private IReceptionEntranceMapper receptionEntranceMapper;

    @Override
    public List<ReceptionEntranceEntity> list2(Map<String, Object> map) {
        return receptionEntranceMapper.list2(map);
    }

    @Override
    public int count(Map<String, Object> map) {
        return receptionEntranceMapper.count(map);
    }

    @Override
    public void receptionInsert(ReceptionEntranceEntity data) {
        receptionEntranceMapper.receptionInsert(data);
    }

    @Override
    public List<ReceptionEntranceEntity> list(Map<String, Object> params) {
        return receptionEntranceMapper.list(params);
    }

    @Override
    public void updateOutTime(String id) {
        receptionEntranceMapper.updateOutTime(id);
    }

    @Override
    public List<ReceptionEntranceEntity> receptionHistoryList(Map<String, Object> map) {
        return receptionEntranceMapper.receptionHistoryList(map);
    }

    @Override
    public int count2(Map<String, Object> map) {
        return receptionEntranceMapper.count2(map);
    }

    @Override
    public List<ReceptionEntranceEntity> findById(String id) {
        return receptionEntranceMapper.findById(id);
    }

    @Override
    public void updateById(ReceptionEntranceEntity data) {
        receptionEntranceMapper.updateById(data);
    }
}
