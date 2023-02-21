package com.zhixin.zhfz.jxkp.entity;

import java.io.Serializable;

/**
 * Created by wangly on 2018/5/10.
 */
public class UserJoinEntity implements Serializable{
private String realNameAndPoliceNo;
private String userId;

    public String getRealNameAndPoliceNo() {
        return realNameAndPoliceNo;
    }

    public void setRealNameAndPoliceNo(String realNameAndPoliceNo) {
        this.realNameAndPoliceNo = realNameAndPoliceNo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "UserJoinEntity{" +
                "realNameAndPoliceNo='" + realNameAndPoliceNo + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
