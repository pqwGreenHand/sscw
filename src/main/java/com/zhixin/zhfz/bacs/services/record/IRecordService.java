package com.zhixin.zhfz.bacs.services.record;

import com.zhixin.zhfz.bacs.entity.RecordEntity;

import java.util.List;
import java.util.Map;

/**
 * @program: zhfz
 * @description: 审讯
 * @author: jzw
 * @create: 2019-02-21 11:20
 **/

public interface IRecordService {

    /**
     * @return java.util.List<com.zhixin.zhfz.bacs.entity.RecordEntity>
     * @Author jzw
     * @Description 查询及条件查询
     * @Date 11:29 2019/2/21
     * @Param [map]
     **/
    List<RecordEntity> list(Map<String, Object> map) throws Exception;

    /**
     * @return int
     * @Author jzw
     * @Description 分页
     * @Date 11:30 2019/2/21
     * @Param [map]
     **/
    int count(Map<String, Object> map) throws Exception;

    String getRecordingRoomName(Map<String, Object> map) throws Exception;

    /*
     * @Author jzw
     * @Description 删除
     * @Date 11:31 2019/2/21
     * @Param [id]
     * @return void
     **/
    void delete(Long id) throws Exception;

    /**
     * @return void
     * @Author jzw
     * @Description 插入
     * @Date 11:32 2019/2/21
     * @Param [entity]
     **/
    RecordEntity insert(RecordEntity entity) throws Exception;


    Long getMaxId();

    /**
     * @return void
     * @Author jzw
     * @Description 更新
     * @Date 15:57 2019/2/21
     * @Param [form]
     **/
    void update(RecordEntity entity) throws Exception;


    int getSerialRecording(Long serialId);

    Map<String, String> getRecordIps(Long recordId);

    int queryNextCount(Long serialId);

    int getRoomRecording(Map<String, Object> map);

    void updateStatus(Map<String, Object> map) throws Exception;

    RecordEntity getRecordInfo(Long id);

    String getRoomCameraNo(Map<String, Object> map);

    List<RecordEntity> selectBySerialId(Map<String, Object> map);

    List<RecordEntity> getRecordRoomInfoBySerialId(Map<String, Object> map) throws Exception;

    /**
     * 根据serialId和personid判断嫌疑人是否已审讯
     *
     * @param map
     * @return
     */
    List<RecordEntity> selectBySerialIdAndPersonId(Map<String, Object> map);

    void updateStatusNew(RecordEntity entity);

    List<RecordEntity> listRecordQuartz() throws Exception;

    void updateStatusQuartz(RecordEntity obj);
}
