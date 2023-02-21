package com.zhixin.zhfz.bacs.services.njxzwd;

import com.zhixin.zhfz.bacs.dao.njxzwd.ICaseNatureAnswerMapper;
import com.zhixin.zhfz.bacs.dao.njxzwd.ICaseNatureQuestionMapper;
import com.zhixin.zhfz.bacs.entity.CaseNatureQuestionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @program: zhfz
 * @description: 案件性质问题service实现
 * @author: jzw
 * @create: 2019-02-22 15:06
 **/

@Service
public class CaseNatureQuestionServiceImpl implements ICaseNatureQuestionService {

    @Autowired
    private ICaseNatureQuestionMapper questionMapper;

    @Autowired
    private ICaseNatureAnswerMapper answerMapper;

    @Override
    public List<CaseNatureQuestionEntity> list(Map<String, Object> map) {
        return questionMapper.list(map);
    }

    @Override
    public int count(Map<String, Object> map){
        return questionMapper.count(map);
    }

    @Override
    public void insert(CaseNatureQuestionEntity entity) throws Exception {
        questionMapper.insert(entity);
    }

    @Override
    public void update(CaseNatureQuestionEntity entity) throws Exception {
        questionMapper.update(entity);
    }

    @Override
    public void delete(Long id) throws Exception {
        answerMapper.deleteByQuestionId(id);
        questionMapper.delete(id);
    }
}
