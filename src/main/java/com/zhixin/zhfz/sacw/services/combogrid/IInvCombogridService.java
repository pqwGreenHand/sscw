package com.zhixin.zhfz.sacw.services.combogrid;


import com.zhixin.zhfz.bacs.entity.CombogridEntity;

import java.util.List;
import java.util.Map;

public interface IInvCombogridService {


    List<CombogridEntity> getAreaName(Map<String, Object> map) throws Exception;

    /*
    获取案件信息
     */
    List<CombogridEntity> getLawCase(Map<String, Object> map) throws Exception;

    List<CombogridEntity> getLawCaseForInv(Map<String, Object> map) throws Exception;

    /*
    获取案件信息(远程示证  高级查询)
     */
    List<CombogridEntity> getLawCaseId(Map<String, Object> map) throws Exception;

}
