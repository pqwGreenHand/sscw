package com.zhixin.zhfz.bacs.form;

import java.util.Date;

public class ExportForm implements java.io.Serializable {

    private static final long serialVersionUID = 871600227123355981L;

    private String personName;

    private String workSpace;

    private String personType;

    private String personSex;

    private String caseType;

    private String caseProperties;

    private Date betweenTime;

    private Date betweenTimeMax;

    private Date outBetweenTime;

    private Date outBetweenTimeMax;

    private Date trefresh;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getWorkSpace() {
        return workSpace;
    }

    public void setWorkSpace(String workSpace) {
        this.workSpace = workSpace;
    }

    public String getPersonType() {
        return personType;
    }

    public void setPersonType(String personType) {
        this.personType = personType;
    }

    public String getPersonSex() {
        return personSex;
    }

    public void setPersonSex(String personSex) {
        this.personSex = personSex;
    }

    public String getCaseType() {
        return caseType;
    }

    public void setCaseType(String caseType) {
        this.caseType = caseType;
    }

    public String getCaseProperties() {
        return caseProperties;
    }

    public void setCaseProperties(String caseProperties) {
        this.caseProperties = caseProperties;
    }

    public Date getBetweenTime() {
        return betweenTime;
    }

    public void setBetweenTime(Date betweenTime) {
        this.betweenTime = betweenTime;
    }

    public Date getBetweenTimeMax() {
        return betweenTimeMax;
    }

    public void setBetweenTimeMax(Date betweenTimeMax) {
        this.betweenTimeMax = betweenTimeMax;
    }

    public Date getOutBetweenTime() {
        return outBetweenTime;
    }

    public void setOutBetweenTime(Date outBetweenTime) {
        this.outBetweenTime = outBetweenTime;
    }

    public Date getOutBetweenTimeMax() {
        return outBetweenTimeMax;
    }

    public void setOutBetweenTimeMax(Date outBetweenTimeMax) {
        this.outBetweenTimeMax = outBetweenTimeMax;
    }

    public Date getTrefresh() {
        return trefresh;
    }

    public void setTrefresh(Date trefresh) {
        this.trefresh = trefresh;
    }
}
