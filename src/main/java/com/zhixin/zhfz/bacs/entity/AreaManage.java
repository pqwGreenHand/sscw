package com.zhixin.zhfz.bacs.entity;

import java.io.Serializable;

public class AreaManage implements  Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3874021801752820855L;

	private String areaName;
	
	private Integer areaId;
	
	private int orderCount = 0;
	
	private int inCount = 0;
	
	private int outCount =0;

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public int getOrderCount() {
		return orderCount;
	}

	public void setOrderCount(int orderCount) {
		this.orderCount = orderCount;
	}

	public int getInCount() {
		return inCount;
	}

	public void setInCount(int inCount) {
		this.inCount = inCount;
	}

	public int getOutCount() {
		return outCount;
	}

	public void setOutCount(int outCount) {
		this.outCount = outCount;
	}

	@Override
	public String toString() {
		return "AreaManage [areaName=" + areaName + ", orderCount=" + orderCount + ", inCount=" + inCount
				+ ", outCount=" + outCount + "]";
	}

	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}
}
