package com.zhixin.zhfz.glpt.entity;


import java.util.Date;

/**
 * 督办
 */
public class SupervisorRequestEntity {

    /**
     * `id` BIGINT ( 11 ) NOT NULL AUTO_INCREMENT,
     * `case_id` INT ( 11 ) NOT NULL COMMENT '案件ID',
     * `ba_sign` INT ( 1 ) DEFAULT '0' COMMENT '1办案系统 0无',
     * `sa_sign` INT ( 1 ) DEFAULT '0' COMMENT '1涉案财物 0无',
     * `jz_sign` INT ( 1 ) DEFAULT '0' COMMENT '1卷宗 0无',
     * `send_orgId` INT ( 11 ) NOT NULL COMMENT '督办发起部门ID',
     * `send_userId` INT ( 11 ) NOT NULL COMMENT '督办发起人ID',
     * `send_time` datetime NOT NULL COMMENT '督办发起时间',
     * `content` VARCHAR ( 1024 ) NOT NULL DEFAULT '' COMMENT '督办内容',
     * `status` INT ( 11 ) DEFAULT '0' COMMENT '0督办下发、1（督办反馈 督办审核）、2督办审核通过 3督办审核不通过',
     * `op_pid` VARCHAR ( 64 ) NOT NULL DEFAULT '' COMMENT '当前操作人员所在单位PID  1_2_3',
     * `op_user_id` INT ( 11 ) NOT NULL DEFAULT '0' COMMENT '当前操作人员ID ',
     */

    private Long id;
    private Long caseId;
    private String caseName;
    private Integer baSign = 0;
    private Integer saSign = 0;
    private Integer jzSign = 0;
    private Integer agSign = 0 ;
    private Integer agFun = 0 ;
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

    public Long getCaseId() {
        return caseId;
    }

    public void setCaseId(Long caseId) {
        this.caseId = caseId;
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

    public Long getSendUserId() {
        return sendUserId;
    }

    public void setSendUserId(Long sendUserId) {
        this.sendUserId = sendUserId;
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

    public String getAuditOrgId() {
        return auditOrgId;
    }

    public void setAuditOrgId(String auditOrgId) {
        this.auditOrgId = auditOrgId;
    }

    public Long getOpUserId() {
        return opUserId;
    }

    public void setOpUserId(Long opUserId) {
        this.opUserId = opUserId;
    }

    public String getReceiveOrgId() {
        return receiveOrgId;
    }

    public void setReceiveOrgId(String receiveOrgId) {
        this.receiveOrgId = receiveOrgId;
    }

    public String getCaseName() {
        return caseName;
    }

    public void setCaseName(String caseName) {
        this.caseName = caseName;
    }

    public String getSendOrgName() {
        return sendOrgName;
    }

    public void setSendOrgName(String sendOrgName) {
        this.sendOrgName = sendOrgName;
    }

    public String getSendUserName() {
        return sendUserName;
    }

    public void setSendUserName(String sendUserName) {
        this.sendUserName = sendUserName;
    }

    public String getOpOrgName() {
        return opOrgName;
    }

    public void setOpOrgName(String opOrgName) {
        this.opOrgName = opOrgName;
    }

    public String getOpUserName() {
        return opUserName;
    }

    public void setOpUserName(String opUserName) {
        this.opUserName = opUserName;
    }

    public String getReceiveOrgName() {
        return receiveOrgName;
    }

    public void setReceiveOrgName(String receiveOrgName) {
        this.receiveOrgName = receiveOrgName;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getHisAnswer() {
        return hisAnswer;
    }

    public void setHisAnswer(Integer hisAnswer) {
        this.hisAnswer = hisAnswer;
    }

    public Integer getAgSign() {
        return agSign;
    }

    public void setAgSign(Integer agSign) {
        this.agSign = agSign;
    }

    public Integer getAgFun() {
        return agFun;
    }

    public void setAgFun(Integer agFun) {
        this.agFun = agFun;
    }

    @Override
    public String toString() {
        return "SupervisorRequestEntity{" +
                "id=" + id +
                ", caseId=" + caseId +
                ", caseName='" + caseName + '\'' +
                ", baSign=" + baSign +
                ", saSign=" + saSign +
                ", jzSign=" + jzSign +
                ", agSign=" + agSign +
                ", agFun=" + agFun +
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
