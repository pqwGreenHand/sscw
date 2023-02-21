package com.zhixin.zhfz.sacw.services.nvr;


import com.zhixin.zhfz.sacw.entity.SaNvrEntity;

import java.util.List;
import java.util.Map;

public interface ISaNvrService {
    /**
     * 查询及条件查询
     *
     * @param map
     * @return
     * @throws Exception
     */
    List<SaNvrEntity> list(Map<String, Object> map) throws Exception;

    List<SaNvrEntity> comboNvr(Map<String, Object> map) throws Exception;

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
     * 删除
     *
     * @param id
     * @throws Exception
     */
    void deleteCameraByNvrId(int id) throws Exception;

    /**
     * 修改
     *
     * @param entity
     * @throws Exception
     */
    void update(SaNvrEntity entity) throws Exception;

    /**
     * 插入方法
     *
     * @param entity
     * @throws Exception
     */
    void insert(SaNvrEntity entity) throws Exception;

    /**
     * 插入方法
     *
     * @param nvrList
     * @throws Exception
     */
    void insertList(List<SaNvrEntity> nvrList) throws Exception;
}
