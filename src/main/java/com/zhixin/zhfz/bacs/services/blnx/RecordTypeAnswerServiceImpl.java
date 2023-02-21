package com.zhixin.zhfz.bacs.services.blnx;

import com.zhixin.zhfz.bacs.dao.blnx.IRecordTypeAnswerMapper;
import com.zhixin.zhfz.bacs.entity.RecordTypeAnswerEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @program: zhfz
 * @description: 笔录类型答案service实现
 * @author: jzw
 * @create: 2019-02-22 16:50
 **/
@Service
public class RecordTypeAnswerServiceImpl implements IRecordTypeAnswerService{

    @Autowired
    private IRecordTypeAnswerMapper dao;

    @Override
    public List<RecordTypeAnswerEntity> list(Map<String, Object> map) {
        return dao.list(map);
    }

    @Override
    public int count(Map<String, Object> map) {
        return dao.count(map);
    }

    @Override
    public void insert(RecordTypeAnswerEntity entity) throws Exception {
        dao.insert(entity);
    }

    @Override
    public void update(RecordTypeAnswerEntity entity) throws Exception {
        dao.update(entity);
    }

    @Override
    public void delete(Long id) throws Exception {
        dao.delete(id);
    }
}
