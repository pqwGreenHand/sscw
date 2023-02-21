package com.zhixin.zhfz.bacs.dao.arraign;

import com.zhixin.zhfz.bacs.entity.ArraignEntity;
import com.zhixin.zhfz.bacs.entity.RoomAssignEntity;
import com.zhixin.zhfz.bacs.form.ArraignForm;

import java.util.List;
import java.util.Map;

/**
 * 提讯管理
 */
public interface IArraignMapper {

    /**
     * 当前用户区域下所有房间
     * @param params
     * @return
     */
    List<ArraignEntity> all(Map<String,Object> params);

    List<ArraignEntity> roomAll(Map<String,Object> params);

    List<ArraignEntity> personAll(Map<String,Object> param);

    void arraign(ArraignForm form);

    void insertAssign(RoomAssignEntity entity);

    void updateAssign(RoomAssignEntity entity);

    Long getMaxId();

    List<ArraignEntity> listAllRecordRoom(Map<String,Object> params);

    List<ArraignEntity> listAllRecordRoom1(Map<String,Object> params);

    void updateRoom(ArraignEntity entity);

    List<ArraignEntity> queryRoomByPerson(Map<String, Object> param);

    List<ArraignEntity> queryRoomInfo(Map<String, Object> map);

    void updateRoomInfo(Map<String, Object> map);
}
