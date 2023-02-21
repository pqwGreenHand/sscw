package com.zhixin.zhfz.common.form;

import java.io.Serializable;

public class CommonConfigDetailForm implements Serializable {

	private static final long serialVersionUID = 662097663877497964L;

	private int id;
	private int commonConfigId;
	private String paramKey;
	private String paramValue;
	private String desc;

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
		return "CommonConfigDetailForm [id=" + id + ", commonConfigId=" + commonConfigId + ", paramKey=" + paramKey
				+ ", paramValue=" + paramValue + ", desc=" + desc + "]";
	}

}
