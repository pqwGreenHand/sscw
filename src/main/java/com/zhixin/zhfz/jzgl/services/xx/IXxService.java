package com.zhixin.zhfz.jzgl.services.xx;

import java.util.List;
import java.util.Map;

import com.zhixin.zhfz.jzgl.entity.XxEntity;

public interface IXxService {

	void insertXx(XxEntity xxEntity);
	
	/**
	 * 卷宗各类告警统计
	 * @param map
	 * @return
	 */
	List<XxEntity> listYjTj(Map<String, Object> map);

}
