package com.zhixin.zhfz.jzgl.services.console;

import java.util.List;
import java.util.Map;

import com.zhixin.zhfz.jzgl.entity.JzlxRateEntity;


public interface ITjfxService {
	
	/**
	 * 查询各单位柜门数部门
	 * @param mapParam
	 * @return
	 */
	public List<JzlxRateEntity> selectAllGmxx(Map<String, Object> mapParam);
    /**
     * 查询柜门使用的数量
     * @param mapParam
     * @return
     */
	public List<JzlxRateEntity> selectUsedGmxx(Map<String, Object> mapParam);
	
	/**
	 * 办案单位告警总数
	 * @param mapParam
	 * @return
	 */
	public List<Map<String, String>> queryAllDwGj(Map<String, Object> mapParam);

	/**
	 * 民警告警总数
	 * @param mapParam
	 * @return
	 */
	public List<Map<String, String>> queryAllMjGj(Map<String, Object> mapParam);
}
