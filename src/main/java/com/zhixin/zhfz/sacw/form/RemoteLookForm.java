package com.zhixin.zhfz.sacw.form;

import java.io.Serializable;
import java.util.Date;

public class RemoteLookForm implements Serializable {


    private static final long serialVersionUID = 1L;
    private Integer id;
    private Integer caseId;
    private String caseNo;
    private String caseName;
    private Integer involvedId;
    private String involvedName;
    private Integer requestPoliceId;
    private String requestPoliceName;
    private Integer status;
    private Integer warehouseId;
    private String warehouseName;
    private String organizationName;
    private String randomPasswd;
    private String showDate;
    private String failDesc;
    private String description;
    private Integer registerUserId;
    private String registerUserName;
    private Date createdTime;
    private Date updatedTime;
    private Integer areaId;
    private String areaName;


    public static long getSerialVersionUID() {
        return serialVersionUID;
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

    public Integer getInvolvedId() {
        return involvedId;
    }

    public void setInvolvedId(Integer involvedId) {
        this.involvedId = involvedId;
    }

    public String getInvolvedName() {
        return involvedName;
    }

    public void setInvolvedName(String involvedName) {
        this.involvedName = involvedName;
    }

    public Integer getRequestPoliceId() {
        return requestPoliceId;
    }

    public void setRequestPoliceId(Integer requestPoliceId) {
        this.requestPoliceId = requestPoliceId;
    }

    public String getRequestPoliceName() {
        return requestPoliceName;
    }

    public void setRequestPoliceName(String requestPoliceName) {
        this.requestPoliceName = requestPoliceName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getRandomPasswd() {
        return randomPasswd;
    }

    public void setRandomPasswd(String randomPasswd) {
        this.randomPasswd = randomPasswd;
    }

    public String getShowDate() {
        return showDate;
    }

    public void setShowDate(String showDate) {
        this.showDate = showDate;
    }

    public String getFailDesc() {
        return failDesc;
    }

    public void setFailDesc(String failDesc) {
        this.failDesc = failDesc;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    @Override
    public String toString() {
        return "RemoteLookForm{" +
                "id=" + id +
                ", caseId=" + caseId +
                ", caseNo='" + caseNo + '\'' +
                ", caseName='" + caseName + '\'' +
                ", involvedId=" + involvedId +
                ", involvedName='" + involvedName + '\'' +
                ", requestPoliceId=" + requestPoliceId +
                ", requestPoliceName='" + requestPoliceName + '\'' +
                ", status=" + status +
                ", warehouseId=" + warehouseId +
                ", warehouseName='" + warehouseName + '\'' +
                ", organizationName='" + organizationName + '\'' +
                ", randomPasswd='" + randomPasswd + '\'' +
                ", showDate='" + showDate + '\'' +
                ", failDesc='" + failDesc + '\'' +
                ", description='" + description + '\'' +
                ", registerUserId=" + registerUserId +
                ", registerUserName='" + registerUserName + '\'' +
                ", createdTime=" + createdTime +
                ", updatedTime=" + updatedTime +
                ", areaId=" + areaId +
                ", areaName='" + areaName + '\'' +
                '}';
    }
}
