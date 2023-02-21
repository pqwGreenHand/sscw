package com.zhixin.zhfz.bacs.services.policebelong;

import com.zhixin.zhfz.bacs.entity.ComboboxEntity;
import com.zhixin.zhfz.bacs.entity.OpenCabinetEntity;
import com.zhixin.zhfz.bacs.entity.PoliceBelongEntity;
import com.zhixin.zhfz.bacs.entity.PoliceBelongingsPhotoEntity;

import java.util.List;
import java.util.Map;

public interface IPoliceBelongService {
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
     * @param map
     * @return
     * @throws Exception
     */
    int count(Map<String, Object> map) throws Exception;

    /**
     * 增加存物信息
     *
     * @param entity
     * @return
     */
    int insertBelong(PoliceBelongEntity entity);

    /**
     * 修改民警存物信息
     *
     * @param entity
     */
    void updateBelong(PoliceBelongEntity entity);

    /**
     * 查询具体存物信息
     *
     * @param map2
     * @return
     */
    List<PoliceBelongEntity> listAllBelongdet(Map<String, Object> map2);

    /**
     * 根据存物id查询总数
     *
     * @param map2
     * @return
     * @throws Exception
     */
    int count1(Map<String, Object> map2) throws Exception;

    /**
     * 修改随身物品详情
     *
     * @param entity
     */
    void updateBelongdet(PoliceBelongEntity entity);

    /**
     * 删除民警存物信息
     *
     * @param id
     * @throws Exception
     */
    void deleteBelong(Long id) throws Exception;

    /**
     * 删除随身物品详情
     *
     * @param integer
     * @throws Exception
     */
    void deleteBelongdet(Long integer) throws Exception;

    /**
     * 修改物品信息
     *
     * @param entity
     */
    void updateBoxopen(PoliceBelongEntity entity);

    /**
     * 查询存物信息列表
     *
     * @param map
     * @return
     */
    List<PoliceBelongEntity> listAllBelongdet2(Map<String, Object> map);

    /**
     * 全部取物时根据唯一编号查询log所需信息
     *
     * @param sid
     * @return
     */
    public List<PoliceBelongEntity> selectloginfo(Long sid);

    /**
     * 根据民警入区id修改未领取的物品信息
     *
     * @param entity
     */
    void updateBoxopenout(PoliceBelongEntity entity);

    /**
     * 修改存物详情
     *
     * @param entity
     */
    void updateBoxopenouts(PoliceBelongEntity entity);

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
     * 增加存物照片
     *
     * @param serialID
     * @param path
     */
    void creatbelongphoto(String serialID, String path,String pid,String opUserId);

    /**
     * 删除一条物品记录，如果所有物品都删除，则删除父记录
     *
     * @param belongId
     * @param detailId
     */
    void deleteBelongdetAndBelong(long belongId, int detailId);

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
    boolean isGetBelongBySerialId(long s);

    /**
     * 根据存物id查询照片信息
     *
     * @param belongingsId
     * @return
     */
    List<PoliceBelongingsPhotoEntity> selectPhotoByBelongingsId(long belongingsId);

    /**
     * 增加开柜日志
     *
     * @param openCabinetEntity
     */
    void insertOpenboxData(OpenCabinetEntity openCabinetEntity);

    /**
     * 查询随身物品开柜记录
     *
     * @param map3
     * @return
     */
    List<PoliceBelongEntity> queryBelongingsCabinetLlog(Map<String, Object> map3);

    /**
     * 随身物品开柜记录总数
     * @param map3
     * @return
     */
    int countBelongingsCabinetLog(Map<String, Object> map3);

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
     *
     * @param params
     * @return
     */
    List<ComboboxEntity> listunUsedbox(Map<String, Object> params);
}
