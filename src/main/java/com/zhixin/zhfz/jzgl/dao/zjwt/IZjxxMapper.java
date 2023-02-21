package com.zhixin.zhfz.jzgl.dao.zjwt;

import java.util.List;
import java.util.Map;

import com.zhixin.zhfz.jzgl.entity.ZjxxEntity;

public interface IZjxxMapper {
	
	public List<ZjxxEntity> listZjxx(Map<String, Object> map) throws Exception;
	
	public int countZjxx(Map<String, Object> map) throws Exception;
	
	public void insertZjxx(ZjxxEntity entity) throws Exception;
	
	public List<Map<String, String>> queryJzxx(Map<String, Object> map);
	
}
