package com.zhixin.zhfz.bacs.entity;

import java.io.Serializable;

public class SerialVideoMappingEntity implements Serializable {

	private static final long serialVersionUID = 8568704627170996139L;

	private int id;

	private int areaId;
	private int configId;
	private long serialId;
	private String keyName;
	private String keyValue;
	private String ip;
	private String port;
	private String user;
	private String password;
	private String url;
	private String downUrl;
	private String upUrl;
	private String ftpPath;
	private String webPath;
	private String localPath;
	private String serviceType;
	private String type;
	private String serviceUrl;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getConfigId() {
		return configId;
	}

	public void setConfigId(int configId) {
		this.configId = configId;
	}

	public long getSerialId() {
		return serialId;
	}

	public void setSerialId(long serialId) {
		this.serialId = serialId;
	}

	public String getKeyName() {
		return keyName;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

	public String getKeyValue() {
		return keyValue;
	}

	public void setKeyValue(String keyValue) {
		this.keyValue = keyValue;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDownUrl() {
		return downUrl;
	}

	public void setDownUrl(String downUrl) {
		this.downUrl = downUrl;
	}

	public String getUpUrl() {
		return upUrl;
	}

	public void setUpUrl(String upUrl) {
		this.upUrl = upUrl;
	}

	public String getFtpPath() {
		return ftpPath;
	}

	public void setFtpPath(String ftpPath) {
		this.ftpPath = ftpPath;
	}

	public String getWebPath() {
		return webPath;
	}

	public void setWebPath(String webPath) {
		this.webPath = webPath;
	}

	public String getLocalPath() {
		return localPath;
	}

	public void setLocalPath(String localPath) {
		this.localPath = localPath;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getAreaId() {
		return areaId;
	}

	public void setAreaId(int areaId) {
		this.areaId = areaId;
	}

	public String getServiceUrl() {
		return serviceUrl;
	}

	public void setServiceUrl(String serviceUrl) {
		this.serviceUrl = serviceUrl;
	}

	public String getListUrl() {
		return listUrl;
	}

	public void setListUrl(String listUrl) {
		this.listUrl = listUrl;
	}

	private String listUrl;

	public String getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}

	private String createdTime;

	@Override
	public String toString() {
		return "SerialVideoMappingEntity{" + "id=" + id + ", areaId=" + areaId + ", configId=" + configId
				+ ", serialId='" + serialId + '\'' + ", ftpPath='" + ftpPath + '\'' + ", keyName='" + keyName + '\''
				+ ", keyValue='" + keyValue + '\'' + ", ip='" + ip + '\'' + ", port='" + port + '\'' + ", user='" + user
				+ '\'' + ", password='" + password + '\'' + ", url='" + url + '\'' + ", downUrl='" + downUrl + '\''
				+ ", upUrl='" + upUrl + '\'' + ", webPath=" + webPath + ", localPath='" + localPath + '\''
				+ ", serviceType=" + serviceType + ", type=" + type + ", createdTime=" + createdTime + '}';
	}

}
