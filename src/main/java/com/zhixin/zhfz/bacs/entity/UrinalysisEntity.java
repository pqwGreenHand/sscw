package com.zhixin.zhfz.bacs.entity;

import java.io.Serializable;
import java.util.Date;

public class UrinalysisEntity implements Serializable {


    private static final long serialVersionUID = 1L;

    private Long id;

    private Long serialId;

    private Integer caseId;

    private Integer mainUserId;

    private Integer checkUserId;

    private Date urinalysisTime;

    private Integer areaId;

    private String testMethod;

    private String result;

    private Integer count;

    private String description;

    private Date createdTime;

    private Date updatedTime;

    //链表

    private String personName;

    private int sex;

    private String age;

    private String crimeType;

    private int caseType;

    private String mainUserName;

    private String checkUserName;

    private String cuffNumber;

    private String checkUserNo;

    private String sendOrgName;

    private String birth;

    private String certificateNo;

    private String certificateType;

    private String serialNo;

    private String opPid;

    private Integer opUserId;

    private String serialUUID;

    public String getSerialUUID() {
        return serialUUID;
    }

    public void setSerialUUID(String serialUUID) {
        this.serialUUID = serialUUID;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSerialId() {
        return serialId;
    }

    public void setSerialId(Long serialId) {
        this.serialId = serialId;
    }

    public Integer getCaseId() {
        return caseId;
    }

    public void setCaseId(Integer caseId) {
        this.caseId = caseId;
    }

    public Integer getMainUserId() {
        return mainUserId;
    }

    public void setMainUserId(Integer mainUserId) {
        this.mainUserId = mainUserId;
    }

    public Integer getCheckUserId() {
        return checkUserId;
    }

    public void setCheckUserId(Integer checkUserId) {
        this.checkUserId = checkUserId;
    }

    public Date getUrinalysisTime() {
        return urinalysisTime;
    }

    public void setUrinalysisTime(Date urinalysisTime) {
        this.urinalysisTime = urinalysisTime;
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public String getTestMethod() {
        return testMethod;
    }

    public void setTestMethod(String testMethod) {
        this.testMethod = testMethod;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
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

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getCrimeType() {
        return crimeType;
    }

    public void setCrimeType(String crimeType) {
        this.crimeType = crimeType;
    }

    public int getCaseType() {
        return caseType;
    }

    public void setCaseType(int caseType) {
        this.caseType = caseType;
    }

    public String getMainUserName() {
        return mainUserName;
    }

    public void setMainUserName(String mainUserName) {
        this.mainUserName = mainUserName;
    }

    public String getCheckUserName() {
        return checkUserName;
    }

    public void setCheckUserName(String checkUserName) {
        this.checkUserName = checkUserName;
    }

    public String getCuffNumber() {
        return cuffNumber;
    }

    public void setCuffNumber(String cuffNumber) {
        this.cuffNumber = cuffNumber;
    }

    public String getCheckUserNo() {
        return checkUserNo;
    }

    public void setCheckUserNo(String checkUserNo) {
        this.checkUserNo = checkUserNo;
    }

    public String getSendOrgName() {
        return sendOrgName;
    }

    public void setSendOrgName(String sendOrgName) {
        this.sendOrgName = sendOrgName;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getCertificateNo() {
        return certificateNo;
    }

    public void setCertificateNo(String certificateNo) {
        this.certificateNo = certificateNo;
    }

    public String getCertificateType() {
        return certificateType;
    }

    public void setCertificateType(String certificateType) {
        this.certificateType = certificateType;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }


    @Override
    public String toString() {
        return "UrinalysisEntity{" +
                "id=" + id +
                ", serialId=" + serialId +
                ", caseId=" + caseId +
                ", mainUserId=" + mainUserId +
                ", checkUserId=" + checkUserId +
                ", urinalysisTime=" + urinalysisTime +
                ", areaId=" + areaId +
                ", testMethod='" + testMethod + '\'' +
                ", result='" + result + '\'' +
                ", count=" + count +
                ", description='" + description + '\'' +
                ", createdTime=" + createdTime +
                ", updatedTime=" + updatedTime +
                ", personName='" + personName + '\'' +
                ", sex=" + sex +
                ", age='" + age + '\'' +
                ", crimeType='" + crimeType + '\'' +
                ", caseType=" + caseType +
                ", mainUserName='" + mainUserName + '\'' +
                ", checkUserName='" + checkUserName + '\'' +
                ", cuffNumber='" + cuffNumber + '\'' +
                ", checkUserNo='" + checkUserNo + '\'' +
                ", sendOrgName='" + sendOrgName + '\'' +
                ", birth='" + birth + '\'' +
                ", certificateNo='" + certificateNo + '\'' +
                ", certificateType='" + certificateType + '\'' +
                ", serialNo='" + serialNo + '\'' +
                ", opPid=" + opPid +
                ", opUserId=" + opUserId +
                '}';
    }
}