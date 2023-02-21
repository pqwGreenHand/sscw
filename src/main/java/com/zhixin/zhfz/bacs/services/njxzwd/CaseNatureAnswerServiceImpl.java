package com.zhixin.zhfz.bacs.services.njxzwd;

import com.zhixin.zhfz.bacs.dao.njxzwd.ICaseNatureAnswerMapper;
import com.zhixin.zhfz.bacs.entity.CaseNatureAnswerEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @program: zhfz
 * @description: 案件性质答案service实现
 * @author: jzw
 * @create: 2019-02-22 16:50
 **/
@Service
public class CaseNatureAnswerServiceImpl implements ICaseNatureAnswerService {

    @Autowired
    private ICaseNatureAnswerMapper dao;

    @Override
    public List<CaseNatureAnswerEntity> list(Map<String, Object> map) {
        return dao.list(map);
    }

    @Override
    public int count(Map<String, Object> map) {
        return dao.count(map);
    }

    @Override
    public void insert(CaseNatureAnswerEntity entity) throws Exception {
        dao.insert(entity);
    }

    @Override
    public void update(CaseNatureAnswerEntity entity) throws Exception {
        dao.update(entity);
    }

    @Override
    public void delete(Long id) throws Exception {
        dao.delete(id);
    }
}
