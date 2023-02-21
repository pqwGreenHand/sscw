package com.zhixin.zhfz.bacs.entity;

import java.io.Serializable;
import java.util.Date;

public class UrinalysisPhotoEntity  implements Serializable {
    private static final long serialVersionUID = 6261091137899953739L;
    private Long id;

    private Long urinalysisId;

    private String url;

    private Date createdTime;

    private String uuid;

    private int areaId;

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

    public Long getUrinalysisId() {
        return urinalysisId;
    }

    public void setUrinalysisId(Long urinalysisId) {
        this.urinalysisId = urinalysisId;
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

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getAreaId() {
        return areaId;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }

    @Override
    public String toString() {
        return "UrinalysisPhotoEntity{" +
                "id=" + id +
                ", urinalysisId=" + urinalysisId +
                ", url='" + url + '\'' +
                ", createdTime=" + createdTime +
                ", uuid='" + uuid + '\'' +
                ", areaId=" + areaId +
                '}';
    }
}