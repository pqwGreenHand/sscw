package com.zhixin.zhfz.bacs.services.rpc;

import com.zhixin.zhfz.bacs.entity.RecognizeFlagConfigEntity;
import com.zhixin.zhfz.bacs.entity.RecognizePersonConfigEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * @program: zhfz
 * @description: 人员图片管理
 * @author: jzw
 * @create: 2019-02-21 11:20
 **/

public interface IRecognizeConfigService {

   /**
    * @Author jzw
    * @Description 查询及条件查询
    * @Date 11:29 2019/2/21
    * @Param [map]
    * @return java.util.List<com.zhixin.zhfz.bacs.entity.RecognizePersonConfigEntity>
    **/
    List<RecognizePersonConfigEntity> personList(Map<String, Object> map) throws Exception;

    /**
     * @Author jzw
     * @Description 分页
     * @Date 11:30 2019/2/21
     * @Param [map]
     * @return int
     **/
    int personCount(Map<String, Object> map) throws Exception;

    /*
     * @Author jzw
     * @Description 删除
     * @Date 11:31 2019/2/21
     * @Param [id]
     * @return void
     **/
    void personDelete(Long id) throws Exception;

    /**
     * @Author jzw
     * @Description 插入
     * @Date 11:32 2019/2/21
     * @Param [entity]
     * @return void
     **/
    void personInsert(RecognizePersonConfigEntity entity) throws Exception;


    void insert(MultipartFile file,String url) throws Exception;


     /**
      * @Author jzw
      * @Description 更新
      * @Date 15:57 2019/2/21
      * @Param [form]
      * @return void
      **/
     void personUpdate(RecognizePersonConfigEntity entity) throws Exception;

     List<RecognizeFlagConfigEntity> selectFlagAll(Map<String,Object> params);
  
}
