package com.zhixin.zhfz.bacsapp.dao.common;


import com.zhixin.zhfz.bacs.entity.RoomEntity;
import com.zhixin.zhfz.bacsapp.entity.SerialAppEntity;

import java.util.List;
import java.util.Map;

public interface ICommonMapper {

	List<SerialAppEntity> listAllSerial(Map<String, Object> map);
	int countAllSerial(Map<String, Object> map);
	List<SerialAppEntity> listAllPerson(Map<String, Object> map);
	List<RoomEntity> listAllRoom(Map<String, Object> map);



}