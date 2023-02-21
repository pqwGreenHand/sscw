package com.zhixin.zhfz.bacs.services.lawdoc;

import com.zhixin.zhfz.bacs.dao.lawdoc.IDocSendcaseMapper;
import com.zhixin.zhfz.bacs.entity.DocSendcaseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DocSendCaseServiceImpl implements IDocSendCaseService{

    @Autowired
    IDocSendcaseMapper docSendcaseMapper;

    @Override
    public DocSendcaseEntity getDocSendcaseById(Long serialId) {
        return docSendcaseMapper.selectBySerialId(serialId);
    }
}
