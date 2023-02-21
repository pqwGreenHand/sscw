package com.zhixin.zhfz.bacs.dao.archives;

import java.util.List;
import java.util.Map;

import com.zhixin.zhfz.bacs.entity.DvdLogEntity;

public interface IDvdLogMapper {
	
	public List<DvdLogEntity> list(Map<String, Object> map);
	
	public int count(Map<String, Object> map);

    public void insert(DvdLogEntity dvdLogEntity);
	//查询全部dvd记录
	public List<DvdLogEntity> listByAll(Map<String,Object> map);

}