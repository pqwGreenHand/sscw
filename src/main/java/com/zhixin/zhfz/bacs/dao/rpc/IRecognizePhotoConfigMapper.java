package com.zhixin.zhfz.bacs.dao.rpc;

import com.zhixin.zhfz.bacs.entity.RecognizePhotoConfigEntity;

import java.util.List;
import java.util.Map;

/**
 * @program: zhfz
 * @description: 人员图片mapper
 * @author: jzw
 * @create: 2019-02-22 16:19
 **/

public interface IRecognizePhotoConfigMapper {

    List<RecognizePhotoConfigEntity> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    void insert(RecognizePhotoConfigEntity entity) throws Exception;

    void update(RecognizePhotoConfigEntity entity) throws Exception;

    void delete(Long id) throws Exception;


}
