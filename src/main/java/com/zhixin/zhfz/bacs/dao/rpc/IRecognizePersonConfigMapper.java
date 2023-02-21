package com.zhixin.zhfz.bacs.dao.rpc;

import com.zhixin.zhfz.bacs.entity.RecognizePersonConfigEntity;

import java.util.List;
import java.util.Map;

/**
 * @program: zhfz
 * @description: 人员图片mapper
 * @author: jzw
 * @create: 2019-02-22 16:19
 **/

public interface IRecognizePersonConfigMapper {

    List<RecognizePersonConfigEntity> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    void insert(RecognizePersonConfigEntity entity) throws Exception;

    void update(RecognizePersonConfigEntity entity) throws Exception;

    void delete(Long id) throws Exception;

    RecognizePersonConfigEntity get(Map<String,Object> map);


}
