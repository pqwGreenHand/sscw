package com.zhixin.zhfz.bacsapp.entity;


import java.util.Date;

public class InformationEntity {

    /**
     * xt_schedule
     * `id` BIGINT ( 20 ) NOT NULL AUTO_INCREMENT,
     * 	`sender_id` INT ( 11 ) NOT NULL COMMENT '发送人ID',
     * 	`receiver_id` INT ( 11 ) NOT NULL COMMENT '收件人ID',
     * 	`title` VARCHAR ( 256 ) NOT NULL COMMENT '标题',
     * 	`content` VARCHAR ( 1000 ) NOT NULL COMMENT '内容',
     * 	`status` INT ( 1 ) NOT NULL DEFAULT '0' COMMENT '状态 0未处理 1已处理',
     * 	`happen_time` datetime DEFAULT NULL COMMENT '发生时间',
     * 	`handler_time` datetime DEFAULT NULL COMMENT '处理时间',
     * 	`handler_content` VARCHAR ( 255 ) DEFAULT NULL COMMENT '处理答复',
     * 	`system_name` VARCHAR ( 255 ) NOT NULL COMMENT 'BA 办案、SA 涉案、JZ 卷宗、KP 考评、XT 系统',
     *
     * xt_inform
     * `id` BIGINT ( 20 ) NOT NULL AUTO_INCREMENT,
     * `sender_id` INT ( 11 ) NOT NULL COMMENT '发送人ID',
     * `receiver_id` INT ( 11 ) NOT NULL COMMENT '收件人ID',
     * `title` VARCHAR ( 256 ) NOT NULL COMMENT '标题',
     * `content` VARCHAR ( 1000 ) NOT NULL COMMENT '内容',
     * `send_time` datetime NOT NULL COMMENT '发送时间',
     * `is_read` INT ( 1 ) NOT NULL DEFAULT '0' COMMENT '0未查看 1已查看',
     * `read_time` datetime DEFAULT NULL COMMENT '阅读时间',
     * `system_name` VARCHAR ( 255 ) NOT NULL COMMENT 'BA 办案、SA 涉案、JZ 卷宗、KP 考评、XT 系统',
     */

    private Long id;

    private Long senderId;

    private Long receiverId;

    private Date createTime;

    private String title;

    private String content;

    private String systemName;

    private Integer status;

    private Date dealTime;

    private String handLerContent;

    private Integer type; //0:信息;1:待办

    private Date sendTime;

    private Date readTime;

    private Integer isRead;

    private String ajbh;

    private String ajmc;

    private String opPid;

    private Integer opUserId;

    private Integer shzt;

    public Integer getShzt() {
        return shzt;
    }

    public void setShzt(Integer shzt) {
        this.shzt = shzt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public Long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getDealTime() {
        return dealTime;
    }

    public void setDealTime(Date dealTime) {
        this.dealTime = dealTime;
    }

    public String getHandLerContent() {
        return handLerContent;
    }

    public void setHandLerContent(String handLerContent) {
        this.handLerContent = handLerContent;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public Integer getIsRead() {
        return isRead;
    }

    public void setIsRead(Integer isRead) {
        this.isRead = isRead;
    }

    public String getAjbh() {
        return ajbh;
    }

    public void setAjbh(String ajbh) {
        this.ajbh = ajbh;
    }

    public String getAjmc() {
        return ajmc;
    }

    public void setAjmc(String ajmc) {
        this.ajmc = ajmc;
    }

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

    public Date getReadTime() {
        return readTime;
    }

    public void setReadTime(Date readTime) {
        this.readTime = readTime;
    }

    @Override
    public String toString() {
        return "InformationEntity{" +
                "id=" + id +
                ", senderId=" + senderId +
                ", receiverId=" + receiverId +
                ", createTime=" + createTime +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", systemName='" + systemName + '\'' +
                ", status=" + status +
                ", dealTime=" + dealTime +
                ", handLerContent='" + handLerContent + '\'' +
                ", type=" + type +
                ", sendTime=" + sendTime +
                ", isRead=" + isRead +
                ", ajbh='" + ajbh + '\'' +
                ", ajmc='" + ajmc + '\'' +
                ", opPid='" + opPid + '\'' +
                ", opUserId=" + opUserId +
                '}';
    }
}
