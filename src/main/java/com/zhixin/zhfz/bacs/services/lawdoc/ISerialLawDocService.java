package com.zhixin.zhfz.bacs.services.lawdoc;

import com.zhixin.zhfz.bacs.entity.WaitingRecordEntity;

import java.util.List;

public interface ISerialLawDocService {

    List<WaitingRecordEntity> queryWaitRecord(Long serialId);
}
