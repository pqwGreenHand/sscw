package com.zhixin.zhfz.bacs.entity;

import java.io.Serializable;

public class ExportCaseDataEntity implements Serializable {

	/**
	 * 案件数据统计
	 */
	private static final long serialVersionUID = -1;
	// 统计日期
	private String countDate;
	// 当天刑事总人数
	private int dtxsCount = 0;
	// 当天行政总人数
	private int dtxzCount = 0;
	// 当天总人数
	private int dtCount = 0;

	public String getCountDate() {
		return countDate;
	}

	public void setCountDate(String countDate) {
		this.countDate = countDate;
	}

	public int getDtxsCount() {
		return dtxsCount;
	}

	public void setDtxsCount(int dtxsCount) {
		this.dtxsCount = dtxsCount;
	}

	public int getDtxzCount() {
		return dtxzCount;
	}

	public void setDtxzCount(int dtxzCount) {
		this.dtxzCount = dtxzCount;
	}

	public int getDtCount() {
		return dtCount;
	}

	public void setDtCount(int dtCount) {
		this.dtCount = dtCount;
	}

	@Override
	public String toString() {
		return "ExportCaseDataEntity [countDate=" + countDate + ", dtxsCount=" + dtxsCount + ", dtxzCount=" + dtxzCount
				+ ", dtCount=" + dtCount + "]";
	}

}
