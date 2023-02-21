package com.zhixin.zhfz.bacs.entity;

import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

public class TdOperLogEntity implements Serializable {

    private Long id;
    private String type;
    private String content;
    private String params;
    private String cuffNo;
    private String uuid;
    private String name;
    private String ajmc;
    private String ajbh;
    private String personId;
    private String rybh;
    private Long serialId;
    private Integer sfcl;
    private Date createdTime;
    private Date inTime;
    private Date outTime;

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    @Override
    public String toString() {
        return "TdOperLogEntity{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", content='" + content + '\'' +
                ", params='" + params + '\'' +
                ", cuffNo='" + cuffNo + '\'' +
                ", uuid='" + uuid + '\'' +
                ", name='" + name + '\'' +
                ", ajmc='" + ajmc + '\'' +
                ", ajbh='" + ajbh + '\'' +
                ", rybh='" + rybh + '\'' +
                ", serialId=" + serialId +
                ", sfcl=" + sfcl +
                ", createdTime=" + createdTime +
                ", inTime=" + inTime +
                ", outTime=" + outTime +
                '}';
    }

    public Integer getSfcl() {
        return sfcl;
    }

    public void setSfcl(Integer sfcl) {
        this.sfcl = sfcl;
    }

    public String getRybh() {
        return rybh;
    }

    public void setRybh(String rybh) {
        this.rybh = rybh;
    }

    public String getCuffNo() {
        return cuffNo;
    }

    public void setCuffNo(String cuffNo) {
        this.cuffNo = cuffNo;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAjmc() {
        return ajmc;
    }

    public void setAjmc(String ajmc) {
        this.ajmc = ajmc;
    }

    public String getAjbh() {
        return ajbh;
    }

    public void setAjbh(String ajbh) {
        this.ajbh = ajbh;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getSerialId() {
        return serialId;
    }

    public void setSerialId(Long serialId) {
        this.serialId = serialId;
    }

}