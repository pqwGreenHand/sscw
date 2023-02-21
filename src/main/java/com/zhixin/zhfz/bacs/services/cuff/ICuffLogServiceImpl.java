package com.zhixin.zhfz.bacs.services.cuff;

import com.zhixin.zhfz.bacs.dao.cuff.ICuffLogMapper;
import com.zhixin.zhfz.bacs.entity.CuffLogEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ICuffLogServiceImpl implements ICuffLogService {

    @Autowired
    private ICuffLogMapper cuffLogMapper;

    @Override
    public void insert(CuffLogEntity cuffLogEntity) throws Exception {
        cuffLogMapper.insert(cuffLogEntity);
    }

    @Override
    public void insertLog(int cuffId, int type, String description) {
        CuffLogEntity obj = new CuffLogEntity();
        obj.setCuffId(cuffId);
        obj.setDescription(description);
        obj.setType(type);
        cuffLogMapper.insertLog(obj);
    }
}
