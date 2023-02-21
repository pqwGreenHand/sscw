package com.zhixin.zhfz.sacw.entity;

import java.io.Serializable;
import java.util.Date;

public class BoundAlarmEntity implements Serializable {


    private static final long serialVersionUID = 1L;
    private Integer id;
    private Integer labelId; //标签编号
    private Integer deviceId; //激活器设备Id
    private String alarmName; //告警名称
    private Date alarmTime; //告警时间
    private String caseNo;   //案件编号ajbh
    private String caseName;  //案件名称 ajmc
    private String name;//物品名称
    private Integer alarmType;
    private double weight;
    private double worth;
    private String description;
    private Integer involvedTypeId;//物品类型ID
    private String involvedTypeName;//物品类型名称
    private String involvedOwner;//物品持有人
    private Integer status;
    private Date expiredTime;//保存日期
    private Integer type;//物品分类：1是涉案物品，2是无主物品
    private String outputCount;//出库数量
    private Integer registerUserId;   //登记人id
    private String registerUserName;  //登记人名称
    private Date createdTime;
    private Date updatedTime;
    private Date startTime;//开始日期
    private Date endTime;//结束日期
    private String cameraNo;//监控点编号

    private Integer warehouseId;//仓库id
    private String warehouseName;//仓库名称
    private Integer locationId;//区域id
    private String locationName;//名称


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public String getCaseNo() {
        return caseNo;
    }

    public void setCaseNo(String caseNo) {
        this.caseNo = caseNo;
    }

    public String getCaseName() {
        return caseName;
    }

    public void setCaseName(String caseName) {
        this.caseName = caseName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAlarmType() {
        return alarmType;
    }

    public void setAlarmType(Integer alarmType) {
        this.alarmType = alarmType;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getWorth() {
        return worth;
    }

    public void setWorth(double worth) {
        this.worth = worth;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getInvolvedTypeId() {
        return involvedTypeId;
    }

    public void setInvolvedTypeId(Integer involvedTypeId) {
        this.involvedTypeId = involvedTypeId;
    }

    public String getInvolvedTypeName() {
        return involvedTypeName;
    }

    public void setInvolvedTypeName(String involvedTypeName) {
        this.involvedTypeName = involvedTypeName;
    }

    public String getInvolvedOwner() {
        return involvedOwner;
    }

    public void setInvolvedOwner(String involvedOwner) {
        this.involvedOwner = involvedOwner;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(Date expiredTime) {
        this.expiredTime = expiredTime;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getOutputCount() {
        return outputCount;
    }

    public void setOutputCount(String outputCount) {
        this.outputCount = outputCount;
    }

    public Integer getRegisterUserId() {
        return registerUserId;
    }

    public void setRegisterUserId(Integer registerUserId) {
        this.registerUserId = registerUserId;
    }

    public String getRegisterUserName() {
        return registerUserName;
    }

    public void setRegisterUserName(String registerUserName) {
        this.registerUserName = registerUserName;
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

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getCameraNo() {
        return cameraNo;
    }

    public void setCameraNo(String cameraNo) {
        this.cameraNo = cameraNo;
    }



    public Integer getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Integer warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }


    @Override
    public String toString() {
        return "BoundAlarmEntity{" +
                "id=" + id +
                ", labelId=" + labelId +
                ", deviceId=" + deviceId +
                ", alarmName='" + alarmName + '\'' +
                ", alarmTime=" + alarmTime +
                ", caseNo='" + caseNo + '\'' +
                ", caseName='" + caseName + '\'' +
                ", name='" + name + '\'' +
                ", alarmType=" + alarmType +
                ", weight=" + weight +
                ", worth=" + worth +
                ", description='" + description + '\'' +
                ", involvedTypeId=" + involvedTypeId +
                ", involvedTypeName='" + involvedTypeName + '\'' +
                ", involvedOwner='" + involvedOwner + '\'' +
                ", status=" + status +
                ", expiredTime=" + expiredTime +
                ", type=" + type +
                ", outputCount='" + outputCount + '\'' +
                ", registerUserId=" + registerUserId +
                ", registerUserName='" + registerUserName + '\'' +
                ", createdTime=" + createdTime +
                ", updatedTime=" + updatedTime +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", cameraNo='" + cameraNo + '\'' +

                ", warehouseId=" + warehouseId +
                ", warehouseName='" + warehouseName + '\'' +
                ", locationId=" + locationId +
                ", locationName='" + locationName + '\'' +
                '}';
    }
}
