package com.zhixin.zhfz.bacs.entity;

import java.util.Date;

public class PositionAlarmEntity implements java.io.Serializable {

	private Integer id;
	private Integer cuffId;
	private Integer cuffNo;
	private Integer deviceId;
	private String deviceNo;
	private Integer alarmType;
	private Date alarmTime;

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

	public Integer getAlarmType() {
		return alarmType;
	}

	public void setAlarmType(Integer alarmType) {
		this.alarmType = alarmType;
	}

	public Date getAlarmTime() {
		return alarmTime;
	}

	public void setAlarmTime(Date alarmTime) {
		this.alarmTime = alarmTime;
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