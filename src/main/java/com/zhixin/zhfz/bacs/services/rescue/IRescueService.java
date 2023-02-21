package com.zhixin.zhfz.bacs.services.rescue;

import com.zhixin.zhfz.bacs.entity.RescueEntity;

import java.util.List;
import java.util.Map;

/**
 * @program: zhfz
 * @description: 医疗救助
 * @author: jzw
 * @create: 2019-02-21 11:20
 **/

public interface IRescueService {

   /**
    * @Author jzw
    * @Description 查询及条件查询
    * @Date 11:29 2019/2/21
    * @Param [map]
    * @return java.util.List<com.zhixin.zhfz.bacs.entity.RescueEntity>
    **/
    List<RescueEntity> list(Map<String, Object> map) throws Exception;

    /**
     * @Author jzw
     * @Description 分页
     * @Date 11:30 2019/2/21
     * @Param [map]
     * @return int
     **/
    int count(Map<String, Object> map) throws Exception;

    /*
     * @Author jzw
     * @Description 删除
     * @Date 11:31 2019/2/21
     * @Param [id]
     * @return void
     **/
    void delete(Long id) throws Exception;

    /**
     * @Author jzw
     * @Description 插入
     * @Date 11:32 2019/2/21
     * @Param [entity]
     * @return void
     **/
    void insert(RescueEntity entity) throws Exception;
    

     /**
      * @Author jzw
      * @Description 更新
      * @Date 15:57 2019/2/21
      * @Param [form]
      * @return void
      **/
     void update(RescueEntity entity) throws Exception;

  
}
