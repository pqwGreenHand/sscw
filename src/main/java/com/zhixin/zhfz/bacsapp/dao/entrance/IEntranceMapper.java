package com.zhixin.zhfz.bacsapp.dao.entrance;

import com.zhixin.zhfz.bacsapp.entity.EntranceEntity;

import java.util.List;
import java.util.Map;

/**
 * @program: zhfz
 * @description: 入区登记
 * @author: cuichengwei
 * @create: 2019-04-02
 **/

public interface IEntranceMapper {

    public List<EntranceEntity> entranceList(Map<String, Object> map);

    public int count(Map<String, Object> map);

    public List<EntranceEntity> orderList(Map<String, Object> map);

    public int orderCount(Map<String, Object> map);

    public List<EntranceEntity> personList(Map<String, Object> map);

    List<EntranceEntity> orderListNew(Map<String, Object> map);
}
