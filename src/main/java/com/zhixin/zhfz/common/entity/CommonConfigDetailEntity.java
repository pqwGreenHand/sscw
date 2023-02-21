package com.zhixin.zhfz.common.entity;

import java.io.Serializable;

public class CommonConfigDetailEntity implements Serializable {

	private static final long serialVersionUID = -6705072981563410387L;

	private int id;
	private int commonConfigId;
	private String paramKey;
	private String paramValue;
	private String desc;
	private String op_Pid;
	private Integer op_User_Id;

	public String getOp_Pid() {
		return op_Pid;
	}

	public void setOp_Pid(String op_Pid) {
		this.op_Pid = op_Pid;
	}

	public Integer getOp_User_Id() {
		return op_User_Id;
	}

	public void setOp_User_Id(Integer op_User_Id) {
		this.op_User_Id = op_User_Id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCommonConfigId() {
		return commonConfigId;
	}

	public void setCommonConfigId(int commonConfigId) {
		this.commonConfigId = commonConfigId;
	}

	public String getParamKey() {
		return paramKey;
	}

	public void setParamKey(String paramKey) {
		this.paramKey = paramKey;
	}

	public String getParamValue() {
		return paramValue;
	}

	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	@Override
	public String toString() {
		return "CommonConfigDetailEntity [id=" + id + ", commonConfigId=" + commonConfigId + ", paramKey=" + paramKey
				+ ", paramValue=" + paramValue + ", desc=" + desc + "]";
	}

}
