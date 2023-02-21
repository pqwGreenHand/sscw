package com.zhixin.zhfz.bacs.entity;

import java.util.Date;

public class PositionDataEntity implements java.io.Serializable {

	private Integer id;
	private Integer cuffId;
	private Integer cuffNo;
	private Integer deviceId;
	private String deviceNo;
	private Integer roomId;
	private String roomGroup;
	private int roomTypeId;

	public int getRoomTypeId() {
		return roomTypeId;
	}

	public void setRoomTypeId(int roomTypeId) {
		this.roomTypeId = roomTypeId;
	}

	private Date sendTime;

	public Integer getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Integer getCuffId() {
		return cuffId;
	}

	public void setCuffId(Integer cuffId) {
		this.cuffId = cuffId;
	}

	public Integer getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Integer deviceId) {
		this.deviceId = deviceId;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public Integer getCuffNo() {
		return cuffNo;
	}

	public void setCuffNo(Integer cuffNo) {
		this.cuffNo = cuffNo;
	}

	public String getDeviceNo() {
		return deviceNo;
	}

	public void setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
	}

	public Integer getRoomId() {
		return roomId;
	}

	public void setRoomId(Integer roomId) {
		this.roomId = roomId;
	}

	public String getRoomGroup() {
		return roomGroup;
	}

	public void setRoomGroup(String roomGroup) {
		this.roomGroup = roomGroup;
	}

	@Override
	public String toString() {
		return "PositionDataEntity{" +
				"id=" + id +
				", cuffId=" + cuffId +
				", cuffNo=" + cuffNo +
				", deviceId=" + deviceId +
				", deviceNo='" + deviceNo + '\'' +
				", roomId=" + roomId +
				", roomGroup='" + roomGroup + '\'' +
				", roomTypeId=" + roomTypeId +
				", sendTime=" + sendTime +
				'}';
	}
}