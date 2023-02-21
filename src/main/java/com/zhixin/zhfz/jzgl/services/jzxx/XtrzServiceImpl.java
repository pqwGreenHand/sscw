/*
 * FileName: XtrzMysqlService.java
 * auto create by wangguhua
 * Author:   
 * Description: XtrzMysqlService实体类   
 */
 
package com.zhixin.zhfz.jzgl.services.jzxx;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhixin.zhfz.jzgl.dao.jzxx.IXtrzMapper;
import com.zhixin.zhfz.jzgl.entity.XtrzEntity;

/**
 * 实体类XtrzMysqlService table: xtrz
 * 
 * @author wangguhua
 */
 @Service
public class XtrzServiceImpl implements IXtrzService {

	private static Logger logger = Logger.getLogger(XtrzServiceImpl.class);
	
	@Autowired
	private IXtrzMapper xtrzMapper = null;

	@Override
	public List<XtrzEntity> listAllXtrz() throws Exception{
		return xtrzMapper.listAllXtrz();
	}
	
	@Override
	public Integer countAllXtrz() throws Exception{
		return xtrzMapper.countAllXtrz();
	}
	
	@Override
	public List<XtrzEntity> listXtrz(Map<String,Object> map) throws Exception{
		return xtrzMapper.listXtrz(map);
	}
	
	@Override
	public Integer countXtrz(Map<String,Object> map) throws Exception{
		return xtrzMapper.countXtrz(map);
	}
	
	@Override
	public XtrzEntity getXtrzById(Long id) throws Exception{
		return xtrzMapper.getXtrzById(id);
	}

	@Override
	public void insertXtrz(XtrzEntity entity) throws Exception{
		if(entity.getCzlx()==0){
			entity.setYsj(null);
			entity.setXsj(null);
			xtrzMapper.insertXtrz(entity);
		}
		if(entity.getCzlx()==1){
			Object obj = entity.getObj();
			entity.setYsj(null);
			entity.setXsj(obj.toString());
			xtrzMapper.insertXtrz(entity);
		}
		if(entity.getCzlx()==2){
			List<Object> obj = (List<Object>) entity.getObj();
			entity.setYsj(obj.get(0).toString());
			entity.setXsj(obj.get(1).toString());
			xtrzMapper.insertXtrz(entity);
		}
		if(entity.getCzlx()==3){
			Object obj = entity.getObj();
			entity.setYsj(obj.toString());
			entity.setXsj(null);
			xtrzMapper.insertXtrz(entity);
		}
		if(entity.getCzlx()==4){
			List<Object> obj = (List<Object>) entity.getObj();
			if(obj.get(0)!=null){
			entity.setYsj(obj.get(0).toString());
			}
			entity.setXsj(obj.get(1).toString());
			xtrzMapper.insertXtrz(entity);
		}
	}
    
	
}