package com.zhixin.zhfz.bacs.dao.blnx;

import com.zhixin.zhfz.bacs.entity.RecordTypeQuestionEntity;

import java.util.List;
import java.util.Map;

/**
 * @program: zhfz
 * @description: 笔录类型问题mapper
 * @author: jzw
 * @create: 2019-02-22 14:30
 **/

public interface IRecordTypeQuestionMapper {

    List<RecordTypeQuestionEntity> list(Map<String,Object> params);

    int count(Map<String,Object> params);

    void insert(RecordTypeQuestionEntity entity) throws Exception;

    void update(RecordTypeQuestionEntity entity) throws Exception;

    void delete(Long id) throws Exception;


}
