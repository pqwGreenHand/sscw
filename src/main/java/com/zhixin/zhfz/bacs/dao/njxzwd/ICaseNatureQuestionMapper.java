package com.zhixin.zhfz.bacs.dao.njxzwd;


import com.zhixin.zhfz.bacs.entity.CaseNatureQuestionEntity;

import java.util.List;
import java.util.Map;

/**
 * @program: zhfz
 * @description: 案件性质问题mapper
 * @author: jzw
 * @create: 2019-02-22 14:30
 **/

public interface ICaseNatureQuestionMapper {

    List<CaseNatureQuestionEntity> list(Map<String, Object> params);

    int count(Map<String, Object> params);

    void insert(CaseNatureQuestionEntity entity) throws Exception;

    void update(CaseNatureQuestionEntity entity) throws Exception;

    void delete(Long id) throws Exception;


}
