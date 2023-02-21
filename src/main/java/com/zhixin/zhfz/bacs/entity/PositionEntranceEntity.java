package com.zhixin.zhfz.bacs.entity;

import java.util.Date;

public class PositionEntranceEntity implements java.io.Serializable {

	private Integer id;
	private Integer cuffId;
	private Integer cuffNo;
	private Integer deviceId;
	private String deviceNo;
	private String actionType;
	private Date actionTime;

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

	public String getActionType() {
		return actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	public Date getActionTime() {
		return actionTime;
	}

	public void setActionTime(Date actionTime) {
		this.actionTime = actionTime;
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
}