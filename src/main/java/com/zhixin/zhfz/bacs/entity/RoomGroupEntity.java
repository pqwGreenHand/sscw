package com.zhixin.zhfz.bacs.entity;

public class RoomGroupEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private int id;
	private String name;
	private int interrogateAreaId;
	private String interrogateAreaName;
	
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
	
	public int getInterrogateAreaId() {
		return interrogateAreaId;
	}
	public void setInterrogateAreaId(int interrogateAreaId) {
		this.interrogateAreaId = interrogateAreaId;
	}
	public String getInterrogateAreaName() {
		return interrogateAreaName;
	}
	public void setInterrogateAreaName(String interrogateAreaName) {
		this.interrogateAreaName = interrogateAreaName;
	}
	@Override
	public String toString() {
		return "RoomGroupEntity [id=" + id + ", name=" + name + ", interrogateAreaId=" + interrogateAreaId
				+ ", interrogateAreaName=" + interrogateAreaName + "]";
	}
	
}
