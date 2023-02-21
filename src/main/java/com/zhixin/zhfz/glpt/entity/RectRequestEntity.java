package com.zhixin.zhfz.glpt.entity;

import java.util.Date;

/**
 * 矩形 -> 整改 rectification
 */
public class RectRequestEntity {

    private Long id;
    private Long supId;
    private Long caseId;
    private String caseName;
    private Integer baSign = 0;
    private Integer saSign = 0;
    private Integer jzSign = 0;
    private String sendOrgId;
    private String sendOrgName;
    private Long sendUserId;
    private String sendUserName;
    private Date sendTime;
    private String content;
    private Integer status;
    private Integer count;
    private String auditOrgId;
    private String opOrgName;
    private Long opUserId;
    private String opUserName;
    private String receiveOrgId;
    private String receiveOrgName;
    private Integer hisAnswer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSupId() {
        return supId;
    }

    public void setSupId(Long supId) {
        this.supId = supId;
    }

    public Long getCaseId() {
        return caseId;
    }

    public void setCaseId(Long caseId) {
        this.caseId = caseId;
    }

    public String getCaseName() {
        return caseName;
    }

    public void setCaseName(String caseName) {
        this.caseName = caseName;
    }

    public Integer getBaSign() {
        return baSign;
    }

    public void setBaSign(Integer baSign) {
        this.baSign = baSign;
    }

    public Integer getSaSign() {
        return saSign;
    }

    public void setSaSign(Integer saSign) {
        this.saSign = saSign;
    }

    public Integer getJzSign() {
        return jzSign;
    }

    public void setJzSign(Integer jzSign) {
        this.jzSign = jzSign;
    }

    public String getSendOrgId() {
        return sendOrgId;
    }

    public void setSendOrgId(String sendOrgId) {
        this.sendOrgId = sendOrgId;
    }

    public String getSendOrgName() {
        return sendOrgName;
    }

    public void setSendOrgName(String sendOrgName) {
        this.sendOrgName = sendOrgName;
    }

    public Long getSendUserId() {
        return sendUserId;
    }

    public void setSendUserId(Long sendUserId) {
        this.sendUserId = sendUserId;
    }

    public String getSendUserName() {
        return sendUserName;
    }

    public void setSendUserName(String sendUserName) {
        this.sendUserName = sendUserName;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getAuditOrgId() {
        return auditOrgId;
    }

    public void setAuditOrgId(String auditOrgId) {
        this.auditOrgId = auditOrgId;
    }

    public String getOpOrgName() {
        return opOrgName;
    }

    public void setOpOrgName(String opOrgName) {
        this.opOrgName = opOrgName;
    }

    public Long getOpUserId() {
        return opUserId;
    }

    public void setOpUserId(Long opUserId) {
        this.opUserId = opUserId;
    }

    public String getOpUserName() {
        return opUserName;
    }

    public void setOpUserName(String opUserName) {
        this.opUserName = opUserName;
    }

    public String getReceiveOrgId() {
        return receiveOrgId;
    }

    public void setReceiveOrgId(String receiveOrgId) {
        this.receiveOrgId = receiveOrgId;
    }

    public String getReceiveOrgName() {
        return receiveOrgName;
    }

    public void setReceiveOrgName(String receiveOrgName) {
        this.receiveOrgName = receiveOrgName;
    }

    public Integer getHisAnswer() {
        return hisAnswer;
    }

    public void setHisAnswer(Integer hisAnswer) {
        this.hisAnswer = hisAnswer;
    }

    @Override
    public String toString() {
        return "RectRequestEntity{" +
                "id=" + id +
                ", supId=" + supId +
                ", caseId=" + caseId +
                ", caseName='" + caseName + '\'' +
                ", baSign=" + baSign +
                ", saSign=" + saSign +
                ", jzSign=" + jzSign +
                ", sendOrgId='" + sendOrgId + '\'' +
                ", sendOrgName='" + sendOrgName + '\'' +
                ", sendUserId=" + sendUserId +
                ", sendUserName='" + sendUserName + '\'' +
                ", sendTime=" + sendTime +
                ", content='" + content + '\'' +
                ", status=" + status +
                ", count=" + count +
                ", auditOrgId='" + auditOrgId + '\'' +
                ", opOrgName='" + opOrgName + '\'' +
                ", opUserId=" + opUserId +
                ", opUserName='" + opUserName + '\'' +
                ", receiveOrgId='" + receiveOrgId + '\'' +
                ", receiveOrgName='" + receiveOrgName + '\'' +
                ", hisAnswer=" + hisAnswer +
                '}';
    }
}
