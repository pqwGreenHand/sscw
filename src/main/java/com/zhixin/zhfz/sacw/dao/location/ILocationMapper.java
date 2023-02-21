package com.zhixin.zhfz.sacw.dao.location;

import com.zhixin.zhfz.sacw.entity.LocationEntity;

import java.util.List;
import java.util.Map;

public interface ILocationMapper {
    /**
     * 查询区域信息
     *
     * @return
     * @throws Exception
     */
    List<LocationEntity> listLocation(Map<String, Object> map) throws Exception;

    /**
     * 查询所有某个仓库包含的区域
     *
     * @return
     * @throws Exception
     */
    List<LocationEntity> listLocationByWarehouse(Map<String, Object> map) throws Exception;

    /**
     * 查询所有某个仓库包含的区域
     *
     * @return
     * @throws Exception
     */
    List<LocationEntity> findLocationByWarehouse(Map<String, Object> map) throws Exception;

    /**
     * 插入区域信息
     *
     * @param locationEntity
     * @throws Exception
     */
    void insertLocation(LocationEntity locationEntity) throws Exception;

    /**
     * 根据id删除区域信息
     *
     * @param id
     * @throws Exception
     */
    void deleteLocation(int id) throws Exception;

    /**
     * 修改区域信息
     *
     * @param locationEntity
     * @throws Exception
     */
    void updateLocation(LocationEntity locationEntity) throws Exception;

    int count(Map<String, Object> map) throws Exception;

    /**
     * @param entity
     * @throws Exception
     */
    List<LocationEntity> qureyEntityByPram(LocationEntity entity);

}
