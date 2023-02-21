package com.zhixin.zhfz.sacw.form;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.util.Date;

@JsonIgnoreProperties({"wareHouseId", "wareHouseId1"})
public class ShelfForm implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    private int id;
    private int locationId;
    private String locationName;
    private String no;
    private String description;
    private Date createdTime;
    private Date updatedTime;
    // 批量添加数据用到
    private int locationId1;
    private String no1;
    private String no2;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
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

    public int getLocationId1() {
        return locationId1;
    }

    public void setLocationId1(int locationId1) {
        this.locationId1 = locationId1;
    }

    public String getNo1() {
        return no1;
    }

    public void setNo1(String no1) {
        this.no1 = no1;
    }

    public String getNo2() {
        return no2;
    }

    public void setNo2(String no2) {
        this.no2 = no2;
    }

    @Override
    public String toString() {
        return "ShelfForm{" +
                "id=" + id +
                ", locationId=" + locationId +
                ", locationName='" + locationName + '\'' +
                ", no='" + no + '\'' +
                ", description='" + description + '\'' +
                ", createdTime=" + createdTime +
                ", updatedTime=" + updatedTime +
                ", locationId1=" + locationId1 +
                ", no1='" + no1 + '\'' +
                ", no2='" + no2 + '\'' +
                '}';
    }
}
