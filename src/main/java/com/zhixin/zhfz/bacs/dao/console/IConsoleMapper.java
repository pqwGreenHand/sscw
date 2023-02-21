package com.zhixin.zhfz.bacs.dao.console;



import com.zhixin.zhfz.bacs.entity.LastCaseEntity;
import com.zhixin.zhfz.bacs.entity.SerialEntity;

import java.util.List;
import java.util.Map;

public interface IConsoleMapper {
	/**
	 * 各办案中心候问统计
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	List<SerialEntity> queryDetainByAreaId(Map<String, Object> map) throws Exception;
	/**
	 * 各办案中心候问人数
	 *
	 * @param map
	 * @return
	 * @throws Exception
	 */
	Map<String,Integer> queryCountByAreaId(Map<String, Object> map) throws Exception;
	/**
	 * 各办案中心入区人数
	 *
	 * @param map
	 * @return
	 * @throws Exception
	 */
	List<Map<String,Integer>> queryRenByAreaId(Map<String, Object> map) throws Exception;
	// 一天内出区 -->
	Map<String,Integer> queryChuquByDay(Map<String, Object> map) throws Exception;
	//一天内临时出区 -->
	Map<String,Integer> queryLinshiByDay(Map<String, Object> map) throws Exception;

	public int countWaitingRecord(Map<String, Object> param);
	public int countInterrogateRecord(Map<String, Object> param);
	public int countOrderRequest(Map<String, Object> param);
	public int countInterrogateSerial(Map<String, Object> param);
	public int countOtherStatus(Map<String, Object> param);
	public int countOtherentrance(Map<String, Object> param);

	int queryChuquByDay1(Map<String, Object> param);

	int queryLinshiByDay1(Map<String, Object> param);

    int countkyouttime(Map<String, Object> param);

    //已存物
	int countSscw(Map<String, Object> param);

	//已接收
	int countSsjs(Map<String, Object> param);

	//已移交
	int countSsyj(Map<String, Object> param);


	//当前办案区柜门使用数量
	Integer queryUsedCountBelongDetail(Map<String, Object> param);

	//当前办案区所有柜门数量
	Integer queryAllCountBelongDetail(Map<String, Object> param);

    int countSsjj(Map<String, Object> param);

	List<Map<String, Object>> countSscwTj();

	Map<String,Object> countTj();
}
