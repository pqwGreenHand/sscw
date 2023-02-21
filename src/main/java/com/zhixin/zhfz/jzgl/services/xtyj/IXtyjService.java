package com.zhixin.zhfz.jzgl.services.xtyj;

import java.util.List;
import java.util.Map;

import com.zhixin.zhfz.jzgl.entity.XtyjEntity;

/**
 * IXtyjService
 * 
 * @author cuichengwei
 */
public interface IXtyjService  {
	
    public List<XtyjEntity> listXtyj(Map<String, Object> map) throws Exception;
	
	public int countXtyj(Map<String, Object> map) throws Exception;
	
}