package com.zhixin.zhfz.bacs.entity;

import java.util.ArrayList;
import java.util.List;

public class OrderRequestCheckEntity implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String roomId;
	private String roomTypeId;
	private int amount;
	private String sex;
	private int roomVolume;
	
	public OrderRequestCheckEntity(){}

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public String getRoomTypeId() {
		return roomTypeId;
	}

	public void setRoomTypeId(String roomTypeId) {
		this.roomTypeId = roomTypeId;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public int getRoomVolume() {
		return roomVolume;
	}

	public void setRoomVolume(int roomVolume) {
		this.roomVolume = roomVolume;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
