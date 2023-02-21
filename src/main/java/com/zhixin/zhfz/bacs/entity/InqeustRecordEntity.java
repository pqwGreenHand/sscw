package com.zhixin.zhfz.bacs.entity;

import java.io.Serializable;
import java.util.Date;

public class InqeustRecordEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Long serialId;
	private Long personId;
	private int askUserId;//询问民警ID
	private int recordUserId;//记录民警ID
	private Date startTime;
	private Date endTime;
	private String UUID;
	private int recordRoomId;
	private Date createdTime;
	private Date updatedTime;
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
	public Long getPersonId() {
		return personId;
	}
	public void setPersonId(Long personId) {
		this.personId = personId;
	}
	public int getAskUserId() {
		return askUserId;
	}
	public void setAskUserId(int askUserId) {
		this.askUserId = askUserId;
	}
	public int getRecordUserId() {
		return recordUserId;
	}
	public void setRecordUserId(int recordUserId) {
		this.recordUserId = recordUserId;
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
	public String getUUID() {
		return UUID;
	}
	public void setUUID(String uUID) {
		UUID = uUID;
	}
	public int getRecordRoomId() {
		return recordRoomId;
	}
	public void setRecordRoomId(int recordRoomId) {
		this.recordRoomId = recordRoomId;
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
	
	@Override
	public String toString() {
		
		return "InqeustRecordEntity[id=" + id + ", serialId=" + serialId
				+ ", personId=" + personId + ", askUserId=" + askUserId + ", recordUserId=" + recordUserId + ", startTime=" + startTime
				+ ", endTime=" + endTime + ", uuid=" + UUID + ", recordRoomId=" + recordRoomId + ", createdTime=" + createdTime+ ", updated_time=" + updatedTime+"]";
	}
}
