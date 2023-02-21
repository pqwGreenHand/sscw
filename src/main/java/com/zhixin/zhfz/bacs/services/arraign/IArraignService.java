package com.zhixin.zhfz.bacs.services.arraign;


import com.zhixin.zhfz.bacs.entity.ArraignEntity;
import com.zhixin.zhfz.bacs.entity.RoomAssignEntity;
import com.zhixin.zhfz.bacs.form.ArraignForm;
import com.zhixin.zhfz.common.entity.MessageEntity;
import com.zhixin.zhfz.common.entity.UserEntity;
import com.zhixin.zhfz.sacw.form.UserNoSearchForm;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface IArraignService {

    List<ArraignEntity> all(Map<String,Object> params);

    RoomAssignEntity arraign(ArraignForm form) throws Exception;

    Long getMaxId();

    MessageEntity clear(Long roomId, Integer serialId, HttpServletRequest request) throws Exception;

    UserEntity checkPolceNo(UserNoSearchForm form) throws Exception;

    List<ArraignEntity> listAllRecordRoom(Map<String,Object> params);

    /**
     * 置空审讯室前查询房间是否在审讯中
     * @param params
     * @return
     */
    List<ArraignEntity> listAllRecordRoom1(Map<String,Object> params);

    void updateRoom(ArraignEntity entity);

    List<ArraignEntity> queryRoomByPerson(Map<String, Object> param);

    List<ArraignEntity> queryRoomInfo(Map<String, Object> map);

    void updateRoomInfo(Map<String, Object> map);
}
