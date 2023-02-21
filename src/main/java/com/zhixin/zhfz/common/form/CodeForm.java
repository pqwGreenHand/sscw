package com.zhixin.zhfz.common.form;

import java.io.Serializable;

public class CodeForm implements Serializable{
	
	
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String keyDesc;
	private String codeKey;
	private String codeValue;
	private String type;

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getKeyDesc() {
		return keyDesc;
	}
	public void setKeyDesc(String typeName) {
		this.keyDesc = typeName;
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
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "CodeForm{" +
				"id=" + id +
				", typeName='" + keyDesc + '\'' +
				", codeKey='" + codeKey + '\'' +
				", codeValue='" + codeValue + '\'' +
				", type='" + type + '\'' +
				'}';
	}
}
