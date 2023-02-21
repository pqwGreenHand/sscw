package com.zhixin.zhfz.bacs.entity;

import java.util.Date;

public class SecurityEntity implements java.io.Serializable{
	
	private static final long serialVersionUID = -4783107711681656029L;
	
    private Integer id;

    private Long serialId;

    private Long personId;
    
    private Integer sex;
    
    private Date birth;

    private Integer checkUserId;

    private Date checkedStartTime;
    
    private Date checkedEndTime;

    private Integer caseId;

    private Integer areaId;

    private String medicalHistory;

    private Integer checkType;

    private Integer physical;

    private String physicalDescription;

    private Integer injury;

    private String injuryDescription;
    
    private String dangerous;

    private String otherDescription;

    private Date createdTime;

    private Date updatedTime;
    
    private String serialNo;
    
    private String personName;
    
    private Integer ajlx;

	private String caseNature;

	private String ab;
    
    private String checkUserName;
    
    private String caseName;
    
    private String areaName;
    
    private String certificateNo;
    
    private String certificateTypeName;
    
    private String checkUserNo;
    
    private Date involvedDate;

    private String afdd;

    private String involvedPeople;
    
    private String againReason;//再次体检原因
    
    private int hasWine;//有饮酒
	private int hasInjury;//体表有伤痕
	private int hasPhoto;//有拍照
	private  String uuid;
	private String count;
	private String serialUUID;

	private Date afsj;

	private String opPid;
	private String opUserId;

	public String getAgainReason() {
		return againReason;
	}
	public void setAgainReason(String againReason) {
		this.againReason = againReason;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public Long getSerialId() {
		return serialId;
	}

	public void setSerialId(Long serialId) {
		this.serialId = serialId;
	}

	public Long getPersonId() {
		return personId;
	}
	public void setPersonId(Long personId) {
		this.personId = personId;
	}
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public Date getBirth() {
		return birth;
	}
	public void setBirth(Date birth) {
		this.birth = birth;
	}
	public Integer getCheckUserId() {
		return checkUserId;
	}
	public void setCheckUserId(Integer checkUserId) {
		this.checkUserId = checkUserId;
	}
	public Date getCheckedStartTime() {
		return checkedStartTime;
	}
	public void setCheckedStartTime(Date checkedStartTime) {
		this.checkedStartTime = checkedStartTime;
	}
	public Date getCheckedEndTime() {
		return checkedEndTime;
	}
	public void setCheckedEndTime(Date checkedEndTime) {
		this.checkedEndTime = checkedEndTime;
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

	public String getMedicalHistory() {
		return medicalHistory;
	}
	public void setMedicalHistory(String medicalHistory) {
		this.medicalHistory = medicalHistory;
	}
	public Integer getCheckType() {
		return checkType;
	}
	public void setCheckType(Integer checkType) {
		this.checkType = checkType;
	}
	public Integer getPhysical() {
		return physical;
	}
	public void setPhysical(Integer physical) {
		this.physical = physical;
	}
	public String getPhysicalDescription() {
		return physicalDescription;
	}
	public void setPhysicalDescription(String physicalDescription) {
		this.physicalDescription = physicalDescription;
	}
	public Integer getInjury() {
		return injury;
	}
	public void setInjury(Integer injury) {
		this.injury = injury;
	}
	public String getInjuryDescription() {
		return injuryDescription;
	}
	public void setInjuryDescription(String injuryDescription) {
		this.injuryDescription = injuryDescription;
	}
	public String getDangerous() {
		return dangerous;
	}
	public void setDangerous(String dangerous) {
		this.dangerous = dangerous;
	}
	public String getOtherDescription() {
		return otherDescription;
	}
	public void setOtherDescription(String otherDescription) {
		this.otherDescription = otherDescription;
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
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	public String getPersonName() {
		return personName;
	}
	public void setPersonName(String personName) {
		this.personName = personName;
	}
	public Integer getAjlx() {
		return ajlx;
	}
	public void setAjlx(Integer ajlx) {
		this.ajlx = ajlx;
	}
	public String getCaseNature() {
		return caseNature;
	}
	public void setCaseNature(String caseNature) {
		this.caseNature = caseNature;
	}

	public String getAb() {
		return ab;
	}

	public void setAb(String ab) {
		this.ab = ab;
	}

	public String getCheckUserName() {
		return checkUserName;
	}
	public void setCheckUserName(String checkUserName) {
		this.checkUserName = checkUserName;
	}
	public String getCaseName() {
		return caseName;
	}
	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getCertificateNo() {
		return certificateNo;
	}
	public void setCertificateNo(String certificateNo) {
		this.certificateNo = certificateNo;
	}
	public String getCertificateTypeName() {
		return certificateTypeName;
	}
	public void setCertificateTypeName(String certificateTypeName) {
		this.certificateTypeName = certificateTypeName;
	}
	public String getCheckUserNo() {
		return checkUserNo;
	}
	public void setCheckUserNo(String checkUserNo) {
		this.checkUserNo = checkUserNo;
	}
	public Date getInvolvedDate() {
		return involvedDate;
	}
	public void setInvolvedDate(Date involvedDate) {
		this.involvedDate = involvedDate;
	}
	public String getAfdd() {
		return afdd;
	}

	public void setAfdd(String afdd) {
		this.afdd = afdd;
	}
	public String getInvolvedPeople() {
		return involvedPeople;
	}
	public void setInvolvedPeople(String involvedPeople) {
		this.involvedPeople = involvedPeople;
	}
	public int getHasWine() {
		return hasWine;
	}
	public void setHasWine(int hasWine) {
		this.hasWine = hasWine;
	}
	public int getHasInjury() {
		return hasInjury;
	}
	public void setHasInjury(int hasInjury) {
		this.hasInjury = hasInjury;
	}
	public int getHasPhoto() {
		return hasPhoto;
	}
	public void setHasPhoto(int hasPhoto) {
		this.hasPhoto = hasPhoto;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getSerialUUID() {
		return serialUUID;
	}

	public void setSerialUUID(String serialUUID) {
		this.serialUUID = serialUUID;
	}

	public Date getAfsj() {
		return afsj;
	}
	public void setAfsj(Date afsj) {
		this.afsj = afsj;
	}

	public String getOpPid() {
		return opPid;
	}

	public void setOpPid(String opPid) {
		this.opPid = opPid;
	}

	public String getOpUserId() {
		return opUserId;
	}

	public void setOpUserId(String opUserId) {
		this.opUserId = opUserId;
	}

	@Override
	public String toString() {
		return "SecurityEntity{" +
				"id=" + id +
				", serialId=" + serialId +
				", personId=" + personId +
				", sex=" + sex +
				", birth=" + birth +
				", checkUserId=" + checkUserId +
				", checkedStartTime=" + checkedStartTime +
				", checkedEndTime=" + checkedEndTime +
				", caseId=" + caseId +
				", areaId=" + areaId +
				", medicalHistory='" + medicalHistory + '\'' +
				", checkType=" + checkType +
				", physical=" + physical +
				", physicalDescription='" + physicalDescription + '\'' +
				", injury=" + injury +
				", injuryDescription='" + injuryDescription + '\'' +
				", dangerous='" + dangerous + '\'' +
				", otherDescription='" + otherDescription + '\'' +
				", createdTime=" + createdTime +
				", updatedTime=" + updatedTime +
				", serialNo='" + serialNo + '\'' +
				", personName='" + personName + '\'' +
				", ajlx=" + ajlx +
				", caseNature='" + caseNature + '\'' +
				", ab='" + ab + '\'' +
				", checkUserName='" + checkUserName + '\'' +
				", caseName='" + caseName + '\'' +
				", areaName='" + areaName + '\'' +
				", certificateNo='" + certificateNo + '\'' +
				", certificateTypeName='" + certificateTypeName + '\'' +
				", checkUserNo='" + checkUserNo + '\'' +
				", involvedDate=" + involvedDate +
				", afdd='" + afdd + '\'' +
				", involvedPeople='" + involvedPeople + '\'' +
				", againReason='" + againReason + '\'' +
				", hasWine=" + hasWine +
				", hasInjury=" + hasInjury +
				", hasPhoto=" + hasPhoto +
				", uuid='" + uuid + '\'' +
				", count='" + count + '\'' +
				", serialUUID='" + serialUUID + '\'' +
				", afsj=" + afsj +
				", opPid='" + opPid + '\'' +
				", opUserId='" + opUserId + '\'' +
				'}';
	}
}