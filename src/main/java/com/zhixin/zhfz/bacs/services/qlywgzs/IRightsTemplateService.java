package com.zhixin.zhfz.bacs.services.qlywgzs;

import com.zhixin.zhfz.bacs.entity.RightsTemplateEntity;
import com.zhixin.zhfz.bacs.form.RightsTemplateSaveForm;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @program: zhfz
 * @description: 笔录word模板
 * @author: jzw
 * @create: 2019-02-21 11:20
 **/

public interface IRightsTemplateService {

   /**
    * @Author jzw
    * @Description 查询及条件查询
    * @Date 11:29 2019/2/21
    * @Param [map]
    * @return java.util.List<com.zhixin.zhfz.bacs.entity.RightsTemplateEntity>
    **/
    List<RightsTemplateEntity> list(Map<String, Object> map) throws Exception;

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
    void insert(RightsTemplateEntity entity) throws Exception;

    /**
     * @Author jzw
     * @Description 插入(文件上传)
     * @Date 15:57 2019/2/21
     * @Param [form]
     * @return void
     **/
    void insert(RightsTemplateSaveForm form,HttpServletRequest request) throws Exception;

    /**
     * @Author jzw
     * @Description 修改(文件上传)
     * @Date 15:57 2019/2/21
     * @Param [form]
     * @return void
     **/
    void update(RightsTemplateSaveForm form) throws Exception;


    /**
     * @Author jzw
     * @Description 获取word byte流
     * @Date 19:17 2019/2/21
     * @Param [id]
     * @return byte[]
     **/
    RightsTemplateEntity getWordById(Long id) throws Exception;
}
