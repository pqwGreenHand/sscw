package com.zhixin.zhfz.jxkp.entity;

/**
 * Created by wangly on 2018/5/10.
 */
public class ExamineRateEntity {
    private String id;
    private String caseNo;
    private String caseName;
    private String afz;
    private String bfm;
    private String caseType;
    private String realName;
    private String rate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCaseNo() {
        return caseNo;
    }

    public void setCaseNo(String caseNo) {
        this.caseNo = caseNo;
    }

    public String getCaseName() {
        return caseName;
    }

    public void setCaseName(String caseName) {
        this.caseName = caseName;
    }

    public String getAfz() {
        return afz;
    }

    public void setAfz(String afz) {
        this.afz = afz;
    }

    public String getBfm() {
        return bfm;
    }

    public void setBfm(String bfm) {
        this.bfm = bfm;
    }

    public String getCaseType() {
        return caseType;
    }

    public void setCaseType(String caseType) {
        this.caseType = caseType;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    @Override
    public String toString() {
        return "ExamineRateEntity{" +
                "id='" + id + '\'' +
                ", caseNo='" + caseNo + '\'' +
                ", caseName='" + caseName + '\'' +
                ", afz='" + afz + '\'' +
                ", bfm='" + bfm + '\'' +
                ", caseType='" + caseType + '\'' +
                ", realName='" + realName + '\'' +
                ", rate='" + rate + '\'' +
                '}';
    }
}
