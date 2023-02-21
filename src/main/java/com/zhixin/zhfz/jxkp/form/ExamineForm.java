package com.zhixin.zhfz.jxkp.form;

import java.io.Serializable;

/**
 * Created by wangly on 2018/5/5.
 */
public class ExamineForm implements Serializable {
    private  String subId;
    private String subContext;
    private String subQuotaId;
    private String subPointValue;
    private String subPoliceNo;
    private Integer subEvaId;
    private String statusId;
    private String subStatus;

    public String getSubStatus() {
        return subStatus;
    }

    public void setSubStatus(String subStatus) {
        this.subStatus = subStatus;
    }

    public String getSubId() {
        return subId;
    }

    public void setSubId(String subId) {
        this.subId = subId;
    }

    public String getSubContext() {
        return subContext;
    }

    public void setSubContext(String subContext) {
        this.subContext = subContext;
    }

    public String getSubQuotaId() {
        return subQuotaId;
    }

    public void setSubQuotaId(String subQuotaId) {
        this.subQuotaId = subQuotaId;
    }

    public String getSubPointValue() {
        return subPointValue;
    }

    public void setSubPointValue(String subPointValue) {
        this.subPointValue = subPointValue;
    }

    public String getSubPoliceNo() {
        return subPoliceNo;
    }

    public void setSubPoliceNo(String subPoliceNo) {
        this.subPoliceNo = subPoliceNo;
    }

    public Integer getSubEvaId() {
        return subEvaId;
    }

    public void setSubEvaId(Integer subEvaId) {
        this.subEvaId = subEvaId;
    }

    public String getStatusId() {
        return statusId;
    }
    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }

    @Override
    public String toString() {
        return "ExamineForm{" +
                "subId='" + subId + '\'' +
                ", subContext='" + subContext + '\'' +
                ", subQuotaId='" + subQuotaId + '\'' +
                ", subPointValue='" + subPointValue + '\'' +
                ", subPoliceNo='" + subPoliceNo + '\'' +
                ", subEvaId=" + subEvaId +
                ", statusId='" + statusId + '\'' +
                ", subStatus='" + subStatus + '\'' +
                '}';
    }
}
