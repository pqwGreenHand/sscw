package com.zhixin.zhfz.bacs.entity;

import java.util.Date;

public class BelongVideoEntity {

    private Integer id;
    private Integer belongId;
    private String url;
    private Integer type;
    private Integer createdUser;
    private Date createdTime;

    @Override
    public String toString() {
        return "BelongVideoEntity{" +
                "id=" + id +
                ", belongId=" + belongId +
                ", url='" + url + '\'' +
                ", type=" + type +
                ", createdUser=" + createdUser +
                ", createdTime=" + createdTime +
                ", createdUserName='" + createdUserName + '\'' +
                ", bz='" + bz + '\'' +
                '}';
    }

    private String createdUserName;

    private String bz;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBelongId() {
        return belongId;
    }

    public void setBelongId(Integer belongId) {
        this.belongId = belongId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(Integer createdUser) {
        this.createdUser = createdUser;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public String getCreatedUserName() {
        return createdUserName;
    }

    public void setCreatedUserName(String createdUserName) {
        this.createdUserName = createdUserName;
    }
}
