package com.zhixin.zhfz.bacs.entity;

import java.io.Serializable;

public class LockerBoxEntity implements Serializable{

	private static final long serialVersionUID = 6107046760789030997L;

	private int id;
	private String name;
	private String key;
	private String value;
	private String show;
	private Integer isGet;
	private String personName;
	
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Integer getIsGet() {
		return isGet;
	}
	public void setIsGet(Integer isGet) {
		this.isGet = isGet;
	}
	public String getPersonName() {
		return personName;
	}
	public void setPersonName(String personName) {
		this.personName = personName;
	}
	
	
	public String getShow() {
		return show;
	}
	public void setShow(String show) {
		this.show = show;
	}
	@Override
	public String toString() {
		return "LockerBoxEntity [id=" + id + ", name=" + name + ", key=" + key + ", value=" + value + ", show=" + show
				+ ", isGet=" + isGet + ", personName=" + personName + "]";
	}
	
	
	
}
