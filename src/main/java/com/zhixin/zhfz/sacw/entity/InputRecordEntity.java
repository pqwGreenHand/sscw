package com.zhixin.zhfz.sacw.entity;

import java.io.Serializable;
import java.util.Date;

public class InputRecordEntity implements Serializable {


    /**
     *
     */
    private static final long serialVersionUID = 1646452093843010156L;
    private Integer id;
    //案件编号
    private String caseNo;
    //案件名称
    private String caseName;
    //物品名称
    private String woodsName;
    //物品类别
    private String woodsType;
    //物品数量
    private int count;
    //入库时间
    private Date inputTime;
    //物品存放位置
    private String location;
    //物品存放位置
    private String goodsCode;
    //物品存放位置
    private String dept;
    //还可以出库的
    private Integer outstockable;

    private Integer policeId1;
    private Integer policeId2;
    private Integer involvedId;
    private Integer registerUserId;
    private Integer shelfId;
    private Integer locationId;

    private Integer warehouseId;
    private Integer lawCaseId;
    private Integer involvedTypeId;
    private Integer intputTypeId;
    private Integer interrogateAreaId;
    private Integer organizationId;
    private String description;
    private Date createdTime;
    private Date updatedTime;
    private Integer outputCount;
    private String uuid;


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Integer getOutputCount() {
        return outputCount;
    }

    public void setOutputCount(Integer outputCount) {
        this.outputCount = outputCount;
    }

    public Integer getPoliceId1() {
        return policeId1;
    }

    public void setPoliceId1(Integer policeId1) {
        this.policeId1 = policeId1;
    }

    public Integer getPoliceId2() {
        return policeId2;
    }

    public void setPoliceId2(Integer policeId2) {
        this.policeId2 = policeId2;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getWoodsName() {
        return woodsName;
    }

    public void setWoodsName(String woodsName) {
        this.woodsName = woodsName;
    }

    public String getWoodsType() {
        return woodsType;
    }

    public void setWoodsType(String woodsType) {
        this.woodsType = woodsType;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Date getInputTime() {
        return inputTime;
    }

    public void setInputTime(Date inputTime) {
        this.inputTime = inputTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public Integer getOutstockable() {
        return outstockable;
    }

    public void setOutstockable(Integer outstockable) {
        this.outstockable = outstockable;
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

    public Integer getIntputTypeId() {
        return intputTypeId;
    }

    public void setIntputTypeId(Integer intputTypeId) {
        this.intputTypeId = intputTypeId;
    }

    @Override
    public String toString() {
        return "InputRecordEntity [id=" + id + ", caseNo=" + caseNo + ", caseName=" + caseName + ", woodsName="
                + woodsName + ", woodsType=" + woodsType + ", count=" + count + ", inputTime=" + inputTime
                + ", location=" + location + ", goodsCode=" + goodsCode + ", dept=" + dept + ", outstockable="
                + outstockable + ", policeId1=" + policeId1 + ", policeId2=" + policeId2 + ", involvedId=" + involvedId
                + ", registerUserId=" + registerUserId + ", shelfId=" + shelfId + ", locationId=" + locationId
                + ", warehouseId=" + warehouseId + ", lawCaseId=" + lawCaseId + ", involvedTypeId=" + involvedTypeId
                + ", intputTypeId=" + intputTypeId + ", interrogateAreaId=" + interrogateAreaId + ", organizationId="
                + organizationId + ", description=" + description + ", createdTime=" + createdTime + ", updatedTime="
                + updatedTime + ", outputCount=" + outputCount + "]";
    }


}
