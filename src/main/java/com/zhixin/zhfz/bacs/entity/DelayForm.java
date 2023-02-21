package com.zhixin.zhfz.bacs.entity;

import java.io.Serializable;

public class DelayForm implements Serializable{

	private static final long serialVersionUID = 1461140198356139999L;

	private Integer delayId;
	
	private Integer delayStatus;
	
	private Integer delayTime;
	
	private String planTimeDelay;




	public Integer getDelayId() {
		return delayId;
	}



	public void setDelayId(Integer delayId) {
		this.delayId = delayId;
	}



	public Integer getDelayStatus() {
		return delayStatus;
	}

	public void setDelayStatus(Integer delayStatus) {
		this.delayStatus = delayStatus;
	}

	public Integer getDelayTime() {
		return delayTime;
	}


	public void setDelayTime(Integer delayTime) {
		this.delayTime = delayTime;
	}



	public String getPlanTimeDelay() {
		return planTimeDelay;
	}



	public void setPlanTimeDelay(String planTimeDelay) {
		this.planTimeDelay = planTimeDelay;
	}



	@Override
	public String toString() {
		return "DelayForm [delayId=" + delayId + ", delayStatus=" + delayStatus + ", delayTime=" + delayTime
				+ ", planTimeDelay=" + planTimeDelay + "]";
	}



	
}
