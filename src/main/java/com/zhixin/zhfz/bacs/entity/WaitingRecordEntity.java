package com.zhixin.zhfz.bacs.entity;

import java.io.Serializable;
import java.util.Date;

public class WaitingRecordEntity implements Serializable{
   
	private static final long serialVersionUID = 1L;

	//waiting_record
	private Integer recordId;

    private Long serialId;

    private String serialNo;

    private  String inPhotoName;

    private Long personId;

    private Integer inUserId;

    private Date inTime;

    private Integer outUserId;

    private Date outTime;

    private Integer status;

	private Integer caseId;

    private Integer areaId;

    private Integer sendUserId1;

    private String sendUserName1;

    private String sendUserName2;

    private String getUserName1;

    private String getUserName2;


	private String caseType;// 案件类型
	private String caseNature;// 案件性质

    private Integer sex;

    private String recordName;

    private String getResult;

	private Integer getUserId1;

    private Integer getUserId2;

    private String burcode;

    private Date createdTime;

    private Date updatedTime;

    //person

    private String personName;

    private String personcertNo;

    //user
    private int userId;

    private String userRealName;

    //interrogate_room
    private int roomId;

    private String roomName;
    
    private String opPid;
    private Integer opUserId;

    private String uuid;

    // 后期新增字段
	private String isJuvenile; // 是否未成年
	private String isGravida; // 是否孕妇
	private String isGravidaProve; // 是否有怀孕证明
	private String gravidaMonth; // 怀孕月数
	private String isDisease; // 是否有疾病
	private String isDiseaseProve; //是否有疾病证明
	private String officer; // 特殊身份

	private Integer roomStatus;

	private Integer roomTypeId;
	private String inquestId;
	private String recordUuid;

	public String getRecordUuid() {
		return recordUuid;
	}

	public void setRecordUuid(String recordUuid) {
		this.recordUuid = recordUuid;
	}

	public String getInquestId() {
		return inquestId;
	}

	public void setInquestId(String inquestId) {
		this.inquestId = inquestId;
	}

	public Integer getRoomStatus() {
		return roomStatus;
	}

	public void setRoomStatus(Integer roomStatus) {
		this.roomStatus = roomStatus;
	}

	public Integer getRoomTypeId() {
		return roomTypeId;
	}

	public void setRoomTypeId(Integer roomTypeId) {
		this.roomTypeId = roomTypeId;
	}

	public String getIsJuvenile() {
		return isJuvenile;
	}

	public void setIsJuvenile(String isJuvenile) {
		this.isJuvenile = isJuvenile;
	}

	public String getIsGravida() {
		return isGravida;
	}

	public void setIsGravida(String isGravida) {
		this.isGravida = isGravida;
	}

	public String getIsGravidaProve() {
		return isGravidaProve;
	}

	public void setIsGravidaProve(String isGravidaProve) {
		this.isGravidaProve = isGravidaProve;
	}

	public String getGravidaMonth() {
		return gravidaMonth;
	}

	public void setGravidaMonth(String gravidaMonth) {
		this.gravidaMonth = gravidaMonth;
	}

	public String getIsDisease() {
		return isDisease;
	}

	public void setIsDisease(String isDisease) {
		this.isDisease = isDisease;
	}

	public String getIsDiseaseProve() {
		return isDiseaseProve;
	}

	public void setIsDiseaseProve(String isDiseaseProve) {
		this.isDiseaseProve = isDiseaseProve;
	}

	public String getOfficer() {
		return officer;
	}

	public void setOfficer(String officer) {
		this.officer = officer;
	}

	public Integer getRecordId() {
		return recordId;
	}

	public void setRecordId(Integer recordId) {
		this.recordId = recordId;
	}

	public Long getSerialId() {
		return serialId;
	}

	public void setSerialId(Long serialId) {
		this.serialId = serialId;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public Long getPersonId() {
		return personId;
	}

	public void setPersonId(Long personId) {
		this.personId = personId;
	}

	public Integer getInUserId() {
		return inUserId;
	}

	public void setInUserId(Integer inUserId) {
		this.inUserId = inUserId;
	}

	public Date getInTime() {
		return inTime;
	}

	public void setInTime(Date inTime) {
		this.inTime = inTime;
	}

	public Integer getOutUserId() {
		return outUserId;
	}

	public void setOutUserId(Integer outUserId) {
		this.outUserId = outUserId;
	}

	public Date getOutTime() {
		return outTime;
	}

	public void setOutTime(Date outTime) {
		this.outTime = outTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getRoomId() {
		return roomId;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public void setRoomId(Integer roomId) {
		this.roomId = roomId;
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

	public Integer getSendUserId1() {
		return sendUserId1;
	}

	public void setSendUserId1(Integer sendUserId1) {
		this.sendUserId1 = sendUserId1;
	}

	public String getSendUserName1() {
		return sendUserName1;
	}

	public void setSendUserName1(String sendUserName1) {
		this.sendUserName1 = sendUserName1;
	}


	public String getSendUserName2() {
		return sendUserName2;
	}

	public void setSendUserName2(String sendUserName2) {
		this.sendUserName2 = sendUserName2;
	}

	public String getGetUserName1() {
		return getUserName1;
	}

	public void setGetUserName1(String getUserName1) {
		this.getUserName1 = getUserName1;
	}

	public String getGetUserName2() {
		return getUserName2;
	}

	public void setGetUserName2(String getUserName2) {
		this.getUserName2 = getUserName2;
	}

	public String getCaseType() {
		return caseType;
	}

	public void setCaseType(String caseType) {
		this.caseType = caseType;
	}

	public String getCaseNature() {
		return caseNature;
	}

	public void setCaseNature(String caseNature) {
		this.caseNature = caseNature;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getRecordName() {
		return recordName;
	}

	public void setRecordName(String recordName) {
		this.recordName = recordName;
	}

	public String getGetResult() {
		return getResult;
	}

	public void setGetResult(String getResult) {
		this.getResult = getResult;
	}

	public Integer getGetUserId1() {
		return getUserId1;
	}

	public void setGetUserId1(Integer getUserId1) {
		this.getUserId1 = getUserId1;
	}

	public Integer getGetUserId2() {
		return getUserId2;
	}

	public void setGetUserId2(Integer getUserId2) {
		this.getUserId2 = getUserId2;
	}

	public String getBurcode() {
		return burcode;
	}

	public void setBurcode(String burcode) {
		this.burcode = burcode;
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

	public String getPersoncertNo() {
		return personcertNo;
	}

	public void setPersoncertNo(String personcertNo) {
		this.personcertNo = personcertNo;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserRealName() {
		return userRealName;
	}

	public void setUserRealName(String userRealName) {
		this.userRealName = userRealName;
	}

	public String getInPhotoName() {
		return inPhotoName;
	}

	public void setInPhotoName(String inPhotoName) {
		this.inPhotoName = inPhotoName;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
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

    @Override
    public String toString() {
        return "WaitingRecordEntity{" +
                "recordId=" + recordId +
                ", serialId=" + serialId +
                ", serialNo='" + serialNo + '\'' +
                ", inPhotoName='" + inPhotoName + '\'' +
                ", personId=" + personId +
                ", inUserId=" + inUserId +
                ", inTime=" + inTime +
                ", outUserId=" + outUserId +
                ", outTime=" + outTime +
                ", status=" + status +
                ", caseId=" + caseId +
                ", areaId=" + areaId +
                ", sendUserId1=" + sendUserId1 +
                ", sendUserName1='" + sendUserName1 + '\'' +
                ", sendUserName2='" + sendUserName2 + '\'' +
                ", getUserName1='" + getUserName1 + '\'' +
                ", getUserName2='" + getUserName2 + '\'' +
                ", caseType='" + caseType + '\'' +
                ", caseNature='" + caseNature + '\'' +
                ", sex=" + sex +
                ", recordName='" + recordName + '\'' +
                ", getResult='" + getResult + '\'' +
                ", getUserId1=" + getUserId1 +
                ", getUserId2=" + getUserId2 +
                ", burcode='" + burcode + '\'' +
                ", createdTime=" + createdTime +
                ", updatedTime=" + updatedTime +
                ", personName='" + personName + '\'' +
                ", personcertNo='" + personcertNo + '\'' +
                ", userId=" + userId +
                ", userRealName='" + userRealName + '\'' +
                ", roomId=" + roomId +
                ", roomName='" + roomName + '\'' +
                ", opPid='" + opPid + '\'' +
                ", opUserId=" + opUserId +
                ", uuid='" + uuid + '\'' +
                ", isJuvenile='" + isJuvenile + '\'' +
                ", isGravida='" + isGravida + '\'' +
                ", isGravidaProve='" + isGravidaProve + '\'' +
                ", gravidaMonth='" + gravidaMonth + '\'' +
                ", isDisease='" + isDisease + '\'' +
                ", isDiseaseProve='" + isDiseaseProve + '\'' +
                ", officer='" + officer + '\'' +
                ", roomStatus=" + roomStatus +
                ", roomTypeId=" + roomTypeId +
                ", inquestId='" + inquestId + '\'' +
                ", recordUuid='" + recordUuid + '\'' +
                '}';
    }
}