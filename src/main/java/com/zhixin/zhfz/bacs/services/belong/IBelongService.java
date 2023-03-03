package com.zhixin.zhfz.bacs.services.belong;

import com.zhixin.zhfz.bacs.entity.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 随身物品
 */
public interface IBelongService {
    /**
     * 查询某存物柜的存物信息
     *
     * @param map
     * @return
     */
    List<BelongEntity> listAllBelongdet(Map<String, Object> map);

    /**
     * 添加存物信息
     *
     * @param entity
     * @return
     */
    int insertBelong(BelongEntity entity,HttpServletRequest request);


    /**
     * 修改存物详情
     *
     * @param entity
     */
    void updateBelongdet(BelongEntity entity);

    /**
     * 删除存物信息
     *
     * @param belongId
     * @param detailId
     */
    void deleteBelongdetAndBelong(int belongId, int detailId);

    /**
     * 增加图片上传记录
     *
     * @param serialID
     * @param path
     */
    void creatbelongphoto(String serialID, String path,String pid,String opUserId);

    /**
     * 根据柜门id查询柜门存储信息
     *
     * @param map
     * @return
     */
    List<BelongEntity> listAllBelongdetByLockerId(Map<String, Object> map);

    /**
     * 通过lockId 查询 serial_id
     *
     * @param lockId
     * @return
     */
    long getSerialIdByLockId(String lockId);

    /**
     * 随身物品开柜单保存(单个领取)----out----
     *
     * @param entity
     */
    void updateBoxopenouts(BelongEntity entity) throws Exception;

    /**
     * 增加开柜日志
     *
     * @param openCabinetEntity
     */
    void insertOpenboxData(OpenCabinetEntity openCabinetEntity);

    /**
     * 随身物品开柜单保存----out---- 全部提取
     *
     * @param entity
     */
    void updateBoxopenout(BelongEntity entity, HttpServletRequest request);

    /**
     * 根据存物id查询全部详情数量
     *
     * @param params
     * @return
     */
    int count1(Map<String, Object> params);

    /**
     * 查询空的随身储物柜
     *
     * @param params
     * @return
     */
    List<ComboboxEntity> listunUsedbox(Map<String, Object> params);

    /**
     * 查询所有存物
     *
     * @param map
     * @return
     */
    List<BelongEntity> listAllBelong(Map<String, Object> map);

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
    List<BelongEntity> queryBelongingsCabinetLlog(Map<String, Object> map3);

    /**
     * 随身物品开柜记录总数
     *
     * @param map3
     * @return
     */
    int countBelongingsCabinetLog(Map<String, Object> map3);

    List<BelongEntity> listAllBelongdet2(Map<String, Object> map);

    /**
     * 查询照片
     *
     * @param belongingsId
     * @return
     */
    List<BelongingsPhotoEntity> selectPhotoByBelongingsId(long belongingsId);

    public BelongEntity selectBelonginfo(Map<String, Object> map);
    
    /**
     * 获取所有
     * @param map
     * @return
     */
    List<BelongEntity> getDocInfo(BelongEntity entity);

    public BelongEntity getBelongdetById(Integer id);

    Long selectSerialid(Integer belongingsId);

    BelongEntity getBelongdetByWpUuid(Map<String, Object> map);


    /**
     * 查询所有物品信息
     *
     * @param areaId,str
     * @return
     */
    List<BelongEntity> getListByBelongDetail(Integer areaId, String str);

    Map<String,Object> selectImage(Map<String, Object> fileMap);

    List<BelongingsPhotoTempEntity> selectPhotoByTempId(Integer tempId);

    List<BelongEntity> addBelongcodNew(Map<String, Object> fileMap);

    List<Map<String,Object>> listCaseZfba(Map<String, Object> map);

    int listCaseZfbaCount(Map<String, Object> map);

    List<Map<String,Object>> listPersonZfba(Map<String, Object> map);
}
