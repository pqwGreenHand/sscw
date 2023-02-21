package com.zhixin.zhfz.common.services.fileServiceConfig;

import com.zhixin.zhfz.common.entity.FileServiceConfigEntity;
import com.zhixin.zhfz.common.form.FileDownloadForm;
import com.zhixin.zhfz.common.form.FileUploadForm;
import org.apache.http.HttpResponse;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @program: zhfz
 * @description: 醒酒登记
 * @author: jzw
 * @create: 2019-02-21 11:20
 **/

public interface IFileConfigService {

   /**
    * @Author jzw
    * @Description 查询及条件查询
    * @Date 11:29 2019/2/21
    * @Param [map]
    * @return java.util.List<com.zhixin.zhfz.bacs.entity.FileServiceConfigEntity>
    **/
    List<FileServiceConfigEntity> list(Map<String, Object> map) throws Exception;

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
    void insert(FileServiceConfigEntity entity) throws Exception;
    

     /**
      * @Author jzw
      * @Description 更新
      * @Date 15:57 2019/2/21
      * @Param [form]
      * @return void
      **/
     void update(FileServiceConfigEntity entity) throws Exception;


     /**
      * @Author jzw
      * @Description 修改可用性
      * @Date 9:16 2019/3/14
      * @Param [map]
      * @return void
      **/
     void updateEnable(Map<String,Object> map) throws Exception;

     /**
      * @Author jzw
      * @Description 文件下载
      * @Date 18:59 2019/3/12
      * @Param []
      * @return java.lang.Byte[]
      **/
     String download(FileDownloadForm form);

     /**
      * @Author jzw
      * @Description 文件上传
      * @Date 18:55 2019/3/12
      * @Param [form]
      * @return void
      **/
     void upload(FileUploadForm form) throws Exception;

    /**
     * @Author lb
     * @Description 获取文件base64
     * @Date  2019/5/29
     * @param form
     * @throws Exception
     */
    String getFileBase64(FileDownloadForm form) throws Exception;

}
