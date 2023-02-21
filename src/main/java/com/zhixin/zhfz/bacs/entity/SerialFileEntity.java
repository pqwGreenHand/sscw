package com.zhixin.zhfz.bacs.entity;

import java.util.Date;

public class SerialFileEntity {
    private Integer id;

    private Integer serialId;

    private String fileName;

    private Date creatTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSerialId() {
        return serialId;
    }

    public void setSerialId(Integer serialId) {
        this.serialId = serialId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Date getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
    }

    @Override
    public String toString() {
        return "SerialFileEntity{" +
                "id=" + id +
                ", serialId=" + serialId +
                ", fileName='" + fileName + '\'' +
                ", creatTime=" + creatTime +
                '}';
    }
}