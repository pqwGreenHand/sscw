package com.zhixin.zhfz.sacw.entity;

import java.util.Date;

public class SaPositionDataEntity implements java.io.Serializable {

    private Integer id;
    private Integer labelId;
    private Integer labelNo;
    private Integer deviceId;
    private String deviceNo;
    private Integer locationId;
    private Integer warehouseId;
    private String roomGroup;
    private Date sendTime;
    private Integer lowPower;

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getLabelId() {
        return labelId;
    }

    public void setLabelId(Integer labelId) {
        this.labelId = labelId;
    }

    public Integer getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public Integer getLabelNo() {
        return labelNo;
    }

    public void setLabelNo(Integer labelNo) {
        this.labelNo = labelNo;
    }

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    public Integer getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Integer warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getRoomGroup() {
        return roomGroup;
    }

    public void setRoomGroup(String roomGroup) {
        this.roomGroup = roomGroup;
    }

    public Integer getLowPower() {
        return lowPower;
    }

    public void setLowPower(Integer lowPower) {
        this.lowPower = lowPower;
    }

    @Override
    public String toString() {
        return "PositionDataEntity{" +
                "id=" + id +
                ", labelId=" + labelId +
                ", labelNo=" + labelNo +
                ", deviceId=" + deviceId +
                ", deviceNo='" + deviceNo + '\'' +
                ", locationId=" + locationId +
                ", warehouseId=" + warehouseId +
                ", roomGroup='" + roomGroup + '\'' +
                ", sendTime=" + sendTime +
                ", lowPower=" + lowPower +
                '}';
    }
}