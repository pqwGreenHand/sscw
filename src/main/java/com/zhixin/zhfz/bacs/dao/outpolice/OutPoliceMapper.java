package com.zhixin.zhfz.bacs.dao.outpolice;

import com.zhixin.zhfz.common.entity.UserEntity;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface OutPoliceMapper {

	List<UserEntity> getUsersByOrgId(Long organizationId) throws Exception;

	List<UserEntity> getOutpoliceInfo(Map<String, Object> map)  ;

	int count(Map<String, Object> map);

	void insertUser(UserEntity entity);

	void updateUserByID(UserEntity entity);



	void updateUserRoles(UserEntity entity);




}