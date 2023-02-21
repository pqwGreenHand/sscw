package com.zhixin.zhfz.sacw.form;

import java.util.Date;

public class WareHouseForm implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    private int id;
    private int orgId;
    private String orgName;
    private String name;
    private String description;
    private String address;
    private Date createdTime;
    private Date updatedTime;

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

    @Override
    public String toString() {
        return "WareHouseForm [id=" + id + ", orgId=" + orgId + ", orgName=" + orgName + ", name=" + name
                + ", description=" + description + ", address=" + address + ", createdTime=" + createdTime
                + ", updatedTime=" + updatedTime + "]";
    }


}
