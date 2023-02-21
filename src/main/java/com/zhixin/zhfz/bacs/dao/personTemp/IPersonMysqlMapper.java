package com.zhixin.zhfz.bacs.dao.personTemp;

import com.zhixin.zhfz.bacs.entity.JzPersonEntity;

import java.util.List;

public interface IPersonMysqlMapper {

	List<JzPersonEntity> queryPersonLrrqMaxXz();

	List<JzPersonEntity> queryPersonLrrqMaxXs();

	void insert(JzPersonEntity entity);

}