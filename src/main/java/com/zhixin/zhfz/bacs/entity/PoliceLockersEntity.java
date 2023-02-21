/*
 * Copyright (C), 2016-2016 
 * FileName: PersonLockersEntity.java
 * auto create by wangguhua
 * Author:   
 * Date:     2016-8-15 9:31:00
 * Description: PersonLockersEntity实体类   
 */
 
package com.zhixin.zhfz.bacs.entity;

import java.io.Serializable;
import java.util.Date;


/**
 * 实体类PersonLockersEntity table: person_lockers
 * 
 * @author wangguhua
 */
public class PoliceLockersEntity implements Serializable {
    /** Serial UID */
    private static final long serialVersionUID = 1L;
    
    /** id  */
    private Long id;
    
    /** lockerId 柜子ID from personal_config_detail */
    private Long lockerId;
    
    /** policeId  */
    private Long policeId;
    
    /** isGet 暂存物品是否已领取，0未领取，1已领取 */
    private Integer isGet;
    
    /** caseId  */
   // private Long caseId;
    
    /** fristLockTime  */
    private Date firstLockTime;
    
    /** lastLockTime  */
    private Date lastLockTime;
    

    /**
     * @return the id
     */
    public Long getId() {
        return this.id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }
    
    /**
     * @return the lockerId
     */
    public Long getLockerId() {
        return this.lockerId;
    }

    /**
     * @param lockerId the lockerId to set
     */
    public void setLockerId(Long lockerId) {
        this.lockerId = lockerId;
    }
    
    /**
     * @return the serialId
     */
    public Long getPoliceId() {
        return this.policeId;
    }

    /**
     * @param policeId the serialId to set
     */
    public void setPoliceId(Long policeId) {
        this.policeId = policeId;
    }
    
    /**
     * @return the isGet
     */
    public Integer getIsGet() {
        return this.isGet;
    }

    /**
     * @param isGet the isGet to set
     */
    public void setIsGet(Integer isGet) {
        this.isGet = isGet;
    }
    

    
    /**

    /**
     * @return the lastLockTime
     */
    public Date getLastLockTime() {
        return this.lastLockTime;
    }

    /**
     * @param lastLockTime the lastLockTime to set
     */
    public void setLastLockTime(Date lastLockTime) {
        this.lastLockTime = lastLockTime;
    }

    public Date getFirstLockTime() {
        return firstLockTime;
    }

    public void setFirstLockTime(Date firstLockTime) {
        this.firstLockTime = firstLockTime;
    }

    @Override
    public String toString() {
        return "PoliceLockersEntity{" +
                "id=" + id +
                ", lockerId=" + lockerId +
                ", policeId=" + policeId +
                ", isGet=" + isGet +
                ", firstLockTime=" + firstLockTime +
                ", lastLockTime=" + lastLockTime +
                '}';
    }
}