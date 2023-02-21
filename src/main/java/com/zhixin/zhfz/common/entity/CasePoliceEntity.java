package com.zhixin.zhfz.common.entity;

import java.io.Serializable;
import java.util.Date;

public class CasePoliceEntity implements Serializable {
    private static final long serialVersionUID = -7124585387248838515L;
    private Integer id;

    private Integer xbmjId;

    private Integer caseId;

    private Date updatedTime;

    private Date createdTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getXbmjId() {
        return xbmjId;
    }

    public void setXbmjId(Integer xbmjId) {
        this.xbmjId = xbmjId;
    }

    public Integer getCaseId() {
        return caseId;
    }

    public void setCaseId(Integer caseId) {
        this.caseId = caseId;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }
}