package com.zhixin.zhfz.bacs.services.lawdoc;

import com.zhixin.zhfz.bacs.dao.lawdoc.IDocPhotoEvidenceMapper;
import com.zhixin.zhfz.bacs.entity.DocPhotoEvidenceEntity;
import com.zhixin.zhfz.bacs.entity.DocSendcaseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DocPhotoEvidenceServiceImpl implements IDocPhotoEvidenceService {

    @Autowired
    private IDocPhotoEvidenceMapper docPhotoEvidenceMapper;


    @Override
    public DocPhotoEvidenceEntity getDocPhotoEvidence(Long serialId) {
        return docPhotoEvidenceMapper.getDocPhotoEvidence(serialId);
    }
}
