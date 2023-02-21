package com.zhixin.zhfz.sacw.entity;

import java.util.Date;

public class InputBookEntity implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    private int id;
    private String name;
    private String description;
    private String barcode;
    private String caseNo;
    private String caseName;
    private String involvedOwner;
    private String detailCount;
    private String unit;
    private String policeName;
    private String organization;
    private Date receivedTime;
    private Date updatedTime;
    private String outputTypeName;
    private String handlerPerson;
    private String telephone;
    private String reason;//出库原因
    private String outputCount;//出库数量
    private String sendOrganization;//移交单位
    private String areaName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
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

    public String getInvolvedOwner() {
        return involvedOwner;
    }

    public void setInvolvedOwner(String involvedOwner) {
        this.involvedOwner = involvedOwner;
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

    public String getPoliceName() {
        return policeName;
    }

    public void setPoliceName(String policeName) {
        this.policeName = policeName;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public Date getReceivedTime() {
        return receivedTime;
    }

    public void setReceivedTime(Date receivedTime) {
        this.receivedTime = receivedTime;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public String getOutputTypeName() {
        return outputTypeName;
    }

    public void setOutputTypeName(String outputTypeName) {
        this.outputTypeName = outputTypeName;
    }

    public String getHandlerPerson() {
        return handlerPerson;
    }

    public void setHandlerPerson(String handlerPerson) {
        this.handlerPerson = handlerPerson;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getOutputCount() {
        return outputCount;
    }

    public void setOutputCount(String outputCount) {
        this.outputCount = outputCount;
    }

    public String getSendOrganization() {
        return sendOrganization;
    }

    public void setSendOrganization(String sendOrganization) {
        this.sendOrganization = sendOrganization;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    @Override
    public String toString() {
        return "InputBookEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", barcode='" + barcode + '\'' +
                ", caseNo='" + caseNo + '\'' +
                ", caseName='" + caseName + '\'' +
                ", involvedOwner='" + involvedOwner + '\'' +
                ", detailCount='" + detailCount + '\'' +
                ", unit='" + unit + '\'' +
                ", policeName='" + policeName + '\'' +
                ", organization='" + organization + '\'' +
                ", receivedTime=" + receivedTime +
                ", updatedTime=" + updatedTime +
                ", outputTypeName='" + outputTypeName + '\'' +
                ", handlerPerson='" + handlerPerson + '\'' +
                ", telephone='" + telephone + '\'' +
                ", reason='" + reason + '\'' +
                ", outputCount='" + outputCount + '\'' +
                ", sendOrganization='" + sendOrganization + '\'' +
                ", areaName='" + areaName + '\'' +
                '}';
    }
}
