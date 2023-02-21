package com.zhixin.zhfz.sacw.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 远程示证
 */
public class RemoteLookEntity implements Serializable {


    private static final long serialVersionUID = 1L;
    private Integer id;
    private Integer caseId;
    private String caseNo;
    private String caseName;
    private Integer involvedId;
    private Integer warehouseId;
    private String involvedName;
    private Integer requestPoliceId;
    private String requestPoliceNo;
    private String requestPoliceName;
    private Integer status;
    private String organizationName;
    private String randomPasswd;
    private Date showDate;
    private String failDesc;
    private String description;
    private Integer registerUserId;
    private Integer registerUserNo;
    private String registerUserName;
    private Date createdTime;
    private Date updatedTime;
    private String mobile;
    private Integer areaId;
    private String warehouseName;
    private String op_pid;
    private Integer op_user_id;

    public String getOp_pid() {
        return op_pid;
    }

    public void setOp_pid(String op_pid) {
        this.op_pid = op_pid;
    }

    public Integer getOp_user_id() {
        return op_user_id;
    }

    public void setOp_user_id(Integer op_user_id) {
        this.op_user_id = op_user_id;
    }


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

    public Integer getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Integer warehouseId) {
        this.warehouseId = warehouseId;
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

    public String getRequestPoliceNo() {
        return requestPoliceNo;
    }

    public void setRequestPoliceNo(String requestPoliceNo) {
        this.requestPoliceNo = requestPoliceNo;
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

    public Date getShowDate() {
        return showDate;
    }

    public void setShowDate(Date showDate) {
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

    public Integer getRegisterUserNo() {
        return registerUserNo;
    }

    public void setRegisterUserNo(Integer registerUserNo) {
        this.registerUserNo = registerUserNo;
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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    @Override
    public String toString() {
        return "RemoteLookEntity{" +
                "id=" + id +
                ", caseId=" + caseId +
                ", caseNo='" + caseNo + '\'' +
                ", caseName='" + caseName + '\'' +
                ", involvedId=" + involvedId +
                ", warehouseId=" + warehouseId +
                ", involvedName='" + involvedName + '\'' +
                ", requestPoliceId=" + requestPoliceId +
                ", requestPoliceNo='" + requestPoliceNo + '\'' +
                ", requestPoliceName='" + requestPoliceName + '\'' +
                ", status=" + status +
                ", organizationName='" + organizationName + '\'' +
                ", randomPasswd='" + randomPasswd + '\'' +
                ", showDate=" + showDate +
                ", failDesc='" + failDesc + '\'' +
                ", description='" + description + '\'' +
                ", registerUserId=" + registerUserId +
                ", registerUserNo=" + registerUserNo +
                ", registerUserName='" + registerUserName + '\'' +
                ", createdTime=" + createdTime +
                ", updatedTime=" + updatedTime +
                ", mobile='" + mobile + '\'' +
                ", areaId=" + areaId +
                ", warehouseName='" + warehouseName + '\'' +
                '}';
    }
}
