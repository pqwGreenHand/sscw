package com.zhixin.zhfz.zhag.services.xsajxx;


import java.util.List;
import java.util.Map;

import com.zhixin.zhfz.zhag.entity.CriminalAndAdministrationCaseEntity;

public interface CriminalCaseService {
    /**
     * 查询及条件查询(分页与查询所有共用)
     * @param map
     * @return
     * @throws Exception
     */
    List<CriminalAndAdministrationCaseEntity> listCriminalCase(Map<String, Object> map)throws Exception;
    /**
     * 分页
     * @param map
     * @return
     * @throws Exception
     */
    int listCriminalCount(Map<String, Object> map)throws Exception;
    
    /**
     * 刑事案件
     * @param map
     * @return
     * @throws Exception
     */
   List<CriminalAndAdministrationCaseEntity> xsajList(Map<String, Object> map)throws Exception;
   
    /**
     * 刑事案件count
     * @param map
     * @return
     * @throws Exception
     */
   int xsajCount(Map<String, Object> map)throws Exception;
}
