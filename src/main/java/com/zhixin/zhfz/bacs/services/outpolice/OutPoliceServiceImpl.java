package com.zhixin.zhfz.bacs.services.outpolice;

import com.zhixin.zhfz.bacs.dao.outpolice.OutPoliceMapper;
import com.zhixin.zhfz.common.entity.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.*;


@Service
public class OutPoliceServiceImpl implements OutPoliceService {

	private static final Logger logger = LoggerFactory.getLogger(OutPoliceServiceImpl.class);

	@Autowired
	private OutPoliceMapper outPoliceMapper;


	@Override
	public List<UserEntity> getOutpoliceInfo(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return outPoliceMapper.getOutpoliceInfo(map);
	}

	@Override
	public int count(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return outPoliceMapper.count(map);
	}

	@Override
	public void insertOutpolice(UserEntity entity) {
		// TODO Auto-generated method stub
		outPoliceMapper.insertUser(entity);
	}

	@Override
	public void updateOutpoliceByID(UserEntity entity) {
		// TODO Auto-generated method stub
		outPoliceMapper.updateUserByID(entity);
	}


	@Override
	public List<UserEntity> getOutpoliceByOrgId(Integer organizationId) throws Exception {
		return outPoliceMapper.getUsersByOrgId(Long.parseLong(organizationId + ""));
	}
}
