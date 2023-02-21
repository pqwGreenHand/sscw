package com.zhixin.zhfz.bacs.services.outpolice;

import com.zhixin.zhfz.common.entity.UserEntity;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface OutPoliceService {



	List<UserEntity> getOutpoliceInfo(Map<String, Object> map);

	int count(Map<String, Object> map);

	void insertOutpolice(UserEntity entity);

	void updateOutpoliceByID(UserEntity entity);

	List<UserEntity> getOutpoliceByOrgId(Integer organizationId) throws Exception;


}