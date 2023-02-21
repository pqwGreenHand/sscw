package com.zhixin.zhfz.bacs.entity;

import java.io.Serializable;

public class WaitingPersonCountEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private int areaId;
	
	private String areaName;

	private int roomId;
	
	private String roomName;
	
	private int male;
	
	private int female;
	
	private int sum;

    private int other;

    private int volume;

	public int getVolume() {
		return volume;
	}

	public void setVolume(int volume) {
		this.volume = volume;
	}

	public int getOther() {
		return other;
	}

	public void setOther(int other) {
		this.other = other;
	}

	public int getRoomId() {
		return roomId;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}

	public int getMale() {
		return male;
	}

	public void setMale(int male) {
		this.male = male;
	}

	public int getFemale() {
		return female;
	}

	public void setFemale(int female) {
		this.female = female;
	}

	public int getSum() {
		return sum;
	}

	public void setSum(int sum) {
		this.sum = sum;
	}

	public int getAreaId() {
		return areaId;
	}

	public void setAreaId(int areaId) {
		this.areaId = areaId;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	@Override
	public String toString() {
		return "WaitingPersonCountEntity{" +
				"areaId=" + areaId +
				", areaName='" + areaName + '\'' +
				", roomId=" + roomId +
				", roomName='" + roomName + '\'' +
				", male=" + male +
				", female=" + female +
				", sum=" + sum +
				", other=" + other +
				", volume=" + volume +
				'}';
	}
}
