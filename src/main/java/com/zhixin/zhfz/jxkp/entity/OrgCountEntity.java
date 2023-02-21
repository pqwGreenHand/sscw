package com.zhixin.zhfz.jxkp.entity;

import java.io.Serializable;

/**
 * Created by wangl on 2018/12/30.
 */
public class OrgCountEntity implements Serializable {
  private String count;
  private String orgName;
  private String concatCountOrgName;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getConcatCountOrgName() {
        return concatCountOrgName;
    }

    public void setConcatCountOrgName(String concatCountOrgName) {
        this.concatCountOrgName = concatCountOrgName;
    }

    @Override
    public String toString() {
        return "OrgCountEntity{" +
                "count='" + count + '\'' +
                ", orgName='" + orgName + '\'' +
                ", concatCountOrgName='" + concatCountOrgName + '\'' +
                '}';
    }
}
