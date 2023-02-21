package com.zhixin.zhfz.bacs.form;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class NvrForm implements Serializable{
	
	
	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	private String ip;
	private String ipBack;
	private String account;
	private String password;
	private int port;
	private String areaName;
	private int areaId;
	private String vender;
	private int type;
	private Date createdTime;
	private Date updatedTime;
	private int nvrCount;
	private NvrListForm listForm;
	//涉案财物NVR字段
	private String warehouseName;
	private int warehouseId;


	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getIpBack() {
		return ipBack;
	}

	public void setIpBack(String ipBack) {
		this.ipBack = ipBack;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public int getAreaId() {
		return areaId;
	}

	public void setAreaId(int areaId) {
		this.areaId = areaId;
	}

	public String getVender() {
		return vender;
	}

	public void setVender(String vender) {
		this.vender = vender;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Date getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}

	public int getNvrCount() {
		return nvrCount;
	}

	public void setNvrCount(int nvrCount) {
		this.nvrCount = nvrCount;
	}

	public NvrListForm getListForm() {
		return listForm;
	}

	public void setListForm(NvrListForm listForm) {
		this.listForm = listForm;
	}

	public String getWarehouseName() {
		return warehouseName;
	}

	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}

	public int getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(int warehouseId) {
		this.warehouseId = warehouseId;
	}

	@Override
	public String toString() {
		return "NvrForm{" +
				"id=" + id +
				", name='" + name + '\'' +
				", ip='" + ip + '\'' +
				", ipBack='" + ipBack + '\'' +
				", account='" + account + '\'' +
				", password='" + password + '\'' +
				", port=" + port +
				", areaName='" + areaName + '\'' +
				", areaId=" + areaId +
				", vender='" + vender + '\'' +
				", type=" + type +
				", createdTime=" + createdTime +
				", updatedTime=" + updatedTime +
				", nvrCount=" + nvrCount +
				", listForm=" + listForm +
				", warehouseName='" + warehouseName + '\'' +
				", warehouseId=" + warehouseId +
				'}';
	}
}
