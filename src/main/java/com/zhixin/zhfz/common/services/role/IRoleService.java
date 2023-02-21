package com.zhixin.zhfz.common.services.role;


import com.zhixin.zhfz.common.entity.CheckData;
import com.zhixin.zhfz.common.entity.RoleEntity;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface IRoleService {

	/**
	 * 根据用户id获取用户角色
	 * @param id
	 * @return
	 * @throws Exception
	 */
	Collection<RoleEntity> getUserRolesByUserID(Integer id) throws Exception;

	/**
	 * 模糊匹配查询角色
	 * @param params
	 * @return
	 * @throws Exception
	 */
	Collection<RoleEntity> getRolesByLike(Map<String, Object> params) throws Exception;

	/**
	 * 计算查询结果大小
	 * @param params
	 * @return
	 * @throws Exception
	 */
	int count(Map<String, Object> params) throws Exception;

	/**
	 * 根据用户id获取用户角色
	 * @param params
	 * @return
	 * @throws Exception
	 */
	Collection<RoleEntity> getUserRolesByUserIDMap(Map<String, Object> params) throws Exception;

	/**
	 * 判断用户权限是否在权限表中
	 * @param id
	 * @return
	 * @throws Exception
	 */
	Collection<RoleEntity> getSelectedRolesByUserID(Integer id) throws Exception;

	/**
	 * 分页查询角色信息
	 * @param params
	 * @return
	 * @throws Exception
	 */
	Collection<RoleEntity> getAllRolesByMap(Map<String, Object> params) throws Exception;

	/**
	 * 查询所有角色
	 * @return
	 * @throws Exception
	 */
	Collection<RoleEntity> getAllRoles() throws Exception;

	/**
	 * 根据id查询对应角色
	 * @param id
	 * @return
	 * @throws Exception
	 */
	RoleEntity getRoleByID(Integer id) throws Exception;

	/**
	 * 根据id删除对应角色
	 * @param id
	 * @return
	 * @throws Exception
	 */
	int deleteRoleByID(Integer id) throws Exception;

	/**
	 * 增加角色信息
	 * @param role
	 * @return
	 * @throws Exception
	 */
	int insertRole(RoleEntity role) throws Exception;

	/**
	 * 根据id修改对应角色
	 * @param role
	 * @return
	 * @throws Exception
	 */
	int updateRoleByID(RoleEntity role) throws Exception;

	/**
	 * 增加角色信息
	 * @param role
	 * @return
	 * @throws Exception
	 */
	int insertSelectiveRole(RoleEntity role) throws Exception;

	/**
	 * 修改角色信息
	 * @param role
	 * @return
	 * @throws Exception
	 */
	int updateSelectiveRoleByID(RoleEntity role) throws Exception;

	/**
	 * 根据用户id检查权限
	 * @param userId
	 * @return
	 */
	List<CheckData> getCheckRoleByUserId(Integer userId);
}