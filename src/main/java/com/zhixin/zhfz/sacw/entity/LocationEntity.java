package com.zhixin.zhfz.sacw.entity;

import java.util.Date;

public class LocationEntity implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    private int id;
    private int involvedTypeId;//物品类型ID
    private String involvedTypeName;
    private int wareHouseId;
    private String name;
    private String description;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWareHouseId() {
        return wareHouseId;
    }

    public void setWareHouseId(int wareHouseId) {
        this.wareHouseId = wareHouseId;
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

    public int getInvolvedTypeId() {
        return involvedTypeId;
    }

    public void setInvolvedTypeId(int involvedTypeId) {
        this.involvedTypeId = involvedTypeId;
    }

    public String getInvolvedTypeName() {
        return involvedTypeName;
    }

    public void setInvolvedTypeName(String involvedTypeName) {
        this.involvedTypeName = involvedTypeName;
    }

    @Override
    public String toString() {
        return "LocationEntity{" +
                "id=" + id +
                ", involvedTypeId=" + involvedTypeId +
                ", involvedTypeName='" + involvedTypeName + '\'' +
                ", wareHouseId=" + wareHouseId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", createdTime=" + createdTime +
                ", updatedTime=" + updatedTime +
                '}';
    }
}
