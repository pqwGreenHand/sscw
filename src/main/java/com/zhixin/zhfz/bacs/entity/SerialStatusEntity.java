package com.zhixin.zhfz.bacs.entity;

import java.io.Serializable;
import java.util.Date;

public class SerialStatusEntity implements Serializable{
    /**
	 * 入区状态
	 */
	private static final long serialVersionUID = 8578674861753714751L;

	private Integer id;

    private String statusName;

    private Date statusTime;

    private Long serialId;

    private String opPid;

    private Integer opUserId;

	public String getOpPid() {
		return opPid;
	}

	public void setOpPid(String opPid) {
		this.opPid = opPid;
	}

	public Integer getOpUserId() {
		return opUserId;
	}

	public void setOpUserId(Integer opUserId) {
		this.opUserId = opUserId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public Date getStatusTime() {
		return statusTime;
	}

	public void setStatusTime(Date statusTime) {
		this.statusTime = statusTime;
	}

	public Long getInterrogateSerialId() {
		return serialId;
	}

	public void setInterrogateSerialId(Long interrogateSerialId) {
		this.serialId = interrogateSerialId;
	}


	@Override
	public String toString() {
		return "SerialStatusEntity{" +
				"id=" + id +
				", statusName='" + statusName + '\'' +
				", statusTime=" + statusTime +
				", serialId=" + serialId +
				", opPid='" + opPid + '\'' +
				", opUserId=" + opUserId +
				'}';
	}
}