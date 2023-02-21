package com.zhixin.zhfz.bacs.form;

import java.io.Serializable;

public class PersonalConfigForm implements Serializable{

	private static final long serialVersionUID = 6107046760789030997L;

	private int id;
	private String type;
	private int areaId;
	private String areaName;
	private String desc;

	public int getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(int isEnable) {
		this.isEnable = isEnable;
	}

	private int isEnable;
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String AreaName) {
		this.areaName = AreaName;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type= type;
	}
	public int getAreaId() {
		return areaId;
	}
	public void setAreaId(int interrogateAreaId) {
		this.areaId = interrogateAreaId;
	}
	
	
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	@Override
	public String toString() {
		return "PersonalConfigForm [id=" + id + ", type=" + type + ", AreaId=" + areaId
				+ ", AreaName=" + areaName + ", desc=" + desc+ ", isEnable=" + isEnable + "]";
	}
	
}
