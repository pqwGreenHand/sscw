package com.zhixin.zhfz.bacs.dao.policebelong;

import com.zhixin.zhfz.bacs.entity.ComboboxEntity;
import com.zhixin.zhfz.bacs.entity.ExhibitEntity;
import com.zhixin.zhfz.bacs.entity.PoliceBelongEntity;
import com.zhixin.zhfz.bacs.entity.PoliceBelongingsPhotoEntity;

import java.util.List;
import java.util.Map;

public interface IPoliceBelongMapper {
    /**
     * 查询存物信息
     *
     * @param map
     * @return
     */
    List<PoliceBelongEntity> listAllBelong(Map<String, Object> map);

    /**
     * 查询民警存物信息总数
     *
     * @param params
     * @return
     */
    int count(Map<String, Object> params);

    /**
     * 增加存物信息
     *
     * @param entity
     */
    void insertBelong(PoliceBelongEntity entity);

    /**
     * 修改民警存物信息
     *
     * @param entity
     */
    void updateBelong(PoliceBelongEntity entity);

    /**
     * 删除民警存物信息
     *
     * @param id
     */
    void deleteBelong(Long id);

    /**
     * 查询柜门未领取的民警物品信息
     *
     * @param map
     * @return
     */
    PoliceBelongEntity selectinfo(Map<String, Object> map);

    /**
     * 修改物品信息
     *
     * @param entity
     */
    void updateBoxopen(PoliceBelongEntity entity);

    /**
     * 根据民警入区id修改未领取的物品信息
     *
     * @param entity
     */
    void updateBoxopenout(PoliceBelongEntity entity);

    /**
     * 全部取物时根据唯一编号查询log所需信息
     *
     * @param s
     * @return
     */
    List<PoliceBelongEntity> selectloginfo(Long s);

    /**
     * 根据id修改民警物品信息
     *
     * @param obj
     */
    void updateUpperInfo(PoliceBelongEntity obj);

    /**
     * 根据物品id查询未领取的所有物品总数
     *
     * @param s
     * @return
     */
    int selectbelonginfo(long s);

    /**
     * 查询台账内容
     *
     * @param entity
     * @return
     */
    List<PoliceBelongEntity> getDocInfo(PoliceBelongEntity entity);

    /**
     * 根据民警入区id查存物信息
     *
     * @param entity
     * @return
     */
    List<PoliceBelongEntity> getDocInfoNoLockers(PoliceBelongEntity entity);

    /**
     * 根据searilid查询未领取的物品信息
     *
     * @param serialID
     * @return
     */
    PoliceBelongEntity selectphotoinfo(String serialID);

    /**
     * 增加存物照片
     *
     * @param obj
     */
    void creatbelongphoto(PoliceBelongEntity obj);

    /**
     * 根据民警入区id查询存物总数
     *
     * @param serialID
     * @return
     */
    int selectpinfo(String serialID);

    /**
     * 修改存物照片
     *
     * @param obj
     */
    void updatebelongphoto(PoliceBelongEntity obj);

    /**
     * 根据柜门查看民警入区id
     *
     * @param lockId
     * @return
     */
    long getSerialIdByLockId(String lockId);

    /**
     * 根据民警入区id查询总数
     *
     * @param s
     * @return
     */
    int isGetBelongBySerialId(long s);

    /**
     * 根据id查看民警入区id
     *
     * @param obj
     * @return
     */
    Long selectserialid(PoliceBelongEntity obj);

    /**
     * 查询未领取的入区民警物品信息
     *
     * @param paramMap
     * @return
     */
    PoliceBelongEntity selectSidnfo(Map<String, Object> paramMap);

    /**
     * 根据存物id查询照片信息
     *
     * @param belongingsId
     * @return
     */
    List<PoliceBelongingsPhotoEntity> selectPhotoByBelongingsId(long belongingsId);

    /**
     * 检查当前柜子是否被其他人占用
     *
     * @param map
     * @return
     */
    PoliceBelongEntity checkOtherPersonUse(Map<String, Object> map);

    /**
     * 根据id查询民警存物信息
     *
     * @param id
     * @return
     */
    PoliceBelongEntity getBelongById(Long id);

    /**
     * 根据柜门id查询非领取的总数
     *
     * @param lockerId
     * @return
     */
    int countDetByLockerId(int lockerId);

    /**
     * 导出查询
     *
     * @param paramMap
     * @return
     */
    List<PoliceBelongEntity> getBelongForExport(Map<String, Object> paramMap);
    /**
     * 查询空的随身储物柜
     * @param params
     * @return
     */
    List<ComboboxEntity> listunUsedbox(Map<String, Object> params);
    /**
     * 导出随身储物柜的储物数据
     * @param paramMap
     * @return
     */
    List<PoliceBelongEntity> getPoliceBelongForExport(Map<String, Object> paramMap);
}