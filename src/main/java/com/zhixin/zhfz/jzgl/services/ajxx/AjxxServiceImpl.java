package com.zhixin.zhfz.jzgl.services.ajxx;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhixin.zhfz.jzgl.dao.ajxx.IAjxxMapper;
import com.zhixin.zhfz.jzgl.entity.AjxxEntity;

/**
 * 
 * @author xdp
 */
 @Service
public class AjxxServiceImpl implements IAjxxService {

	
	@Autowired
	private IAjxxMapper ajxxMapper = null;
	 
	
	@Override
	public List<AjxxEntity> listAjxx(Map<String,Object> map) throws Exception{
		return ajxxMapper.listAjxx(map);
	}
	
	@Override
	public Integer countAjxx(Map<String,Object> map) throws Exception{
		return ajxxMapper.countAjxx(map);
	}

	@Override
	public void updateRkAjxx(AjxxEntity entity) throws Exception {
		ajxxMapper.updateRkAjxx(entity);
	}
}