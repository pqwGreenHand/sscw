package com.zhixin.zhfz.common.entity;

import java.io.Serializable;
import java.util.Date;

public class CaseEntity implements Serializable {
    private static final long serialVersionUID = 8580868785038906323L;
    private Integer id;

    private String ajmc;

    private Integer orderRequestId;

    public Integer getOrderRequestId() {
        return orderRequestId;
    }

    public void setOrderRequestId(Integer orderRequestId) {
        this.orderRequestId = orderRequestId;
    }

    private String ajbh;

    private Date afsj;

    private String afdd;

    private Integer ajlx;

    private Integer ab;

    private Integer zbmjId;

    private String xbmjId;

    private Integer zbdwId;

    private String jqbh;

    private String zyaq;

    private Integer ajly;

    private String ajlxDesc;

    private Integer cjr;

    private Date updatedTime;

    private Date createdTime;

    private String zbdwName;

    private String uuid;

    private String afddCode;
    
    private Integer jzAjzt;
    
    private Integer jzRkzt;

    private String zbmjPid;

    private String xbmjIds;

    private String opPid;
    //协办民警姓名
    private String xbmjXm;
    //主办民警姓名
    private String zbmjxm;
    //主办民警警号
    private String zbmjjh;
    //主办单位名称
    private String zbdwmc;
    //协办民警警号
    private String xbmjjh;
    //案别名称
    private String abmc;
    //主办单位代码
    private String zbdwdm;
    //案件状态
    private Integer ajzt;
    //数据库来源
    private Integer databaseSource;
    //数据库表的ID
    private Long tableId;

    public Integer getAjzt() {
        return ajzt;
    }

    public void setAjzt(Integer ajzt) {
        this.ajzt = ajzt;
    }

    public String getZbdwdm() {
        return zbdwdm;
    }

    public void setZbdwdm(String zbdwdm) {
        this.zbdwdm = zbdwdm;
    }

    public String getXbmjXm() {
        return xbmjXm;
    }

    public void setXbmjXm(String xbmjXm) {
        this.xbmjXm = xbmjXm;
    }

    public String getZbmjxm() {
        return zbmjxm;
    }

    public void setZbmjxm(String zbmjxm) {
        this.zbmjxm = zbmjxm;
    }

    public String getZbmjjh() {
        return zbmjjh;
    }

    public void setZbmjjh(String zbmjjh) {
        this.zbmjjh = zbmjjh;
    }

    public String getZbdwmc() {
        return zbdwmc;
    }

    public void setZbdwmc(String zbdwmc) {
        this.zbdwmc = zbdwmc;
    }

    public String getXbmjjh() {
        return xbmjjh;
    }

    public void setXbmjjh(String xbmjjh) {
        this.xbmjjh = xbmjjh;
    }

    public String getAbmc() {
        return abmc;
    }

    public void setAbmc(String abmc) {
        this.abmc = abmc;
    }

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

    public String getAjlxDesc() {
        return ajlxDesc;
    }

    public void setAjlxDesc(String ajlxDesc) {
        this.ajlxDesc = ajlxDesc;
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

    public String getXbmjId() {
        return xbmjId;
    }

    public void setXbmjId(String xbmjId) {
        this.xbmjId = xbmjId;
    }

    public Integer getZbdwId() {
        return zbdwId;
    }

    public void setZbdwId(Integer zbdwId) {
        this.zbdwId = zbdwId;
    }

    public String getJqbh() {
        return jqbh;
    }

    public void setJqbh(String jqbh) {
        this.jqbh = jqbh;
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

	public Integer getJzAjzt() {
		return jzAjzt;
	}

	public void setJzAjzt(Integer jzAjzt) {
		this.jzAjzt = jzAjzt;
	}

	public Integer getJzRkzt() {
		return jzRkzt;
	}

	public void setJzRkzt(Integer jzRkzt) {
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

    public Integer getDatabaseSource() {
        return databaseSource;
    }

    public void setDatabaseSource(Integer databaseSource) {
        this.databaseSource = databaseSource;
    }

    public Long getTableId() {
        return tableId;
    }

    public void setTableId(Long tableId) {
        this.tableId = tableId;
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
                ", xbmjId='" + xbmjId + '\'' +
                ", zbdwId=" + zbdwId +
                ", jqbh='" + jqbh + '\'' +
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
                ", databaseSource=" + databaseSource +
                ", tableId=" + tableId +
                '}';
    }
}
