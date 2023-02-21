package com.zhixin.zhfz.jzgl.dao.jdrd;

import java.util.List;
import java.util.Map;

import com.zhixin.zhfz.jzgl.entity.GjxxEntity;

public interface IGjxxMapper {
	
	public List<GjxxEntity> listGjxx(Map<String, Object> map) throws Exception;
	
	public int countGjxx(Map<String, Object> map) throws Exception;
	
	public void updateGjxx(GjxxEntity entity) throws Exception;

	public GjxxEntity queryGjxxSame(GjxxEntity gj);

	public void insertGjxxLXBC(GjxxEntity gj);
	
}
