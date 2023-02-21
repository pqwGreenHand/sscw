package com.zhixin.zhfz.bacs.dao.exhibit;

import com.zhixin.zhfz.bacs.entity.ExhibitEntity;

import java.util.List;
import java.util.Map;


public interface IExhibitdetMapper {
    /**
     * 根据涉案存物id查询具体存物信息
     *
     * @param map2
     * @return
     */
    List<ExhibitEntity> listAllExhibitdet(Map<String, Object> map2);

    /**
     * 根据存物id查询下面所有物品的总数
     *
     * @param params
     * @return
     */
    int count1(Map<String, Object> params);

    /**
     * 增加具体存物信息
     *
     * @param obj
     */
    void insertExhibitdet(ExhibitEntity obj);

    /**
     * 修改存物信息
     *
     * @param entity
     */
    void updateExhibitdet(ExhibitEntity entity);

    /**
     * 根据id删除物品具体信息
     *
     * @param id
     */
    void deleteExhibitdet(Long id);

    /**
     * 查询具体的未领取的涉案存物信息
     *
     * @param map
     * @return
     */
    List<ExhibitEntity> listAllExhibitdet2(Map<String, Object> map);

    /**
     * 查询具体的涉案存物信息
     *
     * @param map
     * @return
     */
    List<ExhibitEntity> listAllExhibitdet3(Map<String, Object> map);

    /**
     * 根据具体的存物id查询物品的具体信息
     *
     * @param map
     * @return
     */
    List<ExhibitEntity> listAllExhibitdet4(Map<String, Object> map);

    /**
     * 查询所有物品的状态
     *
     * @param map
     * @return
     */
    List<ExhibitEntity> getDetailStatistics(Map<String, Object> map);

    /**
     * 创建开柜记录
     *
     * @param obj
     */
    void creatBoxopenouts(ExhibitEntity obj);

    /**
     * 更新该专属编号下每件物品的提取状态，信息
     *
     * @param obj
     */
    void updateBoxopenoutdets(ExhibitEntity obj);

    /**
     * 修改存物信息
     *
     * @param entity
     */
    void updateBoxopenouts(ExhibitEntity entity);

    /**
     * 根据存物id删除物品具体信息
     *
     * @param id
     */
    void deleteExhibitdetsecond(Long id);

    /**
     * 查询存物id下的所有物品的总数
     *
     * @param id
     * @return
     */
    int countByBelongId(Integer id);

    /**
     * 查询涉案物品开柜记录
     *
     * @param map3
     * @return
     */
    List<ExhibitEntity> queryExhibitCabinetLog(Map<String, Object> map3);

    /**
     * 查询涉案物品开柜总数
     *
     * @param map3
     * @return
     */
    int countExhibitCabinetLog(Map<String, Object> map3);

}