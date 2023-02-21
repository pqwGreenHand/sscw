package com.zhixin.zhfz.bacs.entity;

import java.io.Serializable;
import java.util.Date;

public class DvdLogEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;

	private String receiveUser;

	private Date receiveTime;

	private Integer caseId;

	private Integer areaId;

	private Integer registerUserId;

	private String ajmc;

	private String areaName;

	private String registerUserName;

	private String personName;

	private Integer personId;

	private String policeName;

	private String opPid;
	private Integer opUserId;

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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getReceiveUser() {
		return receiveUser;
	}

	public void setReceiveUser(String receiveUser) {
		this.receiveUser = receiveUser;
	}

	public Date getReceiveTime() {
		return receiveTime;
	}

	public void setReceiveTime(Date receiveTime) {
		this.receiveTime = receiveTime;
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

	public Integer getRegisterUserId() {
		return registerUserId;
	}

	public void setRegisterUserId(Integer registerUserId) {
		this.registerUserId = registerUserId;
	}

	public String getAjmc() {
		return ajmc;
	}

	public void setAjmc(String ajmc) {
		this.ajmc = ajmc;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getRegisterUserName() {
		return registerUserName;
	}

	public void setRegisterUserName(String registerUserName) {
		this.registerUserName = registerUserName;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public Integer getPersonId() {
		return personId;
	}

	public void setPersonId(Integer personId) {
		this.personId = personId;
	}

	public String getPoliceName() {
		return policeName;
	}

	public void setPoliceName(String policeName) {
		this.policeName = policeName;
	}

	@Override
	public String toString() {
		return "DvdLogEntity [id=" + id + ", receiveUser=" + receiveUser + ", receiveTime=" + receiveTime + ", caseId="
				+ caseId + ", areaId=" + areaId + ", registerUserId=" + registerUserId + ", ajmc=" + ajmc
				+ ", areaName=" + areaName + ", registerUserName=" + registerUserName + ", personName=" + personName
				+ ", personId=" + personId + ", policeName=" + policeName + "]";
	}

}