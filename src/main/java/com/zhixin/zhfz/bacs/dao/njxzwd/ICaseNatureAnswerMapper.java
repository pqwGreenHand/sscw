package com.zhixin.zhfz.bacs.dao.njxzwd;


import com.zhixin.zhfz.bacs.entity.CaseNatureAnswerEntity;

import java.util.List;
import java.util.Map;

/**
 * @program: zhfz
 * @description: 案件性质答案mapper
 * @author: jzw
 * @create: 2019-02-22 16:19
 **/

public interface ICaseNatureAnswerMapper {

    List<CaseNatureAnswerEntity> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    void insert(CaseNatureAnswerEntity entity) throws Exception;

    void update(CaseNatureAnswerEntity entity) throws Exception;

    void delete(Long id) throws Exception;

    void deleteByQuestionId(Long id) throws Exception;


}
