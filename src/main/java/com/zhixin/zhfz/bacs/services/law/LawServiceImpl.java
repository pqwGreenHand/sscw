package com.zhixin.zhfz.bacs.services.law;

import com.zhixin.zhfz.bacs.dao.law.ILawMapper;
import com.zhixin.zhfz.bacs.entity.LawEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class LawServiceImpl implements ILawService{

    @Autowired
    private ILawMapper lawMapper;

    @Override
    public void insertLaw(LawEntity lawEntity) throws Exception {
        lawMapper.insertLaw(lawEntity);
    }

    @Override
    public List<LawEntity> list(Map<String, Object> map) throws Exception {
        return lawMapper.list(map);
    }

    @Override
    public int count(Map<String, Object> map) throws Exception {
        return lawMapper.count(map);
    }

    @Override
    public void updateLaw(LawEntity lawEntity) {
        lawMapper.updateLaw(lawEntity);
    }

    @Override
    public void removeLaw(String id) {
        lawMapper.removeLaw(id);
    }
}
