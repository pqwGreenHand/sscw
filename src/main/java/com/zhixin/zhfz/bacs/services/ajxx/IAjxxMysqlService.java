package com.zhixin.zhfz.bacs.services.ajxx;


import com.zhixin.zhfz.bacs.entity.JzAjxxEntity;

import java.util.List;


public interface IAjxxMysqlService  {

	public List<JzAjxxEntity> queryAjxxSlsjMaxXz();

	public void insert(JzAjxxEntity entity);

	public List<JzAjxxEntity> queryAjxxSlsjMaxXs();
}