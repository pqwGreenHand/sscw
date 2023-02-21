package com.zhixin.zhfz.bacsapp.dao.track;


import com.zhixin.zhfz.bacsapp.entity.TrackInfoEntity;

import java.util.List;
import java.util.Map;

public interface ITrackMapper {

	
	/*
	 * 刪除手环对应的轨迹时间段（每个功能室的）
	 */
	void deleteTrack(Integer id);
	
	/*
	 * 插入手环对应的轨迹时间段（每个功能室的）
	 */
	void insertTrack(TrackInfoEntity entity)throws Exception;
	
  
	/*
	 * 批量插入手环对应的轨迹时间段（每个功能室的）
	 */
//	void insertTrackBatch(List<TrackInfoEntity> entitys)throws Exception;
	
	/*
	 * 查询每条入区记录对应的手环在各功能室的开始、结束时间
	 */
	List<TrackInfoEntity> listTrack(Map<String, Object> map);
	


}