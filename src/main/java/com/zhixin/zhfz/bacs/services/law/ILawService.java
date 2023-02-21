package com.zhixin.zhfz.bacs.services.law;

import com.zhixin.zhfz.bacs.entity.LawEntity;

import java.util.List;
import java.util.Map;

public interface ILawService {

    void insertLaw(LawEntity lawEntity) throws Exception;
    
    List<LawEntity> list(Map<String, Object> map) throws Exception;

    int count(Map<String, Object> map) throws Exception;

    void updateLaw(LawEntity lawEntity);

    void removeLaw(String id);
}
