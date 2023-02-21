package com.zhixin.zhfz.common.services.role;

import com.zhixin.zhfz.common.dao.role.IRoleMapper;
import com.zhixin.zhfz.common.dao.user.IUserMapper;
import com.zhixin.zhfz.common.entity.CheckData;
import com.zhixin.zhfz.common.entity.RoleEntity;
import com.zhixin.zhfz.common.entity.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Service("RoleService")
public class RoleServiceImpl implements IRoleService {

    private static final Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);

    @Autowired
    private IRoleMapper roleMapper;
    @Autowired
    private IUserMapper userMapper;

    /**
     * 根据用户id获取用户角色
     *
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public Collection<RoleEntity> getUserRolesByUserID(Integer id) throws Exception {
        return roleMapper.getUserRolesByUserID(id);
    }

    /**
     * 模糊匹配查询角色
     *
     * @param params
     * @return
     * @throws Exception
     */
    @Override
    public Collection<RoleEntity> getRolesByLike(Map<String, Object> params) throws Exception {
        Collection<RoleEntity> roleEntities = roleMapper.getRolesByLike(params);
        if (roleEntities.size() > 0) {
            logger.trace("get Roles By Like size = " + roleEntities.size());
        } else {
            logger.trace("get Roles By Like size = 0");
        }
        return roleEntities;
    }

    /**
     * 计算查询结果大小
     *
     * @param params
     * @return
     * @throws Exception
     */
    @Override
    public int count(Map<String, Object> params) throws Exception {
        int count = roleMapper.count(params);
        logger.trace("get Roles By Like count :\n" + count);
        return count;
    }

    /**
     * 根据用户id检查权限
     * @param userId
     * @return
     */
    @Override
    public List<CheckData> getCheckRoleByUserId(Integer userId) {
        List<CheckData> list=new ArrayList<CheckData>();
        Collection<RoleEntity> roleList=new  ArrayList<RoleEntity>();
        Collection<RoleEntity> urList=new ArrayList<RoleEntity>();
        if(userId>-1)
        {
            try {
                roleList=roleMapper.getAllRoles();
                urList= roleMapper.getUserRolesByUserID(userId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        logger.error("-------------------------------");
        for(RoleEntity entity:roleList)
        {
            CheckData checkData=new CheckData((long)entity.getId(),entity.getName());
            for(RoleEntity ur:urList)
            {
                if(ur.getId().equals(entity.getId()))
                {
                    logger.error("",entity.getId());
                    checkData.setChecked(true);
                    break;
                }
            }
            list.add(checkData);
        }
        logger.debug("-------------------------------");
        return list;
    }

    /**
     * 分页查询用户权限是否在权限表中
     *
     * @param params
     * @return
     * @throws Exception
     */
    @Override
    public Collection<RoleEntity> getUserRolesByUserIDMap(Map<String, Object> params) throws Exception {
        Collection<RoleEntity> roleEntitys = roleMapper.getUserRolesByUserIDMap(params);
        Collection<RoleEntity> roleEntities = roleMapper.getAllRolesByMap(params);
        for (RoleEntity role : roleEntities) {
            for (RoleEntity role1 : roleEntitys) {
                if (role.getId().equals(role1.getId())) {
                    role.setIsSelect(true);
                    break;
                }
            }
        }
        ;
        if (roleEntities.size() > 0) {
            logger.trace("get User Roles By User ID map:\n" + roleEntities.size());
        } else {
            logger.trace("get User Roles By User ID map fail " + params);
        }
        return roleEntities;
    }

    /**
     * 判断用户权限是否在权限表中
     *
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public Collection<RoleEntity> getSelectedRolesByUserID(Integer id) throws Exception {
        Collection<RoleEntity> roleEntitys = roleMapper.getUserRolesByUserID(id);
        Collection<RoleEntity> roleEntities = roleMapper.getAllRoles();
        for (RoleEntity role : roleEntities) {
            for (RoleEntity role1 : roleEntitys) {
                if (role.getId().equals(role1.getId())) {
                    role.setIsSelect(true);
                    break;
                }
            }
        }
        if (roleEntities.size() > 0) {
            logger.trace("get User Roles By User ID :\n" + roleEntities.size());
        } else {
            logger.trace("get User Roles By User ID fail id= " + id);
        }
        return roleEntities;
    }

    /**
     * 分页查询角色信息
     *
     * @param params
     * @return
     * @throws Exception
     */
    @Override
    public Collection<RoleEntity> getAllRolesByMap(Map<String, Object> params) throws Exception {
        Collection<RoleEntity> roleEntities = roleMapper.getAllRolesByMap(params);
        if (roleEntities.size() > 0) {
            logger.trace("get Roles By Like size = " + roleEntities.size());
        } else {
            logger.trace("get Roles By Like size = 0");
        }
        return roleEntities;
    }

    /**
     * 查询所有角色
     *
     * @return
     * @throws Exception
     */
    @Override
    public Collection<RoleEntity> getAllRoles() throws Exception {
        Collection<RoleEntity> roleEntities = roleMapper.getAllRoles();
        if (roleEntities.size() > 0) {
            logger.trace("get Roles By Like size = " + roleEntities.size());
        } else {
            logger.trace("get Roles By Like size = 0");
        }
        return roleEntities;
    }

    /**
     * 根据id查询对应角色
     *
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public RoleEntity getRoleByID(Integer id) throws Exception {
        RoleEntity roleEntity = roleMapper.getRoleByID(id);
        if (roleEntity != null) {
            logger.trace("get Role By ID :\n" + roleEntity.toString());
        } else {
            logger.trace("get Role By ID fail id= " + id);
        }
        return roleEntity;
    }

    /**
     * 根据id删除对应角色
     *
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public int deleteRoleByID(Integer id) throws Exception {
        int result = roleMapper.deleteRoleByID(id);
        logger.trace("delete Role By ID = " + id);
        return result;
    }

    /**
     * 增加角色信息
     *
     * @param role
     * @return
     * @throws Exception
     */
    @Override
    public int insertRole(RoleEntity role) throws Exception {
        int result = roleMapper.insertRole(role);
        logger.trace("insert Role :\n" + role.toString());
        return result;
    }

    /**
     * 根据id修改对应角色
     *
     * @param role
     * @return
     * @throws Exception
     */
    @Override
    public int updateRoleByID(RoleEntity role) throws Exception {
        int result = roleMapper.updateRoleByID(role);
        logger.trace("update Role By ID :\n" + role.toString());
        return result;
    }

    /**
     * 增加角色信息
     *
     * @param role
     * @return
     * @throws Exception
     */
    @Override
    public int insertSelectiveRole(RoleEntity role) throws Exception {
        int result = roleMapper.insertSelectiveRole(role);
        logger.trace("insert Role if not null :\n" + role.toString());
        return result;
    }

    /**
     * 修改角色信息
     *
     * @param role
     * @return
     * @throws Exception
     */
    @Override
    public int updateSelectiveRoleByID(RoleEntity role) throws Exception {
        int result = roleMapper.updateSelectiveRoleByID(role);
        logger.trace("update Role By ID if not null :\n" + role.toString());
        return result;
    }
}
