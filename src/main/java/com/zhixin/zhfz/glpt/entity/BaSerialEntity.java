package com.zhixin.zhfz.glpt.entity;

import java.util.Date;

public class BaSerialEntity implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String serialNo;

    private String uuid;

    private Integer status;

    private Date statusChangeTime;

    private Long personId;

    // private String name;

    private String personInfo;

    private Integer type;

    private Integer orderRequestId;
    private Integer areaId;


    //private String orderNo;

    private String areaName;

    private Integer caseId;

    //  private String interrogateCaseName;
    private  String inPhonoName;
    private String outPhonoName;

    private Integer cuffId;
    private Integer inRegisterUserId;
    private Integer outRegisterUserId;
    private  Integer sendUserId;

    // private Integer policeCuffId1;

    // private Integer policeCuffId2;

    // private String cuffNo;
    private Integer sex;
    private Date inTime;
    private Date outTime;
    private String outReason;
    private Integer outType;
    private  Date confirmTime;
    private String confirmResult;
    private String entranceProcedure;
    private Date writtenTime;
    private String remark;
    private Integer count;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getStatusChangeTime() {
        return statusChangeTime;
    }

    public void setStatusChangeTime(Date statusChangeTime) {
        this.statusChangeTime = statusChangeTime;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public String getPersonInfo() {
        return personInfo;
    }

    public void setPersonInfo(String personInfo) {
        this.personInfo = personInfo;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getOrderRequestId() {
        return orderRequestId;
    }

    public void setOrderRequestId(Integer orderRequestId) {
        this.orderRequestId = orderRequestId;
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public Integer getCaseId() {
        return caseId;
    }

    public void setCaseId(Integer caseId) {
        this.caseId = caseId;
    }

    public String getInPhonoName() {
        return inPhonoName;
    }

    public void setInPhonoName(String inPhonoName) {
        this.inPhonoName = inPhonoName;
    }

    public String getOutPhonoName() {
        return outPhonoName;
    }

    public void setOutPhonoName(String outPhonoName) {
        this.outPhonoName = outPhonoName;
    }

    public Integer getCuffId() {
        return cuffId;
    }

    public void setCuffId(Integer cuffId) {
        this.cuffId = cuffId;
    }

    public Integer getInRegisterUserId() {
        return inRegisterUserId;
    }

    public void setInRegisterUserId(Integer inRegisterUserId) {
        this.inRegisterUserId = inRegisterUserId;
    }

    public Integer getOutRegisterUserId() {
        return outRegisterUserId;
    }

    public void setOutRegisterUserId(Integer outRegisterUserId) {
        this.outRegisterUserId = outRegisterUserId;
    }

    public Integer getSendUserId() {
        return sendUserId;
    }

    public void setSendUserId(Integer sendUserId) {
        this.sendUserId = sendUserId;
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

    public String getOutReason() {
        return outReason;
    }

    public void setOutReason(String outReason) {
        this.outReason = outReason;
    }

    public Integer getOutType() {
        return outType;
    }

    public void setOutType(Integer outType) {
        this.outType = outType;
    }

    public Date getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(Date confirmTime) {
        this.confirmTime = confirmTime;
    }

    public String getConfirmResult() {
        return confirmResult;
    }

    public void setConfirmResult(String confirmResult) {
        this.confirmResult = confirmResult;
    }

    public String getEntranceProcedure() {
        return entranceProcedure;
    }

    public void setEntranceProcedure(String entranceProcedure) {
        this.entranceProcedure = entranceProcedure;
    }

    public Date getWrittenTime() {
        return writtenTime;
    }

    public void setWrittenTime(Date writtenTime) {
        this.writtenTime = writtenTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "BaSerialEntity{" +
                "id=" + id +
                ", serialNo='" + serialNo + '\'' +
                ", uuid='" + uuid + '\'' +
                ", status=" + status +
                ", statusChangeTime=" + statusChangeTime +
                ", personId=" + personId +
                ", personInfo='" + personInfo + '\'' +
                ", type=" + type +
                ", orderRequestId=" + orderRequestId +
                ", areaId=" + areaId +
                ", areaName='" + areaName + '\'' +
                ", caseId=" + caseId +
                ", inPhonoName='" + inPhonoName + '\'' +
                ", outPhonoName='" + outPhonoName + '\'' +
                ", cuffId=" + cuffId +
                ", inRegisterUserId=" + inRegisterUserId +
                ", outRegisterUserId=" + outRegisterUserId +
                ", sendUserId=" + sendUserId +
                ", inTime=" + inTime +
                ", outTime=" + outTime +
                ", outReason='" + outReason + '\'' +
                ", outType=" + outType +
                ", confirmTime=" + confirmTime +
                ", confirmResult='" + confirmResult + '\'' +
                ", entranceProcedure='" + entranceProcedure + '\'' +
                ", writtenTime=" + writtenTime +
                ", remark='" + remark + '\'' +
                '}';
    }
}