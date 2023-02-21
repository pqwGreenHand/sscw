package com.zhixin.zhfz.bacsapp.entity;

import java.util.Date;


public class NjdjAppEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private Long serialId;

	private Integer caseId;

	private Integer mainUserId;

	private Integer checkUserId;

	private Date urinalysisTime;

	private Integer areaId;

	private String testMethod;

	private String result;

	private Integer count;

	private String description;

	private Date createdTime;

	private Date updatedTime;

	//链表

	private String personName;

	private int sex;

	private String age;

	private String crimeType;

	private int caseType;

	private String mainUserName;

	private String checkUserName;

	private String cuffNumber;

	private String checkUserNo;

	private String sendOrgName;

	private String birth;

	private String certificateNo;

	private String certificateType;

	private String serialNo;

	private Date InTime;

	private int status;

	private String uuid;

	private String inPhotoName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Integer getMainUserId() {
		return mainUserId;
	}

	public void setMainUserId(Integer mainUserId) {
		this.mainUserId = mainUserId;
	}

	public Integer getCheckUserId() {
		return checkUserId;
	}

	public void setCheckUserId(Integer checkUserId) {
		this.checkUserId = checkUserId;
	}

	public Date getUrinalysisTime() {
		return urinalysisTime;
	}

	public void setUrinalysisTime(Date urinalysisTime) {
		this.urinalysisTime = urinalysisTime;
	}

	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

	public String getTestMethod() {
		return testMethod;
	}

	public void setTestMethod(String testMethod) {
		this.testMethod = testMethod;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getCrimeType() {
		return crimeType;
	}

	public void setCrimeType(String crimeType) {
		this.crimeType = crimeType;
	}

	public int getCaseType() {
		return caseType;
	}

	public void setCaseType(int caseType) {
		this.caseType = caseType;
	}

	public String getMainUserName() {
		return mainUserName;
	}

	public void setMainUserName(String mainUserName) {
		this.mainUserName = mainUserName;
	}

	public String getCheckUserName() {
		return checkUserName;
	}

	public void setCheckUserName(String checkUserName) {
		this.checkUserName = checkUserName;
	}

	public String getCuffNumber() {
		return cuffNumber;
	}

	public void setCuffNumber(String cuffNumber) {
		this.cuffNumber = cuffNumber;
	}

	public String getCheckUserNo() {
		return checkUserNo;
	}

	public void setCheckUserNo(String checkUserNo) {
		this.checkUserNo = checkUserNo;
	}

	public String getSendOrgName() {
		return sendOrgName;
	}

	public void setSendOrgName(String sendOrgName) {
		this.sendOrgName = sendOrgName;
	}

	public String getBirth() {
		return birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}

	public String getCertificateNo() {
		return certificateNo;
	}

	public void setCertificateNo(String certificateNo) {
		this.certificateNo = certificateNo;
	}

	public String getCertificateType() {
		return certificateType;
	}

	public void setCertificateType(String certificateType) {
		this.certificateType = certificateType;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public Date getInTime() {
		return InTime;
	}

	public void setInTime(Date inTime) {
		InTime = inTime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getInPhotoName() {
		return inPhotoName;
	}

	public void setInPhotoName(String inPhotoName) {
		this.inPhotoName = inPhotoName;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("{");
		sb.append("\"id\":")
				.append(id);
		sb.append(",\"serialId\":")
				.append(serialId);
		sb.append(",\"caseId\":")
				.append(caseId);
		sb.append(",\"mainUserId\":")
				.append(mainUserId);
		sb.append(",\"checkUserId\":")
				.append(checkUserId);
		sb.append(",\"urinalysisTime\":\"")
				.append(urinalysisTime).append('\"');
		sb.append(",\"areaId\":")
				.append(areaId);
		sb.append(",\"testMethod\":\"")
				.append(testMethod).append('\"');
		sb.append(",\"result\":\"")
				.append(result).append('\"');
		sb.append(",\"count\":")
				.append(count);
		sb.append(",\"description\":\"")
				.append(description).append('\"');
		sb.append(",\"createdTime\":\"")
				.append(createdTime).append('\"');
		sb.append(",\"updatedTime\":\"")
				.append(updatedTime).append('\"');
		sb.append(",\"personName\":\"")
				.append(personName).append('\"');
		sb.append(",\"sex\":")
				.append(sex);
		sb.append(",\"age\":\"")
				.append(age).append('\"');
		sb.append(",\"crimeType\":\"")
				.append(crimeType).append('\"');
		sb.append(",\"caseType\":")
				.append(caseType);
		sb.append(",\"mainUserName\":\"")
				.append(mainUserName).append('\"');
		sb.append(",\"checkUserName\":\"")
				.append(checkUserName).append('\"');
		sb.append(",\"cuffNumber\":\"")
				.append(cuffNumber).append('\"');
		sb.append(",\"checkUserNo\":\"")
				.append(checkUserNo).append('\"');
		sb.append(",\"sendOrgName\":\"")
				.append(sendOrgName).append('\"');
		sb.append(",\"birth\":\"")
				.append(birth).append('\"');
		sb.append(",\"certificateNo\":\"")
				.append(certificateNo).append('\"');
		sb.append(",\"certificateType\":\"")
				.append(certificateType).append('\"');
		sb.append(",\"serialNo\":\"")
				.append(serialNo).append('\"');
		sb.append(",\"InTime\":\"")
				.append(InTime).append('\"');
		sb.append(",\"status\":")
				.append(status);
		sb.append(",\"uuid\":\"")
				.append(uuid).append('\"');
		sb.append(",\"inPhotoName\":\"")
				.append(inPhotoName).append('\"');
		sb.append(",\"areaId\":")
				.append(areaId);
		sb.append('}');
		return sb.toString();
	}
}