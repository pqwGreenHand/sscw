package com.zhixin.zhfz.bacs.services.console;


import com.zhixin.zhfz.bacs.entity.LastCaseEntity;
import com.zhixin.zhfz.bacs.entity.SerialEntity;

import java.util.List;
import java.util.Map;

public interface IConsoleService {
	/**
	 * 各办案中心候问统计
	 *
	 * @return
	 * @throws Exception
	 */
	List<SerialEntity> queryDetainByAreaId(Integer areaId, String str) throws Exception;
	/**
	 * 各办案中心候问人数
	 *
	 * @return
	 * @throws Exception
	 */
	Map<String,Integer> queryCountByAreaId(Integer areaId, String str) throws Exception;
	/**
	 * 各办案中心入区人数
	 *
	 * @return
	 * @throws Exception
	 */
	List<Map<String,Integer>> queryRenByAreaId(Integer areaId, String str) throws Exception;
	// 一天内出区 -->
	Map<String,Integer> queryChuquByDay(Integer areaId, String str) throws Exception;
	//一天内临时出区 -->
	Map<String,Integer> queryLinshiByDay(Integer areaId, String str) throws Exception;

//	public List<LastCaseEntity> listInterrogateSerial(Map<String, Object> param);

	public int countWaitingRecord(Map<String, Object> param);

	public int countInterrogateSerial(Map<String, Object> param);

	public int countOtherStatus(Map<String, Object> param);

	public int countInterrogateRecord(Map<String, Object> param);

	public int countOrderRequest(Map<String, Object> param);

	public int countOtherentrance(Map<String, Object> param);

	int queryChuquByDay1(Map<String, Object> param);

	int queryLinshiByDay1(Map<String, Object> param);

    int countkyouttime(Map<String, Object> param);

    List<SerialEntity> listJyOutTime(Map<String, Object> param);

	int countJyOutTime(Map<String, Object> param);

	int countSscw(Map<String, Object> param);

    int countSsyj(Map<String, Object> param);

    int countSsjs(Map<String, Object> param);

	/**
	 * 当前办案中心柜门使用数量
	 *
	 * @return
	 * @throws Exception
	 */
	Integer queryUsedCountBelongDetail(Map<String, Object> param);


	/**
	 * 当前办案中心所有柜门数量
	 *
	 * @return
	 * @throws Exception
	 */
	Integer queryAllCountBelongDetail(Map<String, Object> param);


    int countSsjj(Map<String, Object> param);

	List<Map<String, Object>> countSscwTj();

    Map<String,Object> countTj();

    int countZsacs(Map<String, Object> param);
}
