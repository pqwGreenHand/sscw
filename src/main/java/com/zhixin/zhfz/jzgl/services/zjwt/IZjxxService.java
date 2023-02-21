package com.zhixin.zhfz.jzgl.services.zjwt;

import java.util.List;
import java.util.Map;

import com.zhixin.zhfz.jzgl.entity.ZjxxEntity;

/**
 * IGjxxService
 * 
 * @author cuichengwei
 */
public interface IZjxxService {

	public List<ZjxxEntity> listZjxx(Map<String, Object> map) throws Exception;

	public int countZjxx(Map<String, Object> map) throws Exception;

	public void insertZjxx(ZjxxEntity entity) throws Exception;
	
	public List<Map<String, String>> queryJzxx(Map<String, Object> map);

}