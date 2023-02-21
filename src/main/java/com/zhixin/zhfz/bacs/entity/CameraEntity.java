package com.zhixin.zhfz.bacs.entity;

import java.io.Serializable;
import java.util.Date;

public class CameraEntity implements Serializable{
	
	
	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	private String channel;
	private String ip;
	private String account;
	private String password;
	private int port;
	private String screen;
	private int type;
	private int download;//是否需要下载
	private String description;
	private String nvrName;
	private int nvrId;
	private String vender;
	private String areaName;
	private int areaId;
	private String roomName;
	private int roomId;
	private Date createdTime;
	private Date updatedTime;
	private String cameraNo;


	//涉案财物摄像头字段
	private int warehouseId;
	private int locationId;
	private String warehouseName;
	private String locationName;
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

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
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

	public String getScreen() {
		return screen;
	}

	public void setScreen(String screen) {
		this.screen = screen;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getDownload() {
		return download;
	}

	public void setDownload(int download) {
		this.download = download;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getNvrName() {
		return nvrName;
	}

	public void setNvrName(String nvrName) {
		this.nvrName = nvrName;
	}

	public int getNvrId() {
		return nvrId;
	}

	public void setNvrId(int nvrId) {
		this.nvrId = nvrId;
	}

	public String getVender() {
		return vender;
	}

	public void setVender(String vender) {
		this.vender = vender;
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

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public int getRoomId() {
		return roomId;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
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

	public String getCameraNo() {
		return cameraNo;
	}

	public void setCameraNo(String cameraNo) {
		this.cameraNo = cameraNo;
	}

	public int getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(int warehouseId) {
		this.warehouseId = warehouseId;
	}

	public int getLocationId() {
		return locationId;
	}

	public void setLocationId(int locationId) {
		this.locationId = locationId;
	}

	public String getWarehouseName() {
		return warehouseName;
	}

	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	@Override
	public String toString() {
		return "CameraEntity{" +
				"id=" + id +
				", name='" + name + '\'' +
				", channel='" + channel + '\'' +
				", ip='" + ip + '\'' +
				", account='" + account + '\'' +
				", password='" + password + '\'' +
				", port=" + port +
				", screen='" + screen + '\'' +
				", type=" + type +
				", download=" + download +
				", description='" + description + '\'' +
				", nvrName='" + nvrName + '\'' +
				", nvrId=" + nvrId +
				", vender='" + vender + '\'' +
				", areaName='" + areaName + '\'' +
				", areaId=" + areaId +
				", roomName='" + roomName + '\'' +
				", roomId=" + roomId +
				", createdTime=" + createdTime +
				", updatedTime=" + updatedTime +
				", cameraNo='" + cameraNo + '\'' +
				", warehouseId=" + warehouseId +
				", locationId=" + locationId +
				", warehouseName='" + warehouseName + '\'' +
				", locationName='" + locationName + '\'' +
				'}';
	}
}
