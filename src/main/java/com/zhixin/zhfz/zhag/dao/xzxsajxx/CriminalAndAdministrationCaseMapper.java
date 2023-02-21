package com.zhixin.zhfz.zhag.dao.xzxsajxx;

import java.util.List;
import java.util.Map;

import com.zhixin.zhfz.zhag.entity.CriminalAndAdministrationCaseEntity;

public interface CriminalAndAdministrationCaseMapper {
    /**
     * 刑事案件分页查询
     *
     * @param map
     * @return
     * @throws Exception
     */
    List<CriminalAndAdministrationCaseEntity> listCriminalCase(Map<String, Object> map) throws Exception;

    int listCriminalCount(Map<String, Object> map) throws Exception;

    /**
     * 行政案件分页查询
     *
     * @param map
     * @return
     * @throws Exception
     */
    List<CriminalAndAdministrationCaseEntity> lisAdministrationCase(Map<String, Object> map) throws Exception;

    int listAdministrationCount(Map<String, Object> map) throws Exception;

    

    List<CriminalAndAdministrationCaseEntity> xsajList(Map<String, Object> map)throws Exception;
    
    int xsajCount(Map<String, Object> map)throws Exception;
}
