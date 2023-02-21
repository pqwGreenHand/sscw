package com.zhixin.zhfz.bacs.services.ajxx;

import com.zhixin.zhfz.bacs.dao.ajxx.IAjxxMysqlMapper;
import com.zhixin.zhfz.bacs.entity.JzAjxxEntity;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;



 @Service
public class AjxxMysqlService implements IAjxxMysqlService {

	private static Logger logger = Logger.getLogger(AjxxMysqlService.class);
	
	@Autowired
	private IAjxxMysqlMapper ajxxMapper = null;


	 @Override
	 public List<JzAjxxEntity> queryAjxxSlsjMaxXz() {
		 return ajxxMapper.queryAjxxSlsjMaxXz();
	 }

	 @Override
	 public void insert(JzAjxxEntity entity) {
		 ajxxMapper.insert(entity);
	 }

	 @Override
	 public List<JzAjxxEntity> queryAjxxSlsjMaxXs() {
		 return ajxxMapper.queryAjxxSlsjMaxXs();
	 }
 }