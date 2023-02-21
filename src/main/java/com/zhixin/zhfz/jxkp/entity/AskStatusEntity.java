package com.zhixin.zhfz.jxkp.entity;

import java.io.Serializable;

/**
 * Created by wangl on 2018/11/21.
 */
public class AskStatusEntity implements Serializable{
    private int id;
    private String evaId;
    private int askUserId;
    private int oldDfz;
    private int oldKfz;
    private int newDfz;
    private int newKfz;
    private String status;
    private int replyLeaderId;
    private String createTime;
    private String realNameAsk;
    private String realNameReply;
    private String ajbh;
    private String ajmc;
    private String caseType;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEvaId() {
        return evaId;
    }

    public void setEvaId(String evaId) {
        this.evaId = evaId;
    }

    public int getAskUserId() {
        return askUserId;
    }

    public void setAskUserId(int askUserId) {
        this.askUserId = askUserId;
    }

    public int getOldDfz() {
        return oldDfz;
    }

    public void setOldDfz(int oldDfz) {
        this.oldDfz = oldDfz;
    }

    public int getOldKfz() {
        return oldKfz;
    }

    public void setOldKfz(int oldKfz) {
        this.oldKfz = oldKfz;
    }

    public int getNewDfz() {
        return newDfz;
    }

    public void setNewDfz(int newDfz) {
        this.newDfz = newDfz;
    }

    public int getNewKfz() {
        return newKfz;
    }

    public void setNewKfz(int newKfz) {
        this.newKfz = newKfz;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getReplyLeaderId() {
        return replyLeaderId;
    }

    public void setReplyLeaderId(int replyLeaderId) {
        this.replyLeaderId = replyLeaderId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getRealNameAsk() {
        return realNameAsk;
    }

    public void setRealNameAsk(String realNameAsk) {
        this.realNameAsk = realNameAsk;
    }

    public String getRealNameReply() {
        return realNameReply;
    }

    public void setRealNameReply(String realNameReply) {
        this.realNameReply = realNameReply;
    }

    public String getAjbh() {
        return ajbh;
    }

    public void setAjbh(String ajbh) {
        this.ajbh = ajbh;
    }

    public String getAjmc() {
        return ajmc;
    }

    public void setAjmc(String ajmc) {
        this.ajmc = ajmc;
    }

    public String getCaseType() {
        return caseType;
    }

    public void setCaseType(String caseType) {
        this.caseType = caseType;
    }

    @Override
    public String toString() {
        return "AskStatusEntity{" +
                "id=" + id +
                ", evaId='" + evaId + '\'' +
                ", askUserId=" + askUserId +
                ", oldDfz=" + oldDfz +
                ", oldKfz=" + oldKfz +
                ", newDfz=" + newDfz +
                ", newKfz=" + newKfz +
                ", status='" + status + '\'' +
                ", replyLeaderId=" + replyLeaderId +
                ", createTime='" + createTime + '\'' +
                ", realNameAsk='" + realNameAsk + '\'' +
                ", realNameReply='" + realNameReply + '\'' +
                ", ajbh='" + ajbh + '\'' +
                ", ajmc='" + ajmc + '\'' +
                ", caseType='" + caseType + '\'' +
                '}';
    }
}
