package com.zhixin.zhfz.sacw.entity;

import java.io.Serializable;
import java.util.Date;

public class UnOwnerEntity implements Serializable {


    private static final long serialVersionUID = 1L;
    private int id;
    private int lawCaseId;
    private String caseNo;
    private String caseName;
    private String name;//物品名称
    private String detailCount;
    private String unit;
    private double weight;
    private double worth;
    private String description;
    private int involvedTypeId;//物品类型ID
    private String involvedTypeName;//物品类型名称
    private int inputTypeId;//入库方式ID
    private String inputTypeName;//入库方式名称
    private String barcode;
    private String involvedOwner;//物品持有人
    private int status;
    private Date expiredTime;//保存日期
    private int type;//物品分类：1是涉案物品，2是无主物品
    private String outputCount;//出库数量
    private int registerUserId;
    private String registerUserName;
    private Date createdTime;
    private Date updatedTime;
    private String uuid;

    private Date operationTime;
    private String operation;

    private String url;//入出库图片的URL

    private int areaId;
    private String areaName;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLawCaseId() {
        return lawCaseId;
    }

    public void setLawCaseId(int lawCaseId) {
        this.lawCaseId = lawCaseId;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetailCount() {
        return detailCount;
    }

    public void setDetailCount(String detailCount) {
        this.detailCount = detailCount;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getWorth() {
        return worth;
    }

    public void setWorth(double worth) {
        this.worth = worth;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getInvolvedOwner() {
        return involvedOwner;
    }

    public void setInvolvedOwner(String involvedOwner) {
        this.involvedOwner = involvedOwner;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(Date expiredTime) {
        this.expiredTime = expiredTime;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getOutputCount() {
        return outputCount;
    }

    public void setOutputCount(String outputCount) {
        this.outputCount = outputCount;
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

    public String getInvolvedTypeName() {
        return involvedTypeName;
    }

    public void setInvolvedTypeName(String involvedTypeName) {
        this.involvedTypeName = involvedTypeName;
    }

    public String getInputTypeName() {
        return inputTypeName;
    }

    public void setInputTypeName(String inputTypeName) {
        this.inputTypeName = inputTypeName;
    }

    public Date getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(Date operationTime) {
        this.operationTime = operationTime;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    @Override
    public String toString() {
        return "UnOwnerEntity{" +
                "id=" + id +
                ", lawCaseId=" + lawCaseId +
                ", caseNo='" + caseNo + '\'' +
                ", caseName='" + caseName + '\'' +
                ", name='" + name + '\'' +
                ", detailCount='" + detailCount + '\'' +
                ", unit='" + unit + '\'' +
                ", weight=" + weight +
                ", worth=" + worth +
                ", description='" + description + '\'' +
                ", involvedTypeId=" + involvedTypeId +
                ", involvedTypeName='" + involvedTypeName + '\'' +
                ", inputTypeId=" + inputTypeId +
                ", inputTypeName='" + inputTypeName + '\'' +
                ", barcode='" + barcode + '\'' +
                ", involvedOwner='" + involvedOwner + '\'' +
                ", status=" + status +
                ", expiredTime=" + expiredTime +
                ", type=" + type +
                ", outputCount='" + outputCount + '\'' +
                ", registerUserId=" + registerUserId +
                ", registerUserName='" + registerUserName + '\'' +
                ", createdTime=" + createdTime +
                ", updatedTime=" + updatedTime +
                ", operationTime=" + operationTime +
                ", operation='" + operation + '\'' +
                ", url='" + url + '\'' +
                ", areaId=" + areaId +
                ", areaName='" + areaName + '\'' +
                '}';
    }
}
