package com.zhixin.zhfz.bacs.entity;

import java.util.Date;

public class BelongDetailTempEntity implements java.io.Serializable {
    /**
     * 储物柜使用情况
     */
    private static final long serialVersionUID = 4614966861269493860L;

    private Integer id;
    private Integer tempId;
    private String wpUuid;
    private String name;
    private Integer detailCount;
    private String unit;
    private String saveMethod;
    private String description;
    private Date createdTime;
    private Date updatedTime;

    public Integer getTempId() {
        return tempId;
    }

    public void setTempId(Integer tempId) {
        this.tempId = tempId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWpUuid() {
        return wpUuid;
    }

    public void setWpUuid(String wpUuid) {
        this.wpUuid = wpUuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDetailCount() {
        return detailCount;
    }

    public void setDetailCount(Integer detailCount) {
        this.detailCount = detailCount;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getSaveMethod() {
        return saveMethod;
    }

    public void setSaveMethod(String saveMethod) {
        this.saveMethod = saveMethod;
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

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }
}