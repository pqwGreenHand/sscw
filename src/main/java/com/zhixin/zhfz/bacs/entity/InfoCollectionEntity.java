package com.zhixin.zhfz.bacs.entity;

import java.io.Serializable;
import java.util.Date;

public class InfoCollectionEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long serialId;

    private Long personId;

    private Integer registerUserId;

    private Date registerTime;

    private Integer lawCaseId;

    private Integer AreaId;

    private Date createdTime;

    private Date updatedTime;

    private String personName;//被采集人姓名

    private String certificateNo;//被采集人证件号码

    private String realName;//记录人

    private Date collectTime;//采集时间

    private String involvedReason;//案由

    private String caseName;//案件信息

    private String caseType;// 案件类型

    private String caseNature;// 案件性质

    private String areaName;//办案场所名称

    private String serialNo;//专属编号

    private Integer infoid;

    private String opPid;
    private Integer opUserId;

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

    public String getCaseName() {
        return caseName;
    }

    public void setCaseName(String caseName) {
        this.caseName = caseName;
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

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public Integer getRegisterUserId() {
        return registerUserId;
    }

    public void setRegisterUserId(Integer registerUserId) {
        this.registerUserId = registerUserId;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public Integer getLawCaseId() {
        return lawCaseId;
    }

    public void setLawCaseId(Integer lawCaseId) {
        this.lawCaseId = lawCaseId;
    }

    public Integer getAreaId() {
        return AreaId;
    }

    public void setAreaId(Integer areaId) {
        AreaId = areaId;
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

    public String getCertificateNo() {
        return certificateNo;
    }

    public void setCertificateNo(String certificateNo) {
        this.certificateNo = certificateNo;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Date getCollectTime() {
        return collectTime;
    }

    public void setCollectTime(Date collectTime) {
        this.collectTime = collectTime;
    }

    public String getInvolvedReason() {
        return involvedReason;
    }

    public void setInvolvedReason(String involvedReason) {
        this.involvedReason = involvedReason;
    }

    public String getCaseType() {
        return caseType;
    }

    public void setCaseType(String caseType) {
        this.caseType = caseType;
    }

    public String getCaseNature() {
        return caseNature;
    }

    public void setCaseNature(String caseNature) {
        this.caseNature = caseNature;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public Integer getInfoid() {
        return infoid;
    }

    public void setInfoid(Integer infoid) {
        this.infoid = infoid;
    }


    @Override
    public String toString() {
        return "InfoCollectionEntity{" +
                "id=" + id +
                ", serialId=" + serialId +
                ", personId=" + personId +
                ", registerUserId=" + registerUserId +
                ", registerTime=" + registerTime +
                ", lawCaseId=" + lawCaseId +
                ", AreaId=" + AreaId +
                ", createdTime=" + createdTime +
                ", updatedTime=" + updatedTime +
                ", personName='" + personName + '\'' +
                ", certificateNo='" + certificateNo + '\'' +
                ", realName='" + realName + '\'' +
                ", collectTime=" + collectTime +
                ", involvedReason='" + involvedReason + '\'' +
                ", caseName='" + caseName + '\'' +
                ", caseType='" + caseType + '\'' +
                ", caseNature='" + caseNature + '\'' +
                ", areaName='" + areaName + '\'' +
                ", serialNo='" + serialNo + '\'' +
                ", infoid=" + infoid +
                '}';
    }
}
