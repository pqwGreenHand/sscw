package com.zhixin.zhfz.bacs.dao.rescue;


import com.zhixin.zhfz.bacs.entity.RescueEntity;

import java.util.List;
import java.util.Map;

/**
 * @program: zhfz
 * @description: 医疗救助mapper
 * @author: jzw
 * @create: 2019-02-22 16:19
 **/

public interface IRescueMapper {

    List<RescueEntity> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    void insert(RescueEntity entity) throws Exception;

    void update(RescueEntity entity) throws Exception;

    void delete(Long id) throws Exception;



}
