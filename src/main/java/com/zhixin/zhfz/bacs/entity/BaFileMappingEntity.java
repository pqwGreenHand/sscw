package com.zhixin.zhfz.bacs.entity;

import java.util.Date;

public class BaFileMappingEntity implements java.io.Serializable {

    private static final long serialVersionUID = 4614966861269493860L;

    private int id;

    private String sysType;

    private int caseId;

    private int subsysId;

    private String uuid;

    private String type;

    private String name;

    private String filePath;

    private String fileSuffix;

    private String url;

    private Date createdTime;

    private String oppId;

    private int opuserId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSysType() {
        return sysType;
    }

    public void setSysType(String sysType) {
        this.sysType = sysType;
    }

    public int getCaseId() {
        return caseId;
    }

    public void setCaseId(int caseId) {
        this.caseId = caseId;
    }

    public int getSubsysId() {
        return subsysId;
    }

    public void setSubsysId(int subsysId) {
        this.subsysId = subsysId;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileSuffix() {
        return fileSuffix;
    }

    public void setFileSuffix(String fileSuffix) {
        this.fileSuffix = fileSuffix;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getOppId() {
        return oppId;
    }

    public void setOppId(String oppId) {
        this.oppId = oppId;
    }

    public int getOpuserId() {
        return opuserId;
    }

    public void setOpuserId(int opuserId) {
        this.opuserId = opuserId;
    }
}
