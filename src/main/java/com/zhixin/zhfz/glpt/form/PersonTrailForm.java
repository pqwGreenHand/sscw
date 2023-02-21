package com.zhixin.zhfz.glpt.form;

import java.io.Serializable;
import java.util.Date;

/**
 * @prgram: zhfz
 * @Description: 人员巡查页面参数
 * @Auther: xcf
 * @Date: 2019-04-18 12:43
 */
public class PersonTrailForm implements Serializable {

    private static final long serialVersionUID = 8035326292005301928L;

    private Long id;

    private String fs;

    private Date updatedTime;

    private Date createdTime;

    private int status;

    private Date inTime;

    private Date outTime;

    private int isGet;

    private Date registerTime;

    private String getResult;

    private int count;

    private Date slsj;

    private String rybh;

    private String chuliResult;

    private Date personTime;

    public String getChuliResult() {
        return chuliResult;
    }

    public void setChuliResult(String chuliResult) {
        this.chuliResult = chuliResult;
    }

    public Date getPersonTime() {
        return personTime;
    }

    public void setPersonTime(Date personTime) {
        this.personTime = personTime;
    }

    public String getRybh() {
        return rybh;
    }

    public void setRybh(String rybh) {
        this.rybh = rybh;
    }

    public Date getSlsj() {
        return slsj;
    }

    public void setSlsj(Date slsj) {
        this.slsj = slsj;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFs() {
        return fs;
    }

    public void setFs(String fs) {
        this.fs = fs;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }


    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Date getInTime() {
        return inTime;
    }

    public void setInTime(Date inTime) {
        this.inTime = inTime;
    }

    public int getIsGet() {
        return isGet;
    }

    public void setIsGet(int isGet) {
        this.isGet = isGet;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public String getGetResult() {
        return getResult;
    }

    public void setGetResult(String getResult) {
        this.getResult = getResult;
    }

    public Date getOutTime() {
        return outTime;
    }

    public void setOutTime(Date outTime) {
        this.outTime = outTime;
    }


    @Override
    public String toString() {
        return "PersonTrailForm{" +
                "id=" + id +
                ", fs='" + fs + '\'' +
                ", updatedTime=" + updatedTime +
                ", createdTime=" + createdTime +
                ", status=" + status +
                ", inTime=" + inTime +
                ", outTime=" + outTime +
                ", isGet=" + isGet +
                ", registerTime=" + registerTime +
                ", getResult='" + getResult + '\'' +
                ", count=" + count +
                '}';
    }
}
