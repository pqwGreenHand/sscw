package com.zhixin.zhfz.bacsapp.services.entrance;

import com.zhixin.zhfz.bacs.entity.AlarmEntity;
import com.zhixin.zhfz.bacsapp.entity.EntranceEntity;

import java.util.List;
import java.util.Map;

public interface IEntranceService {

	public List<EntranceEntity> entranceList(Map<String,Object> map);

	public int count(Map<String,Object> map);

    public List<EntranceEntity> orderList(Map<String, Object> map);

    public int orderCount(Map<String, Object> map);

    public List<EntranceEntity> personList(Map<String, Object> map);

    List<EntranceEntity> orderListNew(Map<String, Object> params);
}
