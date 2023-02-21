package com.zhixin.zhfz.bacs.dao.serial;

import com.zhixin.zhfz.bacs.entity.OutReportEntity;

import java.util.List;
import java.util.Map;


public interface IOutReportMapper {

	/*
	 * 获得嫌疑人的基本信息
	 */
	List<OutReportEntity> getBaseInfo(Map<String, Object> params) throws Exception;
	/*
	 * 查询暂存的随身物品
	 */
	List<OutReportEntity> getBelongs(Map<String, Object> params) throws Exception;

	/*
	 * 查询随身物品的返还情况,查出来自己判断是否有未返回物品
	 */
	List<OutReportEntity> getBelongsDetail(Map<String, Object> params) throws Exception;


	List<OutReportEntity> getWaitingRecord1(Map<String, Object> params) throws Exception;

}