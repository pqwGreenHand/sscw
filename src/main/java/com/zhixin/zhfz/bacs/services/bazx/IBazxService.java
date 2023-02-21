package com.zhixin.zhfz.bacs.services.bazx;

import com.zhixin.zhfz.bacs.entity.InterrogateCaseEntity;
import com.zhixin.zhfz.bacs.entity.PersonEntity;
import com.zhixin.zhfz.bacs.entity.SerialEntity;
import com.zhixin.zhfz.common.entity.CaseEntity;

import java.util.List;
import java.util.Map;

public interface IBazxService {

    List<Map<String, Object>> queryAjxx(Map<String, Object> param);

    Integer queryAjxxCount(Map<String, Object> param);

    List<Map<String, Object>> queryPerson(Map<String, Object> param);

    List<Map<String, Object>> queryBelong(Map<String, Object> param);

    InterrogateCaseEntity queryAjxxById(Integer caseId);

    SerialEntity querySerialById(Long serialId);

    PersonEntity getPersonById(Long personId);
}
