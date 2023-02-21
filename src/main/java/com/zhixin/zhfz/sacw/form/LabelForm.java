package com.zhixin.zhfz.sacw.form;

import java.io.Serializable;
import java.util.Date;

/*
 * RFID标签
 * */
public class LabelForm implements Serializable {

    private static final long serialVersionUID = -5103704590064454356L;

    private Integer id;

    private String labelNo;

    private String icNo;

    private Integer status;

    private Integer type;

    private Integer warehouseId;

    private String warehouseName;

    private Date createdTime;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLabelNo() {
        return labelNo;
    }

    public void setLabelNo(String labelNo) {
        this.labelNo = labelNo;
    }

    public String getIcNo() {
        return icNo;
    }

    public void setIcNo(String icNo) {
        this.icNo = icNo;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Integer warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    @Override
    public String toString() {
        return "LabelForm{" +
                "id=" + id +
                ", labelNo='" + labelNo + '\'' +
                ", icNo='" + icNo + '\'' +
                ", status=" + status +
                ", type=" + type +
                ", warehouseId=" + warehouseId +
                ", warehouseName='" + warehouseName + '\'' +
                ", createdTime=" + createdTime +
                '}';
    }
}