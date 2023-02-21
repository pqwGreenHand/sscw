package com.zhixin.zhfz.bacs.entity;

import java.io.Serializable;
import java.util.Date;

public class RecognizeRecordEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private Integer caseId;

    private String areaId;

    private String areaName;

    private String roomName;

    private Integer serialId;

    private Integer personId;

    private String address;

    private Date startTime;

    private Date endTime;

    private String policeman;

    private String victim;

    private String recognize;

    private String witniss;

    private String target;

    private String result;

    private Integer status;

    private Integer recognizeType;

    private Integer userId;

    private Date updatedTime;

    private Date createdTime;

    private String opPid;
    private Integer opUserId;

    @Override
    public String toString() {
        return "RecognizeRecordEntity{" +
                "id=" + id +
                ", caseId=" + caseId +
                ", areaId=" + areaId +
                ", areaName='" + areaName + '\'' +
                ", roomName='" + roomName + '\'' +
                ", serialId=" + serialId +
                ", personId=" + personId +
                ", address='" + address + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", policeman='" + policeman + '\'' +
                ", victim='" + victim + '\'' +
                ", recognize='" + recognize + '\'' +
                ", witniss='" + witniss + '\'' +
                ", target='" + target + '\'' +
                ", result='" + result + '\'' +
                ", status=" + status +
                ", recognizeType='" + recognizeType + '\'' +
                ", userId=" + userId +
                ", updatedTime=" + updatedTime +
                ", createdTime=" + createdTime +
                ", opPid='" + opPid + '\'' +
                ", opUserId=" + opUserId +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCaseId() {
        return caseId;
    }

    public void setCaseId(Integer caseId) {
        this.caseId = caseId;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public Integer getSerialId() {
        return serialId;
    }

    public void setSerialId(Integer serialId) {
        this.serialId = serialId;
    }

    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getPoliceman() {
        return policeman;
    }

    public void setPoliceman(String policeman) {
        this.policeman = policeman;
    }

    public String getVictim() {
        return victim;
    }

    public void setVictim(String victim) {
        this.victim = victim;
    }

    public String getRecognize() {
        return recognize;
    }

    public void setRecognize(String recognize) {
        this.recognize = recognize;
    }

    public String getWitniss() {
        return witniss;
    }

    public void setWitniss(String witniss) {
        this.witniss = witniss;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getRecognizeType() {
        return recognizeType;
    }

    public void setRecognizeType(Integer recognizeType) {
        this.recognizeType = recognizeType;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getOpPid() {
        return opPid;
    }

    public void setOpPid(String opPid) {
        this.opPid = opPid;
    }

    public Integer getOpUserId() {
        return opUserId;
    }

    public void setOpUserId(Integer opUserId) {
        this.opUserId = opUserId;
    }
}
