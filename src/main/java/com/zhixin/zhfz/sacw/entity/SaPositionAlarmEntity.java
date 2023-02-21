package com.zhixin.zhfz.sacw.entity;

import java.util.Date;

public class SaPositionAlarmEntity implements java.io.Serializable {

    private Integer id;
    private Integer labelId;
    private Integer labelNo;
    private Integer deviceId;
    private String deviceNo;
    private Integer alarmType;
    private String alarmName;
    private Date alarmTime;
    private Integer locationId;
    private Integer warehouseId;
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

    public Integer getAlarmType() {
        return alarmType;
    }

    public void setAlarmType(Integer alarmType) {
        this.alarmType = alarmType;
    }

    public String getAlarmName() {
        return alarmName;
    }

    public void setAlarmName(String alarmName) {
        this.alarmName = alarmName;
    }

    public Date getAlarmTime() {
        return alarmTime;
    }

    public void setAlarmTime(Date alarmTime) {
        this.alarmTime = alarmTime;
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

    public Integer getLowPower() {
        return lowPower;
    }

    public void setLowPower(Integer lowPower) {
        this.lowPower = lowPower;
    }

    @Override
    public String toString() {
        return "PositionAlarmEntity{" +
                "id=" + id +
                ", labelId=" + labelId +
                ", labelNo=" + labelNo +
                ", deviceId=" + deviceId +
                ", deviceNo='" + deviceNo + '\'' +
                ", alarmType=" + alarmType +
                ", alarmName='" + alarmName + '\'' +
                ", alarmTime=" + alarmTime +
                ", locationId=" + locationId +
                ", warehouseId=" + warehouseId +
                ", lowPower=" + lowPower +
                '}';
    }
}