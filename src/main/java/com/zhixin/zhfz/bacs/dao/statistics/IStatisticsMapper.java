package com.zhixin.zhfz.bacs.dao.statistics;

import com.zhixin.zhfz.bacs.entity.AreaManage;
import com.zhixin.zhfz.bacs.entity.ExportCaseDataEntity;
import com.zhixin.zhfz.bacs.entity.LastCaseEntity;
import com.zhixin.zhfz.bacs.entity.StatisticsEntity;

import java.util.List;
import java.util.Map;

public interface IStatisticsMapper {

	public List<StatisticsEntity> listSerialsPerDayByAreaId(StatisticsEntity entity);

	public List<StatisticsEntity> listCasesPerDayByAreaId(StatisticsEntity entity);



	public List<LastCaseEntity> listInterrogateSerial(Map<String, Object> param);

	public int countInterrogateSerial(Map<String, Object> param);

    public List<LastCaseEntity> listOrderRequest(Map<String, Object> param);

	public int countOrderRequest(Map<String, Object> param);

    public List<LastCaseEntity> listInterrogateRecord(Map<String, Object> param);

	public int countInterrogateRecord(Map<String, Object> param);

    public List<LastCaseEntity> listWaitingRecord(Map<String, Object> param);

	public int countWaitingRecord(Map<String, Object> param);

	public List<StatisticsEntity> queryDataBySerialId(Map<String, Object> param);

	public List<ExportCaseDataEntity> listCaseCount(Map<String, Object> params);

	public List<StatisticsEntity> queryOherDataBySerialId(Map<String, Object> param);

	public List<LastCaseEntity> otherentranceList(Map<String, Object> param);

	public int countOtherentrance(Map<String, Object> param);

	public int countkyouttime(Map<String, Object> param);

	public List<AreaManage> getOrderCount();

	public List<AreaManage> getInCount();

	public List<AreaManage> getOutCount();

}