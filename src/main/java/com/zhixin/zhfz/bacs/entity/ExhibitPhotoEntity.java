package com.zhixin.zhfz.bacs.entity;

import java.util.Date;

public class ExhibitPhotoEntity {

	private long id;
	private long exhibitId;
	private String url;
	private String uuid;
	private int areaId;
	private Date createdTime;
	private Date updatedTime;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getExhibitId() {
		return exhibitId;
	}

	public void setExhibitId(long exhibitId) {
		this.exhibitId = exhibitId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getAreaId() {
		return areaId;
	}

	public void setAreaId(int areaId) {
		this.areaId = areaId;
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
}
