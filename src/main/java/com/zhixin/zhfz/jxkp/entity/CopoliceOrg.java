package com.zhixin.zhfz.jxkp.entity;

public class CopoliceOrg {
	private int id;
	private String organization;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getOrganization() {
		return organization;
	}
	public void setOrganization(String organization) {
		this.organization = organization;
	}
	public CopoliceOrg(int id, String organization) {
		super();
		this.id = id;
		this.organization = organization;
	}
	public CopoliceOrg() {}
}
