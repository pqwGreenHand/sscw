package com.zhixin.zhfz.bacs.entity;

import java.io.Serializable;
import java.util.Date;

public class TempOutEntity implements Serializable {
    /**
	 * 临时出区
	 */
	private static final long serialVersionUID = 4914813963038328730L;

	private Integer id;

    private Long serialId;

    private Date outTime;

    private Date backTime;
    
    private String outReason;

	@Override
	public String toString() {
		return "TempOutEntity [id=" + id + ", serialId=" + serialId + ", outTime=" + outTime
				+ ", backTime=" + backTime + ", outReason=" + outReason + "]";
	}

	public String getOutReason() {
		return outReason;
	}

	public void setOutReason(String outReason) {
		this.outReason = outReason;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Long getSerialId() {
		return serialId;
	}

	public void setSerialId(Long serialId) {
		this.serialId = serialId;
	}

	public Date getOutTime() {
		return outTime;
	}

	public void setOutTime(Date outTime) {
		this.outTime = outTime;
	}

	public Date getBackTime() {
		return backTime;
	}

	public void setBackTime(Date backTime) {
		this.backTime = backTime;
	}


}