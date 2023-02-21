package com.zhixin.zhfz.zhag.services.xzajxx;

import com.zhixin.zhfz.zhag.entity.CriminalAndAdministrationCaseEntity;

import java.util.List;
import java.util.Map;

public interface AdministrationCaseService {
    /**
     * 查询及条件查询(分页与查询所有共用)
     * @param map
     * @return
     * @throws Exception
     */
    List<CriminalAndAdministrationCaseEntity> listAdministrationCase(Map<String, Object> map)throws Exception;
    /**
     * 分页
     * @param map
     * @return
     * @throws Exception
     */
    int listAdministrationCount(Map<String, Object> map)throws Exception;
}
