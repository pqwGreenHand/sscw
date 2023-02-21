package com.zhixin.zhfz.common.services.user;

import com.zhixin.zhfz.common.entity.UserEntity;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface IUserService {

	UserEntity getUserByLoginNameAndPassword(String loginName, String password) throws Exception;

	Collection<UserEntity> getUserInfo(Map<String, Object> map);

	int count(Map<String, Object> map);

	Collection<UserEntity> getUserInfonoJurisdiction(Map<String, Object> map);

	int noJurisdictionCount(Map<String, Object> map);

	void insertUser(UserEntity entity);

	void updateUserByID(UserEntity entity);

	void deleteUserByID(Integer id);

	void updateUserRoles(Integer userId, Integer userRoleSet);


	List<UserEntity> getUserByRoleId(Map<String, Object> map);

	/**
	 * 根据卷宗柜柜门信息id查询用户
	 * 
	 * @param map
	 * @return
	 */
	List<UserEntity> searchAllUserByGmxxId(Map<String, Object> map);

	/**
	 * 柜门分配-分配用户
	 * 
	 * @param map
	 * @return
	 */
	List<UserEntity> searchAllUser(Map<String, Object> map);

	UserEntity queryUserByCertNo(String CertNo);
	
	void updateMobileNo(UserEntity entity);

	UserEntity getUserByCertificateNo(String certificateNo) throws Exception;

	UserEntity queryUserNoById(Long pid);

	UserEntity getUserByID(Integer id) throws Exception;

	List<UserEntity> getUsersByOrgAndType (Integer organizationId,String type) throws Exception;

	List<UserEntity> getUsersByOrgId(Integer organizationId) throws Exception;

	Collection<UserEntity> getUsersByLike(Map<String, Object> map);

	int getUsersByLikeCount(Map<String, Object> map);
	//获取协办民警
	List<UserEntity> getXbUserInfo(Map<String,Object> parm) throws Exception;

	void changePswByID(UserEntity entity);
	
	/**
	 * 讯飞人员
	 * @param param
	 * @return
	 */
	List<Map<String, Object>> queryUserByCertNoToXF(Map<String, Object> param);

	/**
	 *  根据caseId获取主办民警警号
	 * @param caseId
	 * @return
	 * @throws Exception
	 */
	UserEntity findPoliceNoByCaseId(Integer caseId) throws Exception;

	List<UserEntity> getAllUsers();
}