package com.zhixin.zhfz.bacs.services.geo;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

public interface IGeoMapService {

	/**
	 * @return
	 * @throws Exception
	 */
	JSONObject getRegionGeoJson() throws Exception;

	/**
	 * 获取处理过的json
	 * 预读默认json ,去除特殊位置
	 * 然后加上数据库内特殊位置的子json
	 * @param regionCods
	 * @return
	 * @throws Exception
	 */
	JSONObject getRegionGeoJsonReplaceChildren(List<Long> regionCods) throws Exception;
	
	
	JSONObject getRegionGeoJsonByProp() throws Exception;
	
	/**
	 * 获取组织(派出所)的GEO位置信息
	 * @return
	 * @throws Exception
	 */
	public JSONObject getOrgPointGeoJson() throws Exception;
}
