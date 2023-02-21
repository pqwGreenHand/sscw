package com.zhixin.zhfz.bacs.entity;

import java.util.Date;

public class PositionDeviceEntity implements java.io.Serializable {

	private Integer id;
	private Integer deviceNo;
	private String ip;
	private String description;
	private Integer areaId;
	private Date createdTime;
	private Integer roomId;
	private Integer deviceType;
	private String mode;
	private Integer groupName;
	private Integer groupNo;
	private String roomName;
	private String areaName;
	private String op_Pid;
	private Integer op_User_Id;

	public String getOp_Pid() {
		return op_Pid;
	}

	public void setOp_Pid(String op_Pid) {
		this.op_Pid = op_Pid;
	}

	public Integer getOp_User_Id() {
		return op_User_Id;
	}

	public void setOp_User_Id(Integer op_User_Id) {
		this.op_User_Id = op_User_Id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getDeviceNo() {
		return deviceNo;
	}

	public void setDeviceNo(Integer deviceNo) {
		this.deviceNo = deviceNo;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Integer getRoomId() {
		return roomId;
	}

	public void setRoomId(Integer roomId) {
		this.roomId = roomId;
	}

	public Integer getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(Integer deviceType) {
		this.deviceType = deviceType;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public Integer getGroupName() {
		return groupName;
	}

	public void setGroupName(Integer groupName) {
		this.groupName = groupName;
	}

	public Integer getGroupNo() {
		return groupNo;
	}

	public void setGroupNo(Integer groupNo) {
		this.groupNo = groupNo;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	@Override
	public String toString() {
		return "PositionDeviceEntity{" +
				"id=" + id +
				", deviceNo=" + deviceNo +
				", ip='" + ip + '\'' +
				", description='" + description + '\'' +
				", areaId=" + areaId +
				", createdTime=" + createdTime +
				", roomId=" + roomId +
				", deviceType=" + deviceType +
				", mode='" + mode + '\'' +
				", groupName=" + groupName +
				", groupNo=" + groupNo +
				", roomName='" + roomName + '\'' +
				", areaName='" + areaName + '\'' +
				'}';
	}
}