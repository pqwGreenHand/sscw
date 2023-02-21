package com.zhixin.zhfz.bacs.entity;

import java.io.Serializable;

public class DocEnforcementVideoEntity implements Serializable {

	private static final long serialVersionUID = 8931620674504631941L;

	/**
	 * 现场、搜查执法记录仪录像底联说明
	 */
	
    private Integer id;

    private Long serialId;

    private String dataContent;

    private String format;

    private String evidtime;

    private String extractionAddress;

    private String extractionMethod;

    private String videosource;

    private String original;

    private String reason;

    private String originalplace;

    private String witnessPerson;

    private String producer;

    private String policeName;

    private String unit;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getSerialId() {
        return serialId;
    }

    public void setSerialId(Long serialId) {
        this.serialId = serialId;
    }

    public String getDataContent() {
        return dataContent;
    }

    public void setDataContent(String dataContent) {
        this.dataContent = dataContent == null ? null : dataContent.trim();
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format == null ? null : format.trim();
    }

    public String getEvidtime() {
        return evidtime;
    }

    public void setEvidtime(String evidtime) {
        this.evidtime = evidtime == null ? null : evidtime.trim();
    }

    public String getExtractionAddress() {
        return extractionAddress;
    }

    public void setExtractionAddress(String extractionAddress) {
        this.extractionAddress = extractionAddress == null ? null : extractionAddress.trim();
    }

    public String getExtractionMethod() {
        return extractionMethod;
    }

    public void setExtractionMethod(String extractionMethod) {
        this.extractionMethod = extractionMethod == null ? null : extractionMethod.trim();
    }

    public String getVideosource() {
        return videosource;
    }

    public void setVideosource(String videosource) {
        this.videosource = videosource == null ? null : videosource.trim();
    }

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original == null ? null : original.trim();
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason == null ? null : reason.trim();
    }

    public String getOriginalplace() {
        return originalplace;
    }

    public void setOriginalplace(String originalplace) {
        this.originalplace = originalplace == null ? null : originalplace.trim();
    }

    public String getWitnessPerson() {
        return witnessPerson;
    }

    public void setWitnessPerson(String witnessPerson) {
        this.witnessPerson = witnessPerson == null ? null : witnessPerson.trim();
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer == null ? null : producer.trim();
    }

    public String getPoliceName() {
        return policeName;
    }

    public void setPoliceName(String policeName) {
        this.policeName = policeName == null ? null : policeName.trim();
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }

    @Override
    public String toString() {
        return "DocEnforcementVideoEntity{" +
                "id=" + id +
                ", serialId=" + serialId +
                ", dataContent='" + dataContent + '\'' +
                ", format='" + format + '\'' +
                ", evidtime='" + evidtime + '\'' +
                ", extractionAddress='" + extractionAddress + '\'' +
                ", extractionMethod='" + extractionMethod + '\'' +
                ", videosource='" + videosource + '\'' +
                ", original='" + original + '\'' +
                ", reason='" + reason + '\'' +
                ", originalplace='" + originalplace + '\'' +
                ", witnessPerson='" + witnessPerson + '\'' +
                ", producer='" + producer + '\'' +
                ", policeName='" + policeName + '\'' +
                ", unit='" + unit + '\'' +
                '}';
    }
}