package com.zhixin.zhfz.common.entity;

import java.io.Serializable;
import java.util.Date;


public class InformEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private long id;
	
	private int senderId;
	
	private int receiverId;
	
	private String title;
	
	private String content;
	
	private int type;
	
	private Date sendTime;
	
	private int isRead;
	
	private Date readTime;
	
	private String senderName;
	private String ajbh;
	private String ajmc;

	private String receiverName;

	private Integer opUserId;

	public Integer getOpUserId() {
		return opUserId;
	}

	public void setOpUserId(Integer opUserId) {
		this.opUserId = opUserId;
	}
	private String opPid;

	public String getOpPid() {
		return opPid;
	}

	public void setOpPid(String opPid) {
		this.opPid = opPid;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getSenderId() {
		return senderId;
	}

	public void setSenderId(int senderId) {
		this.senderId = senderId;
	}

	public int getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(int receiverId) {
		this.receiverId = receiverId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public int getIsRead() {
		return isRead;
	}

	public void setIsRead(int isRead) {
		this.isRead = isRead;
	}

	public Date getReadTime() {
		return readTime;
	}

	public void setReadTime(Date readTime) {
		this.readTime = readTime;
	}

	public String getAjbh() {
		return ajbh;
	}

	public void setAjbh(String ajbh) {
		this.ajbh = ajbh;
	}

	public String getAjmc() {
		return ajmc;
	}

	public void setAjmc(String ajmc) {
		this.ajmc = ajmc;
	}


	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}


	@Override
	public String toString() {
		return "InformEntity{" +
				"id=" + id +
				", senderId=" + senderId +
				", receiverId=" + receiverId +
				", title='" + title + '\'' +
				", content='" + content + '\'' +
				", type=" + type +
				", sendTime=" + sendTime +
				", isRead=" + isRead +
				", readTime=" + readTime +
				", senderName='" + senderName + '\'' +
				", ajbh='" + ajbh + '\'' +
				", ajmc='" + ajmc + '\'' +
				", receiverName='" + receiverName + '\'' +
				", opPid='" + opPid + '\'' +
				'}';
	}
}
