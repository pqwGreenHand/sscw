package com.zhixin.zhfz.sacw.dao.involved;


import com.zhixin.zhfz.sacw.entity.InputRecordEntity;
import com.zhixin.zhfz.sacw.entity.InvolvedEntity;

import java.util.List;
import java.util.Map;

public interface InvolvedMapper {

    public Integer countAllInvolved() throws Exception;

    public List<InvolvedEntity> getMaterials(Map<String, Object> map) throws Exception;

    public Integer countMaterials(Map<String, Object> map) throws Exception;

    public List<InvolvedEntity> getRecordByInvId(Map<String, Object> map) throws Exception;

    public List<InvolvedEntity> getRecordByTime(Map<String, Object> map) throws Exception;

    public InvolvedEntity getInvolvedById(Long id) throws Exception;

    void deleteInvolved(int id) throws Exception;

    public void updateAllInvolvedStatus(Map<String, Object> map) throws Exception;

    public void updateAllInvolvedOutputNum(Map<String, Object> map) throws Exception;

    public List<InvolvedEntity> getRecordByCaseId(int caseId) throws Exception;

    public void addInv(InvolvedEntity involved) throws Exception;

    public void update(InvolvedEntity involved) throws Exception;

    public List<InvolvedEntity> listInRecordPhoto(Map<String, Object> map) throws Exception;

    public void updateStatus(Map<String, Object> map) throws Exception;

    public Integer countRecordByInvId(Map<String, Object> map) throws Exception;

    public List<InvolvedEntity> getUnStockedRecordByCaseId(int caseId);

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
