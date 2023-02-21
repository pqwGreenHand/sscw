package com.zhixin.zhfz.jzgl.dao.console;

import java.util.List;
import java.util.Map;

import com.zhixin.zhfz.jzgl.entity.JzlxRateEntity;


public interface ITjfxMapper {
	public List<JzlxRateEntity> selectAllGmxx(Map<String, Object> map);
	public List<JzlxRateEntity> selectUsedGmxx(Map<String, Object> map);
	public List<Map<String, String>> queryAllDwGj(Map<String, Object> map);

	public List<Map<String, String>> queryAllMjGj(Map<String, Object> map);

}
