package com.zhixin.zhfz.common.services.Icase;

import com.zhixin.zhfz.common.entity.CasePoliceEntity;
import org.springframework.stereotype.Service;

import java.util.Map;


public interface CasePoliceService {
    void inserPolice(CasePoliceEntity casePoliceEntity);
    void deleteByCaseId(Integer caseId);
    CasePoliceEntity selectCasePoliceByPolice(Map<String, Object> params);
}
