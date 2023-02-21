package com.zhixin.zhfz.sacw.entity;

import java.io.Serializable;
import java.util.Date;

public class InputEntity implements Serializable {


    /**
     *
     */
    private static final long serialVersionUID = 1646452093843010156L;
    private int id;

    private int lawCaseId;
    //案件编号
    private String caseNo;
    //案件名称
    private String caseName;
    //物品名称
    private String woodsName;
    //物品类别
    private String woodsType;
    //物品id
    private int involvedId;
    //区域id
    private int locationId;
    //物品类型id
    private int involvedTypeId;

    //物品入库方式id
    private int inputTypeId;
    //民警id
    private int policeId1;
    //货架id
    private int shelfId;
    //仓库id
    private int warehouseId;
    //物品数量
    private int count;
    //入库时间
    private Date inputTime;
    //登记人id
    private int registerUserId;
    //登记人姓名
    private String registerUserName;
    //物品存放位置
    private String location;
    //条码内容
    private String barcode;
    //办案地址
    private String interrogateName;

    private int organizationId;//移交民警所在单位ID

    private int areaId;//保管场所ID
    private int labelId;//电子标签
    private String labelNo;//标签编号

    private Date createdTime;
    private Date updatedTime;

    private String description;
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


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInterrogateName() {
        return interrogateName;
    }

    public void setInterrogateName(String interrogateName) {
        this.interrogateName = interrogateName;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getLawCaseId() {
        return lawCaseId;
    }

    public void setLawCaseId(int lawCaseId) {
        this.lawCaseId = lawCaseId;
    }

    public int getInvolvedId() {
        return involvedId;
    }

    public void setInvolvedId(int involvedId) {
        this.involvedId = involvedId;
    }

    public int getShelfId() {
        return shelfId;
    }

    public void setShelfId(int shelfId) {
        this.shelfId = shelfId;
    }

    public int getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(int warehouseId) {
        this.warehouseId = warehouseId;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public int getInvolvedTypeId() {
        return involvedTypeId;
    }

    public void setInvolvedTypeId(int involvedTypeId) {
        this.involvedTypeId = involvedTypeId;
    }

    public int getInputTypeId() {
        return inputTypeId;
    }

    public void setInputTypeId(int inputTypeId) {
        this.inputTypeId = inputTypeId;
    }

    public int getPoliceId1() {
        return policeId1;
    }

    public void setPoliceId1(int policeId1) {
        this.policeId1 = policeId1;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public int getRegisterUserId() {
        return registerUserId;
    }

    public void setRegisterUserId(int registerUserId) {
        this.registerUserId = registerUserId;
    }

    public String getRegisterUserName() {
        return registerUserName;
    }

    public void setRegisterUserName(String registerUserName) {
        this.registerUserName = registerUserName;
    }

    public String getLocation() {
        return location;
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

    public int getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(int organizationId) {
        this.organizationId = organizationId;
    }

    public int getAreaId() {
        return areaId;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }

    public int getLabelId() {
        return labelId;
    }

    public void setLabelId(int labelId) {
        this.labelId = labelId;
    }

    public String getLabelNo() {
        return labelNo;
    }

    public void setLabelNo(String labelNo) {
        this.labelNo = labelNo;
    }

    @Override
    public String toString() {
        return "InputEntity{" +
                "id=" + id +
                ", lawCaseId=" + lawCaseId +
                ", caseNo='" + caseNo + '\'' +
                ", caseName='" + caseName + '\'' +
                ", woodsName='" + woodsName + '\'' +
                ", woodsType='" + woodsType + '\'' +
                ", involvedId=" + involvedId +
                ", locationId=" + locationId +
                ", involvedTypeId=" + involvedTypeId +
                ", inputTypeId=" + inputTypeId +
                ", policeId1=" + policeId1 +
                ", shelfId=" + shelfId +
                ", warehouseId=" + warehouseId +
                ", count=" + count +
                ", inputTime=" + inputTime +
                ", registerUserId=" + registerUserId +
                ", registerUserName='" + registerUserName + '\'' +
                ", location='" + location + '\'' +
                ", barcode='" + barcode + '\'' +
                ", interrogateName='" + interrogateName + '\'' +
                ", organizationId=" + organizationId +
                ", areaId=" + areaId +
                ", labelId=" + labelId +
                ", labelNo='" + labelNo + '\'' +
                ", createdTime=" + createdTime +
                ", updatedTime=" + updatedTime +
                '}';
    }
}
