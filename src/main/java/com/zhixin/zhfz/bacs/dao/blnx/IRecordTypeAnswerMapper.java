package com.zhixin.zhfz.bacs.dao.blnx;

import com.zhixin.zhfz.bacs.entity.RecordTypeAnswerEntity;

import java.util.List;
import java.util.Map;

/**
 * @program: zhfz
 * @description: 笔录类型答案mapper
 * @author: jzw
 * @create: 2019-02-22 16:19
 **/

public interface IRecordTypeAnswerMapper {

    List<RecordTypeAnswerEntity> list(Map<String,Object> map);

    int count(Map<String,Object> map);

    void insert(RecordTypeAnswerEntity entity) throws Exception;

    void update(RecordTypeAnswerEntity entity) throws Exception;

    void delete(Long id) throws Exception;

    void deleteByQuestionId(Long id) throws Exception;


}
