package com.zhixin.zhfz.bacs.entity;

import java.util.Date;

public class BelongTempEntity implements java.io.Serializable {
    /**
     * 储物柜使用情况
     */
    private static final long serialVersionUID = 4614966861269493860L;

    private Integer id;
    private long personId;
    private long belongId;
    private Integer caseId;
    private Integer lockerId;
    private Integer areaId;
    private Integer status;
    private String xm;
    private String sfzh;
    private String yjdwId;
    private String jsdwId;
    private String yjjg;
    private String yjyj;
    private String jsdwName;
    private String yjdwName;
    private String wpUuid;
    private String ajbh;
    private String ajmc;
    private Date createdTime;
    private Date updatedTime;

    public Integer getLockerId() {
        return lockerId;
    }

    public void setLockerId(Integer lockerId) {
        this.lockerId = lockerId;
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public long getBelongId() {
        return belongId;
    }

    public void setBelongId(long belongId) {
        this.belongId = belongId;
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

    public String getJsdwName() {
        return jsdwName;
    }

    public void setJsdwName(String jsdwName) {
        this.jsdwName = jsdwName;
    }

    public String getYjdwName() {
        return yjdwName;
    }

    public void setYjdwName(String yjdwName) {
        this.yjdwName = yjdwName;
    }

    public Integer getCaseId() {
        return caseId;
    }

    public void setCaseId(Integer caseId) {
        this.caseId = caseId;
    }

    public long getPersonId() {
        return personId;
    }

    public void setPersonId(long personId) {
        this.personId = personId;
    }

    public String getWpUuid() {
        return wpUuid;
    }

    public void setWpUuid(String wpUuid) {
        this.wpUuid = wpUuid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getXm() {
        return xm;
    }

    public void setXm(String xm) {
        this.xm = xm;
    }

    public String getSfzh() {
        return sfzh;
    }

    public void setSfzh(String sfzh) {
        this.sfzh = sfzh;
    }

    public String getYjdwId() {
        return yjdwId;
    }

    public void setYjdwId(String yjdwId) {
        this.yjdwId = yjdwId;
    }

    public String getJsdwId() {
        return jsdwId;
    }

    public void setJsdwId(String jsdwId) {
        this.jsdwId = jsdwId;
    }

    public String getYjjg() {
        return yjjg;
    }

    public void setYjjg(String yjjg) {
        this.yjjg = yjjg;
    }

    public String getYjyj() {
        return yjyj;
    }

    public void setYjyj(String yjyj) {
        this.yjyj = yjyj;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }
}