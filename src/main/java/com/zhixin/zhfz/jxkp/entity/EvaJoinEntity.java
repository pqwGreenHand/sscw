package com.zhixin.zhfz.jxkp.entity;

import java.io.Serializable;

/**
 * Created by wangly on 2018/5/10.
 */
public class EvaJoinEntity implements Serializable{
private Integer id;
private Integer evaId;
private Integer userId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEvaId() {
        return evaId;
    }

    public void setEvaId(Integer evaId) {
        this.evaId = evaId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "EvaJoinEntity{" +
                "id=" + id +
                ", evaId=" + evaId +
                ", userId=" + userId +
                '}';
    }
}
