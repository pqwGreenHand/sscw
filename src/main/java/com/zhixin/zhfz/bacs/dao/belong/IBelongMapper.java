package com.zhixin.zhfz.bacs.dao.belong;

import com.zhixin.zhfz.bacs.entity.BelongEntity;
import com.zhixin.zhfz.bacs.entity.BelongingsPhotoEntity;
import com.zhixin.zhfz.bacs.entity.BelongingsPhotoTempEntity;
import com.zhixin.zhfz.bacs.entity.ComboboxEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface IBelongMapper {
    /**
     * 查询嫌疑人所使用柜子信息
     * @param map
     * @return
     */
    List<BelongEntity> listAllBelong(Map<String, Object> map);

    /**
     * 查询嫌疑人所用存物柜的总数
     * @param params
     * @return
     */
    int count(Map<String, Object> params);

    /**
     * 增加随身物品储存信息
     * @param entity
     */
    void insertBelong(BelongEntity entity);

    /**
     * 根据id修改随身储物数据
     * @param entity
     */
    void updateBelong(BelongEntity entity);

    /**
     * 根据id删除随身物品储存信息
     * @param id
     */
    void deleteBelong(Long id);

    /**
     * 根据储物柜编号查询未领取的存物信息
     * @param map
     * @return
     */
    BelongEntity selectinfo(Map<String, Object> map);

    /**
     * 根据searilId修改存物主表信息
     * @param entity
     */
    void updateBoxopen(BelongEntity entity);

    /**
     * 根据searilId修改未领取的存物主表信息
     * @param entity
     */
    void updateBoxopenout(BelongEntity entity);

    /**
     * 全部取物时根据唯一编号查询log所需信息
     * @param s
     * @return
     */
    List<BelongEntity> selectloginfo(Long s);

    /**
     * 根据存物详情id查询未领取的存物详情总数
     * @param s
     * @return
     */
    int selectbelonginfo(int s);

    /**
     * 查询嫌疑人存取物信息
     * @param entity
     * @return
     */
    List<BelongEntity> getDocInfo(BelongEntity entity);

    /**
     * 查询嫌疑人具体存物信息
     * @param entity
     * @return
     */
    List<BelongEntity> getDocInfoNoLockers(BelongEntity entity);

    /**
     * 查询嫌疑人涉案存物信息
     * @param entity
     * @return
     */
    List<BelongEntity> getexDocInfo(BelongEntity entity);

    /**
     * 根据searlid查询未领取的
     * @param serialID
     * @return
     */
    BelongEntity selectphotoinfo(String serialID);

    /**
     * 增加随身物品照片
     * @param obj
     */
    void creatbelongphoto(BelongEntity obj);

    /**
     * 根据searilId查询其下照片总数
     * @param serialID
     * @return
     */
    int selectpinfo(String serialID);

    /**
     * 修改随身物品照片
     * @param obj
     */
    void updatebelongphoto(BelongEntity obj);

    /**
     * 根据储物柜编号查询没有领取的存物信息的searilId
     * @param lockId
     * @return
     */
    long getSerialIdByLockId(String lockId);

    /**
     * 根据id选柜号,ip.groupw.xb
     * @param lockid
     * @return
     */
    BelongEntity selectlockNo(int lockid);

    /**
     * 根据唯一编号选柜号,ip.groupw.xb
     * @param sid
     * @return
     */
    BelongEntity selectlockNobysid(int sid);

    /**
     * 根据dearilId查询未领取的总数
     * @param s
     * @return
     */
    int isGetBelongBySerialId(long s);

    /**
     * 根据存物id查询入区主表seariId
     * @param obj
     * @return
     */
    Long selectserialid(BelongEntity obj);

    /**
     * 根据searilId查询未领取的存物信息
     * @param paramMap
     * @return
     */
    BelongEntity selectSidnfo(Map<String, Object> paramMap);

    /**
     * 根据存物id查询照片信息
     * @param belongingsId
     * @return
     */
    List<BelongingsPhotoEntity> selectPhotoByBelongingsId(long belongingsId);

    /**
     * 根据储物柜编号查询未领取的涉案物品信息
     * @param lockId
     * @return
     */
    BelongEntity getBelongInfoByLockId(String lockId);

    /**
     * 检查当前柜子是否被其他人占用
     */
    BelongEntity checkOtherPersonUse(Map<String, Object> map);

    /**
     * 根据id查询全部数据
     * @param id
     * @return
     */
    BelongEntity getBelongById(Integer id);

    /**
     * 根据未领取的储物柜编号查询总数量
     * @param lockerId
     * @return
     */
    int countDetByLockerId(int lockerId);

    /**
     * 导出随身储物柜的储物数据
     * @param paramMap
     * @return
     */
    List<BelongEntity> getBelongForExport(Map<String, Object> paramMap);

    /**
     * 修改存物信息
     * @param obj
     */
    void updateUpperInfo(BelongEntity obj);

    /**
     * 查询空的随身储物柜
     * @param params
     * @return
     */
    List<ComboboxEntity> listunUsedbox(Map<String, Object> params);

    BelongEntity selectBelonginfo(Map<String, Object> map);

    List<BelongEntity> getBelongDetailById(Integer bid);

    BelongEntity getBelongdetByWpUuid(Map<String, Object> map);

    List<BelongEntity> getDocInfoNew(BelongEntity entity);

    List<BelongEntity> getDocInfoNoLockersNew(BelongEntity entity);

    /**
     * 查询所有物品信息
     * @param map
     * @return
     */
    List<BelongEntity> getListByBelongDetail(Map<String, Object> map);

    Map<String,Object> selectImage(Map<String, Object> fileMap);

    List<BelongEntity> selectcodinfoNew(Map<String, Object> fileMap);

    BelongEntity selectphotoinfonew(String serialID);
}