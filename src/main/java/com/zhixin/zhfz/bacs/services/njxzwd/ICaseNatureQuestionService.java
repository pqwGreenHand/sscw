package com.zhixin.zhfz.bacs.services.njxzwd;


import com.zhixin.zhfz.bacs.entity.CaseNatureQuestionEntity;

import java.util.List;
import java.util.Map;

/**
 * @program: zhfz
 * @description: 笔录类型问题service
 * @author: jzw
 * @create: 2019-02-22 14:54
 **/

public interface ICaseNatureQuestionService {

    /**
     * @Author jzw
     * @Description 分页列表
     * @Date 15:00 2019/2/22
     * @Param [map]
     * @return java.util.List<com.zhixin.zhfz.bacs.entity.CaseNatureQuestionEntity>
     **/
    List<CaseNatureQuestionEntity> list(Map<String, Object> map);

    /**
     * @Author jzw
     * @Description 列表总数
     * @Date 15:01 2019/2/22
     * @Param [map]
     * @return int
     **/
    int count(Map<String, Object> map);

    /**
     * @Author jzw
     * @Description 插入
     * @Date 15:02 2019/2/22
     * @Param [entity]
     * @return void
     **/
    void insert(CaseNatureQuestionEntity entity) throws Exception;

    /**
     * @Author jzw
     * @Description 更新
     * @Date 15:03 2019/2/22
     * @Param [entity]
     * @return void
     **/
    void update(CaseNatureQuestionEntity entity) throws Exception;

    /**
     * @Author jzw
     * @Description 删除
     * @Date 15:06 2019/2/22
     * @Param [id]
     * @return void
     **/
    void delete(Long id) throws Exception;

}
