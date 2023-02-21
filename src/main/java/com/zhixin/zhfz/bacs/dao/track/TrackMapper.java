package com.zhixin.zhfz.bacs.dao.track;

import com.zhixin.zhfz.bacs.entity.PositionDataEntity;
import com.zhixin.zhfz.bacs.entity.TrackInfoEntity;

import java.util.List;
import java.util.Map;

public interface TrackMapper {

	/*
	 * 查询每个案件下的有手环的嫌疑人、有卡片的民警
	 */
	List<Map<String, Object>> listPersonByCase(Map<String, Object> map);


	List<Map<String, Object>> listPoliceByCase(Map<String, Object> map);
	
	/*
	 * 刪除手环对应的轨迹时间段（每个功能室的）
	 */
	void deleteTrack(Map<String, Object> params);
	
	/*
	 * 插入手环对应的轨迹时间段（每个功能室的）
	 */
	void insertTrack(TrackInfoEntity entity)throws Exception;
	
  
	/*
	 * 批量插入手环对应的轨迹时间段（每个功能室的）
	 */
	void insertTrackBatch(List<TrackInfoEntity> entitys)throws Exception;
	
	/*
	 * 查询每条入区记录对应的手环在各功能室的开始、结束时间
	 */
	List<TrackInfoEntity> listTrack(Map<String, Object> map);
	
	List<TrackInfoEntity> listDownloadList(Map<String, Object> map);
	
	/*
	 * 查询每条入区记录对应的手环定位数据
	 */
	List<PositionDataEntity> listDeviceData(Map<String, Object> map);


	List<TrackInfoEntity> listTackInfoByCuffAndSerialId(Map<String, Object> map);
}