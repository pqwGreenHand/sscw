package com.zhixin.zhfz.common.form;

import java.io.Serializable;
import java.util.Date;

/**
 * 角色  表单实体类
 */
public class RoleForm implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;
    private String trefresh;
    private String name;                  //角色名称
    private int bacsDataAuth;                 //0本人 1本部门 2本部门及下级部门 3上级部门及其下级部门 4全部
    private int sacwDataAuth;              //涉案财物权限
    private int jzglDataAuth;               //卷宗管理权限
    private int jxkpDataAuth;               //绩效考评权限
    private String description;           //描述
    private Date createdTime;             //创建时间
    private Date updatedTime;             //修改时间
    private Boolean isSelect = false;
    private int slaDataAuth;
    private int zhglDataAuth;
    private int bacsConfigure;
    private int sacwConfigure;
    private int jzglConfigure;
    private int jxkpConfigure;
    private int slaConfigure;
    private int xtConfigure;

    public int getSlaDataAuth() {
        return slaDataAuth;
    }

    public void setSlaDataAuth(int slaDataAuth) {
        this.slaDataAuth = slaDataAuth;
    }

    public int getZhglDataAuth() {
        return zhglDataAuth;
    }

    public void setZhglDataAuth(int zhglDataAuth) {
        this.zhglDataAuth = zhglDataAuth;
    }

    public int getBacsConfigure() {
        return bacsConfigure;
    }

    public void setBacsConfigure(int bacsConfigure) {
        this.bacsConfigure = bacsConfigure;
    }

    public int getSacwConfigure() {
        return sacwConfigure;
    }

    public void setSacwConfigure(int sacwConfigure) {
        this.sacwConfigure = sacwConfigure;
    }

    public int getJzglConfigure() {
        return jzglConfigure;
    }

    public void setJzglConfigure(int jzglConfigure) {
        this.jzglConfigure = jzglConfigure;
    }

    public int getJxkpConfigure() {
        return jxkpConfigure;
    }

    public void setJxkpConfigure(int jxkpConfigure) {
        this.jxkpConfigure = jxkpConfigure;
    }

    public int getSlaConfigure() {
        return slaConfigure;
    }

    public void setSlaConfigure(int slaConfigure) {
        this.slaConfigure = slaConfigure;
    }

    public int getXtConfigure() {
        return xtConfigure;
    }

    public void setXtConfigure(int xtConfigure) {
        this.xtConfigure = xtConfigure;
    }

    public Boolean getIsSelect() {
        return isSelect;
    }

    public void setIsSelect(Boolean isSelect) {
        this.isSelect = isSelect;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public int getBacsDataAuth() {
        return bacsDataAuth;
    }

    public void setBacsDataAuth(int bacsDataAuth) {
        this.bacsDataAuth = bacsDataAuth;
    }

    public int getSacwDataAuth() {
        return sacwDataAuth;
    }

    public void setSacwDataAuth(int sacwDataAuth) {
        this.sacwDataAuth = sacwDataAuth;
    }

    public int getJzglDataAuth() {
        return jzglDataAuth;
    }

    public void setJzglDataAuth(int jzglDataAuth) {
        this.jzglDataAuth = jzglDataAuth;
    }

    public int getJxkpDataAuth() {
        return jxkpDataAuth;
    }

    public void setJxkpDataAuth(int jxkpDataAuth) {
        this.jxkpDataAuth = jxkpDataAuth;
    }

    @Override
    public String toString() {
        return "RoleForm{" +
                "id=" + id +
                ", trefresh='" + trefresh + '\'' +
                ", name='" + name + '\'' +
                ", bacsDataAuth=" + bacsDataAuth +
                ", sacwDataAuth=" + sacwDataAuth +
                ", jzglDataAuth=" + jzglDataAuth +
                ", jxkpDataAuth=" + jxkpDataAuth +
                ", description='" + description + '\'' +
                ", createdTime=" + createdTime +
                ", updatedTime=" + updatedTime +
                ", isSelect=" + isSelect +
                ", slaDataAuth=" + slaDataAuth +
                ", zhglDataAuth=" + zhglDataAuth +
                ", bacsConfigure=" + bacsConfigure +
                ", sacwConfigure=" + sacwConfigure +
                ", jzglConfigure=" + jzglConfigure +
                ", jxkpConfigure=" + jxkpConfigure +
                ", slaConfigure=" + slaConfigure +
                ", xtConfigure=" + xtConfigure +
                '}';
    }
}