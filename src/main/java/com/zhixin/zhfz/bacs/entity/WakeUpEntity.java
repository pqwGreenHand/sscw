package com.zhixin.zhfz.bacs.entity;

import java.io.Serializable;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;


/**
 * @program: zhfz
 * @description: 醒酒登记
 * @author: jzw
 * @create: 2019-02-26 10:03
 **/

public class WakeUpEntity implements Serializable {

    /**
     * `id` INT ( 11 ) NOT NULL AUTO_INCREMENT,
     * `serial_id` BIGINT ( 20 ) NOT NULL COMMENT '专属编号ID',
     * `person_id` BIGINT ( 20 ) NOT NULL COMMENT '被采集人员ID',
     * `register_user_id` INT ( 11 ) NOT NULL COMMENT '记录人ID',
     * `register_time` datetime NOT NULL COMMENT '记录时间',
     * `case_id` INT ( 11 ) NOT NULL COMMENT '案件ID',
     * `area_id` INT ( 11 ) NOT NULL COMMENT '办案场所ID',
     * `created_time` datetime NOT NULL COMMENT '创建时间',
     * `updated_time` datetime DEFAULT NULL COMMENT '更新时间',
     * `start_time` datetime DEFAULT NULL,
     * `end_time` datetime DEFAULT NULL,
     * `description` VARCHAR ( 255 ) DEFAULT NULL,
     **/

    private SimpleDateFormat format;

    public WakeUpEntity(){
        format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        format.setLenient(true);
    }

    private Long id;

    private Long serialId;

    private Long registerUserId;

    private Long inUserId;

    private Long outUserId;

    private Long caseId;

    private Long areaId;

    private Long personId;

    private Long detainPoliceId;

    private Date createdTime;

    private Date updatedTime;

    private Date startTime;

    private Date endTime;

    private String inDescription;

    private String outDescription;

    private String registerUserName; //警号

    private String inUserName; //入区警号

    private String outUserName; //出区警号

    private String personName; //人员姓名

    private String caseNo; //案件编号

    private String areaName; //办案场所名称

    private String detainPoliceNo;

    private String startTimeStr;

    private String endTimeStr;

    private String opPid;
    private Integer opUserId;

    private String policeNo;

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

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


    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer("WakeUpEntity [");
        buffer.append("id = ").append(id)
                .append(", serialId = ").append(serialId)
                .append(", registerUserId = ").append(registerUserId)
                .append(", registerUserName = ").append(registerUserName)
                .append(", inUserId = ").append(inUserId)
                .append(", inUserName = ").append(inUserName)
                .append(", outUserId = ").append(outUserId)
                .append(", outUserName = ").append(outUserName)
                .append(", personId = ").append(personId)
                .append(", personName = ").append(personName)
                .append(", caseId = ").append(caseId)
                .append(", caseNo = ").append(caseNo)
                .append(", areaId = ").append(areaId)
                .append(", areaName = ").append(areaName)
                .append(", inDdescription = ").append(inDescription)
                .append(", outDdescription = ").append(outDescription)
                .append("]");

        return buffer.toString();
    }

    public Long getDetainPoliceId() {
        return detainPoliceId;
    }

    public void setDetainPoliceId(Long detainPoliceId) {
        this.detainPoliceId = detainPoliceId;
    }

    public String getDetainPoliceNo() {
        return detainPoliceNo;
    }

    public void setDetainPoliceNo(String detainPoliceNo) {
        this.detainPoliceNo = detainPoliceNo;
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

    public Date getStartTime() throws ParseException {
        if(startTime == null && startTimeStr != null && startTimeStr != ""){
            startTime = format.parse(startTimeStr);
        }
        return startTime;
    }

    public Date getEndTime() throws ParseException {
        if(endTime == null && endTimeStr != null && endTimeStr != ""){
            endTime = format.parse(endTimeStr);
        }

        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
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

    public Long getRegisterUserId() {
        return registerUserId;
    }

    public void setRegisterUserId(Long registerUserId) {
        this.registerUserId = registerUserId;
    }

    public Long getInUserId() {
        return inUserId;
    }

    public void setInUserId(Long inUserId) {
        this.inUserId = inUserId;
    }

    public Long getOutUserId() {
        return outUserId;
    }

    public void setOutUserId(Long outUserId) {
        this.outUserId = outUserId;
    }

    public String getInDescription() {
        return inDescription;
    }

    public void setInDescription(String inDescription) {
        this.inDescription = inDescription;
    }

    public String getOutDescription() {
        return outDescription;
    }

    public void setOutDescription(String outDescription) {
        this.outDescription = outDescription;
    }

    public String getRegisterUserName() {
        return registerUserName;
    }

    public void setRegisterUserName(String registerUserName) {
        this.registerUserName = registerUserName;
    }

    public String getInUserName() {
        return inUserName;
    }

    public void setInUserName(String inUserName) {
        this.inUserName = inUserName;
    }

    public String getOutUserName() {
        return outUserName;
    }

    public void setOutUserName(String outUserName) {
        this.outUserName = outUserName;
    }

    public String getStartTimeStr() {
        if(startTimeStr == null && startTime != null){
            startTimeStr = format.format(startTime);
        }
        return startTimeStr;
    }

    public void setStartTimeStr(String startTimeStr) {
        this.startTimeStr = startTimeStr;
    }

    public String getEndTimeStr() {
        if(endTimeStr == null && endTime != null){
            endTimeStr = format.format(endTime);
        }
        return endTimeStr;
    }

    public void setEndTimeStr(String endTimeStr) {
        this.endTimeStr = endTimeStr;
    }

}
