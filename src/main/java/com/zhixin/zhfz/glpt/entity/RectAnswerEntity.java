package com.zhixin.zhfz.glpt.entity;

import java.util.Date;

/**
 * rect->rectification 矩形->整改
 */
public class RectAnswerEntity {

    private Long id;
    private Long requestId;
    private Long receiveUserId;
    private String receiveUserName;
    private Date receiveTime;
    private String content;
    private Integer status;
    private String auditOrgId;
    private String auditOrgName;
    private Long auditUserId;
    private String auditUserName;
    private Date auditTime;
    private String auditContent;
    private String opOrgId;
    private String opUserId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public Long getReceiveUserId() {
        return receiveUserId;
    }

    public void setReceiveUserId(Long receiveUserId) {
        this.receiveUserId = receiveUserId;
    }

    public String getReceiveUserName() {
        return receiveUserName;
    }

    public void setReceiveUserName(String receiveUserName) {
        this.receiveUserName = receiveUserName;
    }

    public Date getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(Date receiveTime) {
        this.receiveTime = receiveTime;
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

    public String getAuditOrgId() {
        return auditOrgId;
    }

    public void setAuditOrgId(String auditOrgId) {
        this.auditOrgId = auditOrgId;
    }

    public String getAuditOrgName() {
        return auditOrgName;
    }

    public void setAuditOrgName(String auditOrgName) {
        this.auditOrgName = auditOrgName;
    }

    public Long getAuditUserId() {
        return auditUserId;
    }

    public void setAuditUserId(Long auditUserId) {
        this.auditUserId = auditUserId;
    }

    public String getAuditUserName() {
        return auditUserName;
    }

    public void setAuditUserName(String auditUserName) {
        this.auditUserName = auditUserName;
    }

    public Date getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }

    public String getAuditContent() {
        return auditContent;
    }

    public void setAuditContent(String auditContent) {
        this.auditContent = auditContent;
    }

    public String getOpOrgId() {
        return opOrgId;
    }

    public void setOpOrgId(String opOrgId) {
        this.opOrgId = opOrgId;
    }

    public String getOpUserId() {
        return opUserId;
    }

    public void setOpUserId(String opUserId) {
        this.opUserId = opUserId;
    }

    @Override
    public String toString() {
        return "RectAnswerEntity{" +
                "id=" + id +
                ", requestId=" + requestId +
                ", receiveUserId=" + receiveUserId +
                ", receiveUserName='" + receiveUserName + '\'' +
                ", receiveTime=" + receiveTime +
                ", content='" + content + '\'' +
                ", status=" + status +
                ", auditOrgId='" + auditOrgId + '\'' +
                ", auditOrgName='" + auditOrgName + '\'' +
                ", auditUserId=" + auditUserId +
                ", auditUserName='" + auditUserName + '\'' +
                ", auditTime=" + auditTime +
                ", auditContent='" + auditContent + '\'' +
                ", opOrgId='" + opOrgId + '\'' +
                ", opUserId='" + opUserId + '\'' +
                '}';
    }
}
