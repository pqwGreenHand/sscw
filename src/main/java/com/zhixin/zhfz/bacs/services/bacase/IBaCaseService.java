package com.zhixin.zhfz.bacs.services.bacase;

import com.zhixin.zhfz.bacs.entity.BaCaseEntity;

import java.util.List;
import java.util.Map;

public interface IBaCaseService {

    List<BaCaseEntity> listAllLawPerson(Map<String, Object> map);

    int countAllLawPerson(Map<String, Object> map);
}
