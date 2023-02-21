package com.zhixin.zhfz.sacw.services.handfy;

import com.zhixin.zhfz.sacw.entity.InvolvedEntity;

import java.util.List;
import java.util.Map;

public interface IHandFyService {
    /**
     * 查询及条件查询
     *
     * @param map
     * @return
     * @throws Exception
     */
    List<InvolvedEntity> list(Map<String, Object> map) throws Exception;

    /**
     * 分页
     *
     * @param map
     * @return
     * @throws Exception
     */
    int count(Map<String, Object> map) throws Exception;


}
