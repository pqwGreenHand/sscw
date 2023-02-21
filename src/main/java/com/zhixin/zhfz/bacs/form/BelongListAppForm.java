package com.zhixin.zhfz.bacs.form;


import java.io.Serializable;
import java.util.List;


public class BelongListAppForm implements Serializable{

	/**
	 * 随身物品
	 */
	private static final long serialVersionUID = 6068170122207873128L;
	private String name;
	private String detailCount;
	private String id;
	private String description;
	private String unit;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDetailCount() {
		return detailCount;
	}

	public void setDetailCount(String detailCount) {
		this.detailCount = detailCount;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	@Override
	public String toString() {
		return "BelongListAppForm{" +
				"name='" + name + '\'' +
				", detailCount='" + detailCount + '\'' +
				", id='" + id + '\'' +
				", description='" + description + '\'' +
				", unit='" + unit + '\'' +
				'}';
	}
}