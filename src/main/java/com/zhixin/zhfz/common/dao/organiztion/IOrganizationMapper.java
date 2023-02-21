package com.zhixin.zhfz.common.dao.organiztion;

import com.zhixin.zhfz.common.entity.OrganizationEntity;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface IOrganizationMapper {
    
	public OrganizationEntity load(Integer id);
	
	public List<OrganizationEntity> list(Map<String, Object> map);
	
	public List<OrganizationEntity> listChild(Integer parentId);

	public List<OrganizationEntity> listByIds(Map<String,Object> map);

	public int count(Map<String, Object> map);

	Collection<OrganizationEntity> getOrganizationByUserId(Integer userId) throws Exception;

	public void insertOrganization(OrganizationEntity entity);

	public int insert(OrganizationEntity record);

	public void update(OrganizationEntity record);

	public void delete(int id);

	public void deleteRela(Map<String, Object> map);

	public List<OrganizationEntity> listChild(Map<String, Object> map);

	public int childCount(Map<String, Object> map);

	public List<OrganizationEntity> listChildOrg(Map<String, Object> map);

	public void updateParentId(OrganizationEntity record);

	List<OrganizationEntity> getOrgByUserId(Integer userId) throws Exception;

	OrganizationEntity getOrganizationByUserId2(Integer userId) throws Exception;

	public List<OrganizationEntity> listOrgByRegionCode(Long regionCode);

	public List<OrganizationEntity> listMapOrg();

	public List<OrganizationEntity> listOrgByOrgCode(Long orgCode);

}