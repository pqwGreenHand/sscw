package com.zhixin.zhfz.bacs.services.lawdoc;

import com.zhixin.zhfz.bacs.dao.waitingmanage.IWaitingManageMapper;
import com.zhixin.zhfz.bacs.entity.WaitingRecordEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ISerialLawDocServiceImpl implements ISerialLawDocService {

    @Autowired
    private IWaitingManageMapper waitingManageMapper;

    @Override
    public List<WaitingRecordEntity> queryWaitRecord(Long serialId) {
        return waitingManageMapper.queryWaitRecord(serialId);
    }
}
