package com.zhixin.zhfz.bacs.entity;

import java.util.Date;

/**
 * 民警存物
 */
public class PoliceBelongingsPhotoEntity {

	private long id;
	private long belongingsId;
	private String url;
	private int areaId;
	private Date createdTime;
	private Date updatedTime;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getBelongingsId() {
		return belongingsId;
	}

	public void setBelongingsId(long belongingsId) {
		this.belongingsId = belongingsId;
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
