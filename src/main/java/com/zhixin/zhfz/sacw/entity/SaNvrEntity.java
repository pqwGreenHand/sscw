package com.zhixin.zhfz.sacw.entity;

import java.io.Serializable;
import java.util.Date;

public class SaNvrEntity implements Serializable {


    private static final long serialVersionUID = 1L;
    private Integer id;
    private String name;
    private String ip;
    private String ipBack;
    private String account;
    private String password;
    private Integer port;
    private String areaName;
    private Integer areaId;
    private String vender;
    private Integer type;
    private Date createdTime;
    private Date updatedTime;

    // 涉案财物nvr字段
    private String warehouseName;
    private Integer warehouseId;
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

    public static long getSerialVersionUID() {
        return serialVersionUID;
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
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getIpBack() {
        return ipBack;
    }

    public void setIpBack(String ipBack) {
        this.ipBack = ipBack;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public String getVender() {
        return vender;
    }

    public void setVender(String vender) {
        this.vender = vender;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public Integer getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Integer warehouseId) {
        this.warehouseId = warehouseId;
    }

    @Override
    public String toString() {
        return "SaNvrEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", ip='" + ip + '\'' +
                ", ipBack='" + ipBack + '\'' +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", port=" + port +
                ", areaName='" + areaName + '\'' +
                ", areaId=" + areaId +
                ", vender='" + vender + '\'' +
                ", type=" + type +
                ", createdTime=" + createdTime +
                ", updatedTime=" + updatedTime +
                ", warehouseName='" + warehouseName + '\'' +
                ", warehouseId=" + warehouseId +
                ", op_pid='" + op_pid + '\'' +
                ", op_user_id=" + op_user_id +
                '}';
    }
}
