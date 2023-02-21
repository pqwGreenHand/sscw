package com.zhixin.zhfz.sacw.entity;

import java.util.Date;

/***
 * 涉案财物  定位标签
 */
public class SaPositionDeviceEntity implements java.io.Serializable {

    private Integer id;
    private Integer deviceNo;  //标签编号
    private String ip;          //设备ip
    private String description;    //详情
    private Date createdTime;     //更新时间
    private String mode;           //定位模式
    private Integer groupName;     //组名称
    private Integer groupNo;        //组内序号
    private Integer deviceType;     //设备类型


//涉案财物   定位标签字段

    private Integer warehouseId;     // 仓库id
    private Integer locationId;      //区域id
    private Integer type;            //标签类型
    private String warehouseName;    //仓库名称
    private String locationName;     //区域名称

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(Integer deviceNo) {
        this.deviceNo = deviceNo;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
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

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public Integer getGroupName() {
        return groupName;
    }

    public void setGroupName(Integer groupName) {
        this.groupName = groupName;
    }

    public Integer getGroupNo() {
        return groupNo;
    }

    public void setGroupNo(Integer groupNo) {
        this.groupNo = groupNo;
    }

    public Integer getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(Integer deviceType) {
        this.deviceType = deviceType;
    }

    public Integer getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Integer warehouseId) {
        this.warehouseId = warehouseId;
    }

    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }


    @Override
    public String toString() {
        return "SaPositionDeviceEntity{" +
                "id=" + id +
                ", deviceNo=" + deviceNo +
                ", ip='" + ip + '\'' +
                ", description='" + description + '\'' +
                ", createdTime=" + createdTime +
                ", mode='" + mode + '\'' +
                ", groupName=" + groupName +
                ", groupNo=" + groupNo +
                ", deviceType=" + deviceType +
                ", warehouseId=" + warehouseId +
                ", locationId=" + locationId +
                ", type=" + type +
                ", warehouseName='" + warehouseName + '\'' +
                ", locationName='" + locationName + '\'' +
                '}';
    }
}