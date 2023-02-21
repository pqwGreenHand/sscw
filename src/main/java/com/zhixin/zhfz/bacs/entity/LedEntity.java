package com.zhixin.zhfz.bacs.entity;

import java.io.Serializable;
import java.util.Date;


public class LedEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	private int id;
	private String ip;
	private int port;
	private int roomId;
	private String roomName;
	private int areaId;//办案场所ID
	private String areaName; 
	private int status; 
	private Date createdTime;
	private String opUserId;
	private String opPid;
	private String lednr;

	/** 需要显示的文字内容 **/
	private String content;
	/** yanse **/
	private String color;

	private int left;

	private int top;

	private int width;

	private int height;

	private int fontSize;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public int getLeft() {
		return left;
	}

	public void setLeft(int left) {
		this.left = left;
	}

	public int getTop() {
		return top;
	}

	public void setTop(int top) {
		this.top = top;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getFontSize() {
		return fontSize;
	}

	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public int getRoomId() {
		return roomId;
	}
	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}
	public String getRoomName() {
		return roomName;
	}
	public void setRoomName(String roomName) {
		this.roomName = roomName;
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
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Date getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	public String getOpUserId() {
		return opUserId;
	}
	public void setOpUserId(String opUserId) {
		this.opUserId = opUserId;
	}
	public String getOpPid() {
		return opPid;
	}
	public void setOpPid(String opPid) {
		this.opPid = opPid;
	}
	
	public String getLednr() {
		return lednr;
	}
	public void setLednr(String lednr) {
		this.lednr = lednr;
	}
	@Override
	public String toString() {
		return "LedEntity [id=" + id + ", ip=" + ip + ", port=" + port + ", roomId=" + roomId + ", roomName=" + roomName
				+ ", areaId=" + areaId + ", areaName=" + areaName + ", status=" + status + ", createdTime="
				+ createdTime + ", opUserId=" + opUserId + ", opPid=" + opPid + ", lednr=" + lednr + "]";
	}
	 
	
	
}
