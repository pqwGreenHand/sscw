package com.zhixin.zhfz.bacs.services.statistics;


import com.zhixin.zhfz.bacs.dao.statistics.IStatisticsMapper;
import com.zhixin.zhfz.bacs.entity.AreaManage;
import com.zhixin.zhfz.bacs.entity.LastCaseEntity;
import com.zhixin.zhfz.bacs.entity.StatisticsEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class StatisticsServiceImpl implements IStatisticsService{

	@Autowired
	private IStatisticsMapper statisticsMapper ;
	
	@Override
	public List<StatisticsEntity> listSerialsPerDayByAreaId(StatisticsEntity entity) {
		return statisticsMapper.listSerialsPerDayByAreaId(entity);
	}

	@Override
	public List<StatisticsEntity> listCasesPerDayByAreaId(StatisticsEntity entity) {
		return statisticsMapper.listCasesPerDayByAreaId(entity);
	}

	@Override
	public List<LastCaseEntity> listInterrogateSerial(Map<String, Object> param) {
		return statisticsMapper.listInterrogateSerial(param);
	}

	@Override
	public int countInterrogateSerial(Map<String, Object> param) {
		return statisticsMapper.countInterrogateSerial(param);
	}

	@Override
	public List<LastCaseEntity> listOrderRequest(Map<String, Object> param) {
		return statisticsMapper.listOrderRequest(param);
	}

	@Override
	public int countOrderRequest(Map<String, Object> param) {
		return statisticsMapper.countOrderRequest(param);
	}

	@Override
	public List<LastCaseEntity> listInterrogateRecord(Map<String, Object> param) {
		return statisticsMapper.listInterrogateRecord(param);
	}

	@Override
	public int countInterrogateRecord(Map<String, Object> param) {
		return statisticsMapper.countInterrogateRecord(param);
	}

	@Override
	public List<LastCaseEntity> listWaitingRecord(Map<String, Object> param) {
		return statisticsMapper.listWaitingRecord(param);
	}

	@Override
	public int countWaitingRecord(Map<String, Object> param) {
		return statisticsMapper.countWaitingRecord(param);
	}

	@Override
	public List<StatisticsEntity> queryDataBySerialId(Map<String,Object> param) {
		return statisticsMapper.queryDataBySerialId(param);
	}

	@Override
	public List<StatisticsEntity> queryOherDataBySerialId(Map<String,Object> param) {
		return statisticsMapper.queryOherDataBySerialId(param);
	}

	@Override
	public List<LastCaseEntity> otherentranceList(Map<String, Object> param) {
		return statisticsMapper.otherentranceList(param);
	}

	@Override
	public int countOtherentrance(Map<String, Object> param) {
		return statisticsMapper.countOtherentrance(param);
	}

	@Override
	public int countkyouttime(Map<String, Object> param) {
		return statisticsMapper.countkyouttime(param);
	}

	@Override
	public List<AreaManage> getOrderCount() {
		return statisticsMapper.getOrderCount();
	}

	@Override
	public List<AreaManage> getInCount() {
		return statisticsMapper.getInCount();
	}

	@Override
	public List<AreaManage> getOutCount() {
		return statisticsMapper.getOutCount();
	}

}
