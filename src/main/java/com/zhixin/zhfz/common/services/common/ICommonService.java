package com.zhixin.zhfz.common.services.common;

import java.util.List;
import java.util.Map;

public interface ICommonService {

	/**
	 * 通过ip查看ip是否存在
	 *
	 * @param ip
	 * @return
	 * @throws Exception
	 */
	Boolean checkIp(String ip) throws Exception;

	List<Map<String, Object>> listTimeoutRecode();

	void updateTimeoutRecode(Integer id);

	List<Map<String, Object>> listBaqOutAlarmData(Map<String, Object> param);

	void updateBaqOutAlarmById(Integer id);

	Map<String, Object> queryUserByCuffNo(String cuffNo);

	List<Map<String, Object>> querySerailByUser12(Map<String, Object> param);
}
