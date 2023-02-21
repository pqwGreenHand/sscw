package com.zhixin.zhfz.jzgl.dao.ajxx;

import java.util.List;
import java.util.Map;

import com.zhixin.zhfz.jzgl.entity.AjxxEntity;

/**
 * @author xdp
 */
public interface IAjxxMapper  {
	
	public List<AjxxEntity> listAjxx(Map<String, Object> map) throws Exception;
	
	public Integer countAjxx(Map<String, Object> map) throws Exception;
	
	public void updateRkAjxx(AjxxEntity entity);
}