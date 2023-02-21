package com.zhixin.zhfz.bacs.services.lawdoc;

import com.zhixin.zhfz.bacs.dao.lawdoc.IDocEnforcementVideoMapper;
import com.zhixin.zhfz.bacs.entity.DocEnforcementVideoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DocEnforcementVideoServiceImpl implements  IDocEnforcementVideoService {

    @Autowired
    private IDocEnforcementVideoMapper docEnforcementVideoMapper;

    @Override
    public DocEnforcementVideoEntity getDocEnforcementVideo(Long serialId) {
        return docEnforcementVideoMapper.getEnforcementVideo(serialId);
    }
}
