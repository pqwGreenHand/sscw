package com.zhixin.zhfz.bacs.form;

import java.io.Serializable;

/*
 * 定位设备表单
 * */
public class PositionDeviceForm implements Serializable {
	
	private static final long serialVersionUID = 5577128090368009959L;

	private Integer id;
	private Integer deviceNo;
	private String ip;
	private String description;
	private Integer areaId;
	private Integer roomId;
	private Integer deviceType;
	private String mode;
	private Integer groupNo;
	private Integer groupName;

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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
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

	public Integer getRoomId() {
		return roomId;
	}

	public void setRoomId(Integer roomId) {
		this.roomId = roomId;
	}

	@Override
	public String toString() {
		return "PositionDeviceForm [id=" + id + ", deviceNo=" + deviceNo + ", ip=" + ip + ", description=" + description
				+ ", areaId=" + areaId + ", roomId=" + roomId + ", deviceType=" + deviceType
				+ ", mode=" + mode + ", groupNo=" + groupNo + ", groupName=" + groupName + "]";
	}

}
