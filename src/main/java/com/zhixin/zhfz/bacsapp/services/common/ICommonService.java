package com.zhixin.zhfz.bacsapp.services.common;

import com.zhixin.zhfz.bacs.entity.RoomEntity;
import com.zhixin.zhfz.bacsapp.entity.SerialAppEntity;

import java.util.List;
import java.util.Map;

public interface ICommonService {

	List<SerialAppEntity> listAllSerial(Map<String, Object> map) throws Exception;

	int countAllSerial(Map<String, Object> map) throws Exception;

	List<SerialAppEntity> listAllPerson(Map<String, Object> map) throws Exception;

	List<RoomEntity> listAllRoom(Map<String, Object> map) throws Exception;


}
