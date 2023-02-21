package com.zhixin.zhfz.bacs.form;

import java.io.Serializable;

public class PersonalConfigDetailForm implements Serializable{

	private static final long serialVersionUID = 6107046760789030997L;

	private int id;
	private int personalConfigId;
	private String  paramKey;
	private String  paramValue;
	private String  desc;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPersonalConfigId() {
		return personalConfigId;
	}
	public void setPersonalConfigId(int personalConfigId) {
		this.personalConfigId = personalConfigId;
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
		return "PersonalConfigDetailForm [id=" + id + ", personalConfigId=" + personalConfigId + ", paramKey="
				+ paramKey + ", paramValue=" + paramValue + ", desc=" + desc + "]";
	}
	
}
