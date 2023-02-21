package com.zhixin.zhfz.bacs.services.blnx;

import com.zhixin.zhfz.bacs.dao.blnx.IRecordTypeAnswerMapper;
import com.zhixin.zhfz.bacs.dao.blnx.IRecordTypeQuestionMapper;
import com.zhixin.zhfz.bacs.entity.RecordTypeQuestionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @program: zhfz
 * @description: 笔录类型问题service实现
 * @author: jzw
 * @create: 2019-02-22 15:06
 **/

@Service
public class RecordTypeQuestionServiceImpl implements IRecordTypeQuestionService {

    @Autowired
    private IRecordTypeQuestionMapper questionMapper;

    @Autowired
    private IRecordTypeAnswerMapper answerMapper;

    @Override
    public List<RecordTypeQuestionEntity> list(Map<String, Object> map) {
        return questionMapper.list(map);
    }

    @Override
    public int count(Map<String, Object> map){
        return questionMapper.count(map);
    }

    @Override
    public void insert(RecordTypeQuestionEntity entity) throws Exception {
        questionMapper.insert(entity);
    }

    @Override
    public void update(RecordTypeQuestionEntity entity) throws Exception {
        questionMapper.update(entity);
    }

    @Override
    public void delete(Long id) throws Exception {
        answerMapper.deleteByQuestionId(id);
        questionMapper.delete(id);
    }
}
