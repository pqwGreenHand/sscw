package com.zhixin.zhfz.bacs.dao.ajxx;

import com.zhixin.zhfz.bacs.entity.JzAjxxEntity;

import java.util.List;

public interface IAjxxMysqlMapper {

	public List<JzAjxxEntity> queryAjxxSlsjMaxXz();

	public void insert(JzAjxxEntity entity);

	public List<JzAjxxEntity> queryAjxxSlsjMaxXs();

}