package com.zhixin.zhfz.bacs.entity;

import java.io.Serializable;
import java.util.Date;

public class ExhibitEntity implements Serializable {
    /**
     * 涉案物品柜
     */
    private static final long serialVersionUID = -6910258234111885248L;

    private Integer id;
    private Integer did;
    private Integer cid;
    private String serialNo;
    private String url;
    private Long serialId;
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
    private Integer exhibitId;
    private String name;
    private Integer detailCount;
    private String description;
    private String burcode;
    private String aname;
    private String cname;
    private String pname;
    private String printerName;
    private String printerPort;
    private String personName;
    private String cabinetGroup;//柜组
    private String casename;
    private String caseNo;
    private String cabinetNo; //柜号
    private String unit;
    private int logId;
    private int logBelongId;
    private String logBelongLockerId;
    private String logBelongOpener;
    private String logBelongReason;
    private Date logCreatedTime;
    private int logExhibitId;
    private String policeName;
    private String involvedReason;
    private Integer op_user_id;
    private String op_pid;
    private String certificateNo;
    private String bename;
    private String boxname;
    private String plName;
    private String pName;
    private String organization1;
    private String organization2;
    private String sendUserId;
    private String caseName;
    private String pNname;

    public String getpNname() {
        return pNname;
    }

    public void setpNname(String pNname) {
        this.pNname = pNname;
    }

    public String getCaseName() {
        return caseName;
    }

    public void setCaseName(String caseName) {
        this.caseName = caseName;
    }

    public String getPlName() {
        return plName;
    }

    public void setPlName(String plName) {
        this.plName = plName;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getOrganization1() {
        return organization1;
    }

    public void setOrganization1(String organization1) {
        this.organization1 = organization1;
    }

    public String getOrganization2() {
        return organization2;
    }

    public void setOrganization2(String organization2) {
        this.organization2 = organization2;
    }

    public String getSendUserId() {
        return sendUserId;
    }

    public void setSendUserId(String sendUserId) {
        this.sendUserId = sendUserId;
    }

    public String getBoxname() {
        return boxname;
    }

    public void setBoxname(String boxname) {
        this.boxname = boxname;
    }

    public String getBename() {
        return bename;
    }

    public void setBename(String bename) {
        this.bename = bename;
    }

    public String getCertificateNo() {
        return certificateNo;
    }

    public void setCertificateNo(String certificateNo) {
        this.certificateNo = certificateNo;
    }

    public Integer getOp_user_id() {
        return op_user_id;
    }

    public void setOp_user_id(Integer op_user_id) {
        this.op_user_id = op_user_id;
    }

    public String getOp_pid() {
        return op_pid;
    }

    public void setOp_pid(String op_pid) {
        this.op_pid = op_pid;
    }

    public String getPoliceName() {
        return policeName;
    }

    public void setPoliceName(String policeName) {
        this.policeName = policeName;
    }

    public String getInvolvedReason() {
        return involvedReason;
    }

    public void setInvolvedReason(String involvedReason) {
        this.involvedReason = involvedReason;
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

    public Long getSerialId() {
        return serialId;
    }

    public void setSerialId(Long interrogateSerialId) {
        this.serialId = interrogateSerialId;
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

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
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

    public Integer getExhibitId() {
        return exhibitId;
    }

    public void setExhibitId(Integer exhibitId) {
        this.exhibitId = exhibitId;
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

    public String getCasename() {
        return casename;
    }

    public void setCasename(String casename) {
        this.casename = casename;
    }

    public String getCaseNo() {
        return caseNo;
    }

    public void setCaseNo(String caseNo) {
        this.caseNo = caseNo;
    }

    public String getCabinetNo() {
        return cabinetNo;
    }

    public void setCabinetNo(String cabinetNo) {
        this.cabinetNo = cabinetNo;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
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

    public int getLogExhibitId() {
        return logExhibitId;
    }

    public void setLogExhibitId(int logExhibitId) {
        this.logExhibitId = logExhibitId;
    }

    @Override
    public String toString() {
        return "ExhibitEntity{" +
                "id=" + id +
                ", did=" + did +
                ", cid=" + cid +
                ", serialNo='" + serialNo + '\'' +
                ", url='" + url + '\'' +
                ", serialId=" + serialId +
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
                ", exhibitId=" + exhibitId +
                ", name='" + name + '\'' +
                ", detailCount=" + detailCount +
                ", description='" + description + '\'' +
                ", burcode='" + burcode + '\'' +
                ", aname='" + aname + '\'' +
                ", cname='" + cname + '\'' +
                ", pname='" + pname + '\'' +
                ", printerName='" + printerName + '\'' +
                ", printerPort='" + printerPort + '\'' +
                ", personName='" + personName + '\'' +
                ", cabinetGroup='" + cabinetGroup + '\'' +
                ", casename='" + casename + '\'' +
                ", caseNo='" + caseNo + '\'' +
                ", cabinetNo='" + cabinetNo + '\'' +
                ", unit='" + unit + '\'' +
                ", logId=" + logId +
                ", logBelongId=" + logBelongId +
                ", logBelongLockerId='" + logBelongLockerId + '\'' +
                ", logBelongOpener='" + logBelongOpener + '\'' +
                ", logBelongReason='" + logBelongReason + '\'' +
                ", logCreatedTime=" + logCreatedTime +
                ", logExhibitId=" + logExhibitId +
                ", policeName='" + policeName + '\'' +
                ", involvedReason='" + involvedReason + '\'' +
                '}';
    }
}