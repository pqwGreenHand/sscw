package com.zhixin.zhfz.bacs.form;

import java.io.Serializable;
import java.util.Date;

public class CabinetDetailConfigForm implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private Integer configId;
	private String openKey;
	private String showNo;
	private Integer row;
	private Integer column;
	private Integer type;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getConfigId() {
		return configId;
	}
	public void setConfigId(Integer configId) {
		this.configId = configId;
	}
	public String getOpenKey() {
		return openKey;
	}
	public void setOpenKey(String openKey) {
		this.openKey = openKey;
	}
	public String getShowNo() {
		return showNo;
	}
	public void setShowNo(String showNo) {
		this.showNo = showNo;
	}
	public Integer getRow() {
		return row;
	}
	public void setRow(Integer row) {
		this.row = row;
	}
	public Integer getColumn() {
		return column;
	}
	public void setColumn(Integer column) {
		this.column = column;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "CabinetDetailConfigForm [id=" + id + ", configId=" + configId + ", openKey=" + openKey + ", showNo="
				+ showNo + ", row=" + row + ", column=" + column + ", type=" + type + "]";
	}

	


}