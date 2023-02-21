package com.zhixin.zhfz.sacw.entity;

public class OutRecordPhotoEntity implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    private int id;
    private long outputRecordId;
    private long involvedId;
    private Integer warehouseId;
    private String url;
    private String createdTime;
    private String fileName;
    private String uuid;
    private String involvedIdName;//外
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

    public long getOutputRecordId() {
        return outputRecordId;
    }

    public void setOutputRecordId(long outputRecordId) {
        this.outputRecordId = outputRecordId;
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

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getInvolvedIdName() {
        return involvedIdName;
    }

    public void setInvolvedIdName(String involvedIdName) {
        this.involvedIdName = involvedIdName;
    }

    @Override
    public String toString() {
        return "OutRecordPhotoEntity{" +
                "id=" + id +
                ", outputRecordId=" + outputRecordId +
                ", involvedId=" + involvedId +
                ", url='" + url + '\'' +
                ", createdTime='" + createdTime + '\'' +
                ", fileName='" + fileName + '\'' +
                ", uuid='" + uuid + '\'' +
                ", involvedIdName='" + involvedIdName + '\'' +
                '}';
    }
}
