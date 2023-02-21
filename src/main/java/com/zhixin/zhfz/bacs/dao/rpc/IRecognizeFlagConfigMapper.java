package com.zhixin.zhfz.bacs.dao.rpc;


import com.zhixin.zhfz.bacs.entity.RecognizeFlagConfigEntity;

import java.util.List;
import java.util.Map;

/**
 * @program: zhfz
 * @description: 人员图片mapper
 * @author: jzw
 * @create: 2019-02-22 16:19
 **/

public interface IRecognizeFlagConfigMapper {

    List<RecognizeFlagConfigEntity> all(Map<String, Object> map);



}
