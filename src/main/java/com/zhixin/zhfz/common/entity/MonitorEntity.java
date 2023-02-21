package com.zhixin.zhfz.common.entity;

import java.io.Serializable;
import java.util.Date;

public class MonitorEntity implements Serializable{

	
	private static final long serialVersionUID = 1L;
	
	private int id;
	
	private String hostname;
	
	private String ip;
	
	private String appname;
	
	private String monitorType;
	
	private String monitorWay;
	
	private int status;
	
	private Date updatedTime;

	private int enabled;

	public int getEnabled() {
		return enabled;
	}

	public void setEnabled(int enabled) {
		this.enabled = enabled;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getAppname() {
		return appname;
	}

	public void setAppname(String appname) {
		this.appname = appname;
	}

	public String getMonitorType() {
		return monitorType;
	}

	public void setMonitorType(String monitorType) {
		this.monitorType = monitorType;
	}

	public String getMonitorWay() {
		return monitorWay;
	}

	public void setMonitorWay(String monitorWay) {
		this.monitorWay = monitorWay;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}
	
	
	

}
