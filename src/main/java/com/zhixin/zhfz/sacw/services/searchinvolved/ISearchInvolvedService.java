package com.zhixin.zhfz.sacw.services.searchinvolved;

import com.zhixin.zhfz.sacw.entity.SearchInvolvedEntity;

import java.util.List;
import java.util.Map;

public interface ISearchInvolvedService {
    /**
     * 查询及条件查询
     *
     * @param map
     * @return
     * @throws Exception
     */
    List<SearchInvolvedEntity> list(Map<String, Object> map) throws Exception;

    int count(Map<String, Object> map) throws Exception;

}
