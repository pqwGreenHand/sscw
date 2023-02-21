package com.zhixin.zhfz.bacs.dao.policebelong;

import com.zhixin.zhfz.bacs.entity.PoliceBelongEntity;

import java.util.List;
import java.util.Map;

public interface IPoliceBelongdetMapper {
    /**
     * 增加存物具体信息
     *
     * @param obj
     */
    void insertBelongdet(PoliceBelongEntity obj);

    /**
     * 查询具体存物信息
     *
     * @param map2
     * @return
     */
    List<PoliceBelongEntity> listAllBelongdet(Map<String, Object> map2);

    /**
     * 根据id删除具体存物信息
     *
     * @param id
     */
    void deleteBelongdet(Long id);

    /**
     * 根据存物id查询总数
     *
     * @param params
     * @return
     */
    int count1(Map<String, Object> params);

    /**
     * 查询存物信息列表
     *
     * @param map
     * @return
     */
    List<PoliceBelongEntity> listAllBelongdet2(Map<String, Object> map);

    /**
     * 修改存物详情
     *
     * @param entity
     */
    void updateBoxopenouts(PoliceBelongEntity entity);

    /**
     * 创建开柜记录
     *
     * @param entity
     */
    void creatBoxopenouts(PoliceBelongEntity entity);

    /**
     * 更新该专属编号下每件物品的提取状态，信息
     *
     * @param obj
     */
    void updateBoxopenoutdets(PoliceBelongEntity obj);

    /**
     * 根据柜门id查看信息
     *
     * @param map
     * @return
     */
    List<PoliceBelongEntity> setSerialId(Map<String, Object> map);

    /**
     * 查询存物信息列表
     *
     * @param map
     * @return
     */
    List<PoliceBelongEntity> listAllBelongdet3(Map<String, Object> map);

    /**
     * 根据id删除具体存物信息
     *
     * @param id
     */
    void deleteBelongdetsecond(Long id);

    /**
     * 根据存物id查询存物总数
     *
     * @param belongId
     * @return
     */
    int countByBelongId(long belongId);

    /**
     * 查询随身物品开柜记录
     *
     * @param map3
     * @return
     */
    List<PoliceBelongEntity> queryBelongingsCabinetLlog(Map<String, Object> map3);

    /**
     * 随身物品开柜记录总数
     *
     * @param map3
     * @return
     */
    int countBelongingsCabinetLog(Map<String, Object> map3);

    /**
     * 根据柜门id查询存物总数
     *
     * @param lockerId
     * @return
     */
    int countByLockerId(int lockerId);

}