package com.zhixin.zhfz.bacs.services.security;

import com.zhixin.zhfz.bacs.entity.*;

import java.util.List;
import java.util.Map;


public interface ISecurityService {

	List<SecurityEntity> list(Map<String, Object> params) throws Exception;
	
	int count(Map<String, Object> params) throws Exception;

	void insert(SecurityEntity entity) throws Exception;
	
	List<SecurityPhotoEntity> getImages(Map<String, Object> params);

	int selectSecurityCount(String serialId);

	void insertSecurityMarkes(SecurityPhotoEntity securityPhotoEntity);

    SecurityEntity getOneSecurity(SecurityEntity temSecurity);


	void deleteSecurityPhotoMarkes(Map<String ,Object> map);

	SecurityEntity selectRepeatPerson(String serialNo);

	List<SecurityEntity> listHistory(Map<String, Object> params);

	void update(SecurityEntity entity);
}
