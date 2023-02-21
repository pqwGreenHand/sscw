package com.zhixin.zhfz.bacs.services.personTemp;

import com.zhixin.zhfz.bacs.dao.personTemp.IPersonMysqlMapper;
import com.zhixin.zhfz.bacs.entity.JzPersonEntity;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 实体类RecordInfoService table: ajxx
 * 
 * @author cuichengwei
 */
 @Service
public class JzPersonService implements IjzPersonService {

	private static Logger logger = Logger.getLogger(JzPersonService.class);

	@Autowired
    private IPersonMysqlMapper personMysqlMapper;


    @Override
    public List<JzPersonEntity> queryPersonLrrqMaxXz() {
        return personMysqlMapper.queryPersonLrrqMaxXz();
    }

    @Override
    public List<JzPersonEntity> queryPersonLrrqMaxXs() {
        return personMysqlMapper.queryPersonLrrqMaxXs();
    }

    @Override
    public void insert(JzPersonEntity entity) {
        personMysqlMapper.insert(entity);
    }
}