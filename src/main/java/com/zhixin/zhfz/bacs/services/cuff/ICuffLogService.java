package com.zhixin.zhfz.bacs.services.cuff;

import com.zhixin.zhfz.bacs.entity.CuffLogEntity;

public interface ICuffLogService {

    void insert(CuffLogEntity cuffLogEntity) throws Exception;

    void insertLog(int cuffId, int type, String description);
}
