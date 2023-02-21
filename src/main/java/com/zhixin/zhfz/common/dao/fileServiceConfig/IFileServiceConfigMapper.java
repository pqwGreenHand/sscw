package com.zhixin.zhfz.common.dao.fileServiceConfig;


import com.zhixin.zhfz.common.entity.FileServiceConfigEntity;

import java.util.List;
import java.util.Map;

/**
 * @program: zhfz
 * @description: 人员图片mapper
 * @author: jzw
 * @create: 2019-02-22 16:19
 **/

public interface IFileServiceConfigMapper {

    List<FileServiceConfigEntity> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    List<FileServiceConfigEntity> all();

    void insert(FileServiceConfigEntity entity) throws Exception;

    void update(FileServiceConfigEntity entity) throws Exception;

    void delete(Long id) throws Exception;

    void updateEnable(Map<String,Object> params);

    FileServiceConfigEntity get(Map<String, Object> map);


}
