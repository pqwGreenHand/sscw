package com.zhixin.zhfz.bacs.form;

import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.Date;

public class SecurityForm implements java.io.Serializable {

	private static final long serialVersionUID = -4783107711681656029L;

	private Integer id;

	private Long interrogateSerialId;

	private Long personId;

	private Integer checkUserId;

	private Date checkedStartTime;

	private Date checkedEndTime;

	private Integer lawCaseId;

	private Integer caseType;

	private Integer interrogateAreaId;

	private String medicalHistory;

	private Integer type;

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

	private String checkUserNo;

	private String lawCaseName;

	private String interrogateAreaName;

	private String certificateNo;

	private String certificateTypeName;

	private Date involvedDate;

	private String involvedAddress;

	private String involvedPeople;

	private String involvedReason;

	private String involvedReasonText;
	
	private String againReason; //再次安检原因
	
	private int hasWine;//有饮酒
	private int hasInjury;//体表有伤痕
	private int hasPhoto;//有拍照
	private String uuid; //uuid
	private String count;//第几次安检了
    private String areaId;

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	private  MultipartFile[] files ;

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
	public Long getInterrogateSerialId() {
		return interrogateSerialId;
	}
	public void setInterrogateSerialId(Long interrogateSerialId) {
		this.interrogateSerialId = interrogateSerialId;
	}
	public Long getPersonId() {
		return personId;
	}
	public void setPersonId(Long personId) {
		this.personId = personId;
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
	public Integer getLawCaseId() {
		return lawCaseId;
	}
	public void setLawCaseId(Integer lawCaseId) {
		this.lawCaseId = lawCaseId;
	}
	public Integer getCaseType() {
		return caseType;
	}
	public void setCaseType(Integer caseType) {
		this.caseType = caseType;
	}
	public Integer getInterrogateAreaId() {
		return interrogateAreaId;
	}
	public void setInterrogateAreaId(Integer interrogateAreaId) {
		this.interrogateAreaId = interrogateAreaId;
	}
	public String getMedicalHistory() {
		return medicalHistory;
	}
	public void setMedicalHistory(String medicalHistory) {
		this.medicalHistory = medicalHistory;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
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
	public String getCheckUserNo() {
		return checkUserNo;
	}
	public void setCheckUserNo(String checkUserNo) {
		this.checkUserNo = checkUserNo;
	}
	public String getLawCaseName() {
		return lawCaseName;
	}
	public void setLawCaseName(String lawCaseName) {
		this.lawCaseName = lawCaseName;
	}
	public String getInterrogateAreaName() {
		return interrogateAreaName;
	}
	public void setInterrogateAreaName(String interrogateAreaName) {
		this.interrogateAreaName = interrogateAreaName;
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
	public Date getInvolvedDate() {
		return involvedDate;
	}
	public void setInvolvedDate(Date involvedDate) {
		this.involvedDate = involvedDate;
	}
	public String getInvolvedAddress() {
		return involvedAddress;
	}
	public void setInvolvedAddress(String involvedAddress) {
		this.involvedAddress = involvedAddress;
	}
	public String getInvolvedPeople() {
		return involvedPeople;
	}
	public void setInvolvedPeople(String involvedPeople) {
		this.involvedPeople = involvedPeople;
	}
	public String getInvolvedReason() {
		return involvedReason;
	}
	public void setInvolvedReason(String involvedReason) {
		this.involvedReason = involvedReason;
	}
	public String getInvolvedReasonText() {
		return involvedReasonText;
	}
	public void setInvolvedReasonText(String involvedReasonText) {
		this.involvedReasonText = involvedReasonText;
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

	public MultipartFile[] getFiles() {
		return files;
	}

	public void setFiles(MultipartFile[] files) {
		this.files = files;
	}

	@Override
	public String toString() {
		return "SecurityForm{" +
				"id=" + id +
				", interrogateSerialId=" + interrogateSerialId +
				", personId=" + personId +
				", checkUserId=" + checkUserId +
				", checkedStartTime=" + checkedStartTime +
				", checkedEndTime=" + checkedEndTime +
				", lawCaseId=" + lawCaseId +
				", caseType=" + caseType +
				", interrogateAreaId=" + interrogateAreaId +
				", medicalHistory='" + medicalHistory + '\'' +
				", type=" + type +
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
				", checkUserNo='" + checkUserNo + '\'' +
				", lawCaseName='" + lawCaseName + '\'' +
				", interrogateAreaName='" + interrogateAreaName + '\'' +
				", certificateNo='" + certificateNo + '\'' +
				", certificateTypeName='" + certificateTypeName + '\'' +
				", involvedDate=" + involvedDate +
				", involvedAddress='" + involvedAddress + '\'' +
				", involvedPeople='" + involvedPeople + '\'' +
				", involvedReason='" + involvedReason + '\'' +
				", involvedReasonText='" + involvedReasonText + '\'' +
				", againReason='" + againReason + '\'' +
				", hasWine=" + hasWine +
				", hasInjury=" + hasInjury +
				", hasPhoto=" + hasPhoto +
				", uuid='" + uuid + '\'' +
				", count='" + count + '\'' +
				", areaId='" + areaId + '\'' +
				", files=" + Arrays.toString(files) +
				'}';
	}
}