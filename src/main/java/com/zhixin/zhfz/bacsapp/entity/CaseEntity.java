package com.zhixin.zhfz.bacsapp.entity;

import java.io.Serializable;
import java.util.Date;

public class CaseEntity implements Serializable {
    private static final long serialVersionUID = 2455224675701754469L;
    private Integer id;

    private Integer caseId;

    public Integer getCaseId() {
        return caseId;
    }

    public void setCaseId(Integer caseId) {
        this.caseId = caseId;
    }

    private String ajmc;

    private String ajbh;

    private Date afsj;

    private String afdd;

    private Integer ajlx;

    private Integer ab;

    private Integer zbmjId;

    private Integer xbmjId;

    private Integer zbdwId;

    private String jqh;

    private String zyaq;

    private Integer ajly;

    private Integer cjr;

    private Date updatedTime;

    private Date createdTime;

    private String zbdwName;

    private String uuid;

    private String afddCode;

    private int jzAjzt;

    private int jzRkzt;

    private String zbmjPid;

    private String xbmjIds;

    private String opPid;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getAfddCode() {
        return afddCode;
    }

    public void setAfddCode(String afddCode) {
        this.afddCode = afddCode;
    }

    //链表
    private String caseNature;

    private String zbmjName;

    private String xbmjName;

    private String zbmjCertificateNo;

    private String xbmjCertificateNo;

    private String  areaName;

    public String getZbmjCertificateNo() {
        return zbmjCertificateNo;
    }

    public void setZbmjCertificateNo(String zbmjCertificateNo) {
        this.zbmjCertificateNo = zbmjCertificateNo;
    }

    public String getXbmjCertificateNo() {
        return xbmjCertificateNo;
    }

    public void setXbmjCertificateNo(String xbmjCertificateNo) {
        this.xbmjCertificateNo = xbmjCertificateNo;
    }

    private Integer areaId;

    private Integer warehouseId;

    public Integer getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Integer warehouseId) {
        this.warehouseId = warehouseId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAjmc() {
        return ajmc;
    }

    public void setAjmc(String ajmc) {
        this.ajmc = ajmc;
    }

    public String getAjbh() {
        return ajbh;
    }

    public void setAjbh(String ajbh) {
        this.ajbh = ajbh;
    }

    public Date getAfsj() {
        return afsj;
    }

    public void setAfsj(Date afsj) {
        this.afsj = afsj;
    }

    public String getAfdd() {
        return afdd;
    }

    public void setAfdd(String afdd) {
        this.afdd = afdd;
    }

    public Integer getAjlx() {
        return ajlx;
    }

    public void setAjlx(Integer ajlx) {
        this.ajlx = ajlx;
    }

    public Integer getAb() {
        return ab;
    }

    public void setAb(Integer ab) {
        this.ab = ab;
    }

    public Integer getZbmjId() {
        return zbmjId;
    }

    public void setZbmjId(Integer zbmjId) {
        this.zbmjId = zbmjId;
    }

    public Integer getXbmjId() {
        return xbmjId;
    }

    public void setXbmjId(Integer xbmjId) {
        this.xbmjId = xbmjId;
    }

    public Integer getZbdwId() {
        return zbdwId;
    }

    public void setZbdwId(Integer zbdwId) {
        this.zbdwId = zbdwId;
    }

    public String getJqh() {
        return jqh;
    }

    public void setJqh(String jqh) {
        this.jqh = jqh;
    }

    public String getZyaq() {
        return zyaq;
    }

    public void setZyaq(String zyaq) {
        this.zyaq = zyaq;
    }

    public Integer getAjly() {
        return ajly;
    }

    public void setAjly(Integer ajly) {
        this.ajly = ajly;
    }

    public Integer getCjr() {
        return cjr;
    }

    public void setCjr(Integer cjr) {
        this.cjr = cjr;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getZbdwName() {
        return zbdwName;
    }

    public void setZbdwName(String zbdwName) {
        this.zbdwName = zbdwName;
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public String getCaseNature() {
        return caseNature;
    }

    public void setCaseNature(String caseNature) {
        this.caseNature = caseNature;
    }

    public String getZbmjName() {
        return zbmjName;
    }

    public void setZbmjName(String zbmjName) {
        this.zbmjName = zbmjName;
    }

    public String getXbmjName() {
        return xbmjName;
    }

    public void setXbmjName(String xbmjName) {
        this.xbmjName = xbmjName;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public int getJzAjzt() {
        return jzAjzt;
    }

    public void setJzAjzt(int jzAjzt) {
        this.jzAjzt = jzAjzt;
    }

    public int getJzRkzt() {
        return jzRkzt;
    }

    public void setJzRkzt(int jzRkzt) {
        this.jzRkzt = jzRkzt;
    }

    public String getZbmjPid() {
        return zbmjPid;
    }

    public void setZbmjPid(String zbmjPid) {
        this.zbmjPid = zbmjPid;
    }

    public String getXbmjIds() {
        return xbmjIds;
    }

    public void setXbmjIds(String xbmjIds) {
        this.xbmjIds = xbmjIds;
    }

    public String getOpPid() {
        return opPid;
    }

    public void setOpPid(String opPid) {
        this.opPid = opPid;
    }

    @Override
    public String toString() {
        return "CaseEntity{" +
                "id=" + id +
                ", ajmc='" + ajmc + '\'' +
                ", ajbh='" + ajbh + '\'' +
                ", afsj=" + afsj +
                ", afdd='" + afdd + '\'' +
                ", ajlx=" + ajlx +
                ", ab=" + ab +
                ", zbmjId=" + zbmjId +
                ", xbmjId=" + xbmjId +
                ", zbdwId=" + zbdwId +
                ", jqh='" + jqh + '\'' +
                ", zyaq='" + zyaq + '\'' +
                ", ajly=" + ajly +
                ", cjr=" + cjr +
                ", updatedTime=" + updatedTime +
                ", createdTime=" + createdTime +
                ", zbdwName='" + zbdwName + '\'' +
                ", uuid='" + uuid + '\'' +
                ", afddCode='" + afddCode + '\'' +
                ", jzAjzt=" + jzAjzt +
                ", jzRkzt=" + jzRkzt +
                ", zbmjPid='" + zbmjPid + '\'' +
                ", xbmjIds='" + xbmjIds + '\'' +
                ", opPid='" + opPid + '\'' +
                ", caseNature='" + caseNature + '\'' +
                ", zbmjName='" + zbmjName + '\'' +
                ", xbmjName='" + xbmjName + '\'' +
                ", zbmjCertificateNo='" + zbmjCertificateNo + '\'' +
                ", xbmjCertificateNo='" + xbmjCertificateNo + '\'' +
                ", areaName='" + areaName + '\'' +
                ", areaId=" + areaId +
                ", warehouseId=" + warehouseId +
                '}';
    }
}
