package com.zhixin.zhfz.sacw.entity;

import java.util.Date;

public class OutputRecordEntity implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private Integer involvedId;
    private Integer registerUserId;
    private Date outputTime;
    private Integer shelfId;
    private Integer locationId;
    private Integer warehouseId;
    private Integer lawCaseId;
    private Integer involvedTypeId;
    private Integer outputTypeId;
    private Integer interrogateAreaId;
    private Integer organizationId;
    private Integer policeId;
    private String description;
    private Date createdTime;
    private Date updatedTime;
    private Integer count;
    private String caseNo;
    private String caseName;
    private String involvedType;
    private String goodsName;
    private String getPoliceName;
    private String getUnit;
    private String logName;
    private String dept;
    private Integer allCount;
    private Integer leftCount;
    private String handlerPerson;
    private String outputType;
    private String telephone;
    private String uuid;
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

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Integer getLeftCount() {
        return leftCount;
    }

    public void setLeftCount(Integer leftCount) {
        this.leftCount = leftCount;
    }

    public String getHandlerPerson() {
        return handlerPerson;
    }

    public void setHandlerPerson(String handlerPerson) {
        this.handlerPerson = handlerPerson;
    }

    public Integer getAllCount() {
        return allCount;
    }

    public void setAllCount(Integer allCount) {
        this.allCount = allCount;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getInvolvedId() {
        return involvedId;
    }

    public void setInvolvedId(Integer involvedId) {
        this.involvedId = involvedId;
    }

    public Integer getRegisterUserId() {
        return registerUserId;
    }

    public void setRegisterUserId(Integer registerUserId) {
        this.registerUserId = registerUserId;
    }

    public Date getOutputTime() {
        return outputTime;
    }

    public void setOutputTime(Date outputTime) {
        this.outputTime = outputTime;
    }

    public Integer getShelfId() {
        return shelfId;
    }

    public void setShelfId(Integer shelfId) {
        this.shelfId = shelfId;
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

    public Integer getLawCaseId() {
        return lawCaseId;
    }

    public void setLawCaseId(Integer lawCaseId) {
        this.lawCaseId = lawCaseId;
    }

    public Integer getInvolvedTypeId() {
        return involvedTypeId;
    }

    public void setInvolvedTypeId(Integer involvedTypeId) {
        this.involvedTypeId = involvedTypeId;
    }

    public Integer getOutputTypeId() {
        return outputTypeId;
    }

    public void setOutputTypeId(Integer outputTypeId) {
        this.outputTypeId = outputTypeId;
    }

    public Integer getInterrogateAreaId() {
        return interrogateAreaId;
    }

    public void setInterrogateAreaId(Integer interrogateAreaId) {
        this.interrogateAreaId = interrogateAreaId;
    }

    public Integer getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Integer organizationId) {
        this.organizationId = organizationId;
    }

    public Integer getPoliceId() {
        return policeId;
    }

    public void setPoliceId(Integer policeId) {
        this.policeId = policeId;
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

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
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

    public String getInvolvedType() {
        return involvedType;
    }

    public void setInvolvedType(String involvedType) {
        this.involvedType = involvedType;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGetPoliceName() {
        return getPoliceName;
    }

    public void setGetPoliceName(String getPoliceName) {
        this.getPoliceName = getPoliceName;
    }

    public String getGetUnit() {
        return getUnit;
    }

    public void setGetUnit(String getUnit) {
        this.getUnit = getUnit;
    }

    public String getLogName() {
        return logName;
    }

    public void setLogName(String logName) {
        this.logName = logName;
    }

    public String getOutputType() {
        return outputType;
    }

    public void setOutputType(String outputType) {
        this.outputType = outputType;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Override
    public String toString() {
        return "OutputRecordEntity{" +
                "id=" + id +
                ", involvedId=" + involvedId +
                ", registerUserId=" + registerUserId +
                ", outputTime=" + outputTime +
                ", shelfId=" + shelfId +
                ", locationId=" + locationId +
                ", warehouseId=" + warehouseId +
                ", lawCaseId=" + lawCaseId +
                ", involvedTypeId=" + involvedTypeId +
                ", outputTypeId=" + outputTypeId +
                ", interrogateAreaId=" + interrogateAreaId +
                ", organizationId=" + organizationId +
                ", policeId=" + policeId +
                ", description='" + description + '\'' +
                ", createdTime=" + createdTime +
                ", updatedTime=" + updatedTime +
                ", count=" + count +
                ", caseNo='" + caseNo + '\'' +
                ", caseName='" + caseName + '\'' +
                ", involvedType='" + involvedType + '\'' +
                ", goodsName='" + goodsName + '\'' +
                ", getPoliceName='" + getPoliceName + '\'' +
                ", getUnit='" + getUnit + '\'' +
                ", logName='" + logName + '\'' +
                ", dept='" + dept + '\'' +
                ", allCount=" + allCount +
                ", leftCount=" + leftCount +
                ", handlerPerson='" + handlerPerson + '\'' +
                ", outputType='" + outputType + '\'' +
                ", telephone='" + telephone + '\'' +
                '}';
    }
}
