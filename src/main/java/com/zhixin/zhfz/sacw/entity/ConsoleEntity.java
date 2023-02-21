package com.zhixin.zhfz.sacw.entity;

import java.io.Serializable;

public class ConsoleEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;
    private long total;//总数

    private long caseNatureId;//案件性质Id
    private String caseNature;//案件性质

    private long organizationId;//单位Id
    private String organization;//单位名称

    private long outputTypeId;//出库方式Id
    private String outputType;//出库方式名称

    private int status;//物品状态
    private String statusName;//状态名称

    private int involvedTypeId;//物品类型Id
    private String involvedType;//物品类型名称

    private int wareHouseId; //仓库id
    private String wareHouseName; //仓库名称

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public void setCaseNatureId(long caseNatureId) {
        this.caseNatureId = caseNatureId;
    }

    public void setCaseNature(String caseNature) {
        this.caseNature = caseNature;
    }

    public void setOrganizationId(long organizationId) {
        this.organizationId = organizationId;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public void setOutputTypeId(long outputTypeId) {
        this.outputTypeId = outputTypeId;
    }

    public void setOutputType(String outputType) {
        this.outputType = outputType;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public void setInvolvedTypeId(int involvedTypeId) {
        this.involvedTypeId = involvedTypeId;
    }

    public void setInvolvedType(String involvedType) {
        this.involvedType = involvedType;
    }

    public void setWareHouseId(int wareHouseId) {
        this.wareHouseId = wareHouseId;
    }

    public void setWareHouseName(String wareHouseName) {
        this.wareHouseName = wareHouseName;
    }

    public Integer getId() {

        return id;
    }

    public long getTotal() {
        return total;
    }

    public long getCaseNatureId() {
        return caseNatureId;
    }

    public String getCaseNature() {
        return caseNature;
    }

    public long getOrganizationId() {
        return organizationId;
    }

    public String getOrganization() {
        return organization;
    }

    public long getOutputTypeId() {
        return outputTypeId;
    }

    public String getOutputType() {
        return outputType;
    }

    public int getStatus() {
        return status;
    }

    public String getStatusName() {
        return statusName;
    }

    public int getInvolvedTypeId() {
        return involvedTypeId;
    }

    public String getInvolvedType() {
        return involvedType;
    }

    public int getWareHouseId() {
        return wareHouseId;
    }

    public String getWareHouseName() {
        return wareHouseName;
    }
}
