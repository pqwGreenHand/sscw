package com.zhixin.zhfz.bacs.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: zhfz
 * @description: 下载任务实体
 * @author: jzw
 * @create: 2019-03-05 15:21
 **/

public class DownloadTaskEntity implements Serializable {

    private static final long serialVersionUID = -1796399577989395664L;
    /**
     * `id` BIGINT ( 64 ) NOT NULL AUTO_INCREMENT,
     * `camera_no` VARCHAR ( 32 ) NOT NULL COMMENT '摄像头监控点编号',
     * `task_no` INT ( 11 ) NOT NULL COMMENT '任务序号',
     * `task_id` BIGINT ( 64 ) DEFAULT '0',
     * `task_type` INT ( 11 ) NOT NULL COMMENT '任务类型，1审讯视频、2轨迹视频,3二期平台审讯视频,4远程辨认视频',
     * `record_id` BIGINT ( 20 ) DEFAULT NULL COMMENT '笔录ID',
     * `serial_id` BIGINT ( 20 ) DEFAULT NULL COMMENT '入区ID',
     * `cuff_no` VARCHAR ( 128 ) DEFAULT NULL COMMENT '手环编号',
     * `status` INT ( 11 ) NOT NULL COMMENT '状态，0初始化，1下载中，2已完成，3已失败',
     * `hash_value` INT ( 11 ) DEFAULT NULL,
     * `count` INT ( 11 ) DEFAULT '0',
     * `size` VARCHAR ( 11 ) DEFAULT '',
     * `start_time` datetime NOT NULL COMMENT '视频下载开始时间',
     * `end_time` datetime NOT NULL COMMENT '视频下载结束时间',
     * `created_time` datetime NOT NULL COMMENT '创建时间',
     * `updated_time` datetime DEFAULT NULL COMMENT '更新时间',
     * `path` VARCHAR ( 255 ) DEFAULT NULL,
     * `error_desc` VARCHAR ( 255 ) DEFAULT NULL,
     **/

    private Long id;

    private String cameraNo;

    private Integer taskNo;

    private Long taskId;

    private Integer taskType;

    private Long recordId;

    private Long serialId;

    private String cuffNo;

    private Integer status;

    private Integer hashValue;

    private Integer count;

    private  String size;

    private Date startTime;

    private Date endTime;

    private Date createdTime;

    private Date updatedTime;

    private String path;

    private String errorDesc;

    private String opPid;

    private Integer opUserId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCameraNo() {
        return cameraNo;
    }

    public void setCameraNo(String cameraNo) {
        this.cameraNo = cameraNo;
    }

    public Integer getTaskNo() {
        return taskNo;
    }

    public void setTaskNo(Integer taskNo) {
        this.taskNo = taskNo;
    }

    public Integer getTaskType() {
        return taskType;
    }

    public void setTaskType(Integer taskType) {
        this.taskType = taskType;
    }

    public Long getRecordId() {
        return recordId;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

    public Long getSerialId() {
        return serialId;
    }

    public void setSerialId(Long serialId) {
        this.serialId = serialId;
    }

    public String getCuffNo() {
        return cuffNo;
    }

    public void setCuffNo(String cuffNo) {
        this.cuffNo = cuffNo;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getHashValue() {
        return hashValue;
    }

    public void setHashValue(Integer hashValue) {
        this.hashValue = hashValue;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getErrorDesc() {
        return errorDesc;
    }

    public void setErrorDesc(String errorDesc) {
        this.errorDesc = errorDesc;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
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
}
