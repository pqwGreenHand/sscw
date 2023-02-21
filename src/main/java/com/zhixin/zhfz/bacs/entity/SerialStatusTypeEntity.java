package com.zhixin.zhfz.bacs.entity;

import java.io.Serializable;

public class SerialStatusTypeEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private long serialId;
	
	private boolean isSecurity; //安检
	
	private boolean isWaiting; //候问
	
	private boolean isRecord;  //审讯
	
	private boolean isArraign;  //提讯

	
	

	public long getSerialId() {
		return serialId;
	}

	public void setSerialId(long serialId) {
		this.serialId = serialId;
	}

	public boolean isSecurity() {
		return isSecurity;
	}

	public void setSecurity(boolean isSecurity) {
		this.isSecurity = isSecurity;
	}

	public boolean isWaiting() {
		return isWaiting;
	}

	public void setWaiting(boolean isWaiting) {
		this.isWaiting = isWaiting;
	}

	public boolean isRecord() {
		return isRecord;
	}

	public void setRecord(boolean isRecord) {
		this.isRecord = isRecord;
	}

	public boolean isArraign() {
		return isArraign;
	}

	public void setArraign(boolean isArraign) {
		this.isArraign = isArraign;
	}

}
