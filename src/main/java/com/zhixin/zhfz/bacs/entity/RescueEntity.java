package com.zhixin.zhfz.bacs.entity;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @program: zhfz
 * @description: 医疗救助实体
 * @author: jzw
 * @create: 2019-03-01 09:59
 **/

public class RescueEntity implements Serializable {
    
    /**
     * `id` INT ( 11 ) NOT NULL AUTO_INCREMENT,
     * `serial_id` BIGINT ( 20 ) NOT NULL COMMENT '专属编号ID',
     * `person_id` BIGINT ( 20 ) NOT NULL COMMENT '被采集人员ID',
     * `register_user_id` INT ( 11 ) NOT NULL COMMENT '记录人ID',
     * `case_id` INT ( 11 ) NOT NULL COMMENT '案件ID',
     * `area_id` INT ( 11 ) NOT NULL COMMENT '办案场所ID',
     * `doctor` VARCHAR ( 255 ) DEFAULT NULL,
     * `description` VARCHAR ( 255 ) DEFAULT NULL COMMENT '其他描述',
     * `item` VARCHAR ( 255 ) DEFAULT NULL COMMENT '救助事项',
     * `rescue_time` datetime DEFAULT NULL,
     * `updated_time` datetime DEFAULT NULL COMMENT '更新时间',
     * `created_time` datetime NOT NULL COMMENT '创建时间',
     * `police_id` int(11) NOT NULL COMMENT '押送民警id(user表)',
     **/

    private SimpleDateFormat format;

    public RescueEntity(){
        format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        format.setLenient(true);
    }

    private Long id;

    private Long serialId;

    private Long registerUserId;

    private Long policeId;

    private Long caseId;

    private Long areaId;

    private Long personId;

    private Date createdTime;

    private Date updatedTime;

    private Date rescueTime;

    private String doctor;

    private String item;

    private String description;

    private String registerUserName; //警号

    private String policeName; //押送警号

    private String personName; //人员姓名

    private String caseNo; //案件编号

    private String areaName; //办案场所名称

    private String rescueTimeStr;
    private String policeNo;

    private String opPid;
    private Integer opUserId;

    public String getPoliceNo() {
        return policeNo;
    }

    public void setPoliceNo(String policeNo) {
        this.policeNo = policeNo;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSerialId() {
        return serialId;
    }

    public void setSerialId(Long serialId) {
        this.serialId = serialId;
    }

    public Long getRegisterUserId() {
        return registerUserId;
    }

    public void setRegisterUserId(Long registerUserId) {
        this.registerUserId = registerUserId;
    }

    public Long getPoliceId() {
        return policeId;
    }

    public void setPoliceId(Long policeId) {
        this.policeId = policeId;
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

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
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

    public Date getRescueTime() throws ParseException {
        if(rescueTime == null && rescueTimeStr != null && rescueTimeStr != "")
            rescueTime = format.parse(rescueTimeStr);
        return rescueTime;
    }

    public void setRescueTime(Date rescueTime) {
        this.rescueTime = rescueTime;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRegisterUserName() {
        return registerUserName;
    }

    public void setRegisterUserName(String registerUserName) {
        this.registerUserName = registerUserName;
    }

    public String getPoliceName() {
        return policeName;
    }

    public void setPoliceName(String policeName) {
        this.policeName = policeName;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getCaseNo() {
        return caseNo;
    }

    public void setCaseNo(String caseNo) {
        this.caseNo = caseNo;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getRescueTimeStr() {
        if(rescueTimeStr == null && rescueTime != null)
            rescueTimeStr = format.format(rescueTime);
        return rescueTimeStr;
    }

    public void setRescueTimeStr(String rescueTimeStr) {
        this.rescueTimeStr = rescueTimeStr;
    }
}
