package com.zhixin.zhfz.bacs.entity;

import java.io.Serializable;
import java.util.Date;

public class OpenCabinetEntity implements Serializable{

	/**
	 * 开柜日志
	 */
	private static final long serialVersionUID = 3963254969256199379L;

	private Integer id;

    private Integer areaId;

    private String lockerId;

    private String opener;

    private Date createdTime;
    
    private String areaName;

    private String showNo;

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

    public String getShowNo() {
        return showNo;
    }

    public void setShowNo(String showNo) {
        this.showNo = showNo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public String getLockerId() {
        return lockerId;
    }

    public void setLockerId(String lockerId) {
        this.lockerId = lockerId == null ? null : lockerId.trim();
    }

    public String getOpener() {
        return opener;
    }

    public void setOpener(String opener) {
        this.opener = opener == null ? null : opener.trim();
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

    @Override
    public String toString() {
        return "OpenCabinetEntity{" +
                "id=" + id +
                ", areaId=" + areaId +
                ", lockerId='" + lockerId + '\'' +
                ", opener='" + opener + '\'' +
                ", createdTime=" + createdTime +
                ", areaName='" + areaName + '\'' +
                ", showNo='" + showNo + '\'' +
                '}';
    }
}