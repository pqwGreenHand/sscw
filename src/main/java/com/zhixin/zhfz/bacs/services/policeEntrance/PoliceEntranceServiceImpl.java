package com.zhixin.zhfz.bacs.services.policeEntrance;

import java.util.List;
import java.util.Map;

import com.zhixin.zhfz.bacs.dao.policebelong.IPoliceBelongMapper;
import com.zhixin.zhfz.bacs.entity.PoliceBelongEntity;
import com.zhixin.zhfz.bacs.entity.SerialEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhixin.zhfz.bacs.dao.policeEntrance.IPoliceEntranceMapper;
import com.zhixin.zhfz.bacs.entity.PoliceEntranceEntity;

@Service
public class PoliceEntranceServiceImpl implements IPoliceEntranceService {

	@Autowired
	private IPoliceEntranceMapper policeEntranceMapper;

	@Autowired
	private IPoliceBelongMapper policeBelongMapper;

	@Override
	public void addPoliceEntrance(PoliceEntranceEntity policeEntrance) {
		policeEntranceMapper.insertPoliceEntrance(policeEntrance);
	}

	@Override
	public void updateOutTime(PoliceEntranceEntity policeEntrance) {
		policeEntranceMapper.updateOutTime(policeEntrance);
	}

	@Override
	public List<PoliceEntranceEntity> list(Map<String, Object> params) throws Exception {
		return policeEntranceMapper.list(params);
	}

	@Override
	public int count(Map<String, Object> params) throws Exception {
		return policeEntranceMapper.count(params);
	}

	@Override
	public List<PoliceEntranceEntity> list2(Map<String, Object> params) throws Exception {
		return policeEntranceMapper.list2(params);
	}

	@Override
	public int count2(Map<String, Object> params) throws Exception {
		return policeEntranceMapper.count2(params);
	}

	@Override
	public List<PoliceEntranceEntity> policeList(Map<String, Object> params) throws Exception {
		return policeEntranceMapper.policeList(params);
	}

	@Override
	public int policeListCount(Map<String, Object> params) throws Exception {
		return policeEntranceMapper.policeListCount(params);
	}

	@Override
	public List<PoliceEntranceEntity> getPoliceEnteance(Map<String, Object> params) throws Exception {
		return policeEntranceMapper.getPoliceEnteance(params);
	}
	@Override
	public PoliceEntranceEntity findPoliceEntranceByPoliceId(int policeId) throws Exception {
		return policeEntranceMapper.findPoliceEntranceByPoliceId(policeId);
	}

	@Override
	public PoliceBelongEntity selectSidnfo(Map<String, Object> paramMap) {
		return policeBelongMapper.selectSidnfo(paramMap);
	}

	@Override
	public List<SerialEntity> getSerialByPolice(Map<String, Object> params) {
		return policeEntranceMapper.getSerialByPolice(params);
	}

}
