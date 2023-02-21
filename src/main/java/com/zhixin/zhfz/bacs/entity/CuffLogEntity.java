package com.zhixin.zhfz.bacs.entity;

import java.util.Date;

public class CuffLogEntity  implements java.io.Serializable {

    private static final long serialVersionUID = 7419473014286360077L;
    /**
     * 手环使用记录
     */
    public static final int BINGDING_TYPE = 0;
    public static final int UNBINGDING_TYPE=1;
    public static final int OTHER_TYPE = 2;
    public static final String EDIT_TYPE = "edit";
    public static final String DELETE_TYPE = "delete";
    public static final String RMI_TYPE = "rmi";
    public static final String UPLOAD_TYPE = "upload";
    public static final String DOWNLOAD_TYPE = "download";
    private Long id;

    private Integer cuffId;

    private Integer type;

    private String description;

    private Date createdTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCuffId() {
        return cuffId;
    }

    public void setCuffId(Integer cuffId) {
        this.cuffId = cuffId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
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
        return "CuffLogEntity{" +
                "id=" + id +
                ", cuffId=" + cuffId +
                ", type=" + type +
                ", description='" + description + '\'' +
                ", createdTime=" + createdTime +
                '}';
    }
}