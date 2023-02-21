package com.zhixin.zhfz.sacw.services.remotelook;

import com.zhixin.zhfz.sacw.entity.RemoteLookEntity;

import java.util.List;
import java.util.Map;

public interface IRemoteLookService {
    /**
     * 查询及条件查询
     *
     * @param map
     * @return
     * @throws Exception
     */
    List<RemoteLookEntity> list(Map<String, Object> map) throws Exception;

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
    void update(RemoteLookEntity entity) throws Exception;

    /**
     * 插入方法
     *
     * @param entity
     * @throws Exception
     */
    void insert(RemoteLookEntity entity) throws Exception;


    /**
     * 查询及条件查询
     *
     * @param map
     * @return
     * @throws Exception
     */
    List<RemoteLookEntity> listById(Map<String, Object> map) throws Exception;

    /**
     * 查询及条件查询
     *
     * @param map
     * @return
     * @throws Exception
     */
    List<RemoteLookEntity> queryRequestInvolved(Map<String, Object> map) throws Exception;
}
