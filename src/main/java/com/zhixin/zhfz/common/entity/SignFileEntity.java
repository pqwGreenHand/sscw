package com.zhixin.zhfz.common.entity;

import java.io.Serializable;
import java.util.Date;

public class SignFileEntity implements Serializable {

    private static final long serialVersionUID = -3840512560395311997L;
    private Integer id;

    private Integer serialId;

    private String fileName;

    private Integer fileType;

    private Date creatTime;

    //链表
    private String name;

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

    public Integer getFileType() {
        return fileType;
    }

    public void setFileType(Integer fileType) {
        this.fileType = fileType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "SignFileEntity{" +
                "id=" + id +
                ", serialId=" + serialId +
                ", fileName='" + fileName + '\'' +
                ", fileType='" + fileType + '\'' +
                ", creatTime=" + creatTime +
                ", name=" + name +
                '}';
    }
}