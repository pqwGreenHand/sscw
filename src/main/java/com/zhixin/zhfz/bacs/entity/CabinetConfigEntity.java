package com.zhixin.zhfz.bacs.entity;

import java.io.Serializable;

public class CabinetConfigEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String name;
	private int type;
	private int areaId;
	private String areaName;
	private int isEnable;
	private String ip;
	private String port;
	private String group;
	private String openIp;
	private int columnCount;
	private int rowCount;
	private int orderType;
	private String op_Pid;
	private Integer op_User_Id;

	public String getOp_Pid() {
		return op_Pid;
	}

	public void setOp_Pid(String op_Pid) {
		this.op_Pid = op_Pid;
	}

	public Integer getOp_User_Id() {
		return op_User_Id;
	}

	public void setOp_User_Id(Integer op_User_Id) {
		this.op_User_Id = op_User_Id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getAreaId() {
		return areaId;
	}

	public void setAreaId(int areaId) {
		this.areaId = areaId;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public int getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(int isEnabe) {
		this.isEnable = isEnabe;
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

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getOpenIp() {
		return openIp;
	}

	public void setOpenIp(String openIp) {
		this.openIp = openIp;
	}

	public int getColumnCount() {
		return columnCount;
	}

	public void setColumnCount(int columnCount) {
		this.columnCount = columnCount;
	}

	public int getRowCount() {
		return rowCount;
	}

	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

	public int getOrderType() {
		return orderType;
	}

	public void setOrderType(int orderType) {
		this.orderType = orderType;
	}

	@Override
	public String toString() {
		return "CabinetConfigEntity{" +
				"id=" + id +
				", name='" + name + '\'' +
				", type=" + type +
				", areaId=" + areaId +
				", areaName='" + areaName + '\'' +
				", isEnable=" + isEnable +
				", ip='" + ip + '\'' +
				", port='" + port + '\'' +
				", group='" + group + '\'' +
				", openIp='" + openIp + '\'' +
				", columnCount=" + columnCount +
				", rowCount=" + rowCount +
				", orderType=" + orderType +
				'}';
	}
}
