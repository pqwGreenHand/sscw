package com.zhixin.zhfz.glpt.entity;

import java.util.Date;

public class KpEvaEntity implements java.io.Serializable {
  private String id;
  private String caseNo;
  private String  caseName;
  private String  realName;
  private String status;
  private String sche;
  private String scorevalue;

    public String getSche() {
        return sche;
    }

    public void setSche(String sche) {
        this.sche = sche;
    }

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

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getScorevalue() {
        return scorevalue;
    }

    public void setScorevalue(String scorevalue) {
        this.scorevalue = scorevalue;
    }

    @Override
    public String toString() {
        return "KpEvaEntity{" +
                "id='" + id + '\'' +
                ", caseNo='" + caseNo + '\'' +
                ", caseName='" + caseName + '\'' +
                ", realName='" + realName + '\'' +
                ", status='" + status + '\'' +
                ", sche='" + sche + '\'' +
                ", scorevalue='" + scorevalue + '\'' +
                '}';
    }
}