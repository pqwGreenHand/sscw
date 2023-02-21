package com.zhixin.zhfz.bacs.entity;

import java.io.Serializable;
import java.util.Date;

public class AskManagementExportEntity implements Serializable {

	private static final long serialVersionUID = -8544902255754221124L;

	private String caseId;

	private Integer recordId;

	private Integer roomId;

	private Integer serialId;

	private Integer personId;

	private Integer areaId;

	private String policeName;

	private String orgName;

	private String roomName;

	private String personName;

	private Integer sex;

	private String certificateNo;

	private Date inTime;

	private Date outTime;

	private Integer ajlx;

	private String ajmc;

	private String caseNature;

	private String serialNo;

	private String afdd;

	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	public Integer getRecordId() {
		return recordId;
	}

	public void setRecordId(Integer recordId) {
		this.recordId = recordId;
	}

	public Integer getRoomId() {
		return roomId;
	}

	public void setRoomId(Integer roomId) {
		this.roomId = roomId;
	}

	public Integer getSerialId() {
		return serialId;
	}

	public void setSerialId(Integer serialId) {
		this.serialId = serialId;
	}

	public Integer getPersonId() {
		return personId;
	}

	public void setPersonId(Integer personId) {
		this.personId = personId;
	}

	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

	public String getPoliceName() {
		return policeName;
	}

	public void setPoliceName(String policeName) {
		this.policeName = policeName;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getCertificateNo() {
		return certificateNo;
	}

	public void setCertificateNo(String certificateNo) {
		this.certificateNo = certificateNo;
	}

	public Date getInTime() {
		return inTime;
	}

	public void setInTime(Date inTime) {
		this.inTime = inTime;
	}

	public Date getOutTime() {
		return outTime;
	}

	public void setOutTime(Date outTime) {
		this.outTime = outTime;
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

	public String getCaseNature() {
		return caseNature;
	}

	public void setCaseNature(String caseNature) {
		this.caseNature = caseNature;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getAfdd() {
		return afdd;
	}

	public void setAfdd(String afdd) {
		this.afdd = afdd;
	}

	@Override
	public String toString() {
		return "AskManagementExportEntity{" +
				"caseId=" + caseId +
				", recordId=" + recordId +
				", roomId=" + roomId +
				", serialId=" + serialId +
				", personId=" + personId +
				", areaId=" + areaId +
				", policeName='" + policeName + '\'' +
				", orgName='" + orgName + '\'' +
				", roomName='" + roomName + '\'' +
				", personName='" + personName + '\'' +
				", sex=" + sex +
				", certificateNo='" + certificateNo + '\'' +
				", inTime=" + inTime +
				", outTime=" + outTime +
				", ajlx=" + ajlx +
				", ajmc='" + ajmc + '\'' +
				", caseNature='" + caseNature + '\'' +
				", serialNo='" + serialNo + '\'' +
				", afdd='" + afdd + '\'' +
				'}';
	}
}
