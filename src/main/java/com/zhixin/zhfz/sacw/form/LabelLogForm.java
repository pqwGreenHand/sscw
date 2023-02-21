package com.zhixin.zhfz.sacw.form;

import java.io.Serializable;
import java.util.Date;

public class LabelLogForm implements Serializable {
    /**
     * RFID标签使用记录
     */
    private static final long serialVersionUID = 4058652044084380833L;

    private long id;
    private int labelId;
    private String labelNo;
    private int type;
    private String description;
    private Date createdTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getLabelId() {
        return labelId;
    }

    public void setLabelId(int labelId) {
        this.labelId = labelId;
    }

    public String getLabelNo() {
        return labelNo;
    }

    public void setLabelNo(String labelNo) {
        this.labelNo = labelNo;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    @Override
    public String toString() {
        return "LabelLogForm{" +
                "id=" + id +
                ", labelId=" + labelId +
                ", labelNo='" + labelNo + '\'' +
                ", type=" + type +
                ", description='" + description + '\'' +
                ", createdTime=" + createdTime +
                '}';
    }
}