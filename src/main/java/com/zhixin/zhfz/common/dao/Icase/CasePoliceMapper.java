package com.zhixin.zhfz.common.dao.Icase;


import com.zhixin.zhfz.common.entity.CasePoliceEntity;

import java.util.Map;

public interface CasePoliceMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CasePoliceEntity record);

    int insertSelective(CasePoliceEntity record);

    CasePoliceEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CasePoliceEntity record);

    int updateByPrimaryKey(CasePoliceEntity record);

    int deleteByCaseId(Integer caseId);

    CasePoliceEntity selectCasePoliceByPolice(Map<String, Object> params);
}