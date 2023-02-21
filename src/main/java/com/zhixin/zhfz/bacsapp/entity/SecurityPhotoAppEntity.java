package com.zhixin.zhfz.bacsapp.entity;

import java.util.Date;

public class SecurityPhotoAppEntity {

	private String id;
	private String securityUUID;
	private int type;
	private String picName;
	private Date createdTime;
    private String count;
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSecurityUUID() {
		return securityUUID;
	}

	public void setSecurityUUID(String securityUUID) {
		this.securityUUID = securityUUID;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getPicName() {
		return picName;
	}

	public void setPicName(String picName) {
		this.picName = picName;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "SecurityPhotoAppEntity{" +
				"id='" + id + '\'' +
				", securityUUID='" + securityUUID + '\'' +
				", type=" + type +
				", picName='" + picName + '\'' +
				", createdTime=" + createdTime +
				", count='" + count + '\'' +
				'}';
	}
}
