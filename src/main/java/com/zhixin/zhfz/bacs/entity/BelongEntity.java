package com.zhixin.zhfz.bacs.entity;

import java.util.Date;

public class BelongEntity implements java.io.Serializable {
    /**
     * 储物柜使用情况
     */
    private static final long serialVersionUID = 4614966861269493860L;

    private Integer id;
    private Integer did;
    private Integer cid;
    private String serialNo;
    private String url;
    private Long serialId;
    private Long personId;
    private Integer registerUserId;
    private Date registerTime;
    private Integer caseId;
    private Integer areaId;
    private String areaName;
    private String lockerId;
    private String boxip;
    private String boxgroup;
    private String boxport;
    private Integer isGet;
    private String getWay;
    private String getPerson;
    private Date getTime;
    private Date createdTime;
    private Date updatedTime;
    private Date inTime;
    private Integer belongingsId;
    private String name;
    private Integer detailCount;
    private String description;
    private String burcode;
    private String aname;
    private String cname;
    private String pname;
    private String printerName;
    private String printerPort;
    private String casename;
    private String caseNo;
    private String personname;
    private String certificateNo;
    private String involvedReason;
    private String bename;
    private String boxname;
    private String personName;
    private String cabinetGroup;//柜组
    private String cabinetNo; //柜号
    private int logId;
    private int logBelongId;
    private String logBelongLockerId;
    private String logBelongOpener;
    private String logBelongReason;
    private Date logCreatedTime;
    private String unit;
    private String inPhotoName;
    private String saveMethod;              //
    private String policeName;              //民警名称
    private String uuid;              //民警名称
    private Integer opUserId;
    private String opPid;
    private String wpUuid;
    private String tempId;
    private String jsdwId;
    private String wname;
    private Integer belongCount;

    private String lqUrl;
    private String crUrl;

    private String cwurl;
    private String qwurl;

    public String getWname() {
        return wname;
    }

    public void setWname(String wname) {
        this.wname = wname;
    }

    public Integer getBelongCount() {
        return belongCount;
    }

    public void setBelongCount(Integer belongCount) {
        this.belongCount = belongCount;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public String getJsdwId() {
        return jsdwId;
    }

    public void setJsdwId(String jsdwId) {
        this.jsdwId = jsdwId;
    }

    public String getTempId() {
        return tempId;
    }

    public void setTempId(String tempId) {
        this.tempId = tempId;
    }

    public Integer getOpUserId() {
        return opUserId;
    }

    public void setOpUserId(Integer opUserId) {
        this.opUserId = opUserId;
    }

    public String getOpPid() {
        return opPid;
    }

    public void setOpPid(String opPid) {
        this.opPid = opPid;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public Date getInTime() {
        return inTime;
    }

    public void setInTime(Date inTime) {
        this.inTime = inTime;
    }

    public String getInPhotoName() {
        return inPhotoName;
    }

    public void setInPhotoName(String inPhotoName) {
        this.inPhotoName = inPhotoName;
    }

    public String getCaseNo() {
        return caseNo;
    }

    public void setCaseNo(String caseNo) {
        this.caseNo = caseNo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDid() {
        return did;
    }

    public void setDid(Integer did) {
        this.did = did;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public Integer getRegisterUserId() {
        return registerUserId;
    }

    public void setRegisterUserId(Integer registerUserId) {
        this.registerUserId = registerUserId;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public Integer getCaseId() {
        return caseId;
    }

    public void setCaseId(Integer caseId) {
        this.caseId = caseId;
    }


    public String getAreaName() {
        return areaName;
    }

    public void setIAreaName(String interrogateAreaName) {
        this.areaName = interrogateAreaName;
    }

    public String getLockerId() {
        return lockerId;
    }

    public void setLockerId(String lockerId) {
        this.lockerId = lockerId;
    }

    public String getBoxip() {
        return boxip;
    }

    public void setBoxip(String boxip) {
        this.boxip = boxip;
    }

    public String getBoxgroup() {
        return boxgroup;
    }

    public void setBoxgroup(String boxgroup) {
        this.boxgroup = boxgroup;
    }

    public String getBoxport() {
        return boxport;
    }

    public void setBoxport(String boxport) {
        this.boxport = boxport;
    }

    public Integer getIsGet() {
        return isGet;
    }

    public void setIsGet(Integer isGet) {
        this.isGet = isGet;
    }

    public String getGetWay() {
        return getWay;
    }

    public void setGetWay(String getWay) {
        this.getWay = getWay;
    }

    public String getGetPerson() {
        return getPerson;
    }

    public void setGetPerson(String getPerson) {
        this.getPerson = getPerson;
    }

    public Date getGetTime() {
        return getTime;
    }

    public void setGetTime(Date getTime) {
        this.getTime = getTime;
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

    public Integer getBelongingsId() {
        return belongingsId;
    }

    public void setBelongingsId(Integer belongingsId) {
        this.belongingsId = belongingsId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDetailCount() {
        return detailCount;
    }

    public void setDetailCount(Integer detailCount) {
        this.detailCount = detailCount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBurcode() {
        return burcode;
    }

    public void setBurcode(String burcode) {
        this.burcode = burcode;
    }

    public String getAname() {
        return aname;
    }

    public void setAname(String aname) {
        this.aname = aname;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPrinterName() {
        return printerName;
    }

    public void setPrinterName(String printerName) {
        this.printerName = printerName;
    }

    public String getPrinterPort() {
        return printerPort;
    }

    public void setPrinterPort(String printerPort) {
        this.printerPort = printerPort;
    }

    public String getCasename() {
        return casename;
    }

    public void setCasename(String casename) {
        this.casename = casename;
    }

    public String getPersonname() {
        return personname;
    }

    public void setPersonname(String personname) {
        this.personname = personname;
    }

    public String getCertificateNo() {
        return certificateNo;
    }

    public void setCertificateNo(String certificateNo) {
        this.certificateNo = certificateNo;
    }

    public String getInvolvedReason() {
        return involvedReason;
    }

    public void setInvolvedReason(String involvedReason) {
        this.involvedReason = involvedReason;
    }

    public String getBename() {
        return bename;
    }

    public void setBename(String bename) {
        this.bename = bename;
    }

    public String getBoxname() {
        return boxname;
    }

    public void setBoxname(String boxname) {
        this.boxname = boxname;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getCabinetGroup() {
        return cabinetGroup;
    }

    public void setCabinetGroup(String cabinetGroup) {
        this.cabinetGroup = cabinetGroup;
    }

    public String getCabinetNo() {
        return cabinetNo;
    }

    public void setCabinetNo(String cabinetNo) {
        this.cabinetNo = cabinetNo;
    }

    public int getLogId() {
        return logId;
    }

    public void setLogId(int logId) {
        this.logId = logId;
    }

    public int getLogBelongId() {
        return logBelongId;
    }

    public void setLogBelongId(int logBelongId) {
        this.logBelongId = logBelongId;
    }

    public String getLogBelongLockerId() {
        return logBelongLockerId;
    }

    public void setLogBelongLockerId(String logBelongLockerId) {
        this.logBelongLockerId = logBelongLockerId;
    }

    public String getLogBelongOpener() {
        return logBelongOpener;
    }

    public void setLogBelongOpener(String logBelongOpener) {
        this.logBelongOpener = logBelongOpener;
    }

    public String getLogBelongReason() {
        return logBelongReason;
    }

    public void setLogBelongReason(String logBelongReason) {
        this.logBelongReason = logBelongReason;
    }

    public Date getLogCreatedTime() {
        return logCreatedTime;
    }

    public void setLogCreatedTime(Date logCreatedTime) {
        this.logCreatedTime = logCreatedTime;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getSaveMethod() {
        return saveMethod;
    }

    public void setSaveMethod(String saveMethod) {
        this.saveMethod = saveMethod;
    }

    public String getPoliceName() {
        return policeName;
    }

    public void setPoliceName(String policeName) {
        this.policeName = policeName;
    }

    public Long getSerialId() {
        return serialId;
    }

    public void setSerialId(Long serialId) {
        this.serialId = serialId;
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    @Override
    public String toString() {
        return "BelongEntity{" +
                "id=" + id +
                ", did=" + did +
                ", cid=" + cid +
                ", serialNo='" + serialNo + '\'' +
                ", url='" + url + '\'' +
                ", serialId=" + serialId +
                ", personId=" + personId +
                ", registerUserId=" + registerUserId +
                ", registerTime=" + registerTime +
                ", caseId=" + caseId +
                ", areaId=" + areaId +
                ", areaName='" + areaName + '\'' +
                ", lockerId='" + lockerId + '\'' +
                ", boxip='" + boxip + '\'' +
                ", boxgroup='" + boxgroup + '\'' +
                ", boxport='" + boxport + '\'' +
                ", isGet=" + isGet +
                ", getWay='" + getWay + '\'' +
                ", getPerson='" + getPerson + '\'' +
                ", getTime=" + getTime +
                ", createdTime=" + createdTime +
                ", updatedTime=" + updatedTime +
                ", inTime=" + inTime +
                ", belongingsId=" + belongingsId +
                ", name='" + name + '\'' +
                ", detailCount=" + detailCount +
                ", description='" + description + '\'' +
                ", burcode='" + burcode + '\'' +
                ", aname='" + aname + '\'' +
                ", cname='" + cname + '\'' +
                ", pname='" + pname + '\'' +
                ", printerName='" + printerName + '\'' +
                ", printerPort='" + printerPort + '\'' +
                ", casename='" + casename + '\'' +
                ", caseNo='" + caseNo + '\'' +
                ", personname='" + personname + '\'' +
                ", certificateNo='" + certificateNo + '\'' +
                ", involvedReason='" + involvedReason + '\'' +
                ", bename='" + bename + '\'' +
                ", boxname='" + boxname + '\'' +
                ", personName='" + personName + '\'' +
                ", cabinetGroup='" + cabinetGroup + '\'' +
                ", cabinetNo='" + cabinetNo + '\'' +
                ", logId=" + logId +
                ", logBelongId=" + logBelongId +
                ", logBelongLockerId='" + logBelongLockerId + '\'' +
                ", logBelongOpener='" + logBelongOpener + '\'' +
                ", logBelongReason='" + logBelongReason + '\'' +
                ", logCreatedTime=" + logCreatedTime +
                ", unit='" + unit + '\'' +
                ", inPhotoName='" + inPhotoName + '\'' +
                ", saveMethod='" + saveMethod + '\'' +
                ", policeName='" + policeName + '\'' +
                ", uuid='" + uuid + '\'' +
                ", opUserId=" + opUserId +
                ", opPid='" + opPid + '\'' +
                ", wpUuid='" + wpUuid + '\'' +
                ", tempId='" + tempId + '\'' +
                ", jsdwId='" + jsdwId + '\'' +
                ", belongCount=" + belongCount +
                '}';
    }

    public String getWpUuid() {
        return wpUuid;
    }

    public void setWpUuid(String wpUuid) {
        this.wpUuid = wpUuid;
    }

    public String getLqUrl() {
        return lqUrl;
    }

    public void setLqUrl(String lqUrl) {
        this.lqUrl = lqUrl;
    }

    public String getCrUrl() {
        return crUrl;
    }

    public void setCrUrl(String crUrl) {
        this.crUrl = crUrl;
    }

    public String getCwurl() {
        return cwurl;
    }

    public void setCwurl(String cwurl) {
        this.cwurl = cwurl;
    }

    public String getQwurl() {
        return qwurl;
    }

    public void setQwurl(String qwurl) {
        this.qwurl = qwurl;
    }
}