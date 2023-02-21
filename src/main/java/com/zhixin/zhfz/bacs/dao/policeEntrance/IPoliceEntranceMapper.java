package com.zhixin.zhfz.bacs.dao.policeEntrance;

import java.util.List;
import java.util.Map;

import com.zhixin.zhfz.bacs.entity.PoliceEntranceEntity;
import com.zhixin.zhfz.bacs.entity.SerialEntity;

public interface IPoliceEntranceMapper {

	// 添加民警信息
	public void insertPoliceEntrance(PoliceEntranceEntity policeEntrance);

	// 更新民警出区时间
	public void updateOutTime(PoliceEntranceEntity policeEntrance);

	// 民警入区列表
	List<PoliceEntranceEntity> list(Map<String, Object> params) throws Exception;

	int count(Map<String, Object> params) throws Exception;
	
	List<PoliceEntranceEntity> list2(Map<String, Object> params) throws Exception;

	int count2(Map<String, Object> params) throws Exception;
	
	List<PoliceEntranceEntity> policeList(Map<String, Object> params) throws Exception;
	
	int policeListCount(Map<String, Object> params) throws Exception;

	List<PoliceEntranceEntity>  getPoliceEnteance(Map<String, Object> params) throws Exception;

	PoliceEntranceEntity findPoliceEntranceByPoliceId(int policeId) throws Exception;

	public List<SerialEntity> getSerialByPolice(Map<String, Object> params);

}