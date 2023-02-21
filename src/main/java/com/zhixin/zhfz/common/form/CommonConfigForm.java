package com.zhixin.zhfz.common.form;

public class CommonConfigForm implements java.io.Serializable {

	private static final long serialVersionUID = 739264707753056230L;

	private int id;
	private String type;
	private String desc;
	private Integer isEnable;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Integer getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(Integer isEnable) {
		this.isEnable = isEnable;
	}

	@Override
	public String toString() {
		return "CommonConfigForm [id=" + id + ", type=" + type + ", desc=" + desc + ", isEnable=" + isEnable + "]";
	}

}
