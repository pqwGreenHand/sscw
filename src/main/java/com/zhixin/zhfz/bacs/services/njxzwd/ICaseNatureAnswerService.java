package com.zhixin.zhfz.bacs.services.njxzwd;

import com.zhixin.zhfz.bacs.entity.CaseNatureAnswerEntity;

import java.util.List;
import java.util.Map;

/**
 * @program: zhfz
 * @description: 案件性质答案service接口
 * @author: jzw
 * @create: 2019-02-22 16:46
 **/

public interface ICaseNatureAnswerService {

    /**
     * @Author jzw
     * @Description 列表
     * @Date 16:48 2019/2/22
     * @Param [map]
     * @return java.util.List<com.zhixin.zhfz.bacs.entity.CaseNatureAnswerEntity>
     **/
    List<CaseNatureAnswerEntity> list(Map<String, Object> map);

    /**
     * @Author jzw
     * @Description 总数
     * @Date 16:48 2019/2/22
     * @Param [map]
     * @return int
     **/
    int count(Map<String, Object> map);

    /**
     * @Author jzw
     * @Description 新增
     * @Date 16:49 2019/2/22
     * @Param [entity]
     * @return void
     **/
    void insert(CaseNatureAnswerEntity entity) throws Exception;

    /**
     * @Author jzw
     * @Description 更新
     * @Date 16:49 2019/2/22
     * @Param [entity]
     * @return void
     **/
    void update(CaseNatureAnswerEntity entity) throws Exception;

    /**
     * @Author jzw
     * @Description 删除
     * @Date 16:49 2019/2/22
     * @Param [id]
     * @return void
     **/
    void delete(Long id) throws Exception;

}
