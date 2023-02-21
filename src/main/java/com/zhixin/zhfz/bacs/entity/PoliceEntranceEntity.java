package com.zhixin.zhfz.bacs.entity;

import java.util.Date;

public class PoliceEntranceEntity implements java.io.Serializable {

	private static final long serialVersionUID = 6647205086835282661L;

	private Long id;

	private Integer caseId;

	private Integer areaId;

	private Integer orderRequestId;

	private Long policeId;

	private Integer policeType;

	private Integer cuffId;

	private Date InTime;

	private Date outTime;

	private Integer policeInCount;

	private Integer policeTotalCount;

	private String caseNature;

	private String certificateNo;

	private String policeNo;

	private String name;

	private String areaName;

	private Integer ajlx;

	private String ajmc;

	private String ajbh;

	private String cuffNo;

	private Integer bCaseId;

	private String opPid;
	private Integer opUserId;
	private String orgName;

	//数据库来源
	private Integer databaseSource;
	//数据库表的ID
	private Long tableId;

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getPoliceNo() {
		return policeNo;
	}

	public void setPoliceNo(String policeNo) {
		this.policeNo = policeNo;
	}

	public Integer getOrderRequestId() {
		return orderRequestId;
	}

	public void setOrderRequestId(Integer orderRequestId) {
		this.orderRequestId = orderRequestId;
	}

	public String getOpPid() {
		return opPid;
	}

	public void setOpPid(String opPid) {
		this.opPid = opPid;
	}

	public Integer getOpUserId() {
		return opUserId;
	}

	public void setOpUserId(Integer opUserId) {
		this.opUserId = opUserId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getCaseId() {
		return caseId;
	}

	public void setCaseId(Integer caseId) {
		this.caseId = caseId;
	}

	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

	public Long getPoliceId() {
		return policeId;
	}

	public void setPoliceId(Long policeId) {
		this.policeId = policeId;
	}

	public Integer getPoliceType() {
		return policeType;
	}

	public void setPoliceType(Integer policeType) {
		this.policeType = policeType;
	}

	public Integer getCuffId() {
		return cuffId;
	}

	public void setCuffId(Integer cuffId) {
		this.cuffId = cuffId;
	}

	public Date getInTime() {
		return InTime;
	}

	public void setInTime(Date inTime) {
		InTime = inTime;
	}

	public Date getOutTime() {
		return outTime;
	}

	public void setOutTime(Date outTime) {
		this.outTime = outTime;
	}

	public Integer getPoliceInCount() {
		return policeInCount;
	}

	public void setPoliceInCount(Integer policeInCount) {
		this.policeInCount = policeInCount;
	}

	public Integer getPoliceTotalCount() {
		return policeTotalCount;
	}

	public void setPoliceTotalCount(Integer policeTotalCount) {
		this.policeTotalCount = policeTotalCount;
	}

	public String getCaseNature() {
		return caseNature;
	}

	public void setCaseNature(String caseNature) {
		this.caseNature = caseNature;
	}

	public String getCertificateNo() {
		return certificateNo;
	}

	public void setCertificateNo(String certificateNo) {
		this.certificateNo = certificateNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public Integer getAjlx() {
		return ajlx;
	}

	public void setAjlx(Integer ajlx) {
		this.ajlx = ajlx;
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

	public String getCuffNo() {
		return cuffNo;
	}

	public void setCuffNo(String cuffNo) {
		this.cuffNo = cuffNo;
	}

	public Integer getbCaseId() {
		return bCaseId;
	}

	public void setbCaseId(Integer bCaseId) {
		this.bCaseId = bCaseId;
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
		return "PoliceEntranceEntity{" +
				"id=" + id +
				", caseId=" + caseId +
				", areaId=" + areaId +
				", policeId=" + policeId +
				", policeType=" + policeType +
				", cuffId=" + cuffId +
				", InTime=" + InTime +
				", outTime=" + outTime +
				", policeInCount=" + policeInCount +
				", policeTotalCount=" + policeTotalCount +
				", caseNature='" + caseNature + '\'' +
				", certificateNo='" + certificateNo + '\'' +
				", name='" + name + '\'' +
				", areaName='" + areaName + '\'' +
				", ajlx=" + ajlx +
				", ajmc='" + ajmc + '\'' +
				", ajbh='" + ajbh + '\'' +
				", cuffNo='" + cuffNo + '\'' +
				", bCaseId=" + bCaseId +
				", opPid=" + opPid +
				", opUserId=" + opUserId +
				", databaseSource=" + databaseSource +
				", tableId=" + tableId +
				'}';
	}
}