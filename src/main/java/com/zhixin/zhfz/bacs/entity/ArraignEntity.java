package com.zhixin.zhfz.bacs.entity;

import com.zhixin.zhfz.common.utils.BeanUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * 提讯实体
 */
public class ArraignEntity implements Serializable {

    private Long id;

    private String name;

    private Integer roomType;

    private Long areaId;

    private String ips;

    private Integer roomStatus;

    private String serialUUID;

    //入区Id
    private Integer serialId;

    /**
     * 送押人ID
     */
    private String sendName;

    /**
     * 提审人ID
     */
    private String goName;

    /**
     * 提审人ID
     */
    private String gtName;

    private String personName;

    private String inPhotoName;

    private Date startTime;

    private Date outTime;

    private Integer showPerson;

    private String caseNo;

    private Integer caseType;

    private String certificateNo;

    private Integer sex;

    private String organization;

    private String caseName;

    private String caseClass;

    public String getCaseClass() {
        return caseClass;
    }

    public void setCaseClass(String caseClass) {
        this.caseClass = caseClass;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getCaseName() {
        return caseName;
    }

    public void setCaseName(String caseName) {
        this.caseName = caseName;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getOutTime() {
        return outTime;
    }

    public void setOutTime(Date outTime) {
        this.outTime = outTime;
    }

    public String getCaseNo() {
        return caseNo;
    }

    public void setCaseNo(String caseNo) {
        this.caseNo = caseNo;
    }

    public Integer getCaseType() {
        return caseType;
    }

    public void setCaseType(Integer caseType) {
        this.caseType = caseType;
    }

    public String getCertificateNo() {
        return certificateNo;
    }

    public void setCertificateNo(String certificateNo) {
        this.certificateNo = certificateNo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRoomType() {
        return roomType;
    }

    public void setRoomType(Integer roomType) {
        this.roomType = roomType;
    }

    public Long getAreaId() {
        return areaId;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

    public String getIps() {
        return ips;
    }

    public void setIps(String ips) {
        this.ips = ips;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getSerialUUID() {
        return serialUUID;
    }

    public void setSerialUUID(String serialUUID) {
        this.serialUUID = serialUUID;
    }

    public Integer getRoomStatus() {
        return roomStatus;
    }

    public void setRoomStatus(Integer roomStatus) {
        this.roomStatus = roomStatus;
    }

    public String getSendName() {
        return sendName;
    }

    public void setSendName(String sendName) {
        this.sendName = sendName;
    }

    public String getGoName() {
        return goName;
    }

    public void setGoName(String goName) {
        this.goName = goName;
    }

    public String getGtName() {
        return gtName;
    }

    public void setGtName(String gtName) {
        this.gtName = gtName;
    }

    public Integer getShowPerson() {
        return showPerson;
    }

    public void setShowPerson(Integer showPerson) {
        this.showPerson = showPerson;
    }

    public boolean equals(ArraignEntity entity) {
        return (BeanUtils.notEmpty(entity) && this.id == entity.getId());
    }

    public String getInPhotoName() {
        return inPhotoName;
    }

    public void setInPhotoName(String inPhotoName) {
        this.inPhotoName = inPhotoName;
    }

    public Integer getSerialId() {
        return serialId;
    }

    public void setSerialId(Integer serialId) {
        this.serialId = serialId;
    }
}
