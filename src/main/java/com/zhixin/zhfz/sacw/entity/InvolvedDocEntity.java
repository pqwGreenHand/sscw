package com.zhixin.zhfz.sacw.entity;

import java.io.Serializable;
import java.util.Map;

public class InvolvedDocEntity implements Serializable {

    private static final long serialVersionUID = -3209220984599504620L;

    private int fileType;
    private String tempFileName;//预览需要生成的临时文件名
    private String xmlFileName;//生成文件所需xml文件
    private String downFileName;//下载定义的文件名
    private Map data;//模板对应的数据
    private Long involvedId;//物品ID
    private Long recordId;//出入库记录ID

    public int getFileType() {
        return fileType;
    }

    public void setFileType(int fileType) {
        this.fileType = fileType;
    }

    public String getTempFileName() {
        return tempFileName;
    }

    public void setTempFileName(String tempFileName) {
        this.tempFileName = tempFileName;
    }

    public String getXmlFileName() {
        return xmlFileName;
    }

    public void setXmlFileName(String xmlFileName) {
        this.xmlFileName = xmlFileName;
    }

    public String getDownFileName() {
        return downFileName;
    }

    public void setDownFileName(String downFileName) {
        this.downFileName = downFileName;
    }

    public Map getData() {
        return data;
    }

    public void setData(Map data) {
        this.data = data;
    }

    public Long getInvolvedId() {
        return involvedId;
    }

    public void setInvolvedId(Long involvedId) {
        this.involvedId = involvedId;
    }

    public Long getRecordId() {
        return recordId;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }
}
