package com.zhixin.zhfz.jzgl.services.jzlxRate;

import java.util.List;
import java.util.Map;

import com.zhixin.zhfz.jzgl.entity.JzlxRateEntity;
import com.zhixin.zhfz.jzgl.entity.ZxgjEntity;

public interface IJzlxRateService {
	// 查询各个卷宗类型数量以及的占比
	public JzlxRateEntity selectCountOfJzlx(Map map);

	public JzlxRateEntity selectCountOfJzlxXs(Map map);
	
	public List<ZxgjEntity>	selectListZxgj(Map map);
	
	public Integer  selectCountOfAlarmJz(Map map );
	
	public Integer selectCountOfJz(Map map);
	
	public Integer selectAllGmCount(Map map);
	
	public Integer selectUsedGmCount(Map map);

}
