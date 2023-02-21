package com.zhixin.zhfz.sacw.dao.labellog;

import com.zhixin.zhfz.sacw.entity.LabelLogEntity;

import java.util.List;
import java.util.Map;

public interface ILabelLogMapper {

    /**
     * 查询及条件查询
     *
     * @param map
     * @return
     * @throws Exception
     */
    List<LabelLogEntity> list(Map<String, Object> map) throws Exception;

    /**
     * 分页
     *
     * @param map
     * @return
     * @throws Exception
     */
    int count(Map<String, Object> map) throws Exception;

    /**
     * 删除
     *
     * @param id
     * @throws Exception
     */
    void delete(int id) throws Exception;

    /**
     * 修改
     *
     * @param entity
     * @throws Exception
     */
    void update(LabelLogEntity entity) throws Exception;

    /**
     * 插入方法
     *
     * @param entity
     * @throws Exception
     */
    void insert(LabelLogEntity entity) throws Exception;

}