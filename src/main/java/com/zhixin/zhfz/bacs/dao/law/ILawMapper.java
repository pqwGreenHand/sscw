package com.zhixin.zhfz.bacs.dao.law;

import com.zhixin.zhfz.bacs.entity.LawEntity;

import java.util.List;
import java.util.Map;

public interface ILawMapper {
    void insertLaw(LawEntity lawEntity);

    List<LawEntity> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    void updateLaw(LawEntity lawEntity);

    void removeLaw(String id);
}
