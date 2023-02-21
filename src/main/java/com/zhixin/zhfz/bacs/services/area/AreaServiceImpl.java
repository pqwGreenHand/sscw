package com.zhixin.zhfz.bacs.services.area;


import com.zhixin.zhfz.common.common.FormatUtil;
import com.zhixin.zhfz.bacs.dao.area.IAreaMapper;
import com.zhixin.zhfz.bacs.dao.room.IRoomMapper;
import com.zhixin.zhfz.bacs.entity.AreaEntity;
import com.zhixin.zhfz.bacs.entity.RoomEntity;
import com.zhixin.zhfz.bacs.entity.RoomTypeEntity;
import com.zhixin.zhfz.bacs.services.room.IRoomTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class AreaServiceImpl implements IAreaService {

	private static Logger logger = LoggerFactory.getLogger(AreaServiceImpl.class);
	
	@Autowired
	private IAreaMapper areaMapper;
	@Autowired
	private IRoomMapper roomMapper;
	@Autowired
	private IRoomTypeService roomTypeService;

	@Override
	public void deleteArea(Long id) throws Exception {
		this.areaMapper.deleteArea(id);
	}

	public void deleteAreaOnly(Long id) throws Exception {
		this.areaMapper.deleteAreaOnly(id);
	}

	@Override
	public int count(Map<String, Object> params) throws Exception {
		return this.areaMapper.count(params);
	}
	@Override
	public int count1(Map<String, Object> params) throws Exception  {
		return this.roomMapper.count1(params);
	}

	@Override
	public List<AreaEntity> listAreaByOrgStr(String orgStr) {
		Map<String, Object> map = new HashMap<>();
		map.put("orgStr", orgStr);
		return this.areaMapper.listAreaByOrgStr(map);
	}

	@Override
	public void insertArea(AreaEntity entity) {
		this.areaMapper.insertArea(entity);
		
	}

	@Override
	public void updateArea(AreaEntity entity) {
		this.areaMapper.updateArea(entity);
		
	}

	@Override
	public List<AreaEntity> listAllArea(Map<String, Object> map) {
		return this.areaMapper.listAllArea(map);
	}

	@Override
	public List<RoomEntity> listRomeByEnpId(Map<String, Object> map2) {
		
		return this.roomMapper.listAllroom(map2);
	}

	@Override
	public List<RoomEntity> listRoomByIp(String ip, String areaId) {
		Map<String, Object> param = new HashMap<>();
		param.put("ip", ip);
		param.put("areaId", areaId);
		return roomMapper.listRoomByIp(param);
	}
	@Override
	public List<RoomEntity> countRoomByArea(Integer AreaId){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("AreaId", AreaId);
		return this.roomMapper.countRoomByArea(param);
	}
	@Override
	public void insertRoom(RoomEntity entity) {
		this.roomMapper.insertRoom(entity);
		
	}

	@Override
	public void updateRoom(RoomEntity entity) {
		this.roomMapper.updateRoom(entity);
		
	}

	@Override
	public void deleteRoom(Long id) throws Exception {
		this.roomMapper.deleteRoom(id);
	}

	@Override
	public String getRecordLocationByRoomID(Long id) {
		return this.roomMapper.getRecordLocationByRoomID(id);
	}
	

	
	@Override
	public void saveBatchRoom (Integer areaId,Map<Integer, Integer> map){
		//获取id
		Map<Integer,Integer> countMap= getRoomeCount(areaId);
		Map<Integer,RoomTypeEntity> rtMap=  getRoomeTypeMap();
		for(Integer id:map.keySet()){
			int start=0;
			if(countMap.containsKey(id) && countMap.get(id)!=null){
				start=countMap.get(id);
			}
			RoomTypeEntity rtEntity=rtMap.get(id);
			Integer num=map.get(id);
			
			if(rtEntity!=null)
			{
				for(int i=1+start;i<=num+start;i++){
					String name=rtEntity.getName()+ FormatUtil.DECADE_FORMAT.format(i);
					RoomEntity entity = new RoomEntity();
					
					
					entity.setName(name);
					entity.setRoomTypeId(id);
					entity.setDescription(name);
					entity.setInterrogateAreaId(areaId);
					entity.setCreatedTime(new Date());
					entity.setUpdatedTime(new Date());
					entity.setIps("127.0.0.1");
					entity.setIsActive(1);
					entity.setVolume(20);
					entity.setLedAddress(name);
					try{
						this.roomMapper.insertRoom(entity);
					}catch(Exception e){
						logger.error(e.getMessage(),e);
					}
				}
			}
			
			
		}
		
		
	}

	private Map<Integer,Integer> getRoomeCount(Integer areaId){
		Map<Integer,Integer> map=new HashMap<Integer,Integer>();
		List<RoomEntity> counts=countRoomByArea(areaId);
		if(counts!=null){
			for(RoomEntity entity:counts){
				map.put(entity.getRoomTypeId(), entity.getCountNum());
			}
		}
		return map;

	}
	private Map<Integer,RoomTypeEntity> getRoomeTypeMap(){
		Map<String, Object> rtParams = new HashMap<String, Object>();
		Map<Integer,RoomTypeEntity> map=new HashMap<Integer,RoomTypeEntity>();
		rtParams.put("pageStart", 0);
		rtParams.put("rows", 1000);
		List<RoomTypeEntity> rtList;
		try {
			rtList = roomTypeService.list(rtParams);
			if(rtList!=null){
				for(RoomTypeEntity entity:rtList){
					map.put(entity.getId(), entity);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}

		return map;

	}


	@Override
	public AreaEntity getAreaById(Long id) {
		return areaMapper.getAreaById(id);
	}
}
