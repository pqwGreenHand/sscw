package com.zhixin.zhfz.common.entity;

import java.io.Serializable;

public class CodeEntity implements Serializable{
	
	
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String codeKey;
	private String codeValue;
	private String keyDesc;
	private String type;

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

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCodeKey() {
		return codeKey;
	}
	public void setCodeKey(String codeKey) {
		this.codeKey = codeKey;
	}
	public String getCodeValue() {
		return codeValue;
	}
	public void setCodeValue(String codeValue) {
		this.codeValue = codeValue;
	}
	public String getKeyDesc() {
		return keyDesc;
	}
	public void setKeyDesc(String typeName) {
		this.keyDesc = typeName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "CodeEntity [id=" + id + ", codeKey=" + codeKey + ", codeValue=" + codeValue+ ", type=" + type
				+ ", keyDesc=" + keyDesc + "]";
	}


}
