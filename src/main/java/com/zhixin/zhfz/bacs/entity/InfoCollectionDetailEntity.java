package com.zhixin.zhfz.bacs.entity;

import java.io.Serializable;
import java.util.Date;

public class InfoCollectionDetailEntity implements Serializable{

    private static final long serialVersionUID = 1L;

    private Integer id;

    private Long infoCollectionId;

    private String collKey;

    private String collValue;

    private Integer collectUserId;

    private Date collectTime;

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

    public Long getInfoCollectionId() {
        return infoCollectionId;
    }

    public void setInfoCollectionId(Long infoCollectionId) {
        this.infoCollectionId = infoCollectionId;
    }

    public String getCollKey() {
        return collKey;
    }

    public void setCollKey(String collKey) {
        this.collKey = collKey;
    }

    public String getCollValue() {
        return collValue;
    }

    public void setCollValue(String collValue) {
        this.collValue = collValue;
    }

    public Integer getCollectUserId() {
        return collectUserId;
    }

    public void setCollectUserId(Integer collectUserId) {
        this.collectUserId = collectUserId;
    }

    public Date getCollectTime() {
        return collectTime;
    }

    public void setCollectTime(Date collectTime) {
        this.collectTime = collectTime;
    }

    @Override
    public String toString() {
        return "InfoCollectionDetailEntity{" +
                "id=" + id +
                ", infoCollectionId=" + infoCollectionId +
                ", collKey='" + collKey + '\'' +
                ", collValue='" + collValue + '\'' +
                ", collectUserId=" + collectUserId +
                ", collectTime=" + collectTime +
                '}';
    }
}
