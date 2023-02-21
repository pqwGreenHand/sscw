package com.zhixin.zhfz.bacs.dao.qlywgzs;

import com.zhixin.zhfz.bacs.entity.RightsTemplateEntity;

import java.util.List;
import java.util.Map;

/**
 * @program: zhfz
 * @description: 笔录word模板dao层接口
 * @author: jzw
 * @create: 2019-02-21 10:46
 **/

public interface IRightsTemplateMapper {

    List<RightsTemplateEntity> list(Map<String, Object> params) throws Exception;

    int count(Map<String, Object> params) throws Exception;

    void insert(RightsTemplateEntity entity) throws Exception;

    void update(RightsTemplateEntity entity) throws Exception;

    void delete(Long id) throws Exception;

    RightsTemplateEntity getWordById(Long id) throws Exception;
}
