package com.zhixin.zhfz.bacs.dao.serial;


import com.zhixin.zhfz.bacs.entity.SerialEntity;

import java.util.List;
import java.util.Map;

public interface ISerialMapper {

    List<SerialEntity> list(Map<String, Object> params) throws Exception;

    int count(Map<String, Object> params) throws Exception;

    List<SerialEntity> timeoutRecodelist(Map<String, Object> params) throws Exception;

    int countTimeoutRecode(Map<String, Object> params) throws Exception;

    void updateStatusById(Map<String, Object> id) throws Exception;

    SerialEntity getSerialByNo(SerialEntity entity1);

    SerialEntity getSerialByNo1(SerialEntity entity1);

    SerialEntity getSerialById(SerialEntity entity);

    void changestatus(SerialEntity entity);

    List<SerialEntity> getSerialByCuffNo(Map<String, Object> params);

    SerialEntity queryBySid(Long sid);

    Integer insert(SerialEntity entity) throws Exception;

    SerialEntity findserialbyNo(String serialNo);

    SerialEntity getSerialById2(SerialEntity entity);

    List<SerialEntity> getIdentifyPersonList(Map<String, Object> params);

    List<SerialEntity> getPersonnelList(Map<String, Object> map);

    int getPersonnelListCount(Map<String, Object> params) throws Exception;

    List<SerialEntity> getCaseIdByOrderId(int orderId) throws Exception;

    void updateSerialById(SerialEntity entity) throws Exception;

    List<SerialEntity> otherPersonList(Map<String, Object> map);

    int otherPersonCount(Map<String, Object> params) throws Exception;

    List<SerialEntity> otherPersonList2(Map<String, Object> map);

    int otherPersonCount2(Map<String, Object> params) throws Exception;


    List<SerialEntity> listAllLawDoc(Map<String, Object> map);

    int countAllLawDoc(Map<String, Object> map);

    SerialEntity outGetSerialByNo(SerialEntity entity);


    SerialEntity selectByUrinalysisPhotoId(Long urinalysisId);

    SerialEntity GetSerialDetailInfoById(Long serialId);

    SerialEntity GetSerialInfoByNo(SerialEntity serialEntity);

    SerialEntity inGetSerialByNo(SerialEntity serial);

    void exit(SerialEntity entity) throws Exception;

    void confirm(SerialEntity entity) throws Exception;

    List<SerialEntity> suspectlist(Map<String, Object> params) throws Exception;

    int suspectcount(Map<String, Object> params) throws Exception;

    List<SerialEntity> otherlist(Map<String, Object> params) throws Exception;

    /**
     * 羁押超时告警查询
     *
     * @param params
     * @return
     * @throws Exception
     */
    List<SerialEntity> listOver23Hours(Map<String, Object> params) throws Exception;

    int othercount(Map<String, Object> params) throws Exception;
    //根据预约id和人员id判断嫌疑人是否入区
    List<SerialEntity>  selectByOrderIdAndPersonId(Map<String, Object> params);

    void updateAppSerialById(SerialEntity entity);

    void updateSerialById2(SerialEntity entity) throws Exception;

    SerialEntity queryNatureByAb(Map<String, Object> params);

    SerialEntity queryOrgIdByPoliceNo(Map<String, Object> params);

    List<SerialEntity> querySerialBySendUserId(Map<String, Object> map);

    int queryStatusById(Map<String, Object> map);

    List<SerialEntity> selectByCaseAndPerson(Map<String, Object> params) throws Exception;
    
    List<SerialEntity> listWarnTime(Map<String, Object> params);
    
    void batchConfirmByIds(Map<String, Object> params);
    
    void updateSftjclByid(Map<String, Object> params);

    void update(SerialEntity serialEntity);

    List<SerialEntity> listJyOutTime(Map<String, Object> param);

    int countJyOutTime(Map<String, Object> param);
}