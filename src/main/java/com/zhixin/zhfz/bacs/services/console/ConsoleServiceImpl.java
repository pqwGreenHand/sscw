package com.zhixin.zhfz.bacs.services.console;


import com.zhixin.zhfz.bacs.dao.console.IConsoleMapper;
import com.zhixin.zhfz.bacs.dao.serial.ISerialMapper;
import com.zhixin.zhfz.bacs.entity.LastCaseEntity;
import com.zhixin.zhfz.bacs.entity.SerialEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ConsoleServiceImpl implements IConsoleService{

	@Autowired
	private IConsoleMapper consoleMapper;
	@Autowired
	private ISerialMapper serialMapper;

	@Override
	public List<SerialEntity> queryDetainByAreaId(Integer areaId, String str) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("areaId",areaId);
		map.put("dataAuth",str);
		List<SerialEntity> list = consoleMapper.queryDetainByAreaId(map);
		return list;
	}

	@Override
	public Map<String,Integer> queryCountByAreaId(Integer areaId,String str) throws Exception {
		Map<String,Integer> map = new HashMap<>();
		Map<String,Object> area = new HashMap<String,Object>();
		area.put("areaId",areaId);
		area.put("dataAuth",str);

		map = consoleMapper.queryCountByAreaId(area);
		return map;
	}

	@Override
	public List<Map<String,Integer>> queryRenByAreaId(Integer areaId,String str) throws Exception {
		List<Map<String,Integer>> map = new ArrayList<Map<String,Integer>>();
		Map<String,Object> area = new HashMap<String,Object>();
		area.put("areaId",areaId);
		area.put("dataAuth",str);
		map = consoleMapper.queryRenByAreaId(area);
		return map;
	}

	@Override
	public Map<String, Integer> queryChuquByDay(Integer areaId,String str) throws Exception {
		Map<String,Object> area = new HashMap<>();
		area.put("areaId",areaId);
		area.put("dataAuth",str);
		Map<String, Integer> param = consoleMapper.queryChuquByDay(area);
		return param;
	}

	@Override
	public Map<String, Integer> queryLinshiByDay(Integer areaId,String str) throws Exception {
		Map<String,Object> area = new HashMap<>();
		area.put("areaId",areaId);
		area.put("dataAuth",str);
		Map<String, Integer> param = consoleMapper.queryLinshiByDay(area);
		return param;
	}

	@Override
	public int countOtherStatus(Map<String, Object> param) {
		return consoleMapper.countOtherStatus(param);
	}

	@Override
	public int countInterrogateSerial(Map<String, Object> param) {
		return consoleMapper.countInterrogateSerial(param);
	}


	@Override
	public int countWaitingRecord(Map<String, Object> param) {
		return consoleMapper.countWaitingRecord(param);
	}

	@Override
	public int countInterrogateRecord(Map<String, Object> param) {
		return consoleMapper.countInterrogateRecord(param);
	}

	@Override
	public int countOrderRequest(Map<String, Object> param) {
		return consoleMapper.countOrderRequest(param);
	}
	@Override
	public int countOtherentrance(Map<String, Object> param) {
		return consoleMapper.countOtherentrance(param);
	}

	@Override
	public int queryChuquByDay1(Map<String, Object> param) {
		return consoleMapper.queryChuquByDay1(param);
	}

	@Override
	public int queryLinshiByDay1(Map<String, Object> param) {
		return consoleMapper.queryLinshiByDay1(param);
	}

	@Override
	public int countkyouttime(Map<String, Object> param) {
		return consoleMapper.countkyouttime(param);
	}
	@Override
	public List<SerialEntity> listJyOutTime(Map<String, Object> param) {
		return serialMapper.listJyOutTime(param);
	}

	@Override
	public int countJyOutTime(Map<String, Object> param) {
		return serialMapper.countJyOutTime(param);
	}

	@Override
	public int countSscw(Map<String, Object> param) {
		return consoleMapper.countSscw(param);
	}

    @Override
    public int countSsyj(Map<String, Object> param) {
        return consoleMapper.countSsyj(param);
    }

    @Override
    public int countSsjs(Map<String, Object> param) {
        return consoleMapper.countSsjs(param);
    }

    @Override
    public Integer queryUsedCountBelongDetail(Map<String, Object> param) {
        return consoleMapper.queryUsedCountBelongDetail(param);
    }

    @Override
    public Integer queryAllCountBelongDetail(Map<String, Object> param) {
        return consoleMapper.queryAllCountBelongDetail(param);
    }

	@Override
	public int countSsjj(Map<String, Object> param) {
		return  consoleMapper.countSsjj(param);
	}

	@Override
	public List<Map<String, Object>> countSscwTj() {
		return  consoleMapper.countSscwTj();
	}

	@Override
	public Map<String, Object> countTj() {
		return consoleMapper.countTj();
	}

}
