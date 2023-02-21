package com.zhixin.zhfz.bacs.dao.record;

import com.zhixin.zhfz.bacs.entity.LedEntity;
import com.zhixin.zhfz.bacs.entity.RecordEntity;

import java.util.List;
import java.util.Map;

public interface RecordDataMapper {
    /**
     * 更新笔录的状态
     *
     * @throws Exception
     */
    void updateRecordStatus(Map<String, Object> map) throws Exception;
    /**
     * 提交笔录时更新房间的状态
     *
     * @throws Exception
     */
    void updateRoom(Map<String, Object> map) throws Exception;
    /**
     * 查询笔录
     *
     * @return
     * @throws Exception
     */
    List<RecordEntity> getRecordInfo(Map<String, Object> map) throws Exception;
    /**
     * 查询LED信息
     *
     * @return
     * @throws Exception
     */
    List<LedEntity> getLedInfo(Map<String, Object> map) throws Exception;
}
