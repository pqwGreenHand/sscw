package com.zhixin.zhfz.zhag.services.xzajxx;

import com.zhixin.zhfz.zhag.dao.xzxsajxx.CriminalAndAdministrationCaseMapper;
import com.zhixin.zhfz.zhag.entity.CriminalAndAdministrationCaseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AdministrationCaseServiceImpl implements AdministrationCaseService{
    @Autowired
    private CriminalAndAdministrationCaseMapper caseMapper;

    @Override
    public List<CriminalAndAdministrationCaseEntity> listAdministrationCase(Map<String, Object> map) throws Exception {
        return caseMapper.lisAdministrationCase(map);
    }

    @Override
    public int listAdministrationCount(Map<String, Object> map) throws Exception {
        return caseMapper.listAdministrationCount(map);
    }
}
