package com.zhixin.zhfz.sacw.entity;

import java.io.Serializable;
import java.util.Date;

public class InOutRecordEntity implements Serializable {

    /**
     * 人员进出功能室时间记录
     */
    private static final long serialVersionUID = -1987953530836542274L;

    private Long id;

    private String personNo;

    private String personId;

    private String areaName;

    private String roomName;

    private Date inTime;

    private Date outTime;

    private Date createdTime;

    private String outReason;


    public String getPersonNo() {
        return personNo;
    }

    public void setPersonNo(String personNo) {
        this.personNo = personNo == null ? null : personNo.trim();
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId == null ? null : personId.trim();
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName == null ? null : areaName.trim();
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName == null ? null : roomName.trim();
    }

    public Date getInTime() {
        return inTime;
    }

    public void setInTime(Date inTime) {
        this.inTime = inTime;
    }

    public Date getOutTime() {
        return outTime;
    }

    public void setOutTime(Date outTime) {
        this.outTime = outTime;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOutReason() {
        return outReason;
    }

    public void setOutReason(String outReason) {
        this.outReason = outReason;
    }

    @Override
    public String toString() {
        return "InOutRecordEntity [id=" + id + ", personNo=" + personNo + ", personId=" + personId + ", areaName="
                + areaName + ", roomName=" + roomName + ", inTime=" + inTime + ", outTime=" + outTime + ", createdTime="
                + createdTime + ", outReason=" + outReason + "]";
    }
}