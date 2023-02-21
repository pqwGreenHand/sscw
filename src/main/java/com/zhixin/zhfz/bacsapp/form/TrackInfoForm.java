package com.zhixin.zhfz.bacsapp.form;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;


public class TrackInfoForm implements java.io.Serializable {
	
	private static final long serialVersionUID = -4783107711681656029L;
	
	private Integer id;
	private Long serialId;
	private String name;
	private String type;
	private int cuffId;
	private String cuffNo;
	private String roomGroup;
	@JsonFormat( pattern="yyyy-MM-dd HH:mm:ss")
	private Date startTime;
	@JsonFormat( pattern="yyyy-MM-dd HH:mm:ss")
	private Date endTime;

	private int caseId;
	private String caseName;
	private String serialNo;
	private int cardType;//1表示办案中心民警、辅警；2表示其他人员，通过这个值来确定哪种表

	public String getRoomGroup() {
		return roomGroup;
	}

	public void setRoomGroup(String roomGroup) {
		this.roomGroup = roomGroup;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getCuffId() {
		return cuffId;
	}
	public void setCuffId(int cuffId) {
		this.cuffId = cuffId;
	}
	public String getCuffNo() {
		return cuffNo;
	}
	public void setCuffNo(String cuffNo) {
		this.cuffNo = cuffNo;
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

	public int getCaseId() {
		return caseId;
	}
	public void setCaseId(int caseId) {
		this.caseId = caseId;
	}
	public String getCaseName() {
		return caseName;
	}
	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}

	public int getCardType() {
		return cardType;
	}

	public void setCardType(int cardType) {
		this.cardType = cardType;
	}

	@Override
	public String toString() {
		return "TrackEntity [id=" + id + ", serialId=" + serialId + ", name=" + name + ", type=" + type + ", cuffId="
				+ cuffId + ", cuffNo=" + cuffNo + ", startTime=" + startTime + ", endTime=" + endTime + ", caseId=" + caseId + ", caseName=" + caseName + ", serialNo=" + serialNo + "]";
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
	
}