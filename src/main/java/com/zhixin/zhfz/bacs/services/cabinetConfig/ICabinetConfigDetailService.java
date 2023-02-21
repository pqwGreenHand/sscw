package com.zhixin.zhfz.bacs.services.cabinetConfig;

import com.zhixin.zhfz.bacs.entity.CabinetConfigDetailEntity;
import com.zhixin.zhfz.bacs.entity.CabinetConfigEntity;

import java.util.List;
import java.util.Map;

public interface ICabinetConfigDetailService {

    List<CabinetConfigDetailEntity> list(Map<String, Object> param);

    int count(Map<String, Object> param);

    void Insert(CabinetConfigDetailEntity detailEntity);

    void delete(int intID);

    void update(CabinetConfigDetailEntity entity);

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
     * 根据外参ID获取内参LIST
     *
     * @param id
     * @return
     * @throws Exception
     */
    CabinetConfigEntity listInParamDetailByOutParamId(Long id) throws Exception;

    /**
     * 根据储物柜id查询编号
     *
     * @param lockid
     * @return
     */
    String queryBoxNumberById(int lockid);

    /**
     * 涉案物品柜手机排序
     *
     * @param areaId
     * @return
     */
    List<CabinetConfigDetailEntity> selectRowColSortBySAAPP(Integer areaId);
}
