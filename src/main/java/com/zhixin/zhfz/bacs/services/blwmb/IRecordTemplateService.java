package com.zhixin.zhfz.bacs.services.blwmb;

import com.zhixin.zhfz.bacs.entity.RecordTemplateEntity;
import com.zhixin.zhfz.bacs.form.RecordTemplateForm;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @program: zhfz
 * @description: 笔录word模板
 * @author: jzw
 * @create: 2019-02-21 11:20
 **/

public interface IRecordTemplateService {

   /**
    * @Author jzw
    * @Description 查询及条件查询
    * @Date 11:29 2019/2/21
    * @Param [map]
    * @return java.util.List<com.zhixin.zhfz.bacs.entity.RightsTemplateEntity>
    **/
    List<RecordTemplateEntity> list(Map<String, Object> map) throws Exception;

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
    void insert(RecordTemplateEntity entity) throws Exception;

    /**
     * @Author jzw
     * @Description 插入(文件上传)
     * @Date 15:57 2019/2/21
     * @Param [form]
     * @return void
     **/
    void insert(RecordTemplateForm form, HttpServletRequest request) throws Exception;

     /**
      * @Author jzw
      * @Description 插入(文件上传)
      * @Date 15:57 2019/2/21
      * @Param [form]
      * @return void
      **/
     void update(RecordTemplateForm form) throws Exception;

    /**
     * @Author jzw
     * @Description 获取word byte流
     * @Date 19:17 2019/2/21
     * @Param [id]
     * @return byte[]
     **/
    RecordTemplateEntity getWordById(Long id) throws Exception;
}
