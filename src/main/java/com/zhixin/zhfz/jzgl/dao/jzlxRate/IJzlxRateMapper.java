package com.zhixin.zhfz.jzgl.dao.jzlxRate;

import java.util.List;
import java.util.Map;

import com.zhixin.zhfz.jzgl.entity.JzlxRateEntity;
import com.zhixin.zhfz.jzgl.entity.ZxgjEntity;

public interface IJzlxRateMapper {
	
	public JzlxRateEntity selectCountOfJzlx(Map map);

	public JzlxRateEntity selectCountOfJzlxXs(Map map);
	
	public List<ZxgjEntity>	selectListZxgj(Map map);
	
	public Integer  selectCountOfAlarmJz(Map map );
	
	public Integer selectCountOfJz(Map map);
	
	public Integer selectAllGmCount(Map map);
	
	public Integer selectUsedGmCount(Map map);

}