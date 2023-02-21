package com.zhixin.zhfz.bacs.dao.exhibit;

import com.zhixin.zhfz.bacs.entity.ExhibitEntity;
import com.zhixin.zhfz.bacs.entity.ExhibitPhotoEntity;
import java.util.List;
import java.util.Map;


public interface IExhibitMapper {
    /**
     * 查询所有柜子的存储信息
     *
     * @param map
     * @return
     */
    List<ExhibitEntity> listAllExhibit(Map<String, Object> map);

    /**
     * 根据id查询涉案物品信息
     *
     * @param id
     * @return
     */
    ExhibitEntity getExhibitById(Long id);

    /**
     * 插叙涉案财物总数
     *
     * @param params
     * @return
     */
    int count(Map<String, Object> params);

    /**
     * 根据柜门id查询未领取的涉案信息
     *
     * @param map
     * @return
     */
    ExhibitEntity selectinfo(Map<String, Object> map);

    /**
     * 增加涉案存物信息
     *
     * @param entity
     */
    void insertExhibit(ExhibitEntity entity);

    /**
     * 修改涉案存物信息
     *
     * @param entity
     */
    void updateExhibit(ExhibitEntity entity);

    /**
     * 根据id删除涉案存物信息
     *
     * @param id
     */
    void deleteExhibit(Long id);

    /**
     * 修改涉案物品信息
     *
     * @param entity
     */
    void updateBoxopen(ExhibitEntity entity);

    /**
     * 全部取物时根据唯一编号查询log所需信息
     *
     * @param s
     * @return
     */
    List<ExhibitEntity> selectloginfo(Long s);

    /**
     * 根据searilid修改未领取的涉案存物信息
     *
     * @param entity
     */
    void updateBoxopenout(ExhibitEntity entity);

    /**
     * 根据存物id查询具体存物总数
     *
     * @param s
     * @return
     */
    int selectExhibitInfo(int s);

    /**
     * 根据id修改涉案物品信息
     *
     * @param obj
     */
    void updateUpperInfo(ExhibitEntity obj);

    /**
     * 根据searilid查询涉案id
     *
     * @param serialID
     * @return
     */
    ExhibitEntity selectphotoinfo(String serialID);

    /**
     * 增加涉案物品照片
     *
     * @param obj
     */
    void creatbelongphoto(ExhibitEntity obj);

    /**
     * 根据searilid查询涉案物品总数
     *
     * @param serialID
     * @return
     */
    int selectpinfo(String serialID);

    /**
     * 根据涉案存物id修改涉案照片
     *
     * @param obj
     */
    void updatebelongphoto(ExhibitEntity obj);

    /**
     * 根据柜门id查询searilid
     *
     * @param lockId
     * @return
     */
    long getSerialIdByLockId(String lockId);

    /**
     * 根据id选柜号,ip.groupw.xb
     *
     * @param lockid
     * @return
     */
    ExhibitEntity selectlockNo(int lockid);

    /**
     * 根据唯一编号选柜号,ip.groupw.xb
     *
     * @param sid
     * @return
     */
    ExhibitEntity selectlockNobysid(int sid);

    /**
     * 根据searilId查询未领取的涉案物品信息
     *
     * @param paramMap
     * @return
     */
    ExhibitEntity selectSidnfo(Map<String, Object> paramMap);

    /**
     * 根据存物id插叙你涉案物品照片
     *
     * @param exhibitId
     * @return
     */
    List<ExhibitPhotoEntity> selectPhotoByExhibitId(long exhibitId);

    /**
     * 根据存物id查询serialid
     *
     * @param s
     * @return
     */
    Long selectExhibitInfoById(int s);
    /**
     * 查询嫌疑人所使用柜子信息
     * @param map
     * @return
     */
    List<ExhibitEntity> listAllExhibitByLocker(Map<String, Object> map);
    /**
     * 查询随身物品开柜记录
     * @param map3
     * @return
     */
    List<ExhibitEntity> queryExhibitCabinetLlog(Map<String, Object> map3);
    /**
     * 随身物品开柜记录总数
     * @param map3
     * @return
     */
    int countyExhibitCabinetLog(Map<String, Object> map3);
    /**
     * 导出随身储物柜的储物数据
     * @param paramMap
     * @return
     */
    List<ExhibitEntity> getExhibitForExport(Map<String, Object> paramMap);
    /**
     * 查询嫌疑人存取物信息
     * @param entity @return
     */
    List<ExhibitEntity> getDocInfo(ExhibitEntity entity);
    /**
     * 查询嫌疑人具体存物信息
     * @param entity
     * @return
     */
    List<ExhibitEntity> getDocInfoNoLockers(ExhibitEntity entity);
}