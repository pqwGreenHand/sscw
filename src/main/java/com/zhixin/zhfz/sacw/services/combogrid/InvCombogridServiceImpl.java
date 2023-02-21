package com.zhixin.zhfz.sacw.services.combogrid;


import com.zhixin.zhfz.bacs.entity.CombogridEntity;
import com.zhixin.zhfz.sacw.dao.combogrid.IInvCombogridMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class InvCombogridServiceImpl implements IInvCombogridService {

    @Autowired
    private IInvCombogridMapper combogridMapper;

    @Override
    public List<CombogridEntity> getAreaName(Map<String, Object> map) throws Exception {
        return combogridMapper.getAreaName(map);
    }

    @Override
    public List<CombogridEntity> getLawCase(Map<String, Object> map) throws Exception {
        return combogridMapper.getLawCase(map);
    }

    @Override
    public List<CombogridEntity> getLawCaseForInv(Map<String, Object> map) throws Exception {
        // TODO Auto-generated method stub
        return combogridMapper.getLawCaseForInv(map);
    }

    @Override
    public List<CombogridEntity> getLawCaseId(Map<String, Object> map) throws Exception {
        return combogridMapper.getLawCaseId(map);
    }
}

