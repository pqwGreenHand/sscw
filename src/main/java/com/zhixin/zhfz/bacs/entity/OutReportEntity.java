package com.zhixin.zhfz.bacs.entity;

import java.util.Date;

public class OutReportEntity implements java.io.Serializable {
    /**
	 * 办案区使用情况登记表
	 */
	private static final long serialVersionUID = 4614966861269493860L;

	private Integer id;

	private String serialNo;
	private String name;
	private String sex;
	private int age;
	private Date birth;
	private String mobile;
	private String telephone;
	private String certificateNo;
	private String certificateType;
	private String caseReason;
	private String entranceProcedure;//入区手续
	private Date inTime;
	private String medicalHistory; //自述症状
	private String injuryDescription;//检查情况
	private int hasInjury;//体表是否有外伤
	private int hasPhoto;//有拍照
	private int hasWine;//有饮酒
	private Date tempOutTime;//临时出区时间
	private String tempOutReason;//临时出区原因
	private Date backTime;//临时出区返回时间
	private Date outTime;//出区时间
	private String confirmResult;//离开原因
	
	private String involvedName;//物品名称
	private String description;
	private int detailCount;
	private String unit;
	private String saveMethod;//保管措施
	private String lockerNo;//保管柜号
	private int isGet;//是否领取
	
	private Date startTime;
	private Date endTime;
	private String roomName;
	private String cameraNo;

	private String result;

	public OutReportEntity(Integer id, String serialNo, String name, String sex, int age, Date birth, String mobile, String telephone, String certificateNo, String certificateType, String caseReason, String entranceProcedure, Date inTime, String medicalHistory, String injuryDescription, int hasInjury, int hasPhoto, int hasWine, Date tempOutTime, String tempOutReason, Date backTime, Date outTime, String confirmResult, String involvedName, String description, int detailCount, String unit, String saveMethod, String lockerNo, int isGet, Date startTime, Date endTime, String roomName, String cameraNo, String result) {
		this.id = id;
		this.serialNo = serialNo;
		this.name = name;
		this.sex = sex;
		this.age = age;
		this.birth = birth;
		this.mobile = mobile;
		this.telephone = telephone;
		this.certificateNo = certificateNo;
		this.certificateType = certificateType;
		this.caseReason = caseReason;
		this.entranceProcedure = entranceProcedure;
		this.inTime = inTime;
		this.medicalHistory = medicalHistory;
		this.injuryDescription = injuryDescription;
		this.hasInjury = hasInjury;
		this.hasPhoto = hasPhoto;
		this.hasWine = hasWine;
		this.tempOutTime = tempOutTime;
		this.tempOutReason = tempOutReason;
		this.backTime = backTime;
		this.outTime = outTime;
		this.confirmResult = confirmResult;
		this.involvedName = involvedName;
		this.description = description;
		this.detailCount = detailCount;
		this.unit = unit;
		this.saveMethod = saveMethod;
		this.lockerNo = lockerNo;
		this.isGet = isGet;
		this.startTime = startTime;
		this.endTime = endTime;
		this.roomName = roomName;
		this.cameraNo = cameraNo;
		this.result = result;
	}

	public OutReportEntity() {
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
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

	public String getCaseReason() {
		return caseReason;
	}

	public void setCaseReason(String caseReason) {
		this.caseReason = caseReason;
	}

	public String getEntranceProcedure() {
		return entranceProcedure;
	}

	public void setEntranceProcedure(String entranceProcedure) {
		this.entranceProcedure = entranceProcedure;
	}

	public Date getInTime() {
		return inTime;
	}

	public void setInTime(Date inTime) {
		this.inTime = inTime;
	}

	public String getMedicalHistory() {
		return medicalHistory;
	}

	public void setMedicalHistory(String medicalHistory) {
		this.medicalHistory = medicalHistory;
	}

	public String getInjuryDescription() {
		return injuryDescription;
	}

	public void setInjuryDescription(String injuryDescription) {
		this.injuryDescription = injuryDescription;
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

	public int getHasWine() {
		return hasWine;
	}

	public void setHasWine(int hasWine) {
		this.hasWine = hasWine;
	}

	public Date getTempOutTime() {
		return tempOutTime;
	}

	public void setTempOutTime(Date tempOutTime) {
		this.tempOutTime = tempOutTime;
	}

	public String getTempOutReason() {
		return tempOutReason;
	}

	public void setTempOutReason(String tempOutReason) {
		this.tempOutReason = tempOutReason;
	}

	public Date getBackTime() {
		return backTime;
	}

	public void setBackTime(Date backTime) {
		this.backTime = backTime;
	}

	public Date getOutTime() {
		return outTime;
	}

	public void setOutTime(Date outTime) {
		this.outTime = outTime;
	}

	public String getConfirmResult() {
		return confirmResult;
	}

	public void setConfirmResult(String confirmResult) {
		this.confirmResult = confirmResult;
	}

	public String getInvolvedName() {
		return involvedName;
	}

	public void setInvolvedName(String involvedName) {
		this.involvedName = involvedName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getDetailCount() {
		return detailCount;
	}

	public void setDetailCount(int detailCount) {
		this.detailCount = detailCount;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getSaveMethod() {
		return saveMethod;
	}

	public void setSaveMethod(String saveMethod) {
		this.saveMethod = saveMethod;
	}

	public String getLockerNo() {
		return lockerNo;
	}

	public void setLockerNo(String lockerNo) {
		this.lockerNo = lockerNo;
	}

	public int getIsGet() {
		return isGet;
	}

	public void setIsGet(int isGet) {
		this.isGet = isGet;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public String getCameraNo() {
		return cameraNo;
	}

	public void setCameraNo(String cameraNo) {
		this.cameraNo = cameraNo;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return "OutReportEntity{" +
				"id=" + id +
				", serialNo='" + serialNo + '\'' +
				", name='" + name + '\'' +
				", sex='" + sex + '\'' +
				", age=" + age +
				", birth=" + birth +
				", mobile='" + mobile + '\'' +
				", telephone='" + telephone + '\'' +
				", certificateNo='" + certificateNo + '\'' +
				", certificateType='" + certificateType + '\'' +
				", caseReason='" + caseReason + '\'' +
				", entranceProcedure='" + entranceProcedure + '\'' +
				", inTime=" + inTime +
				", medicalHistory='" + medicalHistory + '\'' +
				", injuryDescription='" + injuryDescription + '\'' +
				", hasInjury=" + hasInjury +
				", hasPhoto=" + hasPhoto +
				", hasWine=" + hasWine +
				", tempOutTime=" + tempOutTime +
				", tempOutReason='" + tempOutReason + '\'' +
				", backTime=" + backTime +
				", outTime=" + outTime +
				", confirmResult='" + confirmResult + '\'' +
				", involvedName='" + involvedName + '\'' +
				", description='" + description + '\'' +
				", detailCount=" + detailCount +
				", unit='" + unit + '\'' +
				", saveMethod='" + saveMethod + '\'' +
				", lockerNo='" + lockerNo + '\'' +
				", isGet=" + isGet +
				", startTime=" + startTime +
				", endTime=" + endTime +
				", roomName='" + roomName + '\'' +
				", cameraNo='" + cameraNo + '\'' +
				", result='" + result + '\'' +
				'}';
	}
}