package com.zhixin.zhfz.bacs.services.exhibit;

import com.zhixin.zhfz.bacs.entity.ExhibitEntity;
import com.zhixin.zhfz.bacs.entity.ExhibitPhotoEntity;

import java.util.List;
import java.util.Map;

public interface IExhibitService {
    /**
     * 查询具体的未领取的涉案存物信息
     *
     * @param map
     * @return
     */
    List<ExhibitEntity> listAllExhibitdet2(Map<String, Object> map);

    /**
     * 增加涉案物品存物详情
     *
     * @param entity
     * @return
     */
    int insertExhibit(ExhibitEntity entity);

    /**
     * 删除涉案存物具体信息
     *
     * @param exhibitId
     * @param detailId
     */
    void deleteExhibitdetAndExhibit(int exhibitId, int detailId);

    /**
     * 修改存物详情
     *
     * @param entity
     */
    void updateExhibitdet(ExhibitEntity entity);

    /**
     * 查询未领取的涉案物品信息
     *
     * @param map
     * @return
     */
    List<ExhibitEntity> listAllExhibitdet3(Map<String, Object> map);

    /**
     * 通过lockId 查询 serial_id
     *
     * @param lockId
     * @return
     */
    long getSerialIdByLockId(String lockId);

    /**
     * 领取物品
     *
     * @param entity
     */
    void updateBoxopenouts(ExhibitEntity entity) throws Exception;

    /**
     * 全部领取
     *
     * @param entity
     */
    void updateBoxopenout(ExhibitEntity entity);

    /**
     * 根据serialid查询
     *
     * @param sid
     * @return
     */
    ExhibitEntity selectlockNobysid(int sid);

    /**
     * 上传图片
     *
     * @param serialID
     * @param spath
     */
    void createxhibitphoto(String serialID, String spath,String pid,String opUserId);

    /**
     * 查询嫌疑人所使用柜子信息
     *
     * @param map
     * @return
     */
    List<ExhibitEntity> listAllExhibitByLocker(Map<String, Object> map);

    /**
     * 查询所有存物的数量
     *
     * @param map
     * @return
     * @throws Exception
     */
    int count(Map<String, Object> map) throws Exception;

    /**
     * 查询随身物品开柜记录
     *
     * @param map3
     * @return
     */
    List<ExhibitEntity> queryExhibitCabinetLlog(Map<String, Object> map3);

    /**
     * 随身物品开柜记录总数
     *
     * @param map3
     * @return
     */
    int countyExhibitCabinetLog(Map<String, Object> map3);

    /**
     * 根据存物id查询全部详情数量
     *
     * @param params
     * @return
     */
    int count1(Map<String, Object> params);

    /**
     * 查询某存物柜的存物信息
     *
     * @param map
     * @return
     */
    List<ExhibitEntity> listAllExhibitdet(Map<String, Object> map);

    /**
     * 查询图片
     *
     * @param exhibitId
     * @return
     */
    List<ExhibitPhotoEntity> selectPhotoByExhibitId(long exhibitId);
}
