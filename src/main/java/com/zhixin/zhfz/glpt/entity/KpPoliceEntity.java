package com.zhixin.zhfz.glpt.entity;

public class KpPoliceEntity implements java.io.Serializable {
  private String userId;
  private String orgId;
  private String realName;
  private String orgName;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    @Override
    public String toString() {
        return "KpPoliceEntity{" +
                "userId='" + userId + '\'' +
                ", orgId='" + orgId + '\'' +
                ", realName='" + realName + '\'' +
                ", orgName='" + orgName + '\'' +
                '}';
    }
}