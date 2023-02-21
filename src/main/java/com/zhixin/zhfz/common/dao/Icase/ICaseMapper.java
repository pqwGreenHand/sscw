package com.zhixin.zhfz.common.dao.Icase;


import com.zhixin.zhfz.common.entity.CaseEntity;

import java.util.List;
import java.util.Map;

public interface ICaseMapper {
    /**
     * 分页查询
     *
     * @param map
     * @return
     * @throws Exception
     */
    List<CaseEntity> list(Map<String, Object> map) throws Exception;

    int count(Map<String, Object> map) throws Exception;

    /**
     * 插入案件信息
     * @param caseEntity
     * @throws Exception
     */
    int insertSelective(CaseEntity caseEntity) throws Exception;

    int getCaseId(String ajbh) throws Exception;

    void deleteByPrimaryKey(int caseId) throws Exception;
    //更新案件信息
    void updateByPrimaryKeySelective(CaseEntity caseEntity) throws Exception;

    CaseEntity getCaseById(Integer id) throws Exception;

    CaseEntity getCaseInfoById(Integer id) throws Exception;
    
    CaseEntity isCaseInfo(CaseEntity caseEntity);

    /**
     * 查询首页刑事行政案件数
     *
     * @param map
     * @return
     * @throws Exception
     */
    List<CaseEntity> listCase(Map<String, Object> map) throws Exception;


}
