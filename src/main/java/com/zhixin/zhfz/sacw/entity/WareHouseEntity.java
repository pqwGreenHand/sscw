package com.zhixin.zhfz.sacw.entity;

import java.util.Date;

public class WareHouseEntity implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    private int id;
    private int orgId;
    private String orgName;
    private String name;
    private String description;
    private String address;
    private String map;
    private String mapName;
    private Date createdTime;
    private Date updatedTime;
    private String op_pid;
    private Integer op_user_id;

    public String getOp_pid() {
        return op_pid;
    }

    public void setOp_pid(String op_pid) {
        this.op_pid = op_pid;
    }

    public Integer getOp_user_id() {
        return op_user_id;
    }

    public void setOp_user_id(Integer op_user_id) {
        this.op_user_id = op_user_id;
    }

    public String getMap() {
        return map;
    }

    public void setMap(String map) {
        this.map = map;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrgId() {
        return orgId;
    }

    public void setOrgId(int orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public String getMapName() {
        return mapName;
    }

    public void setMapName(String mapName) {
        this.mapName = mapName;
    }

    @Override
    public String toString() {
        return "WareHouseEntity{" +
                "id=" + id +
                ", orgId=" + orgId +
                ", orgName='" + orgName + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", address='" + address + '\'' +
                ", map='" + map + '\'' +
                ", mapName='" + mapName + '\'' +
                ", createdTime=" + createdTime +
                ", updatedTime=" + updatedTime +
                ", op_pid='" + op_pid + '\'' +
                ", op_user_id=" + op_user_id +
                '}';
    }
}
