package com.zhixin.zhfz.sacw.entity;

import java.util.Date;

public class InRecordPhotoEntity implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    private int id;
    private long inputRecordId;
    private long involvedId;
    private String url;
    private String fileName;
    private String uuid;
    private Date createdTime;
    private Integer warehouseId;
    private Integer op_user_id;
    private String op_pid;

    public Integer getOp_user_id() {
        return op_user_id;
    }

    public void setOp_user_id(Integer op_user_id) {
        this.op_user_id = op_user_id;
    }

    public String getOp_pid() {
        return op_pid;
    }

    public void setOp_pid(String op_pid) {
        this.op_pid = op_pid;
    }

    public Integer getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Integer warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getInputRecordId() {
        return inputRecordId;
    }

    public void setInputRecordId(long inputRecordId) {
        this.inputRecordId = inputRecordId;
    }

    public long getInvolvedId() {
        return involvedId;
    }

    public void setInvolvedId(long involvedId) {
        this.involvedId = involvedId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    @Override
    public String toString() {
        return "InRecordPhotoEntity [id=" + id + ", inputRecordId=" + inputRecordId + ", involvedId=" + involvedId
                + ", url=" + url + ", createdTime=" + createdTime + "]";
    }

}
