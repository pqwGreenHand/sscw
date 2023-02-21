package com.zhixin.zhfz.sacw.form;


public class UserNoSearchForm implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    private String userNo;

    @Override
    public String toString() {
        return "UserNoSearchForm [userNo=" + userNo + "]";
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

}