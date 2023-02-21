package com.zhixin.zhfz.common.services.user;

import com.zhixin.zhfz.common.dao.user.IUserMapper;
import com.zhixin.zhfz.common.entity.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service("UserService")
public class UserServiceImpl implements IUserService {

	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private IUserMapper userMapper;

	@Override
	public UserEntity getUserByLoginNameAndPassword(String loginName, String password) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("loginName", loginName);
		params.put("password", password);
		UserEntity userEntity = userMapper.getUserByLoginNameAndPassword(params);
		if (userEntity != null) {
			logger.trace("get User By Login Name And Password :\n" + userEntity.toString());
		} else {
			logger.trace("get User By Login Name And Password loginName = " + loginName + "  password = " + password);
		}
		return userEntity;
	}

	@Override
	public Collection<UserEntity> getUserInfo(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return userMapper.getUserInfo(map);
	}

	@Override
	public int count(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return userMapper.count(map);
	}

	@Override
	public Collection<UserEntity> getUserInfonoJurisdiction(Map<String, Object> map) {
		return userMapper.getUserInfonoJurisdiction(map);
	}

	@Override
	public int noJurisdictionCount(Map<String, Object> map) {
		return userMapper.noJurisdictionCount(map);
	}

	@Override
	public void insertUser(UserEntity entity) {
		// TODO Auto-generated method stub
		userMapper.insertUser(entity);
	}

	@Override
	public void updateUserByID(UserEntity entity) {
		// TODO Auto-generated method stub
		userMapper.updateUserByID(entity);
	}

	@Override
	public void deleteUserByID(Integer id) {
		// TODO Auto-generated method stub
		userMapper.deleteUserByID(id);
	}

	@Override
	public void updateUserRoles(Integer userId, Integer userRoleSet) {
		// TODO Auto-generated method stub
		UserEntity entity = new UserEntity();
		entity.setId(userId);
		entity.setRoleId(userRoleSet);
		userMapper.updateUserRoles(entity);
	}


	@Override
	public List<UserEntity> getUserByRoleId(Map<String, Object> map) {
//		return null;
		return userMapper.getAllUsers();
	}

	@Override
	public List<UserEntity> searchAllUserByGmxxId(Map<String, Object> map) {
		return userMapper.searchAllUserByGmxxId(map);
	}

	@Override
	public List<UserEntity> searchAllUser(Map<String, Object> map) {
		return userMapper.searchAllUser(map);
	}

	@Override
	public UserEntity queryUserByCertNo(String CertNo) {
		return userMapper.selectInfo(CertNo);
	}

	@Override
	public UserEntity getUserByCertificateNo(String certificateNo) throws Exception {
		UserEntity userEntity = userMapper.getUserByCertificateNo(certificateNo);
		if (userEntity != null) {
			logger.trace("get User By certificateNo :\n" + userEntity.toString());
		} else {
			logger.trace("get User By certificateNo fail certificateNo= " + certificateNo);
		}
		return userEntity;
	}
	@Override
	public void updateMobileNo(UserEntity entity) {
		userMapper.updateMobileNo(entity);
	}

	@Override
	public UserEntity queryUserNoById(Long pid) {

		return userMapper.queryUserNoById(pid);
	}

	@Override
	public UserEntity getUserByID(Integer id) throws Exception {
		UserEntity userEntity = userMapper.getUserByID(id);
		if (userEntity != null) {
			logger.trace("get User By ID :\n" + userEntity.toString());
		} else {
			logger.trace("get User By ID fail id= " + id);
		}
		return userEntity;
	}

	@Override
	public List<UserEntity> getUsersByOrgAndType(Integer organizationId, String type) throws Exception {
		List<UserEntity> users1= getUsersByOrgId(organizationId);
		List<UserEntity> users2=new ArrayList<UserEntity>();
		if(users1!=null && users1.size()>0){
			for (int i = 0; i < users1.size(); i++) {
				UserEntity user=new UserEntity();
				user=users1.get(i);
				if(user.getDescription().indexOf(type)>-1){
					users2.add(user);
				}
			}
		}
		return users2;
	}

	@Override
	public List<UserEntity> getUsersByOrgId(Integer organizationId) throws Exception {
		return userMapper.getUsersByOrgId(Long.parseLong(organizationId+""));
	}

	@Override
	public Collection<UserEntity> getUsersByLike(Map<String, Object> map) {
		return userMapper.getUsersByLike(map);
	}

	@Override
	public int getUsersByLikeCount(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return userMapper.getUsersByLikeCount(map);
	}

	@Override
	public List<UserEntity> getXbUserInfo(Map<String,Object> parm) throws Exception {
		return userMapper.getXbUserInfo(parm);
	}

	@Override
	public void changePswByID(UserEntity entity) {
		userMapper.updatePsw(entity);
	}

	@Override
	public List<Map<String, Object>> queryUserByCertNoToXF(Map<String, Object> param) {
		return userMapper.queryUserByCertNoToXF(param);
	}

	@Override
	public UserEntity findPoliceNoByCaseId(Integer caseId) throws Exception {
		return userMapper.findPoliceNoByCaseId(caseId);
	}

	@Override
	public List<UserEntity> getAllUsers() {
		return userMapper.getAllUsers();
	}
}
