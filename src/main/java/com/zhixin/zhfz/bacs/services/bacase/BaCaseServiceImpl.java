package com.zhixin.zhfz.bacs.services.bacase;

import com.zhixin.zhfz.bacs.dao.bacase.IBaCaseMapper;
import com.zhixin.zhfz.bacs.entity.BaCaseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BaCaseServiceImpl implements IBaCaseService {

   @Autowired
    private IBaCaseMapper baCaseMapper;


    @Override
    public List<BaCaseEntity> listAllLawPerson(Map<String, Object> map) {
        return baCaseMapper.listAllLawPerson(map);
    }

    @Override
    public int countAllLawPerson(Map<String, Object> map) {
        return baCaseMapper.countAllLawPerson(map);
    }
}
