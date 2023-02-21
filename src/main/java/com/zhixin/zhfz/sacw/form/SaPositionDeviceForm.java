package com.zhixin.zhfz.sacw.form;

import java.io.Serializable;

/*
 * 定位设备表单
 * */
public class SaPositionDeviceForm implements Serializable {

    private static final long serialVersionUID = 5577128090368009959L;

    private Integer id;
    private Integer deviceNo;
    private String ip;
    private String description;
    private Integer areaId;
    private Integer roomId;
    private Integer deviceType;
    private String mode;
    private Integer groupNo;
    private Integer groupName;


    //涉案财物   定位标签字段

    private Integer warehouseId;     // 仓库id
    private Integer locationId;      //区域id
    private Integer type;            //标签类型
    private String warehouseName;    //仓库名称
    private String locationName;     //区域名称


    public static long getSerialVersionUID() {
        return serialVersionUID;
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

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public Integer getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(Integer deviceType) {
        this.deviceType = deviceType;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public Integer getGroupNo() {
        return groupNo;
    }

    public void setGroupNo(Integer groupNo) {
        this.groupNo = groupNo;
    }

    public Integer getGroupName() {
        return groupName;
    }

    public void setGroupName(Integer groupName) {
        this.groupName = groupName;
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
        return "SaPositionDeviceForm{" +
                "id=" + id +
                ", deviceNo=" + deviceNo +
                ", ip='" + ip + '\'' +
                ", description='" + description + '\'' +
                ", areaId=" + areaId +
                ", roomId=" + roomId +
                ", deviceType=" + deviceType +
                ", mode='" + mode + '\'' +
                ", groupNo=" + groupNo +
                ", groupName=" + groupName +
                ", warehouseId=" + warehouseId +
                ", locationId=" + locationId +
                ", type=" + type +
                ", warehouseName='" + warehouseName + '\'' +
                ", locationName='" + locationName + '\'' +
                '}';
    }
}
