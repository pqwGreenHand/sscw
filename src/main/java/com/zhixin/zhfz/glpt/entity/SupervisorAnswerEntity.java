package com.zhixin.zhfz.glpt.entity;

import java.util.Date;

/**
 * 督办
 */
public class SupervisorAnswerEntity {

    /**
     *`id` BIGINT ( 11 ) NOT NULL AUTO_INCREMENT,
     * `duban_id` INT ( 11 ) NOT NULL COMMENT '督办发起单ID',
     * `receive_userId` INT ( 11 ) NOT NULL COMMENT '督办接收人ID',
     * `receive_time` datetime NOT NULL COMMENT '督办接收时间',
     * `content` VARCHAR ( 1024 ) NOT NULL DEFAULT '' COMMENT '督办反馈内容',
     * `status` INT ( 255 ) DEFAULT '0' COMMENT '审核结果（1通过、0不通过）',
     * `audit_orgId` INT ( 11 ) DEFAULT NULL COMMENT '审核部门ID',
     * `audit_userId` INT ( 11 ) DEFAULT NULL COMMENT '审核人ID',
     * `audit_time` datetime DEFAULT NULL COMMENT '审核时间',
     * `audit_content` VARCHAR ( 1024 ) DEFAULT '' COMMENT '审核意见',
     * `deal_org_id` INT ( 11 ) NOT NULL COMMENT '上一处理部门id',
     * `op_pid` VARCHAR ( 64 ) NOT NULL DEFAULT '' COMMENT '当前操作人员所在单位PID  1_2_3',
     * `op_user_id` INT ( 11 ) NOT NULL DEFAULT '0' COMMENT '当前操作人员ID ',
     */

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
    private String opPid;
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

    public Long getAuditUserId() {
        return auditUserId;
    }

    public void setAuditUserId(Long auditUserId) {
        this.auditUserId = auditUserId;
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

    public String getOpPid() {
        return opPid;
    }

    public void setOpPid(String opPid) {
        this.opPid = opPid;
    }

    public String getOpUserId() {
        return opUserId;
    }

    public void setOpUserId(String opUserId) {
        this.opUserId = opUserId;
    }

    public String getReceiveUserName() {
        return receiveUserName;
    }

    public void setReceiveUserName(String receiveUserName) {
        this.receiveUserName = receiveUserName;
    }

    public String getAuditOrgName() {
        return auditOrgName;
    }

    public void setAuditOrgName(String auditOrgName) {
        this.auditOrgName = auditOrgName;
    }

    public String getAuditUserName() {
        return auditUserName;
    }

    public void setAuditUserName(String auditUserName) {
        this.auditUserName = auditUserName;
    }

    @Override
    public String toString() {
        return "SupervisorAnswerEntity{" +
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
                ", opPid='" + opPid + '\'' +
                ", opUserId='" + opUserId + '\'' +
                '}';
    }
}
