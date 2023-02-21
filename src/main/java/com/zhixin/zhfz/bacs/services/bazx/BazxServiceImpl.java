package com.zhixin.zhfz.bacs.services.bazx;

import com.zhixin.zhfz.bacs.bazxDao.IBazxMapper;
import com.zhixin.zhfz.bacs.entity.InterrogateCaseEntity;
import com.zhixin.zhfz.bacs.entity.PersonEntity;
import com.zhixin.zhfz.bacs.entity.SerialEntity;
import com.zhixin.zhfz.common.entity.CaseEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class BazxServiceImpl implements IBazxService {

    @Resource
    private IBazxMapper bazxMapper;

    @Override
    public List<Map<String, Object>> queryAjxx(Map<String, Object> param) {
        return bazxMapper.queryAjxx(param);
    }

    @Override
    public Integer queryAjxxCount(Map<String, Object> param) {
        return bazxMapper.queryAjxxCount(param);
    }

    @Override
    public List<Map<String, Object>> queryPerson(Map<String, Object> param) {
        return bazxMapper.queryPerson(param);
    }

    @Override
    public List<Map<String, Object>> queryBelong(Map<String, Object> param) {
        return bazxMapper.queryBelong(param);
    }

    @Override
    public InterrogateCaseEntity queryAjxxById(Integer caseId) {
        return bazxMapper.queryAjxxById(caseId);
    }

    @Override
    public SerialEntity querySerialById(Long serialId) {
        return bazxMapper.querySerialById(serialId);
    }

    @Override
    public PersonEntity getPersonById(Long personId) {
        return bazxMapper.getPersonById(personId);
    }
}
