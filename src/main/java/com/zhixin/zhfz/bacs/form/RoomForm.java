package com.zhixin.zhfz.bacs.form;

import java.io.Serializable;
import java.util.Date;

public class RoomForm implements Serializable {

	/**
	* 
	*/
	private static final long serialVersionUID = 5609620842108547101L;

	private Integer id;

	private String name;

	private Integer roomTypeId;

	private Integer roomGroupId;

	private String description;

	private String axis;

	private String triggerNo;

	private Integer interrogateAreaId;

	private Date createdTime;

	private Date updatedTime;

	private String roomtype;
	private String ips;
	 private Integer isActive;
	 private Integer volume;
	    
	    private String ledAddress;
	    
	    public Integer getVolume() {
			return volume;
		}

		public void setVolume(Integer volume) {
			this.volume = volume;
		}

		public String getLedAddress() {
			return ledAddress;
		}

		public void setLedAddress(String ledAddress) {
			this.ledAddress = ledAddress;
		}
	    public Integer getIsActive() {
			return isActive;
		}

		public void setIsActive(Integer isActive) {
			this.isActive = isActive;
		}

	public String getAxis() {
		return axis;
	}

	public void setAxis(String axis) {
		this.axis = axis;
	}

	public String getTriggerNo() {
		return triggerNo;
	}

	public void setTriggerNo(String triggerNo) {
		this.triggerNo = triggerNo;
	}

	public String getIps() {
		return ips;
	}

	public void setIps(String ips) {
		this.ips = ips;
	}

	@Override
	public String toString() {
		return "RoomForm [id=" + id + ", name=" + name + ", roomTypeId=" + roomTypeId + ", roomGroupId=" + roomGroupId
				+ ", description=" + description + ", interrogateAreaId=" + interrogateAreaId + ", createdTime="
				+ createdTime + ", updatedTime=" + updatedTime + ", roomtype=" + roomtype + ", ips=" + ips
				+ ", isActive=" + isActive + "]";
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
		this.name = name == null ? null : name.trim();
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description == null ? null : description.trim();
	}

	public Integer getInterrogateAreaId() {
		return interrogateAreaId;
	}

	public void setInterrogateAreaId(Integer interrogateAreaId) {
		this.interrogateAreaId = interrogateAreaId;
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

	public String getRoomtype() {
		return roomtype;
	}

	public void setRoomtype(String roomtype) {
		this.roomtype = roomtype;
	}

	public Integer getRoomTypeId() {
		return roomTypeId;
	}

	public void setRoomTypeId(Integer roomTypeId) {
		this.roomTypeId = roomTypeId;
	}

	public Integer getRoomGroupId() {
		return roomGroupId;
	}

	public void setRoomGroupId(Integer roomGroupId) {
		this.roomGroupId = roomGroupId;
	}
}