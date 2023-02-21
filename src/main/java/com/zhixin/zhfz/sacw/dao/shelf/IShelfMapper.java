package com.zhixin.zhfz.sacw.dao.shelf;

import com.zhixin.zhfz.sacw.entity.ShelfEntity;

import java.util.List;
import java.util.Map;

public interface IShelfMapper {
    /**
     * 查询所有货架信息
     *
     * @return
     * @throws Exception
     */
    List<ShelfEntity> listAllShelf(Map<String, Object> map) throws Exception;

    /**
     * 插入货架信息
     *
     * @param shelfEntity
     * @throws Exception
     */
    void insertShelf(ShelfEntity shelfEntity) throws Exception;

    /**
     * 根据id删除货架信息
     *
     * @param id
     * @throws Exception
     */
    void deleteShelf(int id) throws Exception;

    /**
     * 修改货架信息信息
     *
     * @param shelfEntity
     * @throws Exception
     */
    void updateShelf(ShelfEntity shelfEntity) throws Exception;

    /**
     * @param map
     * @throws Exception
     */
    int count(Map<String, Object> map) throws Exception;

    /**
     * 根据locationId查询货架信息
     *
     * @param map
     * @return
     * @throws Exception
     */
    List<ShelfEntity> listAllShelfByLocationId(Map<String, Object> map) throws Exception;

}
