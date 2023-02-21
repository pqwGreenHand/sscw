package com.zhixin.zhfz.bacs.entity;

import java.io.Serializable;


public class GeoPersonInfoCountEntity implements Serializable{
	
	
	private static final long serialVersionUID = 1L;

	private String info;
	private int num;
	
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	@Override
	public String toString() {
		return "GeoPersonInfoCountEntity [info=" + info + ", num=" + num + "]";
	}
	
	
	
}
