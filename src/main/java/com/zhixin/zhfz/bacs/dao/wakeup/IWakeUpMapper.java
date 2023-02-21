package com.zhixin.zhfz.bacs.dao.wakeup;


import com.zhixin.zhfz.bacs.entity.WakeUpEntity;

import java.util.List;
import java.util.Map;

/**
 * @program: zhfz
 * @description: 笔录类型答案mapper
 * @author: jzw
 * @create: 2019-02-22 16:19
 **/

public interface IWakeUpMapper {

    List<WakeUpEntity> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    void insert(WakeUpEntity entity) throws Exception;

    void update(WakeUpEntity entity) throws Exception;

    void delete(Long id) throws Exception;



}
