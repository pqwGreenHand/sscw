package com.zhixin.zhfz.jxkp.entity;

import java.util.Date;

/**
 * Created by zuri-pc on 2018/8/11.
 */
public class gdjzSynConfigEntity implements java.io.Serializable {

    private long id;
    private Date startTime;
    private Date endSynTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndSynTime() {
        return endSynTime;
    }

    public void setEndSynTime(Date endSynTime) {
        this.endSynTime = endSynTime;
    }

    public Date getMidSynTime() {
        return midSynTime;
    }

    public void setMidSynTime(Date midSynTime) {
        this.midSynTime = midSynTime;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    private Date midSynTime;
    private int type;
    private int quantity;

    @Override
    public String toString() {
        return "gd_jz_SynConfigEntity [id=" + id + ", startTime=" + startTime + ", endSynTime=" + endSynTime + ", midSynTime="
                + midSynTime + ", type=" + type + ", quantity=" + quantity
                + "]";
    }
}
