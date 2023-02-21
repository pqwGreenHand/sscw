package com.zhixin.zhfz.sacw.services.label;

import com.zhixin.zhfz.sacw.entity.LabelEntity;

import java.util.List;
import java.util.Map;

public interface ILabelService {
    /**
     * 查询及条件查询
     *
     * @param map
     * @return
     * @throws Exception
     */
    List<LabelEntity> list(Map<String, Object> map) throws Exception;

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
    void update(LabelEntity entity) throws Exception;

    /**
     * 插入方法
     *
     * @param entity
     * @throws Exception
     */
    void insert(LabelEntity entity) throws Exception;

    /**
     * 查询及条件查询
     *
     * @param map
     * @return
     * @throws Exception
     */
    List<LabelEntity> listLabelByStatus(Map<String, Object> map) throws Exception;

    List<LabelEntity> listAll() throws Exception;

    LabelEntity getLabelByNo(Map<String, Object> params) throws Exception;

    LabelEntity getLabelByIcNo(Map<String, Object> params) throws Exception;
}
