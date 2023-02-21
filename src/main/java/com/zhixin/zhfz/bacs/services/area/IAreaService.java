package com.zhixin.zhfz.bacs.services.area;

import com.zhixin.zhfz.bacs.entity.AreaEntity;
import com.zhixin.zhfz.bacs.entity.RoomEntity;

import java.util.List;
import java.util.Map;

public interface IAreaService {

	int count(Map<String, Object> params) throws Exception;

	void deleteArea(Long id) throws Exception;

	void deleteAreaOnly(Long id) throws Exception;

	void insertArea(AreaEntity entity);

	void updateArea(AreaEntity entity);

	List<AreaEntity> listAllArea(Map<String, Object> map);

	List<RoomEntity> listRomeByEnpId(Map<String, Object> map2);

	List<RoomEntity> listRoomByIp(String ip, String areaId);
	List<RoomEntity> countRoomByArea(Integer groupId);
	void insertRoom(RoomEntity entity);

	void updateRoom(RoomEntity entity);

	void deleteRoom(Long id)throws Exception;

	int count1(Map<String, Object> params)throws Exception;

	List<AreaEntity> listAreaByOrgStr(String orgStr);
	
	String getRecordLocationByRoomID(Long id);
	

	
	/**
	 * 根据group 批量添加room 
	 * @param
	 * @param params<roomtype,add number>
	 */
	void saveBatchRoom(Integer areaId,  Map<Integer, Integer> map);
	
	AreaEntity getAreaById(Long id);

}
