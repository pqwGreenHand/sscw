package com.zhixin.zhfz.bacs.entity;

import java.io.Serializable;
import java.util.Date;

public class UrinalysisDetailEntity  implements Serializable {

    private static final long serialVersionUID = 6493986047704058943L;

    private Long id;

    private Long urinalysisId;

    private String checkItem;

    private String itemResult;

    private Integer checkUserId;

    private Date createdTime;

    private String opPid;

    private Integer opUserId;

    public String getOpPid() {
        return opPid;
    }

    public void setOpPid(String opPid) {
        this.opPid = opPid;
    }

    public Integer getOpUserId() {
        return opUserId;
    }

    public void setOpUserId(Integer opUserId) {
        this.opUserId = opUserId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUrinalysisId() {
        return urinalysisId;
    }

    public void setUrinalysisId(Long urinalysisId) {
        this.urinalysisId = urinalysisId;
    }

    public String getCheckItem() {
        return checkItem;
    }

    public void setCheckItem(String checkItem) {
        this.checkItem = checkItem;
    }

    public String getItemResult() {
        return itemResult;
    }

    public void setItemResult(String itemResult) {
        this.itemResult = itemResult;
    }

    public Integer getCheckUserId() {
        return checkUserId;
    }

    public void setCheckUserId(Integer checkUserId) {
        this.checkUserId = checkUserId;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    @Override
    public String toString() {
        return "UrinalysisDetailEntity{" +
                "id=" + id +
                ", urinalysisId=" + urinalysisId +
                ", checkItem='" + checkItem + '\'' +
                ", itemResult='" + itemResult + '\'' +
                ", checkUserId=" + checkUserId +
                ", createdTime=" + createdTime +
                ", opPid='" + opPid + '\'' +
                ", opUserId=" + opUserId +
                '}';
    }
}