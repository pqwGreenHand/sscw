package com.zhixin.zhfz.bacs.dao.waitingmanage;

import com.zhixin.zhfz.bacs.entity.RecordEntity;
import com.zhixin.zhfz.bacs.entity.WaitingPersonCountEntity;
import com.zhixin.zhfz.bacs.entity.WaitingRecordEntity;

import java.util.List;
import java.util.Map;

public interface IWaitingManageMapper {


    List<WaitingRecordEntity> getWaitingRecordBySerialId(WaitingRecordEntity temWaitingRecord);

    List<WaitingRecordEntity> queryWaitRecord(Long serialId);

    List<WaitingPersonCountEntity> personRoomCountList(Map<String,Object> map);

    Integer selectPeopleCountTypeKy(Map<String,Object> map);

    Integer selectPeopleCountTypeSx(Map<String,Object> map);

    Integer selectPeopleCountTypeZq(Map<String,Object> map);

    Integer selectPeopleCountTypeRj(Map<String,Object> map);

    Integer selectPeopleCountTypeLs(Map<String,Object> map);

    Integer selectPeopleCountTypeOther(Map<String,Object> map);

    WaitingRecordEntity queryBySerialId(String serialId);

    void updateWaitingRecord(WaitingRecordEntity entity);

    void updateShowBySerialId(String serialId);

    int insertWaitingRecord(WaitingRecordEntity record);

     List<RecordEntity> getInquestRecord(Map<String, Object> map);

     List<WaitingRecordEntity> selectAllSuspectOfRoom(Map<String,Object> map);

    void changeRoom(Map<String,Object> map);

    WaitingRecordEntity queryById(Integer id);

    void saveDirection(Map<String, Object> map);

    WaitingRecordEntity getWaitingRecordById(String id);

    void updateStatusByIdForGoBack(String id);

    List<WaitingRecordEntity> listRecord(Map<String, Object> map);

    Integer listRecordCount(Map<String, Object> map);

    List<WaitingPersonCountEntity> queryRoomSameCase(Map<String, Object> map);

   WaitingRecordEntity getWaitingRecordBySerialId2(WaitingRecordEntity temWaitingRecord);

   Integer selectCountIsJuvenile(Map<String, Object> map);

   Integer selectCountIsDisease(Map<String,Object> map);

   Integer selectCountIsGravida(Map<String,Object> map);

    Integer selectCountWaitingRoom(Map<String,Object> map);

    Integer selectCountshedu(Map<String,Object> map);

    Integer selectCountZhongdian(Map<String,Object> map);

    List<WaitingRecordEntity> selectIsJuvenileAll(Map<String,Object> map); // 未成年人

    List<WaitingRecordEntity> selectIsDiseaseAll(Map<String,Object> map); // 传染病

    List<WaitingRecordEntity> selectIsGravidaAll(Map<String,Object> map); // 孕妇

    List<WaitingRecordEntity> selectWaitingRoomAll(Map<String,Object> map); // 侯问室嫌疑人

    List<WaitingRecordEntity> selectsheduAll(Map<String,Object> map); // 涉毒

    List<WaitingRecordEntity> selectZhongdianAll(Map<String,Object> map); // 重点嫌疑人

    WaitingRecordEntity queryRoomStatusBySerialId(Map<String,Object> map);

	int queryLastRoom(Map<String, Object> map);

    List<Map<String, Object>> queryRecord(int sId);

    int queryStatusBySid(Map<String, Object> map);
}
