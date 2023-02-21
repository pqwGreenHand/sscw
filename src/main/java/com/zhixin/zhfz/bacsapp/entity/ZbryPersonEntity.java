package com.zhixin.zhfz.bacsapp.entity;

import java.util.Date;

public class ZbryPersonEntity {

    private Long SerialId;

    private Long personId;

    private Long caseId;

    private Long  areaId;

    private String uuid;

    private String name;

    private Integer status;

    private Date inTime;

    public Long getSerialId() {
        return SerialId;
    }

    public void setSerialId(Long serialId) {
        SerialId = serialId;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public Long getCaseId() {
        return caseId;
    }

    public void setCaseId(Long caseId) {
        this.caseId = caseId;
    }

    public Long getAreaId() {
        return areaId;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getInTime() {
        return inTime;
    }

    public void setInTime(Date inTime) {
        this.inTime = inTime;
    }
}
