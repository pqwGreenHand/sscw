package com.zhixin.zhfz.sacw.services.warehouse;

import com.zhixin.zhfz.sacw.entity.WareHouseEntity;

import java.util.List;
import java.util.Map;

public interface IWareHouseService {
    /**
     * 查询所有涉案物品仓库
     *
     * @return
     * @throws Exception
     */
    List<WareHouseEntity> listAllWareHouse(Map<String, Object> map) throws Exception;

    List<WareHouseEntity> AllWareHouse(Map<String, Object> map) throws Exception;

    /**
     * @param
     * @throws Exception
     */

    List<WareHouseEntity> findAllWareHouse(Map<String, Object> map) throws Exception;

    /**
     * 插入涉案物品仓库
     *
     * @param lawCaseEntity
     * @throws Exception
     */
    void insertWareHouse(WareHouseEntity wareHouseEntity) throws Exception;

    /**
     * 根据id删除涉案物品仓库
     *
     * @param id
     * @throws Exception
     */
    void deleteWareHouse(int id) throws Exception;

    /**
     * 修改涉案物品仓库信息
     *
     * @param lawCaseEntity
     * @throws Exception
     */
    void updateWareHouse(WareHouseEntity wareHouseEntity) throws Exception;

    /**
     * @param map
     * @throws Exception
     */
    int count(Map<String, Object> map) throws Exception;

    /**
     * 唯一校验
     * @param entity
     * @return
     * @throws Exception
     */
    int qureyEntityByPram(WareHouseEntity entity);
}
