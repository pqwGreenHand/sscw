package com.zhixin.zhfz.bacs.form;

public class RoomGroupForm implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private int id;
	private String name;
	private int interrogateAreaId;
	
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
	@Override
	public String toString() {
		return "RoomGroupForm [id=" + id + ", name=" + name + ", interrogateAreaId=" + interrogateAreaId + "]";
	}
	
}
