package com.zhixin.zhfz.common.entity;


import com.zhixin.zhfz.bacs.entity.AreaEntity;
import com.zhixin.zhfz.sacw.entity.WareHouseEntity;

import java.util.ArrayList;
import java.util.List;

public class SessionInfo implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    private UserEntity user;//用户信息
    private RoleEntity role;//角色信息
    private String clientIP;//客户端IP
    private AreaEntity currentArea;//本部门的办案场所信息
    private List<AreaEntity> currentAndSubArea;//本部门下面的所有办案场所
    private List<AreaEntity> superAndSubArea;//上级部门下面的所有办案场所
    private OrganizationEntity currentOrg;//本部门信息
    private List<OrganizationEntity> currentAndSubOrg;//本部门及所有下级部门
    private List<OrganizationEntity> superAndSubOrg;//上级部门及所有下级部门
    private WareHouseEntity currentWarehoouse;//本部门的涉案场所信息
    private List<WareHouseEntity> currentAndSubWarehoouse;//本部门下面的所有涉案场所
    private List<WareHouseEntity> superAndSubWarehoouse;//上级部门下面的所有涉案场所
    private String currentOrgPid;//本部门pid
    private String currentAndSubOrgPid;//本部门及所有下级部门pid
    private String superAndSubOrgPid;//上级部门及所有下级部门pid

    public String getSuperAndSubOrgPid() {
        return superAndSubOrgPid;
    }

    public void setSuperAndSubOrgPid(String superAndSubOrgPid) {
        this.superAndSubOrgPid = superAndSubOrgPid;
    }

    public String getCurrentAndSubOrgPid() {
        return currentAndSubOrgPid;
    }

    public void setCurrentAndSubOrgPid(String currentAndSubOrgPid) {
        this.currentAndSubOrgPid = currentAndSubOrgPid;
    }

    public String getCurrentOrgPid() {
        return currentOrgPid;
    }

    public void setCurrentOrgPid(String currentOrgPid) {
        this.currentOrgPid = currentOrgPid;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public RoleEntity getRole() {
        return role;
    }

    public void setRole(RoleEntity role) {
        this.role = role;
    }

    public String getClientIP() {
        return clientIP;
    }

    public void setClientIP(String clientIP) {
        this.clientIP = clientIP;
    }

    public AreaEntity getCurrentArea() {
        return currentArea;
    }

    public void setCurrentArea(AreaEntity currentArea) {
        this.currentArea = currentArea;
    }

    public List<AreaEntity> getCurrentAndSubArea() {
        return currentAndSubArea;
    }

    public void setCurrentAndSubArea(List<AreaEntity> currentAndSubArea) {
        this.currentAndSubArea = currentAndSubArea;
    }

    public List<AreaEntity> getSuperAndSubArea() {
        return superAndSubArea;
    }

    public void setSuperAndSubArea(List<AreaEntity> superAndSubArea) {
        this.superAndSubArea = superAndSubArea;
    }

    public OrganizationEntity getCurrentOrg() {
        return currentOrg;
    }

    public void setCurrentOrg(OrganizationEntity currentOrg) {
        this.currentOrg = currentOrg;
    }

    public List<OrganizationEntity> getCurrentAndSubOrg() {
        return currentAndSubOrg;
    }

    public void setCurrentAndSubOrg(List<OrganizationEntity> currentAndSubOrg) {
        this.currentAndSubOrg = currentAndSubOrg;
    }

    public List<OrganizationEntity> getSuperAndSubOrg() {
        return superAndSubOrg;
    }

    public void setSuperAndSubOrg(List<OrganizationEntity> superAndSubOrg) {
        this.superAndSubOrg = superAndSubOrg;
    }

    public List<OrganizationEntity> getCurrentOrgs() {
        List<OrganizationEntity> list = new ArrayList<OrganizationEntity>();
        list.add(currentOrg);
        return list;
    }

    public WareHouseEntity getCurrentWarehoouse() {
        return currentWarehoouse;
    }

    public void setCurrentWarehoouse(WareHouseEntity currentWarehoouse) {
        this.currentWarehoouse = currentWarehoouse;
    }

    public List<WareHouseEntity> getCurrentAndSubWarehoouse() {
        return currentAndSubWarehoouse;
    }

    public void setCurrentAndSubWarehoouse(List<WareHouseEntity> currentAndSubWarehoouse) {
        this.currentAndSubWarehoouse = currentAndSubWarehoouse;
    }

    public List<WareHouseEntity> getSuperAndSubWarehoouse() {
        return superAndSubWarehoouse;
    }

    public void setSuperAndSubWarehoouse(List<WareHouseEntity> superAndSubWarehoouse) {
        this.superAndSubWarehoouse = superAndSubWarehoouse;
    }

    //本办案场所和所有下级办案场所id字符串
    public String getCurrentAndSubAreaInStr() {
        if(currentAndSubArea != null && currentAndSubArea.size()>0){
            StringBuffer inStr = new StringBuffer();
            inStr.append(" in (0");
            for (AreaEntity areaEntity : currentAndSubArea) {
                inStr.append(",");
                inStr.append(areaEntity.getId());
            }
            inStr.append(")");
            return inStr.toString();
        }
        return "in(0)";
    }

    // 上下级办案场所id字符串
    public String getSuperAndSubAreaInStr() {
        if (superAndSubArea != null && superAndSubArea.size() > 0) {
            StringBuffer inStr = new StringBuffer("in ( 0");
            for (AreaEntity areaEntity : superAndSubArea) {
                inStr.append(",");
                inStr.append(areaEntity.getId());
            }
            inStr.append(")");
            return inStr.toString();
        }
        return "in(0)";
    }

    //本办案场所和所有下级办案场所id字符串
    public String getCurrentAndSubWarehouseInStr() {
        if(currentAndSubWarehoouse != null && currentAndSubWarehoouse.size()>0){
            StringBuffer inStr = new StringBuffer();
            inStr.append(" in (0");
            for (WareHouseEntity wareHouseEntity : currentAndSubWarehoouse) {
                inStr.append(",");
                inStr.append(wareHouseEntity.getId());
            }
            inStr.append(")");
            return inStr.toString();
        }
        return "in(0)";
    }

    // 上下级办案场所id字符串
    public String getSuperAndSubWarehouseInStr() {
        if (superAndSubWarehoouse != null && superAndSubWarehoouse.size() > 0) {
            StringBuffer inStr = new StringBuffer("in ( 0");
            for (WareHouseEntity wareHouseEntity : superAndSubWarehoouse) {
                inStr.append(",");
                inStr.append(wareHouseEntity.getId());
            }
            inStr.append(")");
            return inStr.toString();
        }
        return "in(0)";
    }
    public String getCurrentAndSubOrgInStr() {
        if (currentAndSubOrg != null && currentAndSubOrg.size() > 0) {
            StringBuffer inStr = new StringBuffer("in ( 0");
            for (OrganizationEntity or : currentAndSubOrg) {
                inStr.append(",");
                inStr.append(or.getId());
            }
            inStr.append(")");
            return inStr.toString();
        }
        return "in(0)";
    }




    public String getSuperAndSubOrgInStr() {
        if (superAndSubOrg != null && superAndSubOrg.size() > 0) {
            StringBuffer inStr = new StringBuffer("in ( 0");
            for (OrganizationEntity or : superAndSubOrg) {
                inStr.append(",");
                inStr.append(or.getId());
            }
            inStr.append(")");
            return inStr.toString();
        }
        return "in(0)";
    }

    @Override
    public String toString() {
        return "SessionInfo{" +
                "user=" + user +
                ", role=" + role +
                ", clientIP='" + clientIP + '\'' +
                ", currentArea=" + currentArea +
                ", currentAndSubArea=" + currentAndSubArea +
                ", superAndSubArea=" + superAndSubArea +
                ", currentOrg=" + currentOrg +
                ", currentAndSubOrg=" + currentAndSubOrg +
                ", superAndSubOrg=" + superAndSubOrg +
                ", currentOrgPid='" + currentOrgPid + '\'' +
                '}';
    }
}
