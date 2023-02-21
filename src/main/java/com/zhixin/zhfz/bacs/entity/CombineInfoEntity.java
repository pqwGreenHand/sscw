package com.zhixin.zhfz.bacs.entity;

import java.util.Date;

public class CombineInfoEntity {
    private Long id;

    private Long serialId;

    private Integer status;

    private Long personId;

    private Integer areaId;

    private Integer caseId;

    private String content;

    private Date sendTime;

    private Date updatedTime;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSerialId() {
        return serialId;
    }

    public void setSerialId(Long serialId) {
        this.serialId = serialId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public Integer getCaseId() {
        return caseId;
    }

    public void setCaseId(Integer caseId) {
        this.caseId = caseId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    @Override
    public String toString() {
        return "CombineInfoEntity{" +
                "id=" + id +
                ", serialId=" + serialId +
                ", status=" + status +
                ", personId=" + personId +
                ", areaId=" + areaId +
                ", caseId=" + caseId +
                ", content='" + content + '\'' +
                ", sendTime=" + sendTime +
                ", updatedTime=" + updatedTime +
                '}';
    }
}