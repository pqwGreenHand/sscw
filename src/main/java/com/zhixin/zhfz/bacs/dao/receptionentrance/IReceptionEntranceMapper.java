package com.zhixin.zhfz.bacs.dao.receptionentrance;

import com.zhixin.zhfz.bacs.entity.ReceptionEntranceEntity;

import java.util.List;
import java.util.Map;

public interface IReceptionEntranceMapper {

    List<ReceptionEntranceEntity> list2(Map<String, Object> map);

    int count(Map<String, Object> map);

    void receptionInsert(ReceptionEntranceEntity data);

    List<ReceptionEntranceEntity> list(Map<String, Object> params);

    void updateOutTime(String id);

    List<ReceptionEntranceEntity> receptionHistoryList(Map<String, Object> map);

    int count2(Map<String, Object> map);

    List<ReceptionEntranceEntity> findById(String id);

    void updateById(ReceptionEntranceEntity data);
}
