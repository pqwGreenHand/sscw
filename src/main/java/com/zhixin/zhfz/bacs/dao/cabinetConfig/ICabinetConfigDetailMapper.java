package com.zhixin.zhfz.bacs.dao.cabinetConfig;

import com.zhixin.zhfz.bacs.entity.CabinetConfigDetailEntity;
import com.zhixin.zhfz.bacs.entity.CabinetConfigEntity;

import java.util.List;
import java.util.Map;

public interface ICabinetConfigDetailMapper {

    List<CabinetConfigDetailEntity> list(Map<String, Object> param);

    /**
     * 根据行列排序
     *
     * @param areaId
     * @return
     */
    List<CabinetConfigDetailEntity> selectRowColSort(Integer areaId);

    /**
     * 根据行列排序
     *
     * @param areaId
     * @return
     */
    List<CabinetConfigDetailEntity> selectRowColSortByPolice(Integer areaId);

    /**
     * 涉案物品柜根据行列排序
     *
     * @param areaId
     * @return
     */
    List<CabinetConfigDetailEntity> selectRowColSortBySA(Integer areaId);

    /**
     * 涉案物品柜手机排序
     * @param areaId
     * @return
     */
    List<CabinetConfigDetailEntity> selectRowColSortBySAAPP(Integer areaId);

    int count(Map<String, Object> param);

    void insert(CabinetConfigDetailEntity detailEntity);

    void delete(int id);

    void update(CabinetConfigDetailEntity entity);

    /**
     * 根据外参ID获取内参LIST
     *
     * @param id
     * @return
     * @throws Exception
     */
    CabinetConfigEntity listInParamDetailByOutParamId(Long id) throws Exception;

    /**
     * 查询开柜门号
     *
     * @param lockid
     * @return
     */
    String queryBoxNumberById(int lockid);
}
