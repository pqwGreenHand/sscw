package com.zhixin.zhfz.sacw.entity;

import java.io.Serializable;
import java.util.Date;

public class SearchInvolvedEntity implements Serializable {


    private static final long serialVersionUID = 1L;
    private int id;
    private String caseNo;
    private String caseName;
    private String mainPoliceName;//主办民警
    private String policeName1;//移交民警
    private String organizationName1;//移交单位
    private String involvedOwner;
    private String involvedName;
    private String involvedTypeName;
    private String detailCount;
    private String unit;
    private String barcode;
    private int status;
    private Date createdTime;
    private int areaId;
    private String areaName;
    private int warehouseId;
    private String warehouseName;
    private String policeNo1;

    public static long getSerialVersionUID() {
        return serialVersionUID;
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

    public String getMainPoliceName() {
        return mainPoliceName;
    }

    public void setMainPoliceName(String mainPoliceName) {
        this.mainPoliceName = mainPoliceName;
    }

    public String getPoliceName1() {
        return policeName1;
    }

    public void setPoliceName1(String policeName1) {
        this.policeName1 = policeName1;
    }

    public String getOrganizationName1() {
        return organizationName1;
    }

    public void setOrganizationName1(String organizationName1) {
        this.organizationName1 = organizationName1;
    }

    public String getInvolvedOwner() {
        return involvedOwner;
    }

    public void setInvolvedOwner(String involvedOwner) {
        this.involvedOwner = involvedOwner;
    }

    public String getInvolvedName() {
        return involvedName;
    }

    public void setInvolvedName(String involvedName) {
        this.involvedName = involvedName;
    }

    public String getInvolvedTypeName() {
        return involvedTypeName;
    }

    public void setInvolvedTypeName(String involvedTypeName) {
        this.involvedTypeName = involvedTypeName;
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

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
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

    public int getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(int warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public String getPoliceNo1() {
        return policeNo1;
    }

    public void setPoliceNo1(String policeNo1) {
        this.policeNo1 = policeNo1;
    }


    @Override
    public String toString() {
        return "SearchInvolvedEntity{" +
                "id=" + id +
                ", caseNo='" + caseNo + '\'' +
                ", caseName='" + caseName + '\'' +
                ", mainPoliceName='" + mainPoliceName + '\'' +
                ", policeName1='" + policeName1 + '\'' +
                ", organizationName1='" + organizationName1 + '\'' +
                ", involvedOwner='" + involvedOwner + '\'' +
                ", involvedName='" + involvedName + '\'' +
                ", involvedTypeName='" + involvedTypeName + '\'' +
                ", detailCount='" + detailCount + '\'' +
                ", unit='" + unit + '\'' +
                ", barcode='" + barcode + '\'' +
                ", status=" + status +
                ", createdTime=" + createdTime +
                ", areaId=" + areaId +
                ", areaName='" + areaName + '\'' +
                ", warehouseId=" + warehouseId +
                ", warehouseName='" + warehouseName + '\'' +
                ", policeNo1='" + policeNo1 + '\'' +
                '}';
    }
}
