package com.zhixin.zhfz.bacs.entity;

import java.util.Date;

public class ApplicenseEntity {
    private Integer id;
    private String app_name;
    private String app_key;
    private String token;
    private Date expired_time;
    private Date created_time;

    public ApplicenseEntity(Integer id, String app_name, String app_key, String token, Date expired_time, Date created_time) {
        this.id = id;
        this.app_name = app_name;
        this.app_key = app_key;
        this.token = token;
        this.expired_time = expired_time;
        this.created_time = created_time;
    }

    public ApplicenseEntity() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getApp_name() {
        return app_name;
    }

    public void setApp_name(String app_name) {
        this.app_name = app_name;
    }

    public String getApp_key() {
        return app_key;
    }

    public void setApp_key(String app_key) {
        this.app_key = app_key;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getExpired_time() {
        return expired_time;
    }

    public void setExpired_time(Date expired_time) {
        this.expired_time = expired_time;
    }

    public Date getCreated_time() {
        return created_time;
    }

    public void setCreated_time(Date created_time) {
        this.created_time = created_time;
    }

    @Override
    public String toString() {
        return "ApplicenseEntity{" +
                "id=" + id +
                ", app_name='" + app_name + '\'' +
                ", app_key='" + app_key + '\'' +
                ", token='" + token + '\'' +
                ", expired_time=" + expired_time +
                ", created_time=" + created_time +
                '}';
    }
}
