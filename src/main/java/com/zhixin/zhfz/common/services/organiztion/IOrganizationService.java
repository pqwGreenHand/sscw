package com.zhixin.zhfz.common.services.organiztion;

import com.zhixin.zhfz.common.entity.OrganizationEntity;
import com.zhixin.zhfz.common.entity.SessionInfo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface IOrganizationService {

    OrganizationEntity getOrganizationById(Integer id);

    List<OrganizationEntity> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    List<OrganizationEntity> listChild(Integer parentId);

    List<OrganizationEntity> getAllSubOrgByParent(int parentOrgId);

    void refreshSessionOrg(SessionInfo sessionInfo, Long areaId);

    void refreshSessionOrg(SessionInfo sessionInfo);

    List<OrganizationEntity> listAllOrganization(Map<String, Object> map);

    int insertOrganization(OrganizationEntity entity);

    void updateOrganization(OrganizationEntity entity);

    void deleteOrganization(int intID);

    int childCount(Map<String, Object> map);

    List<OrganizationEntity> listChildOrg(Map<String, Object> map);

    void userImportByXls(MultipartFile file, HttpServletRequest request) throws Exception;

    List<OrganizationEntity> getOrgByUserId(Integer userId) throws Exception;

    Collection<OrganizationEntity> getOrganizationByUserId(Integer userId) throws Exception;

    OrganizationEntity getOrganizationByUserId2(Integer userId) throws Exception;

    public List<OrganizationEntity> listOrgByRegionCode(Long regionCode);
    /**
     * @author wgh
     * 获取需要标记地图位置的组织信息
     *
     * @return
     */
    public List<OrganizationEntity> listMapOrg();

    public List<OrganizationEntity> listOrgByOrgCode(Long orgCode);

}
