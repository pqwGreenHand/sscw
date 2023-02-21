package com.zhixin.zhfz.glpt.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 告警
 */
public class AlarmEntity implements Serializable {

    private static final long serialVersionUID = 3338408066215497318L;

    private Integer id;

    private Integer cuffId;

    private Integer deviceId;

    private Integer roomId;

    private Integer alarmType;//1 单人迅询问 2 无人看管  3  手环暴力拆卸  4手环低电量  5手环异常出区  6 SOS告警  7 羁押超时告警  8审讯超时告警  9入区未审讯告警  10 侯问容积告警  11  临时出区超时告警  12同案看押告警

    private String alarmName;

    private Date alarmTime;

    private String cuffNo;

    private String roomName;

    private String deviceNo;

    private String name;

    private String cameraNo;

    private Long serialId;

    private Integer policeId;

    private Integer status;

    private Date handlerTime;

    private String handlerContent;

    private String areaId;

    private String areaName;

    private String policeName;

    private String realName;

    private String icNo;

    private String certificateNo;

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getPoliceName() {
        return policeName;
    }

    public void setPoliceName(String policeName) {
        this.policeName = policeName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getIcNo() {
        return icNo;
    }

    public void setIcNo(String icNo) {
        this.icNo = icNo;
    }

    public String getCertificateNo() {
        return certificateNo;
    }

    public void setCertificateNo(String certificateNo) {
        this.certificateNo = certificateNo;
    }

    public Long getSerialId() {
        return serialId;
    }

    public void setSerialId(Long serialId) {
        this.serialId = serialId;
    }

    public Integer getPoliceId() {
        return policeId;
    }

    public void setPoliceId(Integer policeId) {
        this.policeId = policeId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getHandlerTime() {
        return handlerTime;
    }

    public void setHandlerTime(Date handlerTime) {
        this.handlerTime = handlerTime;
    }

    public String getHandlerContent() {
        return handlerContent;
    }

    public void setHandlerContent(String handlerContent) {
        this.handlerContent = handlerContent;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCuffId() {
        return cuffId;
    }

    public void setCuffId(Integer cuffId) {
        this.cuffId = cuffId;
    }

    public Integer getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
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

    public String getCuffNo() {
        return cuffNo;
    }

    public void setCuffNo(String cuffNo) {
        this.cuffNo = cuffNo;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCameraNo() {
        return cameraNo;
    }

    public void setCameraNo(String cameraNo) {
        this.cameraNo = cameraNo;
    }

    @Override
    public String toString() {
        return "AlarmEntity{" +
                "id=" + id +
                ", cuffId=" + cuffId +
                ", deviceId=" + deviceId +
                ", roomId=" + roomId +
                ", alarmType=" + alarmType +
                ", alarmName='" + alarmName + '\'' +
                ", alarmTime=" + alarmTime +
                ", cuffNo='" + cuffNo + '\'' +
                ", roomName='" + roomName + '\'' +
                ", deviceNo='" + deviceNo + '\'' +
                ", name='" + name + '\'' +
                ", cameraNo='" + cameraNo + '\'' +
                ", serialId=" + serialId +
                ", policeId=" + policeId +
                ", status=" + status +
                ", handlerTime=" + handlerTime +
                ", handlerContent='" + handlerContent + '\'' +
                ", areaId='" + areaId + '\'' +
                ", areaName='" + areaName + '\'' +
                ", policeName='" + policeName + '\'' +
                ", realName='" + realName + '\'' +
                ", icNo='" + icNo + '\'' +
                ", certificateNo='" + certificateNo + '\'' +
                '}';
    }
}
