package com.zhixin.zhfz.bacs.form;

import java.io.Serializable;

public class CheckbodyLawDocForm implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8107992675497965228L;
	private int number;// 编号
	private String name;// 名称
	private int type;// 1:word 2:execl
	private long userId;
	private Integer checkbodyId;

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public Integer getCheckbodyId() {
		return checkbodyId;
	}

	public void setCheckbodyId(Integer checkbodyId) {
		this.checkbodyId = checkbodyId;
	}


}
