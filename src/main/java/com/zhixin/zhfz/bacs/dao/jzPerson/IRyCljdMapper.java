/*
 * FileName: AjxxMysqlEntity.java
 * auto create by wangguhua 2016
 * Author:   
 * Date:     2017-4-6 16:33:43
 * Description: IAjxxMysqlMapper实体类   
 */
 
package com.zhixin.zhfz.bacs.dao.jzPerson;


import com.zhixin.zhfz.bacs.entity.RycljdMysqlEntity;

import java.util.List;
import java.util.Map;


public interface IRyCljdMapper {

	List<RycljdMysqlEntity> queryCljdPzsjMaxXs();

	void insertRYCLJD(RycljdMysqlEntity entity);

	List<Map<String, Object>> queryCljdMap();

	List<Map<String, Object>> queryAjztMap();

	List<Map<String, Object>> queryOrgMap();

	List<Map<String, Object>> queryUserMap();
	
}