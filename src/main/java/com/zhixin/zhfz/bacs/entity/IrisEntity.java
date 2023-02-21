package com.zhixin.zhfz.bacs.entity;

import java.io.Serializable;
import java.util.Date;

public class IrisEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;

    private String leftEye;

    private String rightEye;

    private String leftImg;

    private String rightImg;

    private String areaName;

    private int caseId;

    private String caseName;

    private Long  serialId;

    private Long personId;

    private String personName;

    private int userId;

    private String userName;

    private Date createdTime;

    private String UUID;

    private int areaId;



    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLeftEye() {
        return leftEye;
    }

    public void setLeftEye(String leftEye) {
        this.leftEye = leftEye;
    }

    public String getRightEye() {
        return rightEye;
    }

    public void setRightEye(String rightEye) {
        this.rightEye = rightEye;
    }

    public String getLeftImg() {
        return leftImg;
    }

    public void setLeftImg(String leftImg) {
        this.leftImg = leftImg;
    }

    public String getRightImg() {
        return rightImg;
    }

    public void setRightImg(String rightImg) {
        this.rightImg = rightImg;
    }

    public int getAreaId() {
        return areaId;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public int getCaseId() {
        return caseId;
    }

    public void setCaseId(int caseId) {
        this.caseId = caseId;
    }

    public String getCaseName() {
        return caseName;
    }

    public void setCaseName(String caseName) {
        this.caseName = caseName;
    }

    public Long getSerialId() {
        return serialId;
    }

    public void setSerialId(Long serialId) {
        this.serialId = serialId;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }


    @Override
    public String toString() {
        return "IrisEntity{" +
                "id=" + id +
                ", leftEye='" + leftEye + '\'' +
                ", rightEye='" + rightEye + '\'' +
                ", leftImg='" + leftImg + '\'' +
                ", rightImg='" + rightImg + '\'' +
                ", areaId=" + areaId +
                ", areaName='" + areaName + '\'' +
                ", caseId=" + caseId +
                ", caseName='" + caseName + '\'' +
                ", serialId=" + serialId +
                ", personId=" + personId +
                ", personName='" + personName + '\'' +
                ", userId=" + userId +
                ", userName='" + userName + '\'' +
                ", createdTime=" + createdTime +
                '}';
    }
}
