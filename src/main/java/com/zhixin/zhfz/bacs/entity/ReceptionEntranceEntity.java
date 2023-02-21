package com.zhixin.zhfz.bacs.entity;

import java.util.Date;

public class ReceptionEntranceEntity implements java.io.Serializable {

    private static final long serialVersionUID = 6647205086835282661L;

    private Long id;

    private String department;

    private String name;

    private String certificateNo;

    private String phone;

    private String reason;

    private String receiver;

    private String card;

    private Date inTime;

    private Date outTime;

    private int areaId;

    private String areaName;

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

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCertificateNo() {
        return certificateNo;
    }

    public void setCertificateNo(String certificateNo) {
        this.certificateNo = certificateNo;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
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

    public int getAreaId() {
        return areaId;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }

    @Override
    public String toString() {
        return "ReceptionEntranceEntity{" +
                "id=" + id +
                ", department='" + department + '\'' +
                ", name='" + name + '\'' +
                ", certificateNo='" + certificateNo + '\'' +
                ", phone='" + phone + '\'' +
                ", reason='" + reason + '\'' +
                ", receiver='" + receiver + '\'' +
                ", card='" + card + '\'' +
                ", inTime=" + inTime +
                ", outTime=" + outTime +
                ", areaId=" + areaId +
                ", areaName='" + areaName + '\'' +
                '}';
    }
}
