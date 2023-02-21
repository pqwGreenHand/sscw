package com.zhixin.zhfz.bacs.dao.record;


import com.zhixin.zhfz.bacs.entity.RecordEntity;

import java.util.List;
import java.util.Map;

/**
 * @program: zhfz
 * @description: 医疗救助mapper
 * @author: jzw
 * @create: 2019-02-22 16:19
 **/

public interface IRecordMapper {

    List<RecordEntity> list(Map<String, Object> map);

    RecordEntity getRecordInfo(Long id);

    int count(Map<String, Object> map);

    String getRecordingRoomName(Map<String, Object> map);

    void insert(RecordEntity entity) throws Exception;

    Long getMaxId();

    void update(RecordEntity entity) throws Exception;

    void delete(Long id) throws Exception;

    int getSerialRecording(Long serialId);

    int queryNextCount(Long serialId);

    int getRoomRecording(Map<String, Object> map);

    String getRoomCameraNo(Map<String, Object> map);

    void updateStatus(Map<String, Object> map);

    Map<String, String> getRecordIps(Long recordId);

    List<RecordEntity> selectBySerialId(Map<String, Object> map);

    /**
     * 根据serialId和personid判断嫌疑人是否已审讯
     *
     * @param map
     * @return
     */
    List<RecordEntity> selectBySerialIdAndPersonId(Map<String, Object> map);

    List<RecordEntity> getRecordRoomInfoBySerialId(Map<String, Object> map) throws Exception;

    void updateStatusNew(RecordEntity entity);

    List<RecordEntity> listRecordQuartz() throws Exception;

    void updateStatusQuartz(RecordEntity obj);
}
