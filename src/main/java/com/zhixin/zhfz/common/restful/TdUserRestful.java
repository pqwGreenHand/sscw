package com.zhixin.zhfz.common.restful;

import com.sun.jersey.api.core.InjectParam;
import com.sun.jersey.spi.resource.Singleton;
import com.zhixin.zhfz.common.common.AESUtil;
import com.zhixin.zhfz.common.entity.OrganizationEntity;
import com.zhixin.zhfz.common.entity.UserEntity;
import com.zhixin.zhfz.common.services.organiztion.IOrganizationService;
import com.zhixin.zhfz.common.services.user.IUserService;
import com.zhixin.zhfz.common.utils.PropertyUtil;
import org.apache.http.HttpRequest;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Path("/")
@Component
@Scope("request")
@Singleton
public class TdUserRestful {

    @InjectParam
    private IUserService userService;

    @InjectParam
    private IOrganizationService organizationService;

    private static final Logger logger = LoggerFactory.getLogger(TdUserRestful.class);

   /* *
     * 天地用户同步*/
    @GET
    @Scope("request")
    @Path("/listUserToTd/{deptCode}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Map<String, Object>> listUser(@PathParam("deptCode") Long deptCode) {
        logger.info("进入天地用户同步数据："+deptCode);
        List<Map<String, Object>> lismap = new ArrayList<>();
        try {
            List<Map<String, Object>> orgList = getOrgAndSubListByOrgCode(deptCode);
            for (Map<String, Object> org : orgList){
                Integer orgId = Integer.parseInt(org.get("deptId").toString().substring(4));
                List<UserEntity> userList = userService.getUsersByOrgId(orgId);
                for (UserEntity user : userList){
                    lismap.add(userEntityToMap(user, org));
                }
            }
        } catch (Exception e) {
            logger.info("天地用户同步数据错误！！"+e.getMessage());
        }
        return lismap;
    }

    /* *
     * 天地部门同步*/
    @GET
    @Scope("request")
    @Path("/listDeptToTd/{deptCode}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Map<String, Object>> listOrganization(@PathParam("deptCode") Long deptCode) {
        logger.info("进入天地部门同步数据：" + deptCode);
        return getOrgAndSubListByOrgCode(deptCode);
    }

    private List<Map<String, Object>> getOrgAndSubListByOrgCode(Long deptCode){
        List<Map<String, Object>> lismap = new ArrayList<>();
        List<OrganizationEntity> deptCodeOrgList = organizationService.listOrgByOrgCode(deptCode);
        if (deptCodeOrgList!=null && deptCodeOrgList.size()>0){
            for (OrganizationEntity org: deptCodeOrgList) {
                if (org!=null){
                    lismap.add(orgEntityToMap(org));
                    List<OrganizationEntity> subOrgs = organizationService.getAllSubOrgByParent(org.getId());
                    for(OrganizationEntity subOrg : subOrgs){
                        lismap.add(orgEntityToMap(subOrg));
                    }
                }
            }
        }
        return lismap;
    }

    private Map<String, Object> orgEntityToMap(OrganizationEntity organizationEntity){
        Map<String, Object> result = new HashMap<>();
        result.put("deptId", "dept"+organizationEntity.getId());
        result.put("deptCode", organizationEntity.getOrgCode());
        result.put("parentId", "dept"+organizationEntity.getParentId());
        result.put("deptName", organizationEntity.getName());
        return result;
    }

    private Map<String, Object> userEntityToMap(UserEntity userEntity, Map<String, Object> org){
        Map<String, Object> result = new HashMap<>();
        result.put("userId", "user"+userEntity.getId());
        result.put("realName", userEntity.getRealName());
        result.put("certificateNo", userEntity.getCertificateNo());
        String key = userEntity.getCertificateNo();
        int b = key.length();
        for (int j = 0; j < 16 - b; j++) {
            key += "0";
        }
        try {
            result.put("password", AESUtil.AESEncode(key, userEntity.getPassword()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        result.put("roleId", 2);
        result.put("deptId", org.get("deptId"));
        result.put("deptCode", org.get("deptCode"));
        return result;
    }
}
