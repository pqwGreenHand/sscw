package com.zhixin.zhfz.bacs.services.security;


import com.zhixin.zhfz.bacs.dao.security.ISecurityMapper;
import com.zhixin.zhfz.bacs.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SecurityServiceImpl implements ISecurityService{
	
	@Autowired
	private ISecurityMapper securityMapper;

	@Override
	public List<SecurityEntity> list(Map<String, Object> params) throws Exception {
		return securityMapper.list(params);
	}

	@Override
	public int count(Map<String, Object> params) throws Exception {
		return securityMapper.count(params);
	}

	@Override
	public void insert(SecurityEntity entity) throws Exception {
		securityMapper.insert(entity);		
	}

	@Override
	public List<SecurityPhotoEntity> getImages(Map<String, Object> params) {
		return securityMapper.getImages(params);
	}

	@Override
	public int selectSecurityCount(String serialId) {
		return securityMapper.selectSecurityCount(serialId);
	}

	@Override
	public void insertSecurityMarkes(SecurityPhotoEntity securityPhotoEntity) {
		securityMapper.insertSecurityMarkes(securityPhotoEntity);
	}


	@Override
	public SecurityEntity getOneSecurity(SecurityEntity temSecurity) {
		List<SecurityEntity> list=securityMapper.getOneSecurity(temSecurity);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}


	@Override
	public void deleteSecurityPhotoMarkes(Map<String, Object> map) {
		securityMapper.deleteSecurityPhotoMarkes(map);
	}

	@Override
	public SecurityEntity selectRepeatPerson(String serialNo) {
		return securityMapper.selectRepeatPerson(serialNo);
	}

	@Override
	public List<SecurityEntity> listHistory(Map<String, Object> params) {
		return securityMapper.listHistory(params);
	}

	@Override
	public void update(SecurityEntity entity) {
		securityMapper.update(entity);
	}
}
