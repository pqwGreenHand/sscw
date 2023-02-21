package com.zhixin.zhfz.sacw.dao.warehouse;


import com.zhixin.zhfz.sacw.entity.WareHouseEntity;

import java.util.List;
import java.util.Map;


public interface IWareHouseMapper {
    /**
     * 查询所有涉案物品仓库
     *
     * @return
     * @throws Exception
     */
    List<WareHouseEntity> listAllWareHouse(Map<String, Object> map);

    List<WareHouseEntity> AllWareHouse(Map<String, Object> map);

    /**
     * 插入涉案物品仓库
     *
     * @param lawCaseEntity
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

    void deleteWareHouse1(int id) throws Exception;

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
     * @param entity
     * @throws Exception
     */
    List<WareHouseEntity> qureyEntityByPram(WareHouseEntity entity);
}
