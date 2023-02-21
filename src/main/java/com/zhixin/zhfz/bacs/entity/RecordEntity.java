package com.zhixin.zhfz.bacs.entity;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @program: zhfz
 * @description: 审讯实体
 * @author: jzw
 * @create: 2019-03-02 10:54
 **/

public class RecordEntity implements Serializable {

    /**
     * `id` BIGINT ( 20 ) NOT NULL AUTO_INCREMENT,
     * `uuid` VARCHAR ( 50 ) NOT NULL COMMENT '案件UUID',
     * `serial_id` BIGINT ( 20 ) NOT NULL COMMENT '专属编号ID',
     * `status` INT ( 11 ) NOT NULL COMMENT '0初始化 1审讯中 2点播中 3已完成',
     * `count` INT ( 11 ) NOT NULL COMMENT '审讯次数',
     * `person_id` BIGINT ( 20 ) NOT NULL COMMENT '人员ID',
     * `area_id` INT ( 11 ) NOT NULL COMMENT '办案场所ID',
     * `case_id` INT ( 11 ) DEFAULT NULL COMMENT '案件ID',
     * `record_template_id` INT ( 11 ) NOT NULL COMMENT '笔录模板ID(word模板ID)',
     * `start_time` datetime NOT NULL COMMENT '询问开始时间',
     * `end_time` datetime DEFAULT NULL COMMENT '询问结束时间',
     * `room_id` INT ( 11 ) DEFAULT NULL COMMENT '询问地点，即所在功能室ID',
     * `police_ask_id` INT ( 11 ) NOT NULL COMMENT '询问民警ID',
     * `police_record_id` INT ( 11 ) NOT NULL COMMENT '记录民警ID',
     * `record_type_id` INT ( 1 ) DEFAULT '1',
     * `created_time` datetime NOT NULL COMMENT '创建时间',
     * `updated_time` datetime DEFAULT NULL COMMENT '更新时间',
     * `temp_file_path ` VARCHAR ( 255 ) DEFAULT NULL,
     * `word` MEDIUMBLOB,
     **/

    private SimpleDateFormat format;

    public RecordEntity(){
        format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        format.setLenient(true);
    }

    private Long id;

    private String uuid;

    private String serialUuid;

    private String tempFilePath;

    private byte[] word;

    private Integer status = 0;

    private Integer count;

    private Long recordTemplateId;

    private Long recordTypeId;

    private Long roomId;

    private Long serialId;

//    private Long registerUserId;

    private Long policeAskId;

    private Long policeRecordId;

    private Long caseId;

    private Long areaId;

    private Long personId;

    private Date createdTime;

    private Date updatedTime;

    private Date startTime;

    private Date endTime;

    private String policeAskName; //入区警号

    private String policeRecordName; //出区警号

    private String personName; //人员姓名

    private String caseNo; //案件编号

    private String areaName; //办案场所名称

    private String roomName; //办案场所名称

    private String organizationName; //办案场所名称

    private String caseType;

    private String caseDesc;

    private String startTimeStr;

    private String endTimeStr;

    //嫌疑人出区添加
    private Integer rmId;
    private Integer recordId;
    public Integer getRmId() {
        return rmId;
    }
    public void setRmId(Integer rmId) {
        this.rmId = rmId;
    }

    public Integer getRecordId() {
        return recordId;
    }

    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }
    private String opPid;
    private Integer opUserId;

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

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getTempFilePath() {
        return tempFilePath;
    }

    public void setTempFilePath(String tempFilePath) {
        this.tempFilePath = tempFilePath;
    }

    public byte[] getWord() {
        return word;
    }

    public void setWord(byte[] word) {
        this.word = word;
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

    public Long getRecordTemplateId() {
        return recordTemplateId;
    }

    public void setRecordTemplateId(Long recordTemplateId) {
        this.recordTemplateId = recordTemplateId;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public Long getSerialId() {
        return serialId;
    }

    public void setSerialId(Long serialId) {
        this.serialId = serialId;
    }

//    public Long getRegisterUserId() {
//        return registerUserId;
//    }

//    public void setRegisterUserId(Long registerUserId) {
//        this.registerUserId = registerUserId;
//    }

    public Long getPoliceAskId() {
        return policeAskId;
    }

    public void setPoliceAskId(Long policeAskId) {
        this.policeAskId = policeAskId;
    }

    public Long getPoliceRecordId() {
        return policeRecordId;
    }

    public void setPoliceRecordId(Long policeRecordId) {
        this.policeRecordId = policeRecordId;
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

  /*  public Date getStartTime() throws ParseException {
        if(startTime == null && startTimeStr != null && startTimeStr != ""){
            startTime = format.parse(startTimeStr);
        }
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() throws ParseException {
        if(endTime == null && endTimeStr != null && endTimeStr != ""){
            format.parse(endTimeStr);
        }

        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }*/
    

    public String getPoliceAskName() {
        return policeAskName;
    }

    public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public void setPoliceAskName(String policeAskName) {
        this.policeAskName = policeAskName;
    }

    public String getPoliceRecordName() {
        return policeRecordName;
    }

    public void setPoliceRecordName(String policeRecordName) {
        this.policeRecordName = policeRecordName;
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

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
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

    public Long getRecordTypeId() {
        return recordTypeId;
    }

    public void setRecordTypeId(Long recordTypeId) {
        this.recordTypeId = recordTypeId;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getCaseType() {
        return caseType;
    }

    public void setCaseType(String caseType) {
        this.caseType = caseType;
    }

    public String getCaseDesc() {
        return caseDesc;
    }

    public void setCaseDesc(String caseDesc) {
        this.caseDesc = caseDesc;
    }


    public String getSerialUuid() {
        return serialUuid;
    }

    public void setSerialUuid(String serialUuid) {
        this.serialUuid = serialUuid;
    }

    @Override
    public String toString() {
        return "RecordEntity{" +
                "id=" + id +
                ", uuid='" + uuid + '\'' +
                ", serialUuid='" + serialUuid + '\'' +
                ", tempFilePath='" + tempFilePath + '\'' +
                ", status=" + status +
                ", count=" + count +
                ", recordTemplateId=" + recordTemplateId +
                ", recordTypeId=" + recordTypeId +
                ", roomId=" + roomId +
                ", serialId=" + serialId +
                ", policeAskId=" + policeAskId +
                ", policeRecordId=" + policeRecordId +
                ", caseId=" + caseId +
                ", areaId=" + areaId +
                ", personId=" + personId +
                ", policeAskName='" + policeAskName + '\'' +
                ", policeRecordName='" + policeRecordName + '\'' +
                ", personName='" + personName + '\'' +
                ", caseNo='" + caseNo + '\'' +
                ", areaName='" + areaName + '\'' +
                ", roomName='" + roomName + '\'' +
                ", organizationName='" + organizationName + '\'' +
                ", caseType='" + caseType + '\'' +
                ", caseDesc='" + caseDesc + '\'' +
                ", startTimeStr='" + startTimeStr + '\'' +
                ", endTimeStr='" + endTimeStr + '\'' +
                ", rmId=" + rmId +
                ", recordId=" + recordId +
                ", opPid='" + opPid + '\'' +
                ", opUserId=" + opUserId +
                 ", startTime=" + startTime +
                  ", endTime=" + endTime +
                '}';
    }
    
    
}
