package com.zhixin.zhfz.bacs.services.track;

import java.util.*;

import com.zhixin.zhfz.bacs.dao.track.TrackMapper;
import com.zhixin.zhfz.bacs.entity.PositionDataEntity;
import com.zhixin.zhfz.bacs.entity.TrackInfoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ITrackServiceImpl implements ITrackService {

	@Autowired
	private TrackMapper mapper;

	@Override
	public List<Map<String, Object>> listPersonCase(Map<String, Object> map) {
		return mapper.listPersonByCase(map);
	}


	@Override
	public void deleteTrack(Map<String, Object> params) {
		mapper.deleteTrack(params);
	}

	@Override
	public void insertTrack(TrackInfoEntity entity) throws Exception {
		mapper.insertTrack(entity);
	}

	@Override
	public void insertTrackBatch(List<TrackInfoEntity> entitys) throws Exception {
		mapper.insertTrackBatch(entitys);
	}

	@Override
	public List<TrackInfoEntity> listTrack(Map<String, Object> map) {
		return mapper.listTrack(map);
	}
	
	@Override
	public List<TrackInfoEntity> listDownloadList(Map<String, Object> map) {
		return mapper.listDownloadList(map);
	}
	
	@Override
	public List<PositionDataEntity> listDeviceData(Map<String, Object> map) {
		return mapper.listDeviceData(map);
	}

	@Override
	public void bathCreateTrackInfo(Map<String, Object> map) throws Exception {
		List<PositionDataEntity> positionDatas = mapper.listDeviceData(map);
		// 根据定位信息，获取轨迹信息
		List<TrackInfoEntity> trackInfos = positionToTrackInfo(positionDatas, Long.parseLong(map.get("serialId") + ""));
		if (trackInfos != null && trackInfos.size() > 0) {
			// 将对应的轨迹信息删除
			mapper.deleteTrack(map);
			// 将轨迹信息批量插入到数据库
			mapper.insertTrackBatch(trackInfos);
		}
	}

	// 将定位信息转换为轨迹信息
	private static List<TrackInfoEntity> positionToTrackInfo(List<PositionDataEntity> positionDatas, long serialId) {
		List<TrackInfoEntity> trackInfos = new ArrayList<>();
		if (positionDatas != null) {
			String tempRoomGroup = null;
			TrackInfoEntity tempTrackInfo = null;
			for (int i = 0; i < positionDatas.size(); i++) {
				PositionDataEntity positionData = positionDatas.get(i);
				if (i < (positionDatas.size() - 1)) {
					if (tempRoomGroup != null && tempRoomGroup.equals(positionData.getRoomGroup())) {
						tempTrackInfo.setEndTime(positionData.getSendTime());
					} else {
						if (tempRoomGroup != null) {// 不是第一条数据
							tempTrackInfo.setEndTime(positionData.getSendTime());
							trackInfos.add(tempTrackInfo);
						}
						tempRoomGroup = positionData.getRoomGroup();
						tempTrackInfo = new TrackInfoEntity();
						tempTrackInfo.setSerialId(serialId);
						tempTrackInfo.setCuffNo(positionData.getCuffId() + "");
						tempTrackInfo.setRoomGroup(positionData.getRoomGroup());
						tempTrackInfo.setStartTime(positionData.getSendTime());
					}
				} else {// 最后一条数据
					if (tempRoomGroup == null) {// 又为第一条数据
						tempTrackInfo = new TrackInfoEntity();
						tempTrackInfo.setSerialId(serialId);
						tempTrackInfo.setCuffNo(positionData.getCuffId() + "");
						tempTrackInfo.setRoomGroup(positionData.getRoomGroup());
						tempTrackInfo.setStartTime(positionData.getSendTime());
						tempTrackInfo.setEndTime(positionData.getSendTime());
						trackInfos.add(tempTrackInfo);
					} else {
						if (tempRoomGroup != null && tempRoomGroup.equals(positionData.getRoomGroup())) {
							tempTrackInfo.setEndTime(positionData.getSendTime());
							trackInfos.add(tempTrackInfo);
						} else {
							tempTrackInfo.setEndTime(positionData.getSendTime());
							trackInfos.add(tempTrackInfo);
							tempTrackInfo = new TrackInfoEntity();
							tempTrackInfo.setSerialId(serialId);
							tempTrackInfo.setCuffNo(positionData.getCuffId() + "");
							tempTrackInfo.setRoomGroup(positionData.getRoomGroup());
							tempTrackInfo.setStartTime(positionData.getSendTime());
							tempTrackInfo.setEndTime(positionData.getSendTime());
							trackInfos.add(tempTrackInfo);
						}
					}
				}
			}
		}
		if (trackInfos.size() > 0) {
			for (TrackInfoEntity track : trackInfos) {
				// 开始时间向前推5秒
				track.setStartTime(new Date(track.getStartTime().getTime() - (5 * 1000)));
				Long startTime = track.getStartTime().getTime();
				Long endTIme = track.getEndTime().getTime();
				// 将轨迹数据结束时间少于10秒的 设为10秒
				if ((endTIme - startTime) < (10 * 1000)) {
					track.setEndTime(new Date(track.getStartTime().getTime() + (10 * 1000)));
				}
			}
		}
		return trackInfos;
	}

	// 将定位信息转换为轨迹信息 20171031 zuri
	private static List<TrackInfoEntity> positionToTrackInfoa(List<PositionDataEntity> positionDatas, long serialId) {
		List<TrackInfoEntity> trackInfos = new ArrayList<>();
		if (positionDatas != null && positionDatas.size() > 0) {

			TrackInfoEntity tempTrackInfo = null;
			PositionDataEntity positionData;
			Date start;
			Date end;
			int index = 0;
			for (int i = 0; i < positionDatas.size(); i++) {
				if (i < index) {
					continue;
				}
				index=i+1;
				positionData = positionDatas.get(i);
				start = positionData.getSendTime();
				end = positionData.getSendTime();
				if (i < (positionDatas.size() - 1)) {
					
					for (int j = i+1; j < positionDatas.size();j++) 
					{
						PositionDataEntity positionData1 = positionDatas.get(j);
						if (positionData.getRoomId() == positionData1.getRoomId()) {
							if (end.getTime() + 6000 >= positionData1.getSendTime().getTime()) {
								end = positionData1.getSendTime();
								index = j+1;
								continue;
							} else {
								break;
							}

						}

						break;
					}

				}
				
				
				tempTrackInfo = new TrackInfoEntity();
				tempTrackInfo.setSerialId(serialId);
				tempTrackInfo.setCuffNo(positionData.getCuffId() + "");
				tempTrackInfo.setRoomGroup(positionData.getRoomGroup());
				tempTrackInfo.setStartTime(start);
				tempTrackInfo.setEndTime(end);
				trackInfos.add(tempTrackInfo);
			}
		}

		if (trackInfos.size() > 0) {
			for (TrackInfoEntity track : trackInfos) {
				// 开始时间向前推5秒
				track.setStartTime(new Date(track.getStartTime().getTime() - (5 * 1000)));
				Long startTime = track.getStartTime().getTime();
				Long endTIme = track.getEndTime().getTime();
				// 将轨迹数据结束时间少于10秒的 设为10秒
				if ((endTIme - startTime) < (10 * 1000)) {
					track.setEndTime(new Date(track.getStartTime().getTime() + (10 * 1000)));
				}
			}
		}
		return trackInfos;
	}

	@Override
	public List<Map<String, Object>> listPoliceByCase(Map<String, Object> map) {
		return mapper.listPoliceByCase(map);
	}


	@Override
	public List<TrackInfoEntity> listTackInfoByCuffAndSerialId(Map<String, Object> map) {

		return mapper.listTackInfoByCuffAndSerialId(map);
	}
}
