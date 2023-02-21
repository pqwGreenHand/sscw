package com.zhixin.zhfz.bacs.services.positionManage;

import com.zhixin.zhfz.bacs.entity.PositionEntranceEntity;

import java.util.List;
import java.util.Map;

public interface IPositionEntranceService {

	List<PositionEntranceEntity> list(Map<String, Object> map);

	int count(Map<String, Object> map);




}
