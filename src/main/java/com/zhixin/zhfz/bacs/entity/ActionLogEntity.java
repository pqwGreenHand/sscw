/*
 * Copyright (C), 2016-2016 
 * FileName: ActionLogEntity.java
 * auto create by wangguhua
 * Author:   
 * Date:     2016-12-13 11:10:07
 * Description: ActionLogEntity实体类   
 */
 
package com.zhixin.zhfz.bacs.entity;

import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 实体类ActionLogEntity table: action_log
 * 
 * @author wangguhua
 */
public class ActionLogEntity implements Serializable {
    /** Serial UID */
    private static final long serialVersionUID = 1L;
    
    /** id  */
    private Long id;
    
    /** roomId  */
    private Long roomId;
    
    /** personId  */
    private Long personId;
    
    /** userid  */
    private Long userId;
    
    /** actionTime  */
    private Timestamp actionTime;
    //0.看押 1.去审讯,2.出区,3.去卫生间,4.去信息采集,5.医疗,6.其他,9.巡更(personId=0)
    /** resaon  */
    private Integer resaon;
    

    public ActionLogEntity(){
    	
    }
    
    public ActionLogEntity(WaitingRecordEntity waitingRecord, boolean in){
    	this.roomId=Long.valueOf(waitingRecord.getRoomId());
    	this.personId=waitingRecord.getPersonId();
    	if(in){
    		this.userId=Long.valueOf(waitingRecord.getInUserId());
    		this.resaon=0;
    	}else{
            if(waitingRecord.getOutUserId()!=null){
                this.userId=Long.valueOf(waitingRecord.getOutUserId());
            }else {
                this.userId=Long.valueOf(waitingRecord.getInUserId());
            }

    		if(StringUtils.isEmpty(waitingRecord.getGetResult())){
    			this.resaon=1;
        	}else if("去审讯".equals(waitingRecord.getGetResult())){
    			this.resaon=2;
        	}else if("出区".equals(waitingRecord.getGetResult())){
    			this.resaon=2;
        	}else if("去卫生间".equals(waitingRecord.getGetResult())){
    			this.resaon=3;
        	}else if("信息采集".equals(waitingRecord.getGetResult())){
    			this.resaon=4;
        	}else if("医疗".equals(waitingRecord.getGetResult())){
    			this.resaon=4;
        	}
        	else{
        		
        		this.resaon=6;
        	}
    	}
    	
    	actionTime=new Timestamp(System.currentTimeMillis());
    }
    
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
     * @return the roomId
     */
    public Long getRoomId() {
        return this.roomId;
    }

    /**
     * @param roomId the roomId to set
     */
    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }
    
    /**
     * @return the personId
     */
    public Long getPersonId() {
        return this.personId;
    }

    /**
     * @param personId the personId to set
     */
    public void setPersonId(Long personId) {
        this.personId = personId;
    }
    
    /**
     * @return the userid
     */
    public Long getUserId() {
        return this.userId;
    }

    /**
     * @param userid the userid to set
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    /**
     * @return the actionTime
     */
    public Timestamp getActionTime() {
        return this.actionTime;
    }

    /**
     * @param actionTime the actionTime to set
     */
    public void setActionTime(Timestamp actionTime) {
        this.actionTime = actionTime;
    }
    
    /**
     * @return the resaon
     */
    public Integer getResaon() {
        return this.resaon;
    }

    /**
     * @param resaon the resaon to set
     */
    public void setResaon(Integer resaon) {
        this.resaon = resaon;
    }
    
	/**
     * 覆盖父类toString方法
     */    
    @Override
    public String toString() {
        return "ActionLogEntity ["
        	+"id:"+id
        	+",roomId:"+roomId
        	+",personId:"+personId
        	+",userId:"+userId
        	+",actionTime:"+actionTime
        	+",resaon:"+resaon
        +"]";
    }
    

}