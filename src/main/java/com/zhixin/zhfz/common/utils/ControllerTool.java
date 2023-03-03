package com.zhixin.zhfz.common.utils;

import com.zhixin.zhfz.bacs.entity.AreaEntity;
import com.zhixin.zhfz.common.entity.OrganizationEntity;
import com.zhixin.zhfz.common.entity.RoleEntity;
import com.zhixin.zhfz.common.entity.SessionInfo;
import com.zhixin.zhfz.common.entity.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class ControllerTool {

    private static final Logger logger = LoggerFactory.getLogger(ControllerTool.class);

    @Autowired
    private static PowerCacheUtil powerCacheUtil = null;

    public static SessionInfo getSessionInfo(HttpServletRequest request) {
        SessionInfo info = (SessionInfo) request.getSession().getAttribute("sessionInfo");
        if (info == null) {
            logger.info("Can not getSessionInfo from session!!!");
            info = new SessionInfo();
        }
        return info;
    }

    public static UserEntity getUser(HttpServletRequest request) {
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        return sessionInfo.getUser();
    }

    /**
     * 查询登陆用户的id
     *
     * @param request
     * @return
     */
    public static Integer getUserID(HttpServletRequest request) {
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        return sessionInfo.getUser().getId();
    }

    public static String getLoginName(HttpServletRequest request) {
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        return sessionInfo.getUser().getLoginName();
    }

    public static String getRealName(HttpServletRequest request) {
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        return sessionInfo.getUser().getRealName();
    }

    public static String getRoleName(HttpServletRequest request) {
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        RoleEntity role = sessionInfo.getRole();
        return role.getName();
    }

    public static String getClienIp(HttpServletRequest request) {
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        return sessionInfo.getClientIP();
    }

    public static String getAreasInSql(String column, List<AreaEntity> areas) {
        if (areas == null || areas.size() == 0) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(" ( ").append(column).append(" in(");
        int size = areas.size();
        for (int i = 0; i < size; i++) {
            sb.append(areas.get(i).getId());
            if (i < size - 1) {
                sb.append(",");
            }
        }
        sb.append(")) ");
        return sb.toString();
    }

    /**
     * 获取办案中心id
     * @param request
     * @return
     */
    public static Integer getCurrentAreaID(HttpServletRequest request) {
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        if (sessionInfo.getCurrentArea() != null && sessionInfo.getCurrentArea().getId() != null) {
            return sessionInfo.getCurrentArea().getId();
        }
        return null;
    }


    public static Integer getCurrentOrgID(HttpServletRequest request) {
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        if (sessionInfo.getCurrentOrg() != null && sessionInfo.getCurrentOrg().getId() != null) {
            return sessionInfo.getCurrentOrg().getId();
        }
        return null;
    }

    public static String getOrgsInSql(String column, List<OrganizationEntity> orgs) {
        if (orgs == null || orgs.size() == 0) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(" ( ").append(column).append(" in(");
        int size = orgs.size();
        for (int i = 0; i < size; i++) {
            sb.append(orgs.get(i).getId());
            if (i < size - 1) {
                sb.append(",");
            }
        }
        sb.append(")) ");
        return sb.toString();
    }

    /**
     * 通过HttpServletRequest返回IP地址
     *
     * @param request HttpServletRequest
     * @return ip String
     * @throws Exception
     */
    public static String getClientIpAddr(HttpServletRequest request) throws Exception {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 查询角色权限
     *
     * @param request
     * @return
     */
    public static int getRoleDataAuth(HttpServletRequest request) {
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        RoleEntity role = sessionInfo.getRole();
        return role.getBacsDataAuth();
    }

    public static AreaEntity getCurrentArea(HttpServletRequest request) {
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        return sessionInfo.getCurrentArea();
    }

    /**
     * 涉案财物
     **/
    public static int getRoleDataAuthForINV(HttpServletRequest request) {
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        RoleEntity role = sessionInfo.getRole();
        return role.getSacwDataAuth();
    }

    /**
     * 涉案财物
     **/
    public static int getRoleDataAuthForJZ(HttpServletRequest request) {
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        RoleEntity role = sessionInfo.getRole();
        return role.getJzglDataAuth();
    }

    /**
     * 绩效考评
     * @param request
     * @return
     */
    public static int getRoleDataAuthForEva(HttpServletRequest request) {
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        RoleEntity role = sessionInfo.getRole();
        logger.info("考评role===" + role.getJxkpDataAuth());
        return role.getJxkpDataAuth();
    }

    /**
     * 受立案
     * @param request
     * @return
     */
    public static int getRoleDataAuthForSla(HttpServletRequest request) {
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        RoleEntity role = sessionInfo.getRole();
        logger.info("考评role===" + role.getSlaDataAuth());
        return role.getSlaDataAuth();
    }

    /**
     * 综合管理
     * @param request
     * @return
     */
    public static int getRoleDataAuthForZhgl(HttpServletRequest request) {
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        RoleEntity role = sessionInfo.getRole();
        logger.info("考评role===" + role.getZhglDataAuth());
        return role.getZhglDataAuth();
    }

    /**
     * 办案场所配置
     * @param request
     * @return
     */
    public static int getRoleDataAuthForBacsConf(HttpServletRequest request) {
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        RoleEntity role = sessionInfo.getRole();
        logger.info("考评role===" + role.getBacsConfigure());
        return role.getBacsConfigure();
    }

    /**
     * 涉案财物配置
     * @param request
     * @return
     */
    public static int getRoleDataAuthForSacwConf(HttpServletRequest request) {
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        RoleEntity role = sessionInfo.getRole();
        logger.info("考评role===" + role.getSacwConfigure());
        return role.getSacwConfigure();
    }

    /**
     * 卷宗管理配置
     * @param request
     * @return
     */
    public static int getRoleDataAuthForJzglConf(HttpServletRequest request) {
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        RoleEntity role = sessionInfo.getRole();
        logger.info("考评role===" + role.getJzglConfigure());
        return role.getJzglConfigure();
    }
    /**
     * 绩效考评配置
     * @param request
     * @return
     */
    public static int getRoleDataAuthForJxkpConf(HttpServletRequest request) {
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        RoleEntity role = sessionInfo.getRole();
        logger.info("考评role===" + role.getJxkpConfigure());
        return role.getJxkpConfigure();
    }
    /**
     * 受立案配置
     * @param request
     * @return
     */
    public static int getRoleDataAuthForSlaConf(HttpServletRequest request) {
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        RoleEntity role = sessionInfo.getRole();
        logger.info("考评role===" + role.getSlaConfigure());
        return role.getSlaConfigure();
    }
    /**
     * 系统配置
     * @param request
     * @return
     */
    public static int getRoleDataAuthForXtConf(HttpServletRequest request) {
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        RoleEntity role = sessionInfo.getRole();
        logger.info("考评role===" + role.getXtConfigure());
        return role.getXtConfigure();
    }

    public static Map<String, Object> mapFilter(Map<String, Object> pageMap) {
        Map<String, Object> map = new HashMap<String, Object>();
        for (String key : pageMap.keySet()) {
            Object obj = pageMap.get(key);
            if (obj == null) {
                continue;
            }
            try {
                String value = obj.toString();
                if ("page".equalsIgnoreCase(key) || "rows".equalsIgnoreCase(key) || "xpage".equalsIgnoreCase(key)
                        || "xrows".equalsIgnoreCase(key)) {
                    map.put(key, new Integer(obj.toString()));
                    if ("page".equals(key)) {
                        map.put("pageStart", (Integer.parseInt(pageMap.get("page").toString()) - 1)
                                * Integer.parseInt(pageMap.get("rows").toString()));
                    }
                    if ("xpage".equals(key)) {
                        map.put("pageStart", (Integer.parseInt(pageMap.get("xpage").toString()) - 1)
                                * Integer.parseInt(pageMap.get("xrows").toString()));
                    }
                } else {
                    if (key.endsWith("_t")) {
                        // _t代表text 用String
                        if (value.length() > 0) {
                            map.put(cutKey(key, "_t"), value);
                        }
                    } else if (key.endsWith("_i")) {
                        // _i代表int
                        map.put(cutKey(key, "_i"), new Integer(value));
                    } else if (key.endsWith("_l")) {
                        // _i代表long
                        map.put(cutKey(key, "_l"), new Long(value));
                    } else if (key.endsWith("_d")) {
                        // _i代表date
                        SimpleDateFormat sdf = null;
                        if (value.length() < 4) {
                            continue;
                        } else if (value.length() == 4) {
                            sdf = new SimpleDateFormat("yyyy");
                        } else if (value.length() == 7) {
                            sdf = new SimpleDateFormat("yyyy-MM");
                        } else if (value.length() == 10) {
                            sdf = new SimpleDateFormat("yyyy-MM-dd");
                        } else {
                            sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        }
                        Date date = sdf.parse(value);
                        map.put(cutKey(key, "_d"), date);
                    } else {
                        if (value.trim().length() > 0) {
                            map.put(key, value);
                        }
                    }

                }
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        return map;
    }

    private static String cutKey(String key, String word) {
        int i = key.lastIndexOf(word);
        if (i < 0) {
            return key;
        } else {
            return key.substring(0, i);
        }
    }

    // 根据部门集合获取用户Id集合
    public static List<Long> queryUsersByOrgs(List<Integer> orgs) {
        List<Long> userIds = new ArrayList<>();
        if (powerCacheUtil == null) {
            powerCacheUtil = new PowerCacheUtil();
        }
        if (orgs != null) {
            for (Integer org : orgs) {
                userIds.addAll(powerCacheUtil.getUsersByOrg(org));
            }
        }
        return userIds;
    }

    // 根据部门集合获取用户id的in语句(形如：in (1,2,3))
    public static String queryUsersInSqlByOrgs(List<Integer> orgs) {
        List<Long> users = queryUsersByOrgs(orgs);
        if (users != null) {
            StringBuffer sb = new StringBuffer();
            sb.append(" in (");
            for (int i = 0; i < users.size(); i++) {
                sb.append(users.get(i));
                if (i < (users.size() - 1)) {
                    sb.append(",");
                }
            }
            sb.append(")");
            return sb.toString();
        }
        return null;
    }

    // 根据部门集合获取用户Id集合
    public static List<Long> queryUsersByOrganizations(List<OrganizationEntity> orgs) {
        List<Long> userIds = new ArrayList<>();
        try {
            if (powerCacheUtil == null) {
                powerCacheUtil = PowerCacheUtil.class.newInstance();
            }
        } catch (Exception e) {
            logger.info("queryUsersByOrganizations=====" + e);
        } finally {
            if (orgs != null) {
                for (OrganizationEntity org : orgs) {
                    userIds.addAll(powerCacheUtil.getUsersByOrg(org.getId()));
                }
            }
            return userIds;
        }

    }

    // 根据部门集合获取用户id的in语句(形如：in (1,2,3))
    public static String queryUsersInSqlByOrganizations(List<OrganizationEntity> orgs) {
        List<Long> users = queryUsersByOrganizations(orgs);
        if (users != null) {
            StringBuffer sb = new StringBuffer();
            sb.append(" in (");
            for (int i = 0; i < users.size(); i++) {
                sb.append(users.get(i));
                if (i < (users.size() - 1)) {
                    sb.append(",");
                }
            }
            sb.append(")");
            return sb.toString();
        }
        return null;
    }

    /**
     * 检察两个URL是否绝对匹配，默认url是开始是/
     *
     * @param url
     * @param checkLink
     * @return
     */
    public static boolean isUrlEqual(String url, String checkLink) {
        if (checkLink == null || checkLink.length() == 0) {
            return false;
        }
        //先检查是否都以"/"开头
        boolean k = checkLink.startsWith("/");
        if (k) {
            return url.equalsIgnoreCase(checkLink);
        } else {
            return url.equalsIgnoreCase("/" + checkLink);
        }
    }

    // 根据部门集合获取areaList集合
    public static List<AreaEntity> queryAreaListByOrganizations(List<OrganizationEntity> orgs) {
        List<AreaEntity> areaList = new ArrayList<>();
        try {
            if (powerCacheUtil == null) {
                powerCacheUtil = PowerCacheUtil.class.newInstance();
            }
        } catch (Exception e) {
            logger.info("queryUsersByOrganizations=====" + e);
        } finally {
            if (orgs != null) {
                if (orgs != null) {
                    for (OrganizationEntity org : orgs) {
                        areaList.addAll(powerCacheUtil.getAreaListByOrg(org.getId()));
                    }
                    Collections.sort(areaList, new Comparator<AreaEntity>() {
                        public int compare(AreaEntity arg0, AreaEntity arg1) {
                            return arg0.getType().compareTo(arg1.getType());
                        }
                    });
                }
            }
            return areaList;
        }

    }

    // 根据部门集合获取areaId的in语句(形如：in (1,2,3))
    public static String queryAreaIdByOrganizations(List<OrganizationEntity> orgs) {
        List<AreaEntity> areaIds = queryAreaListByOrganizations(orgs);

        if (areaIds != null) {
            StringBuffer sb = new StringBuffer();
            sb.append(" in (");
            for (int i = 0; i < areaIds.size(); i++) {
                sb.append(areaIds.get(i).getId());
                if (i < (areaIds.size() - 1)) {
                    sb.append(",");
                }
            }
            sb.append(")");
            return sb.toString();
        }
        return null;
    }

}
