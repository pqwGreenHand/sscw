package com.zhixin.zhfz.bacs.entity;

import java.io.Serializable;
import java.util.Date;

public class BurnLogEntity implements Serializable {

	private static final long serialVersionUID = -2180661803162062392L;

	private Integer id;

	private Long serialId;

	private Long personId;

	private Integer burnUserId;

	private Date burnTime;

	private Integer caseId;

	private Integer areaId;

	private String personName;

	private String userName;

	private String ajmc;

	private String areaName;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Long getPersonId() {
		return personId;
	}

	public void setPersonId(Long personId) {
		this.personId = personId;
	}

	public Integer getBurnUserId() {
		return burnUserId;
	}

	public void setBurnUserId(Integer burnUserId) {
		this.burnUserId = burnUserId;
	}

	public Date getBurnTime() {
		return burnTime;
	}

	public void setBurnTime(Date burnTime) {
		this.burnTime = burnTime;
	}

	public Long getSerialId() {
		return serialId;
	}

	public void setSerialId(Long serialId) {
		this.serialId = serialId;
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

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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

	@Override
	public String toString() {
		return "BurnLogEntity [id=" + id + ", serialId=" + serialId + ", personId=" + personId + ", burnUserId="
				+ burnUserId + ", burnTime=" + burnTime + ", caseId=" + caseId + ", areaId=" + areaId + ", personName="
				+ personName + ", userName=" + userName + ", ajmc=" + ajmc + ", areaName=" + areaName + "]";
	}

}
