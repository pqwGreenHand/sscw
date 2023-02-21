package com.zhixin.zhfz.bacs.form;

import java.io.Serializable;

public class BelongDocForm implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8168043699043743068L;
	private int number;// 编号
	private String name;// 名称
	private int type;// 1:word 2:execl
	private long userId;
	private long dataId;
	private String serialNo;
	private String startTime;
	private String endTime;
	private String serialId;

	public String getSerialId() {
		return serialId;
	}

	public void setSerialId(String serialId) {
		this.serialId = serialId;
	}

	@Override
	public String toString() {
		return "SecurityDocForm [number=" + number + ", name=" + name + ", type=" + type + ", userId=" + userId
				+ ", dataId=" + dataId + ", startTime=" + startTime+ ", endTime=" + endTime+ "]";
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public long getDataId() {
		return dataId;
	}
	public void setDataId(long dataId) {
		this.dataId = dataId;
	}
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
}
