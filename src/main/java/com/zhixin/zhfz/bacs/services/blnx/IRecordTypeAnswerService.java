package com.zhixin.zhfz.bacs.services.blnx;

import com.zhixin.zhfz.bacs.entity.RecordTypeAnswerEntity;

import java.util.List;
import java.util.Map;

/**
 * @program: zhfz
 * @description: 笔录类型答案service接口
 * @author: jzw
 * @create: 2019-02-22 16:46
 **/

public interface IRecordTypeAnswerService {

    /**
     * @Author jzw
     * @Description 列表
     * @Date 16:48 2019/2/22
     * @Param [map]
     * @return java.util.List<com.zhixin.zhfz.bacs.entity.RecordTypeAnswerEntity>
     **/
    List<RecordTypeAnswerEntity> list(Map<String,Object> map);

    /**
     * @Author jzw
     * @Description 总数
     * @Date 16:48 2019/2/22
     * @Param [map]
     * @return int
     **/
    int count(Map<String,Object> map);

    /**
     * @Author jzw
     * @Description 新增
     * @Date 16:49 2019/2/22
     * @Param [entity]
     * @return void
     **/
    void insert(RecordTypeAnswerEntity entity) throws Exception;

    /**
     * @Author jzw
     * @Description 更新
     * @Date 16:49 2019/2/22
     * @Param [entity]
     * @return void
     **/
    void update(RecordTypeAnswerEntity entity) throws Exception;

    /**
     * @Author jzw
     * @Description 删除
     * @Date 16:49 2019/2/22
     * @Param [id]
     * @return void
     **/
    void delete(Long id) throws Exception;

}
