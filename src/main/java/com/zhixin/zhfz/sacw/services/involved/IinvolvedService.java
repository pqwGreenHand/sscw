package com.zhixin.zhfz.sacw.services.involved;


import com.zhixin.zhfz.sacw.entity.InputRecordEntity;
import com.zhixin.zhfz.sacw.entity.InvolvedEntity;

import java.util.List;
import java.util.Map;

public interface IinvolvedService {

    Integer countAllInvolved() throws Exception;

    List<InvolvedEntity> getMaterials(Map<String, Object> map) throws Exception;

    Integer countMaterials(Map<String, Object> map) throws Exception;

    List<InvolvedEntity> getRecordByInvId(Map<String, Object> map) throws Exception;

    List<InvolvedEntity> getRecordByTime(Map<String, Object> map) throws Exception;

    InvolvedEntity getInvolvedById(Long id) throws Exception;

    List<InvolvedEntity> getRecordByCaseId(int caseId) throws Exception;

    void addInv(InvolvedEntity involved) throws Exception;

    void update(InvolvedEntity involved) throws Exception;


    List<InvolvedEntity> listInRecordPhoto(Map<String, Object> map) throws Exception;

    void updateAllInvolvedStatus(Map<String, Object> map) throws Exception;

    void updateAllInvolvedOutputNum(Map<String, Object> map) throws Exception;

    void updateStatus(Map<String, Object> map) throws Exception;


    Integer countRecordByInvId(Map<String, Object> map) throws Exception;

    void deleteInvolved(int id) throws Exception;


    List<InvolvedEntity> getUnStockedRecordByCaseId(int caseId) throws Exception;

    void updateStatusById(Map<String, Object> map) throws Exception;

    //查询物品的最后一条入库记录
    List<InvolvedEntity> listLastRecord(Map<String, Object> map) throws Exception;

    //更改物品位置
    void changePosition(Map<String, Object> map) throws Exception;

    /**
     * 根据id查询单个入库记录
     *
     * @return
     * @throws Exception
     */
    List<InputRecordEntity> listInRecordById(Map<String, Object> map) throws Exception;

}
