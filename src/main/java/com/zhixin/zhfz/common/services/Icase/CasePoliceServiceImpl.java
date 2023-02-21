package com.zhixin.zhfz.common.services.Icase;

import com.zhixin.zhfz.common.dao.Icase.CasePoliceMapper;
import com.zhixin.zhfz.common.entity.CasePoliceEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CasePoliceServiceImpl implements CasePoliceService{
    @Autowired
    private CasePoliceMapper casePoliceMapper;
    @Override
    public void inserPolice(CasePoliceEntity casePoliceEntity) {
        casePoliceMapper.insertSelective(casePoliceEntity);
    }

    @Override
    public void deleteByCaseId(Integer caseId) {
        casePoliceMapper.deleteByCaseId(caseId);
    }

    @Override
    public CasePoliceEntity selectCasePoliceByPolice(Map<String, Object> params) {
        return casePoliceMapper.selectCasePoliceByPolice(params);
    }
}
