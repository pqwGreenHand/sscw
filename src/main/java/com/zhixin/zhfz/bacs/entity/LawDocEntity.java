package com.zhixin.zhfz.bacs.entity;

import java.io.Serializable;

public class LawDocEntity implements Serializable{
	
	private static final long serialVersionUID = 3372716634077863989L;

	private int id;
	
	private String name;
	private int status;
	@Override
	public String toString() {
		return "LawDocEntity [id=" + id + ", name=" + name + ", status=" + status + "]";
	}
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
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
}
