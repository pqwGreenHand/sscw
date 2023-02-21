package com.zhixin.zhfz.sacw.services.sacamera;

import com.zhixin.zhfz.sacw.entity.SaCameraEntity;

import java.util.List;
import java.util.Map;


public interface ISaCameraService {
    /**
     * 查询及条件查询
     *
     * @param map
     * @return
     * @throws Exception
     */
    List<SaCameraEntity> list(Map<String, Object> map) throws Exception;

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
    void update(SaCameraEntity entity) throws Exception;

    /**
     * 插入方法
     *
     * @param entity
     * @throws Exception
     */
    void insert(SaCameraEntity entity) throws Exception;

    /**
     * 批量插入方法
     *
     * @param cameraList
     * @throws Exception
     */
    void insertList(List<SaCameraEntity> cameraList) throws Exception;

    List<SaCameraEntity> getCameraByRoomID(Map<String, Object> map) throws Exception;
}
