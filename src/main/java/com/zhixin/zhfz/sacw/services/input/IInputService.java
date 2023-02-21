package com.zhixin.zhfz.sacw.services.input;

import com.zhixin.zhfz.sacw.entity.InputEntity;

import java.util.List;
import java.util.Map;

public interface IInputService {
    /**
     * 查询及条件查询
     *
     * @param map
     * @return
     * @throws Exception
     */
    List<InputEntity> list(Map<String, Object> map) throws Exception;

    /**
     * 分页
     *
     * @param map
     * @return
     * @throws Exception
     */
    int count(Map<String, Object> map) throws Exception;

    /**
     * 插入入库数据
     *
     * @param entity
     * @throws Exception
     */
    public void insert(InputEntity entity) throws Exception;


}
