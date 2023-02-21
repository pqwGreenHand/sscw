package com.zhixin.zhfz.bacs.dao.blwmb;


import com.zhixin.zhfz.bacs.entity.RecordTemplateEntity;

import java.util.List;
import java.util.Map;

/**
 * @program: zhfz
 * @description: 笔录word模板dao层接口
 * @author: jzw
 * @create: 2019-02-21 10:46
 **/

public interface IRecordTemplateMapper {

    List<RecordTemplateEntity> list(Map<String,Object> params) throws Exception;

    int count(Map<String,Object> params) throws Exception;

    void insert(RecordTemplateEntity entity) throws Exception;

    void update(RecordTemplateEntity entity) throws Exception;

    void delete(Long id) throws Exception;

    RecordTemplateEntity getWordById(Long id) throws Exception;
}
