package com.zhixin.zhfz.sacw.services.involved;

import com.zhixin.zhfz.sacw.dao.involved.InvolvedMapper;
import com.zhixin.zhfz.sacw.entity.InputRecordEntity;
import com.zhixin.zhfz.sacw.entity.InvolvedEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class InvolvedServiceImpl implements IinvolvedService {

    @Autowired
    private InvolvedMapper mapper;

    @Override
    public Integer countAllInvolved() throws Exception {
        // TODO Auto-generated method stub
        return mapper.countAllInvolved();
    }

    @Override
    public List<InvolvedEntity> getMaterials(Map<String, Object> map) throws Exception {
        // TODO Auto-generated method stub
        return mapper.getMaterials(map);
    }

    @Override
    public Integer countMaterials(Map<String, Object> map) throws Exception {
        // TODO Auto-generated method stub
        return mapper.countMaterials(map);
    }

    @Override
    public List<InvolvedEntity> getRecordByInvId(Map<String, Object> map) throws Exception {
        // TODO Auto-generated method stub
        return mapper.getRecordByInvId(map);
    }

    @Override
    public List<InvolvedEntity> getRecordByTime(Map<String, Object> map) throws Exception {
        // TODO Auto-generated method stub
        return mapper.getRecordByTime(map);
    }

    @Override
    public InvolvedEntity getInvolvedById(Long id) throws Exception {
        // TODO Auto-generated method stub
        return mapper.getInvolvedById(id);
    }

    @Override
    public List<InvolvedEntity> getUnStockedRecordByCaseId(int caseId) throws Exception {
        // TODO Auto-generated method stub
        return mapper.getUnStockedRecordByCaseId(caseId);
    }

    @Override
    public void updateStatusById(Map<String, Object> map) throws Exception {
        mapper.updateStatusById(map);
    }

    @Override
    public List<InvolvedEntity> listLastRecord(Map<String, Object> map) throws Exception {
        return mapper.listLastRecord(map);
    }

    @Override
    public void changePosition(Map<String, Object> map) throws Exception {
        mapper.changePosition(map);
    }

    @Override
    public List<InputRecordEntity> listInRecordById(Map<String, Object> map) throws Exception {
        return mapper.listInRecordById(map);
    }

    @Override
    public void addInv(InvolvedEntity involved) throws Exception {
        // TODO Auto-generated method stub
        mapper.addInv(involved);
    }

    @Override
    public void update(InvolvedEntity involved) throws Exception {
        mapper.update(involved);
    }


    @Override
    public List<InvolvedEntity> listInRecordPhoto(Map<String, Object> map) throws Exception {
        return mapper.listInRecordPhoto(map);
    }

    @Override
    public void updateAllInvolvedStatus(Map<String, Object> map) throws Exception {
        mapper.updateAllInvolvedStatus(map);
    }

    @Override
    public void updateAllInvolvedOutputNum(Map<String, Object> map) throws Exception {
        mapper.updateAllInvolvedOutputNum(map);

    }

    @Override
    public void updateStatus(Map<String, Object> map) throws Exception {
        mapper.updateStatus(map);

    }

    public Integer countRecordByInvId(Map<String, Object> map) throws Exception {
        // TODO Auto-generated method stub
        return mapper.countRecordByInvId(map);
    }

    @Override
    public void deleteInvolved(int id) throws Exception {
        mapper.deleteInvolved(id);
    }

    @Override
    public List<InvolvedEntity> getRecordByCaseId(int caseId) throws Exception {
        return mapper.getRecordByCaseId(caseId);
    }


}
